package aufgabe2;

public interface AddressBookInterface {

	/**
	 * look up a name or phone number and return the
	 * 
	 * @param key
	 * @return
	 */
	public ContactDetails getDetails(String key);

	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean keyInUse(String key);

	/**
	 * 
	 * @param details
	 */
	public void addDetails(ContactDetails details);

	/**
	 * 
	 * @param details
	 */
	public void changeDetails(String oldKey, ContactDetails details);

	/**
	 * 
	 * @param keyprefix
	 * @return
	 */
	public ContactDetails[] search(String keyprefix);

	/**
	 * 
	 * @return
	 */
	public int getNumberOfEntries();

	/**
	 * 
	 * @param key
	 */
	public void removeDetails(String key);

}
