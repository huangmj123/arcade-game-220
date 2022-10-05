package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class: UserInputListener
 * 
 * @author Joshua Mestemacher, Michael Huang
 * 
 *         Acts as a ActionListener that takes in the player's keyboard presses,
 *         and updates the game to reflect them accordingly. For example if the
 *         player presses W on their keyboard, this class tells GameComponent to
 *         change the Astronaut's y velocity to -10. This class mainly updates
 *         the game by telling GameComponent to update whatever needs to be
 *         updated.
 * 
 * 
 *
 */
public class UserInputListener implements KeyListener {
	private GameComponent gameComponent;
	private Astronaut astro;
	private boolean facingLeft;
	private int levelNumber = 1; // 1 = level 1, 2 = level 2, etc

	public UserInputListener(GameComponent gameComponent) {
		this.gameComponent = gameComponent;
		astro = gameComponent.getAstronaut();
		facingLeft = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {// unused
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == 65) { // a to shoot bullets
			int bulletMotion = 10;
			if (facingLeft) {
				bulletMotion = -10;
			}
			Bullet bullet = new Bullet(astro.getX(), astro.getY(), bulletMotion, true);
			gameComponent.addObject(bullet);
			gameComponent.addBullet(bullet);
		}
		if (arg0.getKeyCode() == 27) { // ESC exit code
			System.out.println("Goodbye!");
			System.exit(0);
		}
		if (arg0.getKeyCode() == 37) { // Arrow Left move left
			facingLeft = true;
			astro.setLeft(true);
			astro.setxVel(-10);
		}
		if (arg0.getKeyCode() == 39) { // Arrow Right move right
			facingLeft = false;
			astro.setLeft(false);
			astro.setxVel(10);
		}
		if (arg0.getKeyCode() == 38) { // Arrow Up move up
			astro.startFlying();
			astro.setyVel(-10);
		}

		if (arg0.getKeyCode() == 83) { // S to drop object
			if (this.astro.isCarryingObject() == true) {
				this.astro.dropObject();
			}
		}

		if (arg0.getKeyCode() == 85 && this.levelNumber != 3) { // U loads next level
			this.levelNumber++;
			gameComponent.setLevel(gameComponent.getLevel() + 1);
			this.gameComponent.loadLevel("levels/level" + this.levelNumber + ".txt");
			astro = this.gameComponent.getAstronaut();
		}
		if (arg0.getKeyCode() == 68 && this.levelNumber != 1) { // D loads last level
			this.levelNumber--;
			gameComponent.setLevel(gameComponent.getLevel() - 1);
			this.gameComponent.loadLevel("levels/level" + this.levelNumber + ".txt");
			astro = this.gameComponent.getAstronaut();
		}

		if (arg0.getKeyCode() == 77) { // M "cheatcode" really so i can debug and look at interactions and such without
										// worrying about dying
			gameComponent.setArmor(Integer.MIN_VALUE);
			System.out.println("CheatCode Activated!");
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == 37) {
			astro.setxVel(0);
		}
		if (arg0.getKeyCode() == 39) {
			astro.setxVel(0);
		}
		if (arg0.getKeyCode() == 38) {
			astro.setyVel(0);
			astro.stopFlying();
		}

	}

	public void addAstro(Astronaut a) {
		astro = a;
	}

}
