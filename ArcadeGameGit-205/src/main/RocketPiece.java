package main;

/**
 * Class: RocketPiece
 * 
 * @author Joshua Mestemacher, Michael Huang
 *
 *         Represents a piece of the rocket. All rocket pieces in a level must
 *         be placed on the rocket along with putting all the fuel in the rocket
 *         in order for the rocket to lift off to win the level. All rocket
 *         pieces must be placed or dropped on the rocket before any fuel can be
 *         placed or dropped on the rocket. The last rocket piece put on the
 *         rocket turns into the nose cone for the rocket. This class has a
 *         method to place itself on the rocket depending on how many rocket
 *         pieces have been put on the rocket already.
 *
 *
 */
public class RocketPiece extends Collectible {
	private final static int WIDTH = 40;
	private final static int HEIGHT = 40;

	public RocketPiece(int x, int y, Astronaut astro) {
		super(x, y, WIDTH, HEIGHT, "Images/crate.png");
		this.astro = astro;

	}

	public void drop(int rocketNumberOfParts, int rocketX, int rocketY, int rocketXWidth) {
		this.setY(rocketY - (this.getYWidth() * rocketNumberOfParts));
		this.setX((int) ((rocketXWidth * (0.5) + rocketX) - (this.getXWidth() / 2)));
	}
}