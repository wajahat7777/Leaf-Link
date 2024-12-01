package test;
import java.time.LocalDate;

public class ScheduledTask {
    private String plantID;
    private String task;
    private LocalDate date;
    private String location; // New field for location
    Database db= new Database();
    
    //private ScheduledTaskDAO scheduledTaskDAO;
    // Constructor
    public ScheduledTask(String plantID, String task, LocalDate date, String location) {
        this.plantID = plantID;
        this.task = task;
        this.date = date;
        this.location = location;
        db.scheduleTaskForPlant(plantID, task, date, location);
        }

    public String getPlantID() {
        return plantID;
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

    public void setPlantID(String plantID) {
        this.plantID = plantID;
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
}