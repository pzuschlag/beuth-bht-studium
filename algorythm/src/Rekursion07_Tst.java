
// Datei Rekursion07_Tst.java
/* ------------------------------------------------------------------------
Dieses Programm Rekursion07_Tst enthaelt die rekursive Methode binSuch
und testet sie.

Die Methode sucht in einer sortierten Reihungen eine bestimmte Komponente.
------------------------------------------------------------------------ */
import java.util.Arrays;

class Rekursion07_Tst {
   // ---------------------------------------------------------------------
   static int binSuch(long[] r, long n, int von, int bis) {
      // Verlaesst sich darauf, dass
      // 1. r keine Doppelgaenger enthaelt (der Einfachheit halber)
      // 2. r aufsteigend sortiert ist
      // 3. von und bis Indizes der Reihung r sind.
      // Sucht in der Teilreihung r[von..bis] nach n.
      // Wird n gefunden, so wird der Index i von n (d.h. der Index i fuer
      // den n == r[i] gilt) als Ergebnis geliefert.
      // Sonst wird der Index i geliefert, an dem n eingefuegt werden
      // sollte (damit r nach dem Einfuegen weiterhin sortiert ist).
      //
      // Hinweis: Wenn n groesser ist als alle Komponenten der Teilreihung
      // r[von..bis], dann wird bis+1 als Ergebnis geliefert. bis+1 ist
      // offensichtlich kein Index der Teilreihung r[von..bis] und
      // moeglicherweise auch groesser als alle Indizes der Reihung r.
      //
      // Beispiele:
      // long[]    r1 = {10, 20, 30, 40, 50, 60, 70, 80, 90};
      // final int GI = r1.length-1; // Groesster Index von r1
      // binSuch(r1, 10, 0, GI) ist gleich 0
      // binSuch(r1, 20, 0, GI) ist gleich 1
      // binSuch(r1, 30, 0, GI) ist gleich 2
      // binSuch(r1, 90, 0, GI) ist gleich 8
      // binSuch(r1,  5, 0, GI) ist gleich 0
      // binSuch(r1, 15, 0, GI) ist gleich 1
      // binSuch(r1, 25, 0, GI) ist gleich 2
      // binSuch(r1, 35, 0, GI) ist gleich 3
      // binSuch(r1, 95, 0, GI) ist gleich 9 // 9 ist kein Index von r1

      return -1; // MUSS ERSETZT WERDEN
   }
   // ---------------------------------------------------------------------
   static long[] r1        = {10, 20, 30, 40, 50, 60, 70, 80, 90};
   static int    anzFehler = 0;
   // ---------------------------------------------------------------------
   static private void tst(long n, int von, int bis, int soll) {
      int ist = binSuch(r1, n, von, bis);

      String text = " <--- OK";
      if (ist != soll) {
         text = " <--- Fehler?";
         anzFehler++;
      }

      printf("binSuch(r1, %2d, %d, %d), ist: %d, soll:%d %s%n",
         n, von, bis, ist, soll, text);
   }
   // ---------------------------------------------------------------------
   static public void main(String[] args) {
      printf("Rekursion07_Tst: Jetzt geht es los!%n");
      printf("-------------------------------------%n");

      printf("r1: %s%n", Arrays.toString(r1));
      printf("-------------------------------------%n");
      printf("Komponenten, die in r1 vorhanden sind:%n%n");
      tst(10, 0, 8, 0);
      tst(20, 0, 8, 1);
      tst(30, 0, 8, 2);
      tst(40, 0, 8, 3);
      tst(50, 0, 8, 4);
      tst(60, 0, 8, 5);
      tst(70, 0, 8, 6);
      tst(80, 0, 8, 7);
      tst(90, 0, 8, 8);
      printf("-------------------------------------%n");
      printf("Komponenten, die in r1 NICHT vorhanden sind:%n%n");
      tst( 5, 0, 8, 0);
      tst(15, 0, 8, 1);
      tst(25, 0, 8, 2);
      tst(35, 0, 8, 3);
      tst(45, 0, 8, 4);
      tst(55, 0, 8, 5);
      tst(65, 0, 8, 6);
      tst(75, 0, 8, 7);
      tst(85, 0, 8, 8);
      tst(95, 0, 8, 9);
      printf("-------------------------------------%n");
      printf("Komponenten, die in r1[2..6] vorhanden sind:%n%n");
      tst(30, 2, 6, 2);
      tst(40, 2, 6, 3);
      tst(50, 2, 6, 4);
      tst(60, 2, 6, 5);
      tst(70, 2, 6, 6);
      printf("-------------------------------------%n");
      printf("Komponenten, die in r1[2..6] NICHT vorhanden sind:%n%n");
      tst(25, 2, 6, 2);
      tst(35, 2, 6, 3);
      tst(45, 2, 6, 4);
      tst(55, 2, 6, 5);
      tst(65, 2, 6, 6);
      tst(75, 2, 6, 7);
      printf("-----------------------------------%n");
      printf("Anzahl Fehler: %d%n", anzFehler);
      printf("-----------------------------------%n");
      printf("Rekursion07_Tst: Das war's erstmal!%n%n");
   } // main
   // ---------------------------------------------------------------------
   // Eine Methode mit einem kurzen Namen:
   static void printf(String f, Object... v) {System.out.printf(f, v);}
   // ---------------------------------------------------------------------
} // class Rekursion07_Tst
/* ------------------------------------------------------------------------
Ausgabe (wenn alles OK ist):

Rekursion07_Tst: Jetzt geht es los!
-------------------------------------
r1: [10, 20, 30, 40, 50, 60, 70, 80, 90]
-------------------------------------
Komponenten, die in r1 vorhanden sind:

binSuch(r1, 10, 0, 8), ist: 0, soll:0  <--- OK
binSuch(r1, 20, 0, 8), ist: 1, soll:1  <--- OK
binSuch(r1, 30, 0, 8), ist: 2, soll:2  <--- OK
binSuch(r1, 40, 0, 8), ist: 3, soll:3  <--- OK
binSuch(r1, 50, 0, 8), ist: 4, soll:4  <--- OK
binSuch(r1, 60, 0, 8), ist: 5, soll:5  <--- OK
binSuch(r1, 70, 0, 8), ist: 6, soll:6  <--- OK
binSuch(r1, 80, 0, 8), ist: 7, soll:7  <--- OK
binSuch(r1, 90, 0, 8), ist: 8, soll:8  <--- OK
-------------------------------------
Komponenten, die in r1 NICHT vorhanden sind:

binSuch(r1,  5, 0, 8), ist: 0, soll:0  <--- OK
binSuch(r1, 15, 0, 8), ist: 1, soll:1  <--- OK
binSuch(r1, 25, 0, 8), ist: 2, soll:2  <--- OK
binSuch(r1, 35, 0, 8), ist: 3, soll:3  <--- OK
binSuch(r1, 45, 0, 8), ist: 4, soll:4  <--- OK
binSuch(r1, 55, 0, 8), ist: 5, soll:5  <--- OK
binSuch(r1, 65, 0, 8), ist: 6, soll:6  <--- OK
binSuch(r1, 75, 0, 8), ist: 7, soll:7  <--- OK
binSuch(r1, 85, 0, 8), ist: 8, soll:8  <--- OK
binSuch(r1, 95, 0, 8), ist: 9, soll:9  <--- OK
-------------------------------------
Komponenten, die in r1[2..6] vorhanden sind:

binSuch(r1, 30, 2, 6), ist: 2, soll:2  <--- OK
binSuch(r1, 40, 2, 6), ist: 3, soll:3  <--- OK
binSuch(r1, 50, 2, 6), ist: 4, soll:4  <--- OK
binSuch(r1, 60, 2, 6), ist: 5, soll:5  <--- OK
binSuch(r1, 70, 2, 6), ist: 6, soll:6  <--- OK
-------------------------------------
Komponenten, die in r1[2..6] NICHT vorhanden sind:

binSuch(r1, 25, 2, 6), ist: 2, soll:2  <--- OK
binSuch(r1, 35, 2, 6), ist: 3, soll:3  <--- OK
binSuch(r1, 45, 2, 6), ist: 4, soll:4  <--- OK
binSuch(r1, 55, 2, 6), ist: 5, soll:5  <--- OK
binSuch(r1, 65, 2, 6), ist: 6, soll:6  <--- OK
binSuch(r1, 75, 2, 6), ist: 7, soll:7  <--- OK
-------------------------------------
Anzahl Fehler: 0
-------------------------------------
Rekursion07_Tst: Das war's erstmal!
------------------------------------------------------------------------ */