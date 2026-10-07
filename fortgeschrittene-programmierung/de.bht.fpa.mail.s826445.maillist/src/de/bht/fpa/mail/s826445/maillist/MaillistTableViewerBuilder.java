package de.bht.fpa.mail.s826445.maillist;

import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.mail.model.Sender;
import de.bht.fpa.mail.s000000.common.table.MessageValues;
import de.ralfebert.rcputils.properties.IValue;
import de.ralfebert.rcputils.properties.IValueFormatter;
import de.ralfebert.rcputils.properties.PropertyValue;
import de.ralfebert.rcputils.tables.ColumnBuilder;
import de.ralfebert.rcputils.tables.TableViewerBuilder;

public class MaillistTableViewerBuilder extends TableViewerBuilder {

	/**
	 * The Constructor.
	 * 
	 * @param parent
	 */
	public MaillistTableViewerBuilder(Composite parent) {
		super(parent);
		createColumnImportance(this);
		createColumnRead(this);
		createColumnReceived(this);
		createColumnSender(this);
		createColumnRecipient(this);
		createColumnSubject(this);
		// createColumnAttachment(this);
	}

	/**
	 * Erzeugt die Importance-Spalte.
	 * 
	 * @param tableCreator
	 */
	private void createColumnImportance(TableViewerBuilder tableCreator) {
		ColumnBuilder importance = tableCreator.createColumn("Imp");
		importance.bindToValue(MessageValues.IMPORTANCE);
		importance.setPixelWidth(24);
		importance.setCustomLabelProvider(new CellLabelProvider() {

			@Override
			public void update(ViewerCell cell) {
				Object o = cell.getElement();
				if (o instanceof Message) {
					Message msg = (Message) o;
					String img = "icons/prio_mid.png";
					if (msg.getImportance() == Importance.HIGH) {
						img = "icons/prio_hig.png";
					} else if (msg.getImportance() == Importance.LOW) {
						img = "icons/prio_low.png";
					}
					Image image = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, img).createImage();
					cell.setImage(image);
				}
			}

		});
		importance.build();
	}

	/**
	 * Erzeugt die Read-Spalte.
	 * 
	 * @param tableCreator
	 */
	private void createColumnRead(TableViewerBuilder tableCreator) {
		ColumnBuilder read = tableCreator.createColumn("Read");
		read.bindToValue(MessageValues.READ);
		read.setPixelWidth(24);
		read.setCustomLabelProvider(new CellLabelProvider() {

			@Override
			public void update(ViewerCell cell) {
				Object o = cell.getElement();
				if (o instanceof Message) {
					Message msg = (Message) o;
					String img = "icons/unread.png";
					if (msg.isRead()) {
						img = "icons/read.png";
					} else if (!msg.isRead()) {
						img = "icons/unread.png";
					}
					Image image = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, img).createImage();
					cell.setImage(image);
				}
			}
		});
		read.build();
	}

	/**
	 * Erzeugt die Received-Spalte.
	 * 
	 * @param tableCreator
	 */
	private void createColumnReceived(TableViewerBuilder tableCreator) {
		ColumnBuilder received = tableCreator.createColumn("Received");
		received.setPixelWidth(80);
		received.bindToValue(MessageValues.RECEIVED);
		received.setCustomLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Object o = cell.getElement();
				if (o instanceof Message) {
					Message msg = (Message) o;
					cell.setText(SimpleDateFormat.getDateInstance().format(msg.getReceived()));
				}
			}
		});
		received.useAsDefaultSortColumn();
		received.build();
	}

	/**
	 * Erzeugt die Sender-Spalte.
	 * 
	 * @param tableCreator
	 */
	private void createColumnSender(TableViewerBuilder tableCreator) {
		ColumnBuilder sender = tableCreator.createColumn("Sender");
		sender.setPercentWidth(30);
		sender.bindToValue(MessageValues.SENDER);
		sender.format(new IValueFormatter<Object, String>() {
			@Override
			public String format(Object obj) {
				Sender s = (Sender) obj;
				return s.getPersonal() + " <" + s.getEmail() + ">";
			}

			@Override
			public Object parse(String string) {
				return null;
			}
		});
		sender.sortBy(new PropertyValue("sender.personal"));
		sender.build();
	}

	/**
	 * Erzeugt die Recipient-Spalte
	 * 
	 * @param tableCreator
	 */
	private void createColumnRecipient(TableViewerBuilder tableCreator) {
		ColumnBuilder recipient = tableCreator.createColumn("Recipients");
		recipient.bindToValue(MessageValues.RECIPIENT);
		recipient.format(new IValueFormatter<Object, String>() {
			@SuppressWarnings("unchecked")
			@Override
			public String format(Object obj) {
				String recipient = "";
				if (obj instanceof List) {
					List<Recipient> recipientents = (List<Recipient>) obj;
					for (int i = 0; i < recipientents.size(); i++) {
						if (i > 0 && i < recipientents.size()) {
							recipient += ", ";
						}
						Recipient r = (Recipient) recipientents.get(i);
						recipient += r.getPersonal() + " <" + r.getEmail() + ">";
					}
				}
				return recipient;
			}

			@Override
			public Object parse(String string) {
				return null;
			}
		});

		recipient.sortBy(new IValue() {
			@Override
			public Object getValue(Object element) {
				String firstPerson = null;
				if (element instanceof Message) {
					Message msg = (Message) element;
					firstPerson = msg.getRecipients().get(0).getPersonal();
				}
				return firstPerson;
			}

			@Override
			public void setValue(Object element, Object value) {
			}
		});
		recipient.build();
	}

	/**
	 * Erzeugt die Subject-Spalte.
	 * 
	 * @param tableCreator
	 */
	private void createColumnSubject(TableViewerBuilder tableCreator) {
		ColumnBuilder subject = tableCreator.createColumn("Subject");
		subject.setPercentWidth(40);
		subject.bindToValue(MessageValues.SUBJECT);
		subject.build();
	}

	/**
	 * Erstellt eine Spalte welche anzeigt ob die Mail einen Anhang besitzt.
	 * 
	 * Derzeit nicht implementiert. Kommt noch ! :)
	 * 
	 * @param tableCreator
	 */
	// private void createColumnAttachment(TableViewerBuilder tableCreator) {
	// ColumnBuilder attachment = tableCreator.createColumn("Attachment");
	// attachment.bindToValue(MessageValues.ATTACHMENT);
	// attachment.setPixelWidth(24);
	// attachment.setCustomLabelProvider(new CellLabelProvider() {
	//
	// @Override
	// public void update(ViewerCell cell) {
	// Object o = cell.getElement();
	// if (o instanceof Message) {
	// Message msg = (Message) o;
	// String img = "icons/attachment.png";
	// if (!msg.getAttachment().isEmpty()) {
	// img = "icons/attachment.png";
	// } else if (!msg.isRead()) {
	// img = "icons/noAttachment.png";
	// }
	// Image image = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, img).createImage();
	// cell.setImage(image);
	// }
	// }
	// });
	// attachment.build();
	// }
}