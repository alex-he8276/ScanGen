
/**
 * This class is used as an object class for a general item. It extends the
 * typical product class.
 * 
 * - Alex He
 */

public class Item extends Product {

	// Fields
	private String brand;
	private String manufacturer;

	// Other products constructor
	public Item(String name, String UPC, String category, String description, String image, String brand,
			String manufacturer, String website, String URL, String price) {

		super(name, UPC, category, description, image, website, URL, price);

		this.brand = brand;
		this.manufacturer = manufacturer;

	}

	// Getters and Setters
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	// toString method
	@Override
	public String toString() {

		return "Item," + super.getName() + "," + super.getUPC() + "," + super.getCategory() + ","
				+ super.getDescription() + "," + super.getImage() + "," + getBrand() + "," + getManufacturer() + ","
				+ super.getWebsite() + "," + super.getURL() + "," + super.getPrice();

	}

}
