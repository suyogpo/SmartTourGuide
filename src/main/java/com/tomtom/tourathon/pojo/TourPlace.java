package com.tomtom.tourathon.pojo;

import java.util.Date;
import lombok.*;

@Data
public class TourPlace {
	
	private String poiId;
	private boolean visited;
	private Date visitedOn;
	

}
