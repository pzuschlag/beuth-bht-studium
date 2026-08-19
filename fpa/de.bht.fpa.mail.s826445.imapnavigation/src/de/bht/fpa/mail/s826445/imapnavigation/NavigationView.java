package de.bht.fpa.mail.s826445.imapnavigation;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
// import org.osgi.service.prefs.BackingStoreException;
// import org.osgi.service.prefs.Preferences;

import de.bht.fpa.mail.s826445.imapnavigation.handlers.FileObservable;
// import de.bht.fpa.mail.s826445.imapnavigation.handlers.SampleHandler;
import de.bht.fpa.mail.s826445.imapnavigation.handlers.PreferencesHandler;

public class NavigationView extends ViewPart implements Observer {

	// Muss in Manifest von imapnavigation unter Extensions als view hinuzugefügt
	// werden

	// private static Composite p;
	private FileObservable file;
	private TreeViewer viewer;

	/*
	 * Erstellt den Treeviewer zur Anzeige der Ordner-/Dateistruktur Lädt das zuletzt angewählte Verzeichnis aus einer
	 * Preference Datei
	 * 
	 */
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.NONE);
		viewer.setContentProvider(new IMAPContentProvider()); // was anzeigen

		viewer.setLabelProvider(new IMAPLabelProvider()); // wie anzeigen

		String userDir = PreferencesHandler.getPref("BaseDir");

		if (userDir == null) {
			userDir = System.getProperty("user.home");
		}

		// IStatusLineManager statusLineManager =
		// getViewSite().getActionBars().getStatusLineManager();
		// statusLineManager.setMessage("Loaded last directory from preferences: " +
		// userDir);

		file = FileObservable.getInstance();
		file.addObserver(this);
		viewer.setInput(new FolderItem(userDir)); // Start-Element -
		// root-Element
		getSite().setSelectionProvider(viewer);
	}

	// ________________________________________________________________________________________________

	/*
	 * Soll: Den Fokus auf Element setzen Ist: Nicht implementiert
	 * 
	 */
	@Override
	public void setFocus() {
	}

	// ________________________________________________________________________________________________

	/*
	 * Aktualisiert den Viewer
	 * 
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		viewer.setSelection(StructuredSelection.EMPTY);
		String path = (String) arg1;
		if (path != null) {
			viewer.setInput(new FolderItem(path));
		}
	}

}
