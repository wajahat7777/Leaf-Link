package test;
import java.time.LocalDate;
import java.sql.Connection;

import java.util.ArrayList;

public class Manager 
{
	private String ID;
	private String name;
	private String role;
    //private ManagerDAO managerDAO; // Add this line for the DAO
    Database db = new Database();
	
	private PlantManagementSystem sys= new PlantManagementSystem();
	
	//Constructor
	public Manager() {
        this.ID = ID;
        this.name = name;
        this.role = role;
        this.sys = sys;
        this.db=db;
    }
	
	

	public void addUser(Database db,String name, int pin, String email, String password)
	{
		boolean foundUser=sys.validateUserEmail(email);
		if(!foundUser)
		{
			boolean samepass=sys.validateUserPassword(password);
			if(!samepass)
			{
				PlantOwner user=new PlantOwner(db,name, pin, email, password);
				sys.addUser(user);
				db.addUser(name, pin, email, password);
			}
			else
			{
				System.out.println("Password already taken.");
			}
		}
		else
			System.out.println("User already exists");
		
	}
	
	public void removeUser(String userID)
	{
		PlantOwner found=sys.validateUser(userID);
		if(found!=null)
		{
			sys.removeUser(userID);
		}
		else
			System.out.println("User "+userID+" not in record.");
		db.removeUser(userID);
	}
	
	
	public void paymentChecks()
	{
		for(PlantOwner user: sys.getUsers())
		{
	        ArrayList<Payment> payments = user.getPayments();
	        Payment lastPayment = payments.get(payments.size() - 1);
	        LocalDate lastPaymentDate = lastPayment.getDate();
	        LocalDate today = LocalDate.now();

	        // Check if it's been more than 40 days since the last payment
	        if (lastPaymentDate.plusDays(40).isBefore(today))
	        {
	            System.out.println("User " + user.getUserID() + " last paid on " + lastPaymentDate +
	                    ". Overdue by more than 40 days. Marked for removal.");
	            removeUser(user.getUserID());
	        }
		}
	}
	
	
	public void collectsFeedback()
	{
		float sumStars = 0;
	    int totalFeedbacks = 0; 
	    
	    for(Feedback feedback: sys.getFeedbacks())
	    {
	    	sumStars += feedback.getStars();
	    	totalFeedbacks++; 
	    }
	    
	    if(totalFeedbacks > 0)
	    {
	        float averageRating = sumStars / totalFeedbacks;
	        System.out.println("The average rating is: " + averageRating);
	    }
	    else
	    {
	        System.out.println("No feedbacks available to calculate the average rating.");
	    }
	}
	
}
