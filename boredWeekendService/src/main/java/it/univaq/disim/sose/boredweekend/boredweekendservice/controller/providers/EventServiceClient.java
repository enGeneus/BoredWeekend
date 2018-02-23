package it.univaq.disim.sose.boredweekend.boredweekendservice.controller.providers;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Event;
import it.univaq.disim.sose.boredweekend.boredweekendservice.util.ModelUtils;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.CityEventsRequest;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.CityEventsResponse;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsPT;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsService;

@Service
public class EventServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceClient.class);
	
	@Async
	public CompletableFuture<List<Event>> getEvent(List<String> city, Date start, Date end){
		
		LOGGER.info("Calling event SOAP service");
		
		EventsService service = new EventsService();
		EventsPT port = service.getEventsPort();
		CityEventsRequest request = new CityEventsRequest();

		request.getCity().addAll(city);
		request.setStart(start);
		request.setEnd(end);
		
		CityEventsResponse response = port.getCityEvents(request);

		List<Event> events = ModelUtils.transformEventType(response.getEvents());
		
		LOGGER.info("Returning event service response");

		return CompletableFuture.completedFuture(events);
		
	}
}