package aufgabe1.gui.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import aufgabe1.gui.model.ContactDetails;

public class EditDialogController {

	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField nummerField;
	@FXML
	private TextField mailField;
	@FXML
	private TextField addressField;

	private Stage dialogStage;
	private ContactDetails contact;

	/**
	 * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setContact(ContactDetails contact) {
		this.contact = contact;

		firstNameField.setText(contact.getFirstName());
		lastNameField.setText(contact.getLastName());
		nummerField.setText(contact.getNumber());
		mailField.setText(contact.getMail());
		addressField.setText(contact.getAddress());
	}

	@FXML
	private void clickOk() {
		contact.setFirstName(firstNameField.getText());
		contact.setLastName(lastNameField.getText());
		contact.setNumber(nummerField.getText());
		contact.setAddress(addressField.getText());
		contact.setMail(mailField.getText());

		dialogStage.close();
	}

	@FXML
	private void clickCancel() {
		dialogStage.close();
	}

}
