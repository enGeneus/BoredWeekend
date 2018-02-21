package it.univaq.disim.sose.boredweekend.boredweekendservice.util;

import java.util.Comparator;

import it.univaq.disim.sose.boredweekend.boredweekendservice.model.Day;

public class CalendarOrder implements Comparator<Day> {

	@Override
	public int compare(Day day1, Day day2) {
		return day1.getFullDate().compareTo(day2.getFullDate());
	}

}
