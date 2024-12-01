package test;
import java.time.LocalDate;

public class Payment 
{   
    private String userID;
	private float amountGiven;
	private LocalDate date;

	//Constructor
	Payment(String userID, float amount, LocalDate date)
	{   
		this.amountGiven=amount;
        this.userID = userID;
		this.date=date;

	}
	public float getAmountGiven() {
		return amountGiven;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setAmountGiven(float amountGiven) {
		this.amountGiven = amountGiven;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
}
