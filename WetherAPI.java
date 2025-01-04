package application;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;
import org.json.JSONObject;
//import org.json.JSONArray;
//import org.json.JSONObject;
import java.time.LocalDate;
//import java.net.URI;


public class WetherAPI
{

    // Replace with your weather API URL and API key
	
	private static final String API_KEY = "ca021619cd10e8fa3d47108f6347c28c"; // Replace with your actual API key
	//private static final String API_URL = "api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=a42231d0b22543297af9b87584a6d21e";
   
    public WetherAPI()
    {
   
    }
   
    public ArrayList<ScheduledTask> recommendedTasks(String location) {
        ArrayList<ScheduledTask> tasks = new ArrayList<>();
        try {
            // Step 1: Fetch latitude and longitude for the location
        	String geoUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&APPID=" + API_KEY;
        	System.out.println("Geo Request URL: " + geoUrl);

            URL url = new URL(geoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int geoResponseCode = conn.getResponseCode();
            if (geoResponseCode != 200) {
                throw new RuntimeException("Failed to fetch geo data: " + geoResponseCode);
            }

            StringBuilder geoResponse = new StringBuilder();
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(),Charset.forName("UTF-8")));
            
            
//            Scanner scanner = new Scanner(url.openStream());
            String resData="";
            while ((resData = bf.readLine()) !=null) {
                geoResponse.append(resData);
            }
            bf.close();
            System.out.println("Geo Response: " + geoResponse.toString());
           // String readAPIResponse;
            

           
//            String  x = "{\"coord\":{\"lon\":73.1338,\"lat\":33.7104},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"base\":\"stations\",\"main\":{\"temp\":291.04,\"feels_like\":289.82,\"temp_min\":291.04,\"temp_max\":291.04,\"pressure\":1014,\"humidity\":36,\"sea_level\":1014,\"grnd_level\":932},\"visibility\":10000,\"wind\":{\"speed\":2.8,\"deg\":291,\"gust\":3.31},\"clouds\":{\"all\":62},\"dt\":1733141084,\"sys\":{\"country\":\"PK\",\"sunrise\":1733104510,\"sunset\":1733140744},\"timezone\":18000,\"id\":1162015,\"name\":\"Islamabad\",\"cod\":200}";
//    		String x = "{"
//+ "\"coord\":{\"lon\":73.1338,\"lat\":33.7104},"
//+ "\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],"
//+ "\"base\":\"stations\","
//+ "\"main\":{\"temp\":292.5,\"feels_like\":291.85,\"temp_min\":292.5,\"temp_max\":292.5,\"pressure\":1019,\"humidity\":52,\"sea_level\":1019,\"grnd_level\":937},"
//+ "\"visibility\":10000,"
//+ "\"wind\":{\"speed\":2.29,\"deg\":280,\"gust\":2.19},"
//+ "\"clouds\":{\"all\":10},"
//+ "\"dt\":1733205002,"
//+ "\"sys\":{\"type\":2,\"id\":2007435,\"country\":\"PK\",\"sunrise\":1733190961,\"sunset\":1733227140},"
//+ "\"timezone\":18000,"
//+ "\"id\":1162015,"
//+ "\"name\":\"Islamabad\","
//+ "\"cod\":200"
//+ "}";
//          String quotedString = geoResponse.toString().replace("\"", "\\\"");  
//          
//          System.out.println("Geo Response2: " + quotedString);
//            JSONObject weatherData = new JSONObject(quotedString);
           
          /*  String readAPIResponse;
            
            while((readAPIResponse = x.readLine()) != null){
                jsonString.append(readAPIResponse);
            }
            JSONObject jsonObj = new JSONObject(jsonString.toString());*/
            
            
            
            
            
            
           
            //JSONObject weatherData=new JSONObject();
        
//            JSONObject weatherData = new JSONObject(geoResponse.toString());
            
            
            
            
            
            
            int ind = geoResponse.indexOf("weather");
    		geoResponse = geoResponse.delete(0, ind+7);
    		ind = geoResponse.indexOf("main\":");
    		geoResponse = geoResponse.delete(0, ind+7);
    		ind = geoResponse.indexOf("\",");
    		String weather0 =geoResponse.substring(0, ind);
    		geoResponse = geoResponse.delete(0, ind);
    		ind = geoResponse.indexOf("main\":{");
    		geoResponse = geoResponse.delete(0, ind+7);
    		ind = geoResponse.indexOf("temp\":");
    		geoResponse = geoResponse.delete(0, ind+6);
    		ind = geoResponse.indexOf(",");
    		String temp0 =geoResponse.substring(0, ind);
    		geoResponse = geoResponse.delete(0, ind);
    		ind = geoResponse.indexOf("humidity\":");
    		geoResponse = geoResponse.delete(0, ind+10);
    		ind = geoResponse.indexOf(",");
    		String humidity0 =geoResponse.substring(0, ind);
    		
    		
    		
    		
//            double temperature = weatherData.getJSONObject("main").getDouble("temp");
//            double humidity = weatherData.getJSONObject("main").getDouble("humidity");
//            String weatherCondition = weatherData.getJSONArray("weather").getJSONObject(0).getString("main");

    		  double temperature = Double.parseDouble(temp0);
              double humidity = Double.parseDouble(humidity0);
              String weatherCondition = weather0;
              
            // Generate recommendations based on weather data
            if (temperature > 30) {
                tasks.add(new ScheduledTask("Water the plant thoroughly", LocalDate.now(), "Outdoor"));
            }
            if (humidity < 30) {
                tasks.add(new ScheduledTask("Increase humidity around the plant", LocalDate.now(), "Indoor"));
            }
            if (weatherCondition.equalsIgnoreCase("Rain")) {
                tasks.add(new ScheduledTask("Protect the plant from excessive rain", LocalDate.now(), "Outdoor"));
            }
            if (weatherCondition.equalsIgnoreCase("Snow")) {
                tasks.add(new ScheduledTask("Move the plant indoors to avoid frost damage", LocalDate.now(), "Outdoor"));
            }
            if (weatherCondition.equalsIgnoreCase("Clouds")) {
                tasks.add(new ScheduledTask("Ensure the plant gets enough light indoors", LocalDate.now(), "Indoor"));
            }
            if (weatherCondition.equalsIgnoreCase("Sunny")) {
                tasks.add(new ScheduledTask("Ensure the plant has adequate water", LocalDate.now(), "Outdoor"));
            }
            if (temperature < 10) {
                tasks.add(new ScheduledTask("Move the plant indoors to protect it from cold temperatures", LocalDate.now(), "Outdoor"));
            }

        } catch (Exception e) {
            System.out.println("Error fetching weather data: " + e.getMessage());
            e.printStackTrace();
        }

        return tasks;
    }
}