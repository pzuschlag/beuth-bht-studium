import nodenet.ChannelDisabledException;
import nodenet.ChannelEmptyException;
import nodenet.ChannelFullException;
import nodenet.InputChannelVector;
import nodenet.NodeBehavior;
import nodenet.OutputChannelVector;

public class Verzoegerung implements NodeBehavior {

	private Object o = null;
	int inputNo = 0;
	int outputNo = 0;

	/**
	 * Der Gerechte Knoten verteilt die einkommenden Objekte gerecht auf die Vorhandenen Ausgangskanaele, pro Tik Werden
	 * daher Pro Ausgangskanal ein Paket ausgeliefert
	 * 
	 * Erweitert Gerecht um Sleep einen Sleep von 2sek.
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
				inputNo++;
			} catch (ChannelEmptyException e) {
				System.err.println("Inputkanal ist leer !");
				return;
			} catch (ChannelDisabledException e) {
				System.err.println("Inputkanal ist deaktiviert !");
				return;
			}

			if (inputNo == numOutputChannels) {
				inputNo = 0;
			}
		}

		// 2sek. Verzögerung des Threads
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
		}

		// Schreiben auf dem OutputKanals
		{
			try {
				outputChannels.elementAt(outputNo).writeObject(o);
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
