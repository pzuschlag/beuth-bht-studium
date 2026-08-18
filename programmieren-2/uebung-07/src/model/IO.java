package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.scene.control.Button;

public class IO {
	/**
	 * Schreibt das Spielfeld als eine Datei weg.
	 * 
	 * @param spielfeld
	 * @param dateipfad
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void writeSpielfeld(ArrayList<Button> spielfeld, String dateipfad) throws FileNotFoundException, IOException {
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(dateipfad));

		ArrayList<SerializableButton> sf = null;
		for (Button bt : spielfeld) {
			sf.add((SerializableButton) bt);
		}

		output.writeObject(sf);
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
	@SuppressWarnings("unchecked")
	public static ArrayList<Button> readSpielfeld(String dateiname) throws FileNotFoundException, IOException, ClassNotFoundException {
		ArrayList<Button> spielfeld;
		ObjectInputStream input;
		input = new ObjectInputStream(new FileInputStream(dateiname));
		spielfeld = (ArrayList<Button>) input.readObject();
		input.close();
		return spielfeld;
	}
}
