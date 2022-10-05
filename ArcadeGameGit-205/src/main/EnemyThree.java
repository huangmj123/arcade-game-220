package main;

/**
 * 
 * 
 * Class: EnemyThree
 * 
 * @author Joshua Mestemacher Michael Huang
 *
 * 
 *         EnemyThree actively moves towards the Astronaut like a heat-seeking
 *         missile. However it does this at a slow speed. If EnemyThree touches
 *         the Astronaut, the Astronaut loses a life.
 * 
 */
public class EnemyThree extends Enemy {
	public final static int GRAVITY = 0;
	public final static int XWIDTH = 30;
	public final static int YWIDTH = 50;
	private Astronaut astro;

	public EnemyThree(int xPos, int yPos, Astronaut a) {
		super(xPos, yPos, GRAVITY, XWIDTH, YWIDTH, "Images/enemy three.png");
		astro = a;
	}

	public void moveConsistently() {
		if (astro.getX() > this.getX())
			this.xVel = 2;
		else if (astro.getX() == this.getX())
			xVel = 0;
		else
			xVel = -2;
		if (astro.getY() > this.getY())
			this.yVel = 2;
		else if (astro.getY() == this.getY())
			yVel = 0;
		else
			yVel = -2;
		this.updatePosition(xVel, yVel);
	}

}
