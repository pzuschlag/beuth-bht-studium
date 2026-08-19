package sketchboard;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import aufgabe1.a.DrawingInstructor;

public class EtchASketch extends JFrame {

	public static int GAP = 99999;
	public static final int BLACK = 0, BLUE = 1, RED = 2, GREEN = 3, YELLOW = 4, WHITE = 5;

	private Graphics graphics;
	Color color = Color.red;
	int lastX = translateX(0), lastY = translateY(0);
	boolean gap = false;

	public EtchASketch() {
		this.setTitle("Etch A Sketch: " + DrawingInstructor.getTitle());
		this.setSize(500, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		graphics = this.getGraphics();
		setColor();
	}

	private void setColor() {
		switch (DrawingInstructor.nextColor()) {
		case BLACK:
			color = Color.BLACK;
			break;
		case BLUE:
			color = Color.BLUE;
			break;
		case RED:
			color = Color.RED;
			break;
		case GREEN:
			color = Color.GREEN;
			break;
		case YELLOW:
			color = Color.YELLOW;
			break;
		case WHITE:
			color = Color.WHITE;
			break;
		default:
			color = Color.GRAY;
		}
		graphics.setColor(color);
	}

	private void setStartingPoint() {
		lastX = DrawingInstructor.firstX(this.getWidth() / 2);
		lastY = DrawingInstructor.firstY(this.getHeight() / 2);
		lastX = translateX(lastX);
		lastY = translateY(lastY);
	}

	/* draw line up to current coord. in current color, unless GAP */
	private void drawLine() {
		int x = DrawingInstructor.nextX(this.getWidth() / 2);
		int y = DrawingInstructor.nextY(this.getHeight() / 2);
		setColor();
		if (x == GAP || y == GAP) {
			gap = true;
			return; // instead of a long else part
		}

		x = translateX(x);
		y = translateY(y);
		if (gap) // end point of a gap
			gap = false; // start a new line here, but don't draw to here
		else {
			if (isVisible(x, y))
				graphics.drawLine(lastX, lastY, x, y); // draw to here

		}
		lastX = x; // next line starts here
		lastY = y;
	}

	/* translate pixel coordinates into sketchboard coordinates */
	private int translateX(int coordinate) {
		return this.getWidth() / 2 + coordinate;
	}

	/* translate pixel coordinates into sketchboard coordinates */
	private int translateY(int coordinate) {
		return this.getHeight() / 2 - coordinate;
	}

	/* find out whether point is within sketchboard */
	private boolean isVisible(int x, int y) {
		return !(x < 0 || y < 0 || x > this.getWidth() || y > this.getHeight());
	}

	// MAIN METHOD //
	public static void main(String[] args) {
		EtchASketch board = new EtchASketch();
		board.setStartingPoint();
		while (true) {
			board.drawLine();
			try {
				Thread.sleep(5);
			} catch (InterruptedException x) {
			}
		}
	}

}
