package de.bht.fpa.mail.s826445.imapnavigation;

//import org.eclipse.jface.viewers.IBaseLabelProvider;
//import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s000000.common.mail.model.IMessageTreeItem;

public class IMAPLabelProvider extends LabelProvider {

  /*
   * Gibt den Text-Inhalt des Objekts zurück
   * 
   */
  @Override
  public String getText(Object element) {
    if (element instanceof IMessageTreeItem) {
      IMessageTreeItem s = (IMessageTreeItem) element;
      return s.getText();
    }
    return null;
  }

  /*
   * Gibt den Bild-Inhalt des Objekts zurück
   * 
   */
  @Override
  public Image getImage(Object element) {
    if (element instanceof IMessageTreeItem) {
      IMessageTreeItem s = (IMessageTreeItem) element;
      return s.getImage();
    }
    return null;
  }

}
