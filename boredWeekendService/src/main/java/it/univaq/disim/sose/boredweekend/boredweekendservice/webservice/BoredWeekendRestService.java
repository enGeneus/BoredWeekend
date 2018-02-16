package it.univaq.disim.sose.boredweekend.boredweekendservice.webservice;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.disim.sose.boredweekend.boredweekendservice.controller.BoredWeekendProsumerCore;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Weekend;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.DataUtils;

@RestController
public class BoredWeekendRestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoredWeekendRestService.class);

	@RequestMapping(value = "/getWeekends", method = RequestMethod.GET, headers = "Accept=application/json")
	public Weekend getWeekends(@RequestParam("where") String city, @RequestParam("start") String start,
			@RequestParam("end") String end, @RequestParam("preferences") String preferences,
			@RequestParam("daytime") String daytime, @RequestParam("traveldistance") int distance) {

		LOGGER.info("Received getWeekends request");

		Date startDate = DataUtils.getDateFromString(start, "yyy-MM-dd");
		Date endDate = DataUtils.getDateFromString(end, "yyy-MM-dd");

		List<String> preferencesList = DataUtils.extractListFromConcatenatedValues(preferences);

		BoredWeekendProsumerCore prosumerCore = new BoredWeekendProsumerCore();
		return prosumerCore.buildWeekend(city, startDate, endDate, preferencesList, daytime, distance);
	}

}
