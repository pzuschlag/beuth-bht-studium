package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IO {
	/**
	 * Schreibt das Spielfeld als eine Datei weg.
	 * 
	 * @param spielfeld
	 * @param dateipfad
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void writeWorld(Spielfeld spielfeld, String dateipfad) throws FileNotFoundException, IOException {
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(dateipfad));
		output.writeObject(spielfeld);
		output.close();
	}

	/**
	 * Dient zum einlesen des Spielfeldes
	 * 
	 * @param dateiname
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Spielfeld readWorld(String dateiname) throws FileNotFoundException, IOException, ClassNotFoundException {
		Spielfeld spielfeld;
		ObjectInputStream input;
		input = new ObjectInputStream(new FileInputStream(dateiname));
		spielfeld = (Spielfeld) input.readObject();
		input.close();
		return spielfeld;
	}
}
