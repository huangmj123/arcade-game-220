package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Class: Platform
 * 
 * @author Joshua Mestemacher, Michael Huang
 * 
 *         The Platform class for the arcade game. Each Platform has a set
 *         thickness of 20, a length, and an x and y position for its upper left
 *         hand corner. Platforms are colored orange. Enemies can go through
 *         Platforms, but the astronaut can't. Collectibles also cannot fall
 *         through platforms, and instead sit on top of them.
 * 
 * 
 * 
 * 
 */
public class Platform extends GameObject {
	private static final int THICKNESS = 20;
	private int length;

	public Platform(int x, int y, int length) {
		super(x, y, 0, length, THICKNESS, "");
		this.length = length;
	}

	public void drawOn(Graphics2D g) {
		g.setColor(Color.ORANGE);
		Rectangle2D.Double platformBox = new Rectangle2D.Double(super.getX(), super.getY(), this.length, THICKNESS);
		g.fill(platformBox);
		g.setColor(Color.BLACK);
	}

}
