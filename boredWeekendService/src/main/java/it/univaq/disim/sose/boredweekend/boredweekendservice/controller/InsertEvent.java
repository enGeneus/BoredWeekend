package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;


import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Event;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.AddEventRequest;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventCategory;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventType;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsPT;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsService;

public class InsertEvent {
	
	private EventType eventType = new EventType();
	
	public InsertEvent(Event event) {
		
		this.eventType.setAddress(event.getAddress());
		this.eventType.setCity(event.getCity());
		this.eventType.setCategory(EventCategory.fromValue(event.getCategories()));
		this.eventType.setDescription(event.getDescription());
		this.eventType.setEnd(event.getEnd());
		this.eventType.setInfo(event.getInfo());
		this.eventType.setImg(event.getImg());
		this.eventType.setLocationName(event.getLocationName());
		this.eventType.setName(event.getName());
		this.eventType.setPayment(event.isPayment());
		this.eventType.setStart(event.getStart());
		
	}
	
	public void addEvent() {
		
		EventsService service = new EventsService();
		EventsPT port = service.getEventsPort();
		AddEventRequest request = new AddEventRequest();
		request.setEvent(this.eventType);
		port.addEvent(request.getEvent());
		
	}


}
