package it.univaq.disim.sose.boredweekend.boredweekendservice.controller.providers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.boredweekend.boredweekendservice.util.ProviderServiceUtils;

@Service
public class GeonamesServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeonamesServiceClient.class);

	public List<String> findNearbyPlaceName(double lat, double lon, int radius) {
		List<String> nearbycities = new ArrayList<>();

		try {
			LOGGER.info("Calling geonames service");
			String geoNamesStringUrl = ProviderServiceUtils.buildGeoNamesURL(lat, lon, radius);

			LOGGER.info("URL: " + geoNamesStringUrl);

			URL geoNamesUrl = new URL(geoNamesStringUrl);
			HttpURLConnection c = (HttpURLConnection) geoNamesUrl.openConnection();
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

				JSONArray geonames = response.getJSONArray("geonames");
				for (int i = 0; i < geonames.length(); i++) {
					JSONObject name = geonames.getJSONObject(i);
					nearbycities.add(name.getString("name").replace("’", "'"));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return nearbycities;
	}

}
