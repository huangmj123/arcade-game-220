package main;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Class: GameObject
 * 
 * @author Joshua Mestemacher, Micheal Huang
 * 
 *         Acts as the super class for all of the objects for the game. stores
 *         the object's x and y position(marking the upper left corner), the
 *         object's gravity, the object's width and height, and the object's
 *         image. Draws onto the screen the object's image at its x and y
 *         position, and can update the x and y position of the object using a
 *         given x velocity and y velocity. Has a method for objects to move
 *         downwards according to their gravity.
 * 
 *
 */
public class GameObject {
	private int x; // upper left hand corner x
	private int y; // upper left hand corner y
	private int objectGravity;
	private int xwidth;
	private int ywidth;
	private ImageIcon icon2;

	public GameObject(int x1, int y1, int z, int xwid, int ywid, String imgloc) {
		x = x1;
		y = y1;
		objectGravity = z;
		xwidth = xwid;
		ywidth = ywid;
		ImageIcon icon = new ImageIcon(imgloc);
		Image image = icon.getImage();
		Image newimg = image.getScaledInstance(xwidth, ywidth, java.awt.Image.SCALE_SMOOTH);
		icon2 = new ImageIcon(newimg);
	}

	public void updatePosition(int velX, int velY) {

		this.x += velX;
		this.y += velY;
		isOnScreen();
	}

	public boolean isOnScreen() {
		return true;
	}

	public void moveConsistently() {
	}

	public void drawOn(Graphics2D g2) {
		icon2.paintIcon(null, g2, x, y);
	}

	public void moveDueToGravity() {
		y += objectGravity;
		isOnScreen();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int newX) {
		x = newX;
	}

	public void setY(int newY) {
		y = newY;
	}

	public int getXWidth() {
		return xwidth;
	}

	public int getYWidth() {
		return ywidth;
	}

	public void setYWidth(int i) {
		ywidth = i;
	}

	public ImageIcon getIcon() {
		return icon2;
	}

	public void setIcon(ImageIcon x) {
		icon2 = x;
	}

	public void setImage(String str) {
		ImageIcon icon3 = new ImageIcon(str);
		Image image = icon3.getImage();
		Image newimg = image.getScaledInstance(this.xwidth, this.ywidth, java.awt.Image.SCALE_SMOOTH);
		this.setIcon(new ImageIcon(newimg));
	}
}
