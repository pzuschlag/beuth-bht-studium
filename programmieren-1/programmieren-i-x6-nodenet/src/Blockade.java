import nodenet.InputChannelVector;
import nodenet.NodeBehavior;
import nodenet.OutputChannelVector;

public class Blockade implements NodeBehavior {

	/**
	 * Der Knoten blockiert den Ablauf Blockkade da keine Behandlung implementiert wurde.
	 */
	@Override
	public void transmitPacket(InputChannelVector inputChannels, OutputChannelVector outputChannels) {
		// Keine Behandlung
	}

}
