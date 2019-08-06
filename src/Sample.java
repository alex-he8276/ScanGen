import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

/**
 * This class is used for template classes for the product, store, and reviews.
 * It is also used to access the barcode lookup API. Copied from:
 * https://www.barcodelookup.com/api-documentation
 */

//public class Sample {
//	public class Store {
//		public String store_name;
//		public String store_price;
//		public String product_url;
//		public String currency_code;
//		public String currency_symbol;
//	}
//
//	public class Review {
//		public String name;
//		public String rating;
//		public String title;
//		public String review;
//		public String datetime;
//	}
//
//	public class Product {
//		public String barcode_number;
//		public String barcode_type;
//		public String barcode_formats;
//		public String mpn;
//		public String model;
//		public String asin;
//		public String product_name;
//		public String title;
//		public String category;
//		public String manufacturer;
//		public String brand;
//		public String label;
//		public String author;
//		public String publisher;
//		public String artist;
//		public String actor;
//		public String director;
//		public String studio;
//		public String genre;
//		public String audience_rating;
//		public String ingredients;
//		public String nutrition_facts;
//		public String color;
//		public String format;
//		public String package_quantity;
//		public String size;
//		public String length;
//		public String width;
//		public String height;
//		public String weight;
//		public String release_date;
//		public String description;
//		public Object[] features;
//		public String[] images;
//		public Store[] stores;
//		public Review[] reviews;
//	}
//
//	public class RootObject {
//		public Product[] products;
//	}
//
//	public static void main(String[] args) {
//		try {
//			URL url = new URL(
//					"https://api.barcodelookup.com/v2/products?barcode=077341125112&formatted=y&key=ENTER_YOUR_API_KEY_HERE");
//			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
//			String str = "";
//			String data = "";
//			while (null != (str = br.readLine())) {
//				data += str;
//			}
//
//			Gson g = new Gson();
//
//			RootObject value = g.fromJson(data, RootObject.class);
//
//			String barcode = value.products[0].barcode_number;
//			System.out.print("Barcode Number: ");
//			System.out.println(barcode);
//
//			String name = value.products[0].product_name;
//			System.out.print("Product Name: ");
//			System.out.println(name);
//
//			System.out.println("Entire Response:");
//			System.out.println(data);
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//
//	}
//}