public interface LongSpeicher {

	abstract public boolean fuegeEin(long n);

	// Fuegt n in diesen Speicher ein und liefert true.
	// Liefert false, wenn n nicht eingefuegt werden konnte.

	abstract public boolean loesche(long n);

	// Loescht ein Vorkommen von n in diesem Speicher, und liefert true.
	// Liefert false falls n nicht in diesem Speicher vorkommt.

	abstract public boolean istDrin(long n);
	// Liefert true genau dann wenn n in diesem Speicher vorkommt.
}
