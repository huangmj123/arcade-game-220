package main;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Class: Astronaut
 * 
 * @author Joshua Mestemacher, Michael Huang
 *
 *
 *         The astronaut can move left, right, up, move in diagonal directions,
 *         or fall down due to gravity (depending on the player's keyboard
 *         presses). The astronaut can become invincible for a temporary portion
 *         of time if a InvincibilityPowerUp is touched. The astronaut can drop
 *         collectible objects they are carrying onto the rocket. The astronaut
 *         can shoot bullets, but that functionality is not handled here. The
 *         astronaut has an xVel and yVel that determines its movement every
 *         frame. This class stores whether the astronaut is carrying an object
 *         or not, and if so, what object is being carried.
 */
public class Astronaut extends GameObject {

	public static final int GRAVITY = 9;
	public static final int XWIDTH = 50;
	public static final int YWIDTH = 50;
//	private int x; // upper left hand corner x (can change later)
//	private int y; // upper left hand corner y (can change later)
	private boolean flying;
	private int xVel;
	private int yVel;
	private int invincibilityCountDown;
	private boolean isInvincible;
	private boolean isCarryingObject;
	private boolean facingLeft;
	private Collectible carriedObject;

	// creates two ImageIcon's to be displayed depending on the direction the
	// astronaut's facing
	ImageIcon right = new ImageIcon("Images/astronaut right.png");
	Image image = right.getImage();
	Image newimg = image.getScaledInstance(XWIDTH, YWIDTH, java.awt.Image.SCALE_SMOOTH);
	ImageIcon rightSprite = new ImageIcon(newimg);
	ImageIcon left = new ImageIcon("Images/astronaut left.png");
	Image image2 = left.getImage();
	Image newimg2 = image2.getScaledInstance(XWIDTH, YWIDTH, java.awt.Image.SCALE_SMOOTH);
	ImageIcon leftSprite = new ImageIcon(newimg2);

	public Astronaut(int x, int y) {
		super(x, y, GRAVITY, XWIDTH, YWIDTH, "Images/astronaut right.png");
		flying = false;
		xVel = 0;
		yVel = 0;

		this.isCarryingObject = false;
		this.carriedObject = null;
		this.isInvincible = false;
		this.invincibilityCountDown = 0;
	}

	public boolean isOnScreen() {
		if (super.getX() > 800 - XWIDTH)
			super.setX(0);
		if (super.getX() < 0)
			super.setX(800 - XWIDTH);
		if (super.getY() + YWIDTH >= 556) {
			super.setY(556 - (YWIDTH + 1));
		}
		if (super.getY() < 0)
			super.setY(0);
		return true;
	}

	public boolean isCarryingObject() {
		return isCarryingObject;
	}

	public void setCarryingObject(boolean isCarryingObject) {
		this.isCarryingObject = isCarryingObject;
	}

	public void startFlying() {
		flying = true;
	}

	public void stopFlying() {
		flying = false;
	}

	public void moveDueToGravity() {
		if (flying == false)
			super.setY(super.getY() + GRAVITY);
	}

	public void drawOn(Graphics2D g) {
		if (this.isInvincible) {
			this.invincibilityCountDown--;
			if (this.invincibilityCountDown == 0) {
				this.isInvincible = false;
			}
		}
		if (facingLeft) {
			leftSprite.paintIcon(null, g, super.getX(), super.getY());
		} else
			rightSprite.paintIcon(null, g, super.getX(), super.getY());
	}

	public Collectible getCarriedObject() {
		return carriedObject;
	}

	public void setCarriedObject(Collectible carriedObject) {
		this.carriedObject = carriedObject;
	}

	public void setxVel(int x) {
		xVel = x;
	}

	public void setyVel(int y) {
		yVel = y;
	}

	public void moveConsistently() {
		super.updatePosition(xVel, yVel);
	}

	public void setLeft(boolean maybe) {
		facingLeft = maybe;
	}

	public void dropObject() {
		this.carriedObject.setY(this.carriedObject.getY() + this.getYWidth());
		this.isCarryingObject = false;
		this.carriedObject.hasBeenTouchedByAstronaut = false;
		this.carriedObject = null;
	}

	public boolean isInvincible() {
		return isInvincible;
	}

	public void setInvincible(boolean isInvincible) {
		this.isInvincible = isInvincible;
	}

	public int getInvincibilityCountDown() {
		return invincibilityCountDown;
	}

	public void setInvincibilityCountDown(int invincibilityCountDown) {
		this.invincibilityCountDown = invincibilityCountDown;
	}

}