package aufgabe1.console.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import aufgabe1.console.exceptions.KeyEmptyException;
import aufgabe1.console.exceptions.KeyNullException;
import aufgabe1.console.exceptions.ParameterDupblicateException;
import aufgabe1.console.exceptions.ParameterEmptyException;
import aufgabe1.console.exceptions.ParameterNullException;

public class AddressBook implements AddressBookInterface {

	private TreeMap<String, ContactDetails> addressBook = new TreeMap<String, ContactDetails>();

	public AddressBook() {
		fillAddressBook();
	}

	public ContactDetails getDetails(String key) {
		return addressBook.get(key.toLowerCase());
	}

	@Override
	public boolean keyInUse(String key) throws KeyNullException, KeyEmptyException {

		if (key == null) {
			throw new KeyNullException("Key can not be null");
		}
		if (key.isEmpty()) {
			throw new KeyEmptyException("Key can not be empty");
		}

		return addressBook.containsKey(key.toLowerCase());
	}

	public void addDetails(ContactDetails details) throws ParameterNullException, ParameterEmptyException, ParameterDupblicateException,
			KeyEmptyException, KeyNullException {

		if (details == null) {
			throw new ParameterNullException("Detail can not be null");
		}
		if (details.getFirstName().isEmpty() || details.getLastName().isEmpty() || details.getNumber().isEmpty()
				|| details.getMail().isEmpty() || details.getAddress().isEmpty()) {
			throw new ParameterEmptyException("There is a detail without information");
		}

		String key = details.getLastName().toLowerCase() + details.getFirstName().toLowerCase();

		if (keyInUse(key)) {
			throw new ParameterDupblicateException("This contact already exists");
		}

		addressBook.put(key, details);
	}

	public void changeDetails(String oldKey, ContactDetails details) throws KeyNullException, KeyEmptyException, ParameterNullException,
			ParameterEmptyException, ParameterDupblicateException {

		if (oldKey == null) {
			throw new KeyNullException("Key can not be null");
		}
		if (oldKey.isEmpty()) {
			throw new KeyEmptyException("Key can not be empty");
		}
		if (details == null) {
			throw new ParameterNullException("Detail can not be null");
		}
		if (details.getFirstName().isEmpty() || details.getLastName().isEmpty() || details.getNumber().isEmpty()
				|| details.getMail().isEmpty() || details.getAddress().isEmpty()) {
			throw new ParameterEmptyException("There is a detail without information");
		}

		String key = details.getLastName().toLowerCase() + details.getFirstName().toLowerCase();
		if (keyInUse(key)) {
			throw new ParameterDupblicateException("This contact already exists");
		}

		addressBook.remove(oldKey.toLowerCase());
		addressBook.put(key, details);
	}

	public int getNumberOfEntries() {
		return addressBook.size();
	}

	public void removeDetails(String key) throws KeyNullException, KeyEmptyException {

		if (key == null) {
			throw new KeyNullException("Key can not be null");
		}
		if (key.isEmpty()) {
			throw new KeyEmptyException("You have to type in any letter");
		}

		key.toLowerCase();
		if (keyInUse(key)) {
			addressBook.remove(key);
		}
	}

	public ContactDetails[] search(String keyPreFix) throws KeyNullException, KeyEmptyException {

		if (keyPreFix == null) {
			throw new KeyNullException("Key can not be null");
		}
		if (keyPreFix.isEmpty()) {
			throw new KeyEmptyException("You have to type in any letter");
		}

		ArrayList<ContactDetails> resultList = new ArrayList<ContactDetails>();

		for (String key : addressBook.keySet()) {
			if (key.startsWith(keyPreFix.toLowerCase())) {
				resultList.add(addressBook.get(key));
			}
		}

		return resultList.toArray(new ContactDetails[resultList.size()]);
	}

	public Collection<ContactDetails> getAddressBook() {
		return addressBook.values();
	}

	private void fillAddressBook() {
		try {
			addDetails(new ContactDetails("Philip", "Zusch", "Prenzelberg", "0173724882", "zuschy_1992@web.de"));
			addDetails(new ContactDetails("Leon", "Rösler", "Steglitz", "01234423", "dirty-harry@hallo.com"));
			addDetails(new ContactDetails("Robert", "Dzubia", "Köbenick", "0123 383823", "rooooobert@geißens.com"));
			addDetails(new ContactDetails("Tobi", "Klatt", "Kreuzberg", "0128 281312", "Pferd@youform.co"));
			addDetails(new ContactDetails("Inga", "Schwa", "CHB", "0123 137823", "bärechi@himmel.de"));
			addDetails(new ContactDetails("Julian", "Dil", "Tempelhof", "0123 13329", "hot_dog@ikea.de"));
			addDetails(new ContactDetails("Charli", "Wald", "CHB", "889234 23", "honey@honigtopg.goa"));
		} catch (ParameterNullException | ParameterEmptyException | ParameterDupblicateException | KeyEmptyException | KeyNullException e) {
		}
	}
}