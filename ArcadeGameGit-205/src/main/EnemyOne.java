package main;

/**
 * 
 * Class: EnemyOne
 * 
 * 
 * @author Joshua Mestemacher, Michael Huang
 * 
 * 
 *         EnemyOne just moves around randomly, going on its current x and y
 *         velocity until it bounces off an edge of the window, at which point
 *         either its x or y velocities are reversed. Does not specifically
 *         chase the player.
 *
 */
public class EnemyOne extends Enemy {
	public final static int GRAVITY = 0;
	public final static int XWIDTH = 50;
	public final static int YWIDTH = 50;

	public EnemyOne(int x1, int y1) {
		super(x1, y1, GRAVITY, XWIDTH, YWIDTH, "Images/enemy 1.png");
		this.isHit = false;
		this.xVel = 15;
		this.yVel = -10;
	}

}
