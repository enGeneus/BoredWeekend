package it.univaq.disim.sose.boredweekend.boredweekendservice.util;

public class ProviderServiceUtils {

	private static final String WUNDERGROUND_URL = "http://api.wunderground.com/api/";

	private static final String WUNDERGROUND_APIKEY = "f5d7b7ece3b0e77d";

	public static String buildWundergroundRequestURL(double lat, double lon) {
		String url = WUNDERGROUND_URL + WUNDERGROUND_APIKEY + "/forecast/q/" + lat +","+ lon; 
		return url;
	}
}
