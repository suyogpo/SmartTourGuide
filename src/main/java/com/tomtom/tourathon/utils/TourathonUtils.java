package com.tomtom.tourathon.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomtom.tourathon.pojo.Address;
import com.tomtom.tourathon.pojo.PointOfInterest;

public class TourathonUtils {
	public static ObjectMapper obj = new ObjectMapper();
	
	public static  List<PointOfInterest> parseSearchPoiRespone(final String jasonString) throws IOException {
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
	

}
