package test;
import java.sql.Connection;
import java.time.LocalDate;

public class Feedback 
{
	private int stars;
	private String text;
	private LocalDate date;
    Database db=new Database();	
	//Constrcutor
	public Feedback(String id,int stars, String text, LocalDate date)
	{
		this.stars=stars;
		this.text=text;
		this.date=date;
		db.addFeedback(id, stars, text, date);
	}

	public int getStars() {
		return stars;
	}

	public String getText() {
		return text;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
