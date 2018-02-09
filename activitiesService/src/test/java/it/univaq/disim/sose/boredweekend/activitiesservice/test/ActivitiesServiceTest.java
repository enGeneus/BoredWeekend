package it.univaq.disim.sose.boredweekend.activitiesservice.test;

import org.junit.Test;

import it.univaq.disim.sose.boredweekend.activitiesservice.ActivitiesPT;
import it.univaq.disim.sose.boredweekend.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.activitiesservice.ActivityType;
import it.univaq.disim.sose.boredweekend.activitiesservice.Daytime;
import it.univaq.disim.sose.boredweekend.activitiesservice.Position;
import it.univaq.disim.sose.boredweekend.activitiesservice.WeekDay;
import it.univaq.disim.sose.boredweekend.activitiesservice.webservice.ActivitiesPTImpl;

public class ActivitiesServiceTest {

	@Test
	public void webServiceAddActivityTest() {
		ActivitiesPT activitiesPT = new ActivitiesPTImpl();

		ActivityType activity = new ActivityType();
		activity.setName("Test activity");
		activity.setCity("L'Aquila");
		activity.setInfo("Activity updated by test");

		Position position = new Position();
		position.setLatitude(42.0);
		position.setLongitude(13.3);
		activity.setDaytime(Daytime.FULL_DAY);
		activity.getDays().add(WeekDay.SATURDAY);
		activity.getDays().add(WeekDay.SUNDAY);
		activity.getCategory().add(ActivityCategory.OUTDOOR);
		activity.getCategory().add(ActivityCategory.SPORT);
		activity.setPayment(false);
		activity.setState(true);

		activitiesPT.addActivity(activity);
	}
}
