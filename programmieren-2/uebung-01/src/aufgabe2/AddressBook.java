package aufgabe2;

import java.util.ArrayList;
import java.util.TreeMap;

public class AddressBook implements AddressBookInterface {

	TreeMap<String, ContactDetails> adressBook = new TreeMap<String, ContactDetails>();

	public AddressBook() {
		fillAddressBook();
	}

	@Override
	public ContactDetails getDetails(String key) {
		return adressBook.get(key.toLowerCase());
	}

	@Override
	public boolean keyInUse(String key) {
		return adressBook.containsKey(key.toLowerCase());
	}

	@Override
	public void addDetails(ContactDetails details) {
		adressBook.put(details.getLastName().toLowerCase() + details.getFirstName().toLowerCase(), details);
	}

	@Override
	public void changeDetails(String oldKey, ContactDetails details) {
		if (adressBook.containsKey(oldKey.toLowerCase())) {
			adressBook.remove(oldKey.toLowerCase());
			adressBook.put(details.getLastName().toLowerCase() + details.getFirstName().toLowerCase(), details);
		}
	}

	@Override
	public ContactDetails[] search(String keyprefix) {

		ArrayList<ContactDetails> resultList = new ArrayList<ContactDetails>();

		for (String key : adressBook.keySet()) {
			if (key.startsWith(keyprefix.toLowerCase())) {
				resultList.add(adressBook.get(key));
			}
		}

		return resultList.toArray(new ContactDetails[resultList.size()]);
	}

	@Override
	public int getNumberOfEntries() {
		return adressBook.size();
	}

	@Override
	public void removeDetails(String key) {
		adressBook.remove(key.toLowerCase());
	}

	private void fillAddressBook() {
		addDetails(new ContactDetails("Philip", "Zusch", "Prenzelberg", "0173 714 888 2", "p-zuschlag@web.de"));
		addDetails(new ContactDetails("Leon", "Rösler", "Steglitz", "01234423", "dirty-harry@hallo.com"));
		addDetails(new ContactDetails("Robert", "Dzubia", "Köbenick", "0123 383823", "rooooobert@geißens.com"));
		addDetails(new ContactDetails("Tobi", "Klatt", "Kreuzberg", "0128 281312", "Pferde-Hängst@youform.co"));
		addDetails(new ContactDetails("Inga", "Schwa", "CHB", "0123 137823", "gluecksbärechi@himmel.de"));
		addDetails(new ContactDetails("Julian", "Dil", "Tempelhof", "0123 13329", "hotdognachfüller@ikea.de"));
		addDetails(new ContactDetails("Charli", "Wald", "CHB", "889234 23", "honey@honigtopg.goa"));
	}
}
