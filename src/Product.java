
/**
 * This class is used as as a parent object class for others to extend from
 * (Book and Item). It contains the typical information of all products.
 * 
 * - Alex He
 */

public class Product {

	// Fields
	private String name;
	private String UPC;
	private String category;
	private String description;
	private String image;
	private String website;
	private String URL;
	private String price;

	// Product Constructor
	public Product(String name, String UPC, String category, String description, String image, String website,
			String URL, String price) {

		super();
		this.name = name;
		this.UPC = UPC;
		this.category = category;
		this.description = description;
		this.image = image;
		this.website = website;
		this.URL = URL;
		this.price = price;

	}

	// Getters and Setters
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	// toString method
	@Override
	public String toString() {

		return "Product [name=" + name + ", UPC=" + UPC + ", category=" + category + ", description=" + description
				+ ", website=" + website + ", links=" + URL + ", price=" + price + "]";

	}

}
