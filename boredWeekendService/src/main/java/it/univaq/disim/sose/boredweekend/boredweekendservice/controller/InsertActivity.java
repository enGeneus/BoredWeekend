package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.util.List;

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
	
	public InsertActivity(String name, String city, double lat, double lon, String daytime, boolean state, String info, boolean payment, String img, List<String> categories, List<String> days) {
	
		this.activityType.setName(name);
		this.activityType.setInfo(info);
		this.activityType.setCity(city);
		this.activityType.setPayment(payment);
		this.activityType.setState(state);
		this.activityType.setImg(img);
		this.activityType.setDaytime(Daytime.fromValue(daytime));
		
		Position position = new Position();
		position.setLatitude(lat);
		position.setLongitude(lon);
		this.activityType.setPosition(position);

		for (String category : categories) {
			this.activityType.getCategory().add(ActivityCategory.fromValue(category));
		}

		for (String weekDay : days) {
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
