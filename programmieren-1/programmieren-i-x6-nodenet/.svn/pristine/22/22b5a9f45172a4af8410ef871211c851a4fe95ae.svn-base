import nodenet.ChannelDisabledException;
import nodenet.ChannelEmptyException;
import nodenet.ChannelFullException;
import nodenet.InputChannelVector;
import nodenet.NodeBehavior;
import nodenet.OutputChannelVector;

public class Zufall implements NodeBehavior {

	private Object o = null;
	private int inputNo = 0;
	private int outputNo = 0;

	/**
	 * Der Zufallsknoten liesst zufaellig aus den Verhanden Kanaelenen einen aus und Schreibt das erhaltene Objekt
	 * anschliessend zufaellig in einen der Vorhandenen Ausgangskanaele.
	 */
	@Override
	public void transmitPacket(InputChannelVector inputChannels, OutputChannelVector outputChannels) {

		int numInputChannels = inputChannels.size();
		int numOutputChannels = outputChannels.size();

		// Falls Keine Kanaele mit dem Knoten verbunden sind
		if ((numInputChannels == 0) || (numOutputChannels == 0)) {
			return;
		}

		// Auslesen des InputKanals
		{
			try {
				this.o = inputChannels.elementAt(inputNo).readObject();
			} catch (ChannelEmptyException e) {
				System.err.println("Inputkanal ist leer !");
				return;
			} catch (ChannelDisabledException e) {
				System.err.println("Inputkanal ist deaktiviert !");
				return;
			}

			// Ermitteln des Zufallskanals
			inputNo = (int) (Math.random() * numInputChannels);
		}

		// Schreiben auf dem OutputKanals
		{
			try {
				outputChannels.elementAt(outputNo).writeObject(this.o);
			} catch (ChannelFullException e) {
				System.err.println("Outputkanal ist voll !");
				return;
			} catch (ChannelDisabledException e) {
				System.err.println("Outputkanal ist deaktiviert !");
				return;
			}

			// Ermitteln des Zufallkanals
			outputNo = (int) (Math.random() * numOutputChannels);

		}
	}
}
