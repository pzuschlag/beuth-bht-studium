import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.IO;
import model.Spieler;
import model.Spielfeld;

public class Main {

	static String dateipfad = "resources/spielfeld.class";

	static Spielfeld sf = new Spielfeld();
	static ArrayList<Spieler> spieler = new ArrayList<Spieler>();

	public static void main(String[] args) {

		Scanner scr = new Scanner(System.in);

		System.out.println("TicTacToe");
		System.out.print("Name Spieler 1: ");
		String nameSpieler1 = scr.next();
		System.out.print("Name Spieler 2: ");
		String nameSpieler2 = scr.next();
		scr.close();

		spieler = new ArrayList<Spieler>();
		spieler.add(Spieler.SPIELER_O));
		spieler.add(Spieler.SPIELER_X));

		System.out.println();
		try {
			IO.writeSpielfeld(sf, dateipfad);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		boolean spielEnde = false;

		while (!spielEnde) {
			for (Spieler aktSpieler : spieler) {

				// Spiel
				System.out.println(aktSpieler.getName() + " am Zug:");
				try {
					sf = IO.readSpielfeld(dateipfad);
					aktSpieler.spiele(sf);
					IO.writeSpielfeld(sf, dateipfad);
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				printSpielfeld();
				System.out.println();

				// Auswertung
				if (spielGewonnen(aktSpieler)) {
					spielEnde = true;
					break;
				}
				if (spielEnde()) {
					spielEnde = true;
					break;
				}
			}
			System.out.println();
		}
		System.out.print("Spiel Ende :)");
	}

	/**
	 * Ausgabe des Spielstandes
	 * 
	 * @param sf
	 */
	private static void printSpielfeld() {
		System.out.println("###################");
		System.out.println("#     #     #     #");
		System.out.println("#  " + sf.getFeld(0, 0) + "  #  " + sf.getFeld(1, 0) + "  #  " + sf.getFeld(2, 0) + "  #");
		System.out.println("#     #     #     #");
		System.out.println("###################");
		System.out.println("#     #     #     #");
		System.out.println("#  " + sf.getFeld(0, 1) + "  #  " + sf.getFeld(1, 1) + "  #  " + sf.getFeld(2, 1) + "  #");
		System.out.println("#     #     #     #");
		System.out.println("###################");
		System.out.println("#     #     #     #");
		System.out.println("#  " + sf.getFeld(0, 2) + "  #  " + sf.getFeld(1, 2) + "  #  " + sf.getFeld(2, 2) + "  #");
		System.out.println("#     #     #     #");
		System.out.println("###################");
	}

	/**
	 * Prüft ob das Spiel zu Ende ist.
	 * 
	 * @return
	 */
	private static boolean spielEnde() {
		if (sf.getLeereFelder().size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Prüft ob ein Spieler gewonnen hat.
	 * 
	 * @param spieler
	 * @return
	 */
	private static boolean spielGewonnen(Spieler spieler) {

		// Prüfung Senkrecht
		for (int i = 0; i < 3; i++) {
			if (sf.getFeld(i, 0).getValue().equals(spieler.getSign()) && sf.getFeld(i, 1).getValue().equals(spieler.getSign())
					&& sf.getFeld(i, 2).getValue().equals(spieler.getSign())) {
				System.out.println(spieler.getName() + " hat gewonnen !! :)");
				return true;
			}
		}

		// Prüfung Wagerecht
		for (int i = 0; i < 3; i++) {
			if (sf.getFeld(0, i).getValue().equals(spieler.getSign()) && sf.getFeld(1, i).getValue().equals(spieler.getSign())
					&& sf.getFeld(2, i).getValue().equals(spieler.getSign())) {
				System.out.println(spieler.getName() + " hat gewonnen !! :)");
				return true;
			}
		}

		// Prüfung Diagonal Links nach Rechts
		if (sf.getFeld(0, 0).getValue().equals(spieler.getSign()) && sf.getFeld(1, 1).getValue().equals(spieler.getSign())
				&& sf.getFeld(2, 2).getValue().equals(spieler.getSign())) {
			System.out.println(spieler.getName() + " hat gewonnen !! :)");
			return true;
		}

		// Prüfung Diagonal Rechts nach Links
		if (sf.getFeld(2, 0).getValue().equals(spieler.getSign()) && sf.getFeld(1, 1).getValue().equals(spieler.getSign())
				&& sf.getFeld(0, 2).getValue().equals(spieler.getSign())) {
			System.out.println(spieler.getName() + " hat gewonnen !! :)");
			return true;
		}

		return false;
	}
}