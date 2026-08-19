package aufgabe2.b;

import sketchboard.EtchASketch;

public class DrawingInstructor {
	private static int x = 0, y = 0;

	private static int versatz = 10;
	private static int changeColor = 0;
	private static int add = 1;

	public static String getTitle() {
		return "First Drawing: straight line";
	}

	public static int firstX(int max) {
		x = 0;
		return x;
	}

	public static int firstY(int max) {
		y = 0;
		return y;
	}

	public static int nextX(int max) {

		return x++;
	}

	public static int nextY(int max) {

		y = y + add;
		add++;

		return y;
	}

	public static int nextColor() {

		if (changeColor % 2 == 0) {
			return EtchASketch.BLUE;
		} else {
			return EtchASketch.RED;
		}

		// return EtchASketch.BLACK;
		// return EtchASketch.GREEN;
		// return EtchASketch.YELLOW;
	}
}
