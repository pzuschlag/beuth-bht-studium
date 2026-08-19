import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Abteilungsserver, dieser nimmt Suchanfragen entgegen, Sucht im Telefonbuch nach Einträgen und gibt Ergebnisse an den
 * Aufrufer zurück.
 * 
 * @author Philip
 *
 */
public class AbteilungsServer extends UnicastRemoteObject implements ServerInterface {

	protected AbteilungsServer() throws RemoteException {
		super();
	}

	public static void main(String[] args) {

		try {
			// Bei der lokalen Registry registrieren um von anderen Klasseng gefunden zu werden
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			// Instanziieren der Serverklasse und anmelden bei der registry
			Naming.rebind("AbteilungsServer", new AbteilungsServer());

			System.err.println("Server ready and waiting for RMIs on port " + Registry.REGISTRY_PORT);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	/**
	 * 
	 * @param str
	 * @param column
	 * @param results
	 * @throws InterruptedException
	 */
	@Override
	public CopyOnWriteArrayList<String[]> search(String str, String num) throws RemoteException {

		CopyOnWriteArrayList<String[]> results = new CopyOnWriteArrayList<String[]>();

		Thread t1 = null;
		Thread t2 = null;

		if (!str.isEmpty()) {
			t1 = new Thread(new Search(str, 0, results), "search-name");
			t1.start();
			System.out.println("Suche nach \"" + str + "\" ausgeführt");
		}

		if (!num.isEmpty()) {
			t2 = new Thread(new Search(num, 1, results), "search-number");
			t2.start();
			System.out.println("Suche nach \"" + num + "\" ausgeführt");
		}

		// Auf Such-Threads warten
		try {
			if (t1 != null) {
				t1.join();
			}
			if (t2 != null) {
				t2.join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("-------------");
		return results;
	}

	public void shutdown() throws RemoteException {
		System.err.println("Abteilungsserver wurde Beendet");
		try {
			LocateRegistry.getRegistry().unbind("AbteilungsServer");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		UnicastRemoteObject.unexportObject(this, true);
		// System.exit(0);
	}
}

/**
 * Diese Klasse kann von einem Thread aufgerufen werden und durchsucht anschließend eine übergebene Liste
 * 
 * @className SearchTelefonserver
 * @author Philip Zuschlag
 * @date 2016-04-22
 */
class Search implements Runnable {

	private Telefonbuch server = new Telefonbuch();

	private String str;
	private int column;
	private CopyOnWriteArrayList<String[]> results;

	/**
	 * Konfiguriert die Suche durch setzen den Suchstring und die Spalte in welcher dieser gesucht werden soll.
	 * 
	 * @param str
	 * @param column
	 * @param results
	 */
	public Search(String str, int column, CopyOnWriteArrayList<String[]> results) {
		this.str = str;
		this.column = column;
		this.results = results;
	}

	@Override
	public void run() {
		lookingFor();
	}

	/**
	 * Iteriert über den Telefonbuch und vergleicht die Einträge mit dem definierten Such-String, werden
	 * Übereinstimmungen gefunden so werden diese als String zurückgeliefert
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
			results.add(new String[] { "Die Suche nach \"" + str + "\" war erfolglos" });
		}
	}
}

/**
 * Stellt die Abstraktion eines Telefonservers da, welcher Datenbereitstellt
 * 
 * @className Telefonbuch
 * @author Philip Zuschlag
 * @date 2016-04-22
 */
class Telefonbuch {

	// Thread-safe variant of ArrayList
	private CopyOnWriteArrayList<String[]> server;

	public Telefonbuch() {
		server = new CopyOnWriteArrayList<>();

		server.add(new String[] { "Meier", "4711" });
		server.add(new String[] { "Schmitt", "0815" });
		server.add(new String[] { "Müller", "4711" });
		server.add(new String[] { "Meier", "0816" });
		server.add(new String[] { "von Schulze", "0816" });
	}

	/**
	 * Returns the number of elements in this list.
	 * 
	 * @return size
	 */
	public int size() {
		return server.size();
	}

	/**
	 * Returns the element at the specified position in this list.
	 * 
	 * @param column
	 * @return entry
	 */
	public String[] getEntry(int column) {
		return server.get(column);
	}
}

interface ServerInterface extends Remote {

	CopyOnWriteArrayList<String[]> search(String str, String num) throws RemoteException;

	void shutdown() throws RemoteException;

}