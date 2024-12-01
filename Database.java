package test;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private String dbUrl = "jdbc:mysql://localhost:3306/w";
    private String username = "root";
    private String password = "root";

    public Database() {

    }
    Database db;


    // Method to remove a user from the database
    public void removeUser(String userID) {
        String query = "DELETE FROM users WHERE userID = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, userID);
            stmt.executeUpdate();

            System.out.println("User " + userID + " removed from the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to validate user by email
    public boolean validateUserEmail(String email) {
        String query = "SELECT * FROM Users WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to validate user by password
    public boolean validateUserPassword(String password) {
        String query = "SELECT * FROM Users WHERE password = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to validate plant specie
    public boolean validatePlantSpecie(String specie) {
        String query = "SELECT * FROM plant_species WHERE specie = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, specie);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
     private boolean isUserIDValid(Connection connection, String userID) {
    	
        String query = "SELECT * FROM Users WHERE userID = ? LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
            	System.out.println("Owner exists ");
                return rs.next(); // Returns true if at least one record is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Default to false if an exception occurs or no record is found
    }


    public void addPlantInInventory(String userID, String plantSpecie, float height, String disease, String leafColor) {
        String query = "INSERT INTO Plant (plantID, name, scientificName, height, disease, leafColor, userID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            // Check if userID exists
            if (isUserIDValid(connection, userID)) {
                System.out.println("Invalid ownerID: " + userID + ". Cannot add plant.");
                return;
            }

            // Use plantSpecie directly for name and scientificName
            String name = plantSpecie; // Assuming plantSpecie is the common name
            String scientificName = plantSpecie; // Defaulting scientific name to the same value

            // Generate a unique plantID
            String plantID = generateUniquePlantID(connection);

            // Insert the new plant
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, plantID);
                stmt.setString(2, name);
                stmt.setString(3, scientificName);
                stmt.setFloat(4, height);
                stmt.setString(5, disease);
                stmt.setString(6, leafColor);
                stmt.setString(7, userID);

                stmt.executeUpdate();
                System.out.println("Plant added to the inventory with ID: " + plantID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method to remove a plant from the database by its ID
    public boolean removePlant(String plantId) {
        String query = "DELETE FROM Plant WHERE PlantID = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the PlantID parameter
            stmt.setString(1, plantId);

            // Execute the update query
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Plant with ID " + plantId + " has been successfully removed.");
                return true; // Indicates success
            } else {
                System.out.println("No plant found with ID " + plantId);
                return false; // Indicates the plant does not exist
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Indicates failure
        }
    }

    // Helper method to generate a unique plantID
    private String generateUniquePlantID(Connection connection) {
        String query = "SELECT MAX(CAST(SUBSTRING(plantID, 3) AS UNSIGNED)) AS maxID FROM Plant";
        int maxID = 0;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                maxID = rs.getInt("maxID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Increment maxID and format it as P-xxxxx
        return String.format("P-%05d", maxID + 1);
    }

    public void addFeedback(String userID, int stars, String text, LocalDate date) {
        String sql = "INSERT INTO Feedback (userID, stars, text, date) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.setInt(2, stars);
            stmt.setString(3, text);
            stmt.setDate(4, java.sql.Date.valueOf(date));
            stmt.executeUpdate();
            System.out.println("Feedback added to the database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  
    
   private boolean isPlantIDValid(Connection connection, String plantID) {
    	
        String query = "SELECT * FROM Plant WHERE plantID = ? LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, plantID);
            try (ResultSet rs = stmt.executeQuery()) {
            	System.out.println("Plant exists ");
                return rs.next(); // Returns true if at least one record is found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Default to false if an exception occurs or no record is found
    }



           
    
    
    public void scheduleTaskForPlant(String plantID, String task, LocalDate date, String location) {
        String query = "INSERT INTO ScheduledTask (plant_id, task, task_date, location) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {
        	 
            
            // Set the parameters
            stmt.setString(1, plantID);
            stmt.setString(2, task);
            stmt.setDate(3, Date.valueOf(date)); // Convert LocalDate to SQL Date
            stmt.setString(4, location);

            // Execute the query
            stmt.executeUpdate();
            System.out.println("Task scheduled for the plant.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch all feedbacks for a user
    public ArrayList<Feedback> getFeedbacks(String userID) {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT * FROM Feedback WHERE userID = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int stars = rs.getInt("stars");
                    String text = rs.getString("text");
                    LocalDate date = rs.getDate("date").toLocalDate();
                    Feedback feedback = new Feedback(userID,stars, text, date);
                    feedbacks.add(feedback);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbacks;
    }

    // Method to check for today's scheduled tasks
    public void checkForTasks() {
        String query = "SELECT plant_id, task, task_date, location FROM ScheduledTask ";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Scheduled Tasks:");
            while (rs.next()) {
                String plantID = rs.getString("plant_id");
                String task = rs.getString("task");
                Date taskDate = rs.getDate("task_date");
                String location = rs.getString("location");

                System.out.println("Plant ID: " + plantID + ", Task: " + task + ", Date: " + taskDate + ", Location: " + location);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPlantPerformanceReports(String userID) {
        
                    String query = "SELECT * FROM PlantPerformance WHERE plant_id = ?";  // Ensure the query has the placeholder


        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, userID);
            System.out.println("Executing query for User ID: " + userID);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean hasResults = false;
                System.out.println("Plant Performance Reports for User: " + userID);

                while (rs.next()) {
                    hasResults = true;
                    String plantID = rs.getString("plant_id");
                    LocalDate reportDate = rs.getDate("report_date").toLocalDate();
                    String disease = rs.getString("disease");
                    float height = rs.getFloat("height");
                    String leafColor = rs.getString("leaf_color");

                    System.out.println("Plant ID: " + plantID + ", Report Date: " + reportDate + ", Disease: " + disease + 
                                       ", Height: " + height + ", Leaf Color: " + leafColor);
                }

                if (!hasResults) {
                    System.out.println("No performance reports found for User ID: " + userID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


 // Method to gather plant health data and update PlantPerformance
    public void gatherPlantHealthData(String userID) {
        String selectPlantsQuery = "SELECT plantID, name FROM Plant WHERE ownerID = ?";
        String insertPerformanceQuery = """
                INSERT INTO PlantPerformance (plant_id, report_date, disease, height, leaf_color) 
                VALUES (?, CURDATE(), ?, ?, ?)
                ON DUPLICATE KEY UPDATE 
                    disease = VALUES(disease), 
                    height = VALUES(height), 
                    leaf_color = VALUES(leaf_color)
                """;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement selectStmt = connection.prepareStatement(selectPlantsQuery);
             PreparedStatement insertStmt = connection.prepareStatement(insertPerformanceQuery)) {

            // Set the ownerID parameter for the select query
            selectStmt.setString(1, userID);

            try (ResultSet rs = selectStmt.executeQuery()) {
                System.out.println("Gathering health data for user's plants...");

                if (!rs.isBeforeFirst()) { // Check if no plants are found
                    System.out.println("No plants found for User ID: " + userID);
                    return;
                }

                // Iterate over each plant belonging to the user
                while (rs.next()) {
                    String plantID = rs.getString("plantID");
                    String plantName = rs.getString("name");

                    // Simulate health data
                    String disease = simulateDisease(); // Replace with actual logic
                    float height = simulateHeight();    // Replace with actual logic
                    String leafColor = simulateLeafColor(); // Replace with actual logic

                    // Insert or update plant performance data
                    insertStmt.setString(1, plantID);
                    insertStmt.setString(2, disease);
                    insertStmt.setFloat(3, height);
                    insertStmt.setString(4, leafColor);
                    insertStmt.executeUpdate();

                    // Display updated health data in the console
                    System.out.printf("Plant ID: %s | Name: %s | Disease: %s | Height: %.2f cm | Leaf Color: %s%n",
                                      plantID, plantName, disease, height, leafColor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper methods for simulating plant health data
    private String simulateDisease() {
        // Replace with actual logic to determine the plant's disease
        return Math.random() < 0.2 ? "Powdery Mildew" : "None"; // Example logic
    }

    private float simulateHeight() {
        // Replace with actual logic to measure plant height
        return (float) (50 + Math.random() * 100); // Example: random height between 50 and 150 cm
    }

    private String simulateLeafColor() {
        // Replace with actual logic to determine leaf color
        return Math.random() < 0.1 ? "Yellow" : "Green"; // Example logic
    }


 // Method to add a payment record
    public void addPayment(String userID, double amount, LocalDate date) {
        String validateUserQuery = "SELECT COUNT(*) FROM Users WHERE userID = ?";
        String insertPaymentQuery = "INSERT INTO Payment (userID, amount_given, date) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            // Validate if userID exists
            try (PreparedStatement validateStmt = connection.prepareStatement(validateUserQuery)) {
                validateStmt.setString(1, userID);
                try (ResultSet rs = validateStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        System.out.println("Invalid userID: " + userID + ". Cannot record payment.");
                        return;
                    }
                }
            }

            // Add payment record
            try (PreparedStatement stmt = connection.prepareStatement(insertPaymentQuery)) {
                stmt.setString(1, userID);
                stmt.setDouble(2, amount);
                stmt.setDate(3, Date.valueOf(date));
                stmt.executeUpdate();

                System.out.println("Payment recorded successfully for User: " + userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



 // Add User to the Users table
    public void addUser(String name, int pin, String email, String passwordd) {
        String checkQuery = "SELECT 1 FROM Users WHERE email = ?";
        String insertQuery = "INSERT INTO Users (userID, name, pin, email, password, dateRegistered) VALUES (?, ?, ?, ?, ?, NOW())";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            // Check if email already exists
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    
                    return; 
                }
            }

            // Insert the user if email doesn't exist
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                String userId = generateUniqueUserId(); // Implement method to generate a unique ID
                insertStmt.setString(1, userId);
                insertStmt.setString(2, name);
                insertStmt.setInt(3, pin);
                insertStmt.setString(4, email);
                insertStmt.setString(5, passwordd); // Consider hashing the password
                insertStmt.executeUpdate();
                System.out.println("User " + userId + " added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add Plant Species to the PlantSpecies table
    public void addPlantSpecie(String name, String scientificName, String specie) {
        String sql = "INSERT INTO PlantSpecie (name, scientific_name, specie) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, scientificName);
            stmt.setString(3, specie);
            stmt.executeUpdate();
            System.out.println("Plant species added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void paymentChecks() {
        List<PlantOwner> users = getAllUsers(); // Fetch all users from the database

        String sql = "SELECT MAX(date) AS last_payment_date FROM Payment WHERE userID = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (PlantOwner user : users) {
                stmt.setString(1, user.getUserID());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Date lastPaymentDate = rs.getDate("last_payment_date");
                        // Check if lastPaymentDate is null
                        if (lastPaymentDate == null) {
                            // Handle case where no payment has been made yet, e.g. flag the user
                            System.out.println("User " + user.getUserID() + " has not made any payments yet.");
                        } else {
                            LocalDate lastPaymentLocalDate = lastPaymentDate.toLocalDate();
                            LocalDate today = LocalDate.now();
                            if (lastPaymentLocalDate.plusDays(40).isBefore(today)) {
                                System.out.println("User " + user.getUserID() + " is overdue by more than 40 days.");
                                removeUser(user.getUserID());
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 // Method to check if a specific user has overdue payments
    public boolean paymentReminderForUser(String userID) {
        String query = "SELECT u.userID FROM Users u " +
                       "LEFT JOIN Payment p ON u.userID = p.userID " +
                       "WHERE u.userID = ? AND (p.date IS NULL OR p.date < CURDATE() - INTERVAL 30 DAY)";
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("User ID " + userID + " has overdue payments.");
                    return true; // User has overdue payments
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("User ID " + userID + " has no overdue payments.");
        return false; // No overdue payments
    }


 // Collect, display, and calculate average feedback rating
    public void collectsFeedback() {
        String sql = "SELECT userID, stars, text, date FROM Feedback";
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            float sumStars = 0;
            int totalFeedbacks = 0;

            System.out.println("Feedbacks:");
            System.out.println("====================================");

            while (rs.next()) {
                String userId = rs.getString("userID");
                int stars = rs.getInt("stars");
                String text = rs.getString("text");
                LocalDate date = rs.getDate("date").toLocalDate();

                // Print individual feedback
                System.out.println("User ID: " + userId);
                System.out.println("Stars: " + stars);
                System.out.println("Feedback: " + text);
                System.out.println("Date: " + date);
                System.out.println("------------------------------------");

                sumStars += stars;
                totalFeedbacks++;
            }

            // Calculate and print average rating
            if (totalFeedbacks > 0) {
                float averageRating = sumStars / totalFeedbacks;
                System.out.println("====================================");
                System.out.println("The average rating is: " + averageRating);
            } else {
                System.out.println("No feedbacks available to display or calculate the average rating.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Utility method to generate a unique User ID (example logic)
    private String generateUniqueUserId() {
        return "U-" + String.format("%05d", (int) (Math.random() * 100000)); // Generates ID like U-00001
    }

 // Method to get all users from the database (returns a list of PlantOwner)
    private List<PlantOwner> getAllUsers() {
        List<PlantOwner> users = new ArrayList<>();
        String sql = "SELECT userID, name, pin, email, password FROM Users"; // Fetching all necessary columns
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String userId = rs.getString("userID");
                String userName = rs.getString("name");
                int pin = rs.getInt("pin");
                String email = rs.getString("email");
                String password = rs.getString("password");
                // Passing all the parameters to the constructor
                PlantOwner user = new PlantOwner(db,userName, pin, email, password);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Create the Manager table if it doesn't exist
    public void createManagerTableIfNotExist() {
        String createManagerTable = "CREATE TABLE IF NOT EXISTS Manager (" +
                                    "id VARCHAR(50) PRIMARY KEY, " +
                                    "name VARCHAR(255), " +
                                    "role VARCHAR(255))";  // Adding ID, name, and role columns

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createManagerTable);
            System.out.println("Manager table created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void createFeedbackTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS Feedback (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "stars INT NOT NULL CHECK (stars BETWEEN 1 AND 5), " + 
                     "text TEXT NOT NULL, " + 
                     "date DATE NOT NULL, " + 
                     "userID VARCHAR(100));"; 
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Feedback table created (if it did not already exist).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createPaymentTableIfNotExist() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Payment (" +
                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                "amount_given FLOAT, " +
                                "date DATE, " +
                                "userID VARCHAR(100));"; 
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Payment table created (if it did not already exist).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createPlantTableIfNotExist() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Plant (" +
                                "plantID VARCHAR(10) PRIMARY KEY, " +
                                "name VARCHAR(100), " +
                                "scientificName VARCHAR(100), " +
                                "specie VARCHAR(100), " +
                                "height FLOAT, " +
                                "disease VARCHAR(100), " +
                                "leafColor VARCHAR(50), " +
                                "userID VARCHAR(100));"; // Replaced ownerID with ownerName
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Plant table created (if it did not already exist).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    public void createUsersTableIfNotExist() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Users (" +
                                "userID VARCHAR(10) PRIMARY KEY, " +
                                "name VARCHAR(100), " +
                                "email VARCHAR(100) UNIQUE, " +
                                "password VARCHAR(100), " +
                                "pin INT, " +
                                "dateRegistered DATE);";
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Users table created (if it did not already exist).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void createPlantPerformanceIfNotExist() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS PlantPerformance (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " + // Unique identifier for each report
                "plant_id VARCHAR(50) NOT NULL, " +     // ID of the plant
                "report_date DATE NOT NULL, " +         // Date of the report
                "disease VARCHAR(100), " +             // Disease affecting the plant
                "height FLOAT, " +                     // Height of the plant
                "leaf_color VARCHAR(50), " +           // Color of the leaves
                "UNIQUE (plant_id, report_date), " +   // Ensures one report per plant per date
                "plantID VARCHAR(10)" +
                ");";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = connection.createStatement()) {

            stmt.executeUpdate(createTableSQL);
            System.out.println("Table PlantPerformance created (if it did not already exist).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void createPlantSpecie() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS PlantSpecie (" +
                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                "name VARCHAR(100) NOT NULL, " +
                                "scientific_name VARCHAR(150) NOT NULL, " +
                                "specie VARCHAR(100) NOT NULL, " +
                                "plantID VARCHAR(10)" +  // Removed the extra comma here
                                ");";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table PlantSpecie created (if it did not already exist).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTableScheduledTask() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS ScheduledTask (" +
                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                "plant_id VARCHAR(50) NOT NULL, " +
                                "task VARCHAR(255) NOT NULL, " +
                                "task_date DATE NOT NULL, " +
                                "location VARCHAR(150), " +
                                "plantID VARCHAR(10), " +
                                "UNIQUE (plant_id, task_date, task)" + 
                                ");";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("ScheduledTask table created (if it did not already exist).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     
}




