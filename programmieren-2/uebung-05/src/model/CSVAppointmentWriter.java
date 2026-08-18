package model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVAppointmentWriter {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	public static void writeEntityList(List<Appointment> appointments, String filename, String splitter) throws IOException {
		Path path = Paths.get(filename);
		writeEntityList(appointments, path, splitter);

	}

	public static void writeEntityList(List<Appointment> appointments, Path path, String splitter) throws IOException {
		List<String> lines = new ArrayList<>();
		for (Appointment appointment : appointments) {
			lines.add(appointmentAsCSVLine(appointment, splitter));
		}
		Files.write(path, lines, ENCODING);
	}

	private static String appointmentAsCSVLine(Appointment a, String splitter) {
		return a.getTerminbezeichnung() + splitter + a.getTerminbeschreibung() + splitter + a.getDatum() + splitter + a.getOrt();
	}
}
