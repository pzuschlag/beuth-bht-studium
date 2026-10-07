package de.bht.fpa.mail.s826445.fsnavigation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s000000.common.mail.model.IMessageTreeItem;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Darstellung der Order in der Treeview
 */
public class FolderItem extends TreeItemAbstract {

	/**
	 * Konstruktor.
	 *
	 * @param file
	 */
	public FolderItem(File file) {
		super(file);
	}

	/**
	 * Konstruktor, für übergabe des Pfades als String
	 * 
	 * @param path
	 */
	public FolderItem(String path) {
		super(new File(path));
	}

	/**
	 * Liefert das Bild für die Darstellung in der TreeView
	 */
	@Override
	public Image getImage() {
		return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/folder.png").createImage();
	}

	/**
	 * Liefert true falls Kind-Elemente exsistieren.
	 */
	@Override
	public boolean hasChildren() {
		return file.list() != null;
	}

	/**
	 * Liefert eine Liste mit Kind-Elementen
	 */
	@Override
	public List<IMessageTreeItem> getChildren() {
		ArrayList<IMessageTreeItem> children = new ArrayList<IMessageTreeItem>();
		if (file == null || file.listFiles() == null || file.listFiles().length == 0)
			return children;

		for (File item : file.listFiles()) {
			if (item.isDirectory() && !item.getName().startsWith(".")) {
				children.add(new FolderItem(item));
			}
		}
		return children;
	}

	/**
	 * Liefert die hinterlegten Messages.
	 */
	public List<Message> getMessages() {
		List<Message> msglist = new ArrayList<Message>();

		for (File item : file.listFiles()) {
			try {
				Message msg = JAXB.unmarshal(item, Message.class);
				if (msg.getId() != null) {
					msglist.add(msg);
				}
			} catch (Exception e) {
			}
		}
		return msglist;
	}

	/**
	 * Liefert den absoluten Pfad des Ordners.
	 * 
	 */
	public String getPath() {
		return file.getPath();
	}
}