package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class test {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/w";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) { // Ensures the scanner is closed
            PlantManagementSystem pms = new PlantManagementSystem();
            Manager manager = new Manager();
            Database db = new Database();
            PlantOwner user = new PlantOwner();
            System.out.println("Welcome to the Plant Management System!");

            boolean running = true;
            while (running) {
                System.out.println("\nMenu:");
                System.out.println("1. Add User");
                System.out.println("2. Remove User");
                System.out.println("3. Add Plant");
                System.out.println("4. Add Plant to User Inventory");
                System.out.println("5. Schedule Task for a Plant");
                System.out.println("6. Enter Feedback");
                System.out.println("7. View All Feedbacks");
                System.out.println("8. Generate Plant Performance Report");
                System.out.println("9. Remove Plant");
                System.out.println("10. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter user name: ");
                        String userName = scanner.nextLine();
                        System.out.print("Enter PIN: ");
                        int pin = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        manager.addUser(db, userName, pin, email, password);
                    }
                    case 2 -> {
                        System.out.print("Enter User ID to remove: ");
                        String userID = scanner.nextLine();
                        pms.removeUser(userID);
                    }
                    case 3 -> {
                        System.out.print("Enter plant name: ");
                        String plantName = scanner.nextLine();
                        System.out.print("Enter scientific name: ");
                        String scientificName = scanner.nextLine();
                        System.out.print("Enter plant species: ");
                        String species = scanner.nextLine();
                        pms.addPlantSpecie(new PlantSpecie(db, plantName, scientificName, species));
                    }
                    case 4 -> {
                        System.out.print("Enter User ID: ");
                        String userId = scanner.nextLine();
                        System.out.print("Enter plant species: ");
                        String plantSpecie = scanner.nextLine();
                        System.out.print("Enter plant height: ");
                        float height = scanner.nextFloat();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter disease (if any) or 'none': ");
                        String disease = scanner.nextLine();
                        System.out.print("Enter leaf color: ");
                        String leafColor = scanner.nextLine();
                        pms.addPlantInInventory(userId, plantSpecie, height, disease, leafColor);
                    }
                    case 5 -> {
                        System.out.print("Enter User ID: ");
                        String userId = scanner.nextLine();
                        System.out.print("Enter Plant ID: ");
                        String plantId = scanner.nextLine();
                        System.out.print("Enter task description: ");
                        String taskDescription = scanner.nextLine();
                        System.out.print("Enter task date (yyyy-mm-dd): ");
                        LocalDate taskDate = LocalDate.parse(scanner.nextLine());
                        System.out.print("Enter location for the task: ");
                        String location = scanner.nextLine();
                        pms.scheduleTaskForPlant(userId, plantId, taskDescription, taskDate, location);
                    }
                    case 6 -> {
                        System.out.print("Enter User ID: ");
                        String userId = scanner.nextLine();
                        System.out.print("Enter stars (0 to 5): ");
                        int stars = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter feedback text: ");
                        String feedbackText = scanner.nextLine();
                        user.enterFeedback(db, userId, stars, feedbackText, LocalDate.now());
                    }
                    case 7 -> {
                        System.out.println("Feedbacks:");
                        db.collectsFeedback();
                    }
                    case 8 -> {
                        System.out.print("Enter Plant ID: ");
                        String plantID = scanner.nextLine();
                        user.generatePlantReport(plantID);
                    }
                    case 9 -> {
                        System.out.print("Enter Plant ID to remove: ");
                        String plantId = scanner.nextLine();
                        user.removePlant(plantId);
                        System.out.println("Plant with ID " + plantId + " has been removed.");
                    }
                    case 10 -> {
                        running = false;
                        System.out.println("Exiting the Plant Management System. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }

                // Skip these checks if exiting
                if (running) {
                    pms.checksForTasks();
                    System.out.print("Enter User ID for payment check: ");
                    String checkUserId = scanner.nextLine();
                    boolean overdue = user.paymentReminder(checkUserId);
                    if (overdue) {
                        System.out.print("You have overdue payments. Enter amount to pay: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        db.addPayment(checkUserId, amount, LocalDate.now());
                    } else {
                        System.out.println("No overdue payments for User ID: " + checkUserId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
