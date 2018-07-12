package com.tomtom.tourathon.pojo;

public enum Category {
	
	MUSEUM(1,""),
	AMUSEMENT_PARK(2,""),
	PUB(3, ""),
	PIZZA(4, ""),
	WORSHIP(5, "");
	
	private Category(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	private Integer id;
	private String name;
	
	public int getId(){
		return id;
	}
	public String getName() {
		return name;
	}
	
}
