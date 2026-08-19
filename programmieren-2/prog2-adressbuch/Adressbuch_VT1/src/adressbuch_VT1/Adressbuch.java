package adressbuch_VT1;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Eine Klasse zur Verwaltung einer beliebigen Anzahl von Kontakten. Die Daten
 * werden sowohl ueber den Namen als auch ueber die Telefonnummer indiziert.
 *
 * @author David J. Barnes und Michael K?lling.
 * @version 2008.03.30
 */
public class Adressbuch {

    // Speicher fuer beliebige Anzahl von Kontakten.
    private TreeMap<String, adressbuch_VT2.Kontakt> buch;
    private int anzahlEintraege;

    /**
     * Initialisiere ein neues Adressbuch.
     */
    public Adressbuch() {
        buch = new TreeMap<String, adressbuch_VT2.Kontakt>();
        anzahlEintraege = 0;
        setTestData();
    }

    /**
     * Schlage einen Namen oder eine Telefonnummer nach und liefere den
     * zugehoerigen Kontakt.
     *
     * @param schluessel der Name oder die Nummer zum Nachschlagen.
     * @return den zum Schluessel gehoerenden Kontakt.
     */
    public adressbuch_VT2.Kontakt getKontakt(String schluessel) {
        if (schluessel.isEmpty() || schluessel == null || schluessel == "") {
            throw new IllegalArgumentException("Bitte geben sie einen korrekten Schluessel ein");
        }
        if (buch.isEmpty()){
            throw new NullPointerException("Im Adressbuch befinden sich im Moment noch keine Kontakte");
        }
        if (!schluesselBekannt(schluessel)){
            throw new NullPointerException("Zu diesem Schluessel gibt es keinen Eintrag");
        }
        return buch.get(schluessel);
    }

    /**
     * Ist der gegebene Schluessel in diesem Adressbuch bekannt?
     *
     * @param schluessel der Name oder die Nummer zum Nachschlagen.
     * @return true wenn der Schluessel eingetragen ist, false sonst.
     */
    public boolean schluesselBekannt(String schluessel) {
        return buch.containsKey(schluessel);
    }

    /**
     * Fuege einen neuen Kontakt in dieses Adressbuch ein.
     *
     * @param kontakt der neue Kontakt.
     */
    public void addKontakt(adressbuch_VT2.Kontakt kontakt) {
        if (kontakt == null) throw new NullPointerException("Beim Erstellen des Kontaektes ist ein Fehler aufgetreten");
        buch.put(kontakt.getName(), kontakt);
        buch.put(kontakt.getTelefon(), kontakt);
        anzahlEintraege++;
    }

    /**
     * Aendere die Kontaktdaten des Kontakts, der bisher unter dem gegebenen
     * Schluessel eingetragen war.
     *
     * @param alterSchluessel einer der verwendeten Schl?ssel.
     * @param daten           die neuen Kontaktdaten.
     */
    public void updateKontakt(String alterSchluessel, adressbuch_VT2.Kontakt daten) {
        deleteKontakt(alterSchluessel);
        addKontakt(daten);
    }

    /**
     * Suche nach allen Kontakten mit einem Schluessel, der mit dem gegebenen
     * Praefix beginnt.
     *
     * @param praefix der Schluesselpraefix, nach dem gesucht werden soll.
     * @return ein Array mit den gefundenen Kontakten.
     */
    public adressbuch_VT2.Kontakt[] getKontakte(String praefix) {
        if (praefix == null || praefix.isEmpty()) {
            return getAlleKontakte();
        }
        // Eine Liste fuer die Treffer anlegen.
        List<adressbuch_VT2.Kontakt> treffer = new ArrayList<adressbuch_VT2.Kontakt>();
        // Finden von Schluesseln, die gleich dem oder groesser als
        // der Praefix sind.
        SortedMap<String, adressbuch_VT2.Kontakt> rest = buch.tailMap(praefix);
        Iterator<String> it = rest.keySet().iterator();
        // Stoppen bei der ersten Nichtuebereinstimmung.
        while (it.hasNext()) {
            String schluessel = it.next();
            if (schluessel.startsWith(praefix)) {
                treffer.add(buch.get(schluessel));
            } else {
                break;
            }
        }
        adressbuch_VT2.Kontakt[] ergebnisse = new adressbuch_VT2.Kontakt[treffer.size()];
        treffer.toArray(ergebnisse);
        return ergebnisse;
    }

    /**
     * @return die momentane Anzahl der Eintr?ge in diesem Adressbuch.
     */
    public int gibAnzahlEintraege() {
        return anzahlEintraege;
    }

    /**
     * Entferne den Eintrag mit dem gegebenen Schluessel aus diesem Adressbuch.
     *
     * @param schluessel einer der Schluessel des Eintrags, der entfernt werden
     *                   soll.
     * @return den geloeschten Kontakt oder null
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public void deleteKontakt(String schluessel) {
        if (schluessel.isEmpty() || schluessel == "") {
            throw new IllegalArgumentException("Es muss ein Schluessel eingegeben werden");
        }
        if (schluessel == null) {
            throw new NullPointerException("Es wurde ein falscher Schluessel uebergeben");
        }
        adressbuch_VT2.Kontakt kontakt = buch.get(schluessel);
        if (kontakt == null) {
            throw new NullPointerException("Es wurde kein Kontakt mit diesem Schluessel gefunden");
        }
        buch.remove(kontakt.getName());
        buch.remove(kontakt.getTelefon());
        anzahlEintraege--;
    }

    /**
     * @return alle Kontaktdaten, sortiert nach der Sortierreihenfolge, die die
     * Klasse Kontakt definiert.
     */
    public String getAlleAlsText() {
        // Weil jeder Satz unter zwei Schluesseln eingetragen ist,
        // muss ein Set mit den Kontakten gebildet werden. Dadurch
        // werden Duplikate entfernt.
        StringBuffer alleEintraege = new StringBuffer();
        Set<adressbuch_VT2.Kontakt> sortierteDaten = new TreeSet<adressbuch_VT2.Kontakt>(buch.values());
        for (adressbuch_VT2.Kontakt kontakt : sortierteDaten) {
            alleEintraege.append(kontakt);
            alleEintraege.append('\n');
            alleEintraege.append('\n');
        }
        return alleEintraege.toString();
    }

    public adressbuch_VT2.Kontakt[] getAlleKontakte() {
        Set<adressbuch_VT2.Kontakt> sortierteDaten = new TreeSet<adressbuch_VT2.Kontakt>(buch.values());
        adressbuch_VT2.Kontakt[] ergebnisse = new adressbuch_VT2.Kontakt[sortierteDaten.size()];
        sortierteDaten.toArray(ergebnisse);
        return ergebnisse;
    }

    public void setTestData() {
        adressbuch_VT2.Kontakt[] testdaten = {
                new adressbuch_VT2.Kontakt("david", "08459 100000", "david@gmx.de"),
                new adressbuch_VT2.Kontakt("michael", "08459 200000", "michael@gmx.de"),
                new adressbuch_VT2.Kontakt("john", "08459 300000", "john@gmx.de"),
                new adressbuch_VT2.Kontakt("heike", "08459 400000", "heike@gmx.de"),
                new adressbuch_VT2.Kontakt("emma", "08459 500000", "emma@gmx.de"),
                new adressbuch_VT2.Kontakt("simone", "08459 600000", "simone@gmx.de"),
                new adressbuch_VT2.Kontakt("chris", "08459 700000", "chris@gmx.de"),
                new adressbuch_VT2.Kontakt("axel", "08459 800000", "axel@gmx.de"),};
        for (adressbuch_VT2.Kontakt kontakt : testdaten) {
            addKontakt(kontakt);
        }
    }
}
