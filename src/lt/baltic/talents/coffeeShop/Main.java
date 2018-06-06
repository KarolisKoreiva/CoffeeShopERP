package lt.baltic.talents.coffeeShop;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
	
	private static final Logger logger = Logger.getLogger(Main.class.getName());
	
	//Pakraunam logerio properties faila
	static {
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream(new File("resources/logging.properties")));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SecurityException, IOException {
		
		//Inicializavimo faze.  
		//Sukuriam nauja kavine (duodam pavadinima) ir sudarom meniu konstruktoriuje.
		Cafe cafe = new Cafe("Bahamas");
		
		System.out.println("Welcome to the best cafe in town \"" + cafe.getName() + "\". We have " + Cafe.TABLES + " tables available. ");
		
		//Inicializuojam pagalbinius kintamuosius
		String status;
		int tableNumber;
		
		Scanner input = new Scanner(System.in);

		//Paleidziam pagrindine programa
		do {
			System.out.println("Choose your option: new (O)rder or (A)ppend existing one, (C)heck orders, issue (I)nvoice, choose (L)anguage, (R)eporting or (Q)uit");
			System.out.println("Current language: " + Locale.getDefault().getLanguage());
			status = input.next();
			
			switch (status.toLowerCase()) {
				case "a" : //uzsakom arba papildom esama uzsakyma
					cafe.printOrders();
				case "o" :
					
					//Gaunam staliuka
					tableNumber = Utils.getTableNumber(input);
					
					//Duodam meniu ir priimam uzsakyma
					System.out.println("Here is the menu. Please order your dishes (and/or drinks)");
					cafe.printMenu();
					cafe.setOrder(tableNumber, input);
					
					//Atspausdinam uzsakyma
					System.out.println("You have ordered:");
					if (cafe.getOrder(tableNumber) != null) {
						System.out.println(cafe.printOrder(tableNumber));
					}
					break;
					
				case "c" : //parodom kokie yra aktyvus uzsakymai
					cafe.printOrdersWithAmount();
					break;
					
				case "i" : //Atsapusdinam aktyvius staliukus ir pateikiam saskaita
					cafe.printOrders();
					tableNumber = Utils.getTableNumber(input);
					if (cafe.getOrder(tableNumber) != null)	{
						double tips = cafe.tips(tableNumber);
						System.out.print(cafe.issueInvoice(tableNumber) + "Recommended tips: " + tips  + "\n");
					}
					else {
						logger.info(tableNumber + " is not active table");
					}
					break;
					
				case "q" : //iseinam is meniu
					break;
					
				case "l" : //pasirenkam menu kalba
					cafe.chooseMenuLang(input);
					break;
					
				case "r" : //einam i reportingo menu
					cafe.reports(input);
					break;
					
				default: //jei nera tokio meniu punkto
					//System.out.println("There are no such a menu item. Try again.");
					logger.info("There are no such a menu item. Try again.");
					break;
			}
		} while (status.compareToIgnoreCase("q") != 0);
		
		//Uzdarom
		input.close();

	}
	
}
