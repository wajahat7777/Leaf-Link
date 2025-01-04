package application;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlantHeightGraph extends JFrame 
{
    private ArrayList<PlantPerformanceReport> reports;

    public PlantHeightGraph(ArrayList<PlantPerformanceReport> reports) 
    {
        this.reports = reports;

        setTitle("Plant Growth Trend");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void paint(Graphics g) 
    {
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
        double maxHeight = 0;
        for (PlantPerformanceReport report : reports)
        {
            if (report.getHeight() > maxHeight) 
            {
                maxHeight = report.getHeight();
            }
        }

        // Draw bars
        for (int i = 0; i < reports.size(); i++)
        {
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

   /* public static void main(String[] args) 
    {
        // Sample data
        ArrayList<PlantPerformanceReport> reports = new ArrayList<>();
        reports.add(new PlantPerformanceReport(LocalDate.of(2024, 11, 1), "Healthy", 20, "Green"));
        reports.add(new PlantPerformanceReport(LocalDate.of(2024, 11, 5), "Healthy", 25, "Green"));
        reports.add(new PlantPerformanceReport(LocalDate.of(2024, 11, 10), "Healthy", 30, "Green"));
        reports.add(new PlantPerformanceReport(LocalDate.of(2024, 11, 15), "Healthy", 35, "Green"));

        // Create the graph window
        SwingUtilities.invokeLater(() -> 
        {
            PlantHeightGraph graph = new PlantHeightGraph(reports);
            graph.setVisible(true);
        });
    }*/
}

