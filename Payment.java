package application;

import java.time.LocalDate;

public class Payment
{
	private int ID;
	private double amount;
	private LocalDate date;
	
	public Payment(double amount)
	{
		this.amount=amount;
		this.date=LocalDate.now();
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}

