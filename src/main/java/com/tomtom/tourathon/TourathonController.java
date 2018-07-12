package com.tomtom.tourathon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomtom.tourathon.map.api.client.MapApiClient;
import com.tomtom.tourathon.pojo.Address;
import com.tomtom.tourathon.pojo.Category;
import com.tomtom.tourathon.pojo.Distance;
import com.tomtom.tourathon.pojo.DistanceMatrix;
import com.tomtom.tourathon.pojo.Duration;
import com.tomtom.tourathon.pojo.Element;
import com.tomtom.tourathon.pojo.Location;
import com.tomtom.tourathon.pojo.PointOfInterest;
import com.tomtom.tourathon.pojo.UserInfo;
import com.tomtom.tourathon.utils.TourathonFileUtils;
import com.tomtom.tourathon.utils.TourathonUtils;

@RestController
public class TourathonController {
	static MapApiClient mapApiClient = new MapApiClient();

    @RequestMapping("/getPoisFromPreference")
    public List<PointOfInterest> getPoisFromPreference(@RequestParam(value="categories") String categories, 
    													@RequestParam(value="lat") String latCord,
    													@RequestParam(value="lon") String lonCord) throws IOException{
    	
    	String [] categoryArr = categories.split(",");
    	List<PointOfInterest> allPois = new ArrayList<>();
    	for(String category : categoryArr) {
    			try {
    				String result = mapApiClient.searchPoisFromPreferenceUsingTomtomApi(category, latCord, lonCord);
    				List<PointOfInterest> poisForCategory = TourathonUtils.parseTomTomSearchPoiRespone(result);
    				allPois.addAll(poisForCategory);
    			}catch(final Exception e ) {
    				//do nothing. Swallow the exception
    			}
    	}
		return allPois;
    }
    
    @RequestMapping("/getNearByPoisForCurrentLocation")
    public  List<PointOfInterest> getNearByPoisForCurrentLocation( @RequestParam(value="lat") String latCord,
																@RequestParam(value="lon") String lonCord) throws IOException{
    	List<PointOfInterest> allPois = new ArrayList<>();
    	for (Category category : Category.values()) {
    		String result = mapApiClient.searchNearByPoisForCurrentLocationUsingGoogleApi(category, latCord, lonCord);
    		List<PointOfInterest> categoryPois = TourathonUtils.parseGoogleSearchPoiResponse(category, result);
    		allPois.addAll(categoryPois);
    	}
    	return allPois;
    }
    
    
//    @RequestMapping("/getDistanceMatrix")
//    public  Element[][] getDistanceMatrix( @RequestParam(value="latFrom") String latCordFrom,
//													@RequestParam(value="lonFrom") String longCordFrom,
//													@RequestParam(value="latTo") String latCordTo,
//													@RequestParam(value="lonTo") String longCordTo) throws IOException{
//    	Element[][] elementsArray  = null;
//    	String result = mapApiClient.getDistanceMatrixUsingGoogleApi(latCordFrom, longCordFrom, latCordTo, longCordTo);
//    	elementsArray = TourathonUtils.parseGoogleDistanceMatrixResponse(result);
//    	return elementsArray;
//    }
    
    @PostMapping("/getPlannedTrip")
    public static Object getPlannedTrip(@RequestBody String body) throws  IOException
    {
 	   ObjectMapper mapper = new ObjectMapper();
// 	   //String jsonInString = "{\n    \"destination_addresses\": [\n        \"566 Vermont St, Brooklyn, NY 11207, USA\",\n        \"67 Pacific St, Brooklyn, NY 11201, USA\"\n    ],\n    \"origin_addresses\": [\n        \"566 Vermont St, Brooklyn, NY 11207, USA\",\n        \"67 Pacific St, Brooklyn, NY 11201, USA\"\n    ],\n    \"rows\": [\n        {\n            \"elements\": [\n                {\n                    \"distance\": {\n                        \"text\": \"1 ft\",\n                        \"value\": 0\n                    },\n                    \"duration\": {\n                        \"text\": \"1 min\",\n                        \"value\": 0\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"6.5 mi\",\n                        \"value\": 10431\n                    },\n                    \"duration\": {\n                        \"text\": \"36 mins\",\n                        \"value\": 2143\n                    },\n                    \"status\": \"OK\"\n                }\n            ]\n        },\n        {\n            \"elements\": [\n                {\n                    \"distance\": {\n                        \"text\": \"6.4 mi\",\n                        \"value\": 10352\n                    },\n                    \"duration\": {\n                        \"text\": \"34 mins\",\n                        \"value\": 2030\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"1 ft\",\n                        \"value\": 0\n                    },\n                    \"duration\": {\n                        \"text\": \"1 min\",\n                        \"value\": 0\n                    },\n                    \"status\": \"OK\"\n                }\n            ]\n        }\n    ],\n    \"status\": \"OK\"\n}";
// 	   String jsonInString = "{\n" + 
// 	   "    \"destination_addresses\": [\n        \"67 Pacific St, Brooklyn, NY 11201, USA\",\n        \"585 Schenectady Ave, Brooklyn, NY 11203, USA\",\n        \"66-0 103rd St, Rego Park, NY 11374, USA\",\n        \"931 N Village Ave, Rockville Centre, NY 11570, USA\",\n        \"316 Beach 19th St, Far Rockaway, NY 11691, USA\",\n        \"66-0 103rd St, Rego Park, NY 11374, USA\"\n    ],\n    \"origin_addresses\": [\n        \"67 Pacific St, Brooklyn, NY 11201, USA\",\n        \"585 Schenectady Ave, Brooklyn, NY 11203, USA\",\n        \"66-0 103rd St, Rego Park, NY 11374, USA\",\n        \"931 N Village Ave, Rockville Centre, NY 11570, USA\",\n        \"316 Beach 19th St, Far Rockaway, NY 11691, USA\",\n        \"66-0 103rd St, Rego Park, NY 11374, USA\"\n    ],\n    \"rows\": [\n        {\n            \"elements\": [\n                {\n                    \"distance\": {\n                        \"text\": \"1 ft\",\n                        \"value\": 0\n                    },\n                    \"duration\": {\n                        \"text\": \"1 min\",\n                        \"value\": 0\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"4.7 mi\",\n                        \"value\": 7578\n                    },\n                    \"duration\": {\n                        \"text\": \"26 mins\",\n                        \"value\": 1583\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"10.7 mi\",\n                        \"value\": 17201\n                    },\n                    \"duration\": {\n                        \"text\": \"23 mins\",\n                        \"value\": 1407\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"27.1 mi\",\n                        \"value\": 43623\n                    },\n                    \"duration\": {\n                        \"text\": \"42 mins\",\n                        \"value\": 2499\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"24.7 mi\",\n                        \"value\": 39801\n                    },\n                    \"duration\": {\n                        \"text\": \"49 mins\",\n                        \"value\": 2967\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"10.7 mi\",\n                        \"value\": 17201\n                    },\n                    \"duration\": {\n                        \"text\": \"23 mins\",\n                        \"value\": 1407\n                    },\n                    \"status\": \"OK\"\n                }\n            ]\n        },\n        {\n            \"elements\": [\n                {\n                    \"distance\": {\n                        \"text\": \"5.8 mi\",\n                        \"value\": 9386\n                    },\n                    \"duration\": {\n                        \"text\": \"26 mins\",\n                        \"value\": 1534\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"1 ft\",\n                        \"value\": 0\n                    },\n                    \"duration\": {\n                        \"text\": \"1 min\",\n                        \"value\": 0\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"10.5 mi\",\n                        \"value\": 16842\n                    },\n                    \"duration\": {\n                        \"text\": \"34 mins\",\n                        \"value\": 2064\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"19.1 mi\",\n                        \"value\": 30675\n                    },\n                    \"duration\": {\n                        \"text\": \"40 mins\",\n                        \"value\": 2378\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"16.5 mi\",\n                        \"value\": 26481\n                    },\n                    \"duration\": {\n                        \"text\": \"45 mins\",\n                        \"value\": 2712\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"10.5 mi\",\n                        \"value\": 16842\n                    },\n                    \"duration\": {\n                        \"text\": \"34 mins\",\n                        \"value\": 2064\n                    },\n                    \"status\": \"OK\"\n                }\n            ]\n        },\n        {\n            \"elements\": [\n                {\n                    \"distance\": {\n                        \"text\": \"11.3 mi\",\n                        \"value\": 18215\n                    },\n                    \"duration\": {\n                        \"text\": \"28 mins\",\n                        \"value\": 1676\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"9.7 mi\",\n                        \"value\": 15684\n                    },\n                    \"duration\": {\n                        \"text\": \"31 mins\",\n                        \"value\": 1858\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"1 ft\",\n                        \"value\": 0\n                    },\n                    \"duration\": {\n                        \"text\": \"1 min\",\n                        \"value\": 0\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"17.8 mi\",\n                        \"value\": 28613\n                    },\n                    \"duration\": {\n                        \"text\": \"28 mins\",\n                        \"value\": 1660\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"14.0 mi\",\n                        \"value\": 22502\n                    },\n                    \"duration\": {\n                        \"text\": \"34 mins\",\n                        \"value\": 2030\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"1 ft\",\n                        \"value\": 0\n                    },\n                    \"duration\": {\n                        \"text\": \"1 min\",\n                        \"value\": 0\n                    },\n                    \"status\": \"OK\"\n                }\n            ]\n        },\n        {\n            \"elements\": [\n                {\n                    \"distance\": {\n                        \"text\": \"27.9 mi\",\n                        \"value\": 44851\n                    },\n                    \"duration\": {\n                        \"text\": \"44 mins\",\n                        \"value\": 2662\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"23.6 mi\",\n                        \"value\": 37913\n                    },\n                    \"duration\": {\n                        \"text\": \"43 mins\",\n                        \"value\": 2556\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"18.0 mi\",\n                        \"value\": 29016\n                    },\n                    \"duration\": {\n                        \"text\": \"28 mins\",\n                        \"value\": 1683\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"1 ft\",\n                        \"value\": 0\n                    },\n                    \"duration\": {\n                        \"text\": \"1 min\",\n                        \"value\": 0\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"10.9 mi\",\n                        \"value\": 17583\n                    },\n                    \"duration\": {\n                        \"text\": \"31 mins\",\n                        \"value\": 1858\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"18.0 mi\",\n                        \"value\": 29016\n                    },\n                    \"duration\": {\n                        \"text\": \"28 mins\",\n                        \"value\": 1683\n                    },\n                    \"status\": \"OK\"\n                }\n            ]\n        },\n        {\n            \"elements\": [\n                {\n                    \"distance\": {\n                        \"text\": \"23.8 mi\",\n                        \"value\": 38359\n                    },\n                    \"duration\": {\n                        \"text\": \"54 mins\",\n                        \"value\": 3210\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"15.7 mi\",\n                        \"value\": 25246\n                    },\n                    \"duration\": {\n                        \"text\": \"49 mins\",\n                        \"value\": 2935\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"12.8 mi\",\n                        \"value\": 20626\n                    },\n                    \"duration\": {\n                        \"text\": \"36 mins\",\n                        \"value\": 2163\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"9.4 mi\",\n                        \"value\": 15112\n                    },\n                    \"duration\": {\n                        \"text\": \"29 mins\",\n                        \"value\": 1717\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"1 ft\",\n                        \"value\": 0\n                    },\n                    \"duration\": {\n                        \"text\": \"1 min\",\n                        \"value\": 0\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"12.8 mi\",\n                        \"value\": 20626\n                    },\n                    \"duration\": {\n                        \"text\": \"36 mins\",\n                        \"value\": 2163\n                    },\n                    \"status\": \"OK\"\n                }\n            ]\n        },\n        {\n            \"elements\": [\n                {\n                    \"distance\": {\n                        \"text\": \"11.3 mi\",\n                        \"value\": 18215\n                    },\n                    \"duration\": {\n                        \"text\": \"28 mins\",\n                        \"value\": 1676\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"9.7 mi\",\n                        \"value\": 15684\n                    },\n                    \"duration\": {\n                        \"text\": \"31 mins\",\n                        \"value\": 1858\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"1 ft\",\n                        \"value\": 0\n                    },\n                    \"duration\": {\n                        \"text\": \"1 min\",\n                        \"value\": 0\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"17.8 mi\",\n                        \"value\": 28613\n                    },\n                    \"duration\": {\n                        \"text\": \"28 mins\",\n                        \"value\": 1660\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"14.0 mi\",\n                        \"value\": 22502\n                    },\n                    \"duration\": {\n                        \"text\": \"34 mins\",\n                        \"value\": 2030\n                    },\n                    \"status\": \"OK\"\n                },\n                {\n                    \"distance\": {\n                        \"text\": \"1 ft\",\n                        \"value\": 0\n                    },\n                    \"duration\": {\n                        \"text\": \"1 min\",\n                        \"value\": 0\n                    },\n                    \"status\": \"OK\"\n                }\n            ]\n        }\n    ],\n    \"status\": \"OK\"\n}";
// 	   
 	   //List<Location> destinations = TourathonUtils.parseDestinationsJsonString(body.get("destinations"));
 	   String CurrentLocation = TourathonUtils.obj.readTree(body).get("currentLocation").toString();
 	   System.out.println("");
 	   Location currentlocation = TourathonUtils.parseLocationJsonString(CurrentLocation);
 	   String destinations = TourathonUtils.obj.readTree(body).get("destinations").toString();
 	   JsonNode jsonNodeRoot = TourathonUtils.obj.readTree(destinations);
 	   int noOfSelectedLocations = jsonNodeRoot.size() + 1;
 	   Location[] destinationLocations = new Location[noOfSelectedLocations];
 	   destinationLocations[0] = currentlocation;
 	   for(int i=0; i < noOfSelectedLocations-1; i++) {
 		   destinationLocations[i+1] = TourathonUtils.parseLocationJsonString(jsonNodeRoot.get(i).toString());
 	   }
 	   String jsonOutString = mapApiClient.getDistanceMatrixUsingGoogleApi(destinationLocations);
 	   JsonNode jsonNodeOutRoot = mapper.readTree(jsonOutString);
	   DistanceMatrix distMatrix = new DistanceMatrix();
	   JsonNode rowsList = jsonNodeOutRoot.get("rows");
	   int rowListSize = rowsList.size();
	   Element[][] elementsArray = new Element[rowListSize][rowListSize];
 	   
	   //Element[][] elementsArray = new Element[rowListSize][rowListSize];
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
 	   List<Location> finalList = new ArrayList<>();
 	   int index = 0;
 	   List<Integer> list_index = new ArrayList<Integer>();
 	   list_index.add(0);
 	   for(int i = 1; i < rowListSize; i++) {
 		  index = findNextLocation(elementsArray, elementsArray[index], index, list_index);
 		  list_index.add(index);
 	   }
 	   double tot_distance = 0;
 	   double tot_duration = 0;
 	   for(int i = 0; i< list_index.size()-1; i++)
 	   {
 		  tot_distance += elementsArray[list_index.get(i)][list_index.get(i+1)].getDistance().getValue();		//meters
 		  tot_duration += elementsArray[list_index.get(i)][list_index.get(i+1)].getDuration().getValue();    //minutes
 	   }
 	   for (Integer i : list_index)
 		   finalList.add(destinationLocations[i]);
 	   distMatrix.setElements(elementsArray);
 	   return finalList;
    }
    
    public static int findNextLocation(Element[][] elementsArray, Element[] elementSubArray, int index, List<Integer> indexList) {
 	   int minValue = Integer.MAX_VALUE;
 	   int minIndex = Integer.MAX_VALUE;
 	   for(int i=0; i< elementSubArray.length; i++) {
 		   int tempValue = elementSubArray[i].getDistance().getValue();
 		   if(tempValue < minValue && !indexList.contains(i)) {
 			   minValue = tempValue;
 			   minIndex = i;
 		   }
 	   }
// 	   for(int i = 0; i< elementSubArray.length; i++)
// 	   {
// 		   elementsArray[i][minIndex].getDistance().setValue(Integer.MAX_VALUE);
// 		   elementsArray[minIndex][i].getDistance().setValue(Integer.MAX_VALUE);
// 	   }
 	   return minIndex;
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
