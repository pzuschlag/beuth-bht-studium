package de.bht.fpa.mail.s826445.fsnavigation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s000000.common.mail.model.IMessageTreeItem;

/**
 * Darstellung der Dateien in der TreeView
 */
public class FileItem extends TreeItemAbstract {

	/**
	 * Konstruktor.
	 * 
	 * @param file
	 */
	public FileItem(File file) {
		super(file);
	}

	/**
	 * Liefert das Image für File
	 */
	@Override
	public Image getImage() {
		return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/file.png").createImage();
	}

	/**
	 * Liefert alle Kind-Elemente
	 */
	@Override
	public List<IMessageTreeItem> getChildren() {
		return new ArrayList<>();
	}
}