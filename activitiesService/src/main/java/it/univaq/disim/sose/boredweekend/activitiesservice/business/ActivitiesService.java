package it.univaq.disim.sose.boredweekend.activitiesservice.business;

import java.util.List;

import it.univaq.disim.sose.boredweekend.activitiesservice.business.model.Activity;

public interface ActivitiesService {

	public void storeActivity(Activity activity);

	public List<Activity> getActivities(List<String> cities, List<String> category, List<String> day);
}
