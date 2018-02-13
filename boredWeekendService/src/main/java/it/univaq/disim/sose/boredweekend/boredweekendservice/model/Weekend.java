package it.univaq.disim.sose.boredweekend.boredweekendservice.model;

import java.util.ArrayList;
import java.util.List;

public class Weekend {

	List<Day> days;
	List<Place> places;

	public Weekend() {
		this.days = new ArrayList<>();
		this.places = new ArrayList<>();
	}

	public List<Day> getDays() {
		return days;
	}

	public List<Place> getPlaces() {
		return places;
	}

}
