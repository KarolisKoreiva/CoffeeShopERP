package lt.baltic.talents.coffeeShop;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * 
 * Helper class for Coffee Shop
 * 
 * @author Karolis Koreiva
 *
 */

public class Utils {
	
	private static final Logger logger = Logger.getLogger(Utils.class.getName());
	
	//Time and date formatter
	public static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
	
	//Getting table number from input and checking is it in range
	public static int getTableNumber(Scanner input) {
		
		int tableNumber = 0;
		do {
			System.out.println("Please enter your table number: ");
			try {
				tableNumber = input.nextInt();
				if (Utils.checkInterval(0, Cafe.TABLES, tableNumber)) {
					break;
				} else {
					logger.info("We do not have such table. Please try once again.");
				}
			} catch (Exception e) {
				logger.info("You have not entered any valid table. Try again.");
				input.skip(".*");
			}
		} while (true);
		return tableNumber;
	}
	
	//Checking is number within given interval a..b (inclusive)
	public static boolean checkInterval(int a, int b, int number) {
		return number >= a && number <=b ? true : false;
	}
}
