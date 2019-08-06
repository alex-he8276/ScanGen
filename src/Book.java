
/**
 * This class is used as an object class for a book.It extends the typical
 * product class.
 * 
 * - Alex He
 */

public class Book extends Product {

	// Fields
	private String author;
	private String publisher;

	// Book constructor
	public Book(String name, String UPC, String category, String description, String image, String author,
			String publisher, String website, String URL, String price) {

		super(name, UPC, category, description, image, website, URL, price);

		this.author = author;
		this.publisher = publisher;

	}

	// Getters and Setters
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	// toString method
	@Override
	public String toString() {

		return "Book," + super.getName() + "," + super.getUPC() + "," + super.getCategory() + ","
				+ super.getDescription() + "," + super.getImage() + "," + getAuthor() + "," + getPublisher() + ","
				+ super.getWebsite() + "," + super.getURL() + "," + super.getPrice();

	}
}
