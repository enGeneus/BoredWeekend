package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Activity;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Day;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Event;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.ForecastInfo;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Weekend;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.DataUtils;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.ProviderServiceUtils;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivitiesPT;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivitiesService;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivityType;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.AddActivityRequest;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.Daytime;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.Position;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.WeekDay;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.AddEventRequest;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventCategory;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventType;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsPT;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsService;

public class BoredWeekendProsumerCore {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoredWeekendProsumerCore.class);

	public Weekend buildWeekend(String city, Date startDate, Date endDate, List<String> preferences, String daytime, int distance) {
		LOGGER.info("BoredWeekend is handling the getWeekends request in its core");

		LOGGER.info("Retreiving coordinates of city " + city);
		double[] latlon = callMapsGeocodeService(city);

		LOGGER.debug("Coordinates: " + latlon[0] + "," + latlon[1]);

		// Start thread for weather forecast service
		LOGGER.info("Starting new thread for calling forecast service...");
		WundergroundServiceClient forecastService = new WundergroundServiceClient(latlon[0], latlon[1]);
		forecastService.run();

		LOGGER.info("Retreiving nearby cities...");
		List<String> nearbyCities = callGeoNamesService(latlon[0], latlon[1], distance);

		for (String currentCity : nearbyCities) {
			LOGGER.debug("Nearby City found: " + currentCity);
		}

		if (nearbyCities==null || nearbyCities.isEmpty() || !nearbyCities.contains(city)) {
			LOGGER.debug("Forced selected city into nerby list");
			nearbyCities.add(city);
		}

		List<String> days = DataUtils.getWeekdaysInInterval(startDate, endDate);

		//Starting threads for activities and events
		ActivityServiceClient activityService = new ActivityServiceClient(nearbyCities, preferences, days, daytime);
		activityService.run();

		EventServiceClient eventService = new EventServiceClient(nearbyCities, startDate, endDate);
		eventService.run();

		//Join threads
		try {
			forecastService.join();
			activityService.join();
			eventService.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Recovering data from threads
		ForecastInfo forecast = forecastService.getForecast();
		List<Activity> activities = activityService.getActivity();
		List<Event> events = eventService.getEvents();

		Weekend weekend = composeData(activities, events, forecast, startDate, endDate);
		return weekend;
	}

	public void insertActivity(Activity activity) {
		ActivityType activityType = new ActivityType();

		activityType.setName(activity.getName());
		activityType.setInfo(activity.getInfo());
		activityType.setCity(activity.getCity());
		activityType.setPayment(activity.isPayment());
		activityType.setState(true);
		activityType.setImg(activity.getImg());
		activityType.setDaytime(Daytime.fromValue(activity.getDaytime()));
		
		Position position = new Position();
		position.setLatitude(activity.getLat());
		position.setLongitude(activity.getLon());
		activityType.setPosition(position);

		for (String category : activity.getCategories()) {
			activityType.getCategory().add(ActivityCategory.fromValue(category));
		}

		for (String weekDay : activity.getDays()) {
			activityType.getDays().add(WeekDay.fromValue(weekDay));
		}

		ActivitiesService service = new ActivitiesService();
		ActivitiesPT port = service.getActivitiesPort();
		AddActivityRequest request = new AddActivityRequest();
		request.setActivity(activityType);
		port.addActivity(request.getActivity());
		
	}

	public void insertEvent(Event event) {
		EventType eventType = new EventType();

		eventType.setAddress(event.getAddress());
		eventType.setCity(event.getCity());
		eventType.setCategory(EventCategory.fromValue(event.getCategories()));
		eventType.setDescription(event.getDescription());
		eventType.setEnd(event.getEnd());
		eventType.setInfo(event.getInfo());
		eventType.setImg(event.getImg());
		eventType.setLocationName(event.getLocationName());
		eventType.setName(event.getName());
		eventType.setPayment(event.isPayment());
		eventType.setStart(event.getStart());

		EventsService service = new EventsService();
		EventsPT port = service.getEventsPort();
		AddEventRequest request = new AddEventRequest();
		request.setEvent(eventType);
		port.addEvent(request.getEvent());
	}

	private Weekend composeData(List<Activity> activities, List<Event> events, ForecastInfo forecast, Date startDate, Date endDate) {

		String startWeekdayName = DataUtils.getWeekdayName(startDate);
		String endWeekdayName = DataUtils.getWeekdayName(endDate);

		LOGGER.debug("Starting weekday: " + startWeekdayName);
		LOGGER.debug("Ending weekday: " + endWeekdayName);

		LOGGER.debug(activities.size() + " Activities found");
		LOGGER.debug(events.size() + " Events found");

		List<Date> dates = DataUtils.getDaysBetweenDates(startDate, endDate);

		Map<String, Day> days = new HashMap<>();

		// Create list of days
		for (Date date : dates) {
			LOGGER.debug("Creating new weekend day for " + date.toString());
			Day day = new Day(date);
			day.setWeatherForecast(forecast.getDayForecast(date));
			days.put(DataUtils.getDateSimpleRepresentation(date), day);
		}

		// Filter activities and add to each day
		for (Activity activity : activities) {
			for (Map.Entry<String, Day> dayEntry : days.entrySet()) {
				Day day = dayEntry.getValue();
				if (activity.isAvailable() && activity.getDays().contains(day.getDay()) && (!activity.getCategories().contains("Outdoors") || day.isGoodWeather())) {
					LOGGER.debug("Adding activity " + activity.getName() + " to day " + day.getDay() + " " + day.getDayNumber());
					day.getActivities().add(activity);
				}
			}
		}

		// Add events to each day
		for (Event event : events) {
			List<Date> eventDays = DataUtils.getDaysBetweenDates(event.getStart(), event.getEnd());
			for (Date eventDay : eventDays) {
				String dayRepresentation = DataUtils.getDateSimpleRepresentation(eventDay);
				if (days.containsKey(dayRepresentation)) {
					days.get(dayRepresentation).getEvents().add(event);
				}
			}
		}
		
		Weekend weekend = new Weekend();

		weekend.getDays().addAll(days.values());

		return weekend;
	}


	private double[] callMapsGeocodeService(String cityName) {
		double[] latlon = new double[2];

		try {
			LOGGER.info("Calling maps geocode service");
			String mapsGeocodeStringUrl = ProviderServiceUtils.buildMapsGeocodeURL(cityName);

			LOGGER.info("URL: " + mapsGeocodeStringUrl);

			URL mapsGeocodeUrl = new URL(mapsGeocodeStringUrl);
			HttpURLConnection c = (HttpURLConnection) mapsGeocodeUrl.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.connect();
			int status = c.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();

				if (c != null) {
					c.disconnect();
				}

				JSONObject response = new JSONObject(sb.toString());

				LOGGER.debug(response.toString());

				JSONObject location = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
						.getJSONObject("location");
				latlon[0] = location.getDouble("lat");
				latlon[1] = location.getDouble("lng");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return latlon;
	}

	private List<String> callGeoNamesService(double lat, double lon, int radius) {
		List<String> nearbycities = new ArrayList<>();

		try {
			LOGGER.info("Calling geonames service");
			String geoNamesStringUrl = ProviderServiceUtils.buildGeoNamesURL(lat, lon, radius);

			LOGGER.info("URL: " + geoNamesStringUrl);

			URL geoNamesUrl = new URL(geoNamesStringUrl);
			HttpURLConnection c = (HttpURLConnection) geoNamesUrl.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.connect();
			int status = c.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();

				if (c != null) {
					c.disconnect();
				}

				JSONObject response = new JSONObject(sb.toString());

				LOGGER.debug(response.toString());

				JSONArray geonames = response.getJSONArray("geonames");
				for (int i = 0; i < geonames.length(); i++) {
					JSONObject name = geonames.getJSONObject(i);
					nearbycities.add(name.getString("name").replace("’", "'"));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return nearbycities;
	}

}
