package it.univaq.disim.sose.boredweekend.eventsservice.business.jdbc;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.boredweekend.eventsservice.business.EventsDAO;
import it.univaq.disim.sose.boredweekend.eventsservice.business.EventsService;
import it.univaq.disim.sose.boredweekend.eventsservice.business.model.Event;



@Service
public class JDBCEventsServiceImpl implements EventsService {
	
	@Autowired
	private EventsDAO eventDAO;


	@Override
	public List<Event> getEvents(List<String> city, Date start, Date end) {

		return eventDAO.find(city, start, end);
	}

	@Override
	public void storeEvent(Event event) {
		eventDAO.insert(event);		
	}

}
