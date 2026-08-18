package aufgabe2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import aufgabe2.model.ContactDetails;

public class AddressBookController {

	// Liste mit Key-Value-Paaren
	private ObservableList<ContactDetails> contactData = FXCollections.observableArrayList();

	// ListView
	@FXML
	private ListView<String> contactList;

	// TableView
	@FXML
	private TableView<ContactDetails> contactTable;
	@FXML
	private TableColumn<ContactDetails, String> firstNameColumn;
	@FXML
	private TableColumn<ContactDetails, String> lastNameColumn;
	@FXML
	private TableColumn<ContactDetails, String> numberColumn;
	@FXML
	private TableColumn<ContactDetails, String> addressColumn;

	// TreeView
	@FXML
	private TreeView<String> contactTree;

	/**
	 * Constructor
	 */
	public AddressBookController() {
		contactData.add(new ContactDetails("Vorname1", "Nachname1", "Wohnort1", "Tel1"));
		contactData.add(new ContactDetails("Vorname2", "Nachname2", "Wohnort2", "Tel2"));
		contactData.add(new ContactDetails("Vorname3", "Nachname3", "Wohnort3", "Tel3"));
		contactData.add(new ContactDetails("Vorname4", "Nachname4", "Wohnort4", "Tel4"));
		contactData.add(new ContactDetails("Vorname5", "Nachname5", "Wohnort5", "Tel5"));
		contactData.add(new ContactDetails("Vorname6", "Nachname6", "Wohnort6", "Tel6"));
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
		addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());

		// TableView
		initializeTableView();

		// ListView
		initializeListView();

		// TreeView
		initializeTreeView();
	}

	/**
	 * initialisiert die TableView
	 */
	private void initializeTableView() {
		contactTable.setItems(contactData);
	}

	/**
	 * initialisert die ListView
	 */
	private void initializeListView() {
		ObservableList<String> contactKeys = FXCollections.observableArrayList();
		for (int i = 0; i < contactData.size(); i++) {
			contactKeys.add(contactData.get(i).getLastName());
		}
		contactList.setItems(contactKeys);
	}

	/**
	 * initialisiert die TreeView
	 */
	@SuppressWarnings("unchecked")
	private void initializeTreeView() {
		TreeItem<String> root = new TreeItem<>("AddressBook");
		for (int i = 0; i < contactData.size(); i++) {

			TreeItem<String> branch = new TreeItem<>(contactData.get(i).getLastName());

			TreeItem<String> leafeFN = new TreeItem<>(contactData.get(i).getFirstName());
			TreeItem<String> leafeLN = new TreeItem<>(contactData.get(i).getLastName());
			TreeItem<String> leafeNum = new TreeItem<>(contactData.get(i).getNumber());
			TreeItem<String> leafeAdd = new TreeItem<>(contactData.get(i).getAddress());

			branch.getChildren().addAll(leafeFN, leafeLN, leafeNum, leafeAdd);
			root.getChildren().add(branch);
		}
		contactTree.setRoot(root);
	}
}
