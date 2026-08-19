package aufgabe1.d;

import sketchboard.EtchASketch;

public class DrawingInstructor {
	private static int x = 0, y = 0;

	private static int colorChooser = 0;

	public static String getTitle() {
		return "First Drawing: straight line";
	}

	public static int firstX(int max) {
		x = -250;
		return x;
	}

	public static int firstY(int max) {
		y = -250;
		return y;
	}

	public static int nextX(int max) {
		x++;

		if (x <= 0) {
			if (Math.abs(x % 10) < 5) {
				return EtchASketch.GAP;
			}
		} else if ((x % 10) >= 5) {
			return EtchASketch.GAP;
		}

		return x;

	}

	public static int nextY(int max) {
		y++;

		if (y <= 0) {
			if (Math.abs(y % 10) < 5) {
				colorChooser++;
				return EtchASketch.GAP;
			}
		} else if ((y % 10) >= 5) {
			colorChooser++;
			return EtchASketch.GAP;
		}

		return y;
	}

	public static int nextColor() {

		switch (colorChooser) {
		case 5:
			return EtchASketch.RED;
		case 10:
			colorChooser = 0;
		default:
			return EtchASketch.BLUE;
		}

	}
}
