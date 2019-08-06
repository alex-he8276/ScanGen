
/**
 * This class is used as an object class for each purchased product. It extends
 * the wishlist class and has it's own unique field (cost).
 * 
 * - Alex He
 */

public class Purchases extends Wishlist {

	// Fields
	private String cost;

	// Wishlist constructor
	public Purchases(String UPC, String name, String price) {

		super(UPC, name);
		this.cost = price;

	}

	// Getters and Setters
	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return " - " + super.getName() + " for: " + cost;
	}

}
