package model;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class BufferedAppointmentWriter {

	public static void writeEntityList(List<Appointment> appointments, String filename, String splitter) throws IOException {

		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));

		for (Appointment appointment : appointments) {
			output.write(appointmentAsCSVLine(appointment, splitter));
		}

		output.flush();
		output.close();
	}

	/**
	 * Gibt den Inhalt eines bestimmten Arrays mit dem Index a als Stringkette zur�ck
	 * 
	 * @param a
	 * @param splitter
	 * @return
	 */
	public static String appointmentAsCSVLine(Appointment a, String splitter) {
		return a.getTerminbezeichnung() + splitter + a.getTerminbeschreibung() + splitter + a.getDatum() + splitter + a.getOrt() + splitter;
	}
}