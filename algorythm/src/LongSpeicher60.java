// Datei LongSpeicher60.java
/*
 * ------------------------------------------------------------------------ Jedes Objekt der Klasse LongSpeicher60 ist
 * ein Speicher, in dem man long-Werte sammeln (einfuegen, entfernen, suchen) kann. "Doppelgaenger" sind erlaubt.
 * --------------------------------------------------------------------------- Implementierung: Als Hash-Tabelle
 * ------------------------------------------------------------------------
 */
// Zum Testen

class LongSpeicher60 implements LongSpeicher {
	// ---------------------------------------------------------------------
	// Zum Ein-/Ausschalten von Testbefehlen:
	static final boolean TST1 = false;
	// ---------------------------------------------------------------------
	final LongSpeicher30[] TAB; // Die Hash-Tabelle

	public LongSpeicher60(int len) {
		if (len < 2) {
			printf("TAB.length ist gleich %d und damit zu klein.%n", len);
			printf("Statt dessen wird 2 genommen!%n%n");
			len = 2;
		}
		TAB = new LongSpeicher30[len];
		for (int i = 0; i < TAB.length; i++)
			TAB[i] = new LongSpeicher30();
	}

	// ---------------------------------------------------------------------
	private LongSpeicher30 zustaendigeListe(long n) {
		// Liefert die Liste TAB[i], die für den Schluessel n zustaendig ist:
		return TAB[(int) Math.abs(n % TAB.length)];
	}

	// ---------------------------------------------------------------------
	public boolean fuegeEin(long n) {
		// Fuegt n in diesen Speicher ein und liefert true.
		return zustaendigeListe(n).fuegeEin(n);
	}

	// ---------------------------------------------------------------------
	public boolean loesche(long n) {
		// Loescht einen Eintrag, der die Zahl n enthaelt, und liefert true.
		// Liefert false falls n nicht in diesem Speicher vorkommt.
		return zustaendigeListe(n).loesche(n);
	}

	// ---------------------------------------------------------------------
	public boolean istDrin(long n) {
		// Liefert true genau dann wenn n in diesem Speicher vorkommt.
		return zustaendigeListe(n).istDrin(n);
	}

	// ---------------------------------------------------------------------
	// Zum Testen:
	private void print() {
		// Gibt diesen Speicher in lesbarer Form zur Standardausgabe aus.

		StringBuilder sb = new StringBuilder("[" + TAB[0].toString());
		for (int i = 1; i < TAB.length; i++) {
			String s = TAB[i].toString();
			if (s.equals(""))
				continue;
			sb.append(", " + s);
		}
		sb.append("]");
		printf("%s%n", sb);
	}

	// ---------------------------------------------------------------------

	public static void main(String[] args) {

		printf("LongSpeicher60: Jetzt geht es los!%n");
		printf("----------------------------------%n");

		LongSpeicher60 lsa = new LongSpeicher60(10);
		lsa.fuegeEin(1);
		lsa.fuegeEin(2);
		lsa.fuegeEin(23);
		lsa.fuegeEin(23);
		lsa.fuegeEin(88);
		lsa.fuegeEin(1);
		lsa.fuegeEin(15);
		lsa.fuegeEin(25);
		lsa.fuegeEin(30);
		lsa.fuegeEin(99);

		lsa.print();
		printf("----------------------------------%n");
		printf("LongSpeicher60: Das war's erstmal!%n");

	}

	// Eine Methode mit einem kurzen Namen:
	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
} // class LongSpeicher60