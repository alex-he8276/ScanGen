
/**
 * This class is used as an object class for each wishlist product.
 * 
 * - Alex He
 * 
 */

public class Wishlist {

	//Fields
	private String name;
	private String UPC;

	//Wishlist constructor
	public Wishlist(String UPC, String name) {
		
		this.name = name;
		this.UPC = UPC;

	}

	//Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUPC() {
		return UPC;
	}

	public void setUPC(String uPC) {
		UPC = uPC;
	}

	@Override
	public String toString() {
		return " - " + getName();
	}

}
