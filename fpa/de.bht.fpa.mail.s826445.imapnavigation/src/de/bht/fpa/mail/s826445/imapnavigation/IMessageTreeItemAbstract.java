package de.bht.fpa.mail.s826445.imapnavigation;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s000000.common.mail.model.IMessageTreeItem;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public abstract class IMessageTreeItemAbstract implements IMessageTreeItem {

  @Override
  public String getText() {
    return null;
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
    return null;
  }

  public abstract String getPath();

}
