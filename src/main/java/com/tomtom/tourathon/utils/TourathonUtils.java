package com.tomtom.tourathon.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomtom.tourathon.pojo.Address;
import com.tomtom.tourathon.pojo.Category;
import com.tomtom.tourathon.pojo.Distance;
import com.tomtom.tourathon.pojo.DistanceMatrix;
import com.tomtom.tourathon.pojo.Duration;
import com.tomtom.tourathon.pojo.Element;
import com.tomtom.tourathon.pojo.Location;
import com.tomtom.tourathon.pojo.PointOfInterest;

public class TourathonUtils {
	public static ObjectMapper obj = new ObjectMapper();
	
	public static  List<PointOfInterest> parseTomTomSearchPoiRespone(final String jasonString) throws IOException {
		JsonNode jsonNodeRoot = obj.readTree(jasonString);
		List<PointOfInterest> pois = new ArrayList<>();
		for(int i =0; i<jsonNodeRoot.get("results").size(); i++) {
			JsonNode poiNodeObj = jsonNodeRoot.get("results").get(i);
			PointOfInterest poi = new PointOfInterest();
			poi.setPoiId(poiNodeObj.get("id").toString());
			JsonNode poiNode = poiNodeObj.get("poi");
			poi.setName(poiNode.get("name").toString());
			poi.setPhoneNumber(poiNode.get("phone").toString());
			JsonNode poiAddress = poiNodeObj.get("address");
			Address address = new Address();
			address.setStreetNumber(poiAddress.get("streetNumber").toString());
			address.setStreetName(poiAddress.get("streetName").toString());
			address.setCountry(poiAddress.get("country").toString());
			address.setCountryCode(poiAddress.get("countryCode").toString());
			address.setFreeformAddress(poiAddress.get("freeformAddress").toString());
			address.setId(UUID.randomUUID().toString());
			poi.setAddress(address);
			Double lat = poiNodeObj.get("position").get("lat").asDouble();
			Double lon = poiNodeObj.get("position").get("lon").asDouble();
			poi.setLat(lat);
			poi.setLon(lon);
			Double distance = poiNodeObj.get("dist").asDouble();
			poi.setDistance(distance);
			pois.add(poi);
		}
		return pois;
	}
	
	public static List<PointOfInterest> parseGoogleSearchPoiResponse(final Category category, final String jasonString) throws IOException{
		JsonNode jsonNodeRoot = obj.readTree(jasonString);
		List<PointOfInterest> pois = new ArrayList<>();
		for(int i =0; i<jsonNodeRoot.get("results").size(); i++) {
			JsonNode poiNodeObj = jsonNodeRoot.get("results").get(i);
			PointOfInterest poi = new PointOfInterest();
			poi.setPoiId(poiNodeObj.get("id").toString());
			poi.setName(poiNodeObj.get("name").toString());
			Double lat =poiNodeObj.get("geometry").get("location").get("lat").asDouble();
			System.out.println(poiNodeObj.get("geometry").get("location"));
			Double lon =poiNodeObj.get("geometry").get("location").get("lng").asDouble();
			poi.setLat(lat);
			poi.setLon(lon);
			poi.setCategory(category);
			//call distance matrix
			pois.add(poi);
			
		}
		return pois;
	}
	
	public static Location parseLocationJsonString(final String jsonString) throws IOException{
		JsonNode jsonNodeRoot = obj.readTree(jsonString);
		return  new Location(jsonNodeRoot.get("id").toString(), jsonNodeRoot.get("lat").asDouble(), jsonNodeRoot.get("lon").asDouble());
	}
	
	public static List<Location> parseDestinationsJsonString(final String jsonString) throws IOException{
		JsonNode jsonNodeRoot = obj.readTree(jsonString);
		int number = jsonNodeRoot.size();
		List<Location> locations = new ArrayList<>();
		for(int i=0; i<number; i++) {
			locations.add(parseLocationJsonString(jsonNodeRoot.get(i).toString()));
		}
		return locations;
	}
	
	public static Element[][] parseGoogleDistanceMatrixResponse(final String jasonString) throws IOException{
		JsonNode jsonNodeRoot = obj.readTree(jasonString);
	 	   DistanceMatrix distMatrix = new DistanceMatrix();
	 	   JsonNode rowsList = jsonNodeRoot.get("rows");
	 	   int rowListSize = rowsList.size();
	 	   Element[][] elementsArray = new Element[rowListSize][rowListSize];
	 	   
	 	   
	 	   
	 	   for(int i =0; i< rowListSize; i++) {
	 		   JsonNode jsonElements = rowsList.get(i);
	 		   JsonNode jsonElementsList = jsonElements.get("elements");
	 		   int tempDist = 0;
	 		   int tempDur = 0;
	 		   for(int j = 0; j < jsonElementsList.size(); j++) {
	 			   JsonNode jsonElement = jsonElementsList.get(j);
	 			   JsonNode distance = jsonElement.get("distance");
	 			   tempDist = distance.get("value").intValue();
	 			   if(tempDist <= 1) {
	 				   tempDist = Integer.MAX_VALUE;
	 			   }
	 			   Distance dist = new Distance(tempDist);
	 			   JsonNode duration = jsonElement.get("duration");
	 			   tempDur = duration.get("value").intValue();
	 			   if(tempDur <= 0) {
	 				   tempDur = Integer.MAX_VALUE;
	 			   }
	 			   Duration dur = new Duration(tempDur); 
	 			   elementsArray[i][j] = new Element(dist, dur);
	 		   }
	 	   }
	 	   return elementsArray;
	}
	
	

}
