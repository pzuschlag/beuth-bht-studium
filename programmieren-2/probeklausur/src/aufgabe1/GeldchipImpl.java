package aufgabe1;

public class GeldchipImpl implements Geldchip {
	private double saldo = 0;

	public void auffuellen(double x) {
		saldo = saldo + x;
	}

	public void bezahlen(double x) throws GuthabenZuKleinAusnahme {

		if (x > saldo) {
			throw new GuthabenZuKleinAusnahme();
		}
		saldo = saldo - x;
	}

	public double guthaben() {
		return saldo;
	}
}