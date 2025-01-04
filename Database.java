package application;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Database 
{
	private String dbUrl = "jdbc:mysql://localhost:3306/l?useSSL=false&serverTimezone=UTC";
    private String username = "root";
    private String password = "nimra868";
    
    public Database() 
    {
        createUsersTableIfNotExist();
        createPlantTableIfNotExist();
        createManagerTableIfNotExist();
        createPlantSpecieTableIfNotExist();
        createPlantPerformanceIfNotExist();
        createScheduledTaskTableIfNotExist() ;
        createPaymentTableIfNotExist();
        createFeedbackTableIfNotExist();

    }

    

    
    
    
    //QUERIES
    public PlantOwner getUser(String email, String pass, Database db) {
        PlantOwner user = new PlantOwner(db);

        String query = "SELECT userID, name, email, password, pin, dateRegistered FROM User WHERE email = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);  // Set the email parameter
            pstmt.setString(2, pass);   // Set the password parameter

            // Execute the query using the prepared statement
            ResultSet rs = pstmt.executeQuery();

            // Process the result set
            if (rs.next()) {  // Use if instead of while, since we expect only one user
                int userID = rs.getInt("userID");
                String name = rs.getString("name");
                int pin = rs.getInt("pin");

                // Create a PlantOwner object and populate it with data
                user = new PlantOwner(name, email, pass, pin, db);
                user.setDateRegistered(rs.getDate("dateRegistered").toLocalDate());
                user.setUserID(userID);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching users from database: " + e.getMessage());
        }

        return user;
    }

    public PlantOwner getUserDetails(int userID, Database db)
    {
    	PlantOwner user = new PlantOwner(db);

        String query = "SELECT userID, name, email, password, pin, dateRegistered FROM User WHERE userID = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userID);

            // Execute the query using the prepared statement
            ResultSet rs = pstmt.executeQuery();

            // Process the result set
            if (rs.next()) {
                String name = rs.getString("name");
                String pass=rs.getString("password");
                LocalDate dateRegistred=rs.getDate("dateRegistered").toLocalDate();
                String email=rs.getString("email");
                int pin = rs.getInt("pin");

                // Create a PlantOwner object and populate it with data
                user = new PlantOwner(name, email, pass, pin, db);
                user.setDateRegistered(rs.getDate("dateRegistered").toLocalDate());
                user.setUserID(userID);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching users from database: " + e.getMessage());
        }

        return user;
    }
    
    public ArrayList<PlantOwner> getAllUsers(Database db) 
    {
        ArrayList<PlantOwner> userList = new ArrayList<>();

        String query = "SELECT userID, name, email, password, pin FROM User";

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query))
        {

            while (rs.next())
            {
                int userID = rs.getInt("userID");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int pin = rs.getInt("pin");

                // Create a PlantOwner object and add it to the list
                PlantOwner user = new PlantOwner(name, email, password, pin, db);
                user.setUserID(userID);
                userList.add(user);
            }

        } 
        catch (SQLException e)
        {
            System.err.println("Error fetching users from database: " + e.getMessage());
        }

        return userList;
    }

    public ArrayList<Plant> getAllPlants(int userID, Database db) 
    {
        ArrayList<Plant> plantList = new ArrayList<>();

        String query = "SELECT plantID, name, scientificName, specie FROM Plant WHERE userID = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) 
        {
             
            // Set the userID parameter in the prepared statement
            pstmt.setInt(1, userID);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Loop through the results and populate the plant list
            while (rs.next()) {
                int plantID = rs.getInt("plantID");
                String name = rs.getString("name");
                String scientificName = rs.getString("scientificName");
                String specie = rs.getString("specie");

                // Create a Plant object and add it to the list
                Plant plant = new Plant(name, scientificName, specie, db);
                plant.setPlantID(plantID);
                plantList.add(plant);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching plants for userID " + userID + ": " + e.getMessage());
        }

        return plantList;
    }

    public String getPlantName(String Sname, String specie)
    {
    	String query = "SELECT name FROM PlantSpecie WHERE scientific_name = ? AND specie = ?";
        String plantName = null;

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set parameters in the prepared statement
            pstmt.setString(1, Sname);
            pstmt.setString(2, specie);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Fetch the plant name if the result exists
            if (rs.next()) {
                plantName = rs.getString("name");
            } else {
                System.out.println("No plant found with the given scientific name and species.");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching plant name: " + e.getMessage());
        }

        return plantName;
    }
    
    public int getNextPlantIDForUser(int userID)
    {
        String query = "SELECT MAX(plantID) FROM Plant WHERE userID = ?";
        int nextPlantID = 1; // Default to 1 if no plants exist for this user

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query))
        {

            // Set the userID in the prepared statement
            pstmt.setInt(1, userID);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Check if a plant already exists for this user
            if (rs.next())
            {
                nextPlantID = rs.getInt(1) + 1; // Increment the highest plantID found
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error fetching next plantID for user: " + e.getMessage());
        }

        return nextPlantID;
    }

    public ArrayList<PlantPerformanceReport> getAllReports(int userID, int plantID) 
    {
        ArrayList<PlantPerformanceReport> reports = new ArrayList<>();
        String query = """
                SELECT report_date, disease, height, leaf_color 
                FROM PlantPerformance 
                WHERE u_ID = ? AND p_ID = ?
                ORDER BY report_date DESC
                """;

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) 
        {
            // Set the parameters for userID and plantID
            pstmt.setInt(1, userID);
            pstmt.setInt(2, plantID);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Loop through the results and populate the list
            while (rs.next())
            {
                LocalDate reportDate = rs.getDate("report_date").toLocalDate();
                String disease = rs.getString("disease");
                Double height = rs.getObject("height", Double.class); // Handle null
                String leafColor = rs.getString("leaf_color");

                // Create a PlantPerformanceReport object
                PlantPerformanceReport report = new PlantPerformanceReport(disease, leafColor, height);
                report.setDate(reportDate);

                // Add it to the list
                reports.add(report);
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error fetching reports: " + e.getMessage());
        }

        return reports;
    }
    
    public double getFeedbacks(int userID)
    {
    	String query = """
    	        SELECT SUM(stars) AS total_stars
    	        FROM Feedback
    	        WHERE userID = ?
    	    """;

    	    double totalStars = 0.0;

    	    try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
    	         PreparedStatement pstmt = conn.prepareStatement(query)) {
    	         
    	        // Set the userID parameter
    	        pstmt.setInt(1, userID);

    	        // Execute the query
    	        ResultSet rs = pstmt.executeQuery();

    	        // Retrieve the total stars if the user has provided feedback
    	        if (rs.next()) {
    	            totalStars = rs.getDouble("total_stars");
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace(); // Handle SQL exceptions
    	    }

    	    return totalStars;
    }
    public int getNoOfFeedbacks(int userID)
    {
    	 // SQL query to count the number of feedbacks for the given userID
        String query = """
            SELECT COUNT(*) AS feedback_count
            FROM Feedback
            WHERE userID = ?
        """;

        int feedbackCount = 0;

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            // Set the userID parameter
            pstmt.setInt(1, userID);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Retrieve the feedback count
            if (rs.next()) {
                feedbackCount = rs.getInt("feedback_count");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }

        return feedbackCount;
    }
    
    public ArrayList<ScheduledTask> getScheduledTasks(int userID, int plantID) {
        ArrayList<ScheduledTask> tasks = new ArrayList<>();
        String query = "SELECT id, p_ID, u_ID, task, task_date, location " +
                       "FROM ScheduledTask WHERE u_ID = ? AND p_ID = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the parameters
            pstmt.setInt(1, userID);
            pstmt.setInt(2, plantID);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String task = rs.getString("task");
                LocalDate taskDate = rs.getDate("task_date").toLocalDate();
                String location = rs.getString("location");

                // Add the scheduled task to the list
                ScheduledTask task1=new ScheduledTask(task, taskDate, location);
                task1.setTaskID(id);
                tasks.add(task1);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving scheduled tasks: " + e.getMessage());
        }

        return tasks;
    }

    public ArrayList<Payment> getAllPayments(int userID)
    {
    	// List to hold all payment records
        ArrayList<Payment> payments = new ArrayList<>();

        // SQL query to fetch all payments for the given userID
        String query = """
            SELECT id, amount_given, date
            FROM Payment
            WHERE userID = ?
            ORDER BY date DESC
        """;

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the userID parameter
            pstmt.setInt(1, userID);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the result set and map each row to a Payment object
            while (rs.next()) {
                // Create a new Payment object
                Payment payment = new Payment(rs.getDouble("amount_given"));
                payment.setID(rs.getInt("id"));
                payment.setDate(rs.getDate("date").toLocalDate());
                
                // Add the payment object to the list
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }

        // Return the list of payments
        return payments;
    }
    
    public void gatherPlantHealthData(int userID, int plantID) {
        // SQL query to fetch plant health data for the given plantID and today's date
        String query = """
            SELECT report_date, disease, height, leaf_color
            FROM PlantPerformance
            WHERE p_ID = ? AND u_ID = ? AND report_date = ?
        """;

        // Get today's date
        LocalDate today = LocalDate.now();

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            // Set the parameters for plantID, userID, and today's date
            pstmt.setInt(1, plantID);
            pstmt.setInt(2, userID);
            pstmt.setDate(3, Date.valueOf(today));

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Check if there are results
            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;

                // Retrieve the data
                LocalDate reportDate = rs.getDate("report_date").toLocalDate();
                String disease = rs.getString("disease");
                float height = rs.getFloat("height");
                String leafColor = rs.getString("leaf_color");

                // Print the data
                System.out.println("Report Date: " + reportDate);
                System.out.println("Disease: " + (disease == null ? "None" : disease));
                System.out.println("Height: " + height);
                System.out.println("Leaf Color: " + leafColor);
                System.out.println("--------------------------------");
            }

            // Handle case when no results are found
            if (!hasResults) {
                System.out.println("No health data available for today.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
    }

    
    
    
    
    
    
    
    public boolean validateUserEmail(String email) 
    {
        String query = "SELECT COUNT(*) AS user_count FROM User WHERE email = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) 
        {
             
            // Set the email parameter in the prepared statement
            pstmt.setString(1, email);
            
            // Execute the query
            ResultSet rs = pstmt.executeQuery();
            
            // Check the result and return true if the user exists
            if (rs.next() && rs.getInt("user_count") > 0) 
            {
                return true;
            }
        } 
        catch (SQLException e)
        {
            System.err.println("Error validating user: " + e.getMessage());
        }
        
        // Return false if the user does not exist or in case of an error
        return false;
    }

    public boolean validateUserPassword(String pass) {
        String query = "SELECT COUNT(*) AS user_count FROM User WHERE password = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the password parameter in the prepared statement
            pstmt.setString(1, pass);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Check the result and return true if there is any user with the same password
            if (rs.next() && rs.getInt("user_count") > 0) {
                return true; // Password found for at least one user
            }
        } catch (SQLException e) {
            System.err.println("Error validating password: " + e.getMessage());
        }

        // Return false if no user with the given password is found, or in case of an error
        return false;
    }

    public boolean validateUserID(int userID)
    {
    	String query = "SELECT COUNT(*) AS user_count FROM User WHERE userID = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) 
        {
             
            // Set the email parameter in the prepared statement
            pstmt.setInt(1, userID);
            
            // Execute the query
            ResultSet rs = pstmt.executeQuery();
            
            // Check the result and return true if the user exists
            if (rs.next() && rs.getInt("user_count") > 0) 
            {
                return true;
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error validating user: " + e.getMessage());
        }
        
        // Return false if the user does not exist or in case of an error
        return false;
    }
    
    public boolean validateUser(String email, String pass)
    {
    	String query = "SELECT COUNT(*) AS user_count FROM User WHERE email = ? AND password = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) 
        {
             
            // Set the email parameter in the prepared statement
            pstmt.setString(1, email);
            pstmt.setString(2, pass);
            
            // Execute the query
            ResultSet rs = pstmt.executeQuery();
            
            // Check the result and return true if the user exists
            if (rs.next() && rs.getInt("user_count") > 0) 
            {
                return true;
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error validating user: " + e.getMessage());
        }
        
        // Return false if the user does not exist or in case of an error
        return false;
    }
    
    public boolean validatePlantSpecie(String specie)
    {
    	String query = "SELECT COUNT(*) AS plant_count FROM plantspecie WHERE specie = ?";
        
    	try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
                PreparedStatement pstmt = conn.prepareStatement(query)) 
           {
             
            // Set the email parameter in the prepared statement
            pstmt.setString(1, specie);
            // Execute the query
            ResultSet rs = pstmt.executeQuery();
            
            // Check the result and return true if the user exists
            if (rs.next() && rs.getInt("plant_count") > 0) 
            {
                return true;
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error validating specie: " + e.getMessage());
        }
        
        // Return false if the user does not exist or in case of an error
        return false;
    }
   
    public boolean validatePlant(int userID, int plantID) 
    {
        String query = "SELECT COUNT(*) AS plant_count FROM Plant WHERE userID = ? AND plantID = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query))
        {

            // Set the parameters for the prepared statement
            pstmt.setInt(1, userID); // userID
            pstmt.setInt(2, plantID); // plantID

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Check if a plant exists for this user and plant ID
            if (rs.next() && rs.getInt("plant_count") > 0) 
            {
                return true; // Plant found for the given user and plant ID
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error validating plant: " + e.getMessage());
        }

        // Return false if no plant is found or in case of an error
        return false;
    }
    
    public boolean validateTask(int userID, int plantID, String taskdesc, LocalDate date) 
    {
        String query = "SELECT COUNT(*) AS taskCount FROM ScheduledTask " +
                       "WHERE u_ID = ? AND p_ID = ? AND task = ? AND task_date = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the query parameters
            pstmt.setInt(1, userID);
            pstmt.setInt(2, plantID);
            pstmt.setString(3, taskdesc);
            pstmt.setDate(4, java.sql.Date.valueOf(date)); // Convert LocalDate to java.sql.Date

            // Execute the query
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int taskCount = rs.getInt("taskCount");
                return taskCount > 0; // Returns true if at least one task exists
            }
        } catch (SQLException e) {
            System.err.println("Error validating task: " + e.getMessage());
        }

        return false; // Returns false if no tasks exist or in case of an error
    }

    
    
    
    
    public void addUser(PlantOwner user)
    {
    	String query = """
                INSERT INTO User (name, email, password, pin, dateRegistered)
                VALUES (?, ?, ?, ?, ?)
                """;

        // Get the current date for the dateRegistered column
        LocalDate currentDate = LocalDate.now();
        
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query))
        {

            // Set the parameters for the prepared statement
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setInt(4, user.getPin());
            pstmt.setDate(5, Date.valueOf(currentDate)); 
            
            // Execute the insert query
            int rowsAffected = pstmt.executeUpdate();
            
            // Check if the insertion was successful
            if (rowsAffected > 0) 
            {
                System.out.println("User added successfully.");
            } 
            else 
            {
                System.out.println("Failed to add user.");
            }
            
        } 
        catch (SQLException e) 
        {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }
    
    public void addPlantSpecie(String name, String scientificName, String specie) 
    {
        String query = """
                INSERT INTO PlantSpecie (name, scientific_name, specie)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) 
        {

            // Set the parameters for the prepared statement
            pstmt.setString(1, name);
            pstmt.setString(2, scientificName);
            pstmt.setString(3, specie);

            // Execute the insert query
            int rowsAffected = pstmt.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) 
            {
                System.out.println("Plant species added successfully.");
            } 
            else 
            {
                System.out.println("Failed to add plant species.");
            }

        } 
        catch (SQLException e) 
        {
            System.err.println("Error adding plant species: " + e.getMessage());
        }
    }

    public void addPlantToUserInventory(Plant plant, int userID) 
    {
    	String query = """
                INSERT INTO Plant (plantID, name, scientificName, specie, userID)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) 
        {

            // Set the parameters for the prepared statement
            pstmt.setInt(1, plant.getPlantID());  // plantID
            pstmt.setString(2, plant.getName());   // name
            pstmt.setString(3, plant.getScientificName()); // scientificName
            pstmt.setString(4, plant.getSpecie()); // specie
            pstmt.setInt(5, userID);  // userID

            // Execute the insert query
            int rowsAffected = pstmt.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) 
            {
                System.out.println("Plant added to inventory.");
            }
            else 
            {
                System.out.println("Failed to add plant.");
            }
        } 
        catch (SQLException e)
        {
            System.err.println("Error adding plant to inventory: " + e.getMessage());
        }
    }

    public void addToReport(int plantID, int userID, String disease, String leafColor, float height)
    {
        // Query to fetch the most recent report for the given plantID and userID
        String fetchQuery = """
                SELECT disease, leaf_color, height FROM PlantPerformance
                WHERE p_ID = ? AND u_ID = ?
                ORDER BY report_date DESC LIMIT 1
                """;

        // Insert query to add the new report
        String insertQuery = """
                INSERT INTO PlantPerformance (p_ID, u_ID, report_date, disease, height, leaf_color)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        
        LocalDate currentDate = LocalDate.now();
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password)) 
        {
            // Fetch the most recent report details
            String lastDisease = null;
            String lastLeafColor = null;
            float lastHeight=0;

            try (PreparedStatement fetchStmt = conn.prepareStatement(fetchQuery))
            {
                fetchStmt.setInt(1, plantID);
                fetchStmt.setInt(2, userID);

                ResultSet rs = fetchStmt.executeQuery();
                if (rs.next()) 
                {
                    lastDisease = rs.getString("disease");
                    lastLeafColor = rs.getString("leaf_color");
                    lastHeight=rs.getFloat("height");
                }
            }

            // Use the last known values if the input is null
            if (disease == null) 
            {
                disease = lastDisease;
            }
            if (leafColor == null) 
            {
                leafColor = lastLeafColor;
            }
            if(height==0)
            {
            	height=lastHeight;
            }
            if(height!=0 || disease!=null || leafColor!=null)
            {
            // Insert the new report
            	try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) 
            	{
            		insertStmt.setInt(1, plantID);
            		insertStmt.setInt(2, userID);
            		insertStmt.setDate(3, Date.valueOf(currentDate));
            		insertStmt.setString(4, disease);
            		insertStmt.setObject(5, height); // Use setObject to allow NULL values
            		//insertStmt.setObject(5, (height == 0) ? null : height);
            		insertStmt.setString(6, leafColor);

            		int rowsAffected = insertStmt.executeUpdate();
            		if (rowsAffected > 0)
            		{
            			System.out.println("Report added successfully.");
            		} 
            		else 
            		{
            			System.out.println("Failed to add the report.");
            		}
            	}
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error adding report: " + e.getMessage());
        }
    }

    public void addFeedback(int userID, Feedback feedback) {
        // SQL query to insert feedback into the Feedback table
        String query = """
                INSERT INTO Feedback (stars, text, date, userID)
                VALUES (?, ?, ?, ?);
                """;

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            // Set parameters
            pstmt.setInt(1, feedback.getStars()); // stars
            pstmt.setString(2, feedback.getText()); // text
            pstmt.setDate(3, java.sql.Date.valueOf(feedback.getDateEntered())); // date
            pstmt.setInt(4, userID); // userID

            // Execute the insert operation
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Feedback added successfully.");
            } else {
                System.out.println("Failed to add feedback.");
            }

        } catch (SQLException e) {
            System.err.println("Error adding feedback: " + e.getMessage());
        }
    }

    public void addPayment(int userID, Payment pay)
    {
        // SQL query to insert a new payment into the Payment table
        String query = """
            INSERT INTO Payment (amount_given, date, userID)
            VALUES (?, ?, ?);
        """;
        
        // Execute the query with the provided parameters (amount, date, and userID)
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
                PreparedStatement pstmt = conn.prepareStatement(query))  
        {
            pstmt.setDouble(1, pay.getAmount()); 
            pstmt.setDate(2, java.sql.Date.valueOf(pay.getDate()));
            pstmt.setInt(3, userID);
            
            // Execute the update
            pstmt.executeUpdate();
        } catch (SQLException e) 
        {
            e.printStackTrace(); // Handle any SQL exceptions
        }
    }
    
    public String setTask(int userID, int plantID, ScheduledTask task)
    {
        // SQL query to insert a new task
    	boolean validate=validateTask(userID, plantID, task.getTask(), task.getDate());
    	if(!validate)
    	{
        String query = """
                INSERT INTO ScheduledTask (p_ID, u_ID, task, task_date, location, status)
                VALUES (?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            // Set parameters
            pstmt.setInt(1, plantID); // p_ID
            pstmt.setInt(2, userID); // u_ID
            pstmt.setString(3, task.getTask()); // task
            pstmt.setDate(4, java.sql.Date.valueOf(task.getDate())); // task_date
            pstmt.setString(5, task.getLocation()); // location
            pstmt.setString(6, "pending"); // status

            // Execute the query
            pstmt.executeUpdate();
           // System.out.println("Task set successfully.");
            return "success";
        } catch (SQLException e) {
            System.err.println("Error setting task: " + e.getMessage());
        }
    	}
    	else
    		return "already";
        return null;
    }

    
    
    
        
    
    public void removeUser(int userID) 
    {
        String deleteQuery = "DELETE FROM User WHERE userID = ?";
        String updateQuery = "UPDATE User SET userID = userID - 1 WHERE userID > ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) 
        {
            // Set the userID parameter in the delete query
            deleteStmt.setInt(1, userID);

            // Execute the delete query
            int rowsAffected = deleteStmt.executeUpdate();

            // Check if the deletion was successful
            if (rowsAffected > 0) 
            {
                System.out.println("User removed successfully.");
                
                // After deletion, reorder the remaining users' IDs
                updateStmt.setInt(1, userID); // Reorder users with IDs greater than the deleted user
                updateStmt.executeUpdate();
            } 
            else 
            {
                System.out.println("No user found with the provided userID.");
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error removing user: " + e.getMessage());
        }
    }

    public void removePlantFromInventory(int userID, int plantID) 
    {
        // Delete plant from inventory where userID and plantID match
        String query = "DELETE FROM Plant WHERE userID = ? AND plantID = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) 
        {
            // Set the userID and plantID parameters in the prepared statement
            pstmt.setInt(1, userID);
            pstmt.setInt(2, plantID);

            // Execute the delete query
            int rowsAffected = pstmt.executeUpdate();

            // Check if the deletion was successful
            if (rowsAffected > 0) 
            {
                System.out.println("Plant removed from inventory.");
            } 
            else 
            {
                System.out.println("No plant found with the provided userID and plantID.");
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error removing plant from inventory: " + e.getMessage());
        }
    }

    public void removeFromReport(int userID, int plantID) 
    {
        String query = "DELETE FROM PlantPerformance WHERE u_ID = ? AND p_ID = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) 
        {
            // Set parameters for userID and plantID
            pstmt.setInt(1, userID);
            pstmt.setInt(2, plantID);

            // Execute the DELETE query
            int rowsAffected = pstmt.executeUpdate();

            // Optional: Log the result
            System.out.println(rowsAffected + " report(s) removed for userID: " + userID + ", plantID: " + plantID);
        } 
        catch (SQLException e) 
        {
            System.err.println("Error removing reports: " + e.getMessage());
        }
    }

    public void removeTask(int userID, int plantID, ScheduledTask task) {
        // SQL query to remove a task
        String query = """
                DELETE FROM ScheduledTask
                WHERE p_ID = ? AND u_ID = ? AND task = ? AND task_date = ? AND location = ?;
                """;

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query))
        {
            
            // Set parameters
            pstmt.setInt(1, plantID); // p_ID
            pstmt.setInt(2, userID); // u_ID
            pstmt.setString(3, task.getTask()); // task
            pstmt.setDate(4, java.sql.Date.valueOf(task.getDate()));
            pstmt.setString(5, task.getLocation());
            
            // Execute the delete operation
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) 
            {
                System.out.println("Task removed successfully.");
            } 
            else 
            {
                System.out.println("No task found to remove.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error removing task: " + e.getMessage());
        }
    }

    
    
    public ArrayList<ScheduledTask> taskReminders(int userID, int plantID)
    {
    	// Get today's date
    	ArrayList<ScheduledTask> tasks=new ArrayList<ScheduledTask>();
        LocalDate today = LocalDate.now();
        
        // SQL query to find tasks with a task_date equal to today
        String query = """
            SELECT task, p_ID, task_date
            FROM ScheduledTask
            WHERE u_ID = ? AND p_ID = ? AND task_date = ?
        """;

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
                PreparedStatement pstmt = conn.prepareStatement(query))
           {
            // Set the parameters (userID, plantID, today's date)
            pstmt.setInt(1, userID);
            pstmt.setInt(2, plantID);
            pstmt.setDate(3, java.sql.Date.valueOf(today));

            // Execute the query
            ResultSet rs = pstmt.executeQuery();
            
            // If there are any tasks that match today's date, print them
            
            boolean foundTask = false;
            while (rs.next()) {
                String task = rs.getString("task");
                int taskPlantID = rs.getInt("p_ID");
                LocalDate date=rs.getDate("task_date").toLocalDate();
                ScheduledTask task1=new ScheduledTask(task, date, null);
                tasks.add(task1);
                // Print the task and plant ID
                //System.out.println("Task: " + task + ", Plant ID: " + taskPlantID + ", Date: " + today);
                //return true;
            }
            
            // If no tasks are found, you can print a message indicating no tasks for today
            if (!foundTask) {
                //System.out.println("No tasks scheduled for today.");
            	//return false;
            }
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
            return null;
        }
    }
    
    public String paymentReminders(int userID, Database db)
    {
    	// Get today's date
        LocalDate today = LocalDate.now();
        
        // SQL query to find the most recent payment date for the user
        String query = """
            SELECT MAX(date) AS last_payment_date
            FROM Payment
            WHERE userID = ?
        """;

        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the userID parameter
            pstmt.setInt(1, userID);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Date lastPaymentDate = rs.getDate("last_payment_date");

                if (lastPaymentDate != null) {
                    // Convert the SQL Date to LocalDate
                    LocalDate lastPaymentLocalDate = lastPaymentDate.toLocalDate();

                    // Check if the last payment was exactly a month ago
                    LocalDate oneMonthAgo = today.minusMonths(1);
                    if (lastPaymentLocalDate.isEqual(oneMonthAgo)) {
                        // Print the reminder message
                        //System.out.println("Reminder: Your last payment was on " + lastPaymentLocalDate + ". It's time for a new payment.");
                        //return true;
                    	return "Reminder: Your last payment was on "+lastPaymentLocalDate+". It's time for a new payment.";
                    }
                }if(lastPaymentDate==null)
                {
                	LocalDate oneMonthAgo = today.minusMonths(1);
                	PlantOwner user=getUserDetails(userID, db);
                	if(user.getDateRegistered().isEqual(oneMonthAgo) || user.getDateRegistered().isBefore(oneMonthAgo))
                	{
                		return "Reminder: Make your payment within 10 days.";
                	}
                }
                else {
                    System.out.println("No payments found for this user.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
        return null;
    }
    
    
    
    
    //TABLES
    private void executeQuery(String query)
    {
        try (Connection conn = DriverManager.getConnection(dbUrl, username, password);
             Statement stmt = conn.createStatement()) 
        {
            stmt.executeUpdate(query);
            System.out.println("Table created/verified successfully.");
        } 
        catch (SQLException e) 
        {
            System.err.println("Error executing query: " + e.getMessage());
        }
    }

    private void createUsersTableIfNotExist() 
    {
        String query = """
                CREATE TABLE IF NOT EXISTS User (
                    userID INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    password VARCHAR(100) NOT NULL,
                    pin INT NOT NULL,
                    dateRegistered DATE
                );
                """;
        executeQuery(query);
    }

    private void createManagerTableIfNotExist()
    {
        String query = """
                CREATE TABLE IF NOT EXISTS Manager (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    role VARCHAR(100) NOT NULL
                );
                """;
        executeQuery(query);
    }

    private void createFeedbackTableIfNotExist() 
    {
        String query = """
                CREATE TABLE IF NOT EXISTS Feedback (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    stars TINYINT CHECK (stars BETWEEN 0 AND 5),
                    text TEXT,
                    date DATE,
                    userID INT,
                    FOREIGN KEY (userID) REFERENCES User(userID)
                );
                """;
        executeQuery(query);
    }

    private void createPaymentTableIfNotExist()
    {
        String query = """
                CREATE TABLE IF NOT EXISTS Payment (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    amount_given FLOAT NOT NULL,
                    date DATE,
                    userID INT,
                    FOREIGN KEY (userID) REFERENCES User(userID)
                );
                """;
        executeQuery(query);
    }

    private void createPlantTableIfNotExist() 
    {
        String query = """
                CREATE TABLE IF NOT EXISTS Plant (
                    plantID INT DEFAULT 1,
                    name VARCHAR(100) NOT NULL,
                    scientificName VARCHAR(150),
                    specie VARCHAR(100),
                    userID INT,
                    FOREIGN KEY (userID) REFERENCES User(userID),
                    UNIQUE KEY (plantID, userID)
                );
                """;
        executeQuery(query);
    }

    private void createPlantPerformanceIfNotExist() 
    {
        String query = """
                CREATE TABLE IF NOT EXISTS PlantPerformance (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    p_ID INT,
                    u_ID INT,
                    report_date DATE,
                    disease VARCHAR(100),
                    height FLOAT,
                    leaf_color VARCHAR(100),
                    FOREIGN KEY (p_ID) REFERENCES Plant(plantID),
                    FOREIGN KEY (u_ID) REFERENCES Plant(userID)
                );
                """;
        executeQuery(query);
    }

    private void createPlantSpecieTableIfNotExist() 
    {
        String query = """
                CREATE TABLE IF NOT EXISTS PlantSpecie (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    scientific_name VARCHAR(150),
                    specie VARCHAR(100)
                );
                """;
        executeQuery(query);
    }

    private void createScheduledTaskTableIfNotExist()
    {
        String query = """
                CREATE TABLE IF NOT EXISTS ScheduledTask (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    p_ID INT,
                    u_ID INT,
                    task VARCHAR(100) NOT NULL,
                    task_date DATE,
                    location VARCHAR(100),
                    status VARCHAR(20) DEFAULT 'pending',
                    UNIQUE (p_ID, u_ID, task, task_date),
                    FOREIGN KEY (p_ID) REFERENCES Plant(plantID),
                    FOREIGN KEY (u_ID) REFERENCES User(userID)
                );
                """;
        executeQuery(query);
    }
    
}