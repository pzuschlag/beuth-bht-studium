import static java.lang.Math.floor;
import static java.lang.Math.log;

import java.lang.reflect.Method;

class Alg_SchrittFunktionenA {
	// ---------------------------------------------------------------------
	// Die Algorithmen alg00, alg01, ...
	// ---------------------------------------------------------------------
	static public void alg00(int n) {
		for (int i = 1; i <= n; i++)
			schritt();
	}

	static public void alg01(int n) {
		for (int i = 1; i <= n; i++)
			schritt();
		for (int i = 1; i <= n; i++)
			schritt();
	}

	static public void alg02(int n) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				schritt();
			}
		}
	}

	static public void alg03(int n) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				for (int k = 1; k <= n; k++) {
					schritt();
				}
			}
		}
	}

	static public void alg04(int n) {
		for (int i = 1; i <= n / 2; i++) {
			for (int j = 1; j <= n; j++) {
				for (int k = 1; k <= n / 2; k++) {
					schritt();
				}
			}
		}
	}

	static public void alg05(int n) {
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= n / 2; j++) {
				for (int k = 1; k <= 4; k++) {
					schritt();
				}
			}
		}
	}

	static public void alg06(int n) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= i; j++) {
				schritt();
			}
		}
	}

	static public void alg07(int n) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				for (int k = 1; k <= 3 * n; k++) {
					schritt();
				}
			}
		}

		for (int i = 1; i <= 5 * n; i++) {
			for (int j = 1; j <= n; j++) {
				schritt();
			}
		}

		for (int i = 1; i <= 4 * n; i++) {
			schritt();
		}

		for (int i = 1; i <= 7; i++) {
			schritt();
		}

	}

	static public void alg08(int n) {
		for (int i = 1; i <= n * n; i++) {
			for (int j = 1; j <= n * n * n; j++) {
				schritt();
			}
		}
	}

	static public void alg09(int n) {
		schritt();
		if (n > 1) {
			alg09(n - 1);
		}
	}

	static public void alg10(int n) {
		schritt();
		if (n > 1) {
			alg10(n - 1);
			alg10(n - 1);
		}
	}

	static public void alg11(int n) {
		schritt();
		if (n > 1) {
			alg11(n - 1);
			alg11(n - 1);
			alg11(n - 1);
		}
	}

	static public void alg12(int n) {
		schritt();
		if (n > 1) {
			alg12(n - 1);
			alg12(n - 1);
			alg12(n - 1);
			alg12(n - 1);
		}
	}

	static public void alg13(int n) {
		if (n <= 1) {
			schritt();
		} else {
			alg13(n - 1);
			alg13(n - 1);
		}
	}

	static public void alg14(int n) {
		if (n <= 1) {
			schritt();
		} else {
			alg14(n - 1);
			alg14(n - 1);
			alg14(n - 1);
		}
	}

	static public void alg15(int n) {
		schritt();
		if (n <= 1)
			return;
		alg15(n / 2);
	}

	static public void alg16(int n) {
		schritt();
		if (n <= 2)
			return;
		alg16(n / 3);
	}

	static public void alg17(int n) {
		if (n == 0)
			return;
		if (n == 1) {
			schritt();
			return;
		}

		alg17(n / 2);
		alg17(n - n / 2);
		for (int i = 1; i <= n; i++)
			schritt();
	}

	// ---------------------------------------------------------------------
	// Die SchrittFunktionen stp00, stp01, ... ("stp" wie "step")
	// der Algorithmen alg00, alg01, ...
	// ---------------------------------------------------------------------
	static public int stp00(int n) {
		return n;
	}

	static public int stp01(int n) {
		return 2 * n;
	}

	static public int stp02(int n) {
		return n * n;
	}

	static public int stp03(int n) {
		return n * n * n;
	}

	static public int stp04(int n) {
		return (n / 2) * n * (n / 2);
	}

	static public int stp05(int n) {
		return 3 * (n / 2) * 4;
	}

	static public int stp06(int n) {
		return (n + (n * n)) / 2;
	}

	static public int stp07(int n) {
		return (n + (n * n * n * 3) / 2) + ((n + (n * 5) * n) / 2) + (n * 4) + 7;
	}

	static public int stp08(int n) {
		return n * n * n * n * n;
	}

	static public int stp09(int n) {
		return n;
	}

	static public int stp10(int n) {
		return n;
	}

	static public int stp11(int n) {
		return -99;
	}

	static public int stp12(int n) {
		return -99;
	}

	static public int stp13(int n) {
		return -99;
	}

	static public int stp14(int n) {
		return -99;
	}

	static public int stp15(int n) {
		return -99;
	}

	static public int stp16(int n) {
		return -99;
	}

	static public int stp17(int n) {
		return -99;
	}

	// ---------------------------------------------------------------------
	// Eine (rekursive) Potenzfunktion ("hoch-Funktion") fuer int-Werte
	static int h(int b, int p) {
		// Liefert die Zahl "b hoch p", wenn p positiv ist.
		// Liefert sonst 1.
		if (p <= 0)
			return 1;
		if (p == 1)
			return b;
		return b * h(b, p - 1);
	}

	// ---------------------------------------------------------------------
	// Eine Funktion zur Berechnung ganzzahliger Logarithmen
	// zu beliebigen Basen (die groesser als 1 sind), Version A:
	static int glA(int b, int z) {
		int erg = 1;
		while (z >= b) {
			erg++;
			z /= b;
		}
		return erg;
	}

	// ---------------------------------------------------------------------
	// Eine Funktion zur Berechnung ganzzahliger Logarithmen
	// zu beliebigen Basen (die groesser als 1 sind), Version B.
	static int glB(int b, int z) {
		return 1 + (int) floor(log(z) / log(b));
	}

	// ---------------------------------------------------------------------
	// In dieser Variablen zaehlt die Methode schritt(), wie oft sie
	// aufgerufen wurde:
	static int algSteps;

	// Die Methode schritt() zaehlt nur, wie oft sie aufgerufen wurde:
	static void schritt() {
		algSteps++;
	}

	// ---------------------------------------------------------------------
	// Der Algorithmus alg12 wird mit den Parametern 1, 2, ..., maxN[12]
	// aufgerufen. Fuer die anderen Algorithmen entsprechend.
	static int[] maxN = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 20, 20, 65, };
	// ---------------------------------------------------------------------
	// Die Anzahl Schritte, die der Algorithmus alg12 tatsaechlich
	// ausfuehrt, und die Anzahl Schritte, die seine Schrittfunktion
	// stp12 voraussagt, werden verglichen. Wenn sie nicht gleich sind,
	// wird das als ein Fehler der Schrittfunktion gezaehlt.
	// Fuer die anderen Algorithmen entsprechend.
	static int anzVergleiche = 0;
	static int anzFehler = 0;

	// ---------------------------------------------------------------------
	static void stepCount(int nr) {
		// Ruft den Algorithmus Nummer nr auf und vergleicht seine
		// tatsaechliche Schrittzahl mit der von der zugehoerigen
		// Schrittzahlfunktion vorausgesagten Schrittzahl. Gibt das
		// Ergebnis zur Standardausgabe aus.

		// Die Algorithmen alg00, alg01, ... und ihre
		// SchrittFunktionen stp00, stp01, ... werden
		// per Reflexion aufgerufen.
		Class<?> cob = Alg_SchrittFunktionenA.class;
		// Alle Algorithmen haben genau 1 Parameter vom Typ int:
		Class<?>[] paramTypes = { Integer.TYPE };
		// Name des Algorithmus:
		String nameA = String.format("alg%02d", nr);
		// Name der zugehoehrigen Schrittfunktion:
		String nameS = String.format("stp%02d", nr);
		int stpSteps = 0;

		try {
			Method alg = cob.getMethod(nameA, paramTypes);
			Method stp = cob.getMethod(nameS, paramTypes);

			printf("--------------------------------------%n");
			printf(" N    %5s(N)   %5s(N)%n%n", nameS, nameA);

			for (int n = 1; n <= maxN[nr]; n++) {
				algSteps = 0;
				alg.invoke(null, n);

				stpSteps = (int) stp.invoke(null, n);
				printf("%2d %,8d   %,8d  ", n, stpSteps, algSteps);

				anzVergleiche++;
				if (algSteps != stpSteps) {
					anzFehler++;
					printf(" <-- Fehler?");
				}
				printf("%n");
			}
		} catch (Throwable t) {
			printf("Ausnahme in Methode stepCount mit nr gleich %02d%n", nr);
			printf("%s%n", t);
		}
	}

	// ---------------------------------------------------------------------
	// Eine Methode mit einem kurzen Namen:
	static void printf(String f, Object... v) {
		System.out.printf(f, v);
	}

	// ---------------------------------------------------------------------
	static public void main(String[] sonja) {
		printf("Alg_SchrittFunktionenA: Jetzt geht es los!%n");

		// Die folgende Schleife testet die Schrittfunktionen
		// der ersten beiden Algorithmen (alg00 und alg01):
		// for (int nr=0; nr<2; nr++) stepCount(nr);

		// Die folgende Schleife testet die Schrittfunktionen
		// ALLER Algorithmen:
		for (int nr = 0; nr < maxN.length; nr++)
			stepCount(nr);

		printf("--------------------------------------%n");
		printf("Anzahl Vergleiche: %d, davon Fehler: %d%n", anzVergleiche, anzFehler);
		printf("--------------------------------------%n");
		printf("Alg_SchrittFunktionenA: Das war's erstmal!%n");
	} // main
		// ---------------------------------------------------------------------
} // class Alg_SchrittFunktionenA