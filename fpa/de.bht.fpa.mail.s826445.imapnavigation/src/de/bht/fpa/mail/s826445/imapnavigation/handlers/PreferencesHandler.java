package de.bht.fpa.mail.s826445.imapnavigation.handlers;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import de.bht.fpa.mail.s826445.imapnavigation.Activator;

public class PreferencesHandler {

	/*
	 * Holt den zu einem key gehörigen String aus der Preference Datei zurück Gibt den String oder null zurück
	 * 
	 */
	public static String getPref(String key) {
		Preferences pref = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		return pref.get(key, null);
	}

	/*
	 * Speichert und schreibt den übergebenen String in die Preference Datei
	 * 
	 */
	public static void savePref(String key, String choosenPath) {
		Preferences pref = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		pref.put(key, choosenPath);
		try {
			// forces the application to save the preferences
			pref.flush();
		} catch (BackingStoreException e) {
			// TODO besseres errorhandling
			e.printStackTrace();
		}

	}

}
