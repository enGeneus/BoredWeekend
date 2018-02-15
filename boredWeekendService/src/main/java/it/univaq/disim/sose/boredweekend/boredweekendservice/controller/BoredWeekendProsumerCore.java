package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.ForecastInfo;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Weekend;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.ProviderServiceUtils;

public class BoredWeekendProsumerCore {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoredWeekendProsumerCore.class);

	public Weekend buildWeekend() {
		LOGGER.info("BoredWeekend is handling the getWeekends request in its core");

		ForecastInfo forecast = callForecastService(42.353901, 13.403728);

		return new Weekend();
	}

	private ForecastInfo callForecastService(double lat, double lon) {
		try {
			LOGGER.info("Calling weather service");
			String forecastStringUrl = ProviderServiceUtils.buildWundergroundRequestURL(lat, lon);

			URL forecastUrl = new URL(forecastStringUrl);
			HttpURLConnection c = (HttpURLConnection) forecastUrl.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.connect();
			int status = c.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();

				if (c != null) {
					c.disconnect();
				}

				ForecastInfo forecast = new ForecastInfo();

				JSONObject response = new JSONObject(sb.toString());
				JSONArray forecastDays = response.getJSONObject("forecast").getJSONObject("simpleforecast").getJSONArray("forecastday");

				for (int i=0; i<forecastDays.length(); i++) {
					JSONObject forecastDay = forecastDays.getJSONObject(i);
					int dayNumber = forecastDay.getJSONObject("date").getInt("day");
					String dayForecast = forecastDay.getString("conditions");
					forecast.setDayForecast(dayNumber, dayForecast);
				}

				return forecast;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
