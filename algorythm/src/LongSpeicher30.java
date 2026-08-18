public class LongSpeicher30 implements LongSpeicher {

	// Zum Ein-/Ausschalten von Testbefehlen:
	static final boolean TST1 = true;

	// ---------------------------------------------------------------------
	// Eine (statische) geschachtelte Klasse (nested static class).
	// Jedes Objekt dieser Klasse kann als Knoten einer einfach
	// verketteten Liste verwendet werden:

	static protected class Knoten {
		Knoten next;
		long data;

		Knoten(Knoten next, long data) {
			this.next = next;
			this.data = data;// Konstruktor
		}
	}

	// Eine leere Liste besteht aus 2 Dummy-Knoten:
	// einem End-Dummy-Knoten EDK und einem Anfangs-Dummy-Knoten ADK. Die
	// "richtigen Knoten" werden spaeter zwischen die 2 Dummies gehaengt.
	final Knoten EDK = new Knoten(null, 0); // End-Dummy-Knoten
	final Knoten ADK = new Knoten(EDK, 0); // Anfangs-Dummy-Knoten

	private Knoten vorgaenger(long n) {
		// Liefert den Vorgaenger eines Knotens, der n enthaelt, oder
		// den Vorgaenger des EDK (falls n in dieser Liste nicht vorkommt). ...
		EDK.data = n;
		Knoten hier = ADK;
		while (hier.next.data != n) {
			hier = hier.next;
		}
		return hier;
	}

	/*
	 * static public void main(String[] args) {
	 * System.out.println("LongSpeicher30: Jetzt geht es los!%n");
	 * System.out.println("----------------------------------%n");
	 * 
	 * LongSpeicher30 lsa = new LongSpeicher30();
	 * 
	 * lsa.fuegeEin(10); System.out.println(lsa);
	 * System.out.println(lsa.istDrin(10)); System.out.println(lsa.istDrin(20));
	 * System.out.println(lsa.loesche(10)); System.out.println(lsa.istDrin(10));
	 * 
	 * System.out.println("----------------------------------%n");
	 * System.out.println("LongSpeicher30: Das war's erstmal!%n"); }
	 */

	public boolean fuegeEin(long n) {
		// Es wird stets ein neuer Knoten hinter dem AnfangsDummyKnoten
		// vereinbart
		Knoten neu = new Knoten(ADK.next, n);
		ADK.next = neu;
		return true;
	}

	public boolean loesche(long n) {
		// wenn der knoten n der Enddummyknoten ist, kann man ihn nicht löschen
		// und somit
		// returnt die Methode false.
		// wenn die referenz des knoten auf den nächsten knoten zeigt, war das
		// löschen erfolgreich
		Knoten vor = vorgaenger(n);
		if (vor.next == EDK)
			return false;
		vor.next = vor.next.next;
		return true;
	}

	public boolean istDrin(long n) {
		// gibt true zurück wenn der knoten n NICHT der EndDummyKnoten ist
		return vorgaenger(n).next != EDK;
	}
}
