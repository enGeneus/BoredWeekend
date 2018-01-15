package it.univaq.disim.sose.boredweekend.activitiesservice.webservice;

import org.springframework.stereotype.Component;

import it.univaq.disim.sose.boredweekend.activitiesservice.ActivitiesPT;
import it.univaq.disim.sose.boredweekend.activitiesservice.ActivityType;
import it.univaq.disim.sose.boredweekend.activitiesservice.CityActivitiesRequest;
import it.univaq.disim.sose.boredweekend.activitiesservice.CityActivitiesResponse;

@Component(value = "ActivitiesPTImpl")
public class ActivitiesPTImpl implements ActivitiesPT{

	@Override
	public void addActivity(ActivityType activity) {
		// Implement this method
		
	}

	@Override
	public CityActivitiesResponse getCityActivities(CityActivitiesRequest parameters) {
		// Implement this method
		return null;
	}
	

}
