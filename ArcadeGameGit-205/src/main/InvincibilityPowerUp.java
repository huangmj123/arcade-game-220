package main;

/**
 * Class: InvincibilityPowerUp
 * 
 * @author Joshua Mestemacher, Michael Huang
 * 
 *         Acts as a collectible powerup that when touched by the astronaut
 *         gives the astronaut 3-4 seconds of invincibility where they can't be
 *         hurt by enemies running into them or enemy bullets. Disappears after
 *         being touched by the astronaut. Has an image of a star.
 *
 */
public class InvincibilityPowerUp extends Collectible {
	public final static int WIDTH = 30;
	public final static int HEIGHT = 30;

	public InvincibilityPowerUp(int x, int y) {
		super(x, y, WIDTH, HEIGHT, "Images/invincibility.png");
	}
}
