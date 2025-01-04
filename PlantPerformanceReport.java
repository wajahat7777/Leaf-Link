package application;

import java.time.LocalDate;

public class PlantPerformanceReport
{
	private String disease;
	private String leafColor;
	private double height;
	private LocalDate date;
	
	public PlantPerformanceReport(String disease, String leafColor, double height)
	{
		this.disease=disease;
		this.leafColor=leafColor;
		this.height=height;
	}

	public String getDisease() {
		return disease;
	}

	public String getLeafColor() {
		return leafColor;
	}

	public double getHeight() {
		return height;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public void setLeafColor(String leafColor) {
		this.leafColor = leafColor;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
