package src.application;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class PNGCreater_FX extends Application {

	private WritableImage wrImg;
	Text label;

	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("PNG Creator");

		final Menu fileMenu = new Menu("File");
		HBox hBox = new HBox();
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(fileMenu);

		menuBar.prefWidthProperty()
				.bind(primaryStage.widthProperty().divide(2));

		MenuItem save = new MenuItem("Save");
		fileMenu.getItems().add(save);
		label = new Text();
		label.setFill(Color.WHITE);
		Group group = new Group();

		ImageView imgView = new ImageView();
		imgView.setImage(drawImage(640, 480));

		save.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Image");
			File file = fileChooser.showSaveDialog(primaryStage);
			if (file != null) {
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(wrImg, null), "png",
							file);
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
			}
		});

		group.getChildren().addAll(imgView, hBox);
		hBox.getChildren().addAll(menuBar, label);

		primaryStage.setScene(new Scene(group));

		/**
		 * Über einen AddListener an der HeightProperty und der WidthProperty
		 * der primaryStage wird das neu Zeichnen des Bildes aufgerufen
		 */

		primaryStage.heightProperty().addListener((ChangeEvent) -> {
			redrawImage(imgView, primaryStage);
		});
		primaryStage.widthProperty().addListener((ChangeEvent) -> {
			redrawImage(imgView, primaryStage);
		});

		primaryStage.show();

	}

	/**
	 * Gibt, nach Eingangstest ob die Werte(Höhe, Breite) größer als 0 sind,
	 * diese an drawImage weiter.
	 *
	 * @author Johann
	 * @param imgView
	 *            ImageView Objekt des Bildes
	 * @param primaryStage
	 *            Main Stage
	 */
	private void redrawImage(ImageView imgView, Stage primaryStage) {

		double width = primaryStage.getWidth();
		double height = primaryStage.getHeight();

		if (width > 0 & height > 0) {
			imgView.setImage(drawImage(width, height));
		}
	}

	/**
	 * Geht von oben nach unten jedes Pixel durch und gibt diesen Farbe
	 * 
	 * @author Phil
	 * @param width
	 *            Breite des Bilds
	 * @param height
	 *            Höhe des Bilds
	 * @return Liefert ein Bild mit einer Diagonalen.
	 */
	private WritableImage drawImage(double width, double height) {

		wrImg = new WritableImage(((int) width), ((int) height));

		long start = System.nanoTime();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				wrImg.getPixelWriter().setColor(x, y, getColor(x, y));
			}
		}
		long end = System.nanoTime();
		label.setText((" Rendering Time: " + (end - start) / 1000000000.0F));
		return wrImg;
	}
	/**
	 * Liefert den Farbwert zurück um eine Diagonale Linie in einem schwarzen
	 * Bild zu malen.
	 * 
	 * @author Phil
	 * @param x
	 *            , X Coordinate eines Pixels im Bild
	 * @param y
	 *            Y Coordinate eines Pixels im Bild
	 * @return Farbe wie das Pixel eingefärbt werden soll
	 */
	private Color getColor(int x, int y) {

		if (x == y) {
			return Color.RED;
		}
		return Color.BLACK;
	}

	/**
	 * Start-Methode für FX
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}