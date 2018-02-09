package it.univaq.disim.sose.boredweekend.activitiesservice.business;

import java.util.List;

import it.univaq.disim.sose.boredweekend.activitiesservice.business.model.Activity;

public interface ActivitiesService {

	void storeActivity(Activity activity);

	List<Activity> getActivity(String city, List<String> category, List<String> day, String daytime);
}
