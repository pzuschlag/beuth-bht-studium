package aufgabe1.console.model;

import aufgabe1.console.exceptions.KeyEmptyException;
import aufgabe1.console.exceptions.KeyNullException;
import aufgabe1.console.exceptions.ParameterDupblicateException;
import aufgabe1.console.exceptions.ParameterEmptyException;
import aufgabe1.console.exceptions.ParameterNullException;

// Interface
public interface AddressBookInterface {

	/**
	 * look up a name or phone number and return the
	 * 
	 * @param key
	 * @return
	 */
	public ContactDetails getDetails(String key);

	/**
	 * Return whether or not current key is in use.
	 * 
	 * @param key
	 * @return
	 * @throws KeyEmptyException
	 * @throws KeyNullException
	 */
	public boolean keyInUse(String key) throws KeyNullException, KeyEmptyException;

	/**
	 * Add a new set of details to the notebook.
	 * 
	 * @param details
	 * @throws ParameterDupblicateException
	 * @throws ParameterEmptyException
	 * @throws ParameterNullException
	 * @throws KeyNullException
	 * @throws KeyEmptyException
	 */
	public void addDetails(ContactDetails details) throws ParameterNullException, ParameterEmptyException, ParameterDupblicateException,
			KeyEmptyException, KeyNullException;

	/**
	 * Change the details previously stored under the given key.
	 * 
	 * @param details
	 * @throws ParameterDupblicateException
	 * @throws ParameterEmptyException
	 * @throws ParameterNullException
	 * @throws KeyEmptyException
	 * @throws KeyNullException
	 */
	public void changeDetails(String oldKey, ContactDetails details) throws KeyNullException, KeyEmptyException, ParameterNullException,
			ParameterEmptyException, ParameterDupblicateException;

	/**
	 * Search for all details stored under a key that starts with.
	 * 
	 * @param keyprefix
	 * @return
	 * @throws KeyEmptyException
	 * @throws KeyNullException
	 */
	public ContactDetails[] search(String keyprefix) throws KeyNullException, KeyEmptyException;

	/**
	 * 
	 * @return The number of entries currently in the address book.
	 */
	public int getNumberOfEntries();

	/**
	 * Remove the entry with the given key from the address book.
	 * 
	 * @param key
	 * @throws KeyEmptyException
	 * @throws KeyNullException
	 */
	public void removeDetails(String key) throws KeyNullException, KeyEmptyException;
}
