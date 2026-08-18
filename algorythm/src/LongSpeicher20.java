import java.util.Arrays;

public class LongSpeicher20 implements LongSpeicher {
	// SORTIERTE REIHUNG
	// VORTEIL: schnelle Suche 
	// 				einer Suche von einer Million maximal 20 Schritte
	// 				"-" Milliarde maximal 30 Schritte
	// 				"-" Billion maximal 40 Schritte
	//NACHTEIL: Löschen dauert länger als bei unsortierten denn die Einträge müssen "nachrrücken"
	//			Reihungen werden mit einer festen Größe inizialisiert (vereinbart) 
	
	
	// Zum Ein-/Ausschalten von Testbefehlen:
	static final boolean TST1 = true;
	// ---------------------------------------------------------------------
	private long[] speicher;
	private int nfi = 0; // naechster freier Index

	public LongSpeicher20(int groesse) {
		speicher = new long[groesse];
	}

	// ---------------------------------------------------------------------
	private int index(long n) {
		// Liefert den Index i, an dem n steht oder eingefuegt werden sollte.
		// Binaer gesucht wird jeweils
		// in der Teilreihung speicher[von..bis]:
		// BINÄRE SUCHE:

		int von = 0;
		int bis = nfi - 1;

		while (von <= bis) {
			int mitte = von + (bis - von) / 2;

			if (n > speicher[mitte]) {
				von = mitte + 1;

				// rechts weitersuchen
			} else if (n < speicher[mitte]) {
				bis = mitte - 1;
				// links weitersuchen
			} else {
				return mitte; // n == speicher[mitte]
			}
		}
		return von; // n steht nicht im speicher
	}

	// ---------------------------------------------------------------------
	public boolean fuegeEin(long n) {
		// fügt einen neuen long wert am nächsten freien Index nfi des Speichers
		// ein
		if (nfi == speicher.length)
			return false;

		int index2 = index(n);

		// doppelte Einträge sollen vermieden werden
		if (index2 < nfi && speicher[index2] == n)
			return false;

		// wenn nfi bzw index(n) außerhalb der größe des speichers ist, nicht
		// einfügen!
		if (nfi > speicher.length || index(n) > speicher.length)
			return false;

		for (int i = nfi - 1; i >= index2; i--) {
			speicher[i + 1] = speicher[i];
		}

		speicher[index2] = n;
		nfi++;
		return true;
	}

	// ---------------------------------------------------------------------
	public boolean loesche(long n) {
		// Loescht ein Vorkommen von n in diesem Speicher, und liefert true.
		// Liefert false falls n nicht in diesem Speicher vorkommt.

		int index2 = index(n);
		if (index2 >= nfi || speicher[index2] != n)
			return false;

		for (int i = index2; i <= nfi - 1; i++) {
			if (i < nfi - 1) {
				speicher[i] = speicher[i + 1];
			}
		}

		nfi--;
		return true;

	}

	// ---------------------------------------------------------------------
	public boolean istDrin(long n) {
		// Liefert true genau dann wenn n in diesem Speicher vorkommt.
		int index2 = index(n);
		return index2 < nfi && speicher[index2] == n;
	}

	// ---------------------------------------------------------------------
	// Zum Testen:
	private void print() {
		// Gibt diesen Speicher in lesbarer Form zur Standardausgabe aus:
		printf("nfi: %d, speicher: %s%n", nfi, Arrays.toString(speicher));
	}

	// // ---------------------------------------------------------------------
	// static public void main(String[] args) {
	// printf("LongSpeicher20: Jetzt geht es los!%n");
	// printf("----------------------------------%n");
	//
	// LongSpeicher20 lsa = new LongSpeicher20(5);
	//
	// lsa.print();
	// lsa.fuegeEin(10);
	// lsa.print();
	// lsa.istDrin(10);
	// lsa.istDrin(20);
	//
	// printf("----------------------------------%n");
	// printf("LongSpeicher20: Das war's erstmal!%n");
	// } // main
	// // ---------------------------------------------------------------------
	// // Eine Methode mit einem kurzen Namen:

	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
	// ---------------------------------------------------------------------
}
