package it.univaq.disim.sose.boredweekend.activitiesservice.business.model;

import java.util.ArrayList;
import java.util.List;

public class Activity {

	private int id;
	private String name;
	private String city;
	private double lat;
	private double lon;
	private String daytime;
	private boolean state;
	private String info;
	private boolean payment;
	private byte[] img;

	List<String> days;
	List<String> categories;

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

	public String getDaytime() {
		return daytime;
	}

	public void setDaytime(String daytime) {
		this.daytime = daytime;
	}

	public boolean isAvailable() {
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

	public List<String> getDays() {
		return days;
	}

	public List<String> getCategories() {
		return categories;
	}
	
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	public void setDays(List<String> days) {
		this.days = days;
	}
	

}
