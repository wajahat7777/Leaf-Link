package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class PlantOwner 
{
	private int userID;
	private String name;
	private String email;
	private String password;
	private int pin;
	private LocalDate dateRegistered;
	
	private PlantInventory inventory;
	private ArrayList<Payment> payments;
	
	Database db;
	
	public PlantOwner(Database db)
	{
		this.db=db;
	}
	
	public PlantOwner(String name, String email, String password, int pin, Database db)
	{
		this.db=db;
		
		this.name=name;
		this.email=email;
		this.password=password;
		this.pin=pin;
		this.dateRegistered=LocalDate.now();
		
		this.inventory=new PlantInventory(db);
		this.payments=new ArrayList<Payment>();
	}

	public void addPlantToInventory(Plant plant, int userID, String disease, String leafColor, float height)
	{	
		inventory.addPlantToInventory(plant, userID, disease, leafColor, height);
	}
	
	public void addFeedback(int userID, String text, int stars)
	{
		Feedback feedback=new Feedback(text, stars);
		db.addFeedback(userID, feedback);
	}
	
	public String pay(int userID, double amount)
	{
		payments = db.getAllPayments(userID);

		// Check if there are any payments
		if (payments.isEmpty()) {
		    // No previous payments, check if the user registered 30 days ago or more
		    LocalDate registrationDate = dateRegistered; // Assuming db has a method to get registration date

		    // Get the current date
		    LocalDate currentDate = LocalDate.now();

		    // Calculate the difference in days between the registration date and the current date
		    long daysBetween = ChronoUnit.DAYS.between(registrationDate, currentDate);

		    // Check if the user registered 30 days ago or more
		    if (daysBetween >= 30) 
		    {
		        // Proceed with payment
		        Payment payment = new Payment(amount);
		        db.addPayment(userID, payment);
		        return "paid";
		    }
		} else {
		    // Get the date of the last payment
		    LocalDate lastPaymentDate = payments.get(payments.size() - 1).getDate();

		    // Get the current date
		    LocalDate currentDate = LocalDate.now();

		    // Calculate the difference in days between the last payment and the current date
		    long daysBetween = ChronoUnit.DAYS.between(lastPaymentDate, currentDate);

		    // Check if the last payment was made 30 days ago or more
		    if (daysBetween >= 30) {
		        // Proceed with payment
		        Payment payment = new Payment(amount);
		        db.addPayment(userID, payment);
		        return "paid";
		    }
		}
		return "no";
	}
	
	public ArrayList<ScheduledTask> setTask(int userID, int plantID, String location)
	{
		return inventory.setTask(userID, plantID, location);
	}
	
	public String setCustomTask(int userID, int plantID, String taskdesc, LocalDate date)
	{
		return inventory.setCustomTask(userID, plantID, taskdesc, date);
	}
	
	public void removePlantFromInventory(int userID, int plantID)
	{
		inventory.removePlantFromInventory(userID, plantID);
	}
	
	public void deleteInventory(int userID)
	{
		inventory.deleteInventory(userID);
	}
	
	public void removeTask(int userID, int plantID, ScheduledTask task)
	{
		inventory.removeTask(userID, plantID, task);
	}
	
	public void changeChar(int userID, int plantID, String disease, String leafColor,float height)
	{
		inventory.changeChar(userID, plantID, disease, leafColor, height);
	}
	
	public void gatherPlantHealthData(int userID)
	{
		inventory.gatherPlantHealthData(userID);
	}
	
	public ArrayList<ScheduledTask> taskReminders(int userID, int plantID)
	{
		return inventory.taskReminders(userID, plantID);
	}
	
	public String paymentReminders(int userID)
	{
		return db.paymentReminders(userID, db);
	}
	
	public boolean checkForPayment(int userID)
	{
		payments=db.getAllPayments(userID);
		LocalDate lastPaymentDate = payments.get(payments.size() - 1).getDate();
		LocalDate currentDate = LocalDate.now();
		
		long daysBetween = ChronoUnit.DAYS.between(lastPaymentDate, currentDate);

		// Check if the last payment was made exactly 41 days ago
		if (daysBetween == 41)
		{
		    System.out.println("User "+userID+" is signed out.");
		    return true;
		} 
		else
		{
		    return false;
		}
			
	}
	
	public double getFeedbacks(int userID)
	{
		return db.getFeedbacks(userID);
	}
	
	public int getNoOfFeedbacks(int userID)
	{
		return db.getNoOfFeedbacks(userID);
	}
	
	public void heightGraph(int userID, int plantID, LocalDate from, LocalDate to)
	{
		inventory.heightGraph(userID, plantID, from, to);
	}
	
	public int getUserID() {
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

	public void setUserID(int userID) {
		this.userID = userID;
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

	public LocalDate getDateRegistered() {
		return dateRegistered;
	}

	public PlantInventory getInventory() {
		return inventory;
	}

	public ArrayList<Payment> getPayments() {
		return payments;
	}

	public void setDateRegistered(LocalDate dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	public void setInventory(PlantInventory inventory) {
		this.inventory = inventory;
	}

	public void setPayments(ArrayList<Payment> payments) {
		this.payments = payments;
	}
}
