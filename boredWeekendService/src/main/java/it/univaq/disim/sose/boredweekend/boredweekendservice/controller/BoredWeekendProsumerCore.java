package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.boredweekend.boredweekendservice.controller.providers.ActivityServiceClient;
import it.univaq.disim.sose.boredweekend.boredweekendservice.controller.providers.GeonamesServiceClient;
import it.univaq.disim.sose.boredweekend.boredweekendservice.controller.providers.GoogleMapsGeocodeServiceClient;
import it.univaq.disim.sose.boredweekend.boredweekendservice.controller.providers.WundergroundServiceClient;
import it.univaq.disim.sose.boredweekend.boredweekendservice.controller.providers.EventServiceClient;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Activity;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.CalendarOrder;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Day;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Event;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.ForecastInfo;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Weekend;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.DateUtils;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.ModelUtils;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivitiesPT;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivitiesService;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivityType;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.AddActivityRequest;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.AddEventRequest;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventType;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsPT;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsService;

@Component
@EnableAsync
public class BoredWeekendProsumerCore {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoredWeekendProsumerCore.class);

	@Autowired
	private GoogleMapsGeocodeServiceClient googleMapsGeocodeServiceClient;

	@Autowired
	private WundergroundServiceClient wundergroundServiceClient;

	@Autowired
	private GeonamesServiceClient geonamesServiceClient;
	
	@Autowired
	private ActivityServiceClient activityServiceClient;

	@Autowired
	private EventServiceClient eventServiceClientSpring;


	public Weekend buildWeekend(String city, Date startDate, Date endDate, List<String> preferences, int distance) {
		LOGGER.info("BoredWeekend is handling the getWeekends request in its core");

		LOGGER.info("Retreiving coordinates of city " + city);
		double[] latlon = googleMapsGeocodeServiceClient.geocode(city);

		LOGGER.info("Starting new thread for Wunderground service");
        CompletableFuture<ForecastInfo> forecastinfo = wundergroundServiceClient.getForecastInfo(latlon[0], latlon[1]);

		LOGGER.info("Retreiving nearby cities");
		List<String> nearbyCities = geonamesServiceClient.findNearbyPlaceName(latlon[0], latlon[1], distance);

		if (nearbyCities==null || nearbyCities.isEmpty() || !nearbyCities.contains(city)) {
			nearbyCities.add(city);
		}

		List<String> days = DateUtils.getWeekdaysInInterval(startDate, endDate);

		LOGGER.info("Starting new trheads for activities and events services");
        CompletableFuture<List<Activity>> activity = activityServiceClient.getActivity(nearbyCities, preferences, days);
        CompletableFuture<List<Event>> events1 = eventServiceClientSpring.getEvent(nearbyCities, startDate, endDate);

        LOGGER.info("Waiting for join threads");
        CompletableFuture.allOf(activity,events1,forecastinfo).join();

        LOGGER.info("Threads joined: going ahead");

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

		LOGGER.info("BoredWeekend is handling the insertActivity request in its core");

		double[] latLon = googleMapsGeocodeServiceClient.geocode(activity.getCity());
		activity.setLat(latLon[0]);
		activity.setLon(latLon[1]);

		ActivityType activityType = ModelUtils.transformActivity(activity);

		ActivitiesService service = new ActivitiesService();
		ActivitiesPT port = service.getActivitiesPort();
		AddActivityRequest request = new AddActivityRequest();
		request.setActivity(activityType);
		port.addActivity(request.getActivity());

		LOGGER.info("BoredWeekend completed insertActivity operation");
	}

	public void insertEvent(Event event) {

		LOGGER.info("BoredWeekend is handling the insertEvent request in its core");

		EventType eventType = ModelUtils.transformEvent(event);

		EventsService service = new EventsService();
		EventsPT port = service.getEventsPort();
		AddEventRequest request = new AddEventRequest();
		request.setEvent(eventType);
		port.addEvent(request.getEvent());

		LOGGER.info("BoredWeekend completed insertEvent operation");
	}

	private Weekend composeData(List<Activity> activities, List<Event> events, ForecastInfo forecast, Date startDate, Date endDate) {

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
			if (eventDays.isEmpty()) {
				eventDays.add(event.getStart());
			}
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

}
