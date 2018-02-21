package it.univaq.disim.sose.boredweekend.boredweekendservice.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Day {

	private String weatherForecast;
	private List<Event> events;
	private List<Activity> activities;

	private Date fullDate;

	private String dayNumber;
	private String day;
	private String monthNumber;
	private String month;

	public Day(Date date) {
		this.events = new ArrayList<>();
		this.activities = new ArrayList<>();

		this.fullDate = date;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-EEEE-MM-MMMMM", Locale.ENGLISH);
		String stringDate = dateFormat.format(date);
		String[] elements = stringDate.split("-");
		this.dayNumber = elements[0];
		this.day = elements[1];
		this.monthNumber = elements[2];
		this.month = elements[3];
	}

	public boolean isGoodWeather() {
		for (GoodWeatherValues c : GoodWeatherValues.values()) {
			if(c.value().equals(this.weatherForecast) || this.weatherForecast==null) {
				return true;
			}
		}
		return false;
	}

	public Date getFullDate() {
		return fullDate;
	}

	public void setFullDate(Date fullDate) {
		this.fullDate = fullDate;
	}

	public String getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(String dayNumber) {
		this.dayNumber = dayNumber;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonthNumber() {
		return monthNumber;
	}

	public void setMonthNumber(String monthNumber) {
		this.monthNumber = monthNumber;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
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

	private enum GoodWeatherValues {
		CLEAR("Clear"),
		CLOUDY("Cloudy"),
		OVERCAST("Overcast"),
		HAZE("Haze"),
		MOSTLY_CLOUDY("Mostly Cloudy"),
		MOSTLY_SUNNY("Mostly Sunny"),
		PARTLY_CLOUDY("Partly Cloudy"),
		PARTLY_SUNNY("Partly Sunny"),
		SUNNY("Sunny"),
		SCATTERED_CLOUDS("Scattered Clouds");

		private final String value;

		GoodWeatherValues(String v) {
			value = v;
		}

	    public String value() {
	        return value;
	    }
	}

}
