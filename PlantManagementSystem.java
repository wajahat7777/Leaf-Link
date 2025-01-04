package application;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class PlantManagementSystem 
{
	private ArrayList<PlantOwner> users;
	
	private Database db;
	
	public PlantManagementSystem(Database db)
	{
		this.db=db;
		
		users=new ArrayList<PlantOwner>();
	}
	
	public String addUser(String name, String email, String password, int pin)
	{
		boolean found=db.validateUserEmail(email);
		if(!found)
		{
			found=db.validateUserPassword(password);
			if(!found)
			{
				PlantOwner user=new PlantOwner(name, email, password, pin, db);
				db.addUser(user);
			}
			else
				return "Password taken.";
		}
		else
			return "User alerady exists.";
		return "";
	}
	
	public void addPlantSpecie(String name, String Sname, String specie)
	{
		boolean found=db.validatePlantSpecie(specie);
		if(!found)
		{
			db.addPlantSpecie(name, Sname, specie);
		}
		else
			System.out.println("Specie already in record.");
	}
	
	public void addPlantToInventory(int userID, String Sname, String specie, String disease, String leafColor, float height)
	{
		boolean found=db.validateUserID(userID);
		if(found)
		{
			found=db.validatePlantSpecie(specie);
			if(found)
			{
				users=db.getAllUsers(db);
				String name=db.getPlantName(Sname, specie);
				
				for(PlantOwner user: users)
				{
					if(user.getUserID()==userID)
					{
						Plant plant=new Plant(name, Sname, specie, db);
						user.addPlantToInventory(plant, userID, disease, leafColor, height);
					}
				}
			}
			else
				System.out.println("System doesn't work for this specie.");
		}
		else
			System.out.println("User not in record");
	}
	
	public void addFeedback(int userID, String text, int stars)
	{
		boolean found=db.validateUserID(userID);
		if(found)
		{
			users=db.getAllUsers(db);
			for(PlantOwner user: users)
			{
				if(user.getUserID()==userID)
				{
					user.addFeedback(userID, text, stars);
				}
			}
		}
		else
			System.out.println("User not in record.");
	}

	public String pay(int pin, int userID, double amount)
	{
		boolean found=db.validateUserID(userID);
		if(found)
		{
			users=db.getAllUsers(db);
			for(PlantOwner user: users)
			{
				if(user.getUserID()==userID)
				{
					if(user.getPin()==pin)
						return user.pay(userID, amount);
					else
						return "pin invalid";
				}
			}
		}
		else
			return "user not in record.";
		return null;
	}
	
	public ArrayList<ScheduledTask> setTask(int userID, int plantID, String location)
	{
		boolean found=db.validateUserID(userID);
		if(found)
		{
			users=db.getAllUsers(db);
			for(PlantOwner user: users)
			{
				return user.setTask(userID, plantID, location);
			}
		}
		return null;
	}
	
	public String setCustomTask(int userID, int plantID, String taskdesc, LocalDate date)
	{
		boolean found=db.validateUserID(userID);
		if(found)
		{
			users=db.getAllUsers(db);
			for(PlantOwner user: users)
			{
				return user.setCustomTask(userID, plantID, taskdesc, date);
			}
		}
		return null;
	}
	
	public void removeUser(int userID)
	{
		boolean found=db.validateUserID(userID);
		if(found)
		{
			users=db.getAllUsers(db);
			for(PlantOwner user: users)
			{
				if(user.getUserID()==userID)
				{
					user.deleteInventory(userID);
				}
			}
			db.removeUser(userID);
		}
		else
		{
			System.out.println("User not found.");
		}
	}
	
	public void removePlantFromInventory(int userID, int plantID)
	{
		boolean found=db.validateUserID(userID);
		if(found)
		{
			users=db.getAllUsers(db);
			for(PlantOwner user: users)
			{
				if(user.getUserID()==userID)
				{
					user.removePlantFromInventory(userID, plantID);
				}
			}
		}
		else
			System.out.println("User not in record.");
	}

	public void removeTask(int userID, int plantID, ScheduledTask task)
	{
		boolean found=db.validateUserID(userID);
		if(found)
		{
			users=db.getAllUsers(db);
			for(PlantOwner user: users)
			{
				if(user.getUserID()==userID)
				{
					user.removeTask(userID, plantID, task);
				}
			}
		}
		else
			System.out.println("User not in record.");
	}
	
	public void changeChar(int userID, int plantID, String disease, String leafColor, float height)
	{
		boolean found=db.validateUserID(userID);
		if(found)
		{
			users=db.getAllUsers(db);
			for(PlantOwner user: users)
			{
				if(user.getUserID()==userID)
				{
					user.changeChar(userID, plantID, disease, leafColor, height);
				}
			}
		}
		else
			System.out.println("User not in record.");
	}

	public void gatherPlantHealthData(int userID)
	{
		boolean found=db.validateUserID(userID);
		if(found)
		{
			users=db.getAllUsers(db);
			for(PlantOwner user: users)
			{
				if(user.getUserID()==userID)
				{
					user.gatherPlantHealthData(userID);
				}
			}
		}
		else
			System.out.println("User not in record.");
	}
	
	public void getFeedbacks()
	{
		double sum=0;
		int feedbacks=0;
		users=db.getAllUsers(db);
		for(PlantOwner user: users)
		{
			sum+=user.getFeedbacks(user.getUserID());
			feedbacks+=user.getNoOfFeedbacks(user.getUserID());
		}
		double rating=sum/feedbacks;
		System.out.println("Rating: "+rating);
		
	}
	
	public ArrayList<ScheduledTask> taskReminders(int userID, int plantID)
	{
		users=db.getAllUsers(db);
		for(PlantOwner user: users)
		{
			if(user.getUserID()==userID)
				return user.taskReminders(userID, plantID);
		}
		return null;
	}
	
	public String paymentReminders(int userID)
	{
		users=db.getAllUsers(db);
		for(PlantOwner user: users)
		{
			if(user.getUserID()==userID)
				return user.paymentReminders(userID);
		}
		return "User not found.";
	}
	
	public void checkForPayments()
	{
		users=db.getAllUsers(db);
		for(PlantOwner user: users)
		{
			boolean notPaid=user.checkForPayment(user.getUserID());
			if(notPaid)
			{
				removeUser(user.getUserID());
			}
		}
	}
	
	public void heightGraph(int userID, int plantID, LocalDate from, LocalDate to)
	{
		users=db.getAllUsers(db);
		for(PlantOwner user: users)
		{
			if(userID==user.getUserID())
			{
				user.heightGraph(user.getUserID(), plantID, from , to);
			}
		}
	}
}
