import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Scribble extends Application {

	private BorderPane root;
	private Group group;
	private double x, y, lastX, lastY;
	private ColorPicker picker;
	private ColorPicker pickerBG;
	private Slider sld_lineStrength;
	private Rectangle panel;
	private Color pnl_Background = Color.WHITE;

	@Override
	public void start(Stage primaryStage) {
		root = new BorderPane();

		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Scribble");

		VBox leftPanel = new VBox();
		leftPanel.setPrefWidth(150);

		// Leftpanel
		{
			Label lbl_bgColor = new Label("Hintergrundfarbe");
			leftPanel.getChildren().add(lbl_bgColor);
			pickerBG = new ColorPicker(pnl_Background);
			leftPanel.getChildren().add(pickerBG);
			pickerBG.setOnAction(new ChangeColor());

			Label lbl_color = new Label("Farbe");
			leftPanel.getChildren().add(lbl_color);
			picker = new ColorPicker(Color.TOMATO);
			leftPanel.getChildren().add(picker);

			Label lbl_lineStrength = new Label("Linien Stärke");
			leftPanel.getChildren().add(lbl_lineStrength);
			sld_lineStrength = new Slider(1.0, 10.0, 4.0);
			sld_lineStrength.setShowTickLabels(true);
			sld_lineStrength.setShowTickMarks(true);
			// sld_lineStrength.setMajorTickUnit(4);
			sld_lineStrength.setMinorTickCount(10);
			leftPanel.getChildren().add(sld_lineStrength);

			Label lbl_lineStyle = new Label("Linien Style");
			leftPanel.getChildren().add(lbl_lineStyle);
			ComboBox<String> cbb_lineStyle = new ComboBox<String>();
			cbb_lineStyle.setValue("Standart");
			cbb_lineStyle.getItems().addAll("Standart", "Gestrichelt", "Rund");
			leftPanel.getChildren().add(cbb_lineStyle);

			Button btn_clean = new Button("Clear");
			leftPanel.getChildren().add(btn_clean);
			btn_clean.setOnMouseClicked(new ClearStage());

			Button btn_undo = new Button("Undo");
			leftPanel.getChildren().add(btn_undo);
			// btn_undo.setOnAction(new Undo());
		}

		root.setLeft(leftPanel);

		panel = new Rectangle(450, 400, pnl_Background);
		group = new Group(panel);
		root.setCenter(group);

		group.setOnMousePressed(new LineStarter());
		group.setOnMouseDragged(new DragPainter());

		primaryStage.show();
	}

	private class LineStarter implements EventHandler<MouseEvent> {
		public void handle(MouseEvent event) {
			x = event.getX();
			y = event.getY();
		}
	}

	private class DragPainter implements EventHandler<MouseEvent> {
		public void handle(MouseEvent event) {
			lastX = x;
			lastY = y;
			x = event.getX();
			y = event.getY();
			Line line = new Line(x, y, lastX, lastY);
			line.setStrokeWidth(sld_lineStrength.getValue());
			line.setStroke(picker.getValue());
			// line.strokeProperty().bind(picker.valueProperty());
			double radius = sld_lineStrength.getValue();

			if ((panel.contains(x + radius, y + radius)) && (panel.contains(lastX + radius, lastY + radius))) {
				group.getChildren().add(line);
			}
		}
	}

	private class ClearStage implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			group.getChildren().clear();
			panel = new Rectangle(600, 400, pnl_Background);
			group.getChildren().add(panel);
		}
	}

	private class ChangeColor implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			pnl_Background = pickerBG.getValue();
			panel.setFill(pnl_Background);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
