package model;

public class ObservableContactDetails extends ContactDetails {



	// Konstruktor für das Adressbuch = ContactDetails: die Details müssen als Parameter mitgegeben werden
	// Die Parameter werden aus der Oberklasse geholt
	public ObservableContactDetails(String firstName, String lastName, String address, String number) {
		super(firstName, lastName, address, number);
	}

}
