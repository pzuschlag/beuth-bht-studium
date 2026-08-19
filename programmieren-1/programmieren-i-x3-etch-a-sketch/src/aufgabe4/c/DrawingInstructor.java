package aufgabe4.c;

import sketchboard.EtchASketch;

public class DrawingInstructor {
	private static int x = 0, y = 0;

	private static int changeColor = 0;

	private static boolean boolean1 = true;
	private static boolean boolean2 = true;
	private static boolean boolean3 = true;
	private static boolean boolean4 = true;
	private static boolean boolean6 = true;
	private static boolean boolean7 = true;
	private static boolean boolean8 = true;
	private static boolean boolean9 = true;

	private static boolean boolean10 = true;
	private static boolean boolean11 = true;
	private static boolean boolean12 = true;
	private static boolean boolean13 = true;
	private static boolean boolean15 = true;
	private static boolean boolean16 = true;
	private static boolean boolean17 = true;
	private static boolean boolean18 = true;

	public static String getTitle() {
		return "First Drawing: straight line";
	}

	public static int firstX(int max) {
		x = -25;
		return x;
	}

	public static int firstY(int max) {
		y = 0;
		return y;
	}

	public static int nextX(int max) {

		if (boolean1) {
			boolean1 = false;
			return x = 25;
		}
		if (boolean2) {
			boolean2 = false;
			return x = 25;
		}
		if (boolean3) {
			boolean3 = false;
			return x = -25;
		}
		if (boolean4) {
			boolean4 = false;
			return x = -25;
		}
		if (boolean6) {
			boolean6 = false;
			return x = 25;
		}
		if (boolean7) {
			boolean7 = false;
			return x = 0;
		}
		if (boolean8) {
			boolean8 = false;
			return x = -25;
		}
		if (boolean9) {
			boolean9 = false;
			return x = 25;
		}

		return x;

	}

	public static int nextY(int max) {

		if (boolean10) {
			boolean10 = false;
			return y = 0;
		}
		if (boolean11) {
			boolean11 = false;
			return y = 50;
		}
		if (boolean12) {
			boolean12 = false;
			return y = 50;
		}
		if (boolean13) {
			boolean13 = false;
			return y = 0;
		}
		if (boolean15) {
			boolean15 = false;
			return y = 50;
		}
		if (boolean16) {
			boolean16 = false;
			return y = 75;
		}
		if (boolean17) {
			boolean17 = false;
			return y = 50;
		}
		if (boolean18) {
			boolean18 = false;
			return y = 0;
		}

		return y;
	}

	public static int nextColor() {

		switch (changeColor) {
		case 1:
			return EtchASketch.BLUE;
		case 2:
			return EtchASketch.RED;
		case 3:
			return EtchASketch.GREEN;
		case 4:
			return EtchASketch.YELLOW;
		case 5:
			return EtchASketch.WHITE;
		case 6:
			changeColor = 0;
		default:
			return EtchASketch.BLACK;
		}
	}
}
