package lt.baltic.talents.coffeeShop;

import java.util.Comparator;

public class SortDishes implements Comparator<Dishes> {

	@Override
	public int compare(Dishes o1, Dishes o2) {
		int i = o1.getClass().getName().compareTo(o2.getClass().getName());
		if (i == 0) {
			if (o1.getDishType().ordinal() > (o2.getDishType().ordinal())) {
				return 1;	
			} else if (o1.getDishType().ordinal() == (o2.getDishType().ordinal())) {
				return 0;
			} else {
				return -1;
			}
		}
		return i;
	}

}
