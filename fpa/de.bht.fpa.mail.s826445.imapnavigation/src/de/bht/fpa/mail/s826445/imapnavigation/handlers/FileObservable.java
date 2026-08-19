package de.bht.fpa.mail.s826445.imapnavigation.handlers;


import java.util.Observable;

public final class FileObservable extends Observable {

  private String path;
  private static FileObservable fileobs = null;

  /*
   * Gibt den neu erzeugten ObservableFile zurück
   * 
   */
  public static FileObservable getInstance() {
    if (fileobs == null) {
      fileobs = new FileObservable();
    }
    return fileobs;
  }

  /*
   * Setzt den aktuellen Pfad
   * 
   */
  public void setPath(String path) {
    setChanged();
    notifyObservers(path);
    this.path = path;
  }

  /*
   * Gibt den Pfad zurück
   * 
   */
  public String getPath() {
    return path;
  }

}
