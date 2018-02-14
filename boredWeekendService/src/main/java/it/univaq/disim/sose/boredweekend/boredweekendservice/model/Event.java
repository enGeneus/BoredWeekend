package it.univaq.disim.sose.boredweekend.boredweekendservice.model;

import java.util.Date;

public class Event {
	
	private int id;
	private String info;
	private String address;
	private String name;
	private String city;
	private Date start;
	private Date end;
	private String description;
	private String locationName;
	private byte[] img;
	private boolean payment;

	String category;
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String location_name) {
		this.locationName = location_name;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public boolean isPayment() {
		return payment;
	}
	public void setPayment(boolean payment) {
		this.payment = payment;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCategory(String categories) {
		this.category = categories;
	}
	public String getCategories() {
		return this.category;	
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
