package it.univaq.disim.sose.boredweekend.boredweekendservice.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ForecastInfo {

	private Map<String, String> dayForecastMap;

	private static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public ForecastInfo() {
		this.dayForecastMap = new HashMap<>();
	}

	public String getDayForecast(Date day) {
		return this.dayForecastMap.get(DATEFORMAT.format(day));
	}

	public void setDayForecast(Date day, String forecast) {
		String dayString = DATEFORMAT.format(day);
		this.dayForecastMap.put(dayString, forecast);
	}

	public Map<String, String> getForecastMap() {
		return this.dayForecastMap;
	}
}
