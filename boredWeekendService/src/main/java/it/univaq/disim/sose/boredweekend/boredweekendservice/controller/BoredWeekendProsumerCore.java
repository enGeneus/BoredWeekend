package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Weekend;

public class BoredWeekendProsumerCore {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoredWeekendProsumerCore.class);

	public Weekend buildWeekend() {
		LOGGER.info("BoredWeekend is handling the getWeekends request in its core");
		return new Weekend();
	}
}
