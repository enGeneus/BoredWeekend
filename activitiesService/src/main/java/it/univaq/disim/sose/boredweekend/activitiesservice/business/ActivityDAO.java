package it.univaq.disim.sose.boredweekend.activitiesservice.business;

import java.util.List;

import it.univaq.disim.sose.boredweekend.activitiesservice.business.model.Activity;

public interface ActivityDAO {

	public void insert(Activity activity);

	public Activity find(int id);

	public List<Activity> find(List<String> city, List<String> category, List<String> day);

}
