import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * This class creates an Home Screen GUI that extends the JFrame class. It
 * includes a constructor method that sets up the frame and panel. It has 3
 * buttons that take the user to new screens.
 * 
 * - Alex He
 */

public class HomeScreen extends JFrame implements ActionListener {

	// Various fonts for the JLabels
	Font font = new Font("Constantia", Font.ITALIC, 130);
	Font font2 = new Font("Constantia", Font.ITALIC, 32);
	Font font3 = new Font("Comic Sans MS", Font.PLAIN, 29);

	// JPanel for the home screen
	JPanel homePanel = new JPanel();

	// Label for holding the title
	JLabel titleLabel = new JLabel();

	// Label for holding the title logo

	// Button for entering the next frame
	JButton nextFrameButton = new JButton();

	// Label for holding the background images
	JLabel imageLabel = new JLabel();

	// Label for the background image
	JLabel backgroundLabel = new JLabel();

	// Buttons to take the user to new screens
	JButton[] buttons = new JButton[3];

	// Home screen GUI constructor
	public HomeScreen() {

		panelSetup(); // Calls the method that sets up the panel.
		frameSetup(); // Calls the method that sets up the frame.

	}

	// This method sets up the JFrame
	private void frameSetup() {

		// 1. Setup the GUI
		setSize(1380, 735);
		setTitle("Home Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setFocusable(true);
		setLayout(null);

		// 2. Add the JPanel to the JFrame
		add(homePanel);

		// 3. Make the GUI visible
		setVisible(true);

	}

	// This method sets up the JPanel and all other GUI components
	private void panelSetup() {

		// 1. Set up the properties of the panel.
		homePanel.setBounds(0, 0, 1380, 735);
		homePanel.setLayout(null);

		// 2. Set up the properties of the title label that holds an background image
		backgroundLabel.setBounds(0, 0, 1380, 735);
		backgroundLabel.setIcon(new ImageIcon(new ImageIcon("images/waveBackground.jpg").getImage()
				.getScaledInstance(1380, 735, Image.SCALE_SMOOTH)));
		add(backgroundLabel);

		// 3. Set up the properties of the title label that holds the title
		titleLabel.setBounds(530, 70, 500, 200);
		titleLabel.setText("ScanGen");
		titleLabel.setFont(font);
		titleLabel.setForeground(Color.WHITE);
		backgroundLabel.add(titleLabel);

		// 4. Set up the image label that holds the image logo
		imageLabel.setBounds(300, -50, 400, 400);
		imageLabel.setIcon(new ImageIcon(
				new ImageIcon("images/scanner.png").getImage().getScaledInstance(280, 235, Image.SCALE_SMOOTH)));
		backgroundLabel.add(imageLabel);

		// 5. Set up the properties of the 3 buttons that allow the user to go to
		// following GUIs
		for (int i = 0; i < buttons.length; i++) {

			buttons[i] = new JButton();
			buttons[i].setBorder(new LineBorder(Color.BLACK, 4));
			buttons[i].setBounds(350 + 400 * i, 320, 310, 100);
			buttons[i].setFont(font2);
			buttons[i].addActionListener(this);
			backgroundLabel.add(buttons[i]);

		}

		buttons[2].setBounds(550, 500, 310, 100);
		buttons[0].setText("Barcode Scanner");
		buttons[1].setText("Barcode Generator");
		buttons[2].setText("Purchases & Wishlist");

	}

	// This method handles all the actions performed by the user (button clicks)
	@Override
	public void actionPerformed(ActionEvent event) {

		// 1. If the barcode scanner button is clicked...
		if (event.getSource() == buttons[0]) {

			// 1.1. Make the current frame invisible, and create a new instance of the
			// ScannerGUI
			new ScannerGUI();
			dispose();

			// 2. If the barcode generator button is clicked...
		} else if (event.getSource() == buttons[1]) {

			// 2.1. Make the current frame invisible, and create a new instance of the
			// GeneratorGUI
			new GeneratorGUI();
			dispose();

			// 3. If the purchases/wishlist button is clicked...
		} else if (event.getSource() == buttons[2]) {

			// 3.1. Make the current frame invisible, and create a new instance of the
			// PurchasesWishlistGUI
			new PurchasesWishlistGUI();
			dispose();

		}

	}

}