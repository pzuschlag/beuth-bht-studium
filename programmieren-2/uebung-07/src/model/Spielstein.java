package model;


import java.io.Serializable;

public class Spielstein implements Serializable {

	private static final long serialVersionUID = 1L;

	private String value;

	/**
	 * Ein Spielstein kann innerhalb des Spielfeldes plaziert werden.
	 * 
	 * @param value
	 */
	public Spielstein(String value) {
		this.value = value;
	}

	/**
	 * Überschreiben der toString-Methode um beim auslesen des Objektes, automatisch das Value zu bekommen.
	 */
	public String toString() {
		return value;
	}

	/**
	 * Setzt den Value des Spielsteins
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gibt den Value des Spielsteins zurück
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}

}