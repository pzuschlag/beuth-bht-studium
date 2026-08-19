package aufgabe2.d;

import sketchboard.EtchASketch;

public class DrawingInstructor {
	private static int x = 0, y = 0;

	private static int changeY = 0;

	private static int ausfallswinkelY = 1;

	public static String getTitle() {
		return "First Drawing: straight line";
	}

	public static int firstX(int max) {
		x = -250;
		return x;
	}

	public static int firstY(int max) {
		y = 0;
		return y;
	}

	public static int nextX(int max) {

		x++;

		return x;
	}

	public static int nextY(int max) {

		if (y >= max || y <= (max * -1)) {
			changeY++;
		}

		if (changeY % 2 == 0) {
			y += 10;
		} else {
			y -= 10;
		}

		return y;
	}

	public static int nextColor() {

		return EtchASketch.BLUE;
	}
}
