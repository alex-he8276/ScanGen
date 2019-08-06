import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

/**
 * This class is used to play the background music on a loop by using a timer.
 * 
 * - Alex He
 */

public class Music {

	// ActionListener method for the timer
	public static ActionListener play = new ActionListener() {

		public void actionPerformed(ActionEvent evt) {

			// 1. Play the music clip
			playMusic();

		}
	};

	// Time for the music
	public static Timer musicTimer = new Timer(326000, play);

	// This method plays the music
	public static void playMusic() {

		// 1. Create the file
		File fileA = new File("music/background.wav");

		// 2. Play the music
		try {
			Clip background = AudioSystem.getClip();
			background.open(AudioSystem.getAudioInputStream(fileA));
			background.start();
		} catch (Exception e) {
			System.out.println("Error1");
		}

	}

}
