package de.bht.fpa.mail.s826445.fsnavigation;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.bht.fpa.mail.s000000.common.mail.model.IMessageTreeItem;

public class FsContentProvider implements ITreeContentProvider {

	/**
	 * Nicht Implementiert.
	 */
	@Override
	public void dispose() {
	}

	/**
	 * Nicht Implementiert.
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	/**
	 * Liefert die Kind-Elemente zum Root-Verzeichnis.
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof String) {
			String s = (String) inputElement;
			return new String[] { s + "-Child1", s + "-Child2" };
		}
		return null;
	}

	/**
	 * Liefert alle Kind-Element zu einem Element.
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof String) {
			String s = (String) parentElement;
			return new String[] { s + "-Child1", s + "-Child2", s + "-Child3" };
		}
		return null;
	}

	/**
	 * Nicht Implementiert.
	 */
	@Override
	public Object getParent(Object element) {
		return null;
	}

	/**
	 * Prüft ob das übergebene Element über Kind-Elemente verfügt.
	 */
	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IMessageTreeItem) {
			IMessageTreeItem s = (IMessageTreeItem) element;
			return s.hasChildren();
		}
		return false;
	}
}