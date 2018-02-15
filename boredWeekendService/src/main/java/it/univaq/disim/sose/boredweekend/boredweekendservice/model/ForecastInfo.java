package it.univaq.disim.sose.boredweekend.boredweekendservice.model;

import java.util.HashMap;
import java.util.Map;

public class ForecastInfo {

	private Map<Integer, String> dayForecastMap;

	public ForecastInfo() {
		this.dayForecastMap = new HashMap<>();
	}

	public String getDayForecast(int dayNumber) {
		return this.dayForecastMap.get(dayNumber);
	}

	public void setDayForecast(int dayNumber, String forecast) {
		this.dayForecastMap.put(dayNumber, forecast);
	}

	public boolean isGoodWeather(int dayNumber) {
		String forecast = this.dayForecastMap.get(dayNumber);

		for (GoodWeatherValues c : GoodWeatherValues.values()) {
			if(c.value().equals(forecast)) {
				return true;
			}
		}
		return false;
	}

	private enum GoodWeatherValues {
		CLEAR("Clear"),
		CLOUDY("Cloudy"),
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
