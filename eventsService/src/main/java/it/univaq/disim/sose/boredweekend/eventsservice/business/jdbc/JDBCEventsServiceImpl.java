package it.univaq.disim.sose.boredweekend.eventsservice.business.jdbc;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.boredweekend.eventsservice.business.EventsService;
import it.univaq.disim.sose.boredweekend.eventsservice.business.model.Event;
import it.univaq.disim.sose.boredweekend.eventsservice.business.EventsDAO;



@Service
public class JDBCEventsServiceImpl implements EventsService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(JDBCEventsServiceImpl.class);
	
	@Autowired
	private EventsDAO eventDAO;


	@Override
	public List<Event> getEvents(String city, Date start, Date end) {

		return eventDAO.find(city, start, end);
	}

	@Override
	public void storeEvent(Event event) {
		eventDAO.insert(event);

		// TODO Auto-generated method stub
		
	}

}
