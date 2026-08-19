package de.bht.fpa.mail.s826445.fsnavigation;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import de.bht.fpa.mail.s826445.fsnavigation.handlers.SetBaseDirHandler;
import de.bht.fpa.mail.s826445.fsnavigation.handlers.SingletonFile;

/**
 * TreeViewer
 */
public class NavigationView extends ViewPart implements ISelectionListener, Observer {

	private TreeViewer viewer;

	/**
	 * Erzeugen der View
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		Preferences pref = SetBaseDirHandler.getPrefs();

		String startDir = null;
		try {
			startDir = pref.get(pref.keys().length - 1 + "", System.getProperty("user.home"));
		} catch (BackingStoreException e) {
		}

		// Alternative Statusbar
		// IStatusLineManager statusLineManager = getViewSite().getActionBars().getStatusLineManager();
		// statusLineManager.setMessage("Directory: " + startDir + " was selected");

		viewer = new TreeViewer(parent);
		viewer.setContentProvider(new NsNavigationContentProvider());
		viewer.setLabelProvider(new FsNavigationLabel());
		viewer.setInput(new FolderItem(new File(startDir)));
		// viewer.addSelectionChangedListener(this);
		getSite().setSelectionProvider(viewer);

		SingletonFile.getInstance().addObserver(this);
	}

	/**
	 * Nicht Implementiert.
	 */
	@Override
	public void setFocus() {
	}

	/**
	 * Aktualisiert den Viewer
	 */
	@Override
	public void update(Observable arg0, Object givenPath) {
		String path = (String) givenPath;
		if (path != null) {
			viewer.setInput(new FolderItem(new File(path)));
		}
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
	}
}