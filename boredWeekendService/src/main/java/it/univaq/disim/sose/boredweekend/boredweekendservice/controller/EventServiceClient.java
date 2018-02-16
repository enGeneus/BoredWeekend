package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Event;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.CityEventsRequest;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.CityEventsResponse;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventType;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsPT;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsService;

class EventServiceClient extends Thread {
	
	private List<String> city;
	private Date start;
	private Date end;
	private List<Event> events = new ArrayList<>();
	
	public EventServiceClient(List<String> city, Date start, Date end) {
		this.city = city;
		this.start = start;
		this.end = end;
	}
   
	public List<Event> getEvents(){
		return this.events;
	}
	
	public void setEvents(List<EventType> response) {
		
		for (EventType event : response) {
			
			Event addevent = new Event();
			
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
			
			this.events.add(addevent);

		}
	}

	public void run() {

		EventsService service = new EventsService();
		EventsPT port = service.getEventsPort();
		CityEventsRequest request = new CityEventsRequest();
		
		for (String a : this.city) {
			request.getCity().add(a);
		}
		
		request.setStart(this.start);
		request.setEnd(this.end);
		
		CityEventsResponse response = port.getCityEvents(request);	
		
		setEvents(response.getEvents());

	}
}