package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Activity;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Day;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Event;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.ForecastInfo;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Weekend;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.CalendarOrder;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.DateUtils;
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

@Component
@EnableAsync
public class BoredWeekendProsumerCore {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoredWeekendProsumerCore.class);

	@Autowired
	private ActivityServiceClientSpring activityServiceClient;

	@Autowired
	private EventServiceClientSpring eventServiceClientSpring;

	@Autowired
	private WundergroundServiceClientSpring wundergroundServiceClient;

	public Weekend buildWeekend(String city, Date startDate, Date endDate, List<String> preferences, int distance) {
		LOGGER.info("BoredWeekend is handling the getWeekends request in its core");

		LOGGER.info("Retreiving coordinates of city " + city);
		double[] latlon = GoogleMapsGeocodeServiceClient.callService(city);

		LOGGER.debug("Coordinates: " + latlon[0] + "," + latlon[1]);

//		// Start thread for weather forecast service
//		LOGGER.info("Starting new thread for calling forecast service...");
//		WundergroundServiceClient forecastService = new WundergroundServiceClient(latlon[0], latlon[1]);
//		forecastService.run();

        CompletableFuture<ForecastInfo> forecastinfo = wundergroundServiceClient.getForecastInfo(latlon[0], latlon[1]);

		LOGGER.info("Retreiving nearby cities...");
		List<String> nearbyCities = callGeoNamesService(latlon[0], latlon[1], distance);

		if (nearbyCities==null || nearbyCities.isEmpty() || !nearbyCities.contains(city)) {
			nearbyCities.add(city);
		}

		List<String> days = DateUtils.getWeekdaysInInterval(startDate, endDate);

//		//Starting threads for activities and events
//		ActivityServiceClient activityService = new ActivityServiceClient(nearbyCities, preferences, days);
//		activityService.run();
//
//		EventServiceClient eventService = new EventServiceClient(nearbyCities, startDate, endDate);
//		eventService.run();
//
//		//Join threads
//		try {
//			forecastService.join();
//			activityService.join();
//			eventService.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		 // Kick of multiple, asynchronous lookups
        CompletableFuture<List<Activity>> activity = activityServiceClient.getActivity(nearbyCities, preferences, days);
        CompletableFuture<List<Event>> events1 = eventServiceClientSpring.getEvent(nearbyCities, startDate, endDate);

        // Wait until they are all done
        CompletableFuture.allOf(activity,events1,forecastinfo).join();
		
        //Recovering data from threads
		ForecastInfo forecast = new ForecastInfo();
		List<Activity> activities = new ArrayList<>();
		List<Event> events = new ArrayList<>();
		
		try {
			forecast = forecastinfo.get();
			activities = activity.get();
			events = events1.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		Weekend weekend = composeData(activities, events, forecast, startDate, endDate);
		LOGGER.info("Returning weekend");
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

		String startWeekdayName = DateUtils.getWeekdayName(startDate);
		String endWeekdayName = DateUtils.getWeekdayName(endDate);

		LOGGER.info("Starting weekday: " + startWeekdayName);
		LOGGER.info("Ending weekday: " + endWeekdayName);

		LOGGER.info(activities.size() + " Activities found");
		LOGGER.info(events.size() + " Events found");

		List<Date> dates = DateUtils.getDaysBetweenDates(startDate, endDate);

		Map<String, Day> days = new HashMap<>();

		// Create list of days
		for (Date date : dates) {
			Day day = new Day(date);
			day.setWeatherForecast(forecast.getDayForecast(date));
			days.put(DateUtils.getDateSimpleRepresentation(date), day);
		}

		// Filter activities and add to each day
		for (Activity activity : activities) {
			for (Map.Entry<String, Day> dayEntry : days.entrySet()) {
				Day day = dayEntry.getValue();
				if (activity.isAvailable() && activity.getDays().contains(day.getDay()) && (!activity.getCategories().contains("Outdoors") || day.isGoodWeather())) {
					day.getActivities().add(activity);
				}
			}
		}

		// Add events to each day
		for (Event event : events) {
			List<Date> eventDays = DateUtils.getDaysBetweenDates(event.getStart(), event.getEnd());
			for (Date eventDay : eventDays) {
				String dayRepresentation = DateUtils.getDateSimpleRepresentation(eventDay);
				if (days.containsKey(dayRepresentation)) {
					days.get(dayRepresentation).getEvents().add(event);
				}
			}
		}
		
		Weekend weekend = new Weekend();
		
		//sorting days
		List<Day> daylist = new ArrayList<>(days.values());
		Collections.sort(daylist, new CalendarOrder());
		
		weekend.getDays().addAll(daylist);
		

		return weekend;
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
