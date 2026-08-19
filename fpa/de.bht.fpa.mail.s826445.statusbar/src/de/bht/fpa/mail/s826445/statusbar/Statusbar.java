package de.bht.fpa.mail.s826445.statusbar;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchWindow;

@SuppressWarnings("restriction")
public class Statusbar implements IStartup {

	/**
	 * Initialisiert die Statusbar beim Programmstart.
	 */
	@Override
	public void earlyStartup() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					WorkbenchWindow workbenchWindow = (WorkbenchWindow) window;
					IWorkbenchPage page = window.getActivePage();
					page.addSelectionListener(new StatusBarSelection(workbenchWindow.getStatusLineManager()));
				}
			}
		});
	}
}