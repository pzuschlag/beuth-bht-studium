package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTacToe extends Application {
	static Player player = Player.O_PLAYER;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		Button[] cells = new Button[9];
		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Button("", new ImageView(new Image("http://i.imgur.com/KAQPbPd.png")));
			registerOnAction(cells[i]);
		}

		GridPane board = new GridPane();
		for (int row = 1, col = 1, cell = 0; row <= 3; row++, col -= 2) {
			board.add(cells[cell++], row, col++);
			board.add(cells[cell++], row, col++);
			board.add(cells[cell++], row, col);
		}

		Scene scene = new Scene(board);
		stage.setScene(scene);
		stage.setTitle("TicTacToe By Legato");
		// stage.getIcons().add(new Image("http://i.imgur.com/gxhJkc0.png"));
		stage.show();
	}

	private static Image retrieveMarker() {
		player = player == Player.X_PLAYER ? Player.O_PLAYER : Player.X_PLAYER;
		return player.marker();
	}

	private static void registerOnAction(Button button) {
		button.setOnAction(e -> button.setGraphic(new ImageView(retrieveMarker())));
	}
}