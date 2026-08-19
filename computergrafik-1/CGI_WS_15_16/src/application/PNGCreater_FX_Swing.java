package src.application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class PNGCreater_FX_Swing extends Application {

	private BufferedImage bufImg;

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("PNG Creator");

		final Menu fileMenu = new Menu("File");

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(fileMenu);

		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

		MenuItem save = new MenuItem("Save");
		fileMenu.getItems().add(save);

		Group group = new Group();

		Image image = SwingFXUtils.toFXImage(this.drawImage(640, 480), null);
		ImageView imgView = new ImageView();
		imgView.setImage(image);

		save.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save Image");
			File file = fileChooser.showSaveDialog(primaryStage);
			if (file != null) {
				try {
					ImageIO.write(bufImg, "png", file);
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
			}
		});

		primaryStage.heightProperty().addListener((ChangeEvent) -> {
			resetImage(primaryStage, group);
		});
		primaryStage.widthProperty().addListener((ChangeEvent) -> {
			resetImage(primaryStage, group);
		});

		group.getChildren().addAll(imgView, menuBar);

		primaryStage.setScene(new Scene(group));

		primaryStage.show();

	}

	public void resetImage(Stage stage, Group group) {
		Image image = SwingFXUtils.toFXImage(
				drawImage(((int) stage.getWidth()), ((int) stage.getHeight())),
				null);
		ImageView imgView = new ImageView();
		imgView.setImage(image);
		group.getChildren().addAll(imgView);
	}

	public BufferedImage drawImage(Integer width, Integer height) {

		this.bufImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		this.bufImg.getGraphics().setColor(Color.BLACK);

		drawLine();

		return bufImg;
	}

	public void drawLine() {

		WritableRaster raster = this.bufImg.getRaster();
		ColorModel cModel = this.bufImg.getColorModel();

		for (int x = 0; x < this.bufImg.getWidth(); x++) {
			for (int y = 0; y < this.bufImg.getHeight(); y++) {
				if (x == y)
					raster.setDataElements(x, y,
							cModel.getDataElements(Color.RED.getRGB(), null));
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}