package it.univaq.disim.sose.boredweekend.boredweekendservice.model;

import java.util.ArrayList;
import java.util.List;

public class Weekend {

	List<Day> days;

	public Weekend() {
		this.days = new ArrayList<>();
	}

	public List<Day> getDays() {
		return days;
	}

}
