package it.univaq.disim.sose.boredweekend.activitiesservice.business;

import java.util.Date;
import java.util.List;

import it.univaq.disim.sose.boredweekend.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.activitiesservice.WeekDay;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.model.Activity;

public interface ActivitiesService {

	void storeActivity(Activity activity);

	List<Activity> getActivity(String city, List<ActivityCategory> category, List<WeekDay> day, Date daytime);
}
