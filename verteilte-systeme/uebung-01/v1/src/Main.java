import java.util.concurrent.CopyOnWriteArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import controller.Search;

/**
 * 2 Startet die Applikation.
 *
 * @className MainTelefonserver
 * @author Philip Zuschlag
 * @date 2016-04-22
 */
public class Main extends Application {

	public CopyOnWriteArrayList<String[]> results = new CopyOnWriteArrayList<String[]>();

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Search");

		primaryStage.setWidth(450);
		primaryStage.setHeight(150);
		primaryStage.setResizable(false);

		primaryStage.setScene(new Scene(getPane(primaryStage)));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Liefert die Pane welche in der Scene dargestellt werden soll.
	 *
	 * @param primaryStage
	 * @return
	 */
	private Pane getPane(Stage primaryStage) {

		final Pane root = new Pane();

		// Label - Name
		final Label lbl_name = new Label("Name:");
		lbl_name.setLayoutY(30);
		lbl_name.setLayoutX(20);

		// InputField - Name
		final TextField tf_name = new TextField();
		tf_name.setLayoutY(50);
		tf_name.setLayoutX(20);

		// Label - Number
		final Label lbl_number = new Label("Number:");
		lbl_number.setLayoutY(30);
		lbl_number.setLayoutX(220);

		// InputField - Number
		final TextField tf_number = new TextField();
		tf_number.setLayoutX(220);
		tf_number.setLayoutY(50);

		// Search Button
		final Button btn_search = new Button("Search");
		btn_search.setLayoutX(330);
		btn_search.setLayoutY(80);

		root.getChildren().addAll(lbl_name, tf_name, lbl_number, tf_number, btn_search);

		// ________________________search-Action____________________________________
		btn_search.setOnAction(event -> {

			// if there is any input: check which thread is to be started
			// the threads write to the same threadsafe List
				if (!tf_name.getText().isEmpty() || !tf_number.getText().isEmpty()) {
					Thread t1 = null;
					Thread t2 = null;
					// text-thread
					if (!tf_name.getText().isEmpty()) {
						if ((tf_name.getText().matches("^[a-zA-ZäöüÄÖÜ]+[a-zA-ZäöüÄÖÜ\\s]*"))) {
							t1 = new Thread(new Search(tf_name.getText(), 0, results), "search-name");
							t1.start();
						} else {
							System.err.println("Im Namens-Feld dürfen nur gültige Buchstaben verwendet werden.");
						}
					}
					// number thread
					if (!tf_number.getText().isEmpty()) {
						if ((tf_number.getText().matches("\\d+"))) {
							t2 = new Thread(new Search(tf_number.getText(), 1, results), "search-number");
							t2.start();
						} else {
							System.err.println("Im Nummern-Feld dürfen nur gültige Zahlen verwendet werden.");
						}
					}

					// wait for threads
					try {
						if (t1 != null) {
							t1.join();
						}
						if (t2 != null) {
							t2.join();
						}
					} catch (final Exception e) {
						e.printStackTrace();
					}

					// print all results
					for (final String[] row : results) {
						System.out.print(row[0]);
						if (row.length > 1) {
							System.out.print(", " + row[1]);
						}
						System.out.println();
					}

					// clear List
					results.clear();

				} else {
					System.err.println("Die Felder dürfen nicht leer sein !");
				}
			});
		return root;
	}
}