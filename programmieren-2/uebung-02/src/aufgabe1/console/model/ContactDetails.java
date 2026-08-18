package aufgabe1.console.model;
public class ContactDetails {

	// String Variablen werden deklariert
	private String firstName;
	private String lastName;
	private String address;
	private String number;
	private String mail;

	// Konstruktor: Beim Erstellen m�ssen die Parameter in den Klammern
	// mitgegben werden, die dann in den Variablen gespeichert werden
	public ContactDetails(String firstName, String lastName, String address, String number, String mail) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.number = number;
		this.mail = mail;
	}

	// Getter und Setter f�r alle Attribute
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}