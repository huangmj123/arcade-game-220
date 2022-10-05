package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * @author Joshua Mestemacher, Michael Huang
 * 
 *         Class: GameComponent
 * 
 *         handles the updating of the whole game while running. It updates the
 *         game to reflect what is going on currently, and then draws everything
 *         on screen. All of the collisions are handled here. This class has a
 *         method to load different levels. This class handles checking for
 *         winning or losing a level. This class stores and displays the current
 *         score for the player.
 * 
 * 
 * 
 */

public class GameComponent extends JComponent {
	private Astronaut astro;
	private int astronautLives = 3;
	private UserInputListener userInput;
	private ArrayList<Platform> platforms;
	private ArrayList<GameObject> allObjects;
	private ArrayList<Enemy> enemy1s;
	private ArrayList<Bullet> bullets;
	private ArrayList<EnemyTwo> enemy2s;
	private ArrayList<RocketPiece> pieces;
	private ArrayList<Fuel> fuels;
	private ArrayList<ValuableResource> resources;
	private ArrayList<Collectible> collectibles;
	private ArrayList<InvincibilityPowerUp> invinciblePowerUps;
	private ArrayList<RocketPiece> piecesAdded;
	private int armorCheck = 0;
	private Rocket rocket;
	private int level = 1;

	private boolean rocketBuilt;
	private int fuelCap;
	private int fuelcurr;
	private int score = 0;
	private Font writing = new Font("TimesRoman", Font.PLAIN, 24);
	private boolean readyToGo;
	private boolean rocketOff;
	private int framesLift;
	private int partCount = 0;
	private int partCap;

	ImageIcon icon2 = new ImageIcon("Images/backdrop.jpg"); // creates an object with the image
	// creates a new usable object with resized image

	public GameComponent() {
		this.loadLevel("levels/level1.txt");
	}

	public void updateGame() {
		if (readyToGo) {
			if (framesLift < 100) {
				rocket.setY(rocket.getY() - 5);
				for (int i = 0; i < piecesAdded.size(); i++) {
					piecesAdded.get(i).setY(rocket.getY() - piecesAdded.get(i).getYWidth() * (i + 1));
				}
				if (framesLift == 0) {
					for (Fuel fuel : fuels) {
						this.removeObject(fuel);
					}

				}
				framesLift++;
			} else
				rocketOff = true;
		} else { // main updates
			armorCheck++;
			for (int i = 0; i < allObjects.size(); i++) {
				allObjects.get(i).moveDueToGravity();
				allObjects.get(i).moveConsistently();
				if (allObjects.get(i).isOnScreen() == false) {
					allObjects.remove(i);
					i--;
				}
				allObjects.get(i).updatePosition(0, 0);
			}
			// next set of for loops check various collisions
			for (Platform platform : platforms) {
				if (checkCollision(astro, platform)) {
					if (astro.getY() <= platform.getY())
						astro.setY(platform.getY() - (astro.getYWidth() + 1));
					else
						astro.setY(platform.getY() + platform.getYWidth() + 2);
				}
				for (int i = 0; i < collectibles.size(); i++) {
					if (checkCollision(collectibles.get(i), platform)) {
						collectibles.get(i).setY(platform.getY() - collectibles.get(i).getYWidth());
					}
				}
			}
			for (Bullet bullet : bullets) {
				for (int i = 0; i < enemy2s.size(); i++) {
					if (checkCollision(enemy2s.get(i), bullet)) {
						removeObject(enemy2s.get(i));
						ArrayList<Bullet> lastBullets = enemy2s.get(i).getBullets();
						for (Bullet lasts : lastBullets) {
							allObjects.add(lasts);
						}
						enemy2s.get(i).clearAll();
						score += 100;
						enemy2s.remove(i);
						i--;
						bullet.setCollision();
					}
				}
				for (int i = 0; i < enemy1s.size(); i++) {
					if (checkCollision(enemy1s.get(i), bullet)) {
						removeObject(enemy1s.get(i));
						score += 50;
						enemy1s.remove(i);
						i--;
						bullet.setCollision();
					}
				}
			}

			for (EnemyTwo enemy2 : enemy2s) {
				ArrayList<Bullet> enemyBullet = enemy2.getBullets();
				for (Bullet bullet : enemyBullet) {
					if (checkCollision(astro, bullet) && armorCheck > 10 && !astro.isInvincible()) {
						astronautLives--;
						armorCheck = 0;
						bullet.setCollision();
						score -= 100;
					}
				}
				if (checkCollision(astro, enemy2) && armorCheck > 10 && !astro.isInvincible()) {
					astronautLives--;
					armorCheck = 0;
					score -= 100;
				}
			}
			for (Enemy enemy : enemy1s) {
				if (checkCollision(astro, enemy) && armorCheck > 10 && !astro.isInvincible()) {
					astronautLives--;
					armorCheck = 0;
					score -= 100;
				}
			}
			for (RocketPiece piece : pieces) {
				if (checkCollision(piece, astro) && !piece.isOnRocket() && !astro.isCarryingObject()) {
					piece.setHasBeenTouchedByAstronaut(true);
					astro.setCarryingObject(true);
					astro.setCarriedObject(piece);
				}
			}
			for (Fuel fuel : fuels) {
				if (checkCollision(fuel, astro) && !fuel.isOnRocket() && !astro.isCarryingObject() && rocketBuilt) {
					fuel.setHasBeenTouchedByAstronaut(true);
					astro.setCarryingObject(true);
					astro.setCarriedObject(fuel);
					score += 200;
				}
			}
			for (RocketPiece piece : pieces) {
				if (checkCollision(rocket, piece) && !piece.isOnRocket()) {
					piece.setHasBeenTouchedByAstronaut(false);
					piece.drop(rocket.getNumberOfParts(), rocket.getX(), rocket.getY(), rocket.getXWidth());
					rocket.setNumberOfParts(rocket.getNumberOfParts() + 1);
					piece.setOnRocket(true);
					piecesAdded.add(piece);
					astro.setCarryingObject(false);
					partCount++;
					if (partCount != partCap) {
						piece.setImage("Images/body.png");
					}
					if (partCount == partCap) {
						rocketBuilt = true;
						piece.setImage("Images/nosecone.png");
					}
					astro.setCarriedObject(null);
				}
			}
			for (Fuel fuel : fuels) {
				if (checkCollision(rocket, fuel) && !fuel.isOnRocket()) {
					fuel.setHasBeenTouchedByAstronaut(false);
					fuel.drop(rocket.getFuel(), rocket.getX(), rocket.getY(), rocket.getYWidth());
					rocket.setFuel(rocket.getFuel() + 1);
					fuel.setOnRocket(true);
					astro.setCarryingObject(false);
					fuelcurr++;
					if (fuelcurr == fuelCap) {
						readyToGo = true;
						this.removeObject(astro);
						rocket.setYWidth(rocket.getYWidth() + 10);
						rocket.setImage("Images/firing booster.png");
					}
					astro.setCarriedObject(null);

				}
			}
			for (int i = 0; i < resources.size(); i++) {
				if (checkCollision(resources.get(i), astro)) {
					score += 350;
					allObjects.remove(resources.get(i));
					resources.remove(i);
				}
			}

			for (int i = 0; i < invinciblePowerUps.size(); i++) {
				if (checkCollision(invinciblePowerUps.get(i), astro)) {
					astro.setInvincible(true);
					astro.setInvincibilityCountDown(200);
					allObjects.remove(invinciblePowerUps.get(i));
					invinciblePowerUps.remove(invinciblePowerUps.get(i));
				}
			}
		}
		repaint();
	}

	public void drawScreen() {
		this.getIgnoreRepaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		icon2.paintIcon(null, g2, 0, 0);
		for (int i = 0; i < allObjects.size(); i++) {
			allObjects.get(i).drawOn(g2);
		}
		if (!readyToGo)
			astro.drawOn(g2); // draws astronaut on top of carried objects
		g.setFont(writing);
		g.setColor(Color.WHITE);
		g.drawString("Astronaut Lives: " + astronautLives, 25, 25);
		g.drawString("Score " + score, 650, 25);
		if (fuelcurr != fuelCap)
			g.drawString((int) (fuelcurr * (100.0 / fuelCap)) + "%", rocket.getX() + rocket.getXWidth() + 10,
					rocket.getY());
		if (astro.isInvincible()) {
			g.drawString("Invincibility On ", 300, 25);
		}
	}

	public Astronaut getAstronaut() {
		return astro;
	}

	public void loadLevel(String filename) { // this loads a level from a given text file
		Scanner scanner;
		this.platforms = new ArrayList<>();
		allObjects = new ArrayList<>();
		enemy1s = new ArrayList<>();
		pieces = new ArrayList<>();
		fuels = new ArrayList<>();
		bullets = new ArrayList<>();
		enemy2s = new ArrayList<>();
		resources = new ArrayList<>();
		piecesAdded = new ArrayList<>();
		fuelcurr = 0;
		rocketBuilt = false;
		collectibles = new ArrayList<>();
		rocketOff = false;
		readyToGo = false;
		framesLift = 0;
		partCount = 0;
		partCap = 0;
		invinciblePowerUps = new ArrayList<>();

		try {
			scanner = new Scanner(new File(filename));
			boolean generatedTester = false;
			while (scanner.hasNext()) {
				String line = scanner.nextLine().trim();
				String[] splitString = line.split(",");
				if (splitString[0].equals("Astronaut_Position")) {
					this.astro = new Astronaut(Integer.parseInt(splitString[1]), Integer.parseInt(splitString[2]));
					allObjects.add(this.astro);
				}
				if (generatedTester == false) {
					EnemyThree tester = new EnemyThree(400, 20, astro);
					this.allObjects.add(tester);
					this.enemy1s.add(tester);
					generatedTester = true;
				}
				if (splitString[0].equals("Platform_Position")) {
					Platform platform = new Platform(Integer.parseInt(splitString[1]), Integer.parseInt(splitString[2]),
							Integer.parseInt(splitString[3]));
					this.platforms.add(platform);
					allObjects.add(platform);

				}
				if (splitString[0].equals("EnemyOne_Position")) {
					EnemyOne enemyOne = new EnemyOne(Integer.parseInt(splitString[1]),
							Integer.parseInt(splitString[2]));
					this.allObjects.add(enemyOne);
					enemy1s.add(enemyOne);
				}
				if (splitString[0].equals("EnemyTwo_Position")) {
					EnemyTwo enemyTwo = new EnemyTwo(Integer.parseInt(splitString[1]),
							Integer.parseInt(splitString[2]));
					this.allObjects.add(enemyTwo);
					enemy2s.add(enemyTwo);
				}
				if (splitString[0].equals("Rocket_Position")) {
					Rocket rocket = new Rocket(Integer.parseInt(splitString[1]), Integer.parseInt(splitString[2]));
					this.allObjects.add(rocket);
					this.rocket = rocket;
				}
				if (splitString[0].equals("Rocket_Piece_Position")) {
					RocketPiece piece = new RocketPiece(Integer.parseInt(splitString[1]),
							Integer.parseInt(splitString[2]), astro);
					this.allObjects.add(piece);
					this.pieces.add(piece);
					this.collectibles.add(piece);
				}
				if (splitString[0].equals("Fuel_Position")) {
					Fuel fuel = new Fuel(Integer.parseInt(splitString[1]), Integer.parseInt(splitString[2]), astro);
					this.allObjects.add(fuel);
					this.fuels.add(fuel);
					this.collectibles.add(fuel);
				}
				if (splitString[0].equals("Valuable_Resource_Position")) {
					ValuableResource resource = new ValuableResource(Integer.parseInt(splitString[1]),
							Integer.parseInt(splitString[2]));
					this.allObjects.add(resource);
					this.resources.add(resource);
					this.collectibles.add(resource);
				}
				if (splitString[0].equals("Part_Cap")) {
					partCap = Integer.parseInt(splitString[1]);
				}
				if (splitString[0].equals("Invincibility_PowerUp_Position")) {
					InvincibilityPowerUp powerUp = new InvincibilityPowerUp(Integer.parseInt(splitString[1]),
							Integer.parseInt(splitString[2]));
					this.allObjects.add(powerUp);
					this.invinciblePowerUps.add(powerUp);
					this.collectibles.add(powerUp);
				}
				fuelCap = fuels.size();

			}
			int generated;
			if (this.level >= 3) {
				for (int i = 0; i < level; i++) {
					generated = (int) (3 * (Math.random()));
					if (generated == 0) {
						EnemyOne enemy = new EnemyOne((int) (500 * Math.random()), (int) (300 * Math.random()));
						enemy1s.add(enemy);
						allObjects.add(enemy);
					} else if (generated == 1) {
						EnemyTwo enemy = new EnemyTwo((int) (500 * Math.random()), (int) (300 * Math.random()));
						enemy2s.add(enemy);
						allObjects.add(enemy);
					} else {
						EnemyThree enemy = new EnemyThree((int) (500 * Math.random()), (int) (300 * Math.random()),
								astro);
						enemy1s.add(enemy);
						allObjects.add(enemy);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error, Level File not found");
			System.exit(0);
		}

	}

	public void addObject(GameObject object) {
		allObjects.add(object);
	}

	public void removeObject(GameObject object) {
		boolean removed = false;
		int i = 0;
		while (!removed) {
			if (allObjects.get(i) == object) {
				allObjects.remove(i);
				removed = true;
			}
			i++;
		}
	}

	public void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}

	public boolean checkCollision(GameObject object1, GameObject object2) {
		boolean collision = false;
		if ((object1.getX() + object1.getXWidth() > object2.getX()
				&& object1.getX() < object2.getX() + object2.getXWidth())
				&& (object1.getY() + object1.getYWidth() > object2.getY()
						&& object1.getY() < object2.getY() + object2.getYWidth())) {
			collision = true;
		}
		return collision;
	}

	public int getLives() {
		return astronautLives;
	}

	public void setLives(int i) {
		astronautLives = i;
	}

	public void addListener(UserInputListener l) {
		userInput = l;
		userInput.addAstro(astro);
	}

	public boolean getIsBuilt() {
		return rocketBuilt;
	}

	public int getFuelCap() {
		return fuelCap;
	}

	public int getCurrFuel() {
		return fuelcurr;
	}

	public void setLevel(int i) {
		level = i;
	}

	public int getLevel() {
		return level;
	}

	public void setScore(int x) {
		score = x;
	}

	public int getScore() {
		return score;
	}

	public boolean rocketLift() {
		return rocketOff;
	}

	public void setArmor(int i) {
		armorCheck = i;
	}
}
