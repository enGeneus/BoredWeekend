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
		//Complete this method
		Activity attivita = new Activity();
		
		attivita.setCategories(activity.getCategory());
		attivita.setDays(activity.getDays());
		attivita.setCity(activity.getCity());
		attivita.setDaytime(activity.getDaytime());
		attivita.setImg(activity.getImg());
		attivita.setInfo(activity.getInfo());
		attivita.setLat(activity.getPosition().getLatitude());
		attivita.setLon(activity.getPosition().getLongitude());
		attivita.setName(activity.getName());
		attivita.setPayment(activity.isPayment());
		attivita.setState(activity.isState());
				
		service.storeActivity(attivita);
	}

	@Override
	public CityActivitiesResponse getCityActivities(CityActivitiesRequest parameters) {
		List<Activity> activities = service.getActivity(parameters.getCity(), parameters.getCategory(), parameters.getDay(), parameters.getDaytime());

		LOGGER.info(activities.get(0).getName());
		
		return new CityActivitiesResponse();
	}
	

}
