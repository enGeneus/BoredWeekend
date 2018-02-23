package it.univaq.disim.sose.boredweekend.boredweekendservice.controller.providers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Activity;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.ModelUtils;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivitiesPT;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivitiesService;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.CityActivitiesRequest;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.CityActivitiesResponse;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.WeekDay;

@Service
public class ActivityServiceClient{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityServiceClient.class);
	
	@Async
	public CompletableFuture<List<Activity>> getActivity(List<String> city, List<String> category, List<String> days){

		LOGGER.info("Calling activity SOAP service");

		ActivitiesService service = new ActivitiesService();
		ActivitiesPT port = service.getActivitiesPort();
		CityActivitiesRequest request = new CityActivitiesRequest();

		for (String a : city) {
			request.getCity().add(a);
		}
		
		for(String cat : category) {
			request.getCategory().add(ActivityCategory.fromValue(cat));
		}
		for(String day : days) {
			request.getDay().add(WeekDay.fromValue(day));
		}

		CityActivitiesResponse response = port.getCityActivities(request);		
		
		List<Activity> activities = ModelUtils.transformActivityType(response.getActivities());

		LOGGER.info("Returning activity service response");

		return CompletableFuture.completedFuture(activities);
	}

}