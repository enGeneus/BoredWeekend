package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Event;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.CityEventsRequest;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.CityEventsResponse;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventType;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsPT;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsService;

@Service
class EventServiceClientSpring {
	
	@Async
	public CompletableFuture<List<Event>> getEvent(List<String> city, Date start, Date end){
		
		EventsService service = new EventsService();
		EventsPT port = service.getEventsPort();
		CityEventsRequest request = new CityEventsRequest();
		
		for (String a : city) {
			request.getCity().add(a);
		}
		
		request.setStart(start);
		request.setEnd(end);
		
		CityEventsResponse response = port.getCityEvents(request);	
		
		return CompletableFuture.completedFuture(setEvents(response.getEvents()));
		
	}
	
	
	public List<Event> setEvents(List<EventType> response) {
		
		List<Event> eventList = new ArrayList<>();
		
		for (EventType event : response) {
			
			Event addevent = new Event();
			
			addevent.setId(event.getId());
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
			
			eventList.add(addevent);
		}
		return eventList;
	}
}