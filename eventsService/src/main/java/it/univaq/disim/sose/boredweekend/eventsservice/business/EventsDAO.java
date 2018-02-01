package it.univaq.disim.sose.boredweekend.eventsservice.business;

import java.util.Date;
import java.util.List;

import it.univaq.disim.sose.boredweekend.eventsservice.business.model.Events;

public interface EventsDAO {

	public void insert(Events event);

	public Events find(int id);

	public List<Events> find(String city, Date start, Date end);

}
