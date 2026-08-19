package adressbuch_VT2;

/**
 * Created by Marlon on 24.04.2017.
 */
public class UngueltigerSchluesselException extends Exception {

    public String schluessel;

    public UngueltigerSchluesselException(String schluessel) {
        this.schluessel = schluessel;
    }



    public String getSchluessel() {
        return schluessel;
    }
        public String toString(){
        return "Der Schlüssel " + schluessel + " ist ungültig";
        }
}