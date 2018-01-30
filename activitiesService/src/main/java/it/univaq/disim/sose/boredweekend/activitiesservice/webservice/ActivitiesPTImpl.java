package it.univaq.disim.sose.boredweekend.activitiesservice.webservice;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.boredweekend.activitiesservice.ActivitiesPT;
import it.univaq.disim.sose.boredweekend.activitiesservice.ActivityType;
import it.univaq.disim.sose.boredweekend.activitiesservice.CityActivitiesRequest;
import it.univaq.disim.sose.boredweekend.activitiesservice.CityActivitiesResponse;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.ActivitiesService;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.model.Activity;

@Component(value = "ActivitiesPTImpl")
public class ActivitiesPTImpl implements ActivitiesPT{

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesPTImpl.class);

	@Autowired
	private ActivitiesService service;

	@Override
	public void addActivity(ActivityType activity) {
		// Complete this method
		service.storeActivity(new Activity());
		
	}

	@Override
	public CityActivitiesResponse getCityActivities(CityActivitiesRequest parameters) {
		List<Activity> activities = service.getActivity(parameters.getCity(), parameters.getCategory(), parameters.getDay(), parameters.getDaytime());

		LOGGER.info(activities.get(0).getName());
		
		return new CityActivitiesResponse();
	}
	

}
