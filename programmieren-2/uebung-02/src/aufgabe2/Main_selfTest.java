package aufgabe2;

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
public class Main_selfTest {

	private static Scanner scr = new Scanner(System.in);

	public static void main(String[] args) {
		AddressBook addB = new AddressBook();

		System.out.println("Self-Test Addressbook");

		while (true) {
			System.out.println("What u wanne Test ? \t\t\t\t\t(" + addB.getNumberOfEntries() + ") entries");
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

		System.out.println("Testing remove.");

		// key null
		try {
			addB.removeDetails(null);
		} catch (KeyNullException | KeyEmptyException e) {
			System.err.println(e.getMessage());
		}

		// Key Empty
		try {
			addB.removeDetails("");
		} catch (KeyNullException | KeyEmptyException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void change(AddressBook addB) {

		System.out.println("Testing change.");

		// Parameter null
		try {
			addB.changeDetails("Zusch" + "Philip", null);
		} catch (ParameterNullException | ParameterEmptyException | ParameterDupblicateException | KeyNullException | KeyEmptyException e) {
			System.err.println(e.getMessage());
		}
		// Parameter Empty
		try {
			addB.changeDetails("Zusch" + "Philip", new ContactDetails("", "Zusch", "address", "number", "mail"));
		} catch (ParameterNullException | ParameterEmptyException | ParameterDupblicateException | KeyNullException | KeyEmptyException e) {
			System.err.println(e.getMessage());
		}
		// Parameter Duplicate
		try {
			addB.changeDetails("Zusch" + "Philip", new ContactDetails("Philip", "Zusch", "Prenzelberg", "0173 714 888 2",
					"p-zuschlag@web.de"));
		} catch (ParameterNullException | ParameterEmptyException | ParameterDupblicateException | KeyNullException | KeyEmptyException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void add(AddressBook addB) {

		System.out.println("Testing add.");

		// Testen von Parameter null
		try {
			addB.addDetails(null);
		} catch (ParameterNullException | ParameterEmptyException | ParameterDupblicateException | KeyEmptyException | KeyNullException e) {
			System.err.println(e.getMessage());
		}

		// Testen von Parameter Empty
		try {
			addB.addDetails(new ContactDetails("", "lorem", "lorem", "ipsum", "ipsum"));
		} catch (ParameterNullException | ParameterEmptyException | ParameterDupblicateException | KeyEmptyException | KeyNullException e) {
			System.err.println(e.getMessage());
		}

		// Testen von Parameter Dupiclate
		try {
			addB.addDetails(new ContactDetails("Leon", "Rösler", "Steglitz", "01234423", "dirty-harry@hallo.com"));
		} catch (ParameterNullException | ParameterEmptyException | ParameterDupblicateException | KeyEmptyException | KeyNullException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void search(AddressBook addB) {

		System.out.println("Testing search.");

		// Testen von von null Übergabe
		try {
			for (ContactDetails contact : addB.search(null)) {
				System.out.println(contact.getLastName() + ",\t" + contact.getFirstName() + ",\t" + contact.getNumber() + ",\t"
						+ contact.getMail() + ",\t" + contact.getAddress());

			}
		} catch (KeyNullException | KeyEmptyException e) {
			System.err.println(e.getMessage());
		}
		// Testen von empty übergabe
		try {
			for (ContactDetails contact : addB.search("")) {
				System.out.println(contact.getLastName() + ",\t" + contact.getFirstName() + ",\t" + contact.getNumber() + ",\t"
						+ contact.getMail() + ",\t" + contact.getAddress());

			}
		} catch (KeyNullException | KeyEmptyException e) {
			System.err.println(e.getMessage());
		}
	}
}