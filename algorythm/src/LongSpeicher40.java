public class LongSpeicher40 implements LongSpeicher {
	// SORTIERTE VERKETTETE LISTE
	// VORTEIL: schnelle Suche
	// Listen haben keine Feste Größe, können immer erweitert werden
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

	// TODO
	private Knoten vorgaenger(long n) {
		// Liefert den Vorgaenger eines Knotens, der n enthaelt, oder
		// den Vorgaenger des EDK (falls n in dieser Liste nicht vorkommt). ...
		EDK.data = n;
		Knoten hier = ADK;
		while (hier.next.data < n) {
			hier = hier.next;
		}
		return hier;
	}

	static public void main(String[] args) {
		System.out.println("LongSpeicher40: Jetzt geht es los!%n");
		System.out.println("----------------------------------%n");

		LongSpeicher40 lsa = new LongSpeicher40();
		lsa.fuegeEin(10);
		System.out.println(lsa);
		System.out.println(lsa.istDrin(10));
		System.out.println(lsa.istDrin(20));
		System.out.println(lsa.loesche(10));
		System.out.println(lsa.istDrin(10));

		System.out.println("----------------------------------%n");
		System.out.println("LongSpeicher40: Das war's erstmal!%n");
	}

	public boolean fuegeEin(long n) {
		// fügt einen neuen Knoten an der Stelle des Index n der Reihung ein
		Knoten vorgKnoten = vorgaenger(n);
		vorgKnoten.next = new Knoten(vorgKnoten.next, n);
		return true;
	}

	public boolean loesche(long n) {
		Knoten start = vorgaenger(n);
		if (start.next == EDK)
			return false;
		start.next = start.next.next;
		return true;
	}

	public boolean istDrin(long n) {
		// wenn die referenz des aktuellen Knoten n auf die selben Daten zeigt
		// wie der knoten n selbst, dann ist der gesuchte knoten mit dem Index n
		// vorhanden solange das nicht der fall ist wird durch die while
		// schleife durch die reihung iteriert.

		return vorgaenger(n).next != EDK;
	}
}
