package com.tomtom.tourathon.pojo;
public class Element {
	
	Distance distance;
	Duration duration;
	
	public Element(Distance dist, Duration dur) {
		this.distance = dist;
		this.duration = dur;
	}
	
	public Distance getDistance() {
		return distance;
	}
	public void setDistance(Distance distance) {
		this.distance = distance;
	}
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
}
