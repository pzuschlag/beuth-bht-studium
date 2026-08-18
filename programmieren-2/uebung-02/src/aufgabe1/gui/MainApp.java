package aufgabe1.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import aufgabe1.gui.model.ContactDetails;
import aufgabe1.gui.view.AddressBookController;
import aufgabe1.gui.view.EditDialogController;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressBook");

		this.primaryStage.getIcons().add(new Image("file:resources/images/address_book.png"));

		showAddressBook();

		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void showAddressBook() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/AddressBook.fxml"));

			rootLayout = (BorderPane) loader.load();

			AddressBookController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showContactEditDialog(ContactDetails contact) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL); // Was das ??
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			EditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setContact(contact);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait(); // Notwendigkeit prüfen ?

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}