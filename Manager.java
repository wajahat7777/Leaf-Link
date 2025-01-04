package application;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Manager
{
	private String ID;
	private String name;
	private String role;
	
	private Database db;
	
	private PlantManagementSystem sys;
	
	Manager(String id, String name, String role, Database db)
	{
		this.db=db;
		
		this.ID=id;
		this.name=name;
		this.role=role;
		
		this.sys=new PlantManagementSystem(db);
	}
	
	public String addUser(String name, String email, String password, int pin)
	{
		return sys.addUser(name, email, password, pin);
	}
	
	public void addPlantSpecie(String name, String Sname, String specie)
	{
		sys.addPlantSpecie(name, Sname, specie);
	}
	
	public void addPlantToInventory(int userID, String Sname, String specie, String disease, String leafColor, float height)
	{
		sys.addPlantToInventory(userID, Sname, specie, disease, leafColor, height);
	}
	
	public void addFeedback(int userID, String text, int stars)
	{
		sys.addFeedback(userID, text, stars);
	}
	
	public String pay(int pin, int userID, double amount)
	{
		return sys.pay(pin, userID, amount);
	}
	
	public ArrayList<ScheduledTask> setTask(int userID, int plantID, String location)
	{
		return sys.setTask(userID, plantID, location);
	}
	
	public String setCustomTask(int userID, int plantID, String taskdesc, LocalDate date)
	{
		return sys.setCustomTask(userID, plantID, taskdesc, date);
	}
	
	
	
	
	public void removeUser(int userID)
	{
		sys.removeUser(userID);
	}
	
	public void removePlantFromInentory(int userID, int plantID)
	{
		sys.removePlantFromInventory(userID, plantID);
	}

	public void removeTask(int userID, int plantID, ScheduledTask task)
	{
		sys.removeTask(userID, plantID, task);
	}
	
	
	
	
	public void changeChar(int userID, int plantID, String disease, String leafColor, float height)
	{
		sys.changeChar(userID, plantID, disease, leafColor, height);
	}

	public void gatherPlantHealthData(int userID)
	{
		sys.gatherPlantHealthData(userID);
	}
	
	public void collectsFeedback()
	{
		sys.getFeedbacks();
	}
	
	public ArrayList<ScheduledTask> taskReminders(int userID, int plantID)
	{
		return sys.taskReminders(userID, plantID);
	}
	
	public String paymentReminders(int userID)
	{
		return sys.paymentReminders(userID);
	}
	
	public void checkForPayments()
	{
		sys.checkForPayments();
	}
	
	public void heightGraph(int userID, int plantID, LocalDate from, LocalDate to)
	{
		sys.heightGraph(userID, plantID, from , to);
	}
}
