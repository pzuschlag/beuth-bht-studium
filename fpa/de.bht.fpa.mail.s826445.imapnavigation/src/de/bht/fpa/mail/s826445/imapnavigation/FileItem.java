package de.bht.fpa.mail.s826445.imapnavigation;

import java.io.File;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s000000.common.mail.model.IMessageTreeItem;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class FileItem extends IMessageTreeItemAbstract {
  // public class FileItem implements IMessageTreeItem {

  private File file;

  /*
   * Konstruktor, erzeugt neue Datei
   * 
   */
  public FileItem(String path) {
    this.file = new File(path);
  }

  /*
   * Gibt den Namen der Datei zurück
   * 
   */
  @Override
  public String getText() {
    return file.getName();
  }

  /*
   * Holt sich das - zu der Datei gehörende - icon Gibt den ImageDescriptor
   * zurück
   * 
   */
  @Override
  public Image getImage() {
    return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/file.png").createImage();
  }

  /*
   * Gibt false zurück, da Dateien keine Kinder haben
   */
  @Override
  public boolean hasChildren() {
    return false;
  }

  /*
   * Gibt null zurück, da keine Kinder
   */
  @Override
  public List<IMessageTreeItem> getChildren() {
    return null;
  }

  /*
   * Gibt null zurück, da keine Kinder
   * 
   */
  @Override
  public List<Message> getMessages() {
    return null;
  }

  /*
   * Gibt den absoluten Pfad der Datei zurück
   * 
   */
  @Override
  public String getPath() {
    return file.getPath();
  }

}
