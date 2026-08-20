package aufgabe1;

class Portmonaie {

	private GeldchipImpl privat = new GeldchipImpl();
	private GeldchipImpl gesch = new GeldchipImpl();

	public void privatebezhaen(double x) throws GuthabenZuKleinAusnahme {
		privat.bezahlen(x);
	}

	public double gesamt() {
		return privat.guthaben() + gesch.guthaben();
	}
}
