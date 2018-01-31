package it.univaq.disim.sose.boredweekend.activitiesservice.business.model;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.sose.boredweekend.activitiesservice.ActivityCategory;
import it.univaq.disim.sose.boredweekend.activitiesservice.Daytime;
import it.univaq.disim.sose.boredweekend.activitiesservice.WeekDay;

public class Activity {

	private int id;
	private String name;
	private String city;
	private double lat;
	private double lon;
	private Daytime daytime;
	private boolean state;
	private String info;
	private boolean payment;
	private byte[] img;

	List<WeekDay> days;
	List<ActivityCategory> categories;

	public Activity() {
		this.days = new ArrayList<>();
		this.categories = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public Daytime getDaytime() {
		return daytime;
	}

	public void setDaytime(Daytime daytime) {
		this.daytime = daytime;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isPayment() {
		return payment;
	}

	public void setPayment(boolean payment) {
		this.payment = payment;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public List<WeekDay> getDays() {
		return days;
	}

	public List<ActivityCategory> getCategories() {
		return categories;
	}
	
	public void setCategories(List<ActivityCategory> categories) {
		this.categories = categories;
	}
	
	public void setDays(List<WeekDay> days) {
		this.days = days;
	}
	

}
