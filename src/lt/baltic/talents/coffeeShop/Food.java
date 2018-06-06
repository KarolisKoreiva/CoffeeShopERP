package lt.baltic.talents.coffeeShop;

public class Food extends Dishes {

	private double weight;

	public Food(String name, double price, DishType dishType, int quantity, double weight) {
		super(name, price, dishType, quantity);
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return "Food: " + getName() + ", " + super.getQuantity() + ", pcs " + ", price: " + super.getPrice() + " EUR, weight: " + weight + " gr";
	}
	
	
}
