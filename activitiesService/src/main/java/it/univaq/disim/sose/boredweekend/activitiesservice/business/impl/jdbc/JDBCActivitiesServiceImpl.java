package it.univaq.disim.sose.boredweekend.activitiesservice.business.impl.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.boredweekend.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.activitiesservice.Daytime;
import it.univaq.disim.sose.boredweekend.activitiesservice.WeekDay;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.ActivitiesService;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.ActivityDAO;
import it.univaq.disim.sose.boredweekend.activitiesservice.business.model.Activity;


@Service
public class JDBCActivitiesServiceImpl implements ActivitiesService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(JDBCActivitiesServiceImpl.class);
	
	@Autowired
	private ActivityDAO activityDAO;

	@Override
	public void storeActivity(Activity activity) {
		// TODO Auto-generated method stub	
	}

	@Override
	public List<Activity> getActivity(String city, List<ActivityCategory> category, List<WeekDay> day, Daytime daytime) {

		return activityDAO.find(city, category, day, daytime);
	}

}
