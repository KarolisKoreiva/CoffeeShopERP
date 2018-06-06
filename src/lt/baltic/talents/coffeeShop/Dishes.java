package lt.baltic.talents.coffeeShop;

/**
 * Abstract class for Dishes
 * 
 * @author Karolis
 *
 */

public abstract class Dishes {
	
	private String name;
	private double price;
	private int quantity;
	private DishType dishType;
	
	public Dishes(String name, double price, DishType dishType, int quantity) {
		this.name = name;
		this.price = price;
		this.dishType = dishType;
		this.quantity = quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double calculateDishSum() {
		return price * quantity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public DishType getDishType() {
		return dishType;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
}
