package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVAppointmentReader {

	public static List<Appointment> readEntityList(String dateiname, String splitter) {
		Path source = Paths.get(dateiname);
		return readEntityList(source, splitter);
	}

	public static List<Appointment> readEntityList(Path source, String splitter) {
		List<Appointment> target = new ArrayList<>();
		try {
			List<String> lines = Files.readAllLines(source);
			for (String line : lines) {
				target.add(new Appointment(line.split(splitter)));
			}
		} catch (IOException ex) {
			target.addAll(null);
		}
		return target;
	}
}
