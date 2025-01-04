package application;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class PlantInventory 
{	
	private ArrayList<Plant> plants;
	
	Database db;
	
	public PlantInventory(Database db)
	{
		this.db=db;
		
		plants=new ArrayList<Plant>();
	}
	
	public void addPlantToInventory(Plant plant, int userID, String disease, String leafColor, float height)
	{	
	    int plantID = db.getNextPlantIDForUser(userID);
	    plant.setPlantID(plantID);
	    db.addPlantToUserInventory(plant, userID);
	    
	    plants=db.getAllPlants(userID, db);
	    for(Plant plantt:plants)
	    {
	    	if(plantt.getPlantID()==plant.getPlantID())
	    	{
	    		plantt.addToReport(plant.getPlantID(), userID, disease, leafColor, height);
	    	}
	    }
	}
	
	public ArrayList<ScheduledTask> setTask(int userID, int plantID, String location)
	{
		plants=db.getAllPlants(userID, db);
		for(Plant plant: plants)
		{
			if(plant.getPlantID()==plantID)
			{
				return plant.setTask(userID, plantID, location);
			}
		}
		return null;
	}
	
	public String setCustomTask(int userID, int plantID, String taskdesc, LocalDate date)
	{
		plants=db.getAllPlants(userID, db);
		for(Plant plant: plants)
		{
			if(plant.getPlantID()==plantID)
			{
				return plant.setCustomTask(userID, plantID, taskdesc, date);
			}
		}
		return null;
	}
	
	public void removePlantFromInventory(int userID, int plantID)
	{
		boolean found=db.validatePlant(userID, plantID);
		if(found)
		{
			plants=db.getAllPlants(userID, db);
			for(Plant plant: plants)
			{
				plant.removeFromReport(userID, plantID);
			}
			db.removePlantFromInventory(userID, plantID);
		}
		else
			System.out.println("User doesn't own this plant.");
	}
	
	public void deleteInventory(int userID)
	{
		plants=db.getAllPlants(userID, db);
		for(Plant plant: plants)
		{
			plant.removeFromReport(userID, plant.getPlantID());
			db.removePlantFromInventory(userID, plant.getPlantID());
		}
	}
	
	public void removeTask(int userID, int plantID, ScheduledTask task)
	{
		plants=db.getAllPlants(userID, db);
		for(Plant plant: plants)
		{
			plant.removeTask(userID, plantID, task);
		}
	}
	
	public void changeChar(int userID, int plantID, String disease, String leafColor, float height)
	{
		boolean found=db.validatePlant(userID, plantID);
		if(found)
		{
			plants=db.getAllPlants(userID, db);
			for(Plant plant: plants)
			{
				plant.changeChar(userID, plantID, disease, leafColor, height);
			}
		}
		else
			System.out.println("Plant not owned by user.");
	}
	
	public void gatherPlantHealthData(int userID)
	{
		plants=db.getAllPlants(userID, db);
		for(Plant plant: plants)
		{
				plant.gatherPlantHealthData(userID, plant.getPlantID());
		}
	}
	
	public ArrayList<ScheduledTask> taskReminders(int userID, int plantID)
	{
		plants=db.getAllPlants(userID, db);
		for(Plant plant: plants)
		{
			if(plantID==plant.getPlantID())
				return plant.taskReminders(userID, plant.getPlantID());
		}
		return null;
	}
	
	public void heightGraph(int userID, int plantID, LocalDate from, LocalDate to)
	{
		plants=db.getAllPlants(userID, db);
		for(Plant plant: plants)
		{
			if(plantID==plant.getPlantID())
				plant.heightGraph(userID, plantID, from, to);
		}
	}
}
