package it.univaq.disim.sose.boredweekend.eventsservice.business;

import java.util.Date;
import java.util.List;

import it.univaq.disim.sose.boredweekend.eventsservice.business.model.Event;

public interface EventsService {

	public void storeEvent(Event event);

	public List<Event> getEvents(List<String> cities, Date start, Date end);
}
