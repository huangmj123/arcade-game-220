package main;

/**
 * Class: Rocket
 * 
 * @author Joshua Mestemacher, Michael Huang
 * 
 *         Acts as the rocket that needs to be fixed and refueled by the
 *         astronaut so that the player can take off in it, thereby winning the
 *         current level. Has a parameter storing the number of parts it
 *         currently has.
 * 
 *
 */
public class Rocket extends GameObject {
	public static final int XWIDTH = 50;
	public static final int YWIDTH = 50;
	private int numberOfParts;
	private int fuel = 0;

	public Rocket(int newX, int newY) { // probably should input the gamecomponent so we can update the rocket or
										// something
		super(newX, newY, 0, XWIDTH, YWIDTH, "Images/booster.png");
		numberOfParts = 1;
	}

	public int getFuel() {
		return fuel;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public int getNumberOfParts() {
		return numberOfParts;
	}

	public void setNumberOfParts(int numberOfParts) {
		this.numberOfParts = numberOfParts;
	}

}