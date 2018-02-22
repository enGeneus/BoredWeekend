package it.univaq.disim.sose.boredweekend.boredweekendservice.util;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Activity;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Event;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivityType;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.Daytime;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.Position;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.WeekDay;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventCategory;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventType;

public class ModelUtils {

	public static List<Activity> transformActivityType(List<ActivityType> activities) {

		List<Activity> returningList = new ArrayList<>();

		for (ActivityType activityType : activities) {
			returningList.add(transformActivityType(activityType));
		}

		return returningList;
	}

	public static Activity transformActivityType(ActivityType activityType) {

		Activity activity = new Activity();
		activity.setId(activityType.getId());
		activity.setName(activityType.getName());
		activity.setInfo(activityType.getInfo());
		activity.setCity(activityType.getCity());
		activity.setDaytime(activityType.getDaytime().value());
		activity.setPayment(activityType.isPayment());
		activity.setState(activityType.isState());
		activity.setImg(activityType.getImg());
		activity.setLat(activityType.getPosition().getLatitude());
		activity.setLon(activityType.getPosition().getLongitude());

		List<String> categories = new ArrayList<>();

		for (ActivityCategory category : activityType.getCategory()) {
			categories.add(category.value());
		}
		activity.setCategories(categories);

		List<String> days = new ArrayList<>();

		for (WeekDay day : activityType.getDays()) {
			days.add(day.value());
		}
		activity.setDays(days);

		return activity;
	}

	public static ActivityType transformActivity(Activity activity) {
		
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

		return activityType;
	}

	public static List<Event> transformEventType(List<EventType> events) {
		List<Event> returningList = new ArrayList<>();

		for (EventType eventType : events) {
			returningList.add(transformEventType(eventType));
		}
		return returningList;
	}

	public static Event transformEventType(EventType eventType) {

		Event event = new Event();
		event.setId(eventType.getId());
		event.setAddress(eventType.getAddress());
		event.setCity(eventType.getCity());
		event.setCategory(eventType.getCategory().value());
		event.setDescription(eventType.getDescription());
		event.setEnd(eventType.getEnd());
		event.setInfo(eventType.getInfo());
		event.setImg(eventType.getImg());
		event.setLocationName(eventType.getLocationName());
		event.setName(eventType.getName());
		event.setPayment(eventType.isPayment());
		event.setStart(eventType.getStart());

		return event;
	}

	public static EventType transformEvent(Event event) {
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

		return eventType;
	}
}
