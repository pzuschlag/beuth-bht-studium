package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BufferedAppointmentReader {

	@SuppressWarnings("resource")
	public static List<Appointment> readEntityList(String dateiname, String splitter) {

		BufferedReader input;
		String inputString = null;

		try {

			input = new BufferedReader(new InputStreamReader(new FileInputStream(dateiname)));
			inputString = input.readLine();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Appointment> target = new ArrayList<Appointment>();
		String[] attribute = inputString.split(splitter);

		for (int i = 0; i < attribute.length; i = i + 4) {
			target.add(new Appointment(attribute[i], attribute[i + 1], attribute[i + 2], attribute[i + 3]));
		}

		return target;
	}
}
