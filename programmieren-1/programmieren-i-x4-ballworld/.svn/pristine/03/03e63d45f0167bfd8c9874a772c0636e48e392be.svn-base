package aufgabe6;

import aufgabe2.BasicAbstract;

public class Feuerwerk extends BasicAbstract {

	private int radius = 2;

	private int directionX = (int) Math.round(Math.random());
	private int directionY = (int) Math.round(Math.random());
	private double geschX = 1;
	private double geschY = 2;

	public Feuerwerk() {
		setRadius(radius);
	}

	public Feuerwerk(double startX, double startY) {
		setRadius(radius);
		setX(startX);
		setY(startY);
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

		if (getRadius() < 5) {
			setRadius(getRadius() + 0.03);
		} else {
			getWorld().addBall(new Feuerwerk(getX(), getY()));
			getWorld().addBall(new Feuerwerk(getX(), getY()));
			getWorld().removeBall(this);
		}

	}
}
