package test;
import java.time.LocalDate;

public class PlantPerformanceReport
{
	private LocalDate date;
	private String disease;
	private float height;
	private String leafcolor;
	//private PlantPerformanceDAO plantPerformanceDAO;
	//Constructor
	public PlantPerformanceReport(LocalDate date, String disease, float height, String leafcolor)
	{
		this.date=date;
		this.disease=disease;
		this.height=height;
		this.leafcolor=leafcolor;
		//this.plantPerformanceDAO= new PlantPerformanceDAO();
	}
	
	public void displayPlantHeath(String plantID)
	{
		System.out.println("plantID: "+plantID);
		System.out.println("Disease: "+disease);
		System.out.println("Height: "+height);
		System.out.println("leafcolor: "+leafcolor);
		System.out.println();
	}

	public LocalDate getDate() {
		return date;
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

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setLeafcolor(String leafcolor) {
		this.leafcolor = leafcolor;
	}
	
}
