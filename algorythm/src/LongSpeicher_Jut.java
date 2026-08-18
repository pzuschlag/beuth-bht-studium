// Datei LongSpeicher_Jut.java
/*
 * ------------------------------------------------------------------------ Mit diesem JUnit-Testprogramm kann man
 * Klassen testen, die die Schnittstelle LongSpeicher implementieren und genau einen Konstruktor haben, entweder einen
 * ohne Parameter oder einen mit einem int-Parameter.
 * 
 * Achtung: Dieses Testprogramm laeuft nur mit der Version JUnit 3.8, die als eine Datei namens junit.jar vertrieben
 * wird. Fuer neuere Versionen von JUnit wie 4.0 oder 4.2 etc. (vertrieben in Dateien namens junit-4.0.jar oder
 * junit-4.2.jar etc) gelten etwas andere Regeln.
 * 
 * Auf die zu testende Klasse wird per Reflektion zugegriffen. Dadurch koennen bei gewissen Fehlern informativere
 * Fehlermeldungen ausgegeben werden. ------------------------------------------------------------------------
 */
import java.lang.reflect.Constructor;
import java.util.Random;

import junit.framework.TestCase; // Eine Test-Klasse

public class LongSpeicher_Jut extends TestCase {
	// ---------------------------------------------------------------------
	// Der volle Name der zu testenden Klasse ("mit allen Paketen"):
	final static String KLASSEN_NAME = "LongSpeicher60";

	// ---------------------------------------------------------------------
	public LongSpeicher_Jut() {
		// Prueft folgende Bedingungen:
		// 1. Existiert eine Klasse namens KLASSEN_NAME?
		// 2. Implementiert sie die Schnittstelle LongSpeicher?
		// 3. Hat sie genau einen Konstruktor?
		// 4. Hat dieser Konstruktor 0 oder 1 Parameter?
		// 5. Wenn er 1 Parameter hat: Ist der vom Typ int?
		// Beendet dieses Programm, falls eine Bedingung nicht erfuellt ist.
		// Initialisiert sonst die Variablen kob, kon und konHatParam.

		try {
			// kob soll auf das Class-Objekt der zu testenden Klasse zeigen:
			kob = Class.forName(KLASSEN_NAME);

			// Implementiert die zu testende Klasse die
			// Schnittstell LongSpeicher?
			if (!LongSpeicher.class.isAssignableFrom(kob)) {
				printf("Die Klasse %s implementiert nicht " + "die Schnittstelle LongSpeicher!%n", KLASSEN_NAME);
				printf("Sollte sie aber!%n");
				printf("-------------------------------------------------%n");
				System.exit(1);
			}

			// Enthaelt die zu testende Klasse genau einen Konstruktor?
			Constructor<?>[] kons = kob.getDeclaredConstructors();
			if (kons.length > 1) {
				printf("Error: Die Klasse %s enthaelt %d Konstruktoren!%n", KLASSEN_NAME, kons.length);
				printf("Sie sollte nur EINEN Konstruktor enthalten!%n");
				printf("-------------------------------------------------%n");
				System.exit(2);
			}

			// kon soll auf den einzigen Konstruktor der
			// zu testenden Klasse zeigen:
			kon = kons[0];

			// Hat der Konstruktor kon genau einen Parameter?
			Class<?>[] paramTypen = kon.getParameterTypes();
			if (paramTypen.length > 1) {
				printf("Error: Der Konstruktor %s hat %d Parameter!%n", KLASSEN_NAME, paramTypen.length);
				printf("Er sollte EINEN Parameter vom Typ int haben!%n");
				printf("-------------------------------------------------%n");
				System.exit(3);
			}

			// Ist der (einzige) Parameter von kon vom Typ int?
			if (paramTypen.length == 1) {
				// konHatParam = true;
				if (paramTypen[0] != Integer.TYPE) {
					printf("Error: Konstruktor %s hat Param vom Typ %s%n", KLASSEN_NAME, paramTypen[0]);
					printf("Der Param sollte vom Typ int sein!%n");
					printf("-------------------------------------------------%n");
					System.exit(4);
				}
			}

			// Die Klasse LongSpeicher60 hat einen Konstruktor mit einem
			// int-Param, aber der beschraenkt nicht die Anzahl der
			// long-Werte, die man einfuegen kann:
			// if (KLASSEN_NAME.equals("LongSpeicher60")) konHatParam = false;
		} catch (ClassNotFoundException ex) {
			printf("Eine Klasse namens %s%n", KLASSEN_NAME);
			printf("konnte nicht gefunden werden!%n");
			printf("-------------------------------------------------%n");
			System.exit(1);
		} catch (Exception ex) {
			printf("Ausnahme im Konstruktor LongSpeicher_Jut:%n");
			printf("%s%n", ex.toString());
			printf("-------------------------------------------------%n");
			System.exit(5);
		}
	}

	// ---------------------------------------------------------------------
	static Class<?> kob; // Die zu testende Klasse
	static Constructor<?> kon; // Der einzige Konstruktor von kob
	// static boolean konHatParam = false; // Hat der Konstruktor 1 int-Param?
	static boolean istBegrenzt = // Ist die Sammlung begrenzt?
	KLASSEN_NAME.startsWith("LongSpeicher10") || KLASSEN_NAME.startsWith("LongSpeicher20");
	static final int LEN = 5; // Param-Wert fuer den Konstruktor

	// Wenn der Konstruktor einen int-Param p hat, ist der betreffende
	// Speicher begrenzt (man kann nur p long-Werte einfuegen).
	// Aussnahme: Die Klasse LongSpeicher60 realisiert eine Hash-Tabelle.
	// Obwohl sie einen int-Param p hat, kann man unbegrenzt viele
	// long-Werte einfuegen.

	// Die zu testenden Long-Speicher:
	LongSpeicher lsa; // Anfangs leer
	LongSpeicher lsb; // Enthaelt anfangs LEN viele long-Werte

	public void setUp() {
		try {
			// Wenn der zu testende Long-Speicher eine begrenzte
			// Groesse hat:
			// if (konHatParam || KLASSEN_NAME.equals("LongSpeicher60")) {
			if (istBegrenzt || KLASSEN_NAME.startsWith("LongSpeicher60")) {
				lsa = (LongSpeicher) kon.newInstance(new Integer(LEN));
				lsb = (LongSpeicher) kon.newInstance(new Integer(LEN));
			} else {
				lsa = (LongSpeicher) kon.newInstance();
				lsb = (LongSpeicher) kon.newInstance();
			}

			// Fuege LEN long-Werte in den Speicher lsb:
			for (long n = 1 * 10; n <= LEN * 10; n = n + 10) {
				lsb.fuegeEin(n);
			}
		} catch (Throwable t) {
			printf("Ausnahme in Methode setUp!%n");
			printf("%s%n", t);
		}
	}

	// ---------------------------------------------------------------------
	// Die Testfaelle:
	// ---------------------------------------------------------------------
	public void test_1() {
		// lsa und lsb sollten hier auf LongSpeicher-Objekte zeigen:
		assertNotNull("lsa ist gleich null!", lsa);
		assertNotNull("lsb ist gleich null!", lsb);
	}

	// ---------------------------------------------------------------------
	public void test_2() {
		// Wird 0 zur richtigen Zeit gefunden?
		assertEquals("lsa.istDrin (0)", false, lsa.istDrin(0));
		assertEquals("lsa.fuegeEin(0)", true, lsa.fuegeEin(0));
		assertEquals("lsa.istDrin (0)", true, lsa.istDrin(0));
		assertEquals("lsa.loesche (0)", true, lsa.loesche(0));
		assertEquals("lsa.istDrin (0)", false, lsa.istDrin(0));
	}

	// ---------------------------------------------------------------------
	public void test_3() {
		// Systematische Tests mit istDrin und fuegeEin.
		// Es werden LEN long-Werte in den Speicher lsa eingefuegt.

		assertEquals("lsa.istDrin (10)", false, lsa.istDrin(10));
		assertEquals("lsa.istDrin (20)", false, lsa.istDrin(20));
		assertEquals("lsa.istDrin (30)", false, lsa.istDrin(30));
		assertEquals("lsa.istDrin (40)", false, lsa.istDrin(40));
		assertEquals("lsa.istDrin (50)", false, lsa.istDrin(50));

		assertEquals("lsa.fuegeEin(30)", true, lsa.fuegeEin(30));

		assertEquals("lsa.istDrin (10)", false, lsa.istDrin(10));
		assertEquals("lsa.istDrin (20)", false, lsa.istDrin(20));
		assertEquals("lsa.istDrin (30)", true, lsa.istDrin(30));
		assertEquals("lsa.istDrin (40)", false, lsa.istDrin(40));
		assertEquals("lsa.istDrin (50)", false, lsa.istDrin(50));

		assertEquals("lsa.fuegeEin(10)", true, lsa.fuegeEin(10));

		assertEquals("lsa.istDrin (10)", true, lsa.istDrin(10));
		assertEquals("lsa.istDrin (20)", false, lsa.istDrin(20));
		assertEquals("lsa.istDrin (30)", true, lsa.istDrin(30));
		assertEquals("lsa.istDrin (40)", false, lsa.istDrin(40));
		assertEquals("lsa.istDrin (50)", false, lsa.istDrin(50));

		assertEquals("lsa.fuegeEin(50)", true, lsa.fuegeEin(50));

		assertEquals("lsa.istDrin (10)", true, lsa.istDrin(10));
		assertEquals("lsa.istDrin (20)", false, lsa.istDrin(20));
		assertEquals("lsa.istDrin (30)", true, lsa.istDrin(30));
		assertEquals("lsa.istDrin (40)", false, lsa.istDrin(40));
		assertEquals("lsa.istDrin (50)", true, lsa.istDrin(50));

		assertEquals("lsa.fuegeEin(20)", true, lsa.fuegeEin(20));

		assertEquals("lsa.istDrin (10)", true, lsa.istDrin(10));
		assertEquals("lsa.istDrin (20)", true, lsa.istDrin(20));
		assertEquals("lsa.istDrin (30)", true, lsa.istDrin(30));
		assertEquals("lsa.istDrin (40)", false, lsa.istDrin(40));
		assertEquals("lsa.istDrin (50)", true, lsa.istDrin(50));

		assertEquals("lsa.fuegeEin(40)", true, lsa.fuegeEin(40));

		assertEquals("lsa.istDrin (10)", true, lsa.istDrin(10));
		assertEquals("lsa.istDrin (20)", true, lsa.istDrin(20));
		assertEquals("lsa.istDrin (30)", true, lsa.istDrin(30));
		assertEquals("lsa.istDrin (40)", true, lsa.istDrin(40));
		assertEquals("lsa.istDrin (50)", true, lsa.istDrin(50));

		// Die folgenden Tests sind nur fuer begrenzte Sammlungen sinnvoll:
		if (!istBegrenzt)
			return;

		// lsa ist voll. Einfuegen sollte nicht mehr moeglich sein:
		assertEquals("lsa.fuegeEin(60)", false, lsa.fuegeEin(60));
		// Eine nicht eingefuegte Komponente loeschen sollte auch nicht gehn:
		assertEquals("lsa.loesche (60)", false, lsa.loesche(60));
		// Eine eingefuegte Komponente loeschen sollte gehen:
		assertEquals("lsa.loesche (50)", true, lsa.loesche(50));
	}

	// ---------------------------------------------------------------------
	public void test_4() {
		// Wenn konHatParam gleich false ist, dann ist lsb bereits voll und
		// Wenn istBegrenzt gleich true ist, dann ist lsb bereits voll und
		// weitere Aufrufe von lsb.fuegeEin muessen false liefern.
		// Sonst ist die Kapazitaet von lsb unbeschraenkt und weitere
		// Einfuegungen muessen true liefern.
		//
		// Fuer den Fall, dass lsb sortiert ist, wird versucht,
		// VOR allen schon vorhandenen long-Werten, ZWISCHEN je zwei Werten
		// und NACH allen vorhandenen Werten einen weiteren Wert einzufuegen.

		// final boolean SOLL = !konHatParam;
		final boolean SOLL = !istBegrenzt;

		assertEquals("lsb.fuegeEin( 5)", SOLL, lsb.fuegeEin(5));
		assertEquals("lsb.istDrin ( 5)", SOLL, lsb.istDrin(5));

		assertEquals("lsb.fuegeEin(15)", SOLL, lsb.fuegeEin(15));
		assertEquals("lsb.istDrin (15)", SOLL, lsb.istDrin(15));

		assertEquals("lsb.fuegeEin(25)", SOLL, lsb.fuegeEin(25));
		assertEquals("lsb.istDrin (25)", SOLL, lsb.istDrin(25));

		assertEquals("lsb.fuegeEin(35)", SOLL, lsb.fuegeEin(35));
		assertEquals("lsb.istDrin (35)", SOLL, lsb.istDrin(35));

		assertEquals("lsb.fuegeEin(45)", SOLL, lsb.fuegeEin(45));
		assertEquals("lsb.istDrin (45)", SOLL, lsb.istDrin(45));

		assertEquals("lsb.fuegeEin(55)", SOLL, lsb.fuegeEin(55));
		assertEquals("lsb.istDrin (55)", SOLL, lsb.istDrin(55));

		assertEquals("lsb.fuegeEin(65)", SOLL, lsb.fuegeEin(65));
		assertEquals("lsb.istDrin (65)", SOLL, lsb.istDrin(65));
	}

	// ---------------------------------------------------------------------
	public void test_5() {
		// Systematische Tests mit istDrin und loesche.
		// lsb sollte anfangs 10, 20, 30, 40, 50 enthalten:

		assertEquals("lsb.istDrin (10)", true, lsb.istDrin(10));
		assertEquals("lsb.istDrin (20)", true, lsb.istDrin(20));
		assertEquals("lsb.istDrin (30)", true, lsb.istDrin(30));
		assertEquals("lsb.istDrin (40)", true, lsb.istDrin(40));
		assertEquals("lsb.istDrin (50)", true, lsb.istDrin(50));

		assertEquals("lsb.loesche (30)", true, lsb.loesche(30));

		assertEquals("lsb.istDrin (10)", true, lsb.istDrin(10));
		assertEquals("lsb.istDrin (20)", true, lsb.istDrin(20));
		assertEquals("lsb.istDrin (30)", false, lsb.istDrin(30));
		assertEquals("lsb.istDrin (40)", true, lsb.istDrin(40));
		assertEquals("lsb.istDrin (50)", true, lsb.istDrin(50));

		assertEquals("lsb.loesche (30)", false, lsb.loesche(30));
		assertEquals("lsb.loesche (10)", true, lsb.loesche(10));

		assertEquals("lsb.istDrin (10)", false, lsb.istDrin(10));
		assertEquals("lsb.istDrin (20)", true, lsb.istDrin(20));
		assertEquals("lsb.istDrin (30)", false, lsb.istDrin(30));
		assertEquals("lsb.istDrin (40)", true, lsb.istDrin(40));
		assertEquals("lsb.istDrin (50)", true, lsb.istDrin(50));

		assertEquals("lsb.loesche (30)", false, lsb.loesche(30));
		assertEquals("lsb.loesche (10)", false, lsb.loesche(10));
		assertEquals("lsb.loesche (50)", true, lsb.loesche(50));

		assertEquals("lsb.istDrin (10)", false, lsb.istDrin(10));
		assertEquals("lsb.istDrin (20)", true, lsb.istDrin(20));
		assertEquals("lsb.istDrin (30)", false, lsb.istDrin(30));
		assertEquals("lsb.istDrin (40)", true, lsb.istDrin(40));
		assertEquals("lsb.istDrin (50)", false, lsb.istDrin(50));

		assertEquals("lsb.loesche (30)", false, lsb.loesche(30));
		assertEquals("lsb.loesche (10)", false, lsb.loesche(10));
		assertEquals("lsb.loesche (50)", false, lsb.loesche(50));
		assertEquals("lsb.loesche (20)", true, lsb.loesche(20));

		assertEquals("lsb.istDrin (10)", false, lsb.istDrin(10));
		assertEquals("lsb.istDrin (20)", false, lsb.istDrin(20));
		assertEquals("lsb.istDrin (30)", false, lsb.istDrin(30));
		assertEquals("lsb.istDrin (40)", true, lsb.istDrin(40));
		assertEquals("lsb.istDrin (50)", false, lsb.istDrin(50));

		assertEquals("lsb.loesche (30)", false, lsb.loesche(30));
		assertEquals("lsb.loesche (10)", false, lsb.loesche(10));
		assertEquals("lsb.loesche (50)", false, lsb.loesche(50));
		assertEquals("lsb.loesche (20)", false, lsb.loesche(20));
		assertEquals("lsb.loesche (40)", true, lsb.loesche(40));

		assertEquals("lsb.istDrin (10)", false, lsb.istDrin(10));
		assertEquals("lsb.istDrin (20)", false, lsb.istDrin(20));
		assertEquals("lsb.istDrin (30)", false, lsb.istDrin(30));
		assertEquals("lsb.istDrin (40)", false, lsb.istDrin(40));
		assertEquals("lsb.istDrin (50)", false, lsb.istDrin(50));

		assertEquals("lsb.loesche (30)", false, lsb.loesche(30));
		assertEquals("lsb.loesche (10)", false, lsb.loesche(10));
		assertEquals("lsb.loesche (50)", false, lsb.loesche(50));
		assertEquals("lsb.loesche (20)", false, lsb.loesche(20));
		assertEquals("lsb.loesche (40)", false, lsb.loesche(40));
	}

	// ---------------------------------------------------------------------
	public void test_6() {
		// Spezielle Tests mit loesche bei binaeren Baeumen
		// In die Sammlung lsa werden Zahlen so eingefuegt, dass ein
		// interessanter binaerer Baum entsteht, bei dem einige Knoten auch
		// 2 nicht-leere Unterbaeume haben.

		// Sammlungen mit begrenzter Kapazitaet (auf Reihungen basierend)
		// sollen hier nicht getestet werden
		if (istBegrenzt)
			return;

		// 7 Knoten einfuegen
		assertEquals("lsa.fuegeEin(50)", true, lsa.fuegeEin(50));
		assertEquals("lsa.fuegeEin(30)", true, lsa.fuegeEin(30));
		assertEquals("lsa.fuegeEin(70)", true, lsa.fuegeEin(70));
		assertEquals("lsa.fuegeEin(20)", true, lsa.fuegeEin(20));
		assertEquals("lsa.fuegeEin(40)", true, lsa.fuegeEin(40));
		assertEquals("lsa.fuegeEin(60)", true, lsa.fuegeEin(60));
		assertEquals("lsa.fuegeEin(80)", true, lsa.fuegeEin(80));

		// Sind alle eingefuegten auch wirklich drin?
		assertEquals("lsa.istDrin (50)", true, lsa.istDrin(50));
		assertEquals("lsa.istDrin (30)", true, lsa.istDrin(30));
		assertEquals("lsa.istDrin (70)", true, lsa.istDrin(70));
		assertEquals("lsa.istDrin (20)", true, lsa.istDrin(20));
		assertEquals("lsa.istDrin (40)", true, lsa.istDrin(40));
		assertEquals("lsa.istDrin (60)", true, lsa.istDrin(60));
		assertEquals("lsa.istDrin (80)", true, lsa.istDrin(80));

		// Sind ein paar nicht-eingefuegte auch wirklich nicht-drin?
		assertEquals("lsa.istDrin (15)", false, lsa.istDrin(15));
		assertEquals("lsa.istDrin (55)", false, lsa.istDrin(55));
		assertEquals("lsa.istDrin (35)", false, lsa.istDrin(35));
		assertEquals("lsa.istDrin (75)", false, lsa.istDrin(75));
		assertEquals("lsa.istDrin (25)", false, lsa.istDrin(25));
		assertEquals("lsa.istDrin (45)", false, lsa.istDrin(45));
		assertEquals("lsa.istDrin (65)", false, lsa.istDrin(65));
		assertEquals("lsa.istDrin (85)", false, lsa.istDrin(85));

		// Loesche alle eingefuegten Knoten
		assertEquals("lsa.loesche (50)", true, lsa.loesche(50));
		assertEquals("lsa.loesche (30)", true, lsa.loesche(30));
		assertEquals("lsa.loesche (70)", true, lsa.loesche(70));
		assertEquals("lsa.loesche (20)", true, lsa.loesche(20));
		assertEquals("lsa.loesche (40)", true, lsa.loesche(40));
		assertEquals("lsa.loesche (80)", true, lsa.loesche(80));
		assertEquals("lsa.loesche (60)", true, lsa.loesche(60));

		// Sind alle geloeschten auch wirklich nicht mehr drin?
		assertEquals("lsa.istDrin (50)", false, lsa.istDrin(50));
		assertEquals("lsa.istDrin (30)", false, lsa.istDrin(30));
		assertEquals("lsa.istDrin (70)", false, lsa.istDrin(70));
		assertEquals("lsa.istDrin (20)", false, lsa.istDrin(20));
		assertEquals("lsa.istDrin (40)", false, lsa.istDrin(40));
		assertEquals("lsa.istDrin (60)", false, lsa.istDrin(60));
		assertEquals("lsa.istDrin (80)", false, lsa.istDrin(80));
	}

	// ---------------------------------------------------------------------
	public void test_7() {
		// Tests mit negativen und grossen long-Werten (vor allem fuer die
		// Hash-Tabellen LongSpeicher60, denn dort muss aus den long-Werten
		// ein Index berechnet werden).

		long n1 = +123_456_789_012L;
		long n2 = -123_456_789_012L;
		long n3 = +234_567_890_123L;
		long n4 = -234_567_890_123L;
		long n5 = +345_678_901_234L;
		long n6 = -345_678_901_234L;
		long n7 = Long.MAX_VALUE;
		long n8 = Long.MIN_VALUE;

		// assertEquals("lsa.fuegeEin(n1)", true, lsa.fuegeEin(n1));
		// assertEquals("lsa.fuegeEin(n2)", true, lsa.fuegeEin(n2));
		// assertEquals("lsa.fuegeEin(n3)", true, lsa.fuegeEin(n3));
		assertEquals("lsa.fuegeEin(n4)", true, lsa.fuegeEin(n4));
		assertEquals("lsa.fuegeEin(n5)", true, lsa.fuegeEin(n5));
		assertEquals("lsa.fuegeEin(n6)", true, lsa.fuegeEin(n6));
		assertEquals("lsa.fuegeEin(n7)", true, lsa.fuegeEin(n7));
		assertEquals("lsa.fuegeEin(n8)", true, lsa.fuegeEin(n8));

		// assertEquals("lsa.istDrin(n1)", true, lsa.istDrin(n1));
		// assertEquals("lsa.istDrin(n2)", true, lsa.istDrin(n2));
		// assertEquals("lsa.istDrin(n3)", true, lsa.istDrin(n3));
		assertEquals("lsa.istDrin(n4)", true, lsa.istDrin(n4));
		assertEquals("lsa.istDrin(n5)", true, lsa.istDrin(n5));
		assertEquals("lsa.istDrin(n6)", true, lsa.istDrin(n6));
		assertEquals("lsa.istDrin(n7)", true, lsa.istDrin(n7));
		assertEquals("lsa.istDrin(n8)", true, lsa.istDrin(n8));

		// assertEquals("lsa.loesche(n1)", true, lsa.loesche(n1));
		// assertEquals("lsa.loesche(n2)", true, lsa.loesche(n2));
		// assertEquals("lsa.loesche(n3)", true, lsa.loesche(n3));
		assertEquals("lsa.loesche(n4)", true, lsa.loesche(n4));
		assertEquals("lsa.loesche(n5)", true, lsa.loesche(n5));
		assertEquals("lsa.loesche(n6)", true, lsa.loesche(n6));
		assertEquals("lsa.loesche(n7)", true, lsa.loesche(n7));
		assertEquals("lsa.loesche(n8)", true, lsa.loesche(n8));
	}

	// ---------------------------------------------------------------------
	public void test_8() {
		// In unbegrenzte Sammlungen werden hier "viele" positive und negative
		// long-Werte eingefuegt, gesucht und geloescht.

		// Sammlungen mit begrenzter Kapazitaet (auf Reihungen basierend)
		// sollen hier nicht getestet werden
		if (istBegrenzt)
			return;

		int anzahl = 20_000;
		long seed = 12345;
		Random rand = new Random();

		rand.setSeed(seed);
		for (int i = 1; i <= anzahl; i++) {
			long komp = rand.nextLong();
			String msg = String.format("lsb.fuegeEin(%,d)", komp);
			assertEquals(msg, true, lsa.fuegeEin(komp));
		}

		rand.setSeed(seed);
		for (int i = 1; i <= anzahl; i++) {
			long komp = rand.nextLong();
			String msg = String.format("lsb.istDrin(%,d)", komp);
			assertEquals(msg, true, lsa.istDrin(komp));
		}

		rand.setSeed(seed);
		for (int i = 1; i <= anzahl; i++) {
			long komp = rand.nextLong();
			String msg = String.format("lsb.loesche(%,d)", komp);
			assertEquals(msg, true, lsa.loesche(komp));
		}
	}

	// ---------------------------------------------------------------------
	static public void main(String[] args) {
		printf("LongSpeicher_Jut: Jetzt geht es los!%n");
		printf("------------------------------------%n");
		printf("Getestet wird die Klasse: %s%n", KLASSEN_NAME);
		printf("Diese Klasse ist %s.%n", istBegrenzt ? "begrenzt" : "unbegrenzt");
		printf("-------------------------------------------------%n");

		junit.awtui.TestRunner.run(LongSpeicher_Jut.class);

	} // main
		// ---------------------------------------------------------------------
		// Eine Methode mit einem kurzen Namen:

	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}
	// ---------------------------------------------------------------------
} // class LongSpeicher_Jut

