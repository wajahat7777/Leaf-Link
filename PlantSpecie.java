package application;

public class PlantSpecie 
{
	private String name;
	private String scientificName;
	private String specie;
	
	public PlantSpecie(String name, String SName, String specie)
	{
		this.name=name;
		this.scientificName=SName;
		this.specie=specie;
	}

	public String getName() {
		return name;
	}

	public String getScientificName() {
		return scientificName;
	}

	public String getSpecie() {
		return specie;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}
}

