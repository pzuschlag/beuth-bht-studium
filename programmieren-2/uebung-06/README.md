# Aufgabe 6 - Serialisierung von Spielständen #
* Sie haben vorerst genug von Adressbüchern? Probieren wir es mit einem Spiel-Prototyp. Weil das
Thema Objektserialisierung ist, abstrahieren wir aber (leider) von der visuellen Darstellung und der
Benutzerinteraktion. Wer Lust hat, kann das ja hinzufügen 

## Aufgabe 1 - Programmidee ##
* Ein Spiel soll von mehreren Spielern an verschiedenen Orten auf einem gemeinsamen
Spielfeld gespielt werden. Nach jeden Zug wird der gesamte Spielstand in eine Datei
geschrieben. Der nächste Spieler liest ihn ein, macht seinen Zug und schreibt den neuen
Spielstand in die Datei, usw.

## Aufgabe 2 ##
#### a) ###
* Wählen Sie ein beliebiges Brettspiel, dessen Spielstand Sie leicht als Liste darstellen
können, z.B. Schach, Mensch-ärgere-dich-nicht oder Schiffe versenken. Ein Spielstand
besteht aus dem Spielplan und der Position der Spielfiguren (evtl. noch aus der
Bezeichnung des nächsten Spielers).

### b) ###
* Entwerfen Sie eine Datenstruktur für den Spielstand und machen Sie daraus einen
Abstrakten Datentyp mit den Operationen, die für einen Zug erforderlich sind (Figur
setzen, Figur entnehmen, Feld beschießen...).
Sie brauchen der Einfachheit halber keine Spielregeln zu implementieren, wie die
erlaubten Züge eines Springers, – vertrauen Sie einfach darauf, dass die Spieler sich an
die Regeln halten!

### c) ###
* Schreiben Sie ein kleines Hauptprogramm, das jeweils einen Zug simuliert. Geben Sie als
Start-Argument den Namen oder die Farbe des Spielers ein, so dass Sie es für
verschiedene Spieler benutzen können. Jeder Zug soll den Spielstand einlesen, verändern
(ob zulässig oder regelwidrig ist egal) und wieder in die Datei schreiben!
Tipp: Mit Random können Sie gut einen Würfel simulieren)

### d) ###
* Prüfen Sie auf einfache Weise, dass sich er Spielstand fortlaufend verändert,indem Sie
z.B. die Zahl der Spielfguren im Feld, die Zahl der bisher gelaufenen Felder oder
gemachten Züge oder die Summe der besetzten Feldnummern herausschreiben.

### e) ###
* Spielen Sie mindestens 10 Züge

### f) ###
* Verändern Sie eine Klasse und beobachten Sie, was beim wieder Einlesen passiert.

*** Ein Protokoll ist nicht erforderlich, Sie müssen nur Ihr Spiel erklären können – und die Veränderung
des Spielstands, die Sie beobachten! ***