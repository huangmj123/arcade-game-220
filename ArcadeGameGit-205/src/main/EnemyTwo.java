package main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class: EnemyTwo
 * 
 * @author Joshua Mestemacher, Michael Huang
 * 
 *         EnemyTwo moves around randomly like EnemyOne, but shoots bullets to
 *         the right at random intervals (a 5% chance each frame). If those
 *         bullets hit the astronaut, the Astronaut loses a life.
 *
 */
public class EnemyTwo extends Enemy {
	public final static int GRAVITY = 0;
	public final static int XWIDTH = 30;
	public final static int YWIDTH = 30;
	public ArrayList<Bullet> bullets;

	public EnemyTwo(int xPos, int yPos) {
		super(xPos, yPos, GRAVITY, XWIDTH, YWIDTH, "Images/enemy two.png");
		this.isHit = false;
		this.xVel = 15;
		this.yVel = -15;
		this.bullets = new ArrayList<>();
	}

	public void drawOn(Graphics2D g) {
		super.getIcon().paintIcon(null, g, super.getX(), super.getY());
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).moveConsistently();
			bullets.get(i).drawOn(g);
			if (bullets.get(i).isOnScreen() == false) {
				bullets.remove(i);
				i--;
			}
		}
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void clearAll() {
		bullets = new ArrayList<Bullet>();
	}

	public void moveConsistently() {
		super.updatePosition(this.xVel, this.yVel);
		Random random = new Random();
		if (random.nextDouble() < 0.05) { // 0.05
			Bullet bullet = new Bullet(super.getX(), super.getY(), 10, false);
			bullets.add(bullet);
		}
	}
}
