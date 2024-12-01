package test;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class PlantOwner 
{
	private int plantCounter = 1; 
	private static int userCounter=1;
	private String userID;
	private String name;
	private String email;
	private String password;
	private int pin;
	private LocalDate dateRegistered;
	//private PlantOwnerDAO plantOwnerDAO;  // Add this line for the DAO
    Database db = new Database();
	//composition
	private PlantInventory inventory;
	//Aggregation
	private ArrayList<Feedback> feedbacks;
	//Aggregation
	private ArrayList<Payment> payments;
	
	private static float amount=1000;
	
	//Constructor
	public PlantOwner(Database db, String name, int pin, String email, String password) {
        this.db = db;
        this.userID = String.format("U-%05d", userCounter++);
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateRegistered = LocalDate.now();
        this.inventory = new PlantInventory();
        this.feedbacks = new ArrayList<>();
        this.payments = new ArrayList<>();
        db.addUser(name, pin, email, password);
    }
	
	public PlantOwner() {
		 this.userID = String.format("U-%05d", userCounter++);
	        this.name = name;
	        this.email = email;
	        this.password = password;
	        this.dateRegistered = LocalDate.now();
	        this.inventory = new PlantInventory();
	        this.feedbacks = new ArrayList<>(); // Initialize the feedback list
	        this.payments = new ArrayList<>();
	        this.db=db;
	        }

	void addPlant(Plant plant)
	{
		String ID = String.format("P-%05d", plantCounter++);
		plant.setPlantID(ID);
		inventory.addPlant(plant);
		System.out.println("Plant added successfully");
		
	}
	
	void removePlant(String plantID)
	{
		inventory.removePlant(plantID);
		
	}
	
	public boolean validatePin(int pin)
	{
		if(pin==this.pin)
			return true;
		return false;
	}
	
	public void enterPaymentDetails(int pin, double amount)
	{
		 System.out.println("Please make your first payment.");
	        System.out.print("Enter amount:");
	        Scanner s=new Scanner(System.in);
	        double Amount =s.nextFloat();
		db.addPayment(userID, Amount, dateRegistered);
        return;

	}
	
	public boolean paymentReminder(String userID) {
	    return db.paymentReminderForUser(userID); // Directly return the result
	}

	public float getAmount(){
		return amount;
	}
	
	public void gatherPlantHealthData()
	{
		inventory.gatherPlantHealthData();
	}
	
	public void growthTrend(String plantID, LocalDate from, LocalDate to)
	{
		boolean found=inventory.validatePlant(plantID);
		if(found)
			inventory.growthTrend(plantID, from, to);
		else
			System.out.println("Plant "+plantID+" not found.");
	}
	
	public void setTaskSchedule(String plantID, String location)
	{
		
			inventory.recommendedTask(plantID, location);
			
		
		
		
	}
	
	public void generatePlantReport(String plantID)
	{
		
			inventory.generatePlantReport(plantID);
		
		
	}

	public void enterFeedback(Database db, String userID, int stars, String text, LocalDate date) {
	    // Validate star rating
	    if (stars < 0 || stars > 5) {
	        System.out.println("Stars should be between 0 to 5.");
	        return;
	    }

	    
	    

	            // Add feedback to the user's feedback list
	            Feedback feedback = new Feedback(userID,stars, text, date);
	            // Add feedback to the system feedback list
	            feedbacks.add(feedback);


	            System.out.println("Thank you for the feedback.");
	     
	   
	}


	
	public String getUserID() {
		return userID;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public int getPin() {
		return pin;
	}

	public PlantInventory getInventory() {
		return inventory;
	}

	public ArrayList<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public ArrayList<Payment> getPayments() {
		return payments;
	}

	

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public void setInventory(PlantInventory inventory) {
		this.inventory = inventory;
	}

	public void setFeedbacks(ArrayList<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public void setPayments(ArrayList<Payment> payments) {
		this.payments = payments;
	}
}


