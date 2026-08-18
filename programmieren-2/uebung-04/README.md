# Übung 4 – Editierbare FXDataViews #

## Aufgabe 1 - Properties implementieren ##
### a) ###
* Ein Terminkalender enthält Termine, englisch Appointments. Erstellen Sie eine Klasse
Appointment, die die Attribute eines Termins als Properties enthält:
Datum, StartUhrzeit, EndUhrzeit, Terminkategorie (frei wählbarer String),
Terminbezeichnung, Terminbeschreibung – und was Sie noch wichtig finden. Wir brauchen
diese Klasse später wieder.

### b) ###
* Wenn die Enzeit vor der Startzeit liegt, liegt ein Fehler vor.

### c) ###
* ***Wie wäre es mit einer Property Dauer, die sich aus Start- und Endzeit berechtnet?***

## Aufgabe 2 - Editierbare ListView ##
### a) ###
* Erstellen Sie zunächst ObservableContactDetails als Erweiterung der Klasse ContactDetails,
damit sie im "normalen" Adressbuch verwendbar bleiben. Erkennen Sie den "Charme" der
Getter und Setter – sie bleiben gültig, auch wenn sie anders implementiert sind. (Wer im
AddressBook bisher nicht mit get und set auf die ContactDetails zugegriffen hat, muss das
jetzt anpassen.
(Vergessen Sie nicht, zunächst die alte Version in Git zu sichern und die vorhandenen JunitTests
auf der neuen Version laufen zu lassen!)
### b) ###
* Übernehmen in die Main-Klasse die Teile aus der Main-Klasse aus Aufgabe 3, die sich nur auf
die Anzeige beziehen. Für die ObservableLists, EventHandler etc. schreiben Sie eine neue
Klasse Control im Paket application. Defineren Sie hier Ihre ObservableList und setzen Sie die
CellFactory so, dass Sie die Zellen Editieren können. (Achtung: Doppelklick startet das
Editieren, Enter beendet es.) Initialisieren Sie Ihre ObservableList direkt oder aus dem
AddressBook, wie Sie mögen.
### c) ###
* Erstellen Sie einen Knopf "Daten Drucken", mit dem Sie den Inhalt Ihrer Observable List auf
die Konsole schreiben, um zu bestätigen, dass die Änderungen nicht nur im GUI erfolgt sind.
### d) ###
* Frage: Wie würden Sie die Änderungen in das AddressBook zurückschreiben?

## Aufgabe 3 - Editierbare Tabelle ##
### a) ###
* Erstellen Sie nach dem gleichen Prinzip eine editierbare Tabelle für die Adressdaten.
Spendieren Sie dafür ein eigene Pane, so dass Sie bei Bedarf zwischen den Ansichten wechseln können. Für die Testphase deaktivieren Sie die ListView, um weniger Ballast zu
haben. Als Wertetyp der Zellen wählen Sie in allen Fällen String.
* ***Wer gut zurechtkommt, kann hier auch Integer oder Date ausprobieren. Das macht mehr
Aufwand, weil es auf dem GUI nur String-Werte gibt.***
### b) ###
* Für das Erstellen neuer Einträge programmieren Sie bitte einen Knopf, der der Adressliste ein
neues ObservableContactDetails-Objekt mit Platzhalter-Einträgen, z.B. "-" oder "neu" hizufügt. Durch Editieren der Zellen entsteht so ein sinnvoller neuer Eintrag. Was müssen Sie tun, damit das neue Objekt in der Tabelle angezeigt wird? Was schließen Sie
daraus?
### c) ###
* Genau wie bei der List View, erstellen Sie auch einen Knopf "Daten Drucken", mit dem Sie
den Inhalt der Adressliste in der Konsole ausgeben, um zu sehen, ob das Editieren
funktioniert.
### d) ###
* ***Implementieren Sie auch einen Suchknopf mit und einen Löschknopf für die selektierte Zeile.***
### e) ###
* Was müssten Sie tun, damit sich das Editieren auch (korrekt) auf das AddressBook auswirkt?
Wie sollten Sie mit den Exceptions umgehen, die das AddressBook wirfkt, z.B. Schlüssedopplung?

## Aufgabe 4 - Arbeiten mit dem SceneBuilder ##
* ***Wer mit allem gut zurecht gekommen ist und noch Zeit hat, ist eingeladen, die Tabellenlösung
grafisch mit dem SceneBuilder zu erstellen. Bitte, schauen Sie sich genau an, welcher FXML-Code
entsteht, und versuchen Sie, ihn zu verstehen.***