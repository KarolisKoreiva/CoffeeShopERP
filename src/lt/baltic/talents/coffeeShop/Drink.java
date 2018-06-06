package lt.baltic.talents.coffeeShop;

public class Drink extends Dishes{

	private double volume;

	public Drink(String name, double price, DishType dishType, int quantity, double volume) {
		super(name, price, dishType, quantity);
		this.volume = volume;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "Drink: " + getName() + ", price: " + super.getPrice() + " EUR, volume: " + volume + " ml - " + super.getQuantity() + " pcs";
	}
	
}
