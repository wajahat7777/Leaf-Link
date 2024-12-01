package test;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlantManagementSystem
{
	//composition
	private ArrayList<PlantOwner> users;
	//composition
	private ArrayList<PlantSpecie> plants;

	private ArrayList<Feedback> feedbacks;
	Database db;

	    public PlantManagementSystem() {
	    this.plants = new ArrayList<>();
	    this.users = new ArrayList<>();
	    this.feedbacks = new ArrayList<>();
	    this.db = new Database(); // Initialize the Database instance
	}

	public boolean validatePlantSpecie(String specie)
	{
		for(PlantSpecie plant:plants)
		{
			if(plant.getSpecie().equalsIgnoreCase(specie))
			{
				return true;
			}
		}
		return false;
	}

	public boolean validateUserEmail(String email)
	{
		for(PlantOwner user: users)
		{
			if(user.getEmail().equals(email))
				return true;
		}
		return false;
	}

	public boolean validateUserPassword(String password)
	{
		for(PlantOwner user: users)
		{
			if(user.getPassword().equals(password))
				return true;
		}
		return false;
	}

	public PlantOwner validateUser(String userID)
	{
		for(PlantOwner user: users)
		{
			if(user.getUserID().equals(userID))
				return user;
		}
		return null;
	}


	public void addPlantInInventory(String userID, String specie, float height, String disease, String leafcolor)
	{
		Plant plant;

		
		String scientificName="";
		String name="";
		
		db.addPlantInInventory(scientificName, specie, height, disease, leafcolor);

	}

	public void checksForTasks()
	{
		db.checkForTasks();
	}

	public void addUser(PlantOwner user)
	{
		users.add(user);
		System.out.println("User added.");
		
	}

	public void removeUser(String userID)
	{
		for(int i=0; i<users.size(); ++i)
		{
			if(users.get(i).getUserID().equals(userID))
			{
				users.remove(i);
				System.out.println("User "+ userID+" removed successfully.");
			}
		}
		db.removeUser(userID);
	}

	public void addPlantSpecie(PlantSpecie plant)
	{
		plants.add(plant);
	}

	

	
	public ArrayList<PlantOwner> getUsers() {
		return users;
	}

	public ArrayList<PlantSpecie> getPlants() {
		return plants;
	}

	public void setUsers(ArrayList<PlantOwner> users) 
	{
		this.users = users;
	}

	public void setPlants(ArrayList<PlantSpecie> plants) 
	{
		this.plants = plants;
	}

	public ArrayList<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(ArrayList<Feedback> feedbacks) 
	{
		this.feedbacks = feedbacks;
	}

	public void scheduleTaskForPlant(String userID, String plantID, String task, LocalDate date, String location)
	{
		boolean foundUser=false;
		boolean foundPlant=false;
		ScheduledTask scheduledTask=new  ScheduledTask( plantID, task, date, location) ;
		
	    return;
			
		
		
	}

}
