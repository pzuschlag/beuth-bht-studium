package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import exceptions.ImpossibleTimeException;



public class Appointment {


	/**
	 * Möglicher Konstruktor für die Klasse Appointment.
	 * 
	 * Setzt Referenzen für die übergebenen Parametern und nimmt als Uhrzeit die aktuelle Systemzeit
	 * 
	 * @param designation
	 * @param description
	 * @param date
	 * @param place
	 */
	public Appointment(String designation, String description, String date, String place) {
		setTerminbezeichnung(designation);
		setTerminbeschreibung(description);
		setDatum(LocalDate.parse(date));
		setOrt(place);
	}


	/**
	 * Möglicher Konstruktor fpr die Klasse Appointment.
	 * 
	 * Braucht als Parameter ein String-Array split. Läuft das Array für die Größe von split durch und benutzt die
	 * Setter, um den Attributen Werte zuzuweisen.
	 * 
	 * @param split
	 */
	public Appointment(String[] split) {
		for (int i = 0; i < split.length;) {
			setTerminbezeichnung(split[i]);
			i++;
			setTerminbeschreibung(split[i]);
			i++;
			setDatum(LocalDate.now());
			i++;
			setOrt(split[i]);
			i++;
		}
	}

	// ***Datum*** //
	private ObjectProperty<LocalDate> datum = new SimpleObjectProperty<LocalDate>();

	// ***Startuhrzeit*** //
	private ObjectProperty<LocalTime> startUhrzeit = new SimpleObjectProperty<LocalTime>();

	// ***Enduhrzeit*** //
	private ObjectProperty<LocalTime> endUhrzeit = new SimpleObjectProperty<LocalTime>();

	// ***Terminkategorie*** //
	private StringProperty terminkategorie = new SimpleStringProperty();

	// ***Terminbezeichnung*** //
	private StringProperty terminbezeichnung = new SimpleStringProperty();

	// ***Terminbeschreibung*** //
	private StringProperty terminbeschreibung = new SimpleStringProperty();

	// ***Ort*** //
	private StringProperty ort = new SimpleStringProperty();

	// ***Dauer***//
	private long dauer;

	public String getTerminkategorie() {
		return terminkategorie.get();
	}

	public void setTerminkategorie(String terminkategorie) {
		this.terminkategorie.set(terminkategorie);
	}

	public StringProperty terminkategorie() {
		return terminkategorie;
	}

	public String getTerminbezeichnung() {
		return terminbezeichnung.get();
	}

	public void setTerminbezeichnung(String terminbezeichnug) {
		this.terminbezeichnung.set(terminbezeichnug);
	}

	public StringProperty terminbezeichnung() {
		return terminbezeichnung;
	}

	public LocalDate getDatum() {
		return datum.get();
	}

	public ObjectProperty<LocalDate> datum() {
		return datum;
	}

	public void setDatum(LocalDate date) {
		this.datum.set(date);
	}

	public LocalTime getStartUhrzeit() {
		return startUhrzeit.get();
	}

	public void setStartUhrzeit(LocalTime startTime) throws ImpossibleTimeException {
		this.startUhrzeit.set(startTime);
		setDauer();
	}

	public LocalTime getEndUhrzeit() {
		return endUhrzeit.get();
	}

	public void setEndUhrzeit(LocalTime endTime) throws ImpossibleTimeException {
		this.endUhrzeit.set(endTime);
		setDauer();
	}

	public ObjectProperty endUhrzeit() {
		return endUhrzeit;
	}

	public long getDauer() {
		return dauer;
	}

	public long setDauer() throws ImpossibleTimeException {

		dauer = Duration.between(startUhrzeit.get(), endUhrzeit.get()).toMinutes();
		if (dauer <= 0)
			throw new ImpossibleTimeException("Endzeitpunkt liegt vor dem Startzeitpunkt!");
		return dauer;
	}

	public String getTerminbeschreibung() {
		return terminbeschreibung.get();
	}

	public void setTerminbeschreibung(String terminbeschreibung) {
		this.terminbeschreibung.set(terminbeschreibung);
	}

	public StringProperty terminbeschreibung() {
		return terminbeschreibung;
	}

	public String getOrt() {
		return ort.get();
	}

	public void setOrt(String ort) {
		this.ort.set(ort);
	}

	public StringProperty ort() {
		return ort;
	}
}