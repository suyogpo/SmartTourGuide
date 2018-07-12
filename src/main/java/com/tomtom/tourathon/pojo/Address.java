package com.tomtom.tourathon.pojo;

public class Address {
	
	private String id;
	private String streetNumber;
	private String streetName;
	private String postalCode;
	private String countryCode;
	private String country;
	private String freeformAddress;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFreeformAddress() {
		return freeformAddress;
	}
	public void setFreeformAddress(String freeformAddress) {
		this.freeformAddress = freeformAddress;
	}
}
