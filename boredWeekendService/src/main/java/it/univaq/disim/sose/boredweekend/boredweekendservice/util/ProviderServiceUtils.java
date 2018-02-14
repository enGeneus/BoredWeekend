package it.univaq.disim.sose.boredweekend.boredweekendservice.util;

import org.springframework.beans.factory.annotation.Value;

public class ProviderServiceUtils {

	@Value("#{cfgproperties.wunderground_url}")
	private static String wundergroundUrl;

	@Value("#{cfgproperties.wunderground_apikey}")
	private static String wundergroundApiKey;

	private static String getWundergroundUrl() {
		return wundergroundApiKey;
	}

	private static String getWundergroundApiKey() {
		return wundergroundApiKey;
	}
}
