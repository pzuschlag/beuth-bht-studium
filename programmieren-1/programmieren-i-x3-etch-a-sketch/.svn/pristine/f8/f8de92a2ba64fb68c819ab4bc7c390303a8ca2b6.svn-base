package aufgabe3.a;

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

		// Sinus-Kurve: f(x) = a * sin(b * x + c) + d
		y = (int) (50 * Math.sin(Math.PI / 50 * (x + 200)) + 0);

		return y;
	}

	public static int nextColor() {

		return EtchASketch.BLUE;
	}
}
