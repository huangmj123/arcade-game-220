package main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

//import game_event_loop.Main;
//
/**
 * The main class for your arcade game.
 * 
 * You can design your game any way you like, but make the game start by running
 * main here.
 * 
 * Also don't forget to write javadocs for your classes and functions!
 * 
 * 
 * Class: Main
 * 
 * @author Joshua Mestemacher Michael Huangand Aidan Matthews
 * 
 *         This class starts the game, and shows the game window. It uses a
 *         timer to help run the game. All relevant action listeners for the
 *         game are set up here.
 *
 */
public class Main {

	public static final int DELAY = 50;

	public static void main(String[] args) {
		new Main();
	}

	/**
	 * @param args
	 */
	public Main() {
		JOptionPane.showMessageDialog(null,
				"Welcome! \nUse the arrow keys to move the astronaut\nPress A to fire!\nPress S to drop Items!\nDodge enemies, "
						+ "build the rocket and add Fuel to win!\n\nWant armor? a button connected to our initials does it");

		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setTitle("Arcade Game");

		GameComponent gameComponent = new GameComponent();
		frame.add(gameComponent);

		UserInputListener userInput = new UserInputListener(gameComponent);
		frame.addKeyListener(userInput);
		gameComponent.addListener(userInput);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		TimerListener tlist = new TimerListener(gameComponent, userInput);
		Timer timer = new Timer(DELAY, tlist);
		tlist.add(timer);
		timer.start();

	}

}
