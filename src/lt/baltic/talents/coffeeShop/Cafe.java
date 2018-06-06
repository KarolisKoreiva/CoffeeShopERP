package lt.baltic.talents.coffeeShop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Cafe implements Billing{
	
	private static final Logger logger = Logger.getLogger(Cafe.class.getName());

	private String name;
	private List<Dishes> menu;				//Sudarytas meniu
	private Map<Integer, Order> orders;	//staliuko numeris ir jo uzsakymas
	
	public static final double TIPS = 0.05;
	public static final int TABLES = 10;
	
	public Cafe(String name) {
		this.name = name;
		orders = new HashMap<>();
		//Default locale visada yra en-us
		setMenu("en-us");
	}
	
	public void setOrder(Integer tableNumber, Scanner input) {
		int dishNumber = 0;
		int dishQuantity = 0;
		Order order = null;
		
		do {
			try {
				System.out.println("Enter dish number or '0' to exit: ");
				dishNumber = input.nextInt();
				if (dishNumber == 0) {
					break;
				}
				if (!Utils.checkInterval(0, getMenu().size(), dishNumber)) {
					System.out.println("We do not have such a dish. Please try again.");
					continue;
				}
				System.out.println("How many " + menu.get(dishNumber).getName() + " you want to order or '0' to exit: ");
				dishQuantity = input.nextInt();
				if (dishQuantity == 0) {
					break;
				}
				if (dishQuantity > 0) {
					if (order == null) {
						order = new Order(getMenuItem(dishNumber), dishQuantity);
					} else {
						order.addDish(getMenuItem(dishNumber), dishQuantity);
					}
				} else {
					logger.info("We do not have such a dish. Please try again.");
				}
			} catch (InputMismatchException e) {
				logger.info("You have not entered any valid number. Try again.");
				input.skip(".*");
			}
		} while (true);
		
		orders.put(tableNumber, order);
	}
	
	public Order getOrder(Integer tableNumber) {
		return orders.get(tableNumber);
	}
	
	public void printOrdersWithAmount() {
		System.out.println("Tables report:");
		Iterator<Integer> keys = orders.keySet().iterator();
		Integer nextTable = 0;
		if (keys.hasNext()) {
			nextTable = keys.next();
		}
		for (int i = 1; i <= TABLES; i++) {
			if (!orders.isEmpty()) {
				if (i != nextTable) {
						System.out.println("Table " + i + " is empty");
					} else {
						System.out.println("Table " + i + " has ordered: ");
						System.out.println("   Order started: " + getOrder(i).getStartTime().format(Utils.TIME_FORMATTER));
						System.out.print(printOrder(i));
						System.out.println("   Total amount: " + getOrderSum(i) + " EUR");
						if (keys.hasNext()) {
							nextTable = keys.next();
						}
				}
			} else {
				System.out.println("Table " + i + " is empty");
			}
		}
		System.out.println();
	}

	@Override
	public String issueInvoice(Integer tableNumber) {
		Invoice invoice = new Invoice(tableNumber, orders.get(tableNumber));
		saveInvoice(invoice);
		orders.remove(tableNumber);
		return invoice.toString();
	}

	public String getName() {
		return name;
	}

	public List<Dishes> getMenu() {
		return menu;
	}
	
	public Dishes getMenuItem(int position) {
		return menu.get(position);
	}

	public void setMenu(List<Dishes> menu) {
		this.menu = menu;
	}
	
	public void printMenu() {
		int i = 1;
		for (Dishes dish:menu) {
			System.out.println(i + " " + dish);
			i++;
		}
	}
	
	public double getOrderSum(int tableNumber) {
		return orders.get(tableNumber).getSum();
		
	}
	
	public String printOrder(Integer tableNumber) {
		return orders.get(tableNumber).toString();
	}
	
	public void printOrders() {
		System.out.print("Tables in use: ");
		for (Integer index : orders.keySet()) {
			System.out.print(index + " ");
		}
		System.out.println();
	}
	
	public void setMenu(String locale) {
		
		Locale.setDefault(new Locale(locale));
		ResourceBundle msg = ResourceBundle.getBundle("cafe");
		
		//Sukuriam meniu
		menu = new ArrayList<>();
		menu.add(new Drink(msg.getString("cola"), 2.00, DishType.BEVERAGES, 0, 0.5));
		menu.add(new Drink(msg.getString("orangejuices"), 1.75, DishType.BEVERAGES, 0, 0.4));
		menu.add(new Food(msg.getString("snacks"), 4.50, DishType.APPETIZERS, 0, 200));
		menu.add(new Food(msg.getString("fishchips"), 9.50, DishType.MAIN, 0, 450));
		menu.add(new Drink(msg.getString("beer"), 3.00, DishType.ALCOHOL, 0, 500));
		menu.add(new Food(msg.getString("ribeye"), 16.50, DishType.MAIN, 0, 500));
		menu.add(new Food(msg.getString("applepie"), 3.60, DishType.DESSERT, 0, 150));
		menu.add(new Drink(msg.getString("coffee"), 1.00, DishType.HOT_DRINKS, 0, 0.2));
		menu.add(new Drink(msg.getString("tea"), 1.00, DishType.HOT_DRINKS, 0, 0.2));
		Collections.sort(menu, new SortDishes());
	}

	public void chooseMenuLang(Scanner input) {
		
		int lang = 0;
		do {
			System.out.println("Please choose your language: 1 - English, 2 - Lithuanian");
			try {
				lang = input.nextInt();
				switch (lang) {
				case 1: //pasirenkam en
					setMenu("en-us");
					break;
				case 2: //pasirenkam lt
					setMenu("lt-lt");
					break;
				default: //pakartojam pasirinkima
					logger.info("We do not support this language. Try again");
					break;
				}
			} catch (InputMismatchException e) {
				logger.info("It is not a number of menu. Try again");
				input.skip(".*");
			}
		} while (lang <= 1 && lang >= 2);
		
	}

	@Override
	public void saveInvoice(Invoice invoice) {
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("resources/cafe.json"), true))) {
			GsonBuilder gsonBilder = new GsonBuilder();
			gsonBilder.registerTypeAdapter(Dishes.class, new AbstractElementAdapter());
			Gson gson = gsonBilder.create();
			String gsonInvoice = gson.toJson(invoice);
			writer.write(gsonInvoice + "\n");
			writer.flush();
			
		} catch (FileNotFoundException e) {
			logger.warning(e.getMessage());
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
		
		
	}
	
	public double tips(int tableNumber) {
		return Math.round(getOrderSum(tableNumber) * Cafe.TIPS);
	}

	@Override
	public Invoice readInvoice(Integer tableNumber, LocalDateTime date) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void printInvoicesByQuartersfromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File("resources/cafe.json")))) {
			String line;
			GsonBuilder gsonBilder = new GsonBuilder();
			gsonBilder.registerTypeAdapter(Dishes.class, new AbstractElementAdapter());
			Gson gson = gsonBilder.create();
			
			System.out.println("Invoiced amounts by quarters: ");
			Invoice invoice;
			double sum = 0.0;
			int currentYear = 0;
			int currentQuarter = 0;
			while ((line = reader.readLine()) != null) {
				invoice = gson.fromJson(line, Invoice.class);
				if (currentYear == 0 && currentQuarter == 0) {
					currentYear = invoice.getEndDate().getYear();
					currentQuarter = invoice.getEndDate().get(IsoFields.QUARTER_OF_YEAR);	
				}
				if ((invoice.getEndDate().getYear() == currentYear) &&
						(invoice.getEndDate().get(IsoFields.QUARTER_OF_YEAR) == currentQuarter)) {
						sum += invoice.getSum();
				} else {
						System.out.println(currentYear + " quarter " + currentQuarter + ", total amount: " + sum + " EUR");
						currentQuarter = invoice.getEndDate().get(IsoFields.QUARTER_OF_YEAR);
						currentYear = invoice.getEndDate().getYear();
						sum = invoice.getSum();
				}
			}
			System.out.println(currentYear + " quarter " + currentQuarter + ", total amount: " + sum + " EUR");
		} catch (FileNotFoundException e) {
			logger.warning(e.getMessage());
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
	}

	public void printAllInvoicesfromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File("resources/cafe.json")))) {
			String line;
			GsonBuilder gsonBilder = new GsonBuilder();
			gsonBilder.registerTypeAdapter(Dishes.class, new AbstractElementAdapter());
			Gson gson = gsonBilder.create();
			System.out.println("Invoice list: ");
			int i = 1;
			while ((line = reader.readLine()) != null) {
				System.out.print("Invoice No.: " + i + " ");
				System.out.println(gson.fromJson(line, Invoice.class).toString());
				i++;
			}
		} catch (FileNotFoundException e) {
			logger.warning(e.getMessage());
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
		
		
	}

	public void reports(Scanner input) {
		
		int menu = 0;
		do {
			System.out.println("Please choose reports: 1 - print all invoices from database, 2 - amounts per quarters, 0 - back");
			try {
				menu = input.nextInt();
				switch (menu) {
				case 1: //atspausdinam visus invoisus
					printAllInvoicesfromFile();
					break;
				case 2: //atspausdinam sumas pagal ketvircius
					printInvoicesByQuartersfromFile();
					break;
					
				case 0: // iseinam
					break;
					
				default: //pakartojam pasirinkima
					logger.info("No valid report number. Try again");
					break;
				}
			} catch (InputMismatchException e) {
				logger.info("It is not a number of menu. Try again");
				input.skip(".*");
			}
		} while (menu != 0);
		
	}

	
}
