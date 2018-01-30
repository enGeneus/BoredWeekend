package it.univaq.disim.sose.boredweekend.eventsservice.business.impl.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.univaq.disim.sose.boredweekend.eventsservice.business.EventsService;
import it.univaq.disim.sose.boredweekend.eventsservice.business.model.Events;

public class DummyEventsServiceImpl implements EventsService{

	@Override
	public void storeActivity(Events activity) {
		
	}

	@Override
	public List<Events> getEvents(String city, Date start, Date end) {
		return new ArrayList<>();
	}
}
