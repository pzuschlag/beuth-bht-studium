package Terminverwaltung.src.terminverwaltung;

import java.time.LocalDate;
import java.time.LocalTime;

/**
<<<<<<< HEAD
=======
 *
>>>>>>> origin/check3
 * @author beuth
 */
public class TesteTerminVerwaltung {

    private TerminVerwaltung verwaltung;
    private Termin t1;
    private Termin t2;
    private Termin t3;

    /**
     * Pruefe Basisfunktionalitaet, indem Termine am Tagesanfang, am Tagesende
     * und in der Mitte vereinbart werden.
     */
    public void dreiTermineHinzufuegen() {
        Termin[] allTermine;
        verwaltung = new TerminVerwaltung();
        System.out.println("Teste Hinzufügen von 3 Terminen");
        System.out.println();
        allTermine = verwaltung.getAllTermine();
        System.out.println("Vor dem Hinzufuegen: \n");
        termineAusgeben(allTermine);
        try {
            LocalDate date = LocalDate.of(2014, 5, 19);
            t1 = new Termin("Termin1", date, LocalTime.of(9, 0), LocalTime.of(10, 0));
            t2 = new Termin("Termin2", date, LocalTime.of(10, 30), LocalTime.of(11, 0));
            t3 = new Termin("Termin3", date, LocalTime.of(11, 15), LocalTime.of(12, 30));
            verwaltung.addTermin(t1);
            verwaltung.addTermin(t2);
            verwaltung.addTermin(t3);
        } catch (UngueltigerTerminException ex) {
            System.out.println(ex);
        }
        allTermine = verwaltung.getAllTermine();
        System.out.println("Nach dem Hinzufuegen: \n");
        termineAusgeben(allTermine);
    }

    /**
     * Pruefe, dass eine Terminueberschneidung erkannt wird.
     */
    public void testeTerminueberschneidung() {
        Termin[] allTermine;
        System.out.println("Teste Terminueberschneidung \n");
        System.out.println();
        try {
            dreiTermineHinzufuegen();
            LocalDate date = LocalDate.of(2014, 5, 19);
            // Erzeugung eines Termins mit Ueberschneidung
            Termin neu = new Termin("Neuer Termin", date, LocalTime.of(9, 30), LocalTime.of(12, 0));
            verwaltung.addTermin(neu);
        } catch (UngueltigerTerminException ex) {
            System.out.println(ex);
        }
        allTermine = verwaltung.getAllTermine();
        System.out.println("Nach dem Hinzufuegen des neuen Termins: \n");
        termineAusgeben(allTermine);
    }


    public void testeTerminUpdate() {
        // 1. neue terminverwaltung erstellen
        verwaltung = new TerminVerwaltung();
        Termin[] allTermine;
        Termin ersterTermin;

        System.out.println("Teste Terminupdate: \n");
        System.out.println();

        //*POSITIV-TEST*//
        try {
            // 2. Dummytermine hinzufuegen
            dreiTermineHinzufuegen();
            allTermine = verwaltung.getAllTermine();

            // 3. Den ersten Termin nehmen, dort die Beschreibung ändern und damit updaten
            ersterTermin = allTermine[0];
            ersterTermin.setText("Testaenderung");
            verwaltung.updateTermin(ersterTermin);

        } catch (TerminUeberschneidungException ex) {
            System.out.println(ex);
        }
        allTermine = verwaltung.getAllTermine();
        System.out.println("Nach dem Updaten der Beschreibung: \n");
        termineAusgeben(allTermine);

                        /*NEGATIV-TEST*/
        try {


            allTermine = verwaltung.getAllTermine();
            Termin[] allTermineKopie = allTermine.clone();

            // 3. Den ersten Termin nehmen, dort die Zeit ändern und damit updaten
            ersterTermin = allTermineKopie[0];
            ersterTermin.setVonBis(LocalTime.of(10, 30), LocalTime.of(11, 0));
            verwaltung.updateTermin(ersterTermin);

        } catch (UngueltigerTerminException ex) {
            System.out.println(ex);
        }
        allTermine = verwaltung.getAllTermine();
        System.out.println("Nach dem Updaten der Zeit: \n");
        termineAusgeben(allTermine);
    }



    
    /**
     * Pruefe, ob eine Terminaenderung, die erlaubt ist, erfolgreich durchgefuehrt wird.
     */
    public void testeTerminAendernPositiv() {
        
    }
    
    /**
     * Pruefe, ob eine Terminaenderung, die nicht erlaubt ist, scheitert.
     */
    public void testeTerminAendernNegativ() {
        
    }

    

    private void termineAusgeben(Termin[] termine) {
        if (termine == null || termine.length == 0) {
            System.out.println("Keine Termine\n");
        } else {
            for (Termin t : termine) {
                System.out.println(t.getInfo());
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        TesteTerminVerwaltung tests = new TesteTerminVerwaltung();

        //tests.dreiTermineHinzufuegen();

        //tests.testeTerminueberschneidung();
        //tests.testeTerminAendernPositiv();
        tests.testeTerminUpdate();


        tests.testeTerminueberschneidung();
        //tests.testeTerminAendernPositiv();
        //tests.testeTerminAendernNegativ();

    }

}
