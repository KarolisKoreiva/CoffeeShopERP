package lt.baltic.talents.coffeeShop;

import java.time.LocalDateTime;

public interface Billing{
	
	public String issueInvoice(Integer tableNumber);
	public void saveInvoice(Invoice invoice);
	public Invoice readInvoice(Integer tableNumber, LocalDateTime date);
}
