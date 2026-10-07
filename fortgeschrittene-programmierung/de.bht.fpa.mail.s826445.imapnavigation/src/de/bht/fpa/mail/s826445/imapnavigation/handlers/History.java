package de.bht.fpa.mail.s826445.imapnavigation.handlers;


import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Der Handler extendet den AbstractHandler
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class History extends AbstractHandler {

  private static int HIST_MAX = 5;

  /*
   * Der Konstruktor
   * 
   */
  public History() {
  }

  /*
   * Es wird das der erste History Eintrag in der Preference Datei abgerufen und
   * geprüft ob schon Einträge vorhanden sind. Falls keiner vorhanden ist wird
   * eine entsprechende Fehlermeldung ausgegeben. Falls ein Eintrag vorhanden
   * ist, wird durch die Einträge iteriert und in einer Liste gespeichert. Die
   * Liste wird in einem JFace Dialog ausgegeben. Wenn der Dialog bestätigt
   * wird, wird die Ansicht mit dem erhaltetenen Pfad aktualisiert.
   * 
   */
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    String key = "History_" + 0;
    if (PreferencesHandler.getPref(key) == null) {
      MessageDialog dialogErr = new MessageDialog(window.getShell(), "Warning", null,
          "Sorry, no base directories in history", MessageDialog.ERROR, new String[] { "Close" }, 0);
      dialogErr.open();
    } else {
      ArrayList<String> history = new ArrayList<String>();
      for (int i = 0; i < HIST_MAX; i++) {
        key = "History_" + i;
        String path = PreferencesHandler.getPref(key);
        if (path != null) {
          history.add(path);
        }
      }
      ListDialog ld = new ListDialog(window.getShell());
      ld.setAddCancelButton(true);
      ld.setContentProvider(new ArrayContentProvider());
      ld.setLabelProvider(new LabelProvider());
      ld.setInput(history.toArray());
      ld.setInitialSelections(history.toArray());
      ld.setTitle("Select a recent base directory");
      ld.open();
      FileObservable file = FileObservable.getInstance();
      Object[] pathArray = ld.getResult();
      if (file != null && pathArray != null && pathArray.length != 0) {
        String path = (String) pathArray[0];
        if (path != null) {
          file.setPath(path);
          return path;
        }
      }
    }
    return null;
  }
}
