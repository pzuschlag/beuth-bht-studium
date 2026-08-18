class LongSpeicher50 implements LongSpeicher {
	// ---------------------------------------------------------------------
	// Zum Ein-/Ausschalten von Testbefehlen:
	static final boolean TST1 = true;

	// ---------------------------------------------------------------------
	// Zwei (statische) geschachtelte Klassen (nested static class)

	static protected class Knoten {
		RefK lubr; // Referenz auf linken Unterbaum
		RefK rubr; // Referenz auf rechten Unterbaum
		long data;

		Knoten(RefK lubr, RefK rubr, long data) { // Konstruktor
			this.lubr = lubr;
			this.rubr = rubr;
			this.data = data;
		}
	}

	static protected class RefK {
		Knoten k;

		RefK(Knoten k) {
			this.k = k;
		} // Konstruktor
	}

	// ---------------------------------------------------------------------
	// Ein leerer Baum besteht aus einem End-Dummy-Knoten EDK.
	// Das RefK-Attribut AR (Anfangs-Referenz) zeigt anfangs auf den EDK
	// und spaeter auf den ersten "richtigen Knoten" (die Wurzel) des Baums:
	private final Knoten EDK = new Knoten(null, null, 0);
	private final RefK AR = new RefK(EDK);

	// ---------------------------------------------------------------------
	private RefK refk(long n) {
		// Liefert ein RefK-Objekt hier fuer welches gilt:
		// hier.k.data == n.
		// Falls n in diesem Speicher nicht vorkommt, ist
		// der Knoten hier.k gleich dem EDK.

		EDK.data = n;
		return refkR(n, AR);
	}

	private RefK refkR(long n, RefK hier) {
		// Eine rekursive Methode, die erledigt, was die Methode refk
		// versprochen hat.

		if (n < hier.k.data) {
			return refkR(n, hier.k.lubr); // links weitersuchen
		} else if (n > hier.k.data) {
			return refkR(n, hier.k.rubr); // rechts weitersuchen
		} else { // jetzt gilt n == hier.k.data
			return hier;
		}
	}

	// ---------------------------------------------------------------------
	public boolean fuegeEin(long n) {
		RefK hier = refk(n);
		if (hier.k != EDK)
			return false;

		hier.k = new Knoten(new RefK(EDK), new RefK(EDK), n);
		return true;
	}

	// ---------------------------------------------------------------------
	public boolean loesche(long n) {
		RefK hier = refk(n);
		if (hier.k == EDK)
			return false;

		if (hier.k.lubr.k == EDK)
			hier.k = hier.k.rubr.k;
		else if (hier.k.rubr.k == EDK)
			hier.k = hier.k.lubr.k;
		else {
			RefK gesucht = hier.k.lubr;
			while (gesucht.k.rubr.k != EDK)
				gesucht = gesucht.k.rubr;
			long gefunden = gesucht.k.data;
			this.loesche(gefunden);
			hier.k.data = gefunden;
		}

		return true;
	}

	// ---------------------------------------------------------------------
	public boolean istDrin(long n) {
		return refk(n).k != EDK;
	}

	// ---------------------------------------------------------------------
	// Zum Testen:
	private void print() {
		System.out.println(this);
	}

	public String toString() {
		return treeToString(AR);
	}

	private String treeToString(RefK Knoten) {
		if (Knoten.k == EDK)
			return "";
		return Knoten.k.data + "\n" + treeToString(Knoten.k.rubr) + treeToString(Knoten.k.lubr);
	}

	// ---------------------------------------------------------------------
	static public void main(String[] args) {
		printf("LongSpeicher10: Jetzt geht es los!%n");
		printf("----------------------------------%n");

		LongSpeicher50 lsa = new LongSpeicher50();

		lsa.print();
		lsa.fuegeEin(10);
		System.out.println("---------------------");
		lsa.print();
		lsa.fuegeEin(5);
		lsa.fuegeEin(15);
		lsa.fuegeEin(40);
		lsa.fuegeEin(1);
		System.out.println("---------------------");
		lsa.print();
		lsa.loesche(15);
		System.out.println("---------------------");
		lsa.print();

		printf("----------------------------------%n");
		printf("LongSpeicher10: Das war's erstmal!%n");
	} // main
		// ---------------------------------------------------------------------
		// Eine Methode mit einem kurzen Namen:

	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
	// ---------------------------------------------------------------------
} // class LongSpeicher10
