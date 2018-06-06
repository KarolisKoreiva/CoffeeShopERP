package lt.baltic.talents.coffeeShop;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

	private List<Dishes> dishes;
	private LocalDateTime startTime;
	private double sum;
	
	public Order(Order order) {
		this.dishes = order.getDishes();
		this.startTime = order.getStartTime();
		this.sum = order.getSum();
	}

	public Order(Dishes dish, int quantity) {
		if (dishes == null) {
			dishes = new ArrayList<>();	
		}
		dishes.add(dish);
		dish.setQuantity(quantity);
		sum += dish.calculateDishSum();
		this.startTime = LocalDateTime.now();
	}

	public List<Dishes> getDishes() {
		return dishes;
	}

	public void addDish (Dishes dish, int quantity) {
		dishes.add(dish);
		dish.setQuantity(quantity);
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}
	
	@Override
	public String toString() {
		String result = "   ";
		for (Dishes dish : dishes) {
			result += dish.toString() + "\n";
		}
		return result;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

}
