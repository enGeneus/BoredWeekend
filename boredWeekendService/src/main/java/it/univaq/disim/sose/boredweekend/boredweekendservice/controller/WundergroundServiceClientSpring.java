package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.ForecastInfo;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.ProviderServiceUtils;

@Service
public class WundergroundServiceClientSpring {

	private static final Logger LOGGER = LoggerFactory.getLogger(WundergroundServiceClientSpring.class);
	
	@Async
	public  CompletableFuture<ForecastInfo> getForecastInfo(double lat, double lon){
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

				LOGGER.info(response.toString());

				JSONArray forecastDays = response.getJSONObject("forecast").getJSONObject("simpleforecast").getJSONArray("forecastday");

				for (int i=0; i<forecastDays.length(); i++) {
					JSONObject forecastDay = forecastDays.getJSONObject(i);
					Date day = new Date(forecastDay.getJSONObject("date").getLong("epoch") * 1000);
					String dayForecast = forecastDay.getString("conditions");
					forecast.setDayForecast(day, dayForecast);
				}

				Thread.sleep(10000);
				LOGGER.info("Returning weather service response");
				return CompletableFuture.completedFuture(forecast);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
