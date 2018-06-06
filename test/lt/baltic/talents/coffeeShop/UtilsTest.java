package lt.baltic.talents.coffeeShop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTest {

	@Test
	public void testCheckInterval() {
		
		Assertions.assertTrue(Utils.checkInterval(0, 10, 5));
		Assertions.assertFalse(Utils.checkInterval(-10, 10, 20));
		
	}
}
