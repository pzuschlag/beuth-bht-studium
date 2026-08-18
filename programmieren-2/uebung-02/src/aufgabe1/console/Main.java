package aufgabe1.console;

import java.util.Scanner;

import aufgabe1.console.exceptions.KeyEmptyException;
import aufgabe1.console.exceptions.KeyNullException;
import aufgabe1.console.exceptions.ParameterDupblicateException;
import aufgabe1.console.exceptions.ParameterEmptyException;
import aufgabe1.console.exceptions.ParameterNullException;
import aufgabe1.console.model.AddressBook;
import aufgabe1.console.model.ContactDetails;

/**
 * 
 * @author Philip Zuschlag, Leon Rössler & Chaline Waldrich
 */
public class Main {

	private static Scanner scr = new Scanner(System.in);

	public static void main(String[] args) {
		AddressBook addB = new AddressBook();

		System.out.println("Addressbook");

		while (true) {
			System.out.println("What u wanne do ? \t\t\t\t\t(" + addB.getNumberOfEntries() + ") entries");
			System.out.println("Search (s), Add (a), Change (c), Delete (d), Show all ( ), Exit (e)");
			String in = scr.nextLine();

			if (in.toLowerCase().equals("e")) {
				System.out.println("thanks, bye ...");
				break;
			}

			switch (in) {
			case "s":
				search(addB);
				break;
			case "a":
				add(addB);
				break;
			case "c":
				change(addB);
				break;
			case "d":
				remove(addB);
				break;
			default:
				for (ContactDetails contact : addB.getAddressBook()) {
					System.out.println(contact.getLastName() + ",\t" + contact.getFirstName() + ",\t" + contact.getNumber() + ",\t"
							+ contact.getMail() + ",\t" + contact.getAddress());
				}
				break;
			}
			System.out.println("------------------------------------------------------------------");
			System.out.println("");
		}
	}

	private static void remove(AddressBook addB) {

		System.out.println("Which contact u want to remove ?");

		System.out.print("Lastname: ");
		String lastName = scr.nextLine();
		System.out.print("Firstname: ");
		String firstName = scr.nextLine();

		try {
			addB.removeDetails(lastName + firstName);
		} catch (KeyNullException | KeyEmptyException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Entry has been removed !");
	}

	private static void change(AddressBook addB) {

		System.out.println("Wich contact u want to change ?");

		System.out.print("Lastname: ");
		String oldLastName = scr.nextLine();
		System.out.print("Firstname: ");
		String oldFirstName = scr.nextLine();

		System.out.println("Now new details for the contact !");
		System.out.print("Lastname: ");
		String lastName = scr.nextLine();
		System.out.print("Firstname: ");
		String firstName = scr.nextLine();
		System.out.print("Number: ");
		String number = scr.nextLine();
		System.out.print("Mail: ");
		String mail = scr.nextLine();
		System.out.print("Address: ");
		String address = scr.nextLine();

		try {
			addB.changeDetails(oldLastName + oldFirstName, new ContactDetails(firstName, lastName, address, number, mail));
		} catch (ParameterNullException | ParameterEmptyException | ParameterDupblicateException | KeyNullException | KeyEmptyException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void add(AddressBook addB) {

		System.out.println("Add a new contact.");

		System.out.print("Lastname: ");
		String lastName = scr.nextLine();
		System.out.print("Firstname: ");
		String firstName = scr.nextLine();
		System.out.print("Number: ");
		String number = scr.nextLine();
		System.out.print("Mail: ");
		String mail = scr.nextLine();
		System.out.print("Address: ");
		String address = scr.nextLine();

		try {
			addB.addDetails(new ContactDetails(firstName, lastName, address, number, mail));
		} catch (ParameterNullException | ParameterEmptyException | ParameterDupblicateException | KeyEmptyException | KeyNullException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void search(AddressBook addB) {

		System.out.print("Type u want to search:");
		String in = scr.nextLine();
		System.out.println("");

		try {
			for (ContactDetails contact : addB.search(in)) {
				System.out.println(contact.getLastName() + ",\t" + contact.getFirstName() + ",\t" + contact.getNumber() + ",\t"
						+ contact.getMail() + ",\t" + contact.getAddress());

			}
		} catch (KeyNullException | KeyEmptyException e) {
			System.err.println(e.getMessage());
		}
	}
}