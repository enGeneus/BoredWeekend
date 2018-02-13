package it.univaq.disim.sose.boredweekend.boredweekendservice.model;

import java.util.ArrayList;
import java.util.List;

public class Day {

	private String weatherForecast;
	private List<Event> events;
	private List<Activity> activities;

	public Day() {
		this.events = new ArrayList<>();
		this.activities = new ArrayList<>();
	}

	public String getWeatherForecast() {
		return weatherForecast;
	}

	public void setWeatherForecast(String weatherForecast) {
		this.weatherForecast = weatherForecast;
	}

	public List<Event> getEvents() {
		return events;
	}

	public List<Activity> getActivities() {
		return activities;
	}

}
