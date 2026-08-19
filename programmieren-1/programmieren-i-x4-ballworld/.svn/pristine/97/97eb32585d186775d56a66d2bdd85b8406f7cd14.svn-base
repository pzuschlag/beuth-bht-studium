package aufgabe4;

import aufgabe2.BasicAbstract;

public class Ping extends BasicAbstract {

	private int radius = 20;

	private int directionX = (int) (Math.random() + 0.5);
	private int directionY = (int) (Math.random() + 0.5);
	private double geschX = 1;
	private double geschY = 2;

	public Ping() {
		setRadius(radius);
		setX((Math.random() - 0.5) * (400 - getRadius()));
		setY((Math.random() - 0.5) * (400 - getRadius()));
	}

	public void act() {

		if (directionX % 2 == 0) {
			if ((getX() + getRadius()) <= getWorld().getMaxX()) {
				setX(getX() + geschX);
			} else {
				directionX++;
				geschX = Math.random() + 1;
			}
		} else {
			if ((getX() - getRadius()) >= (getWorld().getMinX())) {
				setX(getX() - geschX);
			} else {
				directionX++;
				geschX = Math.random() + 1;
			}
		}

		if (directionY % 2 == 0) {
			if ((getY() + getRadius()) <= getWorld().getMaxY()) {
				setY(getY() + geschY);
			} else {
				directionY++;
				geschY = Math.random() + 1;
			}
		} else {
			if ((getY() - getRadius()) >= (getWorld().getMinY())) {
				setY(getY() - geschY);
			} else {
				directionY++;
				geschY = Math.random() + 1;
			}
		}

	}
}
