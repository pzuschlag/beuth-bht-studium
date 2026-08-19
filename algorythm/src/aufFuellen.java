public class aufFuellen {

	public static void main(String[] args) {

		char[][] rrc = new char[10][10];

		fuelleAus(rrc, 4, 6);

	}

	static public void fuelleAus(char[][] rrc, int z, int s) {
		// Verlaesst sich darauf, dass rrc "rechteckig" ist (d.h. keinen
		// "Flatterand" hat) und dass die Komponente rrc[z][s] existiert.
		//
		// Diese Methode macht nichts, wenn rrc[z][s] bereits ein 'X' enthaelt.
		// Schreibt sonst ein 'X' in alle Komponenten rrc[i][j], fuer die gilt:
		// 1. rrc[i][j] enthaelt noch kein 'X' und
		// 2. rrc[i][j] ist von rrc[z][s] aus erreichbar auf einem Weg, der
		// nur aus waagerechten und/oder senkrechten Abschnitten besteht
		// und keine Komponente kreuzt, die bereits ein 'X' enthaelt.

		if (rrc[z][s] == 'X') {
			int i = rrc.length;

			return;
		}

		if (rrc[z][s] == ' ') {
			rrc[z][s] = 'X';
			fuellAus(rcc, z + 1, s);

			return;
		}

	}
}
