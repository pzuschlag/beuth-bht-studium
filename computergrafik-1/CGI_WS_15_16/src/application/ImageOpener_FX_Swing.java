package src.application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class ImageOpener_FX_Swing extends Application {

	private BufferedImage bufImg;

	@Override
	public void start(Stage openDialog) throws Exception {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Image Files", "*.png",
						"*.jpg", "*.gif"));

		File selectedFile = fileChooser.showOpenDialog(openDialog);

		if (selectedFile != null) {
			initializeViwer(selectedFile);
		}

	}

	private void initializeViwer(File file) {

		Stage viewer = new Stage();
		Pane pane = new Pane();

		Image image = SwingFXUtils.toFXImage(this.openImage(file), null);
		ImageView imgView = new ImageView();
		imgView.setImage(image);

		pane.getChildren().add(imgView);

		viewer.setScene(new Scene(pane));
		viewer.show();
	}

	public BufferedImage openImage(File file) {

		try {
			this.bufImg = ImageIO.read(file);
		} catch (IOException e) {
		}

		return bufImg;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
