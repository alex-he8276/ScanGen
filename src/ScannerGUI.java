import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class creates an ScannerGUI that extends the JFrame class. It includes a
 * constructor method that sets up the frame and panel. This class allows the
 * user to look up a barcode and be displayed information about the product. It
 * also allows the user to save the product to their wishlist or add it to their
 * purchases.
 * 
 * - Alex He
 */

public class ScannerGUI extends JFrame implements ActionListener {

	// String for the corresponding widths and barcode digits
	final String[] CORRESPONDING_BARCODE_DIG = { "3211", "2221", "2122", "1411", "1132", "1231", "1114", "1312", "1213",
			"3112" };

	// File for the barcode image
	File file;

	// Buffered image for the barcode image
	BufferedImage img, rightImg, leftImg;

	// Array for the pixels
	int[][] pixels;
	int[][] rPixels;
	int[][] lPixels;

	// Current product
	Product currentProduct;

	// Arraylist for all premade products
	ArrayList<Product> premadeProducts = new ArrayList<Product>();

	// String to decide the type of product
	String decide;

	// String for the UPC
	String StringUPC = "";

	// Various fonts for the JLabels
	Font font = new Font("Constantia", Font.BOLD, 40);
	Font font2 = new Font("Constantia", Font.ITALIC, 32);
	Font font3 = new Font("Arial", Font.PLAIN, 29);
	Font font4 = new Font("Constantia", Font.ITALIC, 24);

	// JLabel for the title
	JLabel titleLabel = new JLabel();

	// JLabels for the subtitles
	JLabel[] subtitleLabels = new JLabel[6];

	// JTextArea for the information
	JLabel[] informationLabels = new JLabel[6];

	// JTextArea for the description
	JTextArea descriptionTextArea = new JTextArea();

	// JScrollPane for the description
	JScrollPane scroll;

	// JLabel for the barcode image
	JLabel productImg = new JLabel();

	// JButton to return to home
	JButton returnButton = new JButton();

	// JButons for wishlist and purchase
	JButton[] pwButtons = new JButton[2];

	// JTextArea for the cost
	JTextArea costField = new JTextArea();

	// JButton to upload image
	JButton uploadButton = new JButton();

	// JPanel for user to input product information
	JPanel scanPanel = new JPanel() {

		// Taken from stackoverflow; used to create a gradient background
		// https://stackoverflow.com/questions/14364291/jpanel-gradient-background
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			GradientPaint gp = new GradientPaint(0, 0, getBackground().brighter().brighter(), 0, getHeight(),
					getBackground().darker().darker());
			g2d.setPaint(gp);
			g2d.fillRect(0, 0, getWidth(), getHeight());

		}

	};

	// Scanner GUI constructor, used by PurchasesWishlistGUI if the UPC is already
	// known so that the user can take a look at the product again
	public ScannerGUI(String UPC) {

		StringUPC = UPC;

		// 1. Set up the JPanel
		panelSetup();

		// 2. Set up the JFrame
		frameSetup();

		// 3. Lookup the UPC
		barcodeLookupAPI();

		// 4. Remove upload feature
		uploadButton.setVisible(false);

	}

	// Scanner GUI constructor
	public ScannerGUI() {

		// 1. Set up the JPanel
		panelSetup();

		// 2. Set up the JFrame
		frameSetup();

	}

	// This method sets up the JFrame
	private void frameSetup() {

		// 1. Setup the GUI
		setSize(1380, 735);
		setTitle("Scanner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(177, 220, 249));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setFocusable(true);
		setLayout(null);

		// 2. Add the JPanel to the JFrame
		add(scanPanel);

		// 3. Make the GUI visible
		setVisible(true);

	}

	// This method sets up the JPanel and all other GUI components
	private void panelSetup() {

		// 1. Set up the properties of the panel.
		scanPanel.setBounds(0, 0, 1380, 735);
		scanPanel.setBackground(new Color(102, 226, 255));
		scanPanel.setLayout(null);

		// 2. Set up the properties of the title label
		titleLabel.setBounds(50, 10, 410, 85);
		titleLabel.setText("Barcode Scanner");
		titleLabel.setFont(font);
		titleLabel.setOpaque(false);
		scanPanel.add(titleLabel);

		// 3. Set up the properties of the upload image button
		uploadButton.setFont(font2);
		uploadButton.setText("Upload Barcode Image");
		uploadButton.addActionListener(this);
		uploadButton.setBorder(new LineBorder(Color.BLACK, 1));
		uploadButton.setBounds(420, 20, 350, 50);
		scanPanel.add(uploadButton);

		// 4. Set up the properties of the subtitles
		for (int i = 0; i < subtitleLabels.length; i++) {

			// 4.1. Set up the properties of the labels.
			subtitleLabels[i] = new JLabel();
			subtitleLabels[i].setFont(font2);
			subtitleLabels[i].setBounds(70, 70 + 70 * i, 1810, 85);
			scanPanel.add(subtitleLabels[i]);

		}

		// 5. Assign the proper text to the labels depending on the information of the
		// product.
		subtitleLabels[0].setText("Product:");
		subtitleLabels[1].setText("Category:");
		subtitleLabels[2].setText("Author:");
		subtitleLabels[3].setText("Publisher:");
		subtitleLabels[4].setText("Description:");
		subtitleLabels[5].setText("Online Shop:");
		subtitleLabels[5].setBounds(70, 540, 1110, 85);

		// 6. Set up a JTextArea and add a JScrollPane to it
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setFont(font3);
		descriptionTextArea.setEditable(false);
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);
		scroll = new JScrollPane(descriptionTextArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(270, 380, 580, 180);
		scroll.setBorder(new LineBorder(Color.BLACK, 2));
		scanPanel.add(scroll);

		// 7. Set up the return button
		returnButton.setFont(font2);
		returnButton.setText("Back to Home");
		returnButton.addActionListener(this);
		returnButton.setBorder(new LineBorder(Color.BLACK, 1));
		returnButton.setBounds(1080, 610, 250, 75);
		scanPanel.add(returnButton);

		// 8. Set up the wishlist and purchase button
		for (int i = 0; i < pwButtons.length; i++) {

			pwButtons[i] = new JButton();
			pwButtons[i].setFont(font4);
			pwButtons[i].addActionListener(this);
			pwButtons[i].setBorder(new LineBorder(Color.BLACK, 1));
			pwButtons[i].setBounds(1080, 450 + 70 * i, 250, 45);
			pwButtons[i].setEnabled(false);
			scanPanel.add(pwButtons[i]);

		}

		pwButtons[0].setText("Add to Wishlist");
		pwButtons[1].setText("Add to Purchases");

		// 9. Set up the JTextArea for the cost
		costField.setBounds(920, 480, 130, 50);
		costField.setText("$");
		costField.setBorder(new LineBorder(Color.BLACK, 2));
		costField.setFont(font2);
		scanPanel.add(costField);

	}

	// This method retrieves the image uploaded by the user
	// Referenced from
	// https://stackoverflow.com/questions/3548140/how-to-open-and-save-using-java
	private void uploadImage() {

		// 1. Open a file chooser
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(filter);
		fileChooser.setCurrentDirectory(new File("barcodes/"));

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			// 2. Save the file
			file = fileChooser.getSelectedFile();
		}

		// 3. Decode the UPC
		decodeUPC();

	}

	// This method decodes the UPC code from an image of a barcode
	private void decodeUPC() {

		// 1. Create a BufferedImage of the barcode
		try {
			img = ImageIO.read(file);
			leftImg = img.getSubimage(36, 20, 84, 1);
			rightImg = img.getSubimage(130, 20, 84, 1);
		} catch (IOException e) {
			System.out.print("Barcode failed to read.");
		}

		// 2. Change it into pixels
		lPixels = new int[leftImg.getWidth()][leftImg.getHeight()];
		rPixels = new int[rightImg.getWidth()][rightImg.getHeight()];

		for (int x = 0; x < lPixels.length; x++) {

			for (int y = 0; y < lPixels[0].length; y++) {

				lPixels[x][y] = leftImg.getRGB(x, y);
				rPixels[x][y] = rightImg.getRGB(x, y);

			}

		}

		// 3. Find the widths of each bar
		String color = "white";
		int barCounter = 0;
		int widthCounter = 0;
		int[] widths = new int[48];

		for (int x = 0; x < lPixels.length; x++) {

			if (lPixels[x][0] == -1 && color.equals("white")) {

				color = "white";
				barCounter++;

			} else if (lPixels[x][0] == -16777216 && color.equals("black")) {

				color = "black";
				barCounter++;

			} else {

				widths[widthCounter] = barCounter / 2;
				widthCounter++;
//				System.out.println(widthCounter);

				if (color.equals("white"))
					color = "black";
				else
					color = "white";

				barCounter = 1;

			}

		}

		widths[widthCounter] = barCounter / 2;
		widthCounter++;

		barCounter = 0;
//		color = "black";

		for (int x = 0; x < rPixels.length; x++) {

			if (rPixels[x][0] == -1 && color.equals("white")) {

				color = "white";
				barCounter++;

			} else if (rPixels[x][0] == -16777216 && color.equals("black")) {

				color = "black";
				barCounter++;

			} else {

				widths[widthCounter] = barCounter / 2;
				widthCounter++;
//				System.out.println(widthCounter);

				if (color.equals("white"))
					color = "black";
				else
					color = "white";

				barCounter = 1;

			}

		}

		widths[widthCounter] = barCounter / 2;
		widthCounter++;

		String four = "";

		// 4. Using the widths, create the UPC code
		for (int i = 0; i < widths.length; i += 4) {

			four = Integer.toString(widths[i]) + Integer.toString(widths[i + 1]) + Integer.toString(widths[i + 2])
					+ Integer.toString(widths[i + 3]);
//			System.out.println(four);

			for (int j = 0; j < CORRESPONDING_BARCODE_DIG.length; j++) {

				if (four.equals(CORRESPONDING_BARCODE_DIG[j])) {

					StringUPC += j;

				}
			}

		}

		System.out.println(StringUPC);

		// 5. Look up the UPC code
		barcodeLookupAPI();
		uploadButton.setEnabled(false);

//		File file1 = new File("barcodes/" + "TEST.png");
//
//		try {
//			ImageIO.write(img, "png", file1);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

	}

	// This method looks up the barcode using the API and retrieves information
	// about the product
	private void barcodeLookupAPI() {

		String productName, UPC, category, description, image, author = null, publisher = null, brand = null,
				manufacturer = null, website, URL, price;

		// 1. Read in all the premade products
		try {

			Scanner input = new Scanner(new File("Products.csv"));
			input.useDelimiter(",");
			input.nextLine();

			// 1.1. Program reads the csv file while there is a next line
			while (input.hasNextLine()) {

				// 1.1.1. Decide the type of product and read in the appropriate variables
				decide = input.next().replaceAll("\r", "").replaceAll("\n", "");
				productName = input.next().replaceAll("\r", "").replaceAll("\n", "");
				UPC = input.next().replaceAll("\r", "").replaceAll("\n", "");
				category = input.next().replaceAll("\r", "").replaceAll("\n", "");
				description = input.next().replaceAll("\r", "").replaceAll("\n", "");
				image = input.next().replaceAll("\r", "").replaceAll("\n", "");

				if (decide.equals("Book")) {

					author = input.next().replaceAll("\r", "").replaceAll("\n", "");
					publisher = input.next().replaceAll("\r", "").replaceAll("\n", "");

				} else {

					brand = input.next().replaceAll("\r", "").replaceAll("\n", "");
					manufacturer = input.next().replaceAll("\r", "").replaceAll("\n", "");

				}

				website = input.next().replaceAll("\r", "").replaceAll("\n", "");
				URL = input.next().replaceAll("\r", "").replaceAll("\n", "");
				price = input.next().replaceAll("\r", "").replaceAll("\n", "");

				if (decide.equals("Book")) {
					premadeProducts.add(new Book(productName, UPC, category, description, image, author, publisher,
							website, URL, price));
				} else {
					premadeProducts.add(new Item(productName, UPC, category, description, image, brand, manufacturer,
							website, URL, price));
				}

			}

			// 1.2 Close the file
			input.close();

			// 2. Catch the possible error
		} catch (FileNotFoundException error) {

			// 2.1 If the file does not exist, send error message
			System.out.println("Sorry wrong file - please check the name");

		}

		boolean premade = false;

		// 3. Check if the UPC is a premade product. If so display the product.
		for (Product c : premadeProducts) {

			if (c.getUPC().equals(StringUPC)) {

				currentProduct = c;
				System.out.println(currentProduct);
				premade = true;
				break;

			}

		}

		if (premade == false) {

			// 1. Using the API search up the API
			// 2. Retrieve the appropriate information

		}

		// 5. Update the info
		updateInfo();

	}

	// This method updates the information of the product after it has been found.
	private void updateInfo() {

		subtitleLabels[0].setText("Product: " + currentProduct.getName());
		subtitleLabels[1].setText("Category: " + currentProduct.getCategory());
		descriptionTextArea.setText(currentProduct.getDescription().replaceAll(",", ""));

		// Add a mouse listener for a website
		// Referenced from:
		// https://stackoverflow.com/questions/527719/how-to-add-hyperlink-in-jlabel
		subtitleLabels[5].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(currentProduct.getURL()));
				} catch (URISyntaxException | IOException ex) {
					System.out.println("URL Failed.");
				}
			}

		});

		subtitleLabels[5].setText("<html> Online Shop : <a href=\"\">" + currentProduct.getWebsite() + "  -  "
				+ currentProduct.getPrice() + "</a></html>");
		subtitleLabels[5].setCursor(new Cursor(Cursor.HAND_CURSOR));

		subtitleLabels[5].setBounds(70, 560, subtitleLabels[5].getText().length() * 10, 85);

		if (currentProduct instanceof Book) {
			subtitleLabels[2].setText("Author: " + ((Book) currentProduct).getAuthor());
			subtitleLabels[3].setText("Publisher: " + ((Book) currentProduct).getPublisher());
		} else {
			subtitleLabels[2].setText("Brand: " + ((Item) currentProduct).getBrand());
			subtitleLabels[3].setText("Manufacturer: " + ((Item) currentProduct).getManufacturer());
		}

		// 7. Set up the label to store the product image
		// Referenced from:
		// https://stackoverflow.com/questions/13448368/trying-to-display-url-image-in-jframe
		productImg.setBounds(1000, 50, 335, 335);

		String path = currentProduct.getImage();
		URL url;
		BufferedImage image = null;

		try {
			url = new URL(path);
			image = ImageIO.read(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			System.out.println("URL failed.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Image failed.");
		}

		ImageIcon productIcon = new ImageIcon(
				new ImageIcon(image).getImage().getScaledInstance(335, 335, Image.SCALE_SMOOTH));

		productImg.setIcon(productIcon);
		scanPanel.add(productImg);

		// 8. Allow the buttons to be used
		pwButtons[1].setEnabled(true);
		pwButtons[0].setEnabled(true);

	}

	// This method adds the current product to the wishlist
	private void addToWishlist() throws IOException {

		// 1. Open the wishlist csv file and create a file writer
		FileWriter fileWriter = new FileWriter("Wishlist.csv", true);

		// 2. Add details of the product to the end of the file (name, UPC)
		fileWriter.write(",\n" + currentProduct.getUPC() + "," + currentProduct.getName());

		// 3. Close the file writer
		fileWriter.close();

		// 4. Pop up a message to inform the user that a product has been added.
		JOptionPane.showMessageDialog(null, "Product successfully added to wishlist!", "Success!", 1);

		// 4. Disable the add to purchases button and add to wishlist button (to
		// prevent duplication/user error)
		pwButtons[0].setEnabled(false);
		pwButtons[1].setEnabled(false);

	}

	// This method adds the current product to the list of purchases
	private void addToPurchases() throws IOException {

		// 1. Check that a price has been entered.
		if (costField.getText().length() > 1) {

			// 1.1. If price has been entered, open the file and create a file writer.
			FileWriter fileWriter = new FileWriter("Purchases.csv", true);

			// 1.2. Add the product to purchases
			fileWriter.write(
					",\n" + currentProduct.getUPC() + "," + currentProduct.getName() + "," + costField.getText());

			// 1.3. Close the file writer
			fileWriter.close();

			// 1.4. Display success message
			JOptionPane.showMessageDialog(null, "Product successfully added to purchases!", "Success!", 1);

			// 1.5. Disable the add to purchases button and add to wishlist button (to
			// prevent
			// duplication/user error)
			pwButtons[0].setEnabled(false);
			pwButtons[1].setEnabled(false);

			// 2. If the cost field is empty, error warn the user
		} else
			JOptionPane.showMessageDialog(null, "Error! - Please enter a price.", "Error!", 0);

	}

	// This method handles all the actions performed by the user (button clicks)
	@Override
	public void actionPerformed(ActionEvent event) {

		// 1. If the upload image button is clicked
		// 1.1. Call the upload image method

		// 1. If the add to wishlist button is clicked...
		if (event.getSource() == pwButtons[0]) {

			// 1.1. Add the current product to the wishlist
			try {
				addToWishlist();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 2. If the add to purchases button is clicked.
		} else if (event.getSource() == pwButtons[1]) {

			// 2.1. Add the current product to purchases list
			try {
				addToPurchases();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 3. If the return button is clicked...
		} else if (event.getSource() == returnButton) {

			// 3.1. Dispose the current class and make a new home screen
			new HomeScreen();
			dispose();

			// 4. If the uploadButton is clicked
		} else if (event.getSource() == uploadButton) {

			// 4.1. Upload the image
			uploadImage();

		}

	}

}