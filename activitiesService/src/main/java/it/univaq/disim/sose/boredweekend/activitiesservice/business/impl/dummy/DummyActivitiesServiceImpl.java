package it.univaq.disim.sose.boredweekend.activitiesservice.business.impl.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.univaq.disim.sose.boredweekend.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.activitiesservice.WeekDay;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.ActivitiesService;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.model.Activity;

public class DummyActivitiesServiceImpl implements ActivitiesService{

	@Override
	public void storeActivity(Activity activity) {
		
	}

	@Override
	public List<Activity> getActivity(String city, List<ActivityCategory> category, List<WeekDay> day, Date daytime) {
		return new ArrayList<>();
	}

}
