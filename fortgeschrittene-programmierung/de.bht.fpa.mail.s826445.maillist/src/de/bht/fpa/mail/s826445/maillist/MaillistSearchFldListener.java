package de.bht.fpa.mail.s826445.maillist;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Text;

public class MaillistSearchFldListener implements KeyListener {

	TableViewer tableViewer;
	Text searchText;

	/**
	 * Listener für das SearchField.
	 * 
	 * @param tableViewer
	 * @param searchText
	 */
	public MaillistSearchFldListener(TableViewer tableViewer, Text searchText) {
		this.tableViewer = tableViewer;
		this.searchText = searchText;
	}

	/**
	 * Nicht implentiert.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * Passt die Maillist nach jeder Eingabe im Suchfeld an.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		MaillistTableViewerFilter tf = new MaillistTableViewerFilter(searchText);
		tableViewer.addFilter(tf);
	}
}