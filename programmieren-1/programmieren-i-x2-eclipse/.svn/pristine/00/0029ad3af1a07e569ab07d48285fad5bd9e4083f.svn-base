package javaEyes;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import cs101.lang.Animate;
import cs101.lang.AnimatorThread;
import cs101.util.Coerce;

/**
 * The <code>JavaEyes</code> class is a Java-based implementation of the familiar "xeyes" program, available on many
 * Unix-based systems.
 *
 */
public class JavaEyes extends Frame implements Animate {

	// Class constants

	// Default background color
	private static final Color DEFAULT_BG_COLOR = Color.white;
	// Default foreground color
	private static final Color DEFAULT_FG_COLOR = Color.black;

	// Non-varying instance data
	private Color bgColor; // Actual background color
	private Color fgColor; // Actual foreground color
	private Panel buttonPanel; // A Panel used to hold the quit button
	private Panel eyesPanel; // A Panel used to hold the eyes graphic
	private Button quitButton; // The "quit" button

	// State variables:
	private Point mousePosition; // The current position of the mouse

	// Objects used to animate the eyes,
	private AnimatorThread animator;
	private Image offscreenImage;

	/**
	 * Default class constructor. Builds a <code>JavaEyes</code> object, and starts a <code>Thread</code> to animate the
	 * eyes in response to the <code>mouseMoved</code> event. Uses the default background and foreground colors.
	 * 
	 */
	public JavaEyes() {
		this(DEFAULT_BG_COLOR, DEFAULT_FG_COLOR);
	}

	/**
	 * Class constructor. Builds a <code>JavaEyes</code> object, and starts a <code>Thread</code> to animate the eyes in
	 * response to the <code>mouseMoved</code> event.
	 *
	 * @param bgColor
	 *            the background color of the <code>JavaEyes</code> window
	 * @param fgColor
	 *            the foreground color of the <code>JavaEyes</code> window
	 */
	public JavaEyes(Color bgColor, Color fgColor) {
		super("JavaEyes");

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++//
		// //
		// Add components to the window //
		// //
		// (In other words, add whatever buttons, icons, and //
		// and graphics we'd like this window to contain). //
		// //
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++//

		// First, add a "quit" Button
		//
		this.buttonPanel = new Panel();
		this.quitButton = new Button("Quit");
		this.buttonPanel.add(quitButton);
		this.add("North", buttonPanel);

		// Next, add a Panel in which to display the "eyes" graphic
		//
		this.eyesPanel = new Panel();
		this.add("Center", eyesPanel);

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++//
		// //
		// Initialize the event handlers //
		// //
		// (In other words, tell the window what to do when the //
		// the user clicks a mouse on it, or types something //
		// at the keyboard). //
		// //
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++//

		// Set up an event handler to listen for the mouseMoved event
		//
		MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				JavaEyes.this.setMousePos(e.getX(), e.getY());
			}
		};

		this.addMouseMotionListener(mouseMotionListener);
		this.eyesPanel.addMouseMotionListener(mouseMotionListener);
		this.buttonPanel.addMouseMotionListener(mouseMotionListener);

		// Set up an event handler to detect when the user attempts
		// to close the program by pressing the "quit" button
		//

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animator.stopExecution();
				JavaEyes.this.dispose();
				System.exit(0);
			}
		};

		this.quitButton.addActionListener(actionListener);

		// Set up an event handler to detect when the user attempts
		// to close the program by pressing the window's close button
		//
		WindowListener windowListener = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				animator.stopExecution();
				JavaEyes.this.dispose();
				System.exit(0);
			}
		};

		this.addWindowListener(windowListener);

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++//
		// //
		// Initialize the state of this class //
		// //
		// (As a program runs, things about it will change. For //
		// example, in the JavaEyes program, the position of //
		// eyeballs changes. This changeable data is called //
		// "state". When you start a program which uses state, //
		// it is generally necesssary to initialize it to some //
		// default value. In this case, we tell JavaEyes to //
		// look at the top left corner of the window. If we //
		// failed to do this, the result of our program might //
		// be unpredictable, and that is the last thing we //
		// want!). //
		// //
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++//

		this.bgColor = bgColor;
		this.fgColor = fgColor;
		this.mousePosition = new Point(0, 0); // Tell the eyes where to look
		this.setBackground(this.bgColor); // Set the background color
		this.setForeground(this.fgColor); // Set the foreground color

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++//
		// //
		// Standard window stuff //
		// //
		// (Don't worry about this for now). //
		// //
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++//

		this.pack();
		this.show();
		this.setLocation(1, 1);

		this.animator = new AnimatorThread(this, true, 0, 100);
	}

	/**
	 * The standard entry point into a Java program.
	 *
	 * @param argv
	 *            command-line arguments passed to the program
	 */
	public static void main(String argv[]) {
		Color bgColor = JavaEyes.DEFAULT_BG_COLOR;
		Color fgColor = JavaEyes.DEFAULT_FG_COLOR;
		int argnum = 0;
		String usage = "Usage:  java JavaEyes [-fg <color>] [-bg <color>]";

		if (argv.length == 0) {
			new JavaEyes();
		} else {
			try {
				while (argnum < argv.length) {
					if (argv[argnum].equals("-bg")) {
						bgColor = Coerce.stringToColor(argv[++argnum]);
						argnum++;
					} else if (argv[argnum].equals("-fg")) {
						fgColor = Coerce.stringToColor(argv[++argnum]);
						argnum++;
					} else {
						throw new Error(usage);
					}
				}
			} catch (ArrayIndexOutOfBoundsException exc) {
				throw new Error(usage);
			}

			new JavaEyes(bgColor, fgColor);
		}
	}

	/**
	 * @deprecated use <code>getPreferredSize()</code> instead.
	 */
	public Dimension preferredSize() {
		return this.getPreferredSize();
	}

	/**
	 * Returns the preferred size of this component. This method is implemented so that calling the <code>pack()</code>
	 * method on an instance of this class will not change the size of the window unpredictably.
	 *
	 * @return the preferred size of this component.
	 */
	public Dimension getPreferredSize() {
		// You will comment the subsequent line out during your lab:
		return new Dimension(400, 300);
	}

	/**
	 * The <code>JavaEves</code> implementation of the <code>cs101.lang.Animate</code> interface. Continually updates
	 * the eyes graphic to point to the current location of the mouse pointer.
	 */
	public void act() {
		this.updateEyes();
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	// //
	// Utility Methods //
	// //
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++//

	private synchronized void setMousePos(int x, int y) {
		this.mousePosition.setLocation(x, y);
	}

	private synchronized Point getMousePos() {
		return this.mousePosition.getLocation();
	}

	/*
	 * A lot of this is grungy math to position the eyes. This paints onto a graphic that is only 200 by 80 pixels, but
	 * it is using the coordinates of the mouse in a much larger panel. Thus, we are going to be painting using
	 * coordinates within the (200,80) rectangle, but calculating the position of the pupils using greater coordinates
	 */
	private void paintEyes(Graphics g) {

		// it's a 200 by 80 rectangle
		int g_width = 200;
		int g_height = 80;

		// Get the dimensions of the central Panel
		Dimension d = this.eyesPanel.getSize();
		int w = d.width;
		int h = d.height;

		// The offset of the (200,80) rectangle in the Panel
		int x_offset = (w - g_width) / 2;
		int y_offset = h - g_height;

		// Get the mouse position
		Point p = this.getMousePos();

		// Set the color to the background color,
		// and draw the background rectangle for the eyes
		g.setColor(bgColor);
		g.fillRect(0, 0, g_width, g_height);

		// Set the color to the foreground color and draw the eyes
		g.setColor(fgColor);

		// draw the outline of the eyes
		g.drawOval(35, 20, 50, 50);
		// Set breakpoint on the subsequent line:
		g.drawOval(105, 20, 50, 50);

		// the centers of the eyes, using the small rectangle coordinates
		int eye1_x = 55;
		int eye1_y = 40;
		int eye2_x = 125;
		int eye2_y = 40;

		// the centers of the eyes, with offsets, in the panel coordinates
		int eye1_offset_x = eye1_x + x_offset;
		int eye1_offset_y = eye1_y + y_offset;
		int eye2_offset_x = eye2_x + x_offset;
		int eye2_offset_y = eye2_y + y_offset;

		int x1, y1, x2, y2, dist;

		dist = (int) Math.sqrt((p.x - eye1_offset_x) * (p.x - eye1_offset_x) + (p.y - eye1_offset_y) * (p.y - eye1_offset_y));
		dist = (dist == 0) ? 1 : dist; // prevent arithmetic exception
		x1 = (int) ((p.x - eye1_offset_x) * 16 / dist);
		y1 = (int) ((p.y - eye1_offset_y) * 16 / dist);

		dist = (int) Math.sqrt((p.x - eye2_offset_x) * (p.x - eye2_offset_x) + (p.y - eye2_offset_y) * (p.y - eye2_offset_y));
		dist = (dist == 0) ? 1 : dist; // prevent arithmetic exception
		x2 = (int) ((p.x - eye2_offset_x) * 16 / dist);
		y2 = (int) ((p.y - eye2_offset_y) * 16 / dist);

		// At last, we can draw the eyes!!

		// Set breakpoint on the subsequent line:
		g.fillOval(eye1_x + x1, eye1_y + y1, 12, 12);
		g.fillOval(eye2_x + x2, eye2_y + y2, 12, 12);
	}

	/*
	 * This implements a graphical technique called Double Buffering. You need not worry about what this does, although
	 * it will be explained later on in the course
	 */
	private void updateEyes() {
		// If no offscreen image has yet been created, create one.
		if (this.offscreenImage == null) {
			this.offscreenImage = this.createImage(200, 80);
		}

		paintEyes(this.offscreenImage.getGraphics());

		// paint the offscreen image onto the Panel
		Dimension d = this.eyesPanel.getSize();
		this.eyesPanel.getGraphics().drawImage(this.offscreenImage, d.width / 2 - 100, d.height - 80, this);
		this.repaint();
	}
}

/*
 * $Log: JavaEyes.java,v $ Revision 1.5 2004/01/26 21:48:14 gus prevent arithmetic exception in paint method
 * 
 * Revision 1.4 2004/01/26 21:32:39 gus Stop the animator before disposing the window to avoid NPEs
 * 
 * Revision 1.3 2003/03/28 16:58:37 gus addign log comment
 */
