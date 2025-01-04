package application;

import java.time.LocalDate;

public class Feedback 
{
	String text;
	int stars;
	LocalDate dateEntered;
	
	public Feedback(String text, int stars)
	{
		this.text=text;
		this.stars=stars;
		this.dateEntered=LocalDate.now();
	}

	public String getText() {
		return text;
	}

	public int getStars() {
		return stars;
	}

	public LocalDate getDateEntered() {
		return dateEntered;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public void setDateEntered(LocalDate dateEntered) {
		this.dateEntered = dateEntered;
	}
}
