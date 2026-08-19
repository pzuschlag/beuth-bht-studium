package de.bht.fpa.mail.s826445.maillist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Text;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;

public class MaillistTableViewerFilter extends ViewerFilter {

	private Text searchText;

	/**
	 * The Constructor.
	 * 
	 * @param searchText
	 */
	public MaillistTableViewerFilter(Text searchText) {
		this.searchText = searchText;
	}

	@Override
	/**
	 * Durchsucht die Mails nach dem zu filternden Text. Return true wenn Mail entsprechenden Text beinhaltet.
	 */
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		String searchKey = searchText.getText();

		if (searchKey == null || searchKey.length() == 0) {
			return true;
		}

		if (element instanceof Message) {

			Message msg = (Message) element;

			boolean recEmailContains = false;
			boolean recPersonalContains = false;

			for (Recipient r : msg.getRecipients()) {
				recEmailContains = r.getEmail().contains(searchKey);
				recPersonalContains = r.getPersonal().contains(searchKey);
			}

			DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM);
			String formattedDateReceived = dateFormat.format(msg.getReceived()).toString();
			String formattedDateSent = dateFormat.format(msg.getSent()).toString();

			return msg.getSubject().contains(searchKey) || msg.getText().contains(searchKey) || formattedDateReceived.contains(searchKey)
					|| formattedDateSent.contains(searchKey) || msg.getSender().getEmail().contains(searchKey)
					|| msg.getSender().getPersonal().contains(searchKey) || recEmailContains || recPersonalContains;
		}
		return false;
	}
}