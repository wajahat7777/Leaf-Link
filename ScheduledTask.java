package application;

import java.time.LocalDate;

public class ScheduledTask 
{
	private int taskID;
	private String task;
	private LocalDate date;
	private String location;
	private boolean status;
	
	public ScheduledTask(String task, LocalDate date, String location)
	{
		this.task = task;
        this.date = date;
        this.location = location;
        this.status=false;
	}
	
	
	public String getTask() {
		return task;
	}
	public LocalDate getDate() {
		return date;
	}
	public String getLocation() {
		return location;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public void setLocation(String location) {
		this.location = location;
	}


	public int getTaskID() {
		return taskID;
	}


	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
}