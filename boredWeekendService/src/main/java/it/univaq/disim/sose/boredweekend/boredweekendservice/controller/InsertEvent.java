package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.util.Date;

import it.univaq.disim.sose.boredweekend.providers.eventsservice.AddEventRequest;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventCategory;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventType;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsPT;
import it.univaq.disim.sose.boredweekend.providers.eventsservice.EventsService;

public class InsertEvent {
	
	private EventType eventType = new EventType();
	
	public InsertEvent(String address, String city, String category, String description, Date end, Date start, String info, byte[] img, String location, String name, boolean payment) {
		
		this.eventType.setAddress(address);
		this.eventType.setCity(city);
		this.eventType.setCategory(EventCategory.fromValue(category));
		this.eventType.setDescription(description);
		this.eventType.setEnd(end);
		this.eventType.setInfo(info);
		this.eventType.setImg(img);
		this.eventType.setLocationName(location);
		this.eventType.setName(name);
		this.eventType.setPayment(payment);
		this.eventType.setStart(start);
		
	}
	
	public void addEvent() {
		
		EventsService service = new EventsService();
		EventsPT port = service.getEventsPort();
		AddEventRequest request = new AddEventRequest();
		request.setEvent(this.eventType);
		port.addEvent(request.getEvent());
		
	}


}
