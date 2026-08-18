package aufgabe2;

import org.junit.Test;

import aufgabe1.console.exceptions.KeyEmptyException;
import aufgabe1.console.exceptions.KeyNullException;
import aufgabe1.console.exceptions.ParameterDupblicateException;
import aufgabe1.console.exceptions.ParameterEmptyException;
import aufgabe1.console.exceptions.ParameterNullException;
import aufgabe1.console.model.AddressBook;
import aufgabe1.console.model.ContactDetails;

public class AddressBookTest {

	AddressBook addB = new AddressBook();

	// Add Details
	@Test(expected = ParameterNullException.class)
	public void testAddDetails_paramNull() throws ParameterNullException, ParameterEmptyException, ParameterDupblicateException,
			KeyEmptyException, KeyNullException {
		addB.addDetails(null);
	}

	@Test(expected = ParameterEmptyException.class)
	public void testAddDetails_paramEmpty() throws ParameterNullException, ParameterEmptyException, ParameterDupblicateException,
			KeyEmptyException, KeyNullException {
		addB.addDetails(new ContactDetails("Philip", "Zusch", "", "number", "mail"));
	}

	@Test(expected = ParameterDupblicateException.class)
	public void testAddDetails_paramDuplicate() throws ParameterNullException, ParameterEmptyException, ParameterDupblicateException,
			KeyEmptyException, KeyNullException {
		addB.addDetails(new ContactDetails("Philip", "Zusch", "address", "number", "mail"));
	}

	// Change Details
	@Test(expected = KeyNullException.class)
	public void testChangeDetail_keyNull() throws KeyNullException, KeyEmptyException, ParameterNullException, ParameterEmptyException,
			ParameterDupblicateException {
		addB.changeDetails(null, new ContactDetails("Philip", "Zusch", "address", "number", "mail"));
	}

	@Test(expected = KeyEmptyException.class)
	public void testChangeDetail_keyEmpty() throws KeyNullException, KeyEmptyException, ParameterNullException, ParameterEmptyException,
			ParameterDupblicateException {
		addB.changeDetails("", new ContactDetails("Philip", "Zusch", "address", "number", "mail"));
	}

	@Test(expected = ParameterNullException.class)
	public void testChangeDetail_paramNull() throws KeyNullException, KeyEmptyException, ParameterNullException, ParameterEmptyException,
			ParameterDupblicateException {
		addB.changeDetails("Zusch" + "Philip", null);
	}

	@Test(expected = ParameterEmptyException.class)
	public void testChangeDetail_paramEmpty() throws KeyNullException, KeyEmptyException, ParameterNullException, ParameterEmptyException,
			ParameterDupblicateException {
		addB.changeDetails("Zusch" + "Philip", new ContactDetails("Philip", "Zusch", "", "number", "mail"));
	}

	@Test(expected = ParameterDupblicateException.class)
	public void testChangeDetail_paramDuplicate() throws KeyNullException, KeyEmptyException, ParameterNullException,
			ParameterEmptyException, ParameterDupblicateException {
		addB.changeDetails("Zusch" + "Philip", new ContactDetails("Philip", "Zusch", "address", "number", "mail"));
	}

	// Key in Use
	@Test(expected = KeyEmptyException.class)
	public void testKeyInUse_keyEmpty() throws KeyEmptyException, KeyNullException {
		addB.keyInUse("");
	}

	@Test(expected = KeyNullException.class)
	public void testKeyInUse_keyNull() throws KeyEmptyException, KeyNullException {
		addB.keyInUse(null);
	}

	// Test Remove
	@Test(expected = KeyEmptyException.class)
	public void testRemoveDetails_keyEmpty() throws KeyEmptyException, KeyNullException {
		addB.removeDetails("");
	}

	@Test(expected = KeyNullException.class)
	public void testRemoveDetails_keyNull() throws KeyEmptyException, KeyNullException {
		addB.removeDetails(null);
	}

	// Test Search
	@Test(expected = KeyEmptyException.class)
	public void testSearch_keyEmpty() throws KeyEmptyException, KeyNullException {
		addB.search("");
	}

	@Test(expected = KeyNullException.class)
	public void testSearch_keyNull() throws KeyEmptyException, KeyNullException {
		addB.search(null);
	}
}