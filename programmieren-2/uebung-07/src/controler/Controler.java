package controler;

import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.Spieler;



public class Controler {


	String dateipfad;
	ArrayList<Button> sf;
	ArrayList<Button> sfX;
	ArrayList<Button> sfO;

	Random rnd = new Random();
	boolean spielEnde;

	// Button
	@FXML
	private Button button00;
	@FXML
	private Button button10;
	@FXML
	private Button button20;
	@FXML
	private Button button01;
	@FXML
	private Button button11;
	@FXML
	private Button button21;
	@FXML
	private Button button02;
	@FXML
	private Button button12;
	@FXML
	private Button button22;


	/**
	 * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		dateipfad = "resources/spielfeld.class";
		spielEnde = false;

		// TODO irgendwas mit REFLECTION wäre super
		sf = new ArrayList<Button>();
		sf.add(button00);
		sf.add(button01);
		sf.add(button02);
		sf.add(button10);
		sf.add(button11);
		sf.add(button12);
		sf.add(button20);
		sf.add(button21);
		sf.add(button22);

		sfX = new ArrayList<Button>();
		sfO = new ArrayList<Button>();
	}


	@FXML
	public void spiele(ActionEvent event) {

		if (!spielEnde) {

			// Spieler spielt
			Button spBt = ((Button) event.getSource());
			while (!sf.contains(spBt)) {
				return;
			}
			spBt.setGraphic(new ImageView(Spieler.SPIELER_X.sign()));
			sf.remove(spBt);
			sfX.add(spBt);

			// Auswertung
			if (spielGewonnen(sfX)) {
				System.out.println("Spieler hat gewonnen ! :)");
				spielEnde = true;
			}

			if (sf.isEmpty() == true) {
				System.out.println("Unentschieden, keiner hat gewonnen :O");
				spielEnde = true;
			}
		}

		if (!spielEnde) {

			// KI Spielt
			Button kiBt = sf.get(rnd.nextInt(sf.size()));
			kiBt.setGraphic(new ImageView(Spieler.SPIELER_O.sign()));
			sf.remove(kiBt);
			sfO.add(kiBt);

			// Auswertung
			if (spielGewonnen(sfO)) {
				System.out.println("Der Computer hat gewonnen !");
				spielEnde = true;
			}

		}
	}


	/**
	 * Prüft ob ein Spieler gewonnen hat.
	 * 
	 * @param imageView
	 * 
	 * @param spieler
	 * @return
	 */
	private boolean spielGewonnen(ArrayList<Button> sf) {

		// Prüfung Senkrecht
		if (sf.contains(button00) && sf.contains(button01) && sf.contains(button02))
			return true;
		if (sf.contains(button10) && sf.contains(button11) && sf.contains(button12))
			return true;
		if (sf.contains(button20) && sf.contains(button21) && sf.contains(button22))
			return true;

		// Prüfung Wagerecht
		if (sf.contains(button00) && sf.contains(button10) && sf.contains(button20))
			return true;
		if (sf.contains(button01) && sf.contains(button11) && sf.contains(button21))
			return true;
		if (sf.contains(button02) && sf.contains(button12) && sf.contains(button22))
			return true;

		// Prüfung Diagonal
		if (sf.contains(button00) && sf.contains(button11) && sf.contains(button22))
			return true;
		if (sf.contains(button02) && sf.contains(button11) && sf.contains(button20))
			return true;

		return false;
	}
}
