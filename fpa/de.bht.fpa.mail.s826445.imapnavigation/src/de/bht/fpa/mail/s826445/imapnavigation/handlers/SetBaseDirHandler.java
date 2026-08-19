package de.bht.fpa.mail.s826445.imapnavigation.handlers;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
//import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Der Handler extendet den AbstractHandler
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */

public class SetBaseDirHandler extends AbstractHandler {

  private int i = 0;
  private static int HIST_MAX = 5;

  /*
   * Der Konstruktor
   *
   */
  public SetBaseDirHandler() {
  }

  /*
   * Der gewählte Pfad wird in der Preference Datei gespeichert Das Fenster wird
   * mit der Ansicht des gewählten Pfades aktualisiert
   * 
   */
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    DirectoryDialog dialog = new DirectoryDialog(window.getShell());
    String choosenPath = dialog.open();
    if (choosenPath != null) {
      FileObservable file = FileObservable.getInstance();
      file.setPath(choosenPath);
      String baseDirKey = "BaseDir";
      PreferencesHandler.savePref(baseDirKey, choosenPath);
      String key = "History_" + (i);
      PreferencesHandler.savePref(key, choosenPath);
      i++;
      if (i == HIST_MAX) {
        i = 0;
      }
      return choosenPath;
    }
    return null;
  }
}
