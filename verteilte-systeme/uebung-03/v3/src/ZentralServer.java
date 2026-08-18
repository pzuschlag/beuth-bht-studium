import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.rmi.Naming;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Erzeugt einen Webserver am übergebenen Port
 * 
 * @author Philip
 */
public class ZentralServer {

	static String resultHtml = "";
	static String ip;
	static int port;

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		ip = InetAddress.getLocalHost().getHostAddress();
		port = 9877;
		boolean shutdown = false;

		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}

		ServerSocket serverSocket = new ServerSocket(port);
		System.err.println("Server gestartet am Port: " + port);

		ServerInterface abteilungsServer = (ServerInterface) Naming.lookup("AbteilungsServer");

		// Wartet kontinuierlich auf Antwort.
		while (true) {
			resultHtml = "";
			Socket clientSocket = serverSocket.accept();
			System.err.print("Client verbunden: ");

			// Öffne konversation
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

			/*
			 * Hier lesen wir den InputStream des Client (und zeigen ihn auf der Console), sollte der Client den Link
			 * mit Referenzen aufgerufen haben, so werden diese hier entgegengenommen. Zudem wird hier der FavRequest
			 * überstprungen.
			 */
			String s = in.readLine();
			HashMap<String, String> map = new HashMap<>();
			if (s.startsWith("GET /favicon")) {
				System.out.println(s);
				in.close();
				System.out.println("-----------------");
				continue;
			} else {
				// Falls Referenzen vorhanden sind werden dies hier in eine Map geparst.
				System.out.println(s);
				if (s.contains("?")) {
					String[] get = s.split(" ");
					String[] l = get[1].substring(2).split("&");
					for (String str : l) {
						String[] k = str.split("=");
						if (k.length > 1) {
							map.put(k[0], URLDecoder.decode(k[1], "UTF-8"));
						} else {
							map.put(k[0], "");
						}
					}
				}

				// Anschließend wird wie gewohnt eine Suche durchgeführt.
				if (map.containsKey("C")) {
					if (map.get("C").equals("Suchen")) {
						System.err.print("Client Suche: ");
						if (!map.get("A").isEmpty() || !map.get("B").isEmpty()) {

							CopyOnWriteArrayList<String[]> results = new CopyOnWriteArrayList<String[]>();
							boolean invalidInsert = false;

							if (!map.get("A").isEmpty() && map.get("B").isEmpty()) {
								if ((map.get("A").matches("^[a-zA-ZäöüÄÖÜ]+[a-zA-ZäöüÄÖÜ\\s]*"))) {
									results.addAll(abteilungsServer.search(map.get("A"), ""));
								} else {
									resultHtml += "<div style=\" background-color: rgba(88, 88, 88, .6); padding: 10px; border-radius: 8px; width: 300px; margin: 30px auto;\"><p style=\"text-align: center;\">Im Namens-Feld dürfen nur gültige Buchstaben verwendet werden.</p></div>";
									invalidInsert = true;
								}
							}

							if (!map.get("B").isEmpty() && map.get("A").isEmpty()) {
								if ((map.get("B").matches("\\d+"))) {
									results.addAll(abteilungsServer.search("", map.get("B")));
								} else {
									resultHtml += "<div style=\" background-color: rgba(88, 88, 88, .6); padding: 10px; border-radius: 8px; width: 300px; margin: 30px auto;\"><p style=\"text-align: center;\">Im Nummern-Feld dürfen nur gültige Zahlen verwendet werden.</p></div>";
									invalidInsert = true;
								}
							}

							if (!map.get("A").isEmpty() && !map.get("B").isEmpty()) {
								if ((map.get("A").matches("^[a-zA-ZäöüÄÖÜ]+[a-zA-ZäöüÄÖÜ\\s]*") && (map.get("B").matches("\\d+")))) {
									results.addAll(abteilungsServer.search(map.get("A"), map.get("B")));
								} else {
									resultHtml += "<div style=\" background-color: rgba(88, 88, 88, .6); padding: 10px; border-radius: 8px; width: 300px; margin: 30px auto;\"><p style=\"text-align: center;\">Im Nummern-Feld dürfen nur gültige Zahlen verwendet werden.</p></div>";
									invalidInsert = true;
								}
							}

							// Fehlermeldung für den Trace
							if (invalidInsert) {
								System.out.println("Fehlerhafte Eingabe");
							}

							// Ausgabe des Results als HTML-Tabelle
							if (!results.isEmpty()) {
								resultHtml = "<div style=\" background-color: rgba(88, 88, 88, .6); padding: 10px; border-radius: 8px; width: 300px; margin: 30px auto;\"><table><tr><th>Name</th><th>Nummer</th></tr>";
								for (String[] row : results) {
									resultHtml += "<tr><td>" + row[0] + "</td>";
									if (row.length > 1) {
										resultHtml += "<td>" + row[1] + "</td></tr>";
									}
								}
								resultHtml += "</table></div>";

								// Neu initialisieren der Reuslt-Liste
								System.out.println(results.size() + " Ergebniss(e)");
								results = new CopyOnWriteArrayList<String[]>();
							}
						} else {
							resultHtml += "<div style=\" background-color: rgba(88, 88, 88, .6); padding: 10px; border-radius: 8px; width: 300px; margin: 30px auto;\"><p style=\"text-align: center;\">Eine leere Suche liefert kein Ergebniss.</p></div>";
							System.out.println("Fehlerhafte Eingabe");
						}
					} else if (map.get("C").equals("Server beenden")) {
						System.err.println("Server wurde beendet");
						shutdown = true;
					}
				}
			}
			// Ausgabe an den Client
			out.write("HTTP/1.0 200 OK\r\n");
			out.write("Content-Type: text/html\r\n");
			out.write("\r\n"); // Machen Sachen !

			if (shutdown) {
				out.write("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<meta charset=\"UTF-8\">\n" + "<title>Telefonbuch</title>\n"
						+ "</head>\n" + "<body style=\"background-color: lightblue;\">\n" + "<h2 align=center>Server wurde Beendet.</h2>\n"
						+ "</body>\n" + "</html>\n");
			} else {
				out.write(getIndexHtml());
			}

			// Schließen der Ströme.
			out.flush();
			out.close();
			in.close(); // Wenn wir "in" nicht schließen, läd die Seite ständig weiter.

			if (shutdown) {
				break;
			}
		}
		abteilungsServer.shutdown();
	}

	/**
	 * Ließt die Datei index.html aus dem view Verzeichnis und liefert sie als String zurück.
	 * 
	 * @return
	 * @throws IOException
	 */
	private static String getIndexHtml() throws IOException {

		String index = "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<meta charset=\"UTF-8\">\n" + "<title>Telefonbuch</title>\n"
				+ "</head>\n" + "<body style=\"background-color: lightblue;\">\n" + "<h2 align=center>Telefonverzeichnis</h2>\n"
				+ "<h3 align=center>Sie können nach Name oder nach Telefonnummer oder nach beiden (nebenläufig) suchen.</h3>\n"
				+ "<form method=get action=\"http://" + ip + ":" + port + "\">\n"
				+ "<div style=\" background-color: rgba(88, 88, 88, .6); padding: 10px; border-radius: 8px; width: 300px; height: 70px; margin: 30px auto;\">\n"
				+ "<table>\n" + "<tr>\n" + "<td valign=top>Name:</td>\n" + "<td><input name=A></td>\n" + "<td></td>\n" + "</tr>\n"
				+ "<tr>\n" + "<td valign=top>Nummer:</td>\n" + "<td><input name=B></td>\n" + "<td></td>\n" + "</tr>\n" + "<tr>\n"
				+ "<td valign=top><input type=submit name=C value=Suchen></td>\n" + "<td><input type=reset></td>\n"
				+ "<td><input type=submit name=C value=\"Server beenden\"></td>\n" + "</tr>\n" + "</table>\n" + "</div>\n" + "</form>\n"
				+ "<table></table>\n" + "</body>\n" + "</html>\n";

		// Sucht in der HTML anch dem TableTag und tauscht dieses aus.
		String tableStart = index.substring(0, index.indexOf("<table></table>"));
		String tableEnd = index.substring(index.indexOf("<table></table>") + 15);

		return tableStart + resultHtml + tableEnd;
	}
}