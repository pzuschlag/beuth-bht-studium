package de.bht.fpa.mail.s826445.main;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.addView("de.bht.fpa.mail.s826445.fsnavigation.view1", IPageLayout.LEFT, 0.25f, layout.getEditorArea());
		layout.setEditorAreaVisible(false);
	}
}
