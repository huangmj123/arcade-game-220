package main;

/**
 * Class: Fuel
 * 
 * @author Joshua Mestemacher, Michael Huang
 * 
 *         Fuel represents the fuel needed to power the rocket so that it can
 *         lift off. It is a collectible that is picked up by the astronaut and
 *         placed or dropped on the rocket. Putting all the fuel in a level on
 *         the rocket is required for the rocket to liftoff to win the level.
 *         The astronaut must first place or drop all of the rocket parts for a
 *         level on the rocket before placing any fuel on the rocket.
 * 
 *
 */
public class Fuel extends Collectible {
	private final static int WIDTH = 40;
	private final static int HEIGHT = 40;

	public Fuel(int x, int y, Astronaut astro) {
		super(x, y, WIDTH, HEIGHT, "Images/fuel.png");
		this.astro = astro;
	}

	public void drop(int rocketNumberOfFuel, int rocketX, int rocketY, int rocketYWidth) {
		this.setY(rocketY);
		this.setX(rocketX + 5 + rocketYWidth);
	}

}