package it.univaq.disim.sose.boredweekend.boredweekendservice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Activity;
import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Event;

public class app {
	
	public static void main(String[] args) throws InterruptedException{
		
		@SuppressWarnings("deprecation")
		Date start = new Date(2017-1900, 01, 01, 8, 00, 00);
		@SuppressWarnings("deprecation")
		Date end = new Date(2019-1900, 01, 01, 8, 00, 00);
		List<String> cities = new ArrayList<>();
		cities.add("Rome");
		cities.add("L'Aquila");
		List<String> category = new ArrayList<>();
		category.add("Nature");
		List<String> days = new ArrayList<>();
		days.add("Monday");
		String daytime = "FullDay";
		
		//la funzione ThreadEventService è cambiata perchè riceve come valore una lista di stringhe
		
		ThreadEventService threadEvent = new ThreadEventService(cities, start, end);
		ThreadActivityService threadEvent2 = new ThreadActivityService(cities, category, days, daytime);
		threadEvent.start();
		threadEvent2.start();
		threadEvent.join();
		threadEvent2.join();
		
		System.out.println("fine Thread");

	
		List<Activity> lista = new ArrayList<>();
		
		System.out.println("fine Thread2");
		
		lista = threadEvent2.getActivity();
		
		for(Activity a : lista) {
			System.out.println(a.getName());
		}
		
				
	}
}
