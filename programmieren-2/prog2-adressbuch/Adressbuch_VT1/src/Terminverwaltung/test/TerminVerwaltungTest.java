package Terminverwaltung.test;


import Terminverwaltung.src.terminverwaltung.Termin;
import Terminverwaltung.src.terminverwaltung.TerminUeberschneidungException;
import Terminverwaltung.src.terminverwaltung.TerminVerwaltung;
import Terminverwaltung.src.terminverwaltung.UngueltigerTerminException;
import org.junit.Test;
import org.junit.Before;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Marlon on 15.05.2017.
 */

public class TerminVerwaltungTest {

    // Standardkonstruktor
    public TerminVerwaltungTest(){

    }



    private TerminVerwaltung verwaltung;
    private Termin t1;
    private Termin t2;
    private Termin t3;

    @Before
    public void setUp() throws Exception {
        LocalDate date = LocalDate.of(2014, 5, 19);
        verwaltung = new TerminVerwaltung();
        t1 = new Termin("Termin1", date, LocalTime.of(9, 0), LocalTime.of(10, 0));
        t2 = new Termin("Termin2", date, LocalTime.of(10, 30), LocalTime.of(11, 0));
        t3 = new Termin("Termin3", date, LocalTime.of(11, 15), LocalTime.of(12, 30));
        verwaltung.addTermin(t1);
        verwaltung.addTermin(t2);
        verwaltung.addTermin(t3);
    }


    @Test
    public void getTermineTag() throws Exception {
    }





    @Test
    public void addTermin() throws Exception {

    }

    @Test
    public void updateTermin() throws Exception {
    }

    @Test
    public void removeTermin() throws Exception {
    }

    @Test(expected = TerminUeberschneidungException.class)
    public void checkTerminUeberschneidung() throws Exception {
        Termin t = t1.getCopy();
        try {
            t.setVonBis(t2.getVon(), t3.getBis());
        } catch (UngueltigerTerminException e) {
            e.printStackTrace();
        }
        termineAusgeben(verwaltung.getAllTermine());
        System.out.println(verwaltung.updateTermin(t));

    }

    @Test
    public void getTermin() throws Exception {
    }

    @Test
    public void getAllTermine() throws Exception {
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


}
