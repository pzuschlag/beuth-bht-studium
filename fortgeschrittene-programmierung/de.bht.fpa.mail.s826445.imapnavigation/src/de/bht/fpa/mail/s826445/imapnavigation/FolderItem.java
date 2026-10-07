package de.bht.fpa.mail.s826445.imapnavigation;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s000000.common.mail.model.IMessageTreeItem;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class FolderItem extends IMessageTreeItemAbstract {
  // public class Folder implements IMessageTreeItem {

  private File folder;

  /*
   * Konstruktor, erzeugt neuen Ordner
   * 
   */
  public FolderItem(String path) {
    this.folder = new File(path);
  }

  /*
   * Gibt den Namen des Ordners zurück
   * 
   */
  @Override
  public String getText() {
    return folder.getName();
  }

  /*
   * Holt sich das - zu dem Ordner gehörende - icon Gibt den ImageDescriptor
   * zurück
   * 
   */
  @Override
  public Image getImage() {
    return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/folder.png").createImage();
  }

  /*
   * Gibt false zurück, wenn der Ordner Kinder hat und false vice versa
   * 
   */
  @Override
  public boolean hasChildren() {
    return getChildren() != null;
  }

  /*
   * Erzeugt Array zum Zwischenspeichern und speichert dort alle Kinder als
   * TreeItem (entweder Folder oder File)
   */
  @Override
  public List<IMessageTreeItem> getChildren() {
    File[] files = folder.listFiles();
    if (files == null) {
      return null;
    }
    ArrayList<IMessageTreeItem> children = new ArrayList<IMessageTreeItem>();
    for (int i = 0; i < files.length; i++) {
      if (files[i].isDirectory() && !files[i].getName().startsWith(".")) {
        children.add(new FolderItem(files[i].getPath()));
        // } else {
        // children.add(new FileItem(files[i].getPath()));
      }
    }
    return children;
  }

  /*
   * Gibt eine Liste mit - geprüften - Message Objekten zurück Prüft ob in dem
   * Ordner Dateien sind Prüft ob die Datei eine xml Datei ist
   */
  @Override
  public List<Message> getMessages() {
    File[] files = folder.listFiles();
    if (files == null) {
      return null;
    }
    ArrayList<Message> messages = new ArrayList<Message>();
    FileFilter ff = new FileFilter();
    for (int i = 0; i < files.length; i++) {
      if (ff.accept(files[i])) {
        try {
          Message msg = JAXB.unmarshal(files[i], Message.class);
          if (msg.getId() != null) {
            messages.add(msg);
          }
        } catch (Exception ex) {
          // Exception ignorieren: wird geworfen wenn keine xml Datei gewählt
          // wurde
        }
      }
    }
    return messages;
  }

  /*
   * Gibt den absoluten Pfad des Ordners zurück
   * 
   */
  @Override
  public String getPath() {
    return folder.getPath();
  }

}
