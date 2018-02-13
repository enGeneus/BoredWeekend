package it.univaq.disim.sose.boredweekend.eventsservice.business;

import java.util.Date;
import java.util.List;

import it.univaq.disim.sose.boredweekend.eventsservice.business.model.Event;

public interface EventsDAO {

	public void insert(Event event);

	public List<Event> find(String city, Date start, Date end);

}
