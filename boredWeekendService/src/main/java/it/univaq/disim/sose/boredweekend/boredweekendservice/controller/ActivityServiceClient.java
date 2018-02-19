package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Activity;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivitiesPT;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivitiesService;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivityType;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.CityActivitiesRequest;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.CityActivitiesResponse;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.Daytime;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.WeekDay;

class ActivityServiceClient extends Thread {
	
	private List<String> city;
	private List<String> category;
	private List<String> day;
	private String daytime;
	private List<Activity> activity = new ArrayList<>();
	
	public ActivityServiceClient(List<String> city, List<String> category, List<String> day, String daytime) {
		this.city = city;
		this.category = category;
		this.day = day;
		this.daytime = daytime;
	}
	
	public List<Activity> getActivity() {
		return this.activity;
	}
	
	public void setActivity(List<ActivityType> response) {
		
		for (ActivityType activity : response) {
			
			Activity returningActivity = new Activity();
			
			returningActivity.setName(activity.getName());
			returningActivity.setInfo(activity.getInfo());
			returningActivity.setCity(activity.getCity());
			returningActivity.setPayment(activity.isPayment());
			returningActivity.setState(activity.isState());
			returningActivity.setImg(activity.getImg());
			returningActivity.setDaytime(activity.getDaytime().value());
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
			
			this.activity.add(returningActivity);

		}
	}
	
	public void run() {
		ActivitiesService service = new ActivitiesService();
		ActivitiesPT port = service.getActivitiesPort();
		CityActivitiesRequest request = new CityActivitiesRequest();
		request.setDaytime(Daytime.fromValue(this.daytime));

		for (String a : this.city) {
			request.getCity().add(a);
		}
		
		for(String cat : this.category) {
			request.getCategory().add(ActivityCategory.fromValue(cat));
		}
		for(String day : this.day) {
			request.getDay().add(WeekDay.fromValue(day));
		}
		CityActivitiesResponse response = port.getCityActivities(request);		
		setActivity(response.getActivities());

	}
}