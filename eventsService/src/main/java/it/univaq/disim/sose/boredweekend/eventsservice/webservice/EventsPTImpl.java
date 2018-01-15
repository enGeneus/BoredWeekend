package it.univaq.disim.sose.boredweekend.eventsservice.webservice;

import org.springframework.stereotype.Component;

import it.univaq.disim.sose.boredweekend.eventsservice.CityEventsRequest;
import it.univaq.disim.sose.boredweekend.eventsservice.CityEventsResponse;
import it.univaq.disim.sose.boredweekend.eventsservice.EventType;
import it.univaq.disim.sose.boredweekend.eventsservice.EventsPT;

@Component(value = "EventsPTImpl")
public class EventsPTImpl implements EventsPT {

	@Override
	public CityEventsResponse getCityEvents(CityEventsRequest parameters) {
		// Implement this method
		return null;
	}

	@Override
	public void addEvent(EventType event) {
		// Implement this method
		
	}

}
