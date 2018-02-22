package it.univaq.disim.sose.boredweekend.boredweekendservice.model;

import java.util.Comparator;

public class CalendarOrder implements Comparator<Day> {

	@Override
	public int compare(Day day1, Day day2) {
		return day1.getFullDate().compareTo(day2.getFullDate());
	}

}
