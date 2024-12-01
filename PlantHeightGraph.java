package test;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.sql.*;
import java.util.ArrayList;

public class PlantHeightGraph extends JFrame {
    private ArrayList<PlantPerformanceReport> reports;

    // Constructor to initialize the graph with reports
    public PlantHeightGraph(ArrayList<PlantPerformanceReport> reports) {
        this.reports = reports;

        setTitle("Plant Growth Trend");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        // Margin for the graph
        int margin = 50;

        // Draw axes
        g2d.drawLine(margin, getHeight() - margin, getWidth() - margin, getHeight() - margin); // X-axis
        g2d.drawLine(margin, getHeight() - margin, margin, margin); // Y-axis

        // Labels
        g2d.drawString("Date", getWidth() / 2, getHeight() - margin / 2);
        g2d.drawString("Height", margin / 2, getHeight() / 2);

        // Calculate bar width
        int barWidth = (getWidth() - 2 * margin) / reports.size();

        // Maximum height for scaling
        float maxHeight = 0;
        for (PlantPerformanceReport report : reports) {
            if (report.getHeight() > maxHeight) {
                maxHeight = report.getHeight();
            }
        }

        // Draw bars
        for (int i = 0; i < reports.size(); i++) {
            PlantPerformanceReport report = reports.get(i);
            int barHeight = (int) ((report.getHeight() / maxHeight) * (getHeight() - 2 * margin));
            int x = margin + i * barWidth;
            int y = getHeight() - margin - barHeight;

            // Draw the bar
            g2d.setColor(Color.GREEN);
            g2d.fillRect(x, y, barWidth - 10, barHeight);

            // Add labels
            g2d.setColor(Color.BLACK);
            g2d.drawString(report.getDate().toString(), x, getHeight() - margin + 15);
            g2d.drawString(String.valueOf(report.getHeight()), x + barWidth / 4, y - 5);
        }
    }

    // Method to fetch plant performance reports from the database
    public static ArrayList<PlantPerformanceReport> fetchReportsFromDatabase(String plantID) {
        ArrayList<PlantPerformanceReport> reports = new ArrayList<>();
        String dbUrl = "jdbc:mysql://localhost:3306/plantdb";  // Replace with your database URL
        String username = "root";  // Replace with your database username
        String password = "password";  // Replace with your database password

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            String query = "SELECT * FROM PlantPerformanceReport WHERE plantID = ? ORDER BY date";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, plantID);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        LocalDate date = rs.getDate("date").toLocalDate();
                        float height = rs.getFloat("height");
                        String disease = rs.getString("disease");
                        String leafColor = rs.getString("leafcolor");
                        PlantPerformanceReport report = new PlantPerformanceReport(date, disease, height, leafColor);
                        reports.add(report);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reports;
    }

   }
