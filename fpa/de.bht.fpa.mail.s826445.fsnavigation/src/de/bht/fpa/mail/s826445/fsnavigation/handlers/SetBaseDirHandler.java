package de.bht.fpa.mail.s826445.fsnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

public class SetBaseDirHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		DirectoryDialog dlg = new DirectoryDialog(window.getShell());
		String choosenPath = dlg.open();
		System.out.println(choosenPath);
		SingletonFile file = SingletonFile.getInstance();
		file.setpath(choosenPath);

		Preferences prefs = getPrefs();
		try {
			prefs.put("" + prefs.keys().length, choosenPath);
			prefs.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Preferences getPrefs() {
		IPreferencesService service = Platform.getPreferencesService();
		IEclipsePreferences root = service.getRootNode();
		Preferences prefs = root.node(ConfigurationScope.SCOPE).node("de.bht.fpa.mail.s826445.fsnavigation.view1");
		return prefs;
	}
}