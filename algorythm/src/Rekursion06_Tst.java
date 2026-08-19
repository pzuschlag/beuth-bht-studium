
/*
 * ------------------------------------------------------------------------- Dieses Programm Rekursion06_Tst enthaelt
 * die 2 rekursiven Methoden anzWege und AnzPlusbaeume und testet sie.
 * -------------------------------------------------------------------------
 */
class Rekursion06_Tst {
	// ----------------------------------------------------------------------
	public static int anzWege(int x, int y) {
		// x und y sollen nicht-negativ sein.
		// Liefert die Anzahl der kuerzesten Wege, die in einem Rechtecksgitter
		// vom Ursprung (0, 0) zum Punkt (x, y) fuehren.
		// Beispiele:
		// anzWege(0, 0) ist gleich 1 (von (0,0) nach (0,0) gibt es 1 Weg )
		// anzWege(0, 1) ist gleich 1 (von (0,0) nach (0,1) gibt es 1 Weg )
		// anzWege(1, 0) ist gleich 1 (von (0,0) nach (1,0) gibt es 1 Weg )
		// anzWege(1, 1) ist gleich 2 (von (0,0) nach (1,1) gibt es 2 Wege)
		// anzWege(2, 1) ist gleich 3 (von (0,0) nach (2,1) gibt es 3 Wege)
		// anzWege(3, 4) ist gleich 10 (von (0,0) nach (3,4) gibt es 10 Wege)

		return -1; // MUSS ERSETZT WERDEN
	}

	// ----------------------------------------------------------------------
	static long anzPlusbaeume(int n) {
		// Liefert die Anzahl der Plusbaeume die den Wert n haben. Dabei
		// sollte n positiv (groesser gleich 1) sein.
		//
		// Beispiele:
		// anzPlusbaeume(1) ist gleich 1
		// anzPlusbaeume(2) ist gleich 2
		// anzPlusbaeume(3) ist gleich 5
		// anzPlusbaeume(6) ist gleich 188

		return -1; // MUSS ERSETZT WERDEN
	}

	// ----------------------------------------------------------------------
	static private int anzFehler = 0;

	// ----------------------------------------------------------------------
	static private void tst_anzWege(int x, int y, int soll) {
		int ist = anzWege(x, y);

		String text = " <--- OK";
		if (ist != soll) {
			text = " <--- Fehler?";
			anzFehler++;
		}

		printf("anzWege(%d, %d), ist:%4d, soll:%4d %s%n", x, y, ist, soll, text);
	}

	// ----------------------------------------------------------------------
	static private void aus_anzWege() {
		// Gibt die Anzahl von Wegen zwischen zwei Punkten in einem
		// Rechteck-Gitter als ASCII-Gaphik aus

		printf("Anzahl der Wege zwischen zwei%n");
		printf("Punkten in einem Rechteck-Gitter:%n");
		printf("---------------------------------%n");
		final int MAX = 6;
		for (int y = MAX; y >= 0; y--) {
			printf("%d-", y);
			for (int x = 0; x <= MAX; x++) {
				printf("%4d", anzWege(x, y));
			}
			printf("%n");
		}
		printf("%n     |   |   |   |   |   |   |");
		printf("%n     0   1   2   3   4   5   6%n");
	}

	// ----------------------------------------------------------------------
	static private void tst_anzPlusbaeume(int n, long soll) {
		long ist = anzPlusbaeume(n);

		String text = " <--- OK";
		if (ist != soll) {
			text = " <--- Fehler?";
			anzFehler++;
		}

		printf("anzPlusbaeume(%2d), ist:%13d, soll:%13d %s%n", n, ist, soll, text);
	}

	// ----------------------------------------------------------------------
	static public void main(String[] args) {

		printf("Rekursion06_Tst: Das war's erstmal!%n%n");
		printf("-----------------------------------%n");
		tst_anzWege(0, 0, 1);
		tst_anzWege(0, 1, 1);
		tst_anzWege(0, 2, 1);
		tst_anzWege(0, 3, 1);
		tst_anzWege(0, 4, 1);
		tst_anzWege(0, 5, 1);
		printf("%n");
		tst_anzWege(1, 0, 1);
		tst_anzWege(1, 1, 2);
		tst_anzWege(1, 2, 3);
		tst_anzWege(1, 3, 4);
		tst_anzWege(1, 4, 5);
		tst_anzWege(1, 5, 6);
		printf("%n");
		tst_anzWege(2, 0, 1);
		tst_anzWege(2, 1, 3);
		tst_anzWege(2, 2, 6);
		tst_anzWege(2, 3, 10);
		tst_anzWege(2, 4, 15);
		tst_anzWege(2, 5, 21);
		printf("%n");
		tst_anzWege(3, 0, 1);
		tst_anzWege(3, 1, 4);
		tst_anzWege(3, 2, 10);
		tst_anzWege(3, 3, 20);
		tst_anzWege(3, 4, 35);
		tst_anzWege(3, 5, 56);
		printf("%n");
		tst_anzWege(4, 0, 1);
		tst_anzWege(4, 1, 5);
		tst_anzWege(4, 2, 15);
		tst_anzWege(4, 3, 35);
		tst_anzWege(4, 4, 70);
		tst_anzWege(4, 5, 126);
		printf("%n");
		tst_anzWege(5, 0, 1);
		tst_anzWege(5, 1, 6);
		tst_anzWege(5, 2, 21);
		tst_anzWege(5, 3, 56);
		tst_anzWege(5, 4, 126);
		tst_anzWege(5, 5, 252);
		printf("----------------------------------%n");
		aus_anzWege();
		printf("----------------------------------%n");
		tst_anzPlusbaeume(1, 1L);
		tst_anzPlusbaeume(2, 2L);
		tst_anzPlusbaeume(3, 5L);
		tst_anzPlusbaeume(4, 15L);
		tst_anzPlusbaeume(5, 51L);
		tst_anzPlusbaeume(6, 188L);
		tst_anzPlusbaeume(7, 731L);
		tst_anzPlusbaeume(8, 2950L);
		tst_anzPlusbaeume(9, 12235L);
		tst_anzPlusbaeume(10, 51822L);
		tst_anzPlusbaeume(11, 223191L);
		tst_anzPlusbaeume(12, 974427L);
		tst_anzPlusbaeume(13, 4302645L);
		tst_anzPlusbaeume(14, 19181100L);
		tst_anzPlusbaeume(15, 86211885L);
		tst_anzPlusbaeume(16, 390248055L);
		tst_anzPlusbaeume(17, 1777495635L);
		tst_anzPlusbaeume(18, 8140539950L);
		tst_anzPlusbaeume(19, 37463689775L);
		tst_anzPlusbaeume(20, 173164232965L);
		printf("-----------------------------------%n");
		printf("Anzahl Fehler: %d%n", anzFehler);
		printf("-----------------------------------%n");
		printf("Rekursion06_Tst: Das war's erstmal!%n%n");
	} // main
		// ----------------------------------------------------------------------
		// Eine Methode mit einem kurzen Namen:

	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
	// ----------------------------------------------------------------------
} // class Rekursion06_Tst
/*
 * ------------------------------------------------------------------------- Ausgabe (wenn alles OK ist):
 * 
 * Rekursion06: Jetzt geht es los! ---------------------------------- anzWege(0, 0), ist: 1, soll: 1 <--- OK anzWege(0,
 * 1), ist: 1, soll: 1 <--- OK anzWege(0, 2), ist: 1, soll: 1 <--- OK anzWege(0, 3), ist: 1, soll: 1 <--- OK anzWege(0,
 * 4), ist: 1, soll: 1 <--- OK anzWege(0, 5), ist: 1, soll: 1 <--- OK
 * 
 * anzWege(1, 0), ist: 1, soll: 1 <--- OK anzWege(1, 1), ist: 2, soll: 2 <--- OK anzWege(1, 2), ist: 3, soll: 3 <--- OK
 * anzWege(1, 3), ist: 4, soll: 4 <--- OK anzWege(1, 4), ist: 5, soll: 5 <--- OK anzWege(1, 5), ist: 6, soll: 6 <--- OK
 * 
 * anzWege(2, 0), ist: 1, soll: 1 <--- OK anzWege(2, 1), ist: 3, soll: 3 <--- OK anzWege(2, 2), ist: 6, soll: 6 <--- OK
 * anzWege(2, 3), ist: 10, soll: 10 <--- OK anzWege(2, 4), ist: 15, soll: 15 <--- OK anzWege(2, 5), ist: 21, soll: 21
 * <--- OK
 * 
 * anzWege(3, 0), ist: 1, soll: 1 <--- OK anzWege(3, 1), ist: 4, soll: 4 <--- OK anzWege(3, 2), ist: 10, soll: 10 <---
 * OK anzWege(3, 3), ist: 20, soll: 20 <--- OK anzWege(3, 4), ist: 35, soll: 35 <--- OK anzWege(3, 5), ist: 56, soll: 56
 * <--- OK
 * 
 * anzWege(4, 0), ist: 1, soll: 1 <--- OK anzWege(4, 1), ist: 5, soll: 5 <--- OK anzWege(4, 2), ist: 15, soll: 15 <---
 * OK anzWege(4, 3), ist: 35, soll: 35 <--- OK anzWege(4, 4), ist: 70, soll: 70 <--- OK anzWege(4, 5), ist: 126, soll:
 * 126 <--- OK
 * 
 * anzWege(5, 0), ist: 1, soll: 1 <--- OK anzWege(5, 1), ist: 6, soll: 6 <--- OK anzWege(5, 2), ist: 21, soll: 21 <---
 * OK anzWege(5, 3), ist: 56, soll: 56 <--- OK anzWege(5, 4), ist: 126, soll: 126 <--- OK anzWege(5, 5), ist: 252, soll:
 * 252 <--- OK ---------------------------------- Anzahl der Wege zwischen zwei Punkten in einem Rechteck-Gitter:
 * --------------------------------- 6- 1 7 28 84 210 462 924 5- 1 6 21 56 126 252 462 4- 1 5 15 35 70 126 210 3- 1 4 10
 * 20 35 56 84 2- 1 3 6 10 15 21 28 1- 1 2 3 4 5 6 7 0- 1 1 1 1 1 1 1
 * 
 * | | | | | | | 0 1 2 3 4 5 6 ---------------------------------- anzPlusbaeume( 1), ist: 1, soll: 1 <--- OK
 * anzPlusbaeume( 2), ist: 2, soll: 2 <--- OK anzPlusbaeume( 3), ist: 5, soll: 5 <--- OK anzPlusbaeume( 4), ist: 15,
 * soll: 15 <--- OK anzPlusbaeume( 5), ist: 51, soll: 51 <--- OK anzPlusbaeume( 6), ist: 188, soll: 188 <--- OK
 * anzPlusbaeume( 7), ist: 731, soll: 731 <--- OK anzPlusbaeume( 8), ist: 2.950, soll: 2.950 <--- OK anzPlusbaeume( 9),
 * ist: 12.235, soll: 12.235 <--- OK anzPlusbaeume(10), ist: 51.822, soll: 51.822 <--- OK anzPlusbaeume(11), ist:
 * 223.191, soll: 223.191 <--- OK anzPlusbaeume(12), ist: 974.427, soll: 974.427 <--- OK anzPlusbaeume(13), ist:
 * 4.302.645, soll: 4.302.645 <--- OK anzPlusbaeume(14), ist: 19.181.100, soll: 19.181.100 <--- OK anzPlusbaeume(15),
 * ist: 86.211.885, soll: 86.211.885 <--- OK anzPlusbaeume(16), ist: 390.248.055, soll: 390.248.055 <--- OK
 * anzPlusbaeume(17), ist: 1.777.495.635, soll: 1.777.495.635 <--- OK anzPlusbaeume(18), ist: 8.140.539.950, soll:
 * 8.140.539.950 <--- OK anzPlusbaeume(19), ist: 37.463.689.775, soll: 37.463.689.775 <--- OK anzPlusbaeume(20), ist:
 * 173.164.232.965, soll: 173.164.232.965 <--- OK ---------------------------------- Anzahl Fehler: 0
 * ---------------------------------- Rekursion06: Das war's erstmal!
 * -------------------------------------------------------------------------
 */