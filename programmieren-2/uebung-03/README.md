# Übung 3 – Git und FXDataViews #

## Aufgabe 1 ##
### a) ###
* Bearbeiten Sie die Schritte 1-22 des Git-Tutorials von Lars Vogel http://www.vogella.com/tutorials/Git/article.html. Das Tutorial basiert auf Linux. Die Windows-Konsolenbefehle lauten:
* * dir - Auflisten des Arbeitsvezeichnisses
* * cd - Pfad Wechsel des Arbeitsverzeichnisses nach 'Pfad' 
* * md Name - Erzeugen eines neuen Verzeichnisses 'Name'
* * rd Name - Löschen des Verzeichnisses 'Name'
* * echo Text > Datei - Schreiben nach 'Datei', ggf. Erzeugen der Datei
* * del Datei - Datei löschen
* Die git-Befehle sind auf allen Plattformen gleich (kein "\" bei Windows-Pfadparametern!).
* ***Wer gut vorankommt oder sich schon etwas auskennt, sollte bis Schritt 34 machen***

### b) ###
* Bearbeiten Sie die wichtigsten Schritte nochmals, diesmal mithilfe einer Git-GUI, z.B. TortoiseGit für Windows oder Sourcetree für MacOS.
* ***Wer definitiv die Konsole nutzen will, kann diesen Schritt weglassen.***

## Aufgabe 2 ##
### a) ###
* Finden Sie heraus, wie Sie Daten in JavaFX als ListView und als TableView ***(für Experten auch: als TreeView)*** anzeigen können. Nutzen Sie das, um die Daten Ihres AddressBook auf verschiedene Weise zu präsentieren: Die Schlüssel als ListView, die Gesamtdaten als TableView (und evtl. auch auf- und zuklappbar als TreeView). Zusammenfügen am besten über eine Border Pane, damit die Tabelle beliebig breit werden kann. 
* ***Achtung: Es geht nur um die Anzeige der Daten!*** Sie sollen Ihre Anzeigen nicht nutzen können, um Daten einzugeben oder zu ändern. Auch die entstehenden Doppeleinträge in der Tabelle sind ok – auch wenn Sie sie gern vermeiden dürfen.
* ***Hinweis zum Einstieg:***
* Ihr AddressBook gibt bisher keine Listen heraus. Sie sollten also zunächst öffentliche Methoden schreiben, die alle Schlüssel bzw. alle ContactDetails als Liste zurückgeben.
* Um Daten in einer ListView oder TableView anzeigen zu können, benötigen Sie sie nicht als Liste, sondern in Form einer ObservableList. Diese können Sie aus einer java.util.List durch den Aufruf einer statischen Funktion der Klasse FXCollections erzeugen:
* * ***ObservableList olist = FXCollections.observableList(list);***
* Für eine Tabelle wird angenommen, dass die Listenelemente eine Struktur haben, d.h. mehrere Attribute, die dann den Spalten zugeordnet werden. Dafür sind weitere Umformungen nötig, die aber von sogenannten "Factory"-Methoden übernommen werden können.
* Sie werden vermutlich viel Code im Internet finden – wählen Sie den einfachsten und versuchen Sie, ihn anhand dieses Hinweises zu verstehen!