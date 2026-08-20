package aufgabe2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class Wahlerbefragung extends Application {
	public void start(Stage stage) {

		int pushRed = 0;
		int pushGreen = 0;
		int pushYellow = 0;
		int pushBlack = 0;

		Button btn_red = new Button("Rot");
		Button btn_green = new Button("Gruen");
		Button btn_yellow = new Button("Gelb");
		Button btn_black = new Button("Schwarz");

		// String verkettung macht aus dem int nen String, ansonsten Integer benutzen
		Label lbl_red = new Label("" + pushRed);
		Label lbl_green = new Label("" + pushGreen);
		Label lbl_yellow = new Label("" + pushYellow);
		Label lbl_black = new Label("" + pushBlack);

		VBox vbox = new VBox(btn_red, lbl_red, btn_green, lbl_green, btn_yellow, lbl_yellow, btn_black, lbl_black);
		Scene root = new Scene(vbox, 600, 400);

		stage.setScene(root);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}
