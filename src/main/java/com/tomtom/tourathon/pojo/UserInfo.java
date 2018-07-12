package com.tomtom.tourathon.pojo;


import java.util.List;

import lombok.Data;

@Data
public class UserInfo {
	 	private String id;
		private String userName;
	 	private List<Category> preferences;
	 	private List<PointOfInterest> trip;
	 	
	 	public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public List<Category> getPreferences() {
			return preferences;
		}
		public void setPreferences(List<Category> preferences) {
			this.preferences = preferences;
		}
		public List<PointOfInterest> getTrip() {
			return trip;
		}
		public void setTrip(List<PointOfInterest> trip) {
			this.trip = trip;
		}
	 	
}
