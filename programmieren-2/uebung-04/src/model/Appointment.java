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


	// ***Datum*** //
	private ObjectProperty<LocalDate> datum = new SimpleObjectProperty<LocalDate>();


	public LocalDate getDatum() {
		return datum.get();
	}


	public void setDatum(LocalDate date) {
		this.datum.set(date);
	}

	// ***Startuhrzeit*** //
	private ObjectProperty<LocalTime> startUhrzeit = new SimpleObjectProperty<LocalTime>();


	public LocalTime getStartUhrzeit() {
		return startUhrzeit.get();

	}


	public void setStartUhrzeit(LocalTime startTime) throws ImpossibleTimeException {
		this.startUhrzeit.set(startTime);
		setDauer();
	}

	// ***Enduhrzeit*** //
	private ObjectProperty<LocalTime> endUhrzeit = new SimpleObjectProperty<LocalTime>();


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

	// ***Dauer*** //
	public long dauer;


	public long getDauer() {
		return dauer;
	}


	public long setDauer() throws ImpossibleTimeException {

		dauer = Duration.between(startUhrzeit.get(), endUhrzeit.get()).toMinutes();
		if (dauer <= 0)
			throw new ImpossibleTimeException("Endzeitpunkt liegt vor dem Startzeitpunkt!");
		return dauer;
	}

	// ***Terminkategorie*** //
	private StringProperty terminkategorie = new SimpleStringProperty();


	public String getTerminkategorie() {
		return terminkategorie.get();
	}


	public void setTerminkategorie(String terminkategorie) {
		this.terminkategorie.set(terminkategorie);
	}


	public StringProperty terminkategorie() {
		return terminkategorie;
	}

	// ***Terminbezeichnung*** //
	private StringProperty terminbezeichnung = new SimpleStringProperty();


	public String getTerminbezeichnung() {
		return terminbezeichnung.get();
	}


	public void setTerminbezeichnung(String terminbezeichnug) {
		this.terminbezeichnung.set(terminbezeichnug);
	}


	public StringProperty terminbezeichnung() {
		return terminbezeichnung;
	}

	// ***Terminbeschreibung*** //
	private StringProperty terminbeschreibung = new SimpleStringProperty();


	public String getTerminbeschreibung() {
		return terminbeschreibung.get();
	}


	public void setTerminbeschreibung(String terminbeschreibung) {
		this.terminbeschreibung.set(terminbeschreibung);
	}


	public StringProperty terminbeschreibung() {
		return terminbeschreibung;
	}

	// ***Ort*** //
	private StringProperty ort = new SimpleStringProperty();


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