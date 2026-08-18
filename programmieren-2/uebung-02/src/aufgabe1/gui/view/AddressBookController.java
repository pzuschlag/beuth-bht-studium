package aufgabe1.gui.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import aufgabe1.gui.MainApp;
import aufgabe1.gui.model.ContactDetails;

public class AddressBookController {

	private ObservableList<ContactDetails> contactData = FXCollections.observableArrayList();

	@FXML
	private TextField searchField;
	@FXML
	private TableView<ContactDetails> contactTable;
	@FXML
	private TableColumn<ContactDetails, String> firstNameColumn;
	@FXML
	private TableColumn<ContactDetails, String> lastNameColumn;
	@FXML
	private TableColumn<ContactDetails, String> numberColumn;
	@FXML
	private TableColumn<ContactDetails, String> mailColumn;
	@FXML
	private TableColumn<ContactDetails, String> addressColumn;
	@FXML
	private Label noOfEntries;

	// Reference to the main application.
	private MainApp mainApp;

	public AddressBookController() {

		contactData.add(new ContactDetails("Philip", "Zusch", "Prenzelberg", "0173 714 888 2", "p-zuschlag@web.de"));
		contactData.add(new ContactDetails("Leon", "Rösler", "Steglitz", "01234423", "dirty-harry@hallo.com"));
		contactData.add(new ContactDetails("Robert", "Dzubia", "Köbenick", "0123 383823", "rooooobert@geißens.com"));
		contactData.add(new ContactDetails("Tobi", "Klatt", "Kreuzberg", "0128 281312", "Pferde-Hängst@youform.co"));
		contactData.add(new ContactDetails("Inga", "Schwa", "CHB", "0123 137823", "gluecksbärechi@himmel.de"));
		contactData.add(new ContactDetails("Julian", "Dil", "Tempelhof", "0123 13329", "hotdognachfüller@ikea.de"));
		contactData.add(new ContactDetails("Charli", "Wald", "CHB", "889234 23", "honey@honigtopg.goa"));
	}

	/**
	 * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
		mailColumn.setCellValueFactory(cellData -> cellData.getValue().mailProperty());
		addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		contactTable.setItems(getContactData());
	}

	@FXML
	private void changeContact() {
		ContactDetails selechtedContact = contactTable.getSelectionModel().getSelectedItem();

		mainApp.showContactEditDialog(selechtedContact);
	}

	@FXML
	private void newContact() {
		ContactDetails contact = new ContactDetails("", "", "", "", "");
		mainApp.showContactEditDialog(contact);
		getContactData().add(contact);
	}

	@FXML
	private void deleteContact() {
		int selectedIndex = contactTable.getSelectionModel().getSelectedIndex();
		contactTable.getItems().remove(selectedIndex);
	}

	@FXML
	private void searchContact() {

		ObservableList<ContactDetails> searchedContactTable = FXCollections.observableArrayList();

		System.out.println("Einagbe: " + searchField.getText().toLowerCase());

		if (!searchField.getText().isEmpty()) {
			for (ContactDetails cD : contactData) {
				if (cD.getFirstName().toLowerCase().contains(searchField.getText().toLowerCase())
						|| cD.getLastName().toLowerCase().contains(searchField.getText().toLowerCase())) {
					searchedContactTable.add(cD);
				}
			}
			setContactData(searchedContactTable);
		}
	}

	// private AddressBook contactData = new AddressBook();

	public ObservableList<ContactDetails> getContactData() {
		return contactData;
	}

	public void setContactData(ObservableList<ContactDetails> contactData) {
		this.contactData = contactData;
	}

	private void setNoOfEntries() {
		// noOfEntries.setText(String.valueOf(filteredData.size()));
	}

	// // 1. Wrap the ObservableList in a FilteredList (initially display all data).
	// FilteredList<ContactDetails> filteredData = new FilteredList<>(contactData, p -> true); // Was ist p -> true ?
	// // notewendig !!!
	//
	// // 2. Set the filter Predicate whenever the filter changes.
	// searchField.textProperty().addListener((observable, oldValue, newValue) -> {
	// filteredData.setPredicate(contact -> {
	// // If filter text is empty, display all persons.
	// if (newValue == null || newValue.isEmpty()) {
	// noOfEntries.setText(String.valueOf(filteredData.size()));
	// return true;
	// }
	//
	// // Compare first name and last name of every person with filter text.
	// String lowerCaseFilter = newValue.toLowerCase();
	//
	// if (contact.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
	// return true; // Filter matches first name.
	// } else if (contact.getLastName().toLowerCase().contains(lowerCaseFilter)) {
	// return true; // Filter matches last name.
	// }
	// return false; // Does not match.
	// });
	// });
	//
	// // 3. Wrap the FilteredList in a SortedList.
	// SortedList<ContactDetails> sortedData = new SortedList<>(filteredData);
	//
	// // 4. Bind the SortedList comparator to the TableView comparator.
	// sortedData.comparatorProperty().bind(contactTable.comparatorProperty());
	//
	// // 5. Add sorted (and filtered) data to the table.
	// contactTable.setItems(sortedData);

}
