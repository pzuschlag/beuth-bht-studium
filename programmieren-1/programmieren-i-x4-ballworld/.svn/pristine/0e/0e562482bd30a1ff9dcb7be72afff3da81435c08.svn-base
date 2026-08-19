package aufgabe3;

import aufgabe2.BasicAbstract;

public class Kick extends BasicAbstract {

	public Kick() {
		setRadius(10);
		setX((Math.random() - 0.5) * (400 - getRadius()));
		setY((Math.random() - 0.5) * (400 - getRadius()));
	}

	@Override
	public void userClicked(double arg0, double arg1) {

		this.setX(arg0);
		this.setY(arg1);
	}

	@Override
	public void userTyped(char arg0) {

		if (arg0 == 'd') {
			getWorld().removeBall(this);
		}
	}
}