/***************************************************************************
 * Diese Datei enthaelt 7 Java-Programme namens Rekursion01_Tst bis Rekursion07_Tst, die von Anfang an compilierbar und
 * lauffaehig sein sollten. Die Programme sind durch Sternchen-Zeilen voneinander getrennt. Jedes Programm dient dazu,
 * eine oder mehrere rekursive Methoden zu testen (insgesamt sind das 14 Methoden). Die Ruempfe dieser Methoden sollen
 * von Ihnen ergaenzt werden. Sie bestehen anfangs jeweils nur aus einer Zeile, die mit MUSS ERSETZT WERDEN
 * gekennzeichnet ist.
 ***************************************************************************/
// Datei Rekursion01_Tst.java
/*
 * ------------------------------------------------------------------------ Dieses Programm Rekursion01_Tst enthaelt die
 * 3 rekursiven Methoden fiboR (Fibonacci-Zahlen), anzWeisen12 (Treppen steigen mit 1 oder 2 Stufen auf einemal) und
 * anzWeisen13 (Treppen steigen mit 1, 2 oder 3 Stufen auf einmal) und testet sie.
 * ------------------------------------------------------------------------
 */
class Rekursion01_Tst {
	// ---------------------------------------------------------------------
	static public long fiboR(final int N) { // Fibonacci-Zahlen, rekursiv
		// Liefert das N-te Element der Fibonacci-Folge
		// 0, 1, 1, 2, 3, 5, 8, 13, ...

		if (N == 0)
			return 0;

		if (N == 1)
			return 1;

		return fiboR(N - 1) + fiboR(N - 2);

		// Beispiele:
		// fiboR(0) ist gleich 0
		// fiboR(1) ist gleich 1
		// fiboR(2) ist gleich 1
		// fiboR(3) ist gleich 2
		// fiboR(4) ist gleich 3
		// fiboR(5) ist gleich 5
		// fiboR(6) ist gleich 8
		// fiboR(7) ist gleich 13

	} // fiboR

	// ---------------------------------------------------------------------

	static public long anzWeisen12(final int S) {
		// Angenommen, Sie koennen beim Treppensteigen bei jedem Schritt
		// wahlweise 1 Stufe oder 2 Stufen hochgehen. Auf wie viele
		// verschiedene Weisen koennen Sie dann eine Treppe mit S Stufen
		// hinaufgehen? Diese Methode liefert die Antwort.

		if (S == 1)
			return 1;

		if (S == 2)
			return 2;

		return anzWeisen12(S - 1) + anzWeisen12(S - 2);

		// Beispiel:
		// Eine Treppe mit S gleich 4 Stufen kann man (bei dieser Aufgabe)
		// auf 5 verschiedene Weisen hinaufgehen:
		// 1 1 1 1
		// 1 1 2
		// 1 2 1
		// 2 1 1
		// 2 2
		// Somit ist anzWeisen12(4) gleich 5
	}

	// ---------------------------------------------------------------------
	static public long anzWeisen13(final int S) {
		// Angenommen, Sie koennen beim Treppensteigen bei jedem Schritt
		// wahlweise 1, 2 oder 3 Stufen hochgehen. Auf wie viele
		// verschiedene Weisen koennen Sie dann eine Treppe mit S Stufen
		// hinaufgehen? Diese Methode liefert die Antwort.

		if (S == 1)
			return 1;

		if (S == 2)
			return 2;

		if (S == 3)
			return 4;

		return anzWeisen13(S - 1) + anzWeisen13(S - 2) + anzWeisen13(S - 3);

		// Beispiel:
		// Eine Treppe mit S gleich 4 Stufen kann man (bei dieser Aufgabe)
		// auf 7 verschiedene Weisen hinaufgehen:
		// 1 1 1 1
		// 1 1 2
		// 1 2 1
		// 2 1 1
		// 2 2
		// 1 3
		// 3 1
		// Somit ist anzWeisen13(4) gleich 7
	}

	// ---------------------------------------------------------------------
	static int anzFehler = 0;

	static final void tst_fiboR(final int N, final int SOLL) {
		// Fuehrt einen Test der Funktion fiboR durch:
		final long IST = fiboR(N);

		String text = " <--- OK";
		if (IST != SOLL) {
			text = " <--- Fehler?";
			anzFehler++;
		}

		printf("fiboR      (%2d), IST: %,13d, SOLL: %,13d %s%n", N, IST, SOLL, text);
	}

	// ---------------------------------------------------------------------
	static final void tst_anzWeisen12(final int N, final int SOLL) {
		// Fuehrt einen Test der Funktion anzWeisen12 durch:
		final long IST = anzWeisen12(N);

		String text = " <--- OK";
		if (IST != SOLL) {
			text = " <--- Fehler?";
			anzFehler++;
		}

		printf("anzWeisen12(%2d), IST: %,13d, SOLL: %,13d %s%n", N, IST, SOLL, text);
	}

	// ---------------------------------------------------------------------
	static final void tst_anzWeisen13(final int N, final int SOLL) {
		// Fuehrt einen Test der Funktion anzWeisen12 durch:
		final long IST = anzWeisen13(N);

		String text = " <--- OK";
		if (IST != SOLL) {
			text = " <--- Fehler?";
			anzFehler++;
		}

		printf("anzWeisen13(%2d), IST: %,13d, SOLL: %,13d %s%n", N, IST, SOLL, text);
	}

	// ---------------------------------------------------------------------
	static public void main(String[] args) {
		printf("Rekursion01_Tst: Jetzt geht es los!%n");
		printf("--------------------------------------------------------%n");
		tst_fiboR(0, 0);
		tst_fiboR(1, 1);
		tst_fiboR(2, 1);
		tst_fiboR(3, 2);
		tst_fiboR(4, 3);
		tst_fiboR(5, 5);
		tst_fiboR(6, 8);
		tst_fiboR(7, 13);
		tst_fiboR(8, 21);
		tst_fiboR(10, 55);
		tst_fiboR(20, 6_765);
		tst_fiboR(30, 832_040);
		tst_fiboR(40, 102_334_155);
		tst_fiboR(45, 1_134_903_170);
		printf("--------------------------------------------------------%n");
		tst_anzWeisen12(1, 1);
		tst_anzWeisen12(2, 2);
		tst_anzWeisen12(3, 3);
		tst_anzWeisen12(4, 5);
		tst_anzWeisen12(5, 8);
		tst_anzWeisen12(6, 13);
		tst_anzWeisen12(7, 21);
		tst_anzWeisen12(8, 34);
		tst_anzWeisen12(9, 55);
		tst_anzWeisen12(19, 6_765);
		tst_anzWeisen12(29, 832_040);
		tst_anzWeisen12(39, 102_334_155);
		tst_anzWeisen12(44, 1_134_903_170);
		printf("--------------------------------------------------------%n");
		tst_anzWeisen13(1, 1);
		tst_anzWeisen13(2, 2);
		tst_anzWeisen13(3, 4);
		tst_anzWeisen13(4, 7);
		tst_anzWeisen13(5, 13);
		tst_anzWeisen13(6, 24);
		tst_anzWeisen13(7, 44);
		tst_anzWeisen13(8, 81);
		tst_anzWeisen13(9, 149);
		tst_anzWeisen13(20, 121_415);
		tst_anzWeisen13(30, 53_798_080);
		printf("--------------------------------------------------------%n");
		printf("Anzahl Fehler: %s%n", anzFehler);
		printf("--------------------------------------------------------%n");
		printf("Rekursion01_Tst: Das war's erstmal!%n%n");
	} // main
		// ---------------------------------------------------------------------
		// Eine Methode mit einem kurzen Namen:

	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
	// ---------------------------------------------------------------------
} // class Rekursion01_Tst

/*
 * ------------------------------------------------------------------------ Ausgabe (wenn alles OK ist):
 * 
 * Rekursion01_Tst: Jetzt geht es los! -------------------------------------------------------- fiboR ( 0), IST: 0,
 * SOLL: 0 <--- OK fiboR ( 1), IST: 1, SOLL: 1 <--- OK fiboR ( 2), IST: 1, SOLL: 1 <--- OK fiboR ( 3), IST: 2, SOLL: 2
 * <--- OK fiboR ( 4), IST: 3, SOLL: 3 <--- OK fiboR ( 5), IST: 5, SOLL: 5 <--- OK fiboR ( 6), IST: 8, SOLL: 8 <--- OK
 * fiboR ( 7), IST: 13, SOLL: 13 <--- OK fiboR ( 8), IST: 21, SOLL: 21 <--- OK fiboR (10), IST: 55, SOLL: 55 <--- OK
 * fiboR (20), IST: 6.765, SOLL: 6.765 <--- OK fiboR (30), IST: 832.040, SOLL: 832.040 <--- OK fiboR (40), IST:
 * 102.334.155, SOLL: 102.334.155 <--- OK fiboR (45), IST: 1.134.903.170, SOLL: 1.134.903.170 <--- OK
 * -------------------------------------------------------- anzWeisen12( 1), IST: 1, SOLL: 1 <--- OK anzWeisen12( 2),
 * IST: 2, SOLL: 2 <--- OK anzWeisen12( 3), IST: 3, SOLL: 3 <--- OK anzWeisen12( 4), IST: 5, SOLL: 5 <--- OK
 * anzWeisen12( 5), IST: 8, SOLL: 8 <--- OK anzWeisen12( 6), IST: 13, SOLL: 13 <--- OK anzWeisen12( 7), IST: 21, SOLL:
 * 21 <--- OK anzWeisen12( 8), IST: 34, SOLL: 34 <--- OK anzWeisen12( 9), IST: 55, SOLL: 55 <--- OK anzWeisen12(19),
 * IST: 6.765, SOLL: 6.765 <--- OK anzWeisen12(29), IST: 832.040, SOLL: 832.040 <--- OK anzWeisen12(39), IST:
 * 102.334.155, SOLL: 102.334.155 <--- OK anzWeisen12(44), IST: 1.134.903.170, SOLL: 1.134.903.170 <--- OK
 * -------------------------------------------------------- anzWeisen13( 1), IST: 1, SOLL: 1 <--- OK anzWeisen13( 2),
 * IST: 2, SOLL: 2 <--- OK anzWeisen13( 3), IST: 4, SOLL: 4 <--- OK anzWeisen13( 4), IST: 7, SOLL: 7 <--- OK
 * anzWeisen13( 5), IST: 13, SOLL: 13 <--- OK anzWeisen13( 6), IST: 24, SOLL: 24 <--- OK anzWeisen13( 7), IST: 44, SOLL:
 * 44 <--- OK anzWeisen13( 8), IST: 81, SOLL: 81 <--- OK anzWeisen13( 9), IST: 149, SOLL: 149 <--- OK anzWeisen13(20),
 * IST: 121.415, SOLL: 121.415 <--- OK anzWeisen13(30), IST: 53.798.080, SOLL: 53.798.080 <--- OK
 * -------------------------------------------------------- Anzahl Fehler: 0
 * -------------------------------------------------------- Rekursion01_Tst: Das war's erstmal!
 * ------------------------------------------------------------------------
 */