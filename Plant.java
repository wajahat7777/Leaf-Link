package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class Plant 
{
	private int plantID;
	private String name;
	private String scientificName;
	private String specie;
	
	WetherAPI api;
	
	ArrayList<PlantPerformanceReport> reports;
	ArrayList<ScheduledTask> tasks;
	PlantHeightGraph hg;
	
	Database db;
	
	public Plant(String name, String Sname, String specie, Database db)
	{
		this.db=db;
		
		api=new WetherAPI();
		
		this.name=name;
		this.scientificName=Sname;
		this.specie=specie;
		
		reports=new ArrayList<PlantPerformanceReport>();
		tasks=new ArrayList<ScheduledTask>();
		hg=new PlantHeightGraph(reports);
	}
	
	public void addToReport(int plantID, int userID, String disease, String leafColor, float height)
	{
		db.addToReport(plantID, userID, disease, leafColor, height);
	}
	
	public ArrayList<ScheduledTask> setTask(int userID, int plantID, String location)
	{
		return tasks=api.recommendedTasks(location);
	}
	
	public String setCustomTask(int userID, int plantID, String taskdesc, LocalDate date)
	{
		ScheduledTask task=new ScheduledTask(taskdesc, date, null);
		return db.setTask(userID, plantID, task);
	}
	
	public ArrayList<ScheduledTask> taskReminders(int userID, int plantID)
	{
		tasks=db.taskReminders(userID, plantID);
		return tasks;
	}
	
	public void removeFromReport(int userID, int plantID)
	{
		db.removeFromReport(userID, plantID);
	}
	
	public void removeTask(int userID, int plantID, ScheduledTask task)
	{
		tasks=db.getScheduledTasks(userID, plantID);
		for(ScheduledTask task1: tasks)
		{
			if(task1.getTaskID()==task.getTaskID())
			{
				db.removeTask(userID, plantID, task);
			}
		}
	}
	
	public void changeChar(int userID, int plantID, String disease, String leafColor, float height)
	{
		db.addToReport(plantID, userID, disease, leafColor, height);
	}
	
	public void gatherPlantHealthData(int userID, int plantID)
	{
		db.gatherPlantHealthData(userID, plantID);
	}
	
	public void heightGraph(int userID, int plantID, LocalDate from, LocalDate to)
	{
		reports=db.getAllReports(userID, plantID);
		ArrayList<PlantPerformanceReport> filteredReports = new ArrayList<>();
	    for (PlantPerformanceReport report : reports) 
	    {
	        if ((report.getDate().isEqual(from) || report.getDate().isAfter(from)) &&
	            (report.getDate().isEqual(to) || report.getDate().isBefore(to))) 
	        {
	            filteredReports.add(report);
	        }
	    }
		
	 // Check if there are reports to display
	    if (filteredReports.isEmpty())
	    {
	        System.out.println("No data available for the specified date range.");
	        return;
	    }

	    // Create and display the graph
	    hg = new PlantHeightGraph(filteredReports);
	    javax.swing.SwingUtilities.invokeLater(() -> {
	        hg.setVisible(true);
	    });
	}
	
	public int getPlantID() {
		return plantID;
	}

	public String getName() {
		return name;
	}

	public String getScientificName() {
		return scientificName;
	}

	public String getSpecie() {
		return specie;
	}

	public void setPlantID(int plantID) {
		this.plantID = plantID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}
}

