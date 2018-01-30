package it.univaq.disim.sose.boredweekend.activitiesservice.business;

import java.util.List;

import it.univaq.disim.sose.boredweekend.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.activitiesservice.Daytime;
import it.univaq.disim.sose.boredweekend.activitiesservice.WeekDay;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.model.Activity;

public interface ActivityDAO {

	public void insert(Activity activity);

	public Activity find(int id);

	public List<Activity> find(String city, List<ActivityCategory> category, List<WeekDay> day, Daytime daytime);

}
