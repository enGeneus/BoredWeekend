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
		/*
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
		*/
		/*
		String name = "Prova";
		String city = "L'Aquila";
		String info = "Prova inserimento attività";
		boolean payment = false;
		boolean state = true;
		byte[] img = null;
		String daytime2 = "Night";
		double lat = 42.21;
		double lon = 13.23;
		List<String> category2 = new ArrayList<>();
		category2.add("Culture");
		List<String> day2 = new ArrayList<>();
		day2.add("Thursday");
		
		InsertActivity insertActivity = new InsertActivity(name, city, lat, lon, daytime2, state, info, payment, img, category2, day2);
		insertActivity.addActivity();
		*/
		
		String address = "via Vado di sole 12";
		String city = "L'Aquila";
		String category3 = "Culture";
		String description = "Speriamo funzioni!";
		Date end2 = new Date(2018-1900, 01, 15, 23, 59, 00);
		String info2 = "Prova inserimento evento";
		byte[] img2 = null;
		String location = "Casa";
		String name2 = "Prova";
		boolean payment2 = false;
		Date start2 = new Date(2018-1900, 01, 15, 21, 30, 00);
		
		InsertEvent insertEvent = new InsertEvent(address, city, category3, description, end2, start2, info2, img2, location, name2, payment2);
		insertEvent.addEvent();
		
	}
}
