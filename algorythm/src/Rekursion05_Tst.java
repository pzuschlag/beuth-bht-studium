/***************************************************************************
 * // Datei Rekursion05_Tst.java ------------------------------------------------------------------------ Dieses
 * Programm Rekursion05_Tst enthaelt 2 rekursiven Methoden namens anzRobos und testet sie.
 * 
 * Die Methoden berechnen das Wachstum von Gruppen von Robotern, die Roboter bauen.
 * ------------------------------------------------------------------------
 */
class Rekursion05_Tst {
	// ---------------------------------------------------------------------
	static long anzRobos(long tage) {
		// Liefert die Anzahl der Roboter, die wir nach tage vielen
		// Produktionstagen haben, wenn wir mit 3 Robotern beginnen und
		// 3 Roboter gemeinsam an einem Tag einen weiteren Roboter bauen.
		//
		// Beispiele:
		// anzRobos(0) ist gleich 3
		// anzRobos(1) ist gleich 4
		// anzRobos(2) ist gleich 5
		// anzRobos(3) ist gleich 6
		// anzRobos(4) ist gleich 8

		return -1; // MUSS ERSETZT WERDEN
	}

	// ---------------------------------------------------------------------
	static long anzRobos(long n, long tage) {
		// Liefert die Anzahl der Roboter, die wir nach tage vielen
		// Produktionstagen haben, wenn wir mit n Robotern beginnen und
		// n Roboter gemeinsam an einem Tag einen weiteren Roboter bauen.
		//
		// Beispiele:
		// anzRobos(3, 0) ist gleich 3
		// anzRobos(3, 1) ist gleich 4
		// anzRobos(3, 2) ist gleich 5
		// anzRobos(3, 3) ist gleich 6
		// anzRobos(3, 4) ist gleich 8
		//
		// anzRobos(2, 0) ist gleich 2
		// anzRobos(2, 1) ist gleich 3
		// anzRobos(2, 2) ist gleich 4
		// anzRobos(2, 3) ist gleich 6
		//
		// anzRobos(5, 0) ist gleich 5
		// anzRobos(5, 5) ist gleich 10
		// anzRobos(5, 6) ist gleich 12

		return -1; // MUSS ERSETZT WERDEN
	}

	// ---------------------------------------------------------------------
	static private int anzFehler = 0;

	// ---------------------------------------------------------------------
	static private void tst3(long tage, long soll) {
		long ist = anzRobos(tage);

		String text = " <--- OK";
		if (ist != soll) {
			text = " <--- Fehler?";
			anzFehler++;
		}

		printf("anzRobos(%2d),      ist:%,13d, soll:%,13d %s%n", tage, ist, soll, text);
	}

	// ---------------------------------------------------------------------
	static private void tstn(long n, long tage, long soll) {
		long ist = anzRobos(n, tage);

		String text = " <--- OK";
		if (ist != soll) {
			text = " <--- Fehler?";
			anzFehler++;
		}

		printf("anzRobos(%2d, %3d), ist:%,13d, soll:%,13d %s%n", n, tage, ist, soll, text);
	}

	// ---------------------------------------------------------------------
	static public void main(String[] args) {
		printf("Rekursion05_Tst: Jetzt geht es los!%n");
		printf("-----------------------------------%n");
		tst3(0, 3);
		tst3(1, 4);
		tst3(2, 5);
		tst3(3, 6);
		tst3(4, 8);
		tst3(5, 10);
		tst3(10, 38);
		tst3(20, 656);
		tst3(30, 11628);
		tst3(40, 206472);
		tst3(50, 3666461);
		tst3(60, 65107996);
		tst3(70, 1156169984);
		printf("-------------------------------%n");
		tstn(2, 0, 2);
		tstn(2, 1, 3);
		tstn(2, 2, 4);
		tstn(2, 3, 6);
		tstn(2, 4, 9);
		tstn(2, 5, 13);
		tstn(2, 10, 94);
		tstn(2, 20, 5395);
		tstn(2, 30, 311073);
		tstn(2, 40, 17937985);
		tstn(2, 50, 1034394552);
		printf("-------------------------------%n");
		tstn(5, 0, 5);
		tstn(5, 1, 6);
		tstn(5, 2, 7);
		tstn(5, 3, 8);
		tstn(5, 4, 9);
		tstn(5, 5, 10);
		tstn(5, 6, 12);
		tstn(5, 10, 22);
		tstn(5, 20, 126);
		tstn(5, 30, 772);
		tstn(5, 40, 4770);
		tstn(5, 50, 29523);
		tstn(5, 60, 182787);
		tstn(5, 70, 1131754);
		tstn(5, 80, 7007508);
		tstn(5, 90, 43388630);
		tstn(5, 100, 268650952);
		tstn(5, 110, 1663415874);
		printf("-----------------------------------%n");
		printf("Anzahl Fehler: %d%n", anzFehler);
		printf("-----------------------------------%n");
		printf("Rekursion05_Tst: Das war's erstmal!%n%n");
	} // main
		// ---------------------------------------------------------------------
		// Eine Methode mit einem kurzen Namen:

	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
	// ---------------------------------------------------------------------
} // class Rekursion05_Tst
/**
 * ------------------------------------------------------------------------ Ausgabe:
 * 
 * Rekursion05_Tst: Jetzt geht es los! ----------------------------------- anzRobos( 0), ist: 3, soll: 3 <--- OK
 * anzRobos( 1), ist: 4, soll: 4 <--- OK anzRobos( 2), ist: 5, soll: 5 <--- OK anzRobos( 3), ist: 6, soll: 6 <--- OK
 * anzRobos( 4), ist: 8, soll: 8 <--- OK anzRobos( 5), ist: 10, soll: 10 <--- OK anzRobos(10), ist: 38, soll: 38 <--- OK
 * anzRobos(20), ist: 656, soll: 656 <--- OK anzRobos(30), ist: 11.628, soll: 11.628 <--- OK anzRobos(40), ist: 206.472,
 * soll: 206.472 <--- OK anzRobos(50), ist: 3.666.461, soll: 3.666.461 <--- OK anzRobos(60), ist: 65.107.996, soll:
 * 65.107.996 <--- OK anzRobos(70), ist:1.156.169.984, soll:1.156.169.984 <--- OK -------------------------------
 * anzRobos( 2, 0), ist: 2, soll: 2 <--- OK anzRobos( 2, 1), ist: 3, soll: 3 <--- OK anzRobos( 2, 2), ist: 4, soll: 4
 * <--- OK anzRobos( 2, 3), ist: 6, soll: 6 <--- OK anzRobos( 2, 4), ist: 9, soll: 9 <--- OK anzRobos( 2, 5), ist: 13,
 * soll: 13 <--- OK anzRobos( 2, 10), ist: 94, soll: 94 <--- OK anzRobos( 2, 20), ist: 5.395, soll: 5.395 <--- OK
 * anzRobos( 2, 30), ist: 311.073, soll: 311.073 <--- OK anzRobos( 2, 40), ist: 17.937.985, soll: 17.937.985 <--- OK
 * anzRobos( 2, 50), ist:1.034.394.552, soll:1.034.394.552 <--- OK ------------------------------- anzRobos( 5, 0), ist:
 * 5, soll: 5 <--- OK anzRobos( 5, 1), ist: 6, soll: 6 <--- OK anzRobos( 5, 2), ist: 7, soll: 7 <--- OK anzRobos( 5, 3),
 * ist: 8, soll: 8 <--- OK anzRobos( 5, 4), ist: 9, soll: 9 <--- OK anzRobos( 5, 5), ist: 10, soll: 10 <--- OK anzRobos(
 * 5, 6), ist: 12, soll: 12 <--- OK anzRobos( 5, 10), ist: 22, soll: 22 <--- OK anzRobos( 5, 20), ist: 126, soll: 126
 * <--- OK anzRobos( 5, 30), ist: 772, soll: 772 <--- OK anzRobos( 5, 40), ist: 4.770, soll: 4.770 <--- OK anzRobos( 5,
 * 50), ist: 29.523, soll: 29.523 <--- OK anzRobos( 5, 60), ist: 182.787, soll: 182.787 <--- OK anzRobos( 5, 70), ist:
 * 1.131.754, soll: 1.131.754 <--- OK anzRobos( 5, 80), ist: 7.007.508, soll: 7.007.508 <--- OK anzRobos( 5, 90), ist:
 * 43.388.630, soll: 43.388.630 <--- OK anzRobos( 5, 100), ist: 268.650.952, soll: 268.650.952 <--- OK anzRobos( 5,
 * 110), ist:1.663.415.874, soll:1.663.415.874 <--- OK ----------------------------------- Anzahl Fehler: 0
 * ----------------------------------- Rekursion05_Tst: Das war's erstmal!
 */
