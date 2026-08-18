package aufgabe2;

public class ContactDetails {

	private String firstName;
	private String lastName;
	private String address;
	private String number;
	private String mail;

	public ContactDetails(String firstName, String lastName, String address, String number, String mail) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.number = number;
		this.mail = mail;
	}

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