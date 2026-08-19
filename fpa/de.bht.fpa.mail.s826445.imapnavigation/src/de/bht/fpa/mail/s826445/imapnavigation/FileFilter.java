package de.bht.fpa.mail.s826445.imapnavigation;

import java.io.File;

public class FileFilter implements java.io.FileFilter {

  /*
   * Gibt True zurück, wenn die Datei (der Pfadname) mit .xml endet
   * 
   */
  @Override
  public boolean accept(File pathname) {
    return pathname.getPath().endsWith(".xml");
  }

}
