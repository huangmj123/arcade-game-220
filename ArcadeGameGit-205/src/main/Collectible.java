package main;

import java.awt.Graphics2D;

/**
 * 
 * Class: Collectible
 * 
 * @author Joshua Mestemacher Michael Huang
 * 
 *         Acts as a super class for the various types of collectible objects,
 *         which are Fuel, RocketPiece, ValuableResource, and
 *         InvincibilityPowerUp. Stores whether the collectible has been touched
 *         by the astronaut or not, whether the collectible is offscreen or not,
 *         and whether the collectible is on the rocket or not.
 * 
 * 
 * 
 * 
 */
public class Collectible extends GameObject {
	public static final int GRAVITY = 9;
	protected boolean hasBeenTouchedByAstronaut;
	protected boolean onRocket;
	protected Astronaut astro;
	private boolean isOffScreen;

	public Collectible(int x, int y, int xWidth, int yWidth, String imgloc) {
		super(x, y, GRAVITY, xWidth, yWidth, imgloc);
		this.hasBeenTouchedByAstronaut = false;
		this.onRocket = false;
		this.isOffScreen = false;
	}

	public boolean isOnRocket() {
		return this.onRocket;
	}

	public void setOnRocket(boolean onRocket) {
		this.onRocket = onRocket;
	}

	public void setHasBeenTouchedByAstronaut(boolean hasBeenTouchedByAstronaut) {
		this.hasBeenTouchedByAstronaut = hasBeenTouchedByAstronaut;
	}

	public void moveDueToGravity() {
		if (!onRocket && this.isOffScreen == false) {
			super.setY(super.getY() + 9);
		}
	}

	@Override
	public boolean isOnScreen() {
		if (super.getY() >= 550 - this.getYWidth() && !hasBeenTouchedByAstronaut) {
			super.setY(550 - this.getYWidth());
			this.isOffScreen = true;
		} else {
			this.isOffScreen = false;
		}
		return true;
	}

	@Override
	public void drawOn(Graphics2D g) {
		if (this.hasBeenTouchedByAstronaut) {
			this.setX(this.astro.getX() + 5);
			this.setY(this.astro.getY() + 5);
		}
		super.drawOn(g);
	}

}