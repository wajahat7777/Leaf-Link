package test;

public class PlantSpecie
{
	private String name;
	private String scientificName;
	private String specie;
	//private PlantSpecieDAO plantspecie;
	Database db= new Database();
	
	public PlantSpecie(Database db,String name, String scientificName, String specie)
	{
		this.name=name;
		this.scientificName=scientificName;
		this.specie=specie;
		this.db=db;
		db.addPlantSpecie(name, scientificName, specie);

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
