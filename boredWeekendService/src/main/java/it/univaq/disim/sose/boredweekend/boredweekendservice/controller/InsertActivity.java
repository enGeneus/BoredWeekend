package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;


import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Activity;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivitiesPT;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivitiesService;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.ActivityType;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.AddActivityRequest;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.Daytime;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.Position;
import it.univaq.disim.sose.boredweekend.providers.activitiesservice.WeekDay;

public class InsertActivity {
	
	private ActivityType activityType = new ActivityType();
	
	public InsertActivity(Activity activity) {
	
		this.activityType.setName(activity.getName());
		this.activityType.setInfo(activity.getInfo());
		this.activityType.setCity(activity.getCity());
		this.activityType.setPayment(activity.isPayment());
		this.activityType.setState(true);
		this.activityType.setImg(activity.getImg());
		this.activityType.setDaytime(Daytime.fromValue(activity.getDaytime()));
		
		Position position = new Position();
		position.setLatitude(activity.getLat());
		position.setLongitude(activity.getLon());
		this.activityType.setPosition(position);

		for (String category : activity.getCategories()) {
			this.activityType.getCategory().add(ActivityCategory.fromValue(category));
		}

		for (String weekDay : activity.getDays()) {
			this.activityType.getDays().add(WeekDay.fromValue(weekDay));
		}
		
	}

	public void addActivity() {
		
		ActivitiesService service = new ActivitiesService();
		ActivitiesPT port = service.getActivitiesPort();
		AddActivityRequest request = new AddActivityRequest();
		request.setActivity(this.activityType);
		port.addActivity(request.getActivity());
		
	}
}
