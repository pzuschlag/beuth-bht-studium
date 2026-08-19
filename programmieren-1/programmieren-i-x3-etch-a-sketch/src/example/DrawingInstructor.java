package example;

import sketchboard.EtchASketch;

public class DrawingInstructor {
	private static int x = 0, y = 0;

	public static String getTitle() {
		return "Example: straight line";
	}

	public static int firstX(int max) {
		x = 0;
		return x;
	}

	public static int firstY(int max) {
		y = 0; // center of drawing panel
		return y;
	}

	public static int nextX(int max) {
		x++;
		return x;// horizontal line, x increasing

	}

	public static int nextY(int max) {
		return y; // horizontal line, y unchanged
	}

	public static int nextColor() {
		return EtchASketch.BLUE;
	}

}
