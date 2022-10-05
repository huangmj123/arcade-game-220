package main;

/**
 * 
 * Class: ValuableResource
 * 
 * @author Joshua Mestemacher, Michael Huang
 * 
 *         Acts as a collectible that when touched by the astronaut increases
 *         the player's score by 350. Disappears after being touched by the
 *         astronaut. Has an image of a diamond.
 * 
 * 
 *
 */
public class ValuableResource extends Collectible {
	private final static int WIDTH = 30;
	private final static int HEIGHT = 30;
	private boolean notTouchedByAstronaut;

	public ValuableResource(int x, int y) {
		super(x, y, WIDTH, HEIGHT, "Images/valuable.png");
		notTouchedByAstronaut = true;
	}

	@Override
	public boolean isOnScreen() {
		return notTouchedByAstronaut;
	}

	public void setNotTouchedByAstronaut(boolean notTouchedByAstronaut) {
		this.notTouchedByAstronaut = notTouchedByAstronaut;
	}

}
