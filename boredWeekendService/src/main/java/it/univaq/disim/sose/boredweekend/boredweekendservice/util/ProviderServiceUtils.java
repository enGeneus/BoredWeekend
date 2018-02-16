package it.univaq.disim.sose.boredweekend.boredweekendservice.util;

public class ProviderServiceUtils {

	private static final String WUNDERGROUND_URL = "http://api.wunderground.com/api/";
	private static final String WUNDERGROUND_APIKEY = "f5d7b7ece3b0e77d";

	private static final String GEONAMES_BASEURL = "http://api.geonames.org/findNearbyPlaceNameJSON";
	private static final String GEONAMES_USERNAME = "engeneus";

	private static final String MAPS_GEOCODE_BASEURL = "https://maps.googleapis.com/maps/api/geocode/";
	private static final String MAPS_APIKEY = "AIzaSyAYKjpQqdbofwtrjgmesbEYqXHlwhqMKI8";

	public static String buildWundergroundRequestURL(double lat, double lon) {
		// EXAMPLE: http://api.wunderground.com/api/f5d7b7ece3b0e77d/forecast/q/42.0,13.0.json
		String url = WUNDERGROUND_URL + WUNDERGROUND_APIKEY + "/forecast/q/" + lat +","+ lon + ".json"; 
		return url;
	}

	public static String buildMapsGeocodeURL(String cityName) {
		// EXAMPLE: https://maps.googleapis.com/maps/api/geocode/json?address=L'Aquila&key=AIzaSyAYKjpQqdbofwtrjgmesbEYqXHlwhqMKI8
		String url = MAPS_GEOCODE_BASEURL + "json?address=" + cityName + "&key=" + MAPS_APIKEY;
		return url;
	}

	public static String buildGeoNamesURL(double lat, double lon, int radius) {
		// EXAMPLE: http://api.geonames.org/findNearbyPlaceNameJSON?lat=42.3498479&lng=13.3995091&style=short&cities=1000&radius=40&maxRows=30&username=engeneus
		String url = GEONAMES_BASEURL + "?lat=" + lat + "&lng=" + lon + "&radius=" + radius + "&username=" + GEONAMES_USERNAME;
		return url;
	}
}
