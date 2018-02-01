package it.univaq.disim.sose.boredweekend.eventsservice.business;

import java.util.Date;
import java.util.List;

import it.univaq.disim.sose.boredweekend.eventsservice.business.model.Events;

public interface EventsService {

	void storeEvent(Events event);

	List<Events> getEvents(String city, Date start, Date end);
}
