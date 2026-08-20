public class MaxTeilsumme01 {
	public static void main(String[] args) {

		// int[] r = { +5, -4, +4, -3, +5, -4, +3, -2, +4, -3, +2, -1 };
		int[] r = { 1, 2, 3 };
		// int[] r = { -3, +2, +3, -2 };
		// int[] r = { -123 };

		// System.out.println("Maximale Teilsumme: " + mts01(r));
		// System.out.println("Maximale Teilsumme: " + mts02(r));

		// System.out.println("Rechtes RandMax: " + linkesRandMax(r, 1, r.length - 1));
		System.out.println("Maximale Teilsumme: " + mts04(r));
	}

	static int mts01(int[] r) {
		final int N = r.length; // Eine kleine Abkuerzung
		int mts = 0;

		for (int von = 0; von < N; von++) {
			for (int bis = von; bis < N; bis++) {
				int ts = 0;

				for (int i = von; i <= bis; i++) {
					ts += r[i];
				}
				if (mts < ts)
					mts = ts;
			}
		}
		return mts;
	}

	static int mts02(int[] r) {
		// Liefert die maximale Teilsumme von r

		final int N = r.length; // Eine kleine Abkuerzung
		int[][] teilSumme = new int[N][];
		int bisherMaxTs = 0; // Die maximale Teilsumme ist mindestens gleich 0

		// Initialisiert jede Komponente teilSumme[von] mit einer
		// int-Reihung der Laenge N-von und und initialisiert die
		// Komponente teilSumme[von][0] mit r[von]
		for (int von = 0; von < N; von++) {
			teilSumme[von] = new int[N - von];
			teilSumme[von][0] = r[von];

			// Die uebrigen Spalten von teilSumme initialisieren:
			for (int len = 1; len < N - von; len++) {
				teilSumme[von][len] = teilSumme[von][len - 1] + r[von + len];

				// Die maximale Komponente teilSumme[von][len] ermitteln:
				if (bisherMaxTs < teilSumme[von][len])
					bisherMaxTs = teilSumme[von][len];
			}
			if (bisherMaxTs < teilSumme[von][0])
				bisherMaxTs = teilSumme[von][0];
		}
		return bisherMaxTs;
	}

	static private int rechtesRandMax(int[] r, int links, int rechts) {
		// Liefert das rechte Randmaximum der Teilreihung r[links..rechts]
		int bisherRandMax = 0;
		int bisherSumme = 0;
		for (int i = rechts; i >= links; i--) {
			bisherSumme += r[i];
			if (bisherRandMax < bisherSumme)
				bisherRandMax = bisherSumme;
		}
		return bisherRandMax;
	}

	static private int linkesRandMax(int[] r, int links, int rechts) {
		// Liefert das linke Randmaximum der Teilreihung r[links..rechts]
		int bisherRandMax = 0;
		int bisherSumme = 0;
		for (int i = links; i <= rechts; i++) {
			bisherSumme += r[i];
			if (bisherRandMax < bisherSumme)
				bisherRandMax = bisherSumme;
		}
		return bisherRandMax;
	}

	// ---------------------------------------------------------------------
	static int mts03(int[] r) {
		// Liefert die maximale Teilsumme von r.
		if (r.length == 0)
			return 0;
		return maxTeilsummeRek(r, 0, r.length - 1);
	}

	// ---------------------------------------------------------------------
	static private int maxTeilsummeRek(int[] r, int links, int rechts) {
		// Liefert die maximale Teilsumme der Teilfolge r[links..rechts]

		// Den einfachen (nicht-rekursiven) Fall behandeln (Laenge 1):
		if (links == rechts)
			return (r[links] > 0) ? r[links] : 0;

		// Den rekursiven Fall behandeln (Laenge>1):
		int maxL = maxTeilsummeRek(r, links, ((links + rechts) / 2));
		int rrmaxL = rechtesRandMax(r, links, ((links + rechts) / 2));

		// Berechne die maximale Teilsumme maxR von R und das linke Randmaximum lrmaxR von R
		int maxR = maxTeilsummeRek(r, ((links + rechts) / 2) + 1, rechts);
		int lrmaxR = linkesRandMax(r, ((links + rechts) / 2) + 1, rechts);

		// Liefere das Maximum der drei Zahlen maxL, maxR und (rrmaxL+lrmaxR) als Ergebnis
		if (maxL > maxR && maxL > (lrmaxR + rrmaxL))
			return maxL;
		if (maxR > (lrmaxR + rrmaxL))
			return maxR;
		return (lrmaxR + rrmaxL);
	}

	// ---------------------------------------------------------------------

	static int mts04(int[] r) {
		int rechtesRandMax = 0;
		int bisherMaxTs = 0;

		for (int i = 0; i < r.length; i++) {
			// Berechnen des rechten Randmaximums von LR
			rechtesRandMax = rechtesRandMax(r, 0, i);
			if (bisherMaxTs < rechtesRandMax)
				bisherMaxTs = rechtesRandMax;

			// die maximale Teilsumme von LR
			int ts = 0;
			for (int j = 0; j < i; j++) {
				ts += r[j];
			}
			if (bisherMaxTs < ts)
				bisherMaxTs = ts;
		}
		return bisherMaxTs;
	}
}
