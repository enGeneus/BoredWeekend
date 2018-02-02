package it.univaq.disim.sose.boredweekend.eventsservice.webservice;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.events.EventException;

import it.univaq.disim.sose.boredweekend.eventsservice.CityEventsRequest;
import it.univaq.disim.sose.boredweekend.eventsservice.CityEventsResponse;
import it.univaq.disim.sose.boredweekend.eventsservice.EventType;
import it.univaq.disim.sose.boredweekend.eventsservice.EventsPT;
import it.univaq.disim.sose.boredweekend.eventsservice.business.EventsService;
import it.univaq.disim.sose.boredweekend.eventsservice.business.model.Events;

@Component(value = "EventsPTImpl")
public class EventsPTImpl implements EventsPT {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventsPTImpl.class);

	
	@Autowired
	private EventsService service;

	@Override
	public CityEventsResponse getCityEvents(CityEventsRequest parameters) {
		// Implement this method
		List<Events> event = service.getEvents(parameters.getCity(), parameters.getStart(), parameters.getEnd());
		
		LOGGER.info(event.get(0).getName());
		
		return new CityEventsResponse();
	}

	@Override
	public void addEvent(EventType event) {
		
		Events addevent = new Events();
		
		addevent.setAddress(event.getAddress());
		addevent.setCity(event.getCity());
		addevent.setCategory(event.getCategory());
		addevent.setDate(event.getDate());
		addevent.setDescription(event.getDescription());
		addevent.setEnd(event.getEnd());
		addevent.setInfo(event.getInfo());
		addevent.setImg(event.getImg());
		addevent.setLocation_name(event.getLocationName());
		addevent.setName(event.getName());
		addevent.setPayment(event.isPayment());
		addevent.setStart(event.getStart());
		
		service.storeEvent(addevent);		
	}

}
