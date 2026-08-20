// Datei MaxTeilsumme_Jut.java
/*
 * ------------------------------------------------------------------------ Mit diesem JUnit-Testprogramm kann man
 * Methoden testen, die die maximale Teilsumme einer Reihung (of an array) von ganzen Zahlen berechnen. Die Methoden
 * muessen 1. Klassenmethoden (static methods) sein 2. den Ergebnistyp (return type) int haben 3. genau einen Parameter
 * vom Typ int[] haben
 * 
 * Achtung: Dieses Testprogramm laeuft nur mit der Version JUnit 3.8, die als eine Datei namens junit.jar vertrieben
 * wird. Fuer neuere Versionen von JUnit wie 4.0 oder 4.2 etc. (vertrieben in Dateien namens junit-4.0.jar oder
 * junit-4.2.jar etc) gelten etwas andere Regeln.
 * 
 * Auf die zu testende Methode wird per Reflektion zugegriffen. Dadurch koennen bei gewissen Fehlern informativere
 * Fehlermeldungen ausgegeben werden. ------------------------------------------------------------------------
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
// Eine Schnittstelle
// Eine Test-Klasse
// Eine Test-Klasse

import junit.framework.TestCase;

public class MaxTeilsumme_Jut extends TestCase {
	// ---------------------------------------------------------------------
	// Der volle Name der umgebenden Klasse und
	// der Name der zu testenden Methode:

	final static String KLASSEN_NAME = "MaxTeilsumme01"; // Zeile 31
	final static String METHODEN_NAME = "mts04"; // Zeile 32

	// ---------------------------------------------------------------------
	public MaxTeilsumme_Jut() {
		// Prueft folgende Bedingungen:
		// 1. Existiert eine Klasse namens KLASSEN_NAME?
		// 2. Gibt es darin eine Methode namens METHODEN_NAME?
		// 3. Ist diese Methode eine Klassenmethode (static method)
		// mit Ergebnistyp int und einem Parameter vom Typ int[]?
		// Beendet dieses Programm, falls eine Bedingung nicht erfuellt ist.
		// Initialisiert sonst die Variablen kob und met.

		try {
			// kob soll auf das Class-Objekt der zu testenden Klasse zeigen:
			kob = Class.forName(KLASSEN_NAME);

			// Enthaelt die Klasse kob eine Methode namens METHODEN_NAME
			// mit einem Parameter vom Typ int[]?
			met = kob.getDeclaredMethod(METHODEN_NAME, int[].class);

			// Hat die Methode met den Rueckgabetyp int?
			Class<?> ist_rt = met.getReturnType();
			Class<?> soll_rt = Integer.TYPE;
			if (ist_rt != soll_rt) {
				printf("Die Methode %s.%s%n", KLASSEN_NAME, METHODEN_NAME);
				printf("hat den Ergebnistyp %s.%n", ist_rt);
				printf("Sie sollte den Ergebnistyp %s haben,%n", soll_rt);
				printf("-------------------------------------------------%n");
				System.exit(3);
			}

			// Ist met eine Klassenmethode (a static method)?
			int mods = met.getModifiers();
			if ((mods & Modifier.STATIC) == 0) {
				printf("Die Methode %s.%s%n", KLASSEN_NAME, METHODEN_NAME);
				printf("scheint nicht static zu sein.%n");
				printf("-------------------------------------------------%n");
				System.exit(4);
			}
		} catch (ClassNotFoundException ex) {
			printf("Eine Klasse namens %s%n", KLASSEN_NAME);
			printf("konnte nicht gefunden werden!%n");
			printf("-------------------------------------------------%n");
			System.exit(1);
		} catch (NoSuchMethodException ex) {
			printf("In der Klasse %s%n", KLASSEN_NAME);
			printf("scheint es keine Methode namens %s%n", METHODEN_NAME);
			printf("mit einem int[]-Parameter zu geben.%n");
			printf("-------------------------------------------------%n");
			System.exit(2);
		} catch (Exception ex) {
			printf("Ausnahme im Konstruktor MaxTeilsumme_Jut:%n");
			printf("%s%n", ex.toString());
			printf("-------------------------------------------------%n");
			System.exit(5);
		}
	} // Konstruktor MaxTeisumme_Jut
		// ---------------------------------------------------------------------

	Class<?> kob; // Die zu testende Klasse
	Method met; // Die zu testende Methode

	public void setUp() {
		// Wird hier nicht benoetigt
	}

	// ---------------------------------------------------------------------
	// Ruft die zu testende Methode met auf und faengt Ausnahmen ab:
	private int rufAuf(int[] ir) {
		try {
			int erg = (Integer) met.invoke(null, ir);
			return erg;
		} catch (IllegalAccessException ex) {
			printf("Ausnahme in Methde rufAuf:%n");
			printf("%s%n", ex);
			return 0;
		} catch (InvocationTargetException ex) {
			printf("Ausnahme in Methde rufAuf:%n");
			printf("%s%n", ex);
			return 0;
		}
	}

	// ---------------------------------------------------------------------
	// Die Testfaelle:
	// ---------------------------------------------------------------------
	public void test_01() {
		assertEquals(5, rufAuf(new int[] { +5, -3, +2 }));
	}

	// ---------------------------------------------------------------------
	public void test_02() {
		assertEquals(5, rufAuf(new int[] { +1, +1, +1, +1, +1 }));
	}

	// ---------------------------------------------------------------------
	public void test_03() {
		assertEquals(3, rufAuf(new int[] { -1, +1, +1, +1, -1 }));
	}

	// ---------------------------------------------------------------------
	public void test_04() {
		assertEquals(2, rufAuf(new int[] { -1, -1, +1, +1, -1 }));
	}

	// ---------------------------------------------------------------------
	public void test_05() {
		assertEquals(1, rufAuf(new int[] { -1, -1, -1, +1, -1 }));
	}

	// ---------------------------------------------------------------------
	public void test_06() {
		assertEquals(1, rufAuf(new int[] { +1, -1, +1, -1, +1, -1, +1 }));
	}

	// ---------------------------------------------------------------------
	public void test_07() {
		assertEquals(6, rufAuf(new int[] { +5, -3, +4 }));
	}

	// ---------------------------------------------------------------------
	public void test_08() {
		assertEquals(6, rufAuf(new int[] { +5, -1, -1, -1, +4 }));
	}

	// ---------------------------------------------------------------------
	public void test_09() {
		assertEquals(1, rufAuf(new int[] { +1, -1, +1, -1, +1, -1, +1 }));
	}

	// ---------------------------------------------------------------------
	public void test_10() {
		assertEquals(7, rufAuf(new int[] { +3, -2, +1, -3, +2, -1, +4, -3, +1, -2, +1, -3, +3, -1, +4, -2, +1, -2, +4, -3 }));
	}

	// ---------------------------------------------------------------------
	public void test_11() {
		assertEquals(0, rufAuf(new int[] { -1, -2, -3 }));
	}

	// ---------------------------------------------------------------------
	public void test_12() {
		assertEquals(0, rufAuf(new int[] { -123 }));
	}

	// ---------------------------------------------------------------------
	public void test_13() {
		assertEquals(0, rufAuf(new int[] {}));
	}

	// ---------------------------------------------------------------------
	public void test_14() {
		assertEquals(5, rufAuf(new int[] { 5 }));
	}

	// ---------------------------------------------------------------------
	static public void main(String[] args) {
		printf("MaxTeilsumme_Jut: Jetzt geht es los!%n");
		printf("------------------------------------%n");
		printf("Getestet wird die Klasse: %s%n", KLASSEN_NAME);
		printf("-------------------------------------------------%n");

		junit.awtui.TestRunner.run(MaxTeilsumme_Jut.class);

	} // main
		// ---------------------------------------------------------------------
		// Eine Methode mit einem kurzen Namen:

	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
	// ---------------------------------------------------------------------
} // class MaxTeilsumme_Jut