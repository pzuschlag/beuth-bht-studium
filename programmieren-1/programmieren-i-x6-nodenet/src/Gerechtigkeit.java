import nodenet.ChannelDisabledException;
import nodenet.ChannelEmptyException;
import nodenet.ChannelFullException;
import nodenet.InputChannelVector;
import nodenet.NodeBehavior;
import nodenet.OutputChannelVector;

public class Gerechtigkeit implements NodeBehavior {

	private Object o = null;
	private int inputNo = 0;
	private int outputNo = 0;

	/**
	 * Der Gerechte Knoten verteilt die einkommenden Objekte gerecht auf die Vorhandenen Ausgangskanaele, pro Tik Werden
	 * daher Pro Ausgangskanal ein Paket ausgeliefert
	 */
	@Override
	public void transmitPacket(InputChannelVector inputChannels, OutputChannelVector outputChannels) {

		int numInputChannels = inputChannels.size();
		int numOutputChannels = outputChannels.size();

		// Falls Keine Kanaele mit dem Knoten verbunden sind
		if ((numInputChannels == 0) || (numOutputChannels == 0)) {
			return;
		}

		{
			// Auslesen des InputKanals
			try {
				this.o = inputChannels.elementAt(inputNo).readObject();
				inputNo++;
			} catch (ChannelEmptyException e) {
				System.err.println("Inputkanal ist leer !");
				return;
			} catch (ChannelDisabledException e) {
				System.err.println("Inputkanal ist deaktiviert !");
				return;
			}

			if (inputNo == numInputChannels) {
				inputNo = 0;
			}
		}
		{
			// Schreiben auf den Kanal
			try {
				outputChannels.elementAt(outputNo).writeObject(this.o);
				outputNo++;
			} catch (ChannelFullException e) {
				System.err.println("Outputkanal ist voll !");
				return;
			} catch (ChannelDisabledException e) {
				System.err.println("Outputkanal ist deaktiviert !");
				return;
			}

			if (outputNo == numOutputChannels) {
				outputNo = 0;
			}
		}
	}
}
