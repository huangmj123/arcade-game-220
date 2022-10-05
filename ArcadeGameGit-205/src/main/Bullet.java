package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Class: Bullet
 * 
 * @author Joshua Mestemacher Michael Huang
 *
 *
 *         This is the bullet class. EnemyTwo and the astronaut can shoot
 *         bullets. When a bullet from an enemy hits the astronaut, they lose a
 *         life. When a bullet from the astronaut hits an enemy, the enemy dies.
 *         Bullets are given the shape of tiny rectangles. Enemy bullets are
 *         colored magenta, astronaut bullets are colored red.
 *
 *
 *
 */
public class Bullet extends GameObject {
	private int motion;
	private boolean shotByHero;
	private boolean hasCollided;

	public Bullet(int x1, int y1, int motion, boolean hero) {
		super(x1, y1, 0, 5, 5, "");
		this.motion = motion;
		shotByHero = hero;
		hasCollided = false;

	}

	public boolean isOnScreen() {
		if (super.getX() > 800 || super.getX() < 0) {
			return false;
		}
		return true;
	}

	public void moveConsistently() {
		super.setX(super.getX() + motion);
	}

	public boolean getShotByHero() {
		return shotByHero;
	}

	public void setCollision() {
		hasCollided = true;
	}

	public void drawOn(Graphics2D g) {
		if (!hasCollided) {
			if (!shotByHero)
				g.setColor(Color.MAGENTA);
			else
				g.setColor(Color.RED);
			Rectangle2D.Double Bullet = new Rectangle2D.Double(super.getX(), super.getY(), 5, 5);
			g.fill(Bullet);
			g.setColor(Color.BLACK);
		}
	}

}
