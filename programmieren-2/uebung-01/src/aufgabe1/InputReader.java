package aufgabe1;

import java.util.Scanner;

/**
 * Dient dazu Eingaben von der Konsole aus einzulesen.
 *
 * @author Philip, Leon & Charline
 */
public class InputReader {

	Scanner scr = new Scanner(System.in);

	public InputReader() {
		System.out.println("Please tell us about your Problem ?");
	}

	public String read() {
		return scr.nextLine();
	}
}