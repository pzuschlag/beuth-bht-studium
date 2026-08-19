// Datei Alg_MergeSort01.java
/*
 * ------------------------------------------------------------------------ Dieses Programm Alg_MergeSort01 enthaelt
 * eine Methode mergeSort und testet sie ein bisschen. Die mit // MUSS ERSETZT WERDEN gekennzeichneten Zeilen muessen
 * durch geeignete Java-Befehle ersetzt werden. ------------------------------------------------------------------------
 */
import java.util.Arrays;

class Alg_MergeSort01 {
	// ---------------------------------------------------------------------
	// Variablen zum Ein- und Ausschalten von Test-Befehlen:
	final static boolean TST1 = true;
	final static boolean TST2 = true;
	final static boolean TST3 = true;
	// ---------------------------------------------------------------------
	static private String[] hr; // Hilfsreihung

	static public void mergeSort(String[] str) {
		// Sortiert die Reihung str aufsteigend

		// Reihungen der Laenge 0 oder 1 sind schon sortiert:
		if (str.length <= 1)
			return;

		hr = new String[str.length];
		mergeSortR(str, 0, str.length - 1, 0);
	}

	static private void mergeSortR(String[] str, int von, int bis, int t) {
		// Sortiert die Teilreihung str[von..bis]
		// t ist die Tiefe der Rekursion (dient zum Einruecken der
		// Trace-Ausgaben, siehe Methode print):

		// MUSS ERSETZT WERDEN
	}

	// ---------------------------------------------------------------------
	static private void merge(String[] str, int von, int mit, int bis) {
		// Verlaesst sich darauf, dass die Teilreihungen
		// t1 gleich str[von ..mit] und
		// t2 gleich str[mit+1..bis] sortiert sind
		// Fuehrt t1 und t2 sortiert zusammen nach str[von..bis].
		// Benutzt dabei die Hilfsreihung hr.

		// Teilreihung str[von..bis] nach hr kopieren
		System.arraycopy(str, von, hr, von, bis - von + 1);

		// Die Teilreihungen hr[von..mit] und hr[mit+1..bis]
		// sortiert zusammenfuehren nach str[von..bis]:

		// MUSS ERSETZT WERDEN
	}

	// ---------------------------------------------------------------------
	static boolean istSortiert(String[] str) {
		// Liefert true, wenn str aufsteigend sortiert ist, und sonst false.
		for (int i = 1; i < str.length; i++) {
			if (kleiner(str[i], str[i - 1]))
				return false;
		}
		return true;
	}

	// ---------------------------------------------------------------------
	static boolean kleiner(String s1, String s2) {
		// Liefert true, wenn s1 lexikografisch kleiner ist als s2
		// (d.h. wenn s1 in einem Lexikon weiter vorne stehen muesste als s2).
		// Liefert sonst false.

		return s1.compareTo(s2) < 0;
	}

	// ---------------------------------------------------------------------
	static void print(String[] str, int von, int bis, int t) {
		// Gibt die Teilreihung str[von..bis] lesbar und um
		// t Stufen eingerueckt zur Standardausgabe aus.

		// Die Einrueckung berechnen/erzeugen:
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < t; i++)
			sb.append("        ");
		String s = sb.toString(); // Einrueckung

		printf(s); // Einrueckung ausgeben
		printf("<%2d..%2d>", von, bis);
		printf("[");
		if (von <= bis)
			printf("%s", str[von]);
		for (int i = von + 1; i <= bis; i++) {
			printf(", %s", str[i]);
		}
		printf("]%n");
	}

	// ---------------------------------------------------------------------
	static void tst1MergeSort(String[] str) {
		// Wendet mergeSort auf str an und gibt eine Fehlermeldung aus,
		// falls str danach nicht aufsteigend sortiert ist:
		printf("Alg_MergeSort01: %s%n%n", Arrays.toString(str));
		mergeSort(str);

		if (!istSortiert(str)) {
			printf("######## Fehler: Die Reihung ist nicht sortiert! ###%n");
		}
		printf("-----------------------------------%n");
	}

	// ---------------------------------------------------------------------
	static public void main(String[] sonja) {
		printf("Alg_MergeSort01: Jetzt geht es los!%n");
		printf("-----------------------------------%n");

		// Ein paar Reihungen zum Testen von mergeSort:
		String[][] strr = { { "6", "5", "8", "7", "2", "1", "4", "3" },
		// {"H","O","C","H","S","C","H","U","L","E"},
		// {"J","I","H","G","F","E","D","C","B","A"},
		// {"A","L","G","O","R","I","T","H","M","U","S"},
		// {"F","E","N","S","T","E","R"},
		// {"A","B","A","B","A","B","A"},
		// {"B","B","B","B","A","A","A"},
		// {"D","E","G","F","B","A","C"},
		};

		// Teste mergeSort mit allen Komponenten von strr:
		if (TST1)
			for (String[] str : strr)
				tst1MergeSort(str);

		printf("Alg_MergeSort01: Das war's erstmal!%n%n");
	} // main
		// ---------------------------------------------------------------------
		// Eine Methode mit einem kurzen Namen:

	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
	// ---------------------------------------------------------------------
} // class Alg_MergeSort01
/*
 * ------------------------------------------------------------------------ Ausgabe (wie sie aussehen sollte, wenn alle
 * zu ersetzenden Zeilen richtig ersetzt wurden):
 * 
 * Alg_MergeSort01: Jetzt geht es los! ----------------------------------- Alg_MergeSort01:
 * 
 * < 0.. 7>[6, 5, 8, 7, 2, 1, 4, 3] < 0.. 3>[6, 5, 8, 7] < 0.. 1>[6, 5] < 0.. 0>[6]
 * 
 * < 1.. 1>[5] < 0.. 1>[5, 6]
 * 
 * < 2.. 3>[8, 7] < 2.. 2>[8]
 * 
 * < 3.. 3>[7] < 2.. 3>[7, 8] < 0.. 3>[5, 6, 7, 8]
 * 
 * < 4.. 7>[2, 1, 4, 3] < 4.. 5>[2, 1] < 4.. 4>[2]
 * 
 * < 5.. 5>[1] < 4.. 5>[1, 2]
 * 
 * < 6.. 7>[4, 3] < 6.. 6>[4]
 * 
 * < 7.. 7>[3] < 6.. 7>[3, 4] < 4.. 7>[1, 2, 3, 4] < 0.. 7>[1, 2, 3, 4, 5, 6, 7, 8] -----------------------------------
 * Alg_MergeSort01: Das war's erstmal! ------------------------------------------------------------------------
 */