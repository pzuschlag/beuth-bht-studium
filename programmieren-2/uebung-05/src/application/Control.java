package application;

import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Appointment;
import model.BufferedAppointmentReader;
import model.BufferedAppointmentWriter;
import model.CSVAppointmentReader;
import exceptions.NoEntrySelectedException;

public class Control {

	public static ObservableList<Appointment> appointmentData;

	/**
	 * FXML Attribute
	 */
	// TableView
	@FXML
	private TableView<Appointment> tableView;
	@FXML
	private TableColumn<Appointment, String> col_designation;
	@FXML
	private TableColumn<Appointment, String> col_description;
	@FXML
	private TableColumn<Appointment, LocalDate> col_date;
	@FXML
	private TableColumn<Appointment, String> col_place;

	// Drucken Button
	@FXML
	private Button btn_print;

	// Neuer Eintrag Button
	@FXML
	private Button btn_newEntry;

	// Eintrag lšschen Button
	@FXML
	private Button btn_deleteEntry;

	// Write-Button
	@FXML
	private Button btn_write;

	// Read-Button
	@FXML
	private Button btn_read;

	/**
	 * Constructor
	 */
	public Control() {
		appointmentData = FXCollections.observableArrayList(CSVAppointmentReader.readEntityList("resources/csv/appointments.csv", ","));
	}

	/**
	 * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		// TableView
		initializeTableView();
		initializeTableViewEditable();

	}

	/**
	 * initialisiert die TableView
	 */
	private void initializeTableView() {
		tableView.setItems(appointmentData);

		col_designation.setCellValueFactory(cellData -> cellData.getValue().terminbezeichnung());
		col_description.setCellValueFactory(cellData -> cellData.getValue().terminbeschreibung());
		col_date.setCellValueFactory(cellDate -> cellDate.getValue().datum());
		col_place.setCellValueFactory(cellData -> cellData.getValue().ort());

	}

	/**
	 * Macht die TableView editierbar
	 */
	private void initializeTableViewEditable() {
		col_designation.setCellFactory(TextFieldTableCell.forTableColumn());
		col_designation.setOnEditCommit(new EventHandler<CellEditEvent<Appointment, String>>() {

			@Override
			public void handle(CellEditEvent<Appointment, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setTerminbezeichnung(event.getNewValue());
			}
		});

		col_description.setCellFactory(TextFieldTableCell.forTableColumn());
		col_description.setOnEditCommit(new EventHandler<CellEditEvent<Appointment, String>>() {

			@Override
			public void handle(CellEditEvent<Appointment, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setTerminbeschreibung(event.getNewValue());
			}
		});

		// col_date.setCellFactory(TextFieldTableCell.forTableColumn());
		// col_date.setOnEditCommit(new EventHandler<CellEditEvent<Appointment, String>>() {

		// @Override
		// public void handle(CellEditEvent<Appointment, String> event) {
		// TODO: Date verwenden
		// event.getTableView().getItems().get(event.getTablePosition().getRow()).setDatum(event.getNewValue());
		// }
		// });

		col_place.setCellFactory(TextFieldTableCell.forTableColumn());
		col_place.setOnEditCommit(new EventHandler<CellEditEvent<Appointment, String>>() {

			@Override
			public void handle(CellEditEvent<Appointment, String> event) {
				event.getTableView().getItems().get(event.getTablePosition().getRow()).setOrt(event.getNewValue());
			}
		});
	}

	/**
	 * Druckt die Vorliegende Observable List auf der Console
	 */
	@FXML
	private void printEntry() {

		for (Appointment appointment : appointmentData) {

			System.out.println(appointment.getTerminbezeichnung() + ",\t" + appointment.getTerminbeschreibung() + ",\t"
					+ appointment.getDatum() + ",\t" + appointment.getOrt());
		}
		System.out.println("----------------------------------------------");
	}

	/**
	 * FŸgt einen neuen Eintrag in die Listen hinzu.
	 */
	@FXML
	private void newEntry() {
		appointmentData.add(new Appointment("-", "-", LocalDate.now().toString(), "-"));
	}

	/**
	 * Entfernt den aktuell selektierten Eintrag aus der Liste.
	 */
	@FXML
	private void deleteEntry() {
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			tableView.getItems().remove(selectedIndex);
		} else {
			try {
				throw new NoEntrySelectedException("Kein Eintrag ausgewaehlt!");
			} catch (NoEntrySelectedException e) {
				System.err.println(e);
			}
		}
	}

	/**
	 * Schreibt mithilfe der Klasse BinAppointmentWriter die appointmentData in Binärkodierung in den angegebenen Pfad.
	 */
	@FXML
	private void writeEntry() {

		try {
			// BinAppointmentWriter.writeEntityList(appointmentData, "resources/bin/appointments.bin", ",");
			// System.out.println("BIN wurde geschrieben!");
			// CSVAppointmentWriter.writeEntityList(appointmentData, "resources/csv/appointments.csv", ",");
			// System.out.println("CSV wurde geschrieben!");
			BufferedAppointmentWriter.writeEntityList(appointmentData, "resources/buf/appointments.csv", ",");
			System.out.println("BUFF wurde geschrieben!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Holt sich die geschriebene CSV-Datei und speichert sie als oberservableArrayList in appointmentData. Danach kann
	 * die TableView initialisert werden.
	 */
	@FXML
	private void readEntry() {

		// appointmentData =
		// FXCollections.observableArrayList(BinAppointmentReader.readEntityList("resources/bin/appointments.bin",
		// ","));
		// System.out.println("BIN wurde gelesen!");
		// appointmentData =
		// FXCollections.observableArrayList(CSVAppointmentReader.readEntityList("resources/csv/appointments.csv",
		// ","));
		// System.out.println("CSV wurde gelesen!");
		appointmentData = FXCollections
				.observableArrayList(BufferedAppointmentReader.readEntityList("resources/buf/appointments.csv", ","));
		System.out.println("BUFF wurde gelesen!");

		initializeTableView();
	}
}