package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.univaq.disim.sose.boredweekend.boredweekendservice.util.ProviderServiceUtils;

public class GoogleMapsGeocodeServiceClient{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleMapsGeocodeServiceClient.class);
	
	public static double[] callService(String cityName) {
		double[] latlon = new double[2];

		try {
			LOGGER.info("Calling maps geocode service");
			String mapsGeocodeStringUrl = ProviderServiceUtils.buildMapsGeocodeURL(cityName);

			LOGGER.info("URL: " + mapsGeocodeStringUrl);

			URL mapsGeocodeUrl = new URL(mapsGeocodeStringUrl);
			HttpURLConnection c = (HttpURLConnection) mapsGeocodeUrl.openConnection();
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

				JSONObject response = new JSONObject(sb.toString());

				LOGGER.debug(response.toString());

				JSONObject location = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
						.getJSONObject("location");
				latlon[0] = location.getDouble("lat");
				latlon[1] = location.getDouble("lng");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return latlon;
	}
	
}
