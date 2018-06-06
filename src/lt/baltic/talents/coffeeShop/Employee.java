package lt.baltic.talents.coffeeShop;

public enum Employee {

	JONAS("Jonas Jonaitis"),
	INGA("Inga Ingaite"),
	PETRAS("Petras Petraitis");
	
	private String name;
	
	private Employee(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
