
/**
 * Author - Alex He
 * 
 * Date of Submission - January 19, 2019
 * 
 * Course Code - ICS4U1-01 - Mr. Fernandes
 * 
 * Title - ScanGen
 * 
 * Description & Instructions - In this application, the user is able to create a UPC barcode
 * for their own product and save the product information to the barcode. They can edit this 
 * information in the future by pressing "Edit Product". Then the user is able
 * to upload the barcode and see that information. If that product was not created by the user,
 * the application will use a barcode lookup API to retrieve the information. The user can then 
 * add the product to their wishlist or to their purchases. In the wishlist/purchases screen, the 
 * user can see the items that they purchased and their total spendings. The user can search up the 
 * item or remove the item from their purchases or wishlist. To test, please upload barcode images 
 * starting with GEN_ in /barcodes folder. 
 * 
 * Features - The application is able to generate a barcode based on mathematical 
 * principles and standards of the Universal Product Code (UPC). Then the application is
 * able to read and analyze the Universal Product Code to retrieve the 12-digit code ("reverse 
 * engineering" of creating the barcode). 
 * 
 * Major Skills - Use of data structures (1D Arrays, 2D Arrays, ArrayLists), Repetition 
 * statements (for loops, enhanced for loops, while loops, do while loops), Selection 
 * statements (if statements), Java GUI building, File reading and writing, Writing and 
 * analyzing Images, Random number generation, Mathematical algorithms (for calculating 
 * the check digit of a barcode), Classes and Objects (inheritance and polymorphism), 
 * Overloaded constructors.
 * 
 * Required File Locations - /music, /barcodes, /images, Products.csv, Purchases.csv, Wishlist.csv
 * *All of which should be included*
 * 
 * Areas of Concern - 
 * 1. This application can only scan barcodes that have been generated by
 * itself. Due to time limitarions, I was unable to code the ability to scan 
 * barcodes in real life. On the other hand, any barcode generated by this
 * application can be recognized by all barcode scanners (tested using a mobile
 * app).
 * 
 * 2. Barcode Lookup API DOES NOT WORK. When I was trying to implement the sample API
 * documentation provided by API, it required the use of Google's GSON Java Library. 
 * I tried to implement the GSON library but I kept receiving an error: "The import 
 * com.google.gson.GsonBuilder cannot be resolved". Due to time constraints, I had to 
 * stop trying to implement the library and thus, could not continue working with the 
 * barcode lookup API.
 * 
 * 3. There are some situations where user error may cause the program to be
 * non-functional. For example, when the user enters the price, it must be in
 * the form $23.33, not 23.33. Another example, could be that the user provides
 * a faulty image link when generating a product.
 * 
 * 4. While testing this application, I realized that books use a different
 * convention for barcodes compared to ordinary products. (The program is still
 * functional, but does not 100% represent real life standards).
 * 
 */

/**
 * This class is used to create a new Home Screen GUI that will lead to the
 * application.
 * 
 * - Alex He
 */

public class BarcodeTest {

	// Main method to run the program and create a new GUI.
	public static void main(String[] args) {

		// 1. Create a new Home Screen GUI.
		new HomeScreen();

		// 2. Play music
		Music.playMusic();
		Music.musicTimer.start();

	}

}