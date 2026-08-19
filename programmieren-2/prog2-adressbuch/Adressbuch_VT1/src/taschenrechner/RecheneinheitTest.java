package taschenrechner;



import org.junit.Before;
import org.testng.annotations.Test;

import java.security.SecureRandom;

import static org.junit.Assert.*;

/**
 * Created by Marlon on 05.06.2017.
 */

public class RecheneinheitTest {
    private Recheneinheit recheneinheit;
    SecureRandom rnd;

    @Before
    public void setUp() throws Exception {
        recheneinheit = new Recheneinheit();
        rnd = new SecureRandom();

    }

    @Test
    public void rndAmountOfTests() throws Exception {
        for (int i = 0; i < rnd.nextInt(5) + 10; i++) {
            plus();
            minus();
        }
    }

    @Test
    public void plus() throws Exception {
        recheneinheit.clear();
        int b = 2;
        int a = 3;
        recheneinheit.zifferGetippt(a);
        recheneinheit.plus();
        recheneinheit.zifferGetippt(b);
        recheneinheit.gleich();
        assertTrue(a + b == recheneinheit.gibAnzeigewert());
    }

    @Test
    public void minus() throws Exception {
        recheneinheit.clear();
        int b = rnd.nextInt(10);
        int a = rnd.nextInt(10);
        recheneinheit.zifferGetippt(a);
        recheneinheit.minus();
        recheneinheit.zifferGetippt(b);
        recheneinheit.gleich();
        assertTrue(a-b == recheneinheit.gibAnzeigewert());
    }

    @Test
    public void speicherEingabe() throws Exception{
        plus();
        int val = recheneinheit.gibAnzeigewert();
        recheneinheit.speicherEinlesen(val);
        assertTrue(val == recheneinheit.gibSpeicher());
        assertTrue(recheneinheit.gibAnzeigewert() == 0);
    }

    @Test
    public void speicherAuslesen() throws Exception{
        int val = recheneinheit.gibSpeicher();
        recheneinheit.speicherAuslesen();
        assertTrue(recheneinheit.gibAnzeigewert() == val);
    }


}
