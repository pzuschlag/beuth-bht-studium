package de.bht.fpa.mail.s000000.common.mail.model;

import java.util.List;

import org.eclipse.swt.graphics.Image;

public interface IMessageTreeItem {

	public String getText();

	public Image getImage();

	public boolean hasChildren();

	public List<IMessageTreeItem> getChildren();
	
	public List<Message> getMessages();
}
