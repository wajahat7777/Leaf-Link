package test;

import java.util.ArrayList;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;
import java.time.LocalDate;

public class WetherAPI 
{

    // Replace with your weather API URL and API key
    private static final String API_KEY = "855bca7bd563032e43a0262d922461cf"; // Replace with your actual API key
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?appid=" + API_KEY + "&units=metric";
    
    public WetherAPI()
    {
    	
    }
    
    public ArrayList<ScheduledTask> recommendedTasks(String plantID, String location) 
    {
        ArrayList<ScheduledTask> tasks = new ArrayList<>();
        
        try {
            // Construct the full URL with the specified location
            String urlString = API_URL + "&q=" + location;
            
            // Create the connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Check if the response code is 200 (success)
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Failed to fetch weather data: " + responseCode);
            }

            // Read the response
            StringBuilder response = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Parse the JSON response
            JSONObject weatherData = new JSONObject(response.toString());
            double temperature = weatherData.getJSONObject("main").getDouble("temp");
            double humidity = weatherData.getJSONObject("main").getDouble("humidity");
            String weatherCondition = weatherData.getJSONArray("weather").getJSONObject(0).getString("main");

         // Generate recommendations based on weather data
            if (temperature > 30) 
            {
                tasks.add(new ScheduledTask(plantID, "Water the plant thoroughly", LocalDate.now(), "Outdoor"));
            }
            if (humidity < 30)
            {
                tasks.add(new ScheduledTask(plantID, "Increase humidity around the plant", LocalDate.now(), "Indoor"));
            }
            if (weatherCondition.equalsIgnoreCase("Rain"))
            {
                tasks.add(new ScheduledTask(plantID, "Protect the plant from excessive rain", LocalDate.now(), "Outdoor"));
            }
            if (weatherCondition.equalsIgnoreCase("Snow")) 
            {
                tasks.add(new ScheduledTask(plantID, "Move the plant indoors to avoid frost damage", LocalDate.now(), "Outdoor"));
            }
            if (weatherCondition.equalsIgnoreCase("Cloudy"))
            {
                tasks.add(new ScheduledTask(plantID, "Ensure the plant gets enough light indoors", LocalDate.now(), "Indoor"));
            }
            if (weatherCondition.equalsIgnoreCase("Sunny"))
            {
                tasks.add(new ScheduledTask(plantID, "Ensure the plant has adequate water", LocalDate.now(), "Outdoor"));
            }
            if (temperature < 10)
            {
                tasks.add(new ScheduledTask(plantID, "Move the plant indoors to protect it from cold temperatures", LocalDate.now(), "Outdoor"));
            }
           
        } catch (Exception e)
        {
            System.out.println("Error fetching weather data: " + e.getMessage());
            e.printStackTrace();
        }

        return tasks; // Return the list of recommended tasks
    }
}
