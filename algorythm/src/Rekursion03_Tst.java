/***************************************************************************/
// Datei Rekursion03_Tst.java
/*
 * ------------------------------------------------------------------------ Dieses Programm Rekursion03_Tst enthaelt die
 * rekursive Methode fuelleAus und testet sie.
 * 
 * Die Methode fuelleAus fuellt (in einer zweistufigen Reihung von char-Variablen) das Innere von geschlossenen Figuren
 * mit 'X'-en aus. ------------------------------------------------------------------------
 */
class Rekursion03_Tst {
	// ---------------------------------------------------------------------
	static public void fuelleAus(char[][] rrc, int z, int s) {
		// Verlaesst sich darauf, dass rrc "rechteckig" ist (d.h. keinen
		// "Flatterand" hat) und dass die Komponente rrc[z][s] existiert.
		//
		// Diese Methode macht nichts, wenn rrc[z][s] bereits ein 'X' enthaelt.
		// Schreibt sonst ein 'X' in alle Komponenten rrc[i][j], fuer die gilt:
		// 1. rrc[i][j] enthaelt noch kein 'X' und
		// 2. rrc[i][j] ist von rrc[z][s] aus erreichbar auf einem Weg, der
		// nur aus waagerechten und/oder senkrechten Abschnitten besteht
		// und keine Komponente kreuzt, die bereits ein 'X' enthaelt.

		if (rrc[z][s] != 'X') {
			rrc[z][s] = 'X';

			if (z < rrc.length - 1)
				fuelleAus(rrc, z + 1, s);
			if (z > 0)
				fuelleAus(rrc, z - 1, s);
			if (s < rrc[z].length - 1)
				fuelleAus(rrc, z, s + 1);
			if (s > 0)
				fuelleAus(rrc, z, s - 1);
		}
	}

	// ---------------------------------------------------------------------
	static public void gibAus(char[][] rrc) {
		// Gibt rrc als rechteckige ASCII-Graphik aus.
		final int len = rrc[0].length;
		printf("+");
		pm(len);
		printf("+%n");
		for (char[] zeile : rrc) {
			printf("|");
			for (char zeichen : zeile) {
				printf("%c", zeichen);
			}
			printf("|%n");
		}
		printf("+");
		pm(len);
		printf("+%n");
	}

	// ---------------------------------------------------------------------
	static public void gibAusDiff(char[][] rrc1, char[][] rrc2) {
		// Gibt eine Fehlermeldung aus, wenn rrc1 und rrc2 nicht genau
		// gleiche Laengen haben.
		// Gibt sonst eine rechteckige ASCII-Graphik aus, die genau so gross
		// ist wie die von gibAus(rrc1) oder gibAus(rrc2). Diese Graphik
		// enthaelt lauter ' ', wo rcc1 und rcc2 uebereinstimmen und
		// enthaelt lauter 'F', wo rcci und rcc2 nicht ueberbeinstimmen

		// Pruefen, rcc1 und rcc2 gleich viele Komponenten haben:
		if (rrc1.length != rrc2.length) {
			printf("rrc1.length != rrc2.length%n");
			return;
		}

		// Pruefen, ob alle Komponenten-Reihungen gleich lang sind:
		for (int i = 0; i < rrc1.length; i++) {
			// Komponenten-Reihungen von rrc1 pruefen:
			if (rrc1[i].length != rrc1[0].length) {
				printf("rcc1[%d].length != rcc1[0].length%n", i);
				return;
			}
			// Komponenten-Reihungen von rrc2 pruefen:
			if (rrc2[i].length != rrc1[0].length) {
				printf("rcc2[%d].length != rcc1[0].length%n", i);
				return;
			}
		}

		// Ein 'F' wie "Fehler" ausgeben, wo die elementaren char-Komponenten
		// von rrc1 und rrc2 ungleich sind. Sonst ein ' ' ausgeben:
		int anzAbweich = 0;
		final int len = rrc1[0].length;
		printf("+");
		pm(len);
		printf("+%n");
		for (int i = 0; i < rrc1.length; i++) {
			printf("|");
			char[] zeile1 = rrc1[i];
			char[] zeile2 = rrc2[i];
			for (int j = 0; j < zeile1.length; j++) {
				char c = ' ';
				if (zeile1[j] != zeile2[j]) {
					c = 'F';
					anzAbweich++;
				}
				printf("%c", c);
			}
			printf("|%n");
		}
		printf("+");
		pm(len);
		printf("+%n");
		printf("Anzahl Abweichungen: %d%n", anzAbweich);
	}

	// ---------------------------------------------------------------------
	static void pm(int anz) {
		// Gibt anz viele Minuszeichen aus
		for (int i = 1; i <= anz; i++)
			printf("-");
	}

	// ---------------------------------------------------------------------
	// Graphiken als Reihungen von String-Objekten und als
	// entsprechende Reihungen von char-Werten:

	static String[] rsa = { "                                        ", "    XXXXXXXXXXXXX                       ",
			"   X             X                      ", "  X    XXXXXXXXXX                       ",
			"  X   X                     XXXXXXXXXXXX", "  X    XXXXXXXXXX          X            ",
			"   X             X         X            ", "    XXXXXXXXXXXXX          X            ",
			"                           X            ", "                           X            ", };

	static String[] rsb = { "                                        ", "    XXXXXXXXXXXXX                       ",
			"   XXXXXXXXXXXXXXX                      ", "  XXXXXXXXXXXXXXX                       ",
			"  XXXXX                     XXXXXXXXXXXX", "  XXXXXXXXXXXXXXX          X            ",
			"   XXXXXXXXXXXXXXX         X            ", "    XXXXXXXXXXXXX          X            ",
			"                           X            ", "                           X            ", };

	static String[] rsc = { "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
			"XXXX             XXXXXXXXXXXXXXXXXXXXXXX", "XXX    XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
			"XXX   XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "XXX    XXXXXXXXXXXXXXXXXXXXX            ",
			"XXXX             XXXXXXXXXXX            ", "XXXXXXXXXXXXXXXXXXXXXXXXXXXX            ",
			"XXXXXXXXXXXXXXXXXXXXXXXXXXXX            ", "XXXXXXXXXXXXXXXXXXXXXXXXXXXX            ", };

	static char[][] rrca = new char[rsa.length][rsa[0].length()];
	static char[][] rrcb = new char[rsa.length][rsa[0].length()];
	static char[][] rrcc = new char[rsa.length][rsa[0].length()];

	// Eine Reihung von Reihungen, die eine Zeile zu viel hat:
	static char[][] rrcd = new char[rsa.length + 1][rsa[0].length()];
	// Eine Reihung von Reihungen, in der spaeter eine Zeile zu lang gemacht wird:
	static char[][] rrce = new char[rsa.length][rsa[0].length()];

	// ---------------------------------------------------------------------
	static void rs2rrc(String[] rs, char[][] rrc) {
		// Wandelt eine Reihung von String-Objekten rs in eine entsprechende
		// Reihung von Reihungen von char-Werten rrc um. Auch die Reihung
		// rrc muss bereits exitieren und die richtigen Laengen haben.
		for (int i = 0; i < rs.length; i++)
			rrc[i] = rs[i].toCharArray();
	}

	// ---------------------------------------------------------------------
	static public void main(String[] args) {
		printf("Rekursion03_Tst: Jetzt geht es los!%n");
		printf("-----------------------------------%n");

		rs2rrc(rsa, rrca);
		rs2rrc(rsb, rrcb);
		printf("%nReihung rrca von der wir ausgehen:%n");
		gibAus(rrca);
		printf("%nReihung rrca nach fuelleAus(rrca, 4, 5);%n");
		fuelleAus(rrca, 4, 5);
		gibAus(rrca);
		printf("%nIst/Soll-Abweichungen sind mit 'F' gekennzeichnet:%n");
		gibAusDiff(rrca, rrcb);

		rs2rrc(rsa, rrca);
		rs2rrc(rsc, rrcc);
		printf("%nReihung rcca nach fuelleAus(rrca, 0, 0);%n");
		fuelleAus(rrca, 0, 0);
		gibAus(rrca);
		printf("%nIst/Soll-Abweichungen sind mit 'F' gekennzeichnet:%n");
		gibAusDiff(rrca, rrcc);

		// printf("%nFehlermeldungen der Methode gibAusDiff:%n");
		// gibAusDiff(rrca, rrcd);
		// rrce[3] = new char[rrce[3].length+1];
		// gibAusDiff(rrca, rrce);

		printf("%n");
		printf("-----------------------------------%n");
		printf("Rekursion03_Tst: Das war's erstmal!%n%n");
	} // main
		// ---------------------------------------------------------------------
		// Eine Methode mit einem kurzen Namen:

	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
	// ---------------------------------------------------------------------
} // class Rekursion03_Tst
/*
 * ------------------------------------------------------------------------ Ausgabe, wenn alles OK ist:
 * 
 * Rekursion03_Tst: Jetzt geht es los! -----------------------------------
 * 
 * Reihung rrca von der wir ausgehen: +----------------------------------------+ | | | XXXXXXXXXXXXX | | X X | | X
 * XXXXXXXXXX | | X X XXXXXXXXXXXX| | X XXXXXXXXXX X | | X X X | | XXXXXXXXXXXXX X | | X | | X |
 * +----------------------------------------+
 * 
 * Reihung rrca nach fuelleAus(rrca, 4, 5); +----------------------------------------+ | | | XXXXXXXXXXXXX | |
 * XXXXXXXXXXXXXXX | | XXXXXXXXXXXXXXX | | XXXXX XXXXXXXXXXXX| | XXXXXXXXXXXXXXX X | | XXXXXXXXXXXXXXX X | |
 * XXXXXXXXXXXXX X | | X | | X | +----------------------------------------+
 * 
 * Ist/Soll-Abweichungen sind mit 'F' gekennzeichnet: +----------------------------------------+ | | | | | | | | | | | |
 * | | | | | | | | +----------------------------------------+ Anzahl Abweichungen: 0
 * 
 * Reihung rcca nach fuelleAus(rrca, 0, 0); +----------------------------------------+
 * |XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX| |XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX| |XXXX XXXXXXXXXXXXXXXXXXXXXXX|
 * |XXX XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX| |XXX XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX| |XXX XXXXXXXXXXXXXXXXXXXXX | |XXXX
 * XXXXXXXXXXX | |XXXXXXXXXXXXXXXXXXXXXXXXXXXX | |XXXXXXXXXXXXXXXXXXXXXXXXXXXX | |XXXXXXXXXXXXXXXXXXXXXXXXXXXX |
 * +----------------------------------------+
 * 
 * Ist/Soll-Abweichungen sind mit 'F' gekennzeichnet: +----------------------------------------+ | | | | | | | | | | | |
 * | | | | | | | | +----------------------------------------+ Anzahl Abweichungen: 0
 * 
 * ----------------------------------- Rekursion03_Tst: Das war's erstmal!
 * 
 * ------------------------------------------------------------------------
 */
/***************************************************************************/
// Datei Rekursion06_Tst.java