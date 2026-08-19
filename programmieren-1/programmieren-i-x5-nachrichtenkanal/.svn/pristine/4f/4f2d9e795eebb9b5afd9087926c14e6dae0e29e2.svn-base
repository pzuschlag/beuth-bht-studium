import cs101.lang.Animate;

public class Receiver implements Animate {

	TextContainer chnl;
	TextGUI gui;

	public Receiver(TextContainer chnl) {
		this.chnl = chnl;
		gui = new TextGUI();
	}

	@Override
	public void act() {

		if (!chnl.empty()) {
			try {
				gui.write(chnl.remove());
			} catch (EmptyException e) {
			}
		}
	}
}
