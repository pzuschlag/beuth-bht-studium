package model;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;



public class BinAppointmentWriter {


	/**
	 * Der Inhalt der Appointmentsliste wird zu einem langen String verkettet, zum binären Format umkodiert und der
	 * OutputStream wird geschlossen und der Speicher wieder freigegben.
	 * <ul>
	 * 
	 * Erstellt ein neues Objekt output, vom Typ DataOuputStream. Als Parameter werden bei diesem Objekt die Objekte
	 * BufferedOutputStream und FileOutputStream mitgegeben, diese werden bei dem Aufrufen der Methode erstellt.
	 * 
	 * Es wird ein leerer String outputString deklariert. Mit einer erweiterten For-Schleife wird durch appointment
	 * iteriert und mithilfe der Methode appointmentAsBinLine alle Termine in einem String hintereinandergeschrieben.
	 * Mithilfe von output.writeUTF wird die Stringkette in Binärcode kodiert-, mit flush aus dem Cache zum
	 * Hauptsspeicher geholt und mit close wird schließelich der Outputstream geschlossen und die Ressourcen werden
	 * wieder freigegeben.
	 * 
	 * 
	 * @param appointments
	 * @param filename
	 * @param splitter
	 * @throws IOException
	 */
	public static void writeEntityList(List<Appointment> appointments, String filename, String splitter)
			throws IOException {

		DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));

		String outputString = "";
		for (Appointment appointment : appointments) {
			outputString = outputString + appointmentAsBinLine(appointment, splitter);
		}

		output.writeUTF(outputString);
		output.flush(); // Glaub beimm Bufferd brauchen wir den nicht
		output.close();
	}


	/**
	 * Gibt den Inhalt eines bestimmten Arrays mit dem Index a als Stringkette zurück
	 * 
	 * @param a
	 * @param splitter
	 * @return
	 */
	public static String appointmentAsBinLine(Appointment a, String splitter) {
		return a.getTerminbezeichnung() + splitter + a.getTerminbeschreibung() + splitter + a.getDatum() + splitter
				+ a.getOrt() + splitter;
	}
}