package adressbuch_VT1;

import adressbuch_VT2.AdressbuchTexteingabe;

/**
 * Eine einfache Demo der Klasse Adressbuch.
 * Testdaten werden in das Adressbuch eingetragen und
 * und dem Benutzer wird eine Interaktion erm�glicht.
 * 
 * @author David J. Barnes und Michael K�lling.
 * @version 2008.03.30
 */
public class AdressbuchDemo
{
    private adressbuch_VT2.Adressbuch buch;
    private adressbuch_VT2.AdressbuchTexteingabe interaktion;

    /**
     * Lege ein Adressbuch mit Testdaten an.
     * 
     * Das Adressbuch wird an eine textuelle Benutzungsschnittstelle
     * weitergegeben, um die interaktive Manipulation zu erm�glichen.
     */
    public AdressbuchDemo()
    {
        buch = new adressbuch_VT2.Adressbuch();
        interaktion = new AdressbuchTexteingabe(buch);
    }
    
    /**
     * Ermoegliche dem Benutzer die Interaktion mit dem Adressbuch.
     */
    public void zeigeSchnittstelle()
    {
        interaktion.starten();
    }

    /**
     * @return das Adressbuch mit den Testdaten.
     */
    public adressbuch_VT2.Adressbuch gibAdressbuch()
    {
        return buch;
    }
    
    public static void main(String[] args) {
        adressbuch_VT2.AdressbuchDemo demo = new adressbuch_VT2.AdressbuchDemo();
        demo.zeigeSchnittstelle();
    }
}
