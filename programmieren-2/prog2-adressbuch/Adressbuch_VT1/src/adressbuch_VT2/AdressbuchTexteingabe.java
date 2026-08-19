package adressbuch_VT2;

/**
 * Eine textuelle Schnittstelle fuer ein Adressbuch.
 * ueber verschiedene Befehle kann auf das Adressbuch
 * zugegriffen werden.
 *
 * @author David J. Barnes und Michael K�lling.
 * @version 2008.03.30
 */
public class AdressbuchTexteingabe
{
    // Das Adressbuch, das angezeigt und manipuliert werden soll.
    private Adressbuch buch;
    // Ein Parser fuer die Befehlswoerter.
    private adressbuch_VT2.Parser parser;
    
    /**
     * Konstruktor fuer Objekte der Klasse AdressbuchTexteingabe.
     * @param buch das Adressbuch, das manipuliert werden soll.
     */
    public AdressbuchTexteingabe(Adressbuch buch)
    {
        this.buch = buch;
        parser = new adressbuch_VT2.Parser();
    }
    
    /**
     * Lies interaktiv Befehle vom Benutzer, die 
     * Interaktionen mit dem Adressbuch ermoeglichen.
     * Stoppe, wenn der Benutzer 'ende' eingibt.
     */
    public void starten()
    {
        System.out.println(" -- Adressbuch --");
        System.out.println("Tippen Sie 'hilfe' fuer eine Liste der Befehle.");
        
        String command = "";
        
        while(!(command.equals("ende"))) {
            command = parser.liefereBefehl();
            if(command.equals("neu")){ 
                neuerEintrag();
            }
            else if(command.equals("liste")){
                list();
            }
            else if(command.equals("suche")){
                sucheEintrag();
            }
            else if(command.equals("hilfe")){
                hilfe();
            }
            else if(command.equals("hole")){
                holeEintrag();
            }
            else if(command.equals("entferne")){
                entferneEintrag();
            }
            else if(command.equals("aendere")){
                aendereEintrag();
            }
            else{
                // nichts tun.
            }
        } 

        System.out.println("Ade.");
    }
    
    /**
     * Fuege einen neuen Eintrag hinzu.
     */
    private void neuerEintrag()
    {
        try {
            buch.addKontakt(kontaktEinlesen());
        }
        catch(IllegalStateException e) {
            System.out.println(e.getMessage());
            neuerEintrag();
        }

    }
    
    /**
     * Finde Eintraege mit passendem Praefix.
     */
    private void sucheEintrag()
    {
        System.out.println("Praefix des Suchschluessels:");
        String praefix = parser.zeileEinlesen();
        adressbuch_VT2.Kontakt[] ergebnisse = buch.getKontakte(praefix);
        for(int i = 0; i < ergebnisse.length; i++){
            System.out.println(ergebnisse[i]);
            System.out.println("=====");
        }
    }
    
    private void holeEintrag() {
        System.out.println("Schluessel fuer den Eintrag:");
        String schluessel = parser.zeileEinlesen();
        Kontakt ergebnis = null;
        try {
            ergebnis = buch.getKontakt(schluessel);
        } catch (UngueltigerSchluesselException e) {
            e.printStackTrace();
        }
        if(ergebnis != null) {
            System.out.println("Der gesuchte Kontakt lautet:");
            System.out.println(ergebnis);
        }
        else System.out.println("Kein Kontakt gefunden");
        System.out.println();
    }
    
    private void entferneEintrag() {
        System.out.println("Schluessel fuer den Eintrag:");
        String schluessel = parser.zeileEinlesen();
        try {
            buch.deleteKontakt(schluessel);
        }
        catch(NullPointerException e) {
            System.out.println(e.getMessage());
        } catch (KeinPassenderKontaktException e) {
            e.printStackTrace();
        }
    }
    
    private void aendereEintrag() {
        String schluessel = "";
        boolean falsch = true;
        System.out.println("Schlüssel des zu aendernden Kontakts:");
        schluessel = parser.zeileEinlesen();
        falsch = true;
        // Kontakt einlesen wiederholen, bis Daten korrekt eingegeben wurden
        while(falsch) {
            try {
                adressbuch_VT2.Kontakt neuerKontakt = kontaktEinlesen();
                buch.updateKontakt(schluessel, neuerKontakt);
                falsch = false;
            }
            catch (IllegalStateException e){
                System.out.println(e.getMessage());
            } catch (KeinPassenderKontaktException e) {
                e.printStackTrace();
            } catch (UngueltigerSchluesselException e) {
                e.printStackTrace();
            }
        }

    }
    
    private adressbuch_VT2.Kontakt kontaktEinlesen() {
        adressbuch_VT2.Kontakt k;
        System.out.print("Name: ");
        String name = parser.zeileEinlesen();
        System.out.print("Telefon: ");
        String telefon = parser.zeileEinlesen();
        System.out.print("Email: ");
        String adresse = parser.zeileEinlesen();
        k = new adressbuch_VT2.Kontakt(name, telefon, adresse);
        return k;
    }
    
    /**
     * Zeige die zur Verfuegung stehenden Befehle.
     */
    private void hilfe()
    {
        parser.zeigeBefehle();
    }
    
    /**
     * Gib den Inhalt des Adressbuchs aus.
     */
    private void list()
    {
        System.out.println(buch.getAlleAlsText());
    }
}
