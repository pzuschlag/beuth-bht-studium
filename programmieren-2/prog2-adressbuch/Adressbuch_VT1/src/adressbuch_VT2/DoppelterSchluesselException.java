package adressbuch_VT2;

/**
 * Created by Marlon on 26.04.2017.
 */
public class DoppelterSchluesselException extends UngueltigerSchluesselException{

    public String schluessel;

    public DoppelterSchluesselException(String schluessel) {
        super(schluessel);

    }

    public String getSchluessel (){
        return schluessel;
    }
    public String toString(){
        return "zu dem eingebenen Schlüssel" + this.getSchluessel() + " gibt es bereits einen Kontakt";
    }
}
