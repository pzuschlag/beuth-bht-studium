package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;
import model.ObservableContactDetails;
import exceptions.NoEntrySelectedException;



public class Control {


	private ObservableList<ObservableContactDetails> contactData = FXCollections.observableArrayList();

	/**
	 * Attribute
	 */

	// ListView
	@FXML
	private ListView<String> contactList;

	// TableView
	@FXML
	private TableView<ObservableContactDetails> contactTable;
	@FXML
	private TableColumn<ObservableContactDetails, String> firstNameColumn;
	@FXML
	private TableColumn<ObservableContactDetails, String> lastNameColumn;
	@FXML
	private TableColumn<ObservableContactDetails, String> numberColumn;
	@FXML
	private TableColumn<ObservableContactDetails, String> addressColumn;

	// TreeView
	@FXML
	private TreeView<String> contactTree;

	// Drucken Button
	@FXML
	private Button btn_print;

	// Neuer Eintrag Button
	@FXML
	private Button btn_newEntry;

	// Eintrag löschen Button
	@FXML
	private Button btn_deleteEntry;


	/**
	 * Constructor
	 */
	public Control() {
		contactData.add(new ObservableContactDetails("Vorname1", "Nachname1", "Wohnort1", "Tel1"));
		contactData.add(new ObservableContactDetails("Vorname2", "Nachname2", "Wohnort2", "Tel2"));
		contactData.add(new ObservableContactDetails("Vorname3", "Nachname3", "Wohnort3", "Tel3"));
		contactData.add(new ObservableContactDetails("Vorname4", "Nachname4", "Wohnort4", "Tel4"));
		contactData.add(new ObservableContactDetails("Vorname5", "Nachname5", "Wohnort5", "Tel5"));
		contactData.add(new ObservableContactDetails("Vorname6", "Nachname6", "Wohnort6", "Tel6"));
	}


	/**
	 * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		// TableView
		initializeTableView();
		initializeTableViewEditable();

		// ListView
		initializeListView();
		initializeListViewEditable();

		// TreeView
		initializeTreeView();
	}


	/**
	 * initialisiert die TableView
	 */
	private void initializeTableView() {
		contactTable.setItems(contactData);

		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
		addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());

	}


	/**
	 * Macht die TableView editierbar
	 */
	private void initializeTableViewEditable() {
		lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameColumn.setOnEditCommit(new EventHandler<CellEditEvent<ObservableContactDetails, String>>() {


			@Override
			public void handle(CellEditEvent<ObservableContactDetails, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setLastName(event.getNewValue());
				initializeListView();
				initializeTreeView();
			}
		});

		firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		firstNameColumn.setOnEditCommit(new EventHandler<CellEditEvent<ObservableContactDetails, String>>() {


			@Override
			public void handle(CellEditEvent<ObservableContactDetails, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow())
						.setFirstName(event.getNewValue());
				initializeTreeView();
			}
		});

		numberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		numberColumn.setOnEditCommit(new EventHandler<CellEditEvent<ObservableContactDetails, String>>() {


			@Override
			public void handle(CellEditEvent<ObservableContactDetails, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setNumber(event.getNewValue());
				initializeTreeView();
			}
		});

		addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		addressColumn.setOnEditCommit(new EventHandler<CellEditEvent<ObservableContactDetails, String>>() {


			@Override
			public void handle(CellEditEvent<ObservableContactDetails, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setAddress(event.getNewValue());
				initializeTreeView();
			}
		});

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
	 * Macht die ListView editierbar
	 */
	private void initializeListViewEditable() {

		contactList.setCellFactory(TextFieldListCell.forListView());

		contactList.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {


			@Override
			public void handle(ListView.EditEvent<String> event) {
				contactList.getItems().set(event.getIndex(), event.getNewValue());
				contactData.get(event.getIndex()).setLastName(event.getNewValue());
				initializeTreeView();
			}
		});
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


	/**
	 * Druckt die Vorliegende Observable List auf der Console
	 */
	@FXML
	private void print() {

		for (ObservableContactDetails contact : contactData) {

			System.out.println(contact.getLastName() + ",\t" + contact.getFirstName() + ",\t" + contact.getNumber()
					+ ",\t" + contact.getAddress());
		}
		System.out.println("----------------------------------------------");
	}


	/**
	 * Fügt einen neuen Eintrag in die Listen hinzu.
	 */
	@FXML
	private void newEntry() {
		contactData.add(new ObservableContactDetails("-", "-", "-", "-"));
		initializeListView();
		initializeTreeView();
	}


	/**
	 * Entfernt den aktuell selektierten Eintrag aus der Liste.
	 */
	@FXML
	private void deleteEntry() {
		int selectedIndex = contactTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			contactTable.getItems().remove(selectedIndex);
			initializeListView();
			initializeTableView();
		} else {
			try {
				throw new NoEntrySelectedException("Kein Eintrag ausgew�hlt!");
			} catch (NoEntrySelectedException e) {
				System.err.println(e);
			}
		}
	}
}