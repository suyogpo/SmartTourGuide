package com.tomtom.tourathon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomtom.tourathon.pojo.Address;
import com.tomtom.tourathon.pojo.Category;
import com.tomtom.tourathon.pojo.PointOfInterest;
import com.tomtom.tourathon.pojo.UserInfo;
import com.tomtom.tourathon.utils.TourathonFileUtils;
import com.tomtom.tourathon.utils.TourathonUtils;

@RestController
public class TourathonController {

	final private String TOMTOM_KEY =  "tXAUytnEybxLARy7N2dgwFazfIJNaprf";
	
    @RequestMapping("/getPoisFromPreference")
    public List<PointOfInterest> getPoisFromPreference(@RequestParam(value="categories") String categories, 
    													@RequestParam(value="lat", defaultValue="37.8085") String latCord,
    													@RequestParam(value="long", defaultValue="-122.4239") String longCord) throws IOException{
    	RestTemplate restTemplate = new RestTemplate();
    	String [] categoryArr = categories.split(",");
    	List<PointOfInterest> allPois = new ArrayList<>();
    	for(String category : categoryArr) {
    		String hostUrl = "api.tomtom.com/search/2/search/"+category+ ".json";
    		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https").host(hostUrl).query("key={keyword}").
    	    		  query("lat={keyword}").query("lon={keyword}").buildAndExpand(TOMTOM_KEY, latCord, longCord);
    			String result = restTemplate.getForObject(uriComponents.toUriString(), String.class);
    			try {
    				List<PointOfInterest> poisForCategory = TourathonUtils.parseSearchPoiRespone(result);
    				allPois.addAll(poisForCategory);
    			}catch(final Exception e ) {
    				//do nothing. Swallow the exception
    			}
    	}
		return allPois;
    }
    
    @RequestMapping("/saveCategories")
    public void savePreferences(@RequestParam(value ="userName") String userName,@RequestParam(value ="userId") String userId,  @RequestParam(value = "categories") String categories) throws IOException {
    	String[] categoryArr = categories.split(",");
    	List<Category> preferences = new ArrayList<>();
    	UserInfo ui = new UserInfo();
    	ui.setId(userId);
    	ui.setUserName(userName);
    	for(String category: categoryArr) {
    		preferences.add(Category.valueOf(category));
    	}
    	ui.setPreferences(preferences);
    	final String userInfoJson = TourathonUtils.obj.writeValueAsString(ui);
    	TourathonFileUtils fileUtils = new TourathonFileUtils();
    	fileUtils.writeToUserFile(userName, userInfoJson);
    }

}
