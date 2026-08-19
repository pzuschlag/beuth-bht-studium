package de.bht.fpa.mail.s826445.statusbar;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import de.bht.fpa.mail.s826445.fsnavigation.FolderItem;

/**
 * StatusbarSelectionListener
 */
public class StatusBarSelection implements ISelectionListener {

	// StatusLine
	private IStatusLineManager statusLineManager;

	/**
	 * Konstruktor
	 */
	public StatusBarSelection(final IStatusLineManager statusLineManager) {
		this.statusLineManager = statusLineManager;
	}

	/**
	 * Ändert die Statusbar, wenn die Auswahl geändert wird.
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection iSelection = (IStructuredSelection) selection;
			Object firstObject = iSelection.getFirstElement();
			if (firstObject instanceof FolderItem && firstObject != null) {
				FolderItem folder = (FolderItem) firstObject;
				String path = folder.getPath();
				statusLineManager.setMessage(path + " was selected.");
			}
		}
	}
}