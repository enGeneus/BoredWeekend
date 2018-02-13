package it.univaq.disim.sose.boredweekend.eventsservice.webservice;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.disim.sose.boredweekend.eventsservice.CityEventsRequest;
import it.univaq.disim.sose.boredweekend.eventsservice.CityEventsResponse;
import it.univaq.disim.sose.boredweekend.eventsservice.EventCategory;
import it.univaq.disim.sose.boredweekend.eventsservice.EventType;
import it.univaq.disim.sose.boredweekend.eventsservice.EventsPT;
import it.univaq.disim.sose.boredweekend.eventsservice.business.EventsService;
import it.univaq.disim.sose.boredweekend.eventsservice.business.model.Event;

@Component(value = "EventsPTImpl")
public class EventsPTImpl implements EventsPT {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventsPTImpl.class);

	@Autowired
	private EventsService service;

	@Override
	public CityEventsResponse getCityEvents(CityEventsRequest parameters) {
		List<Event> events = service.getEvents(parameters.getCity(), parameters.getStart(), parameters.getEnd());

		CityEventsResponse response = new CityEventsResponse();

		for (Event event : events) {

			LOGGER.info("Found event: " + event.getName());

			EventType eventType = new EventType();
			eventType.setAddress(event.getAddress());
			eventType.setCategory(EventCategory.fromValue(event.getCategories()));
			eventType.setCity(event.getCity());
			eventType.setDescription(event.getDescription());
			eventType.setEnd(event.getEnd());
			eventType.setImg(event.getImg());
			eventType.setInfo(event.getInfo());
			eventType.setLocationName(event.getLocationName());
			eventType.setName(event.getName());
			eventType.setPayment(event.isPayment());
			eventType.setStart(event.getStart());

			response.getEvents().add(eventType);
		}
		
		return response;
	}

	@Override
	public void addEvent(EventType event) {
		
		Event addevent = new Event();
		
		addevent.setAddress(event.getAddress());
		addevent.setCity(event.getCity());
		addevent.setCategory(event.getCategory().value());
		addevent.setDescription(event.getDescription());
		addevent.setEnd(event.getEnd());
		addevent.setInfo(event.getInfo());
		addevent.setImg(event.getImg());
		addevent.setLocationName(event.getLocationName());
		addevent.setName(event.getName());
		addevent.setPayment(event.isPayment());
		addevent.setStart(event.getStart());
		
		service.storeEvent(addevent);		
	}

}
