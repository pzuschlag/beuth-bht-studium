package jUnit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import model.Appointment;
import model.CSVAppointmentReader;
import model.CSVAppointmentWriter;

import org.junit.Before;
import org.junit.Test;

public class CSVAppointmentWriterTest {
	List<Appointment> appointments = new ArrayList<>();
	List<Appointment> appointmentsRead;

	@Before
	public void setUp() throws Exception {
		appointments.add(new Appointment("Zuschi", "Phil", "2010-07-10", "Rotenburg"));
		appointments.add(new Appointment("Waldo", "Charlie", "2010-07-10", "CHB"));
		appointments.add(new Appointment("Röschen", "Leon", "2010-07-10", "Berlin"));

		CSVAppointmentWriter.writeEntityList(appointments, "resources/jUnit/appointments.csv", ":");

		appointmentsRead = CSVAppointmentReader.readEntityList("resources/jUnit/appointments.csv", ":");
	}

	@Test
	public void testBeschreibung() {
		for (int i = 0; i < appointments.size(); i++) {
			assertEquals(appointments.get(i).getTerminbeschreibung(), appointmentsRead.get(i).getTerminbeschreibung());
		}
	}

	@Test
	public void testBezeichnung() {
		for (int i = 0; i < appointments.size(); i++) {
			assertEquals(appointments.get(i).getTerminbezeichnung(), appointmentsRead.get(i).getTerminbezeichnung());
		}
	}

	@Test
	public void testDatum() {
		for (int i = 0; i < appointments.size(); i++) {
			assertEquals(appointments.get(i).getDatum(), appointmentsRead.get(i).getDatum());
		}

	}

	@Test
	public void testOrt() {
		for (int i = 0; i < appointments.size(); i++) {
			assertEquals(appointments.get(i).getOrt(), appointmentsRead.get(i).getOrt());
		}
	}
}