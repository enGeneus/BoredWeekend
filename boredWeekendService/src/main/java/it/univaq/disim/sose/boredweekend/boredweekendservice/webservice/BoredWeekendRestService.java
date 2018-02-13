package it.univaq.disim.sose.boredweekend.boredweekendservice.webservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Weekend;

@RestController
public class BoredWeekendRestService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoredWeekendRestService.class);

	@RequestMapping(value = "/getWeekends", method = RequestMethod.GET, headers="Accept=application/json")
	public Weekend getWeekends() {
		LOGGER.info("Received getWeekends request");
		return new Weekend();
	}

}
