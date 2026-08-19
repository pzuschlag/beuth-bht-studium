import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Scribble_IngaBert extends Application {

	@Override
	public void start(Stage primaryStage) {
		root = new BorderPane();
		Scene scene = new Scene(root, 600, 450);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Scribble");

		HBox menu = new HBox(15);

		Label label1 = new Label("Strichstaerke");
		strichStaerke = new Slider(1.0, 20.0, 1.0);
		strichStaerke.setShowTickLabels(true);

		Label label2 = new Label("Farbe");
		farbe = new ColorPicker(Color.CORAL);

		Button loeschen = new Button("Alles loeschen");

		menu.getChildren().addAll(label1, strichStaerke, label2, farbe, loeschen);
		root.setTop(menu);

		panel = new Rectangle(600, 400, Color.WHITESMOKE);
		group = new Group(panel);
		root.setCenter(group);

		group.setOnMousePressed(new LineStarter());
		group.setOnMouseDragged(new DragPainter());
		loeschen.setOnMouseReleased(new Delete());

		primaryStage.show();
	}

	private BorderPane root;
	private Group group;
	private double x, y, lastX, lastY;
	private ColorPicker farbe;
	private Slider strichStaerke;
	private Rectangle panel;

	private class LineStarter implements EventHandler<MouseEvent> {
		public void handle(MouseEvent event) {
			x = event.getX();
			y = event.getY();
			// root.getChildren().add(new Circle(x, y, 3, Color.BLUE));
		}
	}

	private class DragPainter implements EventHandler<MouseEvent> {
		public void handle(MouseEvent event) {
			lastX = x;
			lastY = y;
			x = event.getX();
			y = event.getY();
			Line line = new Line(x, y, lastX, lastY);
			line.setStrokeWidth(strichStaerke.getValue());
			line.setStroke(farbe.getValue());
			group.getChildren().add(line);
		}
	}

	private class Delete implements EventHandler<MouseEvent> {
		public void handle(MouseEvent event) {
			group.getChildren().clear();
			panel = new Rectangle(600, 400, Color.WHITESMOKE);
			group.getChildren().add(panel);
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

}
