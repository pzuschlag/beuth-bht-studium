package T1;

import java.util.GregorianCalendar;
import java.util.Scanner;

public class AlterInTagen {

	public static void main(String[] args) {

		int tag;
		int monat;
		int jahr;

		Scanner scan = new Scanner(System.in); // InputStream

		System.out.print("Tag: ");
		tag = scan.nextInt();
		System.out.print("Monat: ");
		monat = scan.nextInt();
		System.out.print("Jahr: ");
		jahr = scan.nextInt();

		GregorianCalendar today = new GregorianCalendar();
		GregorianCalendar birthdate = new GregorianCalendar(jahr, monat, tag);

		System.out.println("Alter in Tagen: " + ((today.getTimeInMillis() - birthdate.getTimeInMillis()) / (24 * 60 * 60 * 1000)));

	}
}
