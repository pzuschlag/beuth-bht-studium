package de.ralfebert.rcputils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.ralfebert.rcputils"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	  /**
	   * Returns the shared instance.
	   * 
	   * @return the shared instance
	   */
	  public static Activator getInstance() {
	    return plugin;
	  }
	  
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	  /**
	   * Logs an {@link Exception} to the eclipse workbench.
	   * 
	   * @param e
	   *          the {@link Exception} to log
	   */
	  public static void logException(Exception e) {
	    if (getInstance() == null) {
	      final Writer result = new StringWriter();
	      e.printStackTrace(new PrintWriter(result));
	      System.err.println(result.toString());
	    } else {
	      getInstance().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, e.getMessage(), e));
	    }
	  }
}
