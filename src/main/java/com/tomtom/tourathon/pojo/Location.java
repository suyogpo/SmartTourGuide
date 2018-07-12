package com.tomtom.tourathon.pojo;
public class Location {
	public Location(String id, Double lat, Double lon) {
		super();
		this.id = id;
		this.lat = lat;
		this.lon = lon;
	}
	String id;
	Double lat;
	Double lon;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
}
