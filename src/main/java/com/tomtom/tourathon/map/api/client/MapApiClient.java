package com.tomtom.tourathon.map.api.client;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.tomtom.tourathon.pojo.Category;
import com.tomtom.tourathon.pojo.Location;

public class MapApiClient {
	final private String TOMTOM_KEY =  "tXAUytnEybxLARy7N2dgwFazfIJNaprf";
	final private String GOOGLE_KEY = "AIzaSyBowJMNnWZQri07xnPfW9j6fFpXJs9ge5M";
	final private String TOMTOM_SEARCH_HOST="api.tomtom.com/search/2/search/";
	final private String GOOGLE_NEAR_BY_SEARCH_HOST="maps.googleapis.com/maps/api/place/nearbysearch/json";
	final private String GOOGLE_DISTANCE_MATRIX ="maps.googleapis.com/maps/api/distancematrix/json";
	RestTemplate restTemplate = new RestTemplate();
	
	public String searchPoisFromPreferenceUsingTomtomApi(final String category, final String latCord, final String lonCord ) {
			String hostUrl = TOMTOM_SEARCH_HOST+category+ ".json";
			UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host(hostUrl).query("key={keyword}").
		    		  query("lat={keyword}").query("lon={keyword}").buildAndExpand(TOMTOM_KEY, latCord, lonCord);
			System.out.println(uriComponents.toUriString());
			return restTemplate.getForObject(uriComponents.toUriString(), String.class);
	}
	
	public String searchNearByPoisForCurrentLocationUsingGoogleApi(final Category category, final String latCord, final String lonCord ) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host(GOOGLE_NEAR_BY_SEARCH_HOST).query("location={keyword},{keyword}").query("radius={keyword}")
    			.query("type={keyword}").query("key={keyword}").buildAndExpand(latCord,lonCord,"1500", category.getGoogleName(), GOOGLE_KEY);
		return restTemplate.getForObject(uriComponents.toUriString(), String.class);
	}
	
	public String getDistanceMatrixUsingGoogleApi(Location[] destinationLocations) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=");
		String s1 = "";
		for(int i =0; i<destinationLocations.length; i++) {
			s1 += "" + destinationLocations[i].getLat() + "," + destinationLocations[i].getLon();
			if(i!=destinationLocations.length-1) {
				s1 +="|";
			}
		}
		stringBuilder.append(s1);
		stringBuilder.append("&destinations=").append(s1).append("&key=").append(GOOGLE_KEY);
		System.out.println(stringBuilder.toString());
		return  restTemplate.getForObject(stringBuilder.toString(), String.class);
	}
	
}
