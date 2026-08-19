package adressbuch_VT2;

/**
 * Created by Marlon on 26.04.2017.
 */
public class KeinPassenderKontaktException extends UngueltigerSchluesselException{

    public String schluessel;

    public KeinPassenderKontaktException(String schluessel){
        super(schluessel);

    }
    public String getSchluessel(){
        return schluessel;
    }

    public String toString(){
        return "Der übergebene Schlüssel: " + schluessel + " ist ungültig!";
    }



}
