package test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class PlantInventory
{
private ArrayList<Plant> plants;
Database db= new Database();
//Constructor
PlantInventory()
{
this.plants=new ArrayList<Plant>();
}

void addPlant(Plant plant)
{
plants.add(plant);
}

void removePlant(String plantID)
{
   db.removePlant(plantID);
}

public void gatherPlantHealthData()
{
for(Plant plant: plants)
{
plant.gatherPlantHealthData();
}
}

public void checksForTasks()
{
for(Plant plant: plants)

plant.checksForTasks();

}


public void growthTrend(String plantID, LocalDate from, LocalDate to)
{
for(Plant plant:plants)
{
plant.growthTrend(plantID, from, to);
}
}

public void recommendedTask(String plantID, String location)
{
   for (Plant plant : plants) {
       if (plant.getPlantID().equals(plantID)) {
           // Create a new ScheduledTask with the given plantID and location
           ScheduledTask scheduledTask = new ScheduledTask(plantID, "Recommended task based on location", LocalDate.now(), location);

           // Pass the ScheduledTask to setTaskSchedule method
           plant.setTaskSchedule(scheduledTask);
           }
   }
}


public void generatePlantReport(String plantID)
{

	db.viewPlantPerformanceReports(plantID);
}

public boolean validatePlant(String plantID)
{
for(Plant plant: plants)
{
if(plant.getPlantID().equals(plantID))
{
return true;
}
}
return false;
}

public ArrayList<Plant> getPlants() {
return plants;
}

public void setPlants(ArrayList<Plant> plants) {
this.plants = plants;
}
}