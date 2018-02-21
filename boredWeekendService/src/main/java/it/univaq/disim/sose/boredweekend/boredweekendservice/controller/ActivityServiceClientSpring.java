package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Activity;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivitiesPT;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivitiesService;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivityType;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.CityActivitiesRequest;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.CityActivitiesResponse;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.WeekDay;

@Service
public class ActivityServiceClientSpring{
	
	@Async
	public CompletableFuture<List<Activity>> getActivity(List<String> city, List<String> category, List<String> days){
		
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
		
		setActivity(response.getActivities());
		
		return CompletableFuture.completedFuture(setActivity(response.getActivities()));
	}

	public List<Activity> setActivity(List<ActivityType> response) {
		
		List<Activity> activitylist = new ArrayList<>();
		
		for (ActivityType activity : response) {
			
			Activity returningActivity = new Activity();
			
			returningActivity.setId(activity.getId());
			returningActivity.setName(activity.getName());
			returningActivity.setInfo(activity.getInfo());
			returningActivity.setCity(activity.getCity());
			returningActivity.setPayment(activity.isPayment());
			returningActivity.setState(activity.isState());
			returningActivity.setImg(activity.getImg());
			returningActivity.setLat(activity.getPosition().getLatitude());
			returningActivity.setLon(activity.getPosition().getLongitude());

			List<String> categories = new ArrayList<>();

			for (ActivityCategory category : activity.getCategory()) {
				categories.add(category.value());
			}
			returningActivity.setCategories(categories);
			
			List<String> days = new ArrayList<>();

			for (WeekDay day : activity.getDays()) {
				days.add(day.value());
			}
			returningActivity.setDays(days);			
			
			activitylist.add(returningActivity);
		}
		return activitylist;
	}
}