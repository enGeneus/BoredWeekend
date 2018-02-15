package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Event;

public class app {
	
	public static void main(String[] args) throws InterruptedException{
		
		@SuppressWarnings("deprecation")
		Date start = new Date(2017-1900, 01, 01, 8, 00, 00);
		@SuppressWarnings("deprecation")
		Date end = new Date(2019-1900, 01, 01, 8, 00, 00);
		
		
		//la funzione ThreadEventService è cambiata perchè riceve come valore una lista di stringhe
		/* 
		ThreadEventService threadEvent = new ThreadEventService("Rome", start, end);
		threadEvent.start();
		threadEvent.join();
		
		System.out.println("fine Thread");

	
		List<Event> lista = new ArrayList<>();
		
		System.out.println("fine Thread2");
		
		lista = threadEvent.getEvents();
		
		for(Event a : lista) {
			System.out.println(a.getLocationName());
		}
		*/
				
	}
}
