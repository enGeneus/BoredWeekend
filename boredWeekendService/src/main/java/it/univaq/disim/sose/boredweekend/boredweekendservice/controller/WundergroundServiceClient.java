package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.ForecastInfo;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.ProviderServiceUtils;

public class WundergroundServiceClient extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(WundergroundServiceClient.class);

	private ForecastInfo forecast;

	private double lat;
	private double lon;

	public WundergroundServiceClient(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public ForecastInfo getForecast() {
		return forecast;
	}

	@Override
	public void run() {
		LOGGER.info("Started new tread for calling weather service");
		this.forecast = callForecastService(this.lat, this.lon);
	}

	private ForecastInfo callForecastService(double lat, double lon) {
		try {
			LOGGER.info("Calling weather service");
			String forecastStringUrl = ProviderServiceUtils.buildWundergroundRequestURL(lat, lon);

			LOGGER.info("URL: " + forecastStringUrl);

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

				LOGGER.debug(response.toString());

				JSONArray forecastDays = response.getJSONObject("forecast").getJSONObject("simpleforecast").getJSONArray("forecastday");

				for (int i=0; i<forecastDays.length(); i++) {
					JSONObject forecastDay = forecastDays.getJSONObject(i);
					Date day = new Date(forecastDay.getJSONObject("date").getLong("epoch") * 1000);
					String dayForecast = forecastDay.getString("conditions");
					forecast.setDayForecast(day, dayForecast);
				}

				return forecast;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
