package aufgabe2;

import ballworld.Ball;
import ballworld.World;
import cs101.lang.Animate;

public abstract class BasicAbstract implements Ball, Animate {

	private double radius;
	private double x;
	private double y;
	private World world;

	@Override
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public double getX() {
		return x;
	}

	public void setX(double pos) {
		x = pos;
	}

	@Override
	public double getY() {
		return y;
	}

	public void setY(double pos) {
		y = pos;
	}

	public World getWorld() {
		return world;
	}

	@Override
	public void setWorld(World arg0) {
		world = arg0;
	}

	@Override
	public void userClicked(double arg0, double arg1) {
	}

	@Override
	public void userTyped(char arg0) {
		// Abstracte Klassen eigentlich ohne Logic !
		if (arg0 == 'c') {
			for (int i = 0; i <= world.BallCount(); i++) {
				world.removeBall(world.getBall(i));
				;
			}
		}

	}

	@Override
	public void act() {
	}
}
