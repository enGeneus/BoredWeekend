package it.univaq.disim.sose.boredweekend.boredweekendservice.webservice;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.boredweekend.boredweekendservice.controller.BoredWeekendProsumerCore;
import it.univaq.disim.sose.boredweekend.boredweekendservice.controller.GoogleMapsGeocodeServiceClient;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Activity;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Event;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Weekend;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.DateUtils;

@Service
public class BoredWeekendRestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoredWeekendRestService.class);

	@Autowired
	BoredWeekendProsumerCore prosumerCore;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getWeekends")
	public Weekend getWeekends(@QueryParam("where") String city, @QueryParam("start") String start,
			@QueryParam("end") String end, @QueryParam("preferences") String preferences, @QueryParam("traveldistance") int distance) {

		LOGGER.info("Received getWeekends request");

		Date startDate = DateUtils.getDateFromString(start, "yyy-MM-dd");
		Date endDate = DateUtils.getDateFromString(end, "yyy-MM-dd");

		List<String> preferencesList = DateUtils.extractListFromConcatenatedValues(preferences);

		Weekend weekend = prosumerCore.buildWeekend(city, startDate, endDate, preferencesList, distance);

		ObjectWriter ow = new ObjectMapper().writer();

		try {
			LOGGER.debug(ow.writeValueAsString(weekend));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return weekend;
	}
	
	@POST
	@Path("/insertActivity")
	@Consumes(MediaType.APPLICATION_JSON)
	public void insert(Activity activity) {
		
		LOGGER.info("Received insertActivity request");
		
		double[] latLon = GoogleMapsGeocodeServiceClient.callService(activity.getCity());
		activity.setLat(latLon[0]);
		activity.setLon(latLon[1]);

		prosumerCore.insertActivity(activity);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insertEvent")
	public void insert(Event event) {

		LOGGER.info("Received insertEvent request");
		prosumerCore.insertEvent(event);
	}
	
}
