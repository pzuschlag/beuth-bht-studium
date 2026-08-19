package de.bht.fpa.mail.s826445.fsnavigation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s000000.common.mail.model.IMessageTreeItem;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Abstrakte Oberklasse für die TreeItem Darstellung
 */
public abstract class TreeItemAbstract implements IMessageTreeItem {

	protected final File file;

	public TreeItemAbstract(File file) {
		this.file = file;
	}

	@Override
	public String getText() {
		return file.getName();
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public List<IMessageTreeItem> getChildren() {
		return null;
	}

	@Override
	public List<Message> getMessages() {
		return new ArrayList<Message>();
	}
}