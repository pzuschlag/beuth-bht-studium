package de.bht.fpa.mail.s826445.imapnavigation;

//import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import de.bht.fpa.mail.s000000.common.mail.model.IMessageTreeItem;

public class IMAPContentProvider implements ITreeContentProvider {

  /*
   * Soll: Ordnet die Elemente an Ist: Nicht implementiert
   * 
   */
  @Override
  public void dispose() {
  }

  /*
   * Soll: Verarbeitet veränderteten Input Ist: Nicht implementiert
   * 
   */
  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
  }

  /*
   * bekommt von .setInput aus NavigationView das root-Element (Folder)
   * übergeben und holt sich Kinder-Elemente
   * 
   */
  @Override
  public Object[] getElements(Object inputElement) {
    return this.getChildren(inputElement);
  }

  /*
   * holt sich alle Kind-Element zu einem Elemente; ruft .getChildren in Element
   * auf
   * 
   */
  @Override
  public Object[] getChildren(Object parentElement) {
    if (parentElement instanceof IMessageTreeItem) {
      IMessageTreeItem s = (IMessageTreeItem) parentElement;
      return s.getChildren().toArray();
    }
    return null;
  }

  /*
   * Soll: holt sich das Eltern-Element zu einem Element Ist: gibt das
   * übergebene Element zurück
   */
  @Override
  public Object getParent(Object element) {
    return element;
  }

  /*
   * Gibt false zurück, wenn Kinder vorhanden und false vice versa
   * 
   */
  @Override
  public boolean hasChildren(Object element) {
    if (element instanceof IMessageTreeItem) {
      IMessageTreeItem s = (IMessageTreeItem) element;
      return s.hasChildren();
    }
    return false;
  }

}
