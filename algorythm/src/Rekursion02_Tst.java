// ***************************************************************************
// Datei Rekursion02_Tst.java
/*
 * ------------------------------------------------------------------------ Dieses Programm Rekursion02_Tst enthaelt die
 * 6 rekursiven Methoden zehner_Ziffer, anz_10_er_Ziffern, als_10_er_Zahl b_er_Ziffer, anz_b_er_Ziffern, als_b_er_Zahl
 * und testet sie.
 * 
 * Alle Methoden haben mit Ziffern von Zahlen zu tun, die in verschiedenen Zahlensystemen (Stellenwertsystemen)
 * dargestellt sind. ------------------------------------------------------------------------
 */
class Rekursion02_Tst {
	// ---------------------------------------------------------------------
	static final int IMAX = Integer.MAX_VALUE; // Eine Abkuerzung

	// ---------------------------------------------------------------------
	static int zehner_Ziffer(int n, int s) {
		// Liefert die Ziffer, die in der Darstellung der Zahl n als
		// 10-er-Zahl (als Zahl im Stellenwertsystem mit der Basis 10)
		// an der Stelle s steht.

		if (s == 0) {
			if (n < 10)
				return -n % 10;
			return n % 10;
		}

		return zehner_Ziffer(n / 10, s - 1);

		// Beispiele:
		// zehner_Ziffer( 17, 0) ist gleich 7
		// zehner_Ziffer( 17, 1) ist gleich 1
		// zehner_Ziffer( 17, 2) ist gleich 0
		// zehner_Ziffer(-17, 0) ist gleich 7
		// zehner_Ziffer(-17, 1) ist gleich 1
		// zehner_Ziffer(-17, 2) ist gleich 0
	}

	// ---------------------------------------------------------------------
	static public int anz_10_er_Ziffern(int n) {
		// Liefert die Anzahl der Ziffern die man braucht, um
		// n als 10-er-Zahl (d.h. als Dezimalzahl) darzustellen.
		// Verlaesst sich darauf, dass n nicht negativ ist.

		if (n < 10)
			return 1;

		return (anz_10_er_Ziffern(n / 10) + 1);

		// Beispiele:
		// anz_10_er_Ziffern( 0) ist gleich 1
		// anz_10_er_Ziffern( 9) ist gleich 1
		// anz_10_er_Ziffern( 10) ist gleich 2
		// anz_10_er_Ziffern(725) ist gleich 3
	}

	// ---------------------------------------------------------------------
	static int b_er_Ziffer(int b, int n, int s) {
		// Verlaesst sich darauf, dass b groesser oder gleich 2 ist.
		// Liefert die Ziffer, die in der Darstellung der Zahl n als
		// b-er-Zahl (als Zahl im Stellenwertsystem mit der Basis b)
		// an der Stelle s steht.

		if (s == 0) {
			return n % b;
		}

		return (b_er_Ziffer(b, n / b, s - 1));

		// Beispiele:
		// b_er_Ziffer(10, 17, 0) ist gleich 7
		// b_er_Ziffer(10, 17, 1) ist gleich 1
		// b_er_Ziffer(10, 17, 2) ist gleich 0
		// b_er_Ziffer(10,-17, 0) ist gleich 7
		// b_er_Ziffer(10,-17, 1) ist gleich 1
		// b_er_Ziffer(10,-17, 2) ist gleich 0
		// b_er_Ziffer( 2, 5, 0) ist gleich 1
		// b_er_Ziffer( 2, 5, 1) ist gleich 0
		// b_er_Ziffer( 2, 5, 2) ist gleich 1
		// b_er_Ziffer( 2, 5, 3) ist gleich 0
		// b_er_Ziffer(16, 15, 0) ist gleich 15
		// b_er_Ziffer(16, 15, 1) ist gleich 0
		// b_er_Ziffer(16,171, 0) ist gleich 11
		// b_er_Ziffer(16,171, 1) ist gleich 10
		// b_er_Ziffer(16,171, 2) ist gleich 0
	}

	// ---------------------------------------------------------------------
	static public int anz_b_er_Ziffern(int b, int n) {
		// Liefert die Anzahl der Ziffern die man braucht, um
		// n als b-er-Zahl darzustellen.
		// Verlaesst sich darauf, dass n nicht negativ und
		// b groesser als 1 ist.

		if (n < b)
			return 1;

		return anz_b_er_Ziffern(b, n / b) + 1;

		// Beispiele:
		// anz_b_er_Ziffern(10, 0) ist gleich 1
		// anz_b_er_Ziffern(10, 9) ist gleich 1
		// anz_b_er_Ziffern(10, 10) ist gleich 2
		// anz_b_er_Ziffern(10, 725) ist gleich 3
		// anz_b_er_Ziffern( 2, 6) ist gleich 3
		// anz_b_er_Ziffern( 2, 15) ist gleich 4
		// anz_b_er_Ziffern( 2, 16) ist gleich 5
		// anz_b_er_Ziffern( 7, 6) ist gleich 1
		// anz_b_er_Ziffern( 7, 7) ist gleich 2
		// anz_b_er_Ziffern( 7, 48) ist gleich 2
		// anz_b_er_Ziffern( 7, 49) ist gleich 3
	}

	// ---------------------------------------------------------------------
	static public String als_10_er_Zahl(int n) {
		// Liefert n als 10-er-Zahl.
		// Verlaesst sich darauf, dass n nicht negativ ist.

		final String[] TAB = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

		if (n <= 9)
			return TAB[n % 10];

		return als_10_er_Zahl(n / 10) + TAB[n % 10];

		// Beispiele:
		// als_10_er_Zahl( 0) ist gleich "0"
		// als_10_er_Zahl( 9) ist gleich "9"
		// als_10_er_Zahl( 10) ist gleich "10"
		// als_10_er_Zahl(725) ist gleich "725"
		//
		// Fuer diese Aufgabe gibt es eine triviale Loesung:
		// return "" + n;
		// Der Operator + fuehrt dann "alle interessanten Umwandlungen" aus.
		// Statt die triviale Loesung zu benutzen, sollen Sie eine rekursive
		// Methode schreiben, die den int-Wert n in seine einzelnen
		// 10-er-Ziffern zerlegt, jede Ziffer mit Hilfe der Reihung TAB
		// in einen String umwandelt und die einzelnen Strings mit + kon-
		// kateniert (d.h. "zu einem String zusammenpappt").
		// Wenn Sie diese Aufgabe so loesen wie hier angedeutet, dann ist
		// (als Belohnung) die folgende Aufgabe-04 ganz einfach.

	}

	// ---------------------------------------------------------------------
	static public String als_b_er_Zahl(int b, int n) {
		// Liefert n als b-er-Zahl.
		// Verlaesst sich darauf, dass n nicht negativ ist und
		// dass b im Intervall [2, 36] liegt.
		// Wenn b groesser als 10 ist, werden die Grossbuchstaben
		// A, B, ..., Z als Ziffern (mit den Werten 10, 11, ..., 35)
		// verwendet.

		final String[] TAB = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
				"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", };

		if (n <= b - 1)
			return TAB[n % b];

		return als_b_er_Zahl(b, n / b) + TAB[n % b];

		// Beispiele:
		// String als_b_er_Zahl(10, 0) ist gleich "0"
		// String als_b_er_Zahl(10, 9) ist gleich "9"
		// String als_b_er_Zahl(10, 10) ist gleich "10"
		// String als_b_er_Zahl(10, 725) ist gleich "725"
		// String als_b_er_Zahl( 2, 6) ist gleich "110"
		// String als_b_er_Zahl( 2, 15) ist gleich "1111"
		// String als_b_er_Zahl( 2, 16) ist gleich "10000"
		// String als_b_er_Zahl( 7, 6) ist gleich "6"
		// String als_b_er_Zahl( 7, 7) ist gleich "10"
		// String als_b_er_Zahl( 7, 48) ist gleich "66"
		// String als_b_er_Zahl( 7, 49) ist gleich "100"
		// String als_b_er_Zahl(36, 35) ist gleich "Z"
		// String als_b_er_Zahl(36, 36) ist gleich "10"
		// String als_b_er_Zahl(36, 360) ist gleich "A0"
	}

	// ---------------------------------------------------------------------
	static private int anzFehler = 0;

	static private void tst(String nam, Object soll, Object ist, Object... param) {
		// Fuehrt einen Test mit einer der 6 Funktionen durch

		StringBuilder text1 = new StringBuilder(nam + "(" + param[0]);
		for (int i = 1; i < param.length; i++)
			text1.append("," + param[i]);
		text1.append(")");

		String text2 = String.format("%10s|%-10s", ist, soll);

		String text3 = "OK";
		if (!ist.toString().equals(soll.toString())) {
			text3 = "<-- Fehler?";
			anzFehler++;
		}

		printf("%-32s %-21s %s%n", text1, text2, text3);
	}

	// ---------------------------------------------------------------------
	static private void tst_zehner_Ziffer() {
		tst("zehner_Ziffer", 7, zehner_Ziffer(17, 0), 17, 0);
		tst("zehner_Ziffer", 1, zehner_Ziffer(17, 1), 17, 1);
		tst("zehner_Ziffer", 0, zehner_Ziffer(17, 2), 17, 2);
		tst("zehner_Ziffer", 7, zehner_Ziffer(-17, 0), -17, 0);
		tst("zehner_Ziffer", 1, zehner_Ziffer(-17, 1), -17, 1);
		tst("zehner_Ziffer", 0, zehner_Ziffer(-17, 2), -17, 2);
		tst("zehner_Ziffer", 7, zehner_Ziffer(IMAX, 0), IMAX, 0);
		tst("zehner_Ziffer", 2, zehner_Ziffer(IMAX, 9), IMAX, 9);
		tst("zehner_Ziffer", 0, zehner_Ziffer(IMAX, 10), IMAX, 10);
	}

	// ---------------------------------------------------------------------
	static private void tst_anz_10_er_Ziffern() {
		tst("anz_10_er_Ziffern", 1, anz_10_er_Ziffern(0), 0);
		tst("anz_10_er_Ziffern", 1, anz_10_er_Ziffern(9), 9);
		tst("anz_10_er_Ziffern", 2, anz_10_er_Ziffern(10), 10);
		tst("anz_10_er_Ziffern", 2, anz_10_er_Ziffern(99), 99);
		tst("anz_10_er_Ziffern", 3, anz_10_er_Ziffern(100), 100);
		tst("anz_10_er_Ziffern", 3, anz_10_er_Ziffern(999), 999);
		tst("anz_10_er_Ziffern", 10, anz_10_er_Ziffern(IMAX), IMAX);
	}

	// ---------------------------------------------------------------------
	static private void tst_b_er_Ziffer() {
		tst("b_er_Ziffer", 7, b_er_Ziffer(10, 17, 0), 10, 17, 0);
		tst("b_er_Ziffer", 1, b_er_Ziffer(10, 17, 1), 10, 17, 1);
		tst("b_er_Ziffer", 0, b_er_Ziffer(10, 17, 2), 10, 17, 2);
		tst("b_er_Ziffer", 7, b_er_Ziffer(10, -17, 0), 10, -17, 0);
		tst("b_er_Ziffer", 1, b_er_Ziffer(10, -17, 1), 10, -17, 1);
		tst("b_er_Ziffer", 0, b_er_Ziffer(10, -17, 2), 10, -17, 2);
		tst("b_er_Ziffer", 1, b_er_Ziffer(2, 5, 0), 2, 5, 0);
		tst("b_er_Ziffer", 0, b_er_Ziffer(2, 5, 1), 2, 5, 1);
		tst("b_er_Ziffer", 1, b_er_Ziffer(2, 5, 2), 2, 5, 2);
		tst("b_er_Ziffer", 0, b_er_Ziffer(2, 5, 3), 2, 5, 3);
		tst("b_er_Ziffer", 15, b_er_Ziffer(16, 15, 0), 16, 15, 0);
		tst("b_er_Ziffer", 0, b_er_Ziffer(16, 15, 1), 16, 15, 1);
		tst("b_er_Ziffer", 11, b_er_Ziffer(16, 171, 0), 16, 171, 0);
		tst("b_er_Ziffer", 10, b_er_Ziffer(16, 171, 1), 16, 171, 1);
		tst("b_er_Ziffer", 0, b_er_Ziffer(16, 171, 2), 16, 171, 2);
	}

	// ---------------------------------------------------------------------
	static private void tst_anz_b_er_Ziffern() {
		tst("anz_b_er_Ziffern", 1, anz_b_er_Ziffern(10, 0), 10, 0);
		tst("anz_b_er_Ziffern", 1, anz_b_er_Ziffern(10, 9), 10, 9);
		tst("anz_b_er_Ziffern", 2, anz_b_er_Ziffern(10, 10), 10, 10);
		tst("anz_b_er_Ziffern", 2, anz_b_er_Ziffern(10, 99), 10, 99);
		tst("anz_b_er_Ziffern", 3, anz_b_er_Ziffern(10, 100), 10, 100);
		tst("anz_b_er_Ziffern", 3, anz_b_er_Ziffern(10, 999), 10, 999);
		tst("anz_b_er_Ziffern", 10, anz_b_er_Ziffern(10, IMAX), 10, IMAX);
		printf("%n");
		tst("anz_b_er_Ziffern", 1, anz_b_er_Ziffern(2, 0), 2, 0);
		tst("anz_b_er_Ziffern", 1, anz_b_er_Ziffern(2, 1), 2, 1);
		tst("anz_b_er_Ziffern", 2, anz_b_er_Ziffern(2, 2), 2, 2);
		tst("anz_b_er_Ziffern", 2, anz_b_er_Ziffern(2, 3), 2, 3);
		tst("anz_b_er_Ziffern", 3, anz_b_er_Ziffern(2, 4), 2, 4);
		tst("anz_b_er_Ziffern", 3, anz_b_er_Ziffern(2, 7), 2, 7);
		tst("anz_b_er_Ziffern", 4, anz_b_er_Ziffern(2, 8), 2, 8);
		tst("anz_b_er_Ziffern", 4, anz_b_er_Ziffern(2, 15), 2, 15);
		tst("anz_b_er_Ziffern", 5, anz_b_er_Ziffern(2, 16), 2, 16);
		tst("anz_b_er_Ziffern", 5, anz_b_er_Ziffern(2, 31), 2, 31);
		tst("anz_b_er_Ziffern", 6, anz_b_er_Ziffern(2, 32), 2, 32);
		tst("anz_b_er_Ziffern", 6, anz_b_er_Ziffern(2, 63), 2, 63);
		tst("anz_b_er_Ziffern", 31, anz_b_er_Ziffern(2, IMAX), 2, IMAX);
		printf("%n");
		tst("anz_b_er_Ziffern", 1, anz_b_er_Ziffern(100, 0), 100, 0);
		tst("anz_b_er_Ziffern", 1, anz_b_er_Ziffern(100, 99), 100, 99);
		tst("anz_b_er_Ziffern", 1, anz_b_er_Ziffern(100, 99), 100, 99);
		tst("anz_b_er_Ziffern", 2, anz_b_er_Ziffern(100, 100), 100, 100);
		tst("anz_b_er_Ziffern", 2, anz_b_er_Ziffern(100, 9999), 100, 9999);
		tst("anz_b_er_Ziffern", 3, anz_b_er_Ziffern(100, 10000), 100, 10000);
		tst("anz_b_er_Ziffern", 3, anz_b_er_Ziffern(100, 999999), 100, 999999);
		tst("anz_b_er_Ziffern", 5, anz_b_er_Ziffern(100, IMAX), 100, IMAX);
	}

	// ---------------------------------------------------------------------
	static private void tst_als_10_er_Zahl() {
		tst("als_10_er_Zahl", 0, als_10_er_Zahl(0), 0);
		tst("als_10_er_Zahl", 1, als_10_er_Zahl(1), 1);
		tst("als_10_er_Zahl", 9, als_10_er_Zahl(9), 9);
		tst("als_10_er_Zahl", 10, als_10_er_Zahl(10), 10);
		tst("als_10_er_Zahl", 99, als_10_er_Zahl(99), 99);
		tst("als_10_er_Zahl", 100, als_10_er_Zahl(100), 100);
		tst("als_10_er_Zahl", 123, als_10_er_Zahl(123), 123);
		tst("als_10_er_Zahl", IMAX, als_10_er_Zahl(IMAX), IMAX);
	}

	// ---------------------------------------------------------------------
	static private void tst_als_b_er_Zahl() {
		tst("als_b_er_Zahl", 0, als_b_er_Zahl(10, 0), 10, 0);
		tst("als_b_er_Zahl", 1, als_b_er_Zahl(10, 1), 10, 1);
		tst("als_b_er_Zahl", 9, als_b_er_Zahl(10, 9), 10, 9);
		tst("als_b_er_Zahl", 10, als_b_er_Zahl(10, 10), 10, 10);
		tst("als_b_er_Zahl", 99, als_b_er_Zahl(10, 99), 10, 99);
		tst("als_b_er_Zahl", 100, als_b_er_Zahl(10, 100), 10, 100);
		tst("als_b_er_Zahl", 123, als_b_er_Zahl(10, 123), 10, 123);
		tst("als_b_er_Zahl", IMAX, als_b_er_Zahl(10, IMAX), 10, IMAX);
		printf("%n");
		tst("als_b_er_Zahl", 0, als_b_er_Zahl(2, 0), 2, 0);
		tst("als_b_er_Zahl", 1, als_b_er_Zahl(2, 1), 2, 1);
		tst("als_b_er_Zahl", 10, als_b_er_Zahl(2, 2), 2, 2);
		tst("als_b_er_Zahl", 11, als_b_er_Zahl(2, 3), 2, 3);
		tst("als_b_er_Zahl", 100, als_b_er_Zahl(2, 4), 2, 4);
		tst("als_b_er_Zahl", 111, als_b_er_Zahl(2, 7), 2, 7);
		tst("als_b_er_Zahl", 1000, als_b_er_Zahl(2, 8), 2, 8);
		tst("als_b_er_Zahl", 1111, als_b_er_Zahl(2, 15), 2, 15);
		tst("als_b_er_Zahl", 10000, als_b_er_Zahl(2, 16), 2, 16);
		tst("als_b_er_Zahl", 11111, als_b_er_Zahl(2, 31), 2, 31);
		tst("als_b_er_Zahl", 100000, als_b_er_Zahl(2, 32), 2, 32);
		tst("als_b_er_Zahl", 111111, als_b_er_Zahl(2, 63), 2, 63);
		tst("als_b_er_Zahl", "1111111111111111111111111111111", als_b_er_Zahl(2, IMAX), 2, IMAX);
		printf("%n");
		tst("als_b_er_Zahl", "0", als_b_er_Zahl(16, 0), 16, 0);
		tst("als_b_er_Zahl", "F", als_b_er_Zahl(16, 15), 16, 15);
		tst("als_b_er_Zahl", "10", als_b_er_Zahl(16, 16), 16, 16);
		tst("als_b_er_Zahl", "A0", als_b_er_Zahl(16, 160), 16, 160);
		tst("als_b_er_Zahl", "FFF", als_b_er_Zahl(16, 4095), 16, 4095);
		tst("als_b_er_Zahl", "1000", als_b_er_Zahl(16, 4096), 16, 4096);
		tst("als_b_er_Zahl", "7FFFFFFF", als_b_er_Zahl(16, IMAX), 16, IMAX);
		printf("%n");
		tst("als_b_er_Zahl", "Z", als_b_er_Zahl(36, 35), 36, 35);
		tst("als_b_er_Zahl", "10", als_b_er_Zahl(36, 36), 36, 36);
		tst("als_b_er_Zahl", "A0", als_b_er_Zahl(36, 360), 36, 360);
		tst("als_b_er_Zahl", "ZIK0ZJ", als_b_er_Zahl(36, IMAX), 36, IMAX);
	}

	// ---------------------------------------------------------------------
	static public void main(String[] args) {
		// Anfangs wird nur die Methode zehner_Ziffer getestet.
		// Um andere Methoden zu testen muessen Sie die entsprechenden
		// Methodenaufrufe (tst_anz_10_er_Ziffern, tst_b_er_Ziffern, ...)
		// aktivieren (oder: entkommentarisieren).

		printf("Rekursion02_Tst: Jetzt geht es los!%n");
		printf("---------------------------------+--------------------+%n");
		printf("Aufruf der zu testenden Funktion | Ist-Erg.|Soll-Erg  |%n");
		printf("---------------------------------+---------+----------+%n");
		tst_zehner_Ziffer();
		printf("-------------------------------------------+-----------%n");
		tst_anz_10_er_Ziffern();
		printf("-------------------------------------------+-----------%n");
		tst_b_er_Ziffer();
		printf("-------------------------------------------+-----------%n");
		tst_anz_b_er_Ziffern();
		printf("-------------------------------------------+-----------%n");
		tst_als_10_er_Zahl();
		printf("-------------------------------------------+-----------%n");
		tst_als_b_er_Zahl();
		printf("-------------------------------------------+-----------%n");
		printf("Anzahl Fehler: %s%n", anzFehler);
		printf("-------------------------------------------------------%n");
		printf("Rekursion02_Tst: Das war's erstmal!%n%n");
	} // main
		// ---------------------------------------------------------------------
		// Eine Methode mit einem kurzen Namen:

	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
	// ---------------------------------------------------------------------
} // class Rekursion02_Tst
/*
 * ------------------------------------------------------------------------ Ausgabe (wenn alle 6 Methoden korrekt
 * implementiert sind):
 * 
 * Rekursion02_Tst: Jetzt geht es los! ------------------------------------------------------- Der senkrechte Strich
 * trennt soll und ist: soll|ist ------------------------------------------------------- zehner_Ziffer(17,0) 7|7 ) OK
 * zehner_Ziffer(17,1) 1|1 ) OK zehner_Ziffer(17,2) 0|0 ) OK zehner_Ziffer(-17,0) 7|7 ) OK zehner_Ziffer(-17,1) 1|1 ) OK
 * zehner_Ziffer(-17,2) 0|0 ) OK zehner_Ziffer(2147483647,0) 7|7 ) OK zehner_Ziffer(2147483647,9) 2|2 ) OK
 * zehner_Ziffer(2147483647,10) 0|0 ) OK ------------------------------------------------------- anz_10_er_Ziffern(0)
 * 1|1 ) OK anz_10_er_Ziffern(9) 1|1 ) OK anz_10_er_Ziffern(10) 2|2 ) OK anz_10_er_Ziffern(99) 2|2 ) OK
 * anz_10_er_Ziffern(100) 3|3 ) OK anz_10_er_Ziffern(999) 3|3 ) OK anz_10_er_Ziffern(2147483647) 10|10 ) OK
 * ------------------------------------------------------- b_er_Ziffer(10,17,0) 7|7 ) OK b_er_Ziffer(10,17,1) 1|1 ) OK
 * b_er_Ziffer(10,17,2) 0|0 ) OK b_er_Ziffer(10,-17,0) 7|7 ) OK b_er_Ziffer(10,-17,1) 1|1 ) OK b_er_Ziffer(10,-17,2) 0|0
 * ) OK b_er_Ziffer(2,5,0) 1|1 ) OK b_er_Ziffer(2,5,1) 0|0 ) OK b_er_Ziffer(2,5,2) 1|1 ) OK b_er_Ziffer(2,5,3) 0|0 ) OK
 * b_er_Ziffer(16,15,0) 15|15 ) OK b_er_Ziffer(16,15,1) 0|0 ) OK b_er_Ziffer(16,171,0) 11|11 ) OK b_er_Ziffer(16,171,1)
 * 10|10 ) OK b_er_Ziffer(16,171,2) 0|0 ) OK -------------------------------------------------------
 * anz_b_er_Ziffern(10,0) 1|1 ) OK anz_b_er_Ziffern(10,9) 1|1 ) OK anz_b_er_Ziffern(10,10) 2|2 ) OK
 * anz_b_er_Ziffern(10,99) 2|2 ) OK anz_b_er_Ziffern(10,100) 3|3 ) OK anz_b_er_Ziffern(10,999) 3|3 ) OK
 * anz_b_er_Ziffern(10,2147483647) 10|10 ) OK
 * 
 * anz_b_er_Ziffern(2,0) 1|1 ) OK anz_b_er_Ziffern(2,1) 1|1 ) OK anz_b_er_Ziffern(2,2) 2|2 ) OK anz_b_er_Ziffern(2,3)
 * 2|2 ) OK anz_b_er_Ziffern(2,4) 3|3 ) OK anz_b_er_Ziffern(2,7) 3|3 ) OK anz_b_er_Ziffern(2,8) 4|4 ) OK
 * anz_b_er_Ziffern(2,15) 4|4 ) OK anz_b_er_Ziffern(2,16) 5|5 ) OK anz_b_er_Ziffern(2,31) 5|5 ) OK
 * anz_b_er_Ziffern(2,32) 6|6 ) OK anz_b_er_Ziffern(2,63) 6|6 ) OK anz_b_er_Ziffern(2,2147483647) 31|31 ) OK
 * 
 * anz_b_er_Ziffern(100,0) 1|1 ) OK anz_b_er_Ziffern(100,99) 1|1 ) OK anz_b_er_Ziffern(100,99) 1|1 ) OK
 * anz_b_er_Ziffern(100,100) 2|2 ) OK anz_b_er_Ziffern(100,9999) 2|2 ) OK anz_b_er_Ziffern(100,10000) 3|3 ) OK
 * anz_b_er_Ziffern(100,999999) 3|3 ) OK anz_b_er_Ziffern(100,2147483647) 5|5 ) OK
 * ------------------------------------------------------- als_10_er_Zahl(0) 0|0 ) OK als_10_er_Zahl(1) 1|1 ) OK
 * als_10_er_Zahl(9) 9|9 ) OK als_10_er_Zahl(10) 10|10 ) OK als_10_er_Zahl(99) 99|99 ) OK als_10_er_Zahl(100) 100|100 )
 * OK als_10_er_Zahl(123) 123|123 ) OK als_10_er_Zahl(2147483647) 2147483647|2147483647) OK
 * ------------------------------------------------------- als_b_er_Zahl(10,0) 0|0 ) OK als_b_er_Zahl(10,1) 1|1 ) OK
 * als_b_er_Zahl(10,9) 9|9 ) OK als_b_er_Zahl(10,10) 10|10 ) OK als_b_er_Zahl(10,99) 99|99 ) OK als_b_er_Zahl(10,100)
 * 100|100 ) OK als_b_er_Zahl(10,123) 123|123 ) OK als_b_er_Zahl(10,2147483647) 2147483647|2147483647) OK
 * 
 * als_b_er_Zahl(2,0) 0|0 ) OK als_b_er_Zahl(2,1) 1|1 ) OK als_b_er_Zahl(2,2) 10|10 ) OK als_b_er_Zahl(2,3) 11|11 ) OK
 * als_b_er_Zahl(2,4) 100|100 ) OK als_b_er_Zahl(2,7) 111|111 ) OK als_b_er_Zahl(2,8) 1000|1000 ) OK als_b_er_Zahl(2,15)
 * 1111|1111 ) OK als_b_er_Zahl(2,16) 10000|10000 ) OK als_b_er_Zahl(2,31) 11111|11111 ) OK als_b_er_Zahl(2,32)
 * 100000|100000 ) OK als_b_er_Zahl(2,63) 111111|111111 ) OK als_b_er_Zahl(2,2147483647)
 * 1111111111111111111111111111111|1111111111111111111111111111111) OK
 * 
 * als_b_er_Zahl(16,0) 0|0 ) OK als_b_er_Zahl(16,15) F|F ) OK als_b_er_Zahl(16,16) 10|10 ) OK als_b_er_Zahl(16,160)
 * A0|A0 ) OK als_b_er_Zahl(16,4095) FFF|FFF ) OK als_b_er_Zahl(16,4096) 1000|1000 ) OK als_b_er_Zahl(16,2147483647)
 * 7FFFFFFF|7FFFFFFF ) OK
 * 
 * als_b_er_Zahl(36,35) Z|Z ) OK als_b_er_Zahl(36,36) 10|10 ) OK als_b_er_Zahl(36,360) A0|A0 ) OK
 * als_b_er_Zahl(36,2147483647) ZIK0ZJ|ZIK0ZJ ) OK -------------------------------------------------------
 * Rekursion02_Tst: Das war's erstmal! ------------------------------------------------------------------------
 */
