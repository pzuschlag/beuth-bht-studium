package controller;

import java.util.concurrent.CopyOnWriteArrayList;

import model.Telefonserver;

/**
 * Diese Klasse kann von einem Thread aufgerufen werden und durchsucht
 * anschließend eine übergebene Liste
 *
 * @className SearchTelefonserver
 * @author Philip Zuschlag
 * @date 2016-04-22
 */
public class Search implements Runnable {

	private final Telefonserver server = new Telefonserver();

	private final String str;
	private final int column;
	private final CopyOnWriteArrayList<String[]> results;

	/**
	 *
	 * @param str
	 *            : String to be searched
	 * @param column
	 *            : column to search in (name or number)
	 * @param results
	 *            : reference to the threadsafe List in which the results have
	 *            to be written
	 */
	public Search(String str, int column, CopyOnWriteArrayList<String[]> results) {
		this.str = str;
		this.column = column;
		this.results = results;
	}

	// ____________________run-Method (needed by the Thread)__________________
	@Override
	public void run() {
		lookingFor();
	}

	/**
	 * iterate through server and compare the search-String with the entrys.
	 * Both Threads start this Method through the run Method - they work
	 * parallel and write the results in the List without knowing from each
	 * other
	 *
	 * @return
	 */
	private void lookingFor() {

		for (int i = 0; i < server.size(); i++) {
			if (server.getEntry(i)[column].equals(str)) {
				results.add(server.getEntry(i));
			}
		}

		if (results.isEmpty()) {
			results.add(new String[]{"Die Suche nach \"" + str + "\" war erfolglos"});
		}
	}
}