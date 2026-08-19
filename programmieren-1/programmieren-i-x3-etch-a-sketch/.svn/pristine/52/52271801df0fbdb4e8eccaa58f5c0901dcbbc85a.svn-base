package aufgabe4.a;

import sketchboard.EtchASketch;

public class DrawingInstructor {
	private static int x = 0, y = 0;

	private static int changeY = 0;

	private static int ausfallswinkelY = 1;

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

		x = (int) Math.sqrt((50 ^ 2) - (50 ^ 2));

		return x;
	}

	public static int nextY(int max) {

		int r = 50;

		// (x-M)^2+(y-N)=r^2
		// Kreis-Formel: y=wurzel(r^2-x^2) oder -wurzel(r^2-x^2)
		y = (int) Math.sqrt((r ^ 2) - (50 ^ 2));

		return y;
	}

	public static int nextColor() {

		return EtchASketch.BLUE;
	}
}
