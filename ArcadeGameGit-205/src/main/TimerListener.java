package main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * Class: TimerListener
 * 
 * @author Joshua Mestemacher, Michael Huang
 *
 *
 *         This class acts as the ActionListener for the timer. It causes
 *         GameComponent to update the game every tick, and and checks for if
 *         the player has died or not in the game after GameComponent does that.
 *         If so, it prompts the player to input their name, then it flashes a
 *         small screen where 3 buttons are shown that either allow the player
 *         to start a new game, exit the game, or input their score into a list
 *         that sorts names and their inputed scores by the highest scores and
 *         displays them as such to the player in a new window. (basically, its
 *         a high score list with that players can input their score into to
 *         have their score sorted there along with their name, and then the
 *         list displays itself to the player in a new window). This class also
 *         checks if the current level has been won, and moves to the next level
 *         if so.
 * 
 *
 *
 *
 */
public class TimerListener implements ActionListener {
	private GameComponent gameComponent;
	private Timer timer;
	private UserInputListener input;

	public TimerListener(GameComponent g, UserInputListener i) {
		gameComponent = g;
		input = i;
	}

	public void add(Timer t) {
		timer = t;
	}

	public void actionPerformed(ActionEvent e) {
		gameComponent.updateGame();
		JPanel panel = new JPanel();
		if (gameComponent.getLives() == 0) {
			try {
				panel = runPostGame(gameComponent);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JFrame frame = new JFrame("Loser");
			frame.add(panel, BorderLayout.NORTH);
			frame.setSize(400, 100);
			JPanel buttons = new JPanel();
			JButton button1 = new JButton("New Game?");
			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					frame.dispose();
					gameComponent.setLives(3);
					gameComponent.setLevel(1);
					gameComponent.setScore(0);
					gameComponent.loadLevel("levels/level1.txt");
					gameComponent.addListener(input);
					timer.start();
				}
			});
			JButton button2 = new JButton("Exit");
			button2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			JButton button3 = new JButton("Display Leaderboard"); // high score list display button
			button3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					JLabel text = new JLabel();
					JFrame leaderboard = new JFrame();
					try {
						text = getLeaderBoard();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					text.setFont(new Font("Verdana", Font.PLAIN, 20));
					JPanel textPanel = new JPanel();
					textPanel.add(text);
					leaderboard.add(textPanel, BorderLayout.WEST);
					leaderboard.setSize(500, 750);
					leaderboard.setVisible(true);
				}
			});
			buttons.add(button1);
			buttons.add(button2);
			buttons.add(button3);
			frame.add(buttons);
			frame.setSize(400, 150);
			frame.setVisible(true);

			timer.stop();
		}
		if (gameComponent.getIsBuilt() && gameComponent.getFuelCap() == gameComponent.getCurrFuel()
				&& gameComponent.rocketLift()) {
			JOptionPane.showMessageDialog(null,
					"You beat level " + gameComponent.getLevel() + "!\n next level loading");
			if (gameComponent.getLevel() == 1)
				gameComponent.loadLevel("levels/level2.txt");
			else
				gameComponent.loadLevel("levels/level3.txt");
			gameComponent.addListener(input);
			gameComponent.setLives(gameComponent.getLives() + 1);
			gameComponent.setLevel(gameComponent.getLevel() + 1);

		}

	}

	public static JPanel runPostGame(GameComponent gameComponent) throws IOException { // high score sorting method

		ArrayList<String> names = new ArrayList<>();
		ArrayList<Integer> scores = new ArrayList<>();
		String name = JOptionPane.showInputDialog("Good Game! Initials for HighScore list(NO NUMBERS): ");
		try {
			Scanner scanner = new Scanner(new File("Scores/listofscores.txt"));
			while (scanner.hasNext()) {
				String line = scanner.nextLine().trim();
				String[] splitString = line.split(",");
				names.add(splitString[0]);
				scores.add(Integer.parseInt(splitString[1]));
				FileWriter fwOb = new FileWriter("Scores/listofscores.txt", false);
				PrintWriter pwOb = new PrintWriter(fwOb, false);
				pwOb.flush();
				pwOb.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int newScore = gameComponent.getScore();
		int idx = findPosition(newScore, scores);
		scores.add(idx, newScore);
		names.add(idx, name);
		int highscore = scores.get(0);
		String bestPlay = names.get(0);
		FileWriter fwOb = new FileWriter("Scores/listofscores.txt", true);
		for (int i = 0; i < scores.size(); i++) {
			fwOb.write(names.get(i) + "," + scores.get(i) + "\n");
		}
		fwOb.close();
		// create a button to display the lists
		// rewrite the whole file

		JPanel textPanel = new JPanel();
		JLabel text = new JLabel("<html>GAME OVER  Score: " + gameComponent.getScore() + "  Lost level: "
				+ gameComponent.getLevel() + "<br/> HighScore of " + highscore + " by " + bestPlay + "<html>",
				SwingConstants.CENTER);
		textPanel.add(text);
		return textPanel;
	}

	private static int findPosition(int newScore, ArrayList<Integer> scores) {
		if (scores.size() == 0 || newScore > scores.get(0))
			return 0;
		if (newScore < scores.get(scores.size() - 1))
			return scores.size();
		boolean notFound = true;
		int idx = 0;
		while (notFound && idx < scores.size() - 1) {
			idx++;
			if (newScore <= scores.get(idx - 1) && newScore >= scores.get(idx)) {
				notFound = false;
			}
		}
		return idx;
	}

	protected JLabel getLeaderBoard() throws IOException { // gets highscore leaderboard
		ArrayList<String> names = new ArrayList<>();
		ArrayList<Integer> scores = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File("Scores/listofscores.txt"));
			while (scanner.hasNext()) {
				String line = scanner.nextLine().trim();
				String[] splitString = line.split(",");
				names.add(splitString[0]);
				scores.add(Integer.parseInt(splitString[1]));

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String leaders = "<html>";
		for (int i = 1; i <= names.size(); i++) {
			leaders = leaders + "" + (i) + ". " + names.get(i - 1) + " Score: " + scores.get(i - 1) + "<br/>";
		}
		leaders = leaders + "<html>";
		return new JLabel(leaders);
	}

}
