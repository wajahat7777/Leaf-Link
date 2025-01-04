package application;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.*;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class Main extends Application 
{
	private static final String DB_URL = "jdbc:mysql://localhost:3306/l?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "nimra868";
    @Override
    public void start(Stage primaryStage) 
    {
    	try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded!");

            // Try to connect to the database
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                System.out.println("Database connected successfully!");

                // Create objects as needed
                Database db = new Database();
                Manager man = new Manager("M-101", "manager", "manager", db);
                
                
              /*  man.addPlantSpecie("Rose", "Rosa rugosa", "Beach Rose");
                man.addPlantSpecie("Rose", "Rosa gallica", "French Rose");
                man.addPlantSpecie("Rose", "Rosa chinensis", "China Rose");
                man.addPlantSpecie("Rose", "Rosa hybrida", "Hybrid Tea Rose");
                man.addPlantSpecie("Tulip", "Tulipa gesneriana", "Common Tulip");
                man.addPlantSpecie("Tulip", "Tulipa clusiana", "Persian Tulip");
                man.addPlantSpecie("Lily", "Lilium longiflorum", "Easter Lily");
                man.addPlantSpecie("Lily", "Lilium orientalis", "Oriental Lily");
                man.addPlantSpecie("Cactus", "Opuntia ficus-indica", "Prickly Pear Cactus");
                man.addPlantSpecie("Cactus", "Cereus peruvianus", "Peruvian Apple Cactus");
                man.addPlantSpecie("Sunflower", "Helianthus annuus", "Common Sunflower");
                man.addPlantSpecie("Sunflower", "Helianthus tuberosus", "Jerusalem Artichoke");
                man.addPlantSpecie("Daisy", "Bellis perennis", "English Daisy");
                man.addPlantSpecie("Daisy", "Leucanthemum vulgare", "Shasta Daisy");
                man.addPlantSpecie("Oak", "Quercus robur", "English Oak");
                man.addPlantSpecie("Oak", "Quercus alba", "White Oak");
                man.addPlantSpecie("Maple", "Acer rubrum", "Red Maple");
                man.addPlantSpecie("Maple", "Acer saccharum", "Sugar Maple");
                man.addPlantSpecie("Clover", "Trifolium repens", "White Clover");
                man.addPlantSpecie("Clover", "Trifolium pratense", "Red Clover");*/
                
                
              //  Login(primaryStage, db, man);
               // Inventory(primaryStage, new Database(), 1, man);
             // DashBoard(primaryStage, 1, man, db);
                
                //System.out.println("-------Remaining--------------");
                System.out.println("1. Add plant");
                System.out.println("2. Graph pattern ");
                System.out.println("3. Gather plant health data.");
                System.out.println("4. System performance report.");
                System.out.println("5. Change height");
                System.out.println("6. Change disease.");
                System.out.println("7. Change leaf color.");
                System.out.println("0. Exit.");
                Scanner s=new Scanner(System.in);
                boolean enter=true;
                while(enter)
                {
                	int choice=s.nextInt();
                	
                	switch(choice)
                	{
                	case 1:
                		System.out.print("Enter userID: ");
                		int userID = s.nextInt();
                		s.nextLine(); // Consume the leftover newline

                		System.out.print("Enter Sname: ");
                		String name = s.nextLine();

                		System.out.print("Enter specie: ");
                		String specie = s.nextLine(); // Use nextLine() to read the entire input including spaces

                		System.out.print("Enter height: ");
                		float height = s.nextFloat();
                		s.nextLine(); // Consume the leftover newline

                		System.out.print("Enter disease (or none): ");
                		String disease = s.nextLine();

                		System.out.print("Enter leaf color: ");
                		String leafColor = s.nextLine();
                		man.addPlantToInventory(userID, name, specie, disease, leafColor, height);
                		break;
                	case 2:
                		System.out.print("Enter userID: ");
                		userID=s.nextInt();
                		System.out.print("Enter plantID: ");
                		int plantID=s.nextInt();
                		System.out.print("Enter from: ");
                		String from=s.next();
                		System.out.print("Enter to: ");
                		String to=s.next();
                		man.heightGraph(userID, plantID, LocalDate.parse(from), LocalDate.parse(to));	
                		break;
                	case 3:
                		System.out.println("Enter userID: ");
                		userID=s.nextInt();
                		
                		man.gatherPlantHealthData(userID);
                		break;
                	case 4:
                		man.collectsFeedback();
                		break;
                	case 5:
                		System.out.println("Enter userID: ");
                		userID=s.nextInt();
                		System.out.println("Enter plantID:");
                		plantID=s.nextInt();
                		System.out.println("Enter hieght: ");
                		height=s.nextFloat();
                		man.changeChar(userID, plantID, null, null, height);
                		break;
                	case 6:
                		System.out.println("Enter userID: ");
                		userID=s.nextInt();
                		System.out.println("Enter plantID:");
                		plantID=s.nextInt();
                		System.out.println("Enter disease: ");
                		disease = s.next();
                		man.changeChar(userID, plantID, disease, null, 0);
                		break;
                	case 7:
                		System.out.println("Enter userID: ");
                		userID=s.nextInt();
                		System.out.println("Enter plantID:");
                		plantID=s.nextInt();
                		System.out.println("Enter leaf color: ");
                		leafColor = s.next();
                		man.changeChar(userID, plantID, null, leafColor, 0);
                		break;
                	case 0:
                		enter=false;
                	}
                }
                return;
               
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }
    
    public void Login(Stage primaryStage, Database db, Manager man) 
    {
        // Create a GridPane layout for the login form
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);"); // Semi-transparent background for form
        grid.setVgap(10);
        grid.setHgap(10);

        // Create labels
        Label emailLabel = new Label("Email:");
        emailLabel.getStyleClass().add("email-label");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Email");

        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("password-label");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Create a Button for login
        Button loginButton = new Button("Login");

        // Link to the SignUp page
        Button signUpLink = new Button("Don't have an account?");
        signUpLink.setStyle("-fx-background-color: transparent; -fx-text-fill: blue; -fx-font-size: 14px; -fx-padding: 0; -fx-underline: true;");
        signUpLink.setMaxWidth(Double.MAX_VALUE);
        
        // Add the labels, username, password fields, and button to the GridPane
        grid.add(emailLabel, 0, 0);        // Email label at row 0, column 0
        grid.add(usernameField, 1, 0);     // Username field at row 0, column 1
        grid.add(passwordLabel, 0, 1);     // Password label at row 1, column 0
        grid.add(passwordField, 1, 1);     // Password field at row 1, column 1
        grid.add(loginButton, 1, 2);       // Login button at row 2, column 1
        grid.add(signUpLink, 1, 3);        // Sign up link button at row 3, column 1

        // Set an action for the login button
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        grid.add(errorLabel, 1, 4); // Add the error label to the grid

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Clear previous error messages and styles
            errorLabel.setText("");
            usernameField.setStyle(null);
            passwordField.setStyle(null);

            // Validate the fields
            if (username.isEmpty() || password.isEmpty()) {
                if (username.isEmpty()) {
                    usernameField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                }
                if (password.isEmpty()) {
                    passwordField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                }
                errorLabel.setText("Fields should not be empty");
            } else {
                boolean foundUser=db.validateUser(username, password);
                if (!foundUser) {
                    // If user is not found, display the error message
                    errorLabel.setText("Either email is not found or password is incorrect.");
                } else {
                    // Proceed with login success (You can add code to transition to another page here)
                	PlantOwner user=db.getUser(username, password, db);
                	DashBoard(primaryStage, user.getUserID(), man, db);
                }
            }
        });

        // Set action for the Sign Up link
        signUpLink.setOnAction(e -> {
            SignUp(primaryStage, man, db); // Switch to the SignUp page
        });

        // Set the scene with the GridPane
        Scene scene = new Scene(grid, 600, 500);
        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());

        // Set the stage (window) properties
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void SignUp(Stage primaryStage, Manager man, Database db) {
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);"); // Semi-transparent background for form
        grid.setVgap(10);
        grid.setHgap(10);

        // Create Labels and Fields
        Label nameLabel = new Label("Name:");
        nameLabel.getStyleClass().add("email-label");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        Label emailLabel = new Label("Email:");
        emailLabel.getStyleClass().add("email-label");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("email-label");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        Label pinLabel = new Label("Pin:");
        pinLabel.getStyleClass().add("email-label");
        TextField pinField = new TextField();
        pinField.setPromptText("Pin");

        // Create a Button for Sign Up
        Button signUpButton = new Button("Sign Up");

        // Create a Label for displaying error messages
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;"); // Error text in red

        // Add components to the GridPane
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(emailLabel, 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(pinLabel, 0, 3);
        grid.add(pinField, 1, 3);
        grid.add(signUpButton, 1, 4);
        grid.add(errorLabel, 1, 5);

        // Add action for the Sign Up button
        signUpButton.setOnAction(e -> {
            // Get input values
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String pin = pinField.getText();

            // Clear previous error messages and styles
            errorLabel.setText("");
            nameField.setStyle(null);
            emailField.setStyle(null);
            passwordField.setStyle(null);
            pinField.setStyle(null);

            // Validation logic
            boolean isValid = true;

            if (name.isEmpty()) {
                nameField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                isValid = false;
            }
            if (email.isEmpty()) {
                emailField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                isValid = false;
            }
            if (password.isEmpty()) {
                passwordField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                isValid = false;
            }
            if (pin.isEmpty()) {
                pinField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                isValid = false;
            } else {
                try {
                	//Integer. parseInt(pin);
                    String added=man.addUser(name, email, password, Integer. parseInt(pin));
                    if(added.equalsIgnoreCase(""))
                    	Login(primaryStage, db, man);
                    else if(added.equalsIgnoreCase("User alerady exists."))
                    {
                    	errorLabel.setText("User alerady exists.");
                    	errorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 16px;");
                    }
                    else if (added.equalsIgnoreCase("Password taken."))
                    {
                    	errorLabel.setText("Password taken.");
                    	errorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 16px;");
                    }
                } catch (NumberFormatException ex) {
                    pinField.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                    errorLabel.setText("Pin must be a numeric value.");
                    isValid = false;
                }
            }
        });

        // Create a scene and set the stage
        Scene scene = new Scene(grid, 600, 500);
        scene.getStylesheets().add(getClass().getResource("signUp.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sign Up Page");
        primaryStage.show();
    }


    public void DashBoard(Stage primaryStage, int userID, Manager man, Database db) 
    {
        // Root container
        BorderPane root = new BorderPane();

        // Sidebar container
        VBox sidebar = new VBox(10);
        sidebar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-padding: 10px; -fx-background-radius: 10px;");
        sidebar.setPrefWidth(200);
        sidebar.setAlignment(Pos.TOP_LEFT);

        // Sidebar buttons
        Button inventoryButton = new Button("Inventory");
        inventoryButton.setStyle("-fx-background-radius: 10px;");
        Button feedbackButton = new Button("Feedback");
        feedbackButton.setStyle("-fx-background-radius: 10px;");
        Button paymentButton = new Button("Payment");
        paymentButton.setStyle("-fx-background-radius: 10px;");
        sidebar.getChildren().addAll(inventoryButton, feedbackButton, paymentButton);
        sidebar.setTranslateX(-250); // Start hidden (offscreen)

        // Hamburger button
        Button hamburgerButton = new Button("☰");
        hamburgerButton.setStyle("-fx-font-size: 18px; -fx-background-color: transparent; -fx-text-fill: white;");
        HBox hamburgerBox = new HBox(hamburgerButton);
        hamburgerBox.setAlignment(Pos.CENTER_LEFT);
        hamburgerBox.setStyle("-fx-padding: 10px;");
        hamburgerButton.setTranslateX(-340);

        // Sidebar toggle animation
        hamburgerButton.setOnAction(e -> {
            TranslateTransition toggleTransition = new TranslateTransition(Duration.millis(300), sidebar);
            if (sidebar.getTranslateX() < 0) {
                toggleTransition.setToX(0);
            } else {
                toggleTransition.setToX(-250);
            }
            toggleTransition.play();
        });
        inventoryButton.setOnAction(e -> {
            Inventory(primaryStage, new Database(), userID, man); // Navigate to Inventory screen
        });
        feedbackButton.setOnAction(e -> {
        	Feedback(primaryStage, db, userID, man); // Placeholder for feedback functionality
        });
        paymentButton.setOnAction(e -> {
        	payment(primaryStage, db, man, userID); // Placeholder for payment functionality
        });
        PlantOwner user = db.getUserDetails(userID, db);
        String userName = user.getName();
        String userEmail = user.getEmail(); // Assuming PlantOwner class has a getEmail() method
        ArrayList<Plant> plants = db.getAllPlants(userID, db);
        int plantCount = plants.size();

        String yesPayment = man.paymentReminders(userID); // Check for payment reminders

        // Fetch all tasks
        ArrayList<String> taskDetails = new ArrayList<>();
        for (Plant plant : plants) {
            ArrayList<ScheduledTask> tasks = man.taskReminders(userID, plant.getPlantID());
            for (ScheduledTask task : tasks) {
                taskDetails.add("Plant ID: " + plant.getPlantID() + ", Task: " + task.getTask()); // Assuming ScheduledTask has a getTask() method
            }
        }

        // User Details Section
        VBox userDetailsSection = createInfoSection("User Details", new String[]{
            "Welcome, " + userName,
            "Email: " + userEmail,
            "Number of Plants: " + plantCount
        });

        // Payment and Tasks Section
        ArrayList<String> taskSectionDetails = new ArrayList<>();
        if (yesPayment!=null) {
            taskSectionDetails.add(yesPayment);
        } else {
            taskSectionDetails.add("No payment reminders.");
        }
        if (taskDetails.isEmpty()) {
            taskSectionDetails.add("All your plants are in good condition!");
        } else {
            taskSectionDetails.addAll(taskDetails);
        }

        VBox paymentTaskSection = createInfoSection("Payment and Tasks", taskSectionDetails.toArray(new String[0]));

        // Main content container
        VBox contentContainer = new VBox(20);
        contentContainer.setStyle("-fx-padding: 20px;");
        contentContainer.getChildren().addAll(userDetailsSection, paymentTaskSection);

        // Add components to the root
        root.setTop(hamburgerBox);
        root.setLeft(sidebar);
        root.setCenter(contentContainer);

        // Create and set the scene
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
        primaryStage.show();
    }

    // Helper method to create styled sections
    private VBox createInfoSection(String header, String[] details) {
        VBox section = new VBox(10);
        section.setStyle("-fx-background-color: #4A90E2; -fx-background-radius: 10px; -fx-padding: 15px;");

        // Header label
        Label headerLabel = new Label(header);
        headerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Details container
        VBox detailsContainer = new VBox(5);
        detailsContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-padding: 10px;");
        for (String detail : details) {
            Label detailLabel = new Label(detail);
            if (detail.startsWith("Welcome")) {
                detailLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #000;");
            } else {
                detailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #000;");
            }
            detailsContainer.getChildren().add(detailLabel);
        }

        section.getChildren().addAll(headerLabel, detailsContainer);
        return section;
    }

    public void Inventory(Stage primaryStage, Database db, int userID, Manager man) {
        ArrayList<Plant> plants = db.getAllPlants(userID, db);

        // Create a GridPane layout for the inventory page
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
        grid.setVgap(10);
        grid.setHgap(10);

        // Create a TableView to display plants
        TableView<Plant> plantTable = new TableView<>();

        // Create columns for plant ID, name, scientific name, and specie
        TableColumn<Plant, String> IDcol = new TableColumn<>("ID");
        IDcol.setCellValueFactory(new PropertyValueFactory<>("plantID"));

        TableColumn<Plant, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Plant, String> scientificNameColumn = new TableColumn<>("Scientific Name");
        scientificNameColumn.setCellValueFactory(new PropertyValueFactory<>("scientificName"));

        TableColumn<Plant, String> specieColumn = new TableColumn<>("Specie");
        specieColumn.setCellValueFactory(new PropertyValueFactory<>("specie"));

        // Create a column for actions (drop-down menu with buttons)
        TableColumn<Plant, Void> actionsColumn = new TableColumn<>("Actions");

        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final MenuButton menuButton = new MenuButton("Actions");
            private final MenuItem deleteItem = new MenuItem("Delete");
            private final MenuItem setTaskItem = new MenuItem("Set Task");

            {
                deleteItem.setOnAction(e -> {
                    Plant plant = getTableView().getItems().get(getIndex());
                    man.removePlantFromInentory(userID, plant.getPlantID());
                    plantTable.getItems().remove(plant);
                });

                setTaskItem.setOnAction(e -> {
                    // Handle task setting
                	Plant plant = getTableView().getItems().get(getIndex());
                	setTask(primaryStage, db, userID, man, plant.getPlantID());
                });

                menuButton.getItems().addAll(deleteItem, setTaskItem);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(menuButton);
                }
            }
        });

        // Add columns to the table
        plantTable.getColumns().addAll(IDcol, nameColumn, scientificNameColumn, specieColumn, actionsColumn);

        // Add plants to the TableView
        plantTable.getItems().setAll(plants);

        // Add the TableView to the GridPane
        grid.add(plantTable, 0, 0);

        // Create a Back button to go back to the Dashboard
        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> DashBoard(primaryStage, userID, man, db));
        grid.add(backButton, 0, 1);

        // Set the scene and show the stage
        Scene scene = new Scene(grid, 800, 600); // Adjusted size for better layout
        scene.getStylesheets().add(getClass().getResource("inventory.css").toExternalForm()); // Link the CSS stylesheet
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inventory");
        primaryStage.show();
    }

    public void setTask(Stage primaryStage, Database db, int userID, Manager man, int plantID) {
        // Create a new VBox layout for the interface
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("layout"); // Add CSS class for layout styling

        // Create a label to display instructions
        Label instructions = new Label("Enter Location to Fetch Recommended Tasks:");
        instructions.getStyleClass().add("instructions"); // Add CSS class for styling
        layout.getChildren().add(instructions);

        // Create a TextField for location input
        TextField locationField = new TextField();
        locationField.setPromptText("Enter location (e.g., city or area)");
        locationField.getStyleClass().add("text-field"); // Add CSS class
        layout.getChildren().add(locationField);

        // Create a button to fetch recommended tasks
        Button fetchTasksButton = new Button("Fetch Tasks");
        fetchTasksButton.getStyleClass().add("button-fetch"); // Add CSS class
        layout.getChildren().add(fetchTasksButton);

        // Create a ListView to display recommended tasks
        ListView<String> taskListView = new ListView<>();
        taskListView.setDisable(true); // Initially disabled until tasks are fetched
        taskListView.getStyleClass().add("list-view"); // Add CSS class
        layout.getChildren().add(taskListView);

        // Create a TextField for custom task input
        TextField customTaskField = new TextField();
        customTaskField.setPromptText("Enter custom task if not listed");
        customTaskField.getStyleClass().add("text-field"); // Add CSS class
        layout.getChildren().add(customTaskField);

        // Create a DatePicker for the task date
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Task Date");
        datePicker.getStyleClass().add("date-picker"); // Add CSS class
        layout.getChildren().add(datePicker);

        // Create buttons for "Submit" and "Cancel"
        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getStyleClass().add("button-layout"); // Add CSS class

        Button submitButton = new Button("Submit Task");
        submitButton.getStyleClass().add("button-submit"); // Add CSS class
        submitButton.setDisable(true); // Initially disabled until tasks are fetched

        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("button-cancel"); // Add CSS class

        buttonLayout.getChildren().addAll(submitButton, cancelButton);
        layout.getChildren().add(buttonLayout);

        // Handle the "Fetch Tasks" button action
        fetchTasksButton.setOnAction(e -> {
            String location = locationField.getText();
            if (location == null || location.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Location Required");
                alert.setContentText("Please enter a location to fetch recommended tasks.");
                alert.showAndWait();
            } else {
                WetherAPI api = new WetherAPI(); // Assuming WeatherAPI is initialized properly
                ArrayList<ScheduledTask> recommendedTasks = api.recommendedTasks(location);

                // Populate the ListView with recommended tasks
                taskListView.getItems().clear();
                for (ScheduledTask task : recommendedTasks) {
                    taskListView.getItems().add(task.getTask());
                }

                taskListView.setDisable(false);
                submitButton.setDisable(false);
            }
        });

        // Handle the "Submit" button action
        submitButton.setOnAction(e -> {
            //String selectedTask = taskListView.getSelectionModel().getSelectedItem();
            String customTask = customTaskField.getText();
            LocalDate date = datePicker.getValue();

            if ((customTask == null || customTask.isEmpty()) || date == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Task Assignment Failed");
                alert.setContentText("Please select or enter a task and choose a date.");
                alert.showAndWait();
                return;
            }

            // Use the selected or custom task
            //String taskDescription = (selectedTask != null) ? selectedTask : customTask;

            // Validate and assign the task
           // boolean taskExists = db.validateTask(userID, plantID, taskDescription, date);
            String taskExists = man.setCustomTask(userID, plantID, customTask, date);
            if (taskExists.equalsIgnoreCase("already")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Duplicate Task");
                alert.setHeaderText("Task Already Exists");
                alert.setContentText("The specified task is already assigned to this plant.");
                alert.showAndWait();
            } else {
                //ScheduledTask task = new ScheduledTask(taskDescription, date, locationField.getText());
                //db.setTask(userID, plantID, task);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Task Assigned");
                alert.setContentText("The task has been successfully assigned to the plant.");
                alert.showAndWait();

                // Navigate back to the inventory page
                Inventory(primaryStage, db, userID, man);
            }
        });

        // Handle the "Cancel" button action
        cancelButton.setOnAction(e -> Inventory(primaryStage, db, userID, man));

        // Set the scene and show the interface
        Scene scene = new Scene(layout, 600, 500);
        scene.getStylesheets().add(getClass().getResource("task.css").toExternalForm()); // Load external CSS
        primaryStage.setScene(scene);
        primaryStage.setTitle("Set Task");
        primaryStage.show();
    }

    public void Feedback(Stage primaryStage, Database db, int userID, Manager man)
    {
        VBox mainLayout = new VBox(20);
        mainLayout.setStyle("-fx-background-color: #F4F7FC; -fx-padding: 30px;");
        mainLayout.setAlignment(Pos.CENTER);

        // Title
        Label title = new Label("Give Feedback");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Rating Section: Stars (Using RadioButtons for ratings)
        Label ratingLabel = new Label("How would you rate your search experience?");
        ratingLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        ToggleGroup starGroup = new ToggleGroup();
        HBox starBox = new HBox(10);
        starBox.setAlignment(Pos.CENTER);

        RadioButton star1 = createStarButton(starGroup, "1");
        RadioButton star2 = createStarButton(starGroup, "2");
        RadioButton star3 = createStarButton(starGroup, "3");
        RadioButton star4 = createStarButton(starGroup, "4");
        RadioButton star5 = createStarButton(starGroup, "5");

        starBox.getChildren().addAll(star1, star2, star3, star4, star5);

        // Comment Section
        Label commentLabel = new Label("What are the main reasons for your rating?");
        commentLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        TextArea commentField = new TextArea();
        commentField.setPromptText("Write your feedback here...");
        commentField.setPrefRowCount(4);
        commentField.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // Consent Checkbox
        CheckBox consentCheckBox = new CheckBox("I agree to provide this feedback.");
        consentCheckBox.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        // Buttons for Submit and Cancel
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        submitButton.setStyle("-fx-background-color: #D3A4FF; -fx-text-fill: white;");
        cancelButton.setStyle("-fx-background-color: #FFB5B5; -fx-text-fill: white;");

        // Action for Submit Button
        submitButton.setOnAction(e -> {
            if (starGroup.getSelectedToggle() == null) {
                man.addFeedback(userID, commentField.getText(), 0);
            } else if (!consentCheckBox.isSelected()) {
                showAlert("Please agree to provide feedback.");
            } else {
                // Get the selected star rating (it is stored as the RadioButton userData)
                int stars = Integer.parseInt(((RadioButton) starGroup.getSelectedToggle()).getUserData().toString());
                
                // Get the feedback comment
                String feedback = commentField.getText();
                
                // Save feedback to the database via Manager class
                man.addFeedback(userID, feedback, stars);  // Adjust this based on your Manager class method
                
                showAlert("Feedback Submitted:\nRating: " + stars + "\nComment: " + feedback);
            }
        });

        // Action for Cancel Button
        cancelButton.setOnAction(e -> DashBoard(primaryStage, userID, man, db));

        // Add all elements to the main layout
        mainLayout.getChildren().addAll(title, ratingLabel, starBox, commentLabel, commentField,
                consentCheckBox, buttonBox);
        buttonBox.getChildren().addAll(submitButton, cancelButton);

        // Set the scene and stage
        Scene scene = new Scene(mainLayout, 500, 400);
        primaryStage.setTitle("Feedback Form");
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to create a RadioButton for a star
    private RadioButton createStarButton(ToggleGroup group, String value) 
    {
        RadioButton starButton = new RadioButton("☆");
        starButton.setStyle("-fx-font-size: 24px; -fx-text-fill: #FFD700;");
        starButton.setToggleGroup(group);
        starButton.setUserData(value);
        return starButton;
    }

    // Helper method to show an alert
    private void showAlert(String message) 
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void payment(Stage primaryStage, Database db, Manager man, int userID) {
        // Create a VBox layout for the payment page
        VBox mainLayout = new VBox(20);
        mainLayout.setStyle("-fx-background-color: #F4F7FC; -fx-padding: 30px;");
        mainLayout.setAlignment(Pos.CENTER);

        // Title
        Label title = new Label("Payment Page");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Amount Input Section
        Label amountLabel = new Label("Enter the amount to pay:");
        amountLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        
        TextField amountField = new TextField();
        amountField.setPromptText("Enter Amount");
        amountField.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-text-fill: black;");

        // PIN Input Section
        Label pinLabel = new Label("Enter your PIN number:");
        pinLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        
        TextField pinField = new TextField();
        pinField.setPromptText("Enter PIN");
        pinField.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-text-fill: black;");

        // Error Label (will display if there are validation errors)
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Submit Button
        Button submitButton = new Button("Submit Payment");
        submitButton.setStyle("-fx-background-color: #D3A4FF; -fx-text-fill: white;");
        
        // Action for Submit Button
        submitButton.setOnAction(e -> {
            String enteredAmount = amountField.getText();
            String enteredPin = pinField.getText();
            String success=man.pay(Integer.parseInt(enteredPin), userID, Double.parseDouble(enteredAmount));
            
            // Clear previous error messages
            errorLabel.setText("");
            
            // Validate the amount (ensure it's not empty and is a positive number)
            if (enteredAmount.isEmpty()) {
                errorLabel.setText("Please enter an amount.");
            } else {
                try {
                    double amount = Double.parseDouble(enteredAmount);
                    if (amount <= 0) {
                        errorLabel.setText("Please enter a valid amount greater than 0.");
                    }else if(amount <1000) {
                    	errorLabel.setText("Amount must be 1000 or above.");
                    }else if (enteredPin.isEmpty()) {
                        errorLabel.setText("Please enter your PIN.");
                    } else if (success.equalsIgnoreCase("pin invalid")) {
                        errorLabel.setText("Incorrect PIN. Please try again.");
                    }else if(success.equalsIgnoreCase("no")){
                    	errorLabel.setText("No payment needed");
                    }
                    else {
                        // Handle successful payment (e.g., update payment status, deduct amount)
                       // boolean paymentSuccessful = man.pay(userID, amount, enteredPin);
                        
                        //if (paymentSuccessful) {
                            showAlert("Payment of $" + amount + " was successful!");
                        //} else {
                       //     showAlert("Payment Failed. Please try again later.");
                      //  }
                    }
                } catch (NumberFormatException ex) {
                    errorLabel.setText("Amount must be a valid number.");
                }
            }
        });

        // Add all elements to the main layout
        mainLayout.getChildren().addAll(title, amountLabel, amountField, pinLabel, pinField, submitButton, errorLabel);

        // Set the scene and stage
        Scene scene = new Scene(mainLayout, 400, 400);
        scene.getStylesheets().add(getClass().getResource("dashboard.css").toExternalForm());
        primaryStage.setTitle("Payment Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    
    


    
    public static void main(String[] args)
    {
    	launch(args);
    }
}
