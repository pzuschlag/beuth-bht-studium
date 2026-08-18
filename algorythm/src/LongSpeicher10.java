import java.util.Arrays;

public class LongSpeicher10 implements LongSpeicher {

	// Zum Ein-/Ausschalten von Testbefehlen:
	static final boolean TST1 = true;
	// ---------------------------------------------------------------------
	private long[] speicher;
	private int nfi = 0; // naechster freier Index

	public LongSpeicher10(int groesse) {
		speicher = new long[groesse];
	}

	// ---------------------------------------------------------------------
	private int index(long n) {
		// Liefert den kleinsten Index i fuer den gilt: speicher[i] == n
		// oder -1, wenn n nicht im speicher ist.
		for (int index = 0; index < nfi; index++) {
			if (n == speicher[index])
				return index;
		}
		return -1;
	}

	// ---------------------------------------------------------------------
	public boolean fuegeEin(long n) {
		// fügt einen neuen long wert am nächsten freien Index nfi des Speichers
		// ein
		if (nfi >= speicher.length)
			return false;
		speicher[nfi++] = n;
		return true;
	}

	// ---------------------------------------------------------------------
	public boolean loesche(long n) {
		// Loescht ein Vorkommen von n in diesem Speicher, und liefert true.
		// Liefert false falls n nicht in diesem Speicher vorkommt.

		int i = this.index(n);
		if (i == -1)
			return false;
		speicher[i] = speicher[--nfi];
		return true;

	}

	// ---------------------------------------------------------------------
	public boolean istDrin(long n) {
		// Liefert true genau dann wenn n in diesem Speicher vorkommt.
		return (this.index(n) != -1);
	}

	// ---------------------------------------------------------------------
	// Zum Testen:
	private void print() {
		// Gibt diesen Speicher in lesbarer Form zur Standardausgabe aus:
		printf("nfi: %d, speicher: %s%n", nfi, Arrays.toString(speicher));
	}

	// ---------------------------------------------------------------------
	// static public void main(String[] args) {
	// printf("LongSpeicher10: Jetzt geht es los!%n");
	// printf("----------------------------------%n");
	//
	// LongSpeicher10 lsa = new LongSpeicher10(5);
	//
	// lsa.print();
	// lsa.fuegeEin(10);
	// lsa.print();
	// printf("10 ist Drin?", lsa.istDrin(10));
	// printf("20 ist Drin?", lsa.istDrin(20));
	// printf("20 löschen!", lsa.loesche(20));
	// printf("5 löschen!", lsa.loesche(5));
	//
	// printf("----------------------------------%n");
	// printf("LongSpeicher10: Das war's erstmal!%n");
	// } // main
	// ---------------------------------------------------------------------
	// Eine Methode mit einem kurzen Namen:

	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
	// ---------------------------------------------------------------------
} // class LongSpeicher10
