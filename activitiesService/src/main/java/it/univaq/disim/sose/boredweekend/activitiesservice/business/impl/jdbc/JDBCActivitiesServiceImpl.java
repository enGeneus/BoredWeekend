package it.univaq.disim.sose.boredweekend.activitiesservice.business.impl.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.boredweekend.activitiesservice.business.ActivitiesService;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.ActivityDAO;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.model.Activity;


@Service
public class JDBCActivitiesServiceImpl implements ActivitiesService {

	@Autowired
	private ActivityDAO activityDAO;

	@Override
	public void storeActivity(Activity activity) {
		activityDAO.insert(activity);
	}

	@Override
	public List<Activity> getActivity(List<String> city, List<String> category, List<String> day, String daytime) {

		return activityDAO.find(city, category, day, daytime);
	}

}
