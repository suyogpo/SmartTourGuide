package com.tomtom.tourathon.pojo;


import lombok.Data;

@Data
public class POIDetail {
	 	private Long id;
	 	private String poiName;
	 	private String description;
	 	private float rating;
	 	private String thumbnailPath;
	 	private String photosPath;
	 	private float avgTimeSpent;
	 	private float entryFee;
	 	
}
