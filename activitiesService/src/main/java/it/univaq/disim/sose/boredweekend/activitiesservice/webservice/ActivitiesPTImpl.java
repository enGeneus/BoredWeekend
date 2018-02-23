package it.univaq.disim.sose.boredweekend.activitiesservice.webservice;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.boredweekend.activitiesservice.ActivitiesPT;
import it.univaq.disim.sose.boredweekend.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.activitiesservice.ActivityType;
import it.univaq.disim.sose.boredweekend.activitiesservice.CityActivitiesRequest;
import it.univaq.disim.sose.boredweekend.activitiesservice.CityActivitiesResponse;
import it.univaq.disim.sose.boredweekend.activitiesservice.Daytime;
import it.univaq.disim.sose.boredweekend.activitiesservice.Position;
import it.univaq.disim.sose.boredweekend.activitiesservice.WeekDay;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.ActivitiesService;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.model.Activity;

@Component(value = "ActivitiesPTImpl")
public class ActivitiesPTImpl implements ActivitiesPT{

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesPTImpl.class);

	@Autowired
	private ActivitiesService service;

	@Override
	public void addActivity(ActivityType inputActivity) {

		LOGGER.info("Called addActivity on Activities Service");

		Activity addingActivity = new Activity();

		for (ActivityCategory category : inputActivity.getCategory()) {
			addingActivity.getCategories().add(category.value());
		}

		for (WeekDay day : inputActivity.getDays()) {
			addingActivity.getDays().add(day.value());
		}

		addingActivity.setCity(inputActivity.getCity());
		addingActivity.setDaytime(inputActivity.getDaytime().value());
		addingActivity.setImg(inputActivity.getImg());
		addingActivity.setInfo(inputActivity.getInfo());
		addingActivity.setLat(inputActivity.getPosition().getLatitude());
		addingActivity.setLon(inputActivity.getPosition().getLongitude());
		addingActivity.setName(inputActivity.getName());
		addingActivity.setPayment(inputActivity.isPayment());
		addingActivity.setState(inputActivity.isState());

		service.storeActivity(addingActivity);

		LOGGER.info("Activity Stored");
	}

	@Override
	public CityActivitiesResponse getCityActivities(CityActivitiesRequest parameters) {

		LOGGER.info("Called getCityActivities on Activities Service");

		List<String> categories = new ArrayList<>();
		List<String> days = new ArrayList<>();

		for (ActivityCategory category : parameters.getCategory()) {
			categories.add(category.value());
		}

		for (WeekDay day : parameters.getDay()) {
			days.add(day.value());
		}


		List<Activity> activities = service.getActivities(parameters.getCity(), categories, days);

		CityActivitiesResponse response = new CityActivitiesResponse();

		for (Activity activity : activities) {
			ActivityType returningActivity = new ActivityType();
			
			returningActivity.setId(activity.getId());
			returningActivity.setName(activity.getName());
			returningActivity.setInfo(activity.getInfo());
			returningActivity.setCity(activity.getCity());
			returningActivity.setPayment(activity.isPayment());
			returningActivity.setState(activity.isAvailable());
			returningActivity.setImg(activity.getImg());
			returningActivity.setDaytime(Daytime.fromValue(activity.getDaytime()));
			
			Position position = new Position();
			position.setLatitude(activity.getLat());
			position.setLongitude(activity.getLon());
			returningActivity.setPosition(position);

			for (String category : activity.getCategories()) {
				returningActivity.getCategory().add(ActivityCategory.fromValue(category));
			}

			for (String weekDay : activity.getDays()) {
				returningActivity.getDays().add(WeekDay.fromValue(weekDay));
			}

			response.getActivities().add(returningActivity);
			
		}

		LOGGER.info("Returning activities");

		return response;
		
	}
	

}
