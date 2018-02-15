package it.univaq.disim.sose.boredweekend.eventsservice.business;

import java.util.Date;
import java.util.List;

import it.univaq.disim.sose.boredweekend.eventsservice.business.model.Event;

public interface EventsService {

	void storeEvent(Event event);

	List<Event> getEvents(List<String> city, Date start, Date end);
}
