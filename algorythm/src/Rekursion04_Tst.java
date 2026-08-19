/*
 * ------------------------------------------------------------------------ Dieses Programm Rekursion04_Tst enthaelt die
 * 3 rekursiven Methoden add, mul und pot und testet sie.
 * 
 * Alle drei Methoden verlassen sich darauf, dass ihre Paramter m un n nicht negativ sind. Sie benutzen die Operationen
 * + und - (Addition und Subtraktion) nur dazu, um 1 zu addieren bzw. zu subtrahieren. Die Methoden koennen als
 * Definitionen der Operationen Addition, Multiplikation und Potenzierung (von natürlichen Zahlen) verstanden werden.
 * ------------------------------------------------------------------------
 */

class Rekursion04_Tst {
	// ---------------------------------------------------------------------
	static int add(int m, int n) {
		// Liefert die Summe von m und n:
		if (n == 0) {
			return m; // m + 0 = m
		} else {
			return (add(m, n - 1)) + 1; // m + n = (m + (n-1)) + 1
		}
	}

	// ---------------------------------------------------------------------
	static int mul(int m, int n) {
		// Liefert das Produkt von m und n:

		return -1; // MUSS ERSETZT WERDEN
	}

	// ---------------------------------------------------------------------
	static int pot(int m, int n) {
		// Liefert die n-te Potenz von m (d.h. den Wert m hoch n):

		return -1; // MUSS ERSETZT WERDEN
	}

	// ---------------------------------------------------------------------
	static private int anzFehler = 0;

	// ---------------------------------------------------------------------
	static private void tst_add(int m, int n, int soll) {
		int ist = add(m, n);

		String text = " <--- OK";
		if (ist != soll) {
			text = " <--- Fehler?";
			anzFehler++;
		}

		printf("add(%d, %d), ist:%4d, soll:%4d %s%n", m, n, ist, soll, text);
	}

	// ---------------------------------------------------------------------
	static private void tst_mul(int m, int n, int soll) {
		int ist = mul(m, n);

		String text = " <--- OK";
		if (ist != soll) {
			text = " <--- Fehler?";
			anzFehler++;
		}

		printf("add(%d, %d), ist:%4d, soll:%4d %s%n", m, n, ist, soll, text);
	}

	// ---------------------------------------------------------------------
	static private void tst_pot(int m, int n, int soll) {
		int ist = pot(m, n);

		String text = " <--- OK";
		if (ist != soll) {
			text = " <--- Fehler?";
			anzFehler++;
		}

		printf("add(%d, %d), ist:%4d, soll:%4d %s%n", m, n, ist, soll, text);
	}

	// ---------------------------------------------------------------------
	static public void main(String[] args) {
		printf("Rekursion04_Tst: Jetzt geht es los!%n");
		printf("-------------------------------%n");
		tst_add(0, 3, 3);
		tst_add(5, 0, 5);
		tst_add(1, 3, 4);
		tst_add(3, 1, 4);
		tst_add(3, 5, 8);
		tst_add(5, 3, 8);
		printf("-------------------------------%n");
		tst_mul(0, 3, 0);
		tst_mul(5, 0, 0);
		tst_mul(1, 3, 3);
		tst_mul(3, 1, 3);
		tst_mul(3, 5, 15);
		tst_mul(5, 3, 15);
		printf("-------------------------------%n");
		tst_pot(0, 3, 0);
		tst_pot(5, 0, 1);
		tst_pot(1, 3, 1);
		tst_pot(3, 1, 3);
		tst_pot(3, 2, 9);
		tst_pot(5, 3, 125);
		tst_pot(4, 5, 1024);
		printf("--------------------------------%n");
		printf("Anzahl Fehler: %d%n", anzFehler);
		printf("--------------------------------%n");
		printf("Rekursion04_Tst: Das war's erstmal!%n%n");
	} // main
		// ---------------------------------------------------------------------
		// Eine Methode mit einem kurzen Namen:

	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
	// ---------------------------------------------------------------------
} // class Rekursion04_Tst
/*
 * ------------------------------------------------------------------------ Ausgabe (Wenn alles OK ist):
 * 
 * Rekursion04_Tst: Jetzt geht es los! ------------------------------- add(0, 3), ist: 3, soll: 3 <--- OK add(5, 0),
 * ist: 5, soll: 5 <--- OK add(1, 3), ist: 4, soll: 4 <--- OK add(3, 1), ist: 4, soll: 4 <--- OK add(3, 5), ist: 8,
 * soll: 8 <--- OK add(5, 3), ist: 8, soll: 8 <--- OK ------------------------------- add(0, 3), ist: 0, soll: 0 <--- OK
 * add(5, 0), ist: 0, soll: 0 <--- OK add(1, 3), ist: 3, soll: 3 <--- OK add(3, 1), ist: 3, soll: 3 <--- OK add(3, 5),
 * ist: 15, soll: 15 <--- OK add(5, 3), ist: 15, soll: 15 <--- OK ------------------------------- add(0, 3), ist: 0,
 * soll: 0 <--- OK add(5, 0), ist: 1, soll: 1 <--- OK add(1, 3), ist: 1, soll: 1 <--- OK add(3, 1), ist: 3, soll: 3 <---
 * OK add(3, 2), ist: 9, soll: 9 <--- OK add(5, 3), ist: 125, soll: 125 <--- OK add(4, 5), ist:1024, soll:1024 <--- OK
 * -------------------------------- Anzahl Fehler: 0 -------------------------------- Rekursion04_Tst: Das war's
 * erstmal! ------------------------------------------------------------------------
 */
