import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * This class creates an GeneratorGUI that extends the JFrame class. It includes
 * a constructor method that sets up the frame and panel. This class allows the
 * user to enter information about their own product and then generate a UPC
 * code and barcode for the product, which can then be uploaded to retrieve this
 * information.
 * 
 * - Alex He
 * 
 * Code Referenced: Java | How to create a random pixel image
 * https://www.youtube.com/watch?v=DSoYfJu2Z00 The above youtube video was used
 * LIGHTLY to understand how to create an image file and to draw the image.
 */

public class GeneratorGUI extends JFrame implements ActionListener {

	// String for the generated barcode
	String stringUPC = new String();

	// Widths of the possible bars
	final int WIDTH_ONE = 1;
	final int WIDTH_TWO = 2;
	final int WIDTH_THREE = 3;
	final int WIDTH_FOUR = 4;

	// Arraylist for all premade products
	ArrayList<Product> premadeProducts = new ArrayList<Product>();

	// String for the corresponding widths and barcode digits
	final String[] CORRESPONDING_BARCODE_DIG = { "3211", "2221", "2122", "1411", "1132", "1231", "1114", "1312", "1213",
			"3112" };

	// Boolean to know the type of product
	boolean book = true;

	// String to decide the type of product
	String decide;

	// Index of the current product
	int index;

	// Various fonts for the JLabels
	Font font = new Font("Constantia", Font.BOLD, 40);
	Font font2 = new Font("Constantia", Font.ITALIC, 32);
	Font font3 = new Font("Arial", Font.PLAIN, 29);
	Font font4 = new Font("Constantia", Font.ITALIC, 22);

	// JLabel for the title
	JLabel titleLabel = new JLabel();

	// JLabels for the subtitles
	JLabel[] subtitleLabels = new JLabel[7];

	// JTextArea for the information
	JTextArea[] informationTextAreas = new JTextArea[5];

	// JTextArea for the description
	JTextArea descriptionTextArea = new JTextArea();

	// JScrollPane for the description
	JScrollPane scroll;

	// JButtons for the type of product
	JButton[] typeButton = new JButton[2];

	// JLabel for the barcode image
	JLabel barcodeImg = new JLabel();

	// JButton to generate the barcode
	JButton generateButton = new JButton();

	// JTextAreas for the online shops
	JTextArea websiteTextArea = new JTextArea();
	JTextArea linkTextArea = new JTextArea();
	JTextArea priceTextArea = new JTextArea();

	// Image of the barcode
	BufferedImage img = new BufferedImage(256, 151, BufferedImage.TYPE_INT_RGB);

	// JButton to return to the home screen
	JButton returnButton = new JButton();

	// JButton to generate the barcode
	JButton genButton = new JButton();

	// JButton to open editor
	JButton editButton = new JButton();

	// JComboxBox for the premade products
	JComboBox<String> productComboBox = new JComboBox();

	// JButton to delete product
	JButton delButton = new JButton();

	// JPanel for user to input product information
	JPanel genPanel = new JPanel() {

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

	// GeneratorGUI constructor
	public GeneratorGUI() {

		panelSetup(); // Calls the method that sets up the panel.
		frameSetup(); // Calls the method that sets up the frame.

	}

	// This method sets up the JPanel and all other GUI components
	private void panelSetup() {

		// 1. Set up the properties of the panel.
		genPanel.setBounds(0, 0, 1380, 735);
		genPanel.setBackground(new Color(138, 240, 177));
		genPanel.setLayout(null);

		// 2. Set up the properties of the two title labels
		titleLabel.setBounds(50, 10, 410, 85);
		titleLabel.setText("Barcode Generator");
		titleLabel.setFont(font);
		titleLabel.setOpaque(false);
		genPanel.add(titleLabel);

		// 3. Set up the properties of the subtitles
		for (int i = 0; i < subtitleLabels.length; i++) {

			subtitleLabels[i] = new JLabel();
			subtitleLabels[i].setFont(font2);
			subtitleLabels[i].setBounds(70, 70 + 70 * i, 210, 85);
			genPanel.add(subtitleLabels[i]);

		}

		subtitleLabels[0].setText("Product:");
		subtitleLabels[1].setText("Category:");
		subtitleLabels[2].setText("Author:");
		subtitleLabels[3].setText("Publisher:");
		subtitleLabels[4].setText("Image Link:");
		subtitleLabels[5].setText("Description:");
		subtitleLabels[6].setText("Online Shop:");
		subtitleLabels[6].setBounds(70, 540, 210, 85);

		// 4. Set up the properties of the description text area and scroll.
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setFont(font3);
		descriptionTextArea.setLineWrap(true);
		descriptionTextArea.setWrapStyleWord(true);
		scroll = new JScrollPane(descriptionTextArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(270, 440, 580, 110);
		scroll.setBorder(new LineBorder(Color.BLACK, 2));
		genPanel.add(scroll);

		// 5. Set up the properties of the other text areas
		for (int i = 0; i < informationTextAreas.length; i++) {

			informationTextAreas[i] = new JTextArea();
			informationTextAreas[i].setFont(font3);
			informationTextAreas[i].setBorder(new LineBorder(Color.BLACK, 2));
			informationTextAreas[i].setBounds(270, 85 + 71 * i, 580, 45);
			genPanel.add(informationTextAreas[i]);

		}

		// 6. Set up the JButton for the type of product
		for (int i = 0; i < typeButton.length; i++) {

			typeButton[i] = new JButton();
			typeButton[i].setFont(font2);
			typeButton[i].addActionListener(this);
			typeButton[i].setBorder(new LineBorder(Color.BLACK, 1));
			typeButton[i].setBounds(480 + 230 * i, 25, 140, 45);
			genPanel.add(typeButton[i]);

		}

		typeButton[1].setBorder(new LineBorder(Color.BLACK, 3));
		typeButton[0].setText("Item");
		typeButton[1].setText("Book");

		// 7. Set up the JButton that allows the user to generate the product
		generateButton.setFont(font2);
		generateButton.setText("Save Barcode");
		generateButton.addActionListener(this);
		generateButton.setBorder(new LineBorder(Color.BLACK, 1));
		generateButton.setBounds(1000, 480, 220, 75);
		generateButton.setEnabled(false);
		genPanel.add(generateButton);

		// 8. Set up the text areas for the online shops
		websiteTextArea.setBounds(270, 560, 580, 45);
		websiteTextArea.setBorder(new LineBorder(Color.BLACK, 2));
		websiteTextArea.setText("Website (e.g. Walmart.ca)");
		websiteTextArea.setFont(font3);

		linkTextArea.setBounds(270, 610, 580, 45);
		linkTextArea.setBorder(new LineBorder(Color.BLACK, 2));
		linkTextArea.setText("URL (e.g. https://www.walmart.ca/en/ip/lg-uk6090-4k-smart-tv/6000198872212)");
		linkTextArea.setFont(font3);

		priceTextArea.setBounds(270, 660, 580, 45);
		priceTextArea.setBorder(new LineBorder(Color.BLACK, 2));
		priceTextArea.setText("Price ($)");
		priceTextArea.setFont(font3);

		genPanel.add(websiteTextArea);
		genPanel.add(linkTextArea);
		genPanel.add(priceTextArea);

		// 9. Set up the return button
		returnButton.setFont(font2);
		returnButton.setText("Back to Home");
		returnButton.addActionListener(this);
		returnButton.setBorder(new LineBorder(Color.BLACK, 1));
		returnButton.setBounds(980, 620, 250, 75);
		genPanel.add(returnButton);

		// 10. Set up the generate barcode button
		genButton.setFont(font2);
		genButton.setText("Create Barcode");
		genButton.addActionListener(this);
		genButton.setBorder(new LineBorder(Color.BLACK, 1));
		genButton.setBounds(1000, 180, 220, 65);
		genPanel.add(genButton);

		// 11. Set up the open editor button
		editButton.setFont(font2);
		editButton.setText("Edit Product");
		editButton.addActionListener(this);
		editButton.setBorder(new LineBorder(Color.BLACK, 1));
		editButton.setBounds(1000, 90, 220, 55);
		genPanel.add(editButton);

		// 12. Create combobox for the premade products
		readPremade();
		for (int index = 0; index < premadeProducts.size(); index++) {
			productComboBox.addItem(premadeProducts.get(index).getName());
		}
		productComboBox.setBounds(980, 20, 260, 35);
		productComboBox.setToolTipText("Select product.");
		productComboBox.setSelectedIndex(0);
		productComboBox.setFont(font4);
		genPanel.add(productComboBox);

		// 13. Set up the delete button
		delButton.setFont(font2);
		delButton.setText("Delete Product");
		delButton.addActionListener(this);
		delButton.setBorder(new LineBorder(Color.BLACK, 1));
		delButton.setBounds(1000, 390, 220, 55);
		delButton.setBackground(new Color(232, 103, 103));
		delButton.setVisible(false);
		genPanel.add(delButton);

	}

	private void readPremade() {

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

	}

	// This method sets up the JFrame
	private void frameSetup() {

		// 1. Setup the GUI
		setSize(1380, 735);
		setTitle("Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(177, 220, 249));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setFocusable(true);
		setLayout(null);

		// 2. Add the JPanel to the JFrame
		add(genPanel);

		// 3. Make the GUI visible
		setVisible(true);

	}

	// This method generates the 12-integer UPC code and the image of the barcode.
	private void createBarcode() {

		Random rand = new Random();
		// long randomUPC = (long) (Math.random() * (long)100000000000);

		// 1. Create a random, 12 - digit integer as the UPC code using the Random class
		int[] randomUPC = new int[12];

		for (int i = 0; i < 11; i++) {

			randomUPC[i] = rand.nextInt(10);
			System.out.print(randomUPC[i]);
			stringUPC = stringUPC + Integer.toString(randomUPC[i]);

		}

		// 2. Calculate the check digit
		int check = 0;

		// 2.1. Add all the digits in the odd positions together (the 1st, 3rd, 5th,
		// 7th, 9th, and 11th digits).
		for (int i = 0; i < 12; i += 2)
			check += randomUPC[i];

		// 2.2. Multiply the result by 3.
		check *= 3;

		// 2.3. Add to this the sum of the even-positioned digits (the 2nd, 4th, 6th,
		// 8th, and 10th) - do not include the check digit itself.
		for (int i = 1; i < 12; i += 2)
			check += randomUPC[i];

		// 2.4. "Chop off" everything except the final digit of your answer, the number
		// in the ones place.
		check %= 10;

		// 2.5. If that number is 0, that is the check digit.
		if (check == 0)
			check = 0;
		// 2.6. If that number is any other digit, subtract it from 10, and the result
		// is the check digit.
		else
			check = 10 - check;

		randomUPC[11] = check;
		System.out.print(randomUPC[11]);
		stringUPC = stringUPC + Integer.toString(randomUPC[11]);

//		stringUPC = "792713839168";

		// 3. Create the graphics object and begin drawing the barcode
		Graphics2D graphics = img.createGraphics();

		// 4. Set the background white
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 256, 151);

		int x1 = 30;
		int y1 = 20;
		int length = 102;

		// 5. Create the default black bars
		graphics.setColor(Color.BLACK);
		graphics.fillRect(x1, y1, 2, length);

		x1 += 4;

		graphics.fillRect(x1, y1, 2, length);

		x1 += 2;

		// 6. Write the barcode numbers
		graphics.setFont(new Font("Arial", Font.PLAIN, 21));
		graphics.drawString(stringUPC.substring(0, 1), x1 - 22, 127);
		graphics.drawString(stringUPC.substring(1, 6), x1 + 20, 127);

		int currentNumber = 0;

		graphics.setColor(Color.BLACK);

		System.out.println();

		length = 88;

		// 7. Draw the bars for each digit of the barcode
		for (int i = 0; i < 12; i++) {

			currentNumber = Character.getNumericValue(stringUPC.charAt(i));

			// 7.1. Change the length if it is the first or last digit
			if (i == 0 || i == 11)
				length = 102;
			else
				length = 88;

			// 7.2. If it is the middle digit, change the lengths to create the seperation
			// bars
			if (i == 6) {

				graphics.setColor(Color.WHITE);
				graphics.fillRect(x1, y1, 2, length);

				length = 102;

				graphics.setColor(Color.BLACK);
				graphics.fillRect(x1 += 2, y1, 2, length);

				graphics.setColor(Color.WHITE);
				graphics.fillRect(x1 += 2, y1, 2, length);

				graphics.setColor(Color.BLACK);
				graphics.fillRect(x1 += 2, y1, 2, length);

				graphics.setColor(Color.WHITE);
				graphics.fillRect(x1 += 2, y1, 2, length);

				x1 += 2;

				// 7.2.1. Write the final 6 digits under the barcode
				graphics.setColor(Color.BLACK);
				graphics.drawString(stringUPC.substring(6, 11), x1 + 5, 127);

				graphics.setColor(Color.WHITE);

				length = 88;

			}

			// 7.3. Write each of the 4 bars for each digit
			for (int j = 0; j < 4; j++) {

				// 7.3.1. Alternate between black and white
				if (graphics.getColor().equals(Color.BLACK))
					graphics.setColor(Color.WHITE);
				else
					graphics.setColor(Color.BLACK);

				// 7.3.2. Depending on the corresponding barcode digit, draw the correct width
				if (CORRESPONDING_BARCODE_DIG[currentNumber].charAt(j) == '1') {

					graphics.fillRect(x1, y1, 2, length);
					x1 += 2;
					System.out.print("1");

				} else if (CORRESPONDING_BARCODE_DIG[currentNumber].charAt(j) == '2') {

					graphics.fillRect(x1, y1, 4, length);
					x1 += 4;
					System.out.print("2");

				} else if (CORRESPONDING_BARCODE_DIG[currentNumber].charAt(j) == '3') {

					graphics.fillRect(x1, y1, 6, length);
					x1 += 6;
					System.out.print("3");

				} else {

					graphics.fillRect(x1, y1, 8, length);
					x1 += 8;
					System.out.print("4");
				}

			}

			System.out.println();

		}

		// 8. Draw the last ending bars
		graphics.setColor(Color.BLACK);
		graphics.fillRect(x1, y1, 2, 102);
		graphics.fillRect(x1 += 4, y1, 2, 102);

		// 9. Write the check digit
		graphics.drawString(stringUPC.substring(11, 12), x1 + 7, 127);

		graphics.setStroke(new BasicStroke(5));
		graphics.drawRect(0, 0, 255, 150);

		graphics.dispose();

		// 10. Display the barcode on a JLabel
		ImageIcon barcodeIcon = new ImageIcon(img);

		barcodeImg.setBounds(980, 260, 410, 205);
		barcodeImg.setIcon(barcodeIcon);
		genPanel.add(barcodeImg);

		// 11. Repaint to see the barcode
		repaint();

	}

	// This method saves the barcode as a PNG
	private void saveBarcode() {

		// Referneced from
		// https://stackoverflow.com/questions/3548140/how-to-open-and-save-using-java
		File file = new File("barcodes/" + stringUPC + ".png");

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(filter);
		fileChooser.setSelectedFile(file);
		fileChooser.setCurrentDirectory(new File("barcodes/"));
		fileChooser.showSaveDialog(null);

		fileChooser.setSelectedFile(file);
		try {
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// This method saves the information entered by the user into a csv file.
	private void saveProduct() throws IOException {

		// 1. Check that all of the text areas contain something
		boolean filled = true;

		for (JTextArea current : informationTextAreas) {

			if (current.getText().isEmpty())
				filled = false;

		}

		if (descriptionTextArea.getText().isEmpty() || websiteTextArea.getText().isEmpty()
				|| linkTextArea.getText().isEmpty() || priceTextArea.getText().isEmpty())
			filled = false;

		// 2. If all fields are filled
		if (filled == true) {

			// 2.1. Create the appropriate variables
			String product = informationTextAreas[0].getText().replaceAll(",", "");
			String category = informationTextAreas[1].getText().replaceAll(",", "");
			String imageLink = informationTextAreas[4].getText().replaceAll(",", "");
			String description = descriptionTextArea.getText().replaceAll(",", "");
			String website = websiteTextArea.getText().replaceAll(",", "");
			String link = linkTextArea.getText().replaceAll(",", "");
			String price = priceTextArea.getText().replaceAll(",", "");

			System.out.println(imageLink);

			// 2.2. Create the file writer
			FileWriter fileWriter = new FileWriter("Products.csv", true);

			// 2.3. Write the file depending on the type of product
			if (book == true) {

				String author = informationTextAreas[2].getText();
				String publisher = informationTextAreas[3].getText();
				Book book = new Book(product, stringUPC, category, description, imageLink, author, publisher, website,
						link, price);
				fileWriter.write(",\n" + book.toString());

			} else {

				String brand = informationTextAreas[2].getText();
				String manufacturer = informationTextAreas[3].getText();
				Item item = new Item(product, stringUPC, category, description, imageLink, brand, manufacturer, website,
						link, price);
				fileWriter.write(",\n" + item.toString());

			}

			// 2.4. Close the file writer
			fileWriter.close();

			if (genButton.isVisible() == true) {

				// 2.5 display success message
				JOptionPane.showMessageDialog(null, "Product successfully recorded! UPC:" + stringUPC, "Success!", 1);

				// 2.6. Save the barcode as a file
				saveBarcode();

			}

			// 2.7. Disable the generate barcode button
			generateButton.setEnabled(false);

			// 3. If one or more fields are empty, error warn the user
		} else
			JOptionPane.showMessageDialog(null, "Error! - One or more field(s)  is/are empty.", "Error!", 0);

	}

	// This method handles all the actions performed by the user (button clicks)
	@Override
	public void actionPerformed(ActionEvent event) {

		// 1. If the item button is clicked...
		if (event.getSource() == typeButton[0]) {

			// 1.1. Change the subtitles and clear any previous text
			subtitleLabels[2].setText("Brand:");
			subtitleLabels[3].setText("Manufacturer:");
			informationTextAreas[2].setText("");
			informationTextAreas[3].setText("");

			// 1.2. Change the appearance of the buttons
			book = false;
			typeButton[0].setBorder(new LineBorder(Color.BLACK, 3));
			typeButton[1].setBorder(new LineBorder(Color.BLACK, 1));

			// 2. If the book button is clicked...
		} else if (event.getSource() == typeButton[1]) {

			// 2.1. Change the subtitles and clear any previous text
			subtitleLabels[2].setText("Author:");
			subtitleLabels[3].setText("Publisher:");
			informationTextAreas[2].setText("");
			informationTextAreas[3].setText("");

			// 2.2. Change the appearance of the buttons
			book = true;
			typeButton[1].setBorder(new LineBorder(Color.BLACK, 3));
			typeButton[0].setBorder(new LineBorder(Color.BLACK, 1));

			// 3. If the generate barcode button is clicked...
		} else if (event.getSource() == generateButton) {

			// 3.1. Edit product, must remove old
			if (genButton.isVisible() == false) {

				stringUPC = premadeProducts.get(index).getUPC();
				premadeProducts.remove(index);
				System.out.println("removed" + index);

				try {
					saveFile();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			// 3.2. Save products
			try {
				saveProduct();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 4. If the return button is clicked
		} else if (event.getSource() == returnButton) {

			// 4.1. Dispose the current class and make a new home screen
			new HomeScreen();
			dispose();

			// 5. If the generate barcode button is clicked
		} else if (event.getSource() == genButton) {

			// 5.1. Create the barcode, disable the button and enable the user to save the
			// barcode
			createBarcode();
			genButton.setEnabled(false);
			generateButton.setEnabled(true);

			// 6. If the edit button is clicked...
		} else if (event.getSource() == editButton) {

			// 6.1. Retrieve product information
			index = productComboBox.getSelectedIndex();
//			decide = ((Object) premadeProducts.get(index)).getSimpleName();	

			informationTextAreas[0].setText(premadeProducts.get(index).getName());
			informationTextAreas[1].setText(premadeProducts.get(index).getCategory());
			informationTextAreas[4].setText(premadeProducts.get(index).getImage());
			descriptionTextArea.setText(premadeProducts.get(index).getDescription());
			websiteTextArea.setText(premadeProducts.get(index).getWebsite());
			linkTextArea.setText(premadeProducts.get(index).getURL());
			priceTextArea.setText(premadeProducts.get(index).getPrice());

			if (premadeProducts.get(index) instanceof Book) {

				informationTextAreas[2].setText(((Book) premadeProducts.get(index)).getAuthor());
				informationTextAreas[3].setText(((Book) premadeProducts.get(index)).getPublisher());
				subtitleLabels[2].setText("Author:");
				subtitleLabels[3].setText("Publisher:");
				typeButton[0].setBorder(new LineBorder(Color.BLACK, 1));
				typeButton[1].setBorder(new LineBorder(Color.BLACK, 3));

			} else {

				informationTextAreas[2].setText(((Item) premadeProducts.get(index)).getBrand());
				informationTextAreas[3].setText(((Item) premadeProducts.get(index)).getManufacturer());
				subtitleLabels[2].setText("Brand:");
				subtitleLabels[3].setText("Manufacturer:");
				typeButton[0].setBorder(new LineBorder(Color.BLACK, 3));
				typeButton[1].setBorder(new LineBorder(Color.BLACK, 1));
			}

			// 6.2. Remove unnessessary buttons
			productComboBox.setVisible(false);
			genButton.setEnabled(false);
			genButton.setVisible(false);
			editButton.setEnabled(false);
			editButton.setVisible(false);
			generateButton.setEnabled(true);
			generateButton.setText("Save Changes");
			delButton.setVisible(true);

			// 7. If the delete button is clicked
		} else if (event.getSource() == delButton) {

			// 7.1. Remove the product and save the file
			premadeProducts.remove(index);
			try {
				saveFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new HomeScreen();

		}

	}

	private void saveFile() throws FileNotFoundException {

		// 1. Create the file formatter
		Formatter fileFormatter = new Formatter("Products.csv");

		// 2. Write the file
		fileFormatter.format(
				"[Item/Book],[Name],[UPC],[Category],[Description],[Image],[Author/Brand],[Publisher/Manufacturer],[Website],[URL],[Price]");

		for (Product c : premadeProducts) {

			fileFormatter.format(",\n" + c.toString());

		}

		// 3. Close the file formatter
		fileFormatter.close();

		// 4. Display success message
		if (genButton.isVisible() == true)
			JOptionPane.showMessageDialog(null, "Product successfully deleted!", "Success!", 1);

	}

}