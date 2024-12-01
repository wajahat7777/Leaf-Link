package test;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Plant
{
private int plantCounter=1;
private String plantID;
private String name;
private String scientificName;
private String specie;
private ArrayList<PlantPerformanceReport> plantPerformanceReport;
private PlantHeightGraph graph;
private ArrayList<ScheduledTask> tasks;
//private PlantDAO plantDAO;  // Add this line for the DAO
float height;
String disease;
String leafcolor;
Database db = new Database();
//Constructor
public Plant(Database db,String name, String scientificName, String specie, float height, String disease, String leafcolor)
{
this.graph=new PlantHeightGraph(plantPerformanceReport);
this.plantID=String.format("P-%05d", plantCounter++);
this.name=name;
this.scientificName=scientificName;
this.plantPerformanceReport = new ArrayList<>();
PlantPerformanceReport initialReport = new PlantPerformanceReport(LocalDate.now(), disease, height, leafcolor);
this.plantPerformanceReport.add(initialReport);
this.tasks=new ArrayList<ScheduledTask>();
this.specie=specie;
this.tasks = new ArrayList<ScheduledTask>();
//this.plantDAO = new PlantDAO(); // Initialize DAO
this.height=height;
this.disease=disease;
this.leafcolor=leafcolor;
this.db=db;

}
public ArrayList<ScheduledTask> getTasks() {
    return tasks;
}

public void setPlantDisease(String disease)
{
if(!plantPerformanceReport.get(plantPerformanceReport.size()-1).getDisease().equalsIgnoreCase(disease))
{
PlantPerformanceReport report=new PlantPerformanceReport(LocalDate.now(), disease, plantPerformanceReport.get(plantPerformanceReport.size()-1).getHeight(), plantPerformanceReport.get(plantPerformanceReport.size()-1).getLeafcolor());
plantPerformanceReport.add(report);
}
}

public void setPlantHeight(float height)
{
if(plantPerformanceReport.get(plantPerformanceReport.size()-1).getHeight()!=height)
{
PlantPerformanceReport report=new PlantPerformanceReport(LocalDate.now(), plantPerformanceReport.get(plantPerformanceReport.size()-1).getDisease(), height, plantPerformanceReport.get(plantPerformanceReport.size()-1).getLeafcolor());
plantPerformanceReport.add(report);
}
}

public void setPlantLeafColor(String leafcolor)
{
if(!plantPerformanceReport.get(plantPerformanceReport.size()-1).getLeafcolor().equalsIgnoreCase(leafcolor))
{
PlantPerformanceReport report=new PlantPerformanceReport(LocalDate.now(), plantPerformanceReport.get(plantPerformanceReport.size()-1).getDisease(), plantPerformanceReport.get(plantPerformanceReport.size()-1).getHeight(), leafcolor);
plantPerformanceReport.add(report);
}
}

public void checksForTasks()
{
for (int i = 0; i < tasks.size(); i++)
{
       ScheduledTask task = tasks.get(i);
       if (task.getDate().equals(LocalDate.now()))
       {
           System.out.println("Task for " + task.getPlantID() + ": " + task.getTask());
           tasks.remove(i);
       }
   }
}

public void gatherPlantHealthData()
{
plantPerformanceReport.get(plantPerformanceReport.size()-1).displayPlantHeath(plantID);
}

public void growthTrend(String plantID, LocalDate from, LocalDate to)
{
ArrayList<PlantPerformanceReport> filteredReports = new ArrayList<>();
   for (PlantPerformanceReport report : plantPerformanceReport)
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

 
}

public void setTaskSchedule(ScheduledTask scheduledTask) {
   // Get the task details from the ScheduledTask object
   String plantID = scheduledTask.getPlantID();
   String location = scheduledTask.getLocation();
   String taskDesc = scheduledTask.getTask();
   LocalDate taskDate = scheduledTask.getDate();

   // Check if there are recommended tasks based on the plant ID and location
   WetherAPI weather = new WetherAPI(); // Assuming WetherAPI fetches tasks based on weather data
   ArrayList<ScheduledTask> recommendedTasks = weather.recommendedTasks(plantID, location); // Fetch recommended tasks based on plant and location

   if (!recommendedTasks.isEmpty()) {
       System.out.println("-------Recommended tasks------");
       int i = 1;
       for (ScheduledTask task : recommendedTasks) {
           System.out.println(i + ". " + task.getTask());
           i++;
       }
       System.out.println();
       System.out.println("Press 0 to create your own or select a number");

       Scanner scanner = new Scanner(System.in);
       int choice = scanner.nextInt(); // Get the user choice for task selection

       if (choice != 0) {
           // User selects a recommended task
           for (i = 0; i < recommendedTasks.size(); ++i) {
               if (--choice == i) {
                   // Create a new task based on the recommended one
                   ScheduledTask task = new ScheduledTask(plantID, recommendedTasks.get(i).getTask(), LocalDate.now(), location);
                   tasks.add(task); // Add task to the list of tasks
                   System.out.println("Task added: " + task.getTask());
                   db.scheduleTaskForPlant( plantID,recommendedTasks.get(i).getTask(), LocalDate.now(), location);
               }
           }
       } else {
           // User wants to create their own task
           scanner.nextLine(); // Consume the newline character
           System.out.print("Enter task description: ");
           String taskdesc = scanner.nextLine();
           System.out.print("Enter task date (yyyy-mm-dd): ");
           String dateString = scanner.nextLine();
           LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);

           // Create a new task with the location
           ScheduledTask task = new ScheduledTask(plantID, taskdesc, date, location);
           tasks.add(task); // Add task to the list of tasks
           System.out.println("Custom task added: " + task.getTask());
           db.scheduleTaskForPlant(plantID, taskdesc, date, location);
       }
   } else {
       // No recommended tasks available, user must enter their own task
       Scanner scanner = new Scanner(System.in);
       System.out.print("Enter task description: ");
       String taskdesc = scanner.nextLine();
       System.out.print("Enter task date (yyyy-mm-dd): ");
       String dateString = scanner.nextLine();
       LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);

       // Create a new task with the location
       ScheduledTask task = new ScheduledTask(plantID, taskdesc, date, location);
       tasks.add(task); // Add task to the list of tasks
       db.scheduleTaskForPlant(plantID, taskdesc, date, location);

       System.out.println("Custom task added: " + task.getTask());
   }
}


public void generatePlantReport(String plantID)
{
db.viewPlantPerformanceReports(plantID);
}

public String getPlantID() {
return plantID;
}

public void setPlantID(String plantID) {
this.plantID = plantID;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getScientificName() {
return scientificName;
}

public void setScientificName(String scientificName) {
this.scientificName = scientificName;
}

public String getSpecie() {
return specie;
}

public String getDisease() {
return disease;
}

public float getHeight() {
return height;
}
public String getLeafcolor() {
return leafcolor;
}

public void setSpecie(String specie) {
this.specie = specie;
}

public ArrayList<PlantPerformanceReport> getPlantPerformanceReport() {
return plantPerformanceReport;
}

public void setPlantPerformanceReport(ArrayList<PlantPerformanceReport> plantPerformanceReport) {
this.plantPerformanceReport = plantPerformanceReport;
}
}
