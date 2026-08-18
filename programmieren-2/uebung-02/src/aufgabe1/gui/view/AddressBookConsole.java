package aufgabe1.gui.view;

import java.util.Scanner;

import aufgabe1.gui.model.AddressBook;
import aufgabe1.gui.model.ContactDetails;

public class AddressBookConsole {

	private static Scanner scr = new Scanner(System.in);

	/**
	 * Konsolenschnittstelle welche eine Instanz von AddressBook erzeugt und dieses verwendet
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		AddressBook addB = new AddressBook();

		System.out.println("Addressbook :)");

		while (true) {
			System.out.println("What u wanne do ? \t\t\t\t\t(" + addB.getNumberOfEntries() + ") entries");
			System.out.println("Search (s), Add (a), Change (c), Delete (d), Show all ( ), Exit (e)");
			String in = scr.nextLine();

			if (in.toLowerCase().equals("e")) {
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
				delete(addB);
				break;
			default:
				for (ContactDetails contact : addB.adressBook.values()) {
					System.out.println(contact.getLastName() + ",\t" + contact.getFirstName() + ",\t" + contact.getNumber() + ",\t"
							+ contact.getMail() + ",\t" + contact.getAddress());
				}
				break;
			}
			System.out.println("------------------------------------------------------------------");
			System.out.println("");
		}
	}

	private static void search(AddressBook addB) {
		String in;
		System.out.print("Type u want to search:");
		in = scr.nextLine();
		System.out.println("");
		for (ContactDetails contact : addB.search(in)) {
			System.out.println(contact.getLastName() + ",\t" + contact.getFirstName() + ",\t" + contact.getNumber() + ",\t"
					+ contact.getMail() + ",\t" + contact.getAddress());
		}
	}

	private static void add(AddressBook addB) {
		System.out.println("Add a new contact.");

		String lastName;
		String firstName;

		while (true) {
			while (true) {
				System.out.print("Lastname: ");
				lastName = scr.nextLine();
				if (lastName.equals("")) {
					System.err.println("Lastname couldn't be empty");
					continue;
				}
				break;
			}
			while (true) {
				System.out.print("Firstname: ");
				firstName = scr.nextLine();
				if (firstName.equals("")) {
					System.err.println("Firstname couldn't be empty");
					continue;
				}
				break;
			}

			if (addB.keyInUse(lastName + firstName)) {
				System.err.println("Sorry, a Contact with the same credentials already exists");
				continue;
			}
			break;
		}

		System.out.print("Number: ");
		String number = scr.nextLine();
		System.out.print("Mail: ");
		String mail = scr.nextLine();
		System.out.print("Address: ");
		String address = scr.nextLine();
		addB.addDetails(new ContactDetails(firstName, lastName, address, number, mail));
	}

	private static void change(AddressBook addB) {
		System.out.println("Wich contact u want to change ?");

		System.out.print("Lastname: ");
		String oldLastName = scr.nextLine();
		System.out.print("Firstname: ");
		String oldFirstName = scr.nextLine();

		if (addB.keyInUse(oldLastName + oldFirstName)) {
			System.out.println("Now enter new details for the contact !");
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

			addB.changeDetails(oldLastName + oldFirstName, new ContactDetails(firstName, lastName, address, number, mail));
		} else {
			System.err.println("Sorry, there is no entry for !");
		}

	}

	private static void delete(AddressBook addB) {
		System.out.println("Which contact u want to remove ?");
		System.out.print("Lastname: ");
		String lastName = scr.nextLine();
		System.out.print("Firstname: ");
		String firstName = scr.nextLine();

		if (addB.keyInUse(lastName + firstName)) {
			addB.removeDetails(lastName + firstName);
			System.out.println("Entry has been removed !");
		} else {
			System.out.println("Sorry there is no entry to remove");
		}
	}
}