package main;

/**
 * Class: Enemy
 * 
 * @author Joshua Mestemacher, Michael Huang
 *
 *
 *         Acts as a super class for the three enemy types. Stores whether the
 *         enemy has been hit, and what its x and y velocities are. Has a method
 *         to check whether the enemy is on screen or not, and place the enemy
 *         back on screen if not, and then flip the enemy's x or y velocities
 *         depending on whether the enemy bounced off the left or right edges or
 *         the top or bottom edges.
 *
 */
public class Enemy extends GameObject {
	protected boolean isHit;
	protected int xVel;
	protected int yVel;

	public Enemy(int xPos, int yPos, int gravity, int xWidth, int yWidth, String imgloc) {
		super(xPos, yPos, gravity, xWidth, yWidth, imgloc);
		this.isHit = false;
	}

	public void moveConsistently() {
		super.updatePosition(this.xVel, this.yVel);
	}

	public boolean isOnScreen() {
		if (super.getX() > 800 - super.getXWidth()) {
			super.setX(800 - super.getXWidth());
			this.xVel *= -1;
		}
		if (super.getX() < 0) {
			super.setX(0);
			this.xVel *= -1;
		}
		if (super.getY() > 600 - super.getYWidth()) {
			this.yVel *= -1;
			super.setY(600 - super.getYWidth());
		}
		if (super.getY() < 0) {
			this.yVel *= -1;
			super.setY(0);
		}
		return true;
	}

}
