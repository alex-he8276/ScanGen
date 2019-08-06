import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * This class creates an PurchasesWishlistGUI that extends the JFrame class. It
 * includes a constructor method that sets up the frame and panel. This class
 * provides the user details about their purchases and their wishlist.
 * Furthermore, it provides an option for the user to remove or search up one of
 * their saved products. It also shows the user their total spendings.
 * 
 * - Alex He
 */

public class PurchasesWishlistGUI extends JFrame implements ActionListener {

	// Various fonts for the JLabels
	Font font = new Font("Constantia", Font.BOLD, 40);
	Font font2 = new Font("Constantia", Font.ITALIC, 32);
	Font font3 = new Font("Arial", Font.PLAIN, 29);

	// Variables for the wishlist and purchased items
	ArrayList<Wishlist> wishlistItems = new ArrayList<Wishlist>();
	ArrayList<Purchases> purchasedItems = new ArrayList<Purchases>();

	// Double to hold the total spendings
	double spendings;

	// Labels to hold the titles
	JLabel[] titleLabels = new JLabel[2];

	// Label for hold total spending
	JLabel totalLabel = new JLabel();

	// Labels to hold the information of each product
	ArrayList<JLabel> pInfoLabels = new ArrayList<JLabel>();
	ArrayList<JLabel> wInfoLabels = new ArrayList<JLabel>();

	// Buttons to search the item
	ArrayList<JButton> pSearchButton = new ArrayList<JButton>();
	ArrayList<JButton> wSearchButton = new ArrayList<JButton>();

	// Buttons to delete the item
	ArrayList<JButton> pDelButton = new ArrayList<JButton>();
	ArrayList<JButton> wDelButton = new ArrayList<JButton>();

	// Label for the black seperation bar
	JLabel barLabel = new JLabel();

	// Button to return to the home screen
	JButton returnButton = new JButton();

	// JPanel for user to input product information
	JPanel pwPanel = new JPanel() {

		// Taken from stackoverflow; used to create a gradient background
		// https://stackoverflow.com/questions/14364291/jpanel-gradient-background
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			GradientPaint gp = new GradientPaint(0, 0, getBackground().brighter().brighter().brighter(), 0, getHeight(),
					getBackground().brighter().brighter());
			g2d.setPaint(gp);
			g2d.fillRect(0, 0, getWidth(), getHeight());

		}

	};

	// PurchasesWishlistGUI constructor
	public PurchasesWishlistGUI() {

		readInPurchases(); // Read in the purchases file
		readInWishlist(); // Read in the wishlist file

		panelSetup(); // Calls the method that sets up the panel.
		frameSetup(); // Calls the method that sets up the frame.

	}

	// This method reads in the products on the wishlist csv file
	private void readInWishlist() {

		String UPC;
		String productName;

		// 1. Read in all the wishlisted items
		try {

			Scanner input = new Scanner(new File("Wishlist.csv"));
			input.useDelimiter(",");
			input.nextLine();

			// 1.1.Program reads the csv file while there is a next line
			while (input.hasNextLine()) {

				// 1.1.1.1 Store the variables (product name, upc code)
				UPC = input.next().replaceAll("\r", "").replaceAll("\n", "");
				productName = input.next().replaceAll("\r", "").replaceAll("\n", "");

				// 1.1.1.2 Create a new wishlist object
				wishlistItems.add(new Wishlist(UPC, productName));

			}

			// 1.2 Close the file
			input.close();

			// 2. Catch the possible error
		} catch (FileNotFoundException error) {

			// 2.1 If the file does not exist, send error message
			System.out.println("Sorry wrong file - please check the name");

		}

	}

	// This method reads in the purchases from the csv file and calculates the total
	// spendings
	private void readInPurchases() {

		String UPC;
		String productName;
		String price;

		// 1. Read in all the purchased items
		try {

			Scanner input = new Scanner(new File("Purchases.csv"));
			input.useDelimiter(",");
			input.nextLine();

			// 1.1.Program reads the csv file while there is a next line
			while (input.hasNextLine()) {

				// 1.1.1.1 Store the variables (product name, upc code, price)
				UPC = input.next().replaceAll("\r", "").replaceAll("\n", "");
				productName = input.next().replaceAll("\r", "").replaceAll("\n", "");
				price = input.next().replaceAll("\r", "").replaceAll("\n", "");

				// 1.1.1.2 Create a new purchases object
				purchasedItems.add(new Purchases(UPC, productName, price));

				// 1.1.1.3. Increment total spendings
				price = price.substring(1);
				spendings += Double.parseDouble(price);

			}

			// 1.2 Close the file
			input.close();

			// 2. Catch the possible error
		} catch (FileNotFoundException error) {

			// 2.1 If the file does not exist, send error message
			System.out.println("Sorry wrong file - please check the name");

		}

	}

	// This method sets up the JPanel and all other GUI components
	private void panelSetup() {

		// 1. Set up the properties of the panel.
		pwPanel.setBounds(0, 0, 1380, 735);
		pwPanel.setBackground(new Color(232, 53, 53));
		pwPanel.setLayout(null);

		// 2. Set up the properties of the two title labels
		for (int i = 0; i < titleLabels.length; i++) {

			// 2.1. Set up the properties of the labels.
			titleLabels[i] = new JLabel();
			titleLabels[i].setFont(font);
			titleLabels[i].setBounds(70 + 680 * i, 20, 210, 85);
			pwPanel.add(titleLabels[i]);

		}

		titleLabels[0].setText("Purchases");
		titleLabels[1].setText("Wishlist");

		// 3. Set up the properties of the total spending label
		totalLabel.setText("  Total Spendings: $" + String.format("%.2f", spendings));
		totalLabel.setFont(font2);
		totalLabel.setBackground(Color.WHITE);
		totalLabel.setOpaque(true);
		totalLabel.setBorder(new LineBorder(Color.BLACK, 5));
		totalLabel.setBounds(250, 620, 400, 90);
		pwPanel.add(totalLabel);

		// 4. Set up a black separation bar
		barLabel.setBounds(670, 0, 20, 780);
		barLabel.setOpaque(true);
		barLabel.setBackground(Color.BLACK);
		pwPanel.add(barLabel);

		// 5. Depending on the number of purchased items, create the same number of
		// labels with the correct text and create 2 buttons, one to search the product
		// and one to delete the product
		for (int i = 0; i < purchasedItems.size(); i++) {

			pInfoLabels.add(new JLabel());
			pInfoLabels.get(i).setBounds(110, 90 + 60 * i, 800, 50);
			pInfoLabels.get(i).setText(purchasedItems.get(i).toString());
			pInfoLabels.get(i).setFont(font2);
			pwPanel.add(pInfoLabels.get(i));

			pSearchButton.add(new JButton());
			pSearchButton.get(i).setBounds(20, 95 + 60 * i, 40, 40);
			pSearchButton.get(i).setBackground(new Color(255, 142, 142));
			pSearchButton.get(i).addActionListener(this);
			pSearchButton.get(i).setBorderPainted(false);
			pSearchButton.get(i).setIcon(new ImageIcon(
					new ImageIcon("images/search.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
			pwPanel.add(pSearchButton.get(i));

			pDelButton.add(new JButton());
			pDelButton.get(i).setBounds(70, 107 + 60 * i, 40, 20);
			pDelButton.get(i).setBorderPainted(false);
			pDelButton.get(i).setBackground(Color.RED);
			pDelButton.get(i).addActionListener(this);
			pwPanel.add(pDelButton.get(i));

		}

		// 6. Depending on the number of wishlisted items, create the same number of
		// labels with the correct text and create 2 buttons, one to search the product
		// and one to delete the product
		for (int i = 0; i < wishlistItems.size(); i++) {

			wInfoLabels.add(new JLabel());
			wInfoLabels.get(i).setBounds(800, 90 + 60 * i, 800, 50);
			wInfoLabels.get(i).setText(wishlistItems.get(i).toString());
			wInfoLabels.get(i).setFont(font2);
			pwPanel.add(wInfoLabels.get(i));

			wSearchButton.add(new JButton());
			wSearchButton.get(i).setBounds(705, 95 + 60 * i, 40, 40);
			wSearchButton.get(i).setBackground(new Color(255, 142, 142));
			wSearchButton.get(i).addActionListener(this);
			wSearchButton.get(i).setBorderPainted(false);
			wSearchButton.get(i).setIcon(new ImageIcon(
					new ImageIcon("images/search.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
			pwPanel.add(wSearchButton.get(i));

			wDelButton.add(new JButton());
			wDelButton.get(i).setBounds(755, 107 + 60 * i, 40, 20);
			wDelButton.get(i).setBorderPainted(false);
			wDelButton.get(i).setBackground(Color.RED);
			wDelButton.get(i).addActionListener(this);
			pwPanel.add(wDelButton.get(i));

		}

		// 7. Set up the return button
		returnButton.setFont(font2);
		returnButton.setText("Back to Home");
		returnButton.addActionListener(this);
		returnButton.setBorder(new LineBorder(Color.BLACK, 2));
		returnButton.setBounds(1080, 630, 250, 75);
		pwPanel.add(returnButton);

	}

	// This method sets up the JFrame
	private void frameSetup() {

		// 1. Setup the GUI
		setSize(1380, 735);
		setTitle("Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setFocusable(true);
		setLayout(null);

		// 2. Add the JPanel to the JFrame
		add(pwPanel);

		// 3. Make the GUI visible
		setVisible(true);

	}

	// This method removes a product from the purchases list or the wishlist
	private void removeProduct(int index, String type) throws IOException {

		// 1. If the product is in the purchases list
		if (type.equals("purchase")) {

			// 1.1. Deduct the price of the item from the total spending and update the
			// JLabel
			spendings -= Double.parseDouble(purchasedItems.get(index).getCost().substring(1));
			totalLabel.setText("  Total Spendings: $" + String.format("%.2f", spendings));

			// 1.2. Remove the item on the purchases and rewrite the file.
			purchasedItems.remove(index);

			// 1.3. Create the file writer
			Formatter fileFormatter = new Formatter("Purchases.csv");

			// 1.4. Write the file
			fileFormatter.format("[UPC],[Product Name],[Price]");

			for (Purchases c : purchasedItems) {

				fileFormatter.format(",\n" + c.getUPC() + "," + c.getName() + "," + c.getCost());

			}

			// 1.5. Close the file formatter
			fileFormatter.close();

			// 1.6. display success message
			JOptionPane.showMessageDialog(null, "Product successfully removed!", "Success!", 1);

			// 2. If the product is in the wishlist
		} else {

			// 2.1. Remove the item on the purchases and rewrite the file.
			wishlistItems.remove(index);

			// 2.2. Create the file writer
			Formatter fileFormatter = new Formatter("Wishlist.csv");

			// 2.3. Write the file
			fileFormatter.format("[UPC],[Product Name]");

			for (Wishlist c : wishlistItems) {

				fileFormatter.format(",\n" + c.getUPC() + "," + c.getName());

			}

			// 2.4. Close the file formatter
			fileFormatter.close();

			// 2.5. display success message
			JOptionPane.showMessageDialog(null, "Product successfully removed!", "Success!", 1);

		}

		// 3. Reload everything so that the item is removed from the JLabel and cannot
		// be seen by the user.
		new PurchasesWishlistGUI();
		dispose();

	}

	// This method handles all the actions performed by the user (button clicks)
	@Override
	public void actionPerformed(ActionEvent event) {

		// 1. Loop through all the possible buttons for purchases
		for (int i = 0; i < purchasedItems.size(); i++) {

			// 1.1. If the delete button is clicked...
			if (event.getSource() == pDelButton.get(i)) {

				// 1.1.1. Call the remove method.
				try {
					removeProduct(i, "purchase");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// 1.2. If the search button is clicked...
			} else if (event.getSource() == pSearchButton.get(i)) {

				// 1.2.1. Create a new instance of the ScannerGUI
				new ScannerGUI(purchasedItems.get(i).getUPC());
				dispose();

			}

		}

		// 2. Loop through all the possible buttons for wishlist
		for (int i = 0; i < wishlistItems.size(); i++) {

			// 2.1. If the remove button is clicked...
			if (event.getSource() == wDelButton.get(i)) {

				// 2.1.1. Call the remove method.
				try {
					removeProduct(i, "wishlist");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// 2.2. If the search button is clicked...
			} else if (event.getSource() == wSearchButton.get(i)) {

				// 2.2.1. Create a new instance of the ScannerGUI
				new ScannerGUI(wishlistItems.get(i).getUPC());
				dispose();

			}

		}

		// 3. If the return button is clicked
		if (event.getSource() == returnButton) {

			// 3.1. Dispose the current class and make a new home screen
			new HomeScreen();
			dispose();

		}

	}

}