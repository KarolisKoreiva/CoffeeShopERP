package lt.baltic.talents.coffeeShop;

import java.time.LocalDateTime;
import java.util.Random;

public class Invoice extends Order {
	
	private int tableNumber;
	private LocalDateTime endDate;
	private Employee waiter; 
	//private Order order;

	public Invoice(int tableNumber, Order order) {
		super(order);
		this.tableNumber = tableNumber;
		this.endDate = LocalDateTime.now();
		this.waiter = assignWaiter();
		//this.order = order;
	}
	
	public int getTableNumber() {
		return tableNumber;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public Employee getWaiter() {
		return waiter;
	}

	public Employee assignWaiter() {
		Random rand = new Random();
		int id = rand.nextInt(2) + 1;
		
		switch (id) {
		case 1: 
			return Employee.JONAS;
		case 2:
			return Employee.INGA;
		case 3:
			return Employee.PETRAS;
		default:
			break;
		}
		return null;
	}

	public String toString() {
		return "Your invoice:\n"
				+ "Your table number: " + tableNumber + "\n"
				+ "Order started on: " + super.getStartTime().format(Utils.TIME_FORMATTER) + "\n"
				+ "Dishes:\n" + super.toString() + "Total amount to pay: " + super.getSum() + " EUR \n"
				+ "You were served by: " + waiter.getName() + "\n"
				+ "Invoice issue date and time: " + endDate.format(Utils.TIME_FORMATTER) + "\n";
	}
	
}
