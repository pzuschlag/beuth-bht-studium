import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.CSVAppointmentWriter;
import application.Control;

public class MainApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("view/calendar.fxml"));

		primaryStage.setTitle("Calendar");
		primaryStage.getIcons().add(new Image("file:resources/images/calendar.png"));
		primaryStage.setScene(new Scene(root));

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				try {
					CSVAppointmentWriter.writeEntityList(Control.appointmentData, "resources/csv/appointments.csv", ",");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}