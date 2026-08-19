package aufgabe1.a;

import sketchboard.EtchASketch;

public class DrawingInstructor {
	private static int x = 0, y = 0;

	static boolean horizontal = false; // Ob entsprechende Linie gezeichnet wurde
	static boolean vertikal = false;
	static boolean diagonal = false;

	static boolean centerX = true; // Ob Zeichner in der Mitte ist
	static boolean centerY = true;

	public static String getTitle() {
		return "First Drawing: straight line";
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

		if (!centerX) {
			x = 0;
			centerX = true;
			return x;
		}

		if (centerX && !horizontal) {
			x = 250; // horizontal line, x increasing
			centerX = false;
		} else if (centerX && !vertikal) {
			x = 0;
			centerX = false;
		} else if (centerX && !diagonal) {
			x = 250;
			centerX = false;
		}

		return x;
	}

	public static int nextY(int max) {

		if (!centerY) {
			y = 0;
			centerY = true;
			return y;
		}

		if (centerY && !horizontal) {
			y = 0;
			centerY = false;
			horizontal = true;
		} else if (centerY && !vertikal) {
			y = 250;
			centerY = false;
			vertikal = true;
		} else if (centerY && !diagonal) {
			y = 250;
			centerY = false;
			diagonal = true;
		}

		return y;
	}

	public static int nextColor() {
		// return EtchASketch.BLACK;
		return EtchASketch.BLUE;
		// return EtchASketch.RED;
		// return EtchASketch.GREEN;
		// return EtchASketch.YELLOW;

	}

}
