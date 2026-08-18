package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Spielfeld implements Serializable {

	private static final long serialVersionUID = 1L;

	private Spielstein[][] spielfeld;

	/**
	 * Spielfeld wird als 3x3 Feld und mit leeren Spielsteinen initialisiert.
	 */
	public Spielfeld() {
		spielfeld = new Spielstein[3][3];

		setFeld(0, 0, new Spielstein(" "));
		setFeld(0, 1, new Spielstein(" "));
		setFeld(0, 2, new Spielstein(" "));
		setFeld(1, 0, new Spielstein(" "));
		setFeld(1, 1, new Spielstein(" "));
		setFeld(1, 2, new Spielstein(" "));
		setFeld(2, 0, new Spielstein(" "));
		setFeld(2, 1, new Spielstein(" "));
		setFeld(2, 2, new Spielstein(" "));
	}

	/**
	 * Setzt Stein auf das ausgewählte Feld.
	 * 
	 * @param spalte
	 * @param zeile
	 * @param stein
	 */
	public void setFeld(int spalte, int zeile, Spielstein stein) {
		spielfeld[spalte][zeile] = stein;
	}

	/**
	 * Liefert Stein von ausgwählter Possition.
	 * 
	 * @param spalte
	 * @param zeile
	 * @return
	 */
	public Spielstein getFeld(int spalte, int zeile) {
		return spielfeld[spalte][zeile];
	}

	/**
	 * Liefert eine Liste mit allen noch ungesetzten Feldern.
	 * 
	 * @return
	 */
	public ArrayList<Spielstein> getLeereFelder() {
		ArrayList<Spielstein> emptyFields = new ArrayList<Spielstein>();

		for (int i = 0; i < spielfeld.length; i++) {
			for (int j = 0; j < spielfeld.length; j++) {
				if (spielfeld[i][j].toString().contains(" ")) {
					emptyFields.add(spielfeld[i][j]);
				}
			}
		}
		return emptyFields;
	}
}
