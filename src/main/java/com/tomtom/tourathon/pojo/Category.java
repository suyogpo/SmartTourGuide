package com.tomtom.tourathon.pojo;

public enum Category {
	
	MUSEUM(1,"", "museum"),
	AMUSEMENT_PARK(2,"", "amusement_park"),
	PUB(3, "", "night_club"),
	RESTAURANT(4, "", "restaurant"),
	WORSHIP(5, "", "place_of_worship");
	
	private Category(Integer id, String tomtomName, String googleName) {
		this.id = id;
		this.tomtomName = tomtomName;
		this.googleName = googleName;
	}
	
	private Integer id;
	private String tomtomName;
	private String googleName;
	
	public String getTomtomName() {
		return tomtomName;
	}

	public String getGoogleName() {
		return googleName;
	}

	public int getId(){
		return id;	
	}

}
