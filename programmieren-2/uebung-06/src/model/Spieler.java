package model;

import java.util.ArrayList;
import java.util.Random;

public class Spieler {

	String name;
	String sign;

	/**
	 * Ein Spieler besitzt einen Namen und ein Zeichen.
	 * 
	 * @param name
	 * @param sign
	 */
	public Spieler(String name, String sign) {
		this.name = name;
		this.sign = sign;
	}

	/**
	 * Gibt den Namen des Spielers.
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gibt das Zeichen des Spielers.
	 * 
	 * @return
	 */
	public String getSign() {
		return this.sign;
	}

	/**
	 * Spielt eine Runde TicTacToe
	 * 
	 * @param spielfeld
	 */
	public void spiele(Spielfeld spielfeld) {
		ArrayList<Spielstein> emptyFields = spielfeld.getLeereFelder();
		Random rnd = new Random();

		Spielstein empty = emptyFields.get(rnd.nextInt(emptyFields.size()));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (spielfeld.getFeld(i, j) == empty) {
					spielfeld.getFeld(i, j).setValue(sign);
				}
			}
		}
	}
}
