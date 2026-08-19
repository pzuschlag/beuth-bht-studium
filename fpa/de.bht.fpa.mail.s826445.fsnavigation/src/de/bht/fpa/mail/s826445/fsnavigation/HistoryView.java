package de.bht.fpa.mail.s826445.fsnavigation;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import de.bht.fpa.mail.s826445.fsnavigation.handlers.SetBaseDirHandler;
import de.bht.fpa.mail.s826445.fsnavigation.handlers.SingletonFile;

/**
 * Speichert die zuvor gewählten Pfade der TreeView
 */
public class HistoryView extends Dialog {

	private Preferences prefs = SetBaseDirHandler.getPrefs();
	private ListViewer listViewer;

	/**
	 * Konstruktor.
	 * 
	 * @param shell
	 */
	public HistoryView(Shell shell) {
		super(shell);
	}

	/**
	 * The method overrides the inherited method from Dialog
	 * 
	 * @return ArrayList with Path-Strings from Preferences
	 */
	@Override
	protected Control createDialogArea(Composite parent) {

		listViewer = new ListViewer(parent);
		listViewer.getList().setSize(400, 300);

		for (String lastpath : getHistory()) {
			listViewer.add(lastpath);
		}

		return createDialogArea(parent);
	}

	/**
	 * Liefert alle Elemente als Array-List
	 * 
	 * @return ArrayList with Path-Strings from Preferences
	 */
	private ArrayList<String> getHistory() {
		ArrayList<String> historyList = new ArrayList<>();

		try {
			for (String k : prefs.keys()) {
				historyList.add(prefs.get(k, ""));
			}
			Collections.reverse(historyList);
		} catch (BackingStoreException e) {
			e.getStackTrace();
		}

		return historyList;
	}

	/**
	 * Ueberschreiben der okPressed()-Methode.
	 */
	@Override
	protected void okPressed() {
		SingletonFile file = SingletonFile.getInstance();
		String path = listViewer.getSelection().toString();
		path = path.substring(1, path.length() - 1);
		file.setpath(path);

		close();
	}
}