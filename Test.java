package application;

import org.json.JSONObject;

public class Test {

	public Test()
	{
		
	}
	public static void main(String abc[]) {
		
		
		
		   String  x = "{\"coord\":{\"lon\":73.1338,\"lat\":33.7104},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"base\":\"stations\",\"main\":{\"temp\":291.04,\"feels_like\":289.82,\"temp_min\":291.04,\"temp_max\":291.04,\"pressure\":1014,\"humidity\":36,\"sea_level\":1014,\"grnd_level\":932},\"visibility\":10000,\"wind\":{\"speed\":2.8,\"deg\":291,\"gust\":3.31},\"clouds\":{\"all\":62},\"dt\":1733141084,\"sys\":{\"country\":\"PK\",\"sunrise\":1733104510,\"sunset\":1733140744},\"timezone\":18000,\"id\":1162015,\"name\":\"Islamabad\",\"cod\":200}";
//		   String  x="{\"coord\":{\"lon\":73.1338,\"lat\":33.7104},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":293.62,\"feels_like\":292.92,\"temp_min\":293.62,\"temp_max\":293.62,\"pressure\":1018,\"humidity\":46,\"sea_level\":1018,\"grnd_level\":936},\"visibility\":10000,\"wind\":{\"speed\":2.83,\"deg\":282,\"gust\":2.57},\"clouds\":{\"all\":0},\"dt\":1733208811,\"sys\":{\"type\":2,\"id\":2007435,\"country\":\"PK\",\"sunrise\":1733190961,\"sunset\":1733227140},\"timezone\":18000,\"id\":1162015,\"name\":\"Islamabad\",\"cod\":200}";
		
//		weather:[main:]
//		main{temp, humidity:
				
		
//		String  x =	"{"coord":{"lon":73.1338,"lat":33.7104},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"base":"stations","main":{"temp":293.06,"feels_like":292.39,"temp_min":293.06,"temp_max":293.06,"pressure":1019,"humidity":49,"sea_level":1019,"grnd_level":937},"visibility":10000,"wind":{"speed":2.26,"deg":281,"gust":2.21},"clouds":{"all":10},"dt":1733206586,"sys":{"type":2,"id":2007435,"country":"PK","sunrise":1733190961,"sunset":1733227140},"timezone":18000,"id":1162015,"name":"Islamabad","cod":200}\";
		  
		
		
		
		
		 
		 StringBuilder geoResponse = new StringBuilder(x);
		 
		 
		int ind = geoResponse.indexOf("weather");
		geoResponse = geoResponse.delete(0, ind+7);
		ind = geoResponse.indexOf("main\":");
		geoResponse = geoResponse.delete(0, ind+7);
		ind = geoResponse.indexOf("\",");
		String weather =geoResponse.substring(0, ind);
		geoResponse = geoResponse.delete(0, ind);
		ind = geoResponse.indexOf("main\":{");
		geoResponse = geoResponse.delete(0, ind+7);
		ind = geoResponse.indexOf("temp\":");
		geoResponse = geoResponse.delete(0, ind+6);
		ind = geoResponse.indexOf(",");
		String temp =geoResponse.substring(0, ind);
		geoResponse = geoResponse.delete(0, ind);
		ind = geoResponse.indexOf("humidity\":");
		geoResponse = geoResponse.delete(0, ind+10);
		ind = geoResponse.indexOf(",");
		String humidity =geoResponse.substring(0, ind);
		
		
		
         // Parse weather data
//         JSONObject weatherData = new JSONObject(geoResponse.toString());
         
         System.out.println(geoResponse.toString());
		 
		//return weatherData;
	}
	
	/*public static void main(String[] args)
	{
		
	}*/
}
