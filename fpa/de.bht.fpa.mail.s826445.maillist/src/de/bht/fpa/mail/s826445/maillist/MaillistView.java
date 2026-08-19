package de.bht.fpa.mail.s826445.maillist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s826445.fsnavigation.FolderItem;
import de.bht.fpa.mail.s826445.fsnavigation.TreeItemAbstract;
import de.ralfebert.rcputils.tables.TableViewerBuilder;

public class MaillistView extends ViewPart implements ISelectionListener, IExecutionListener {

	private TableViewer tableViewer;
	private Text searchText;
	private List<Message> messages;
	TableViewerBuilder tvb;
	private TreeItemAbstract treeItem;
	private FolderItem folderItem;

	@Override
	/**
	 * Erzeugt die gesamte TableView mit samt Listener.
	 */
	public void createPartControl(Composite parent) {

		parent.setLayout(new GridLayout(2, false));

		Label searchLabel = new Label(parent, SWT.LEFT | SWT.TOP);
		searchLabel.setText("Search: ");

		searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER | GridData.HORIZONTAL_ALIGN_FILL));

		Composite tableComposite = new Composite(parent, SWT.BOTTOM);
		GridData tableData = new GridData(SWT.FILL, SWT.FILL, true, true);

		tableData.horizontalSpan = 2; // TODO: Workaround
		tableComposite.setLayoutData(tableData);

		tvb = new MaillistTableViewerBuilder(tableComposite);
		this.tableViewer = tvb.getTableViewer();

		IWorkbench workbench = PlatformUI.getWorkbench();
		ICommandService commandService = (ICommandService) workbench.getService(ICommandService.class);
		commandService.addExecutionListener(this);

		MaillistSearchFldListener searchListener = new MaillistSearchFldListener(tableViewer, searchText);
		searchText.addKeyListener(searchListener);

		getSite().getPage().addSelectionListener(this);
		getSite().setSelectionProvider(this.tableViewer);
	}

	/**
	 * Setzt den Focus auf das SuchFeld.
	 */
	@Override
	public void setFocus() {
		searchText.setFocus();
	}

	/**
	 * Aktualisiert die View wenn die Auswahl wechselt.
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {

		if (selection instanceof IStructuredSelection && selection != null) {
			IStructuredSelection ts = (IStructuredSelection) selection;
			if (ts.getFirstElement() instanceof TreeItemAbstract) {
				treeItem = (TreeItemAbstract) ts.getFirstElement();
				messages = treeItem.getMessages();
				tvb.setInput(messages);
				tableViewer.refresh();
			}
		}
	}

	/**
	 * Nicht implentiert.
	 */
	@Override
	public void notHandled(String commandId, NotHandledException exception) {
	}

	/**
	 * Nicht implementiert.
	 */
	@Override
	public void postExecuteFailure(String commandId, ExecutionException exception) {
	}

	/**
	 * Aktualisiert die View nach einem erfolgreichem Execution-Event.
	 */
	@Override
	public void postExecuteSuccess(String commandId, Object returnValue) {

		if (commandId.equals("de.bht.fpa.mail.s826445.filter.commands.clearFilter")) {
			if (returnValue == null && messages != null) {
				if (treeItem != null) {
					messages = treeItem.getMessages();
					tvb.setInput(messages);
					tableViewer.refresh();
				}
				if (folderItem != null) {
					messages = folderItem.getMessages();
					tvb.setInput(messages);
				}
			}
		}

		if (commandId.equals("de.bht.fpa.mail.s826445.filter.commands.configureFilter")) {
			if (returnValue != null && messages != null) {
				if (returnValue instanceof IFilter && returnValue != null) {
					IFilter iFi = (IFilter) returnValue;
					messages = new ArrayList<Message>(iFi.filter(messages));
					tvb.setInput(messages);
				}
			}
		}

		if (commandId.equals("de.bht.fpa.mail.s826445.fsnavigation.commands.SetBaseDirCommand")
				|| commandId.equals("de.bht.fpa.mail.s826445.fsnavigation.commands.historyCommand")) {
			if (returnValue != null) {
				folderItem = new FolderItem((String) returnValue);
				messages = folderItem.getMessages();
				tvb.setInput(messages);
			}
		}
	}

	/**
	 * Nicht implentiert.
	 */
	@Override
	public void preExecute(String commandId, ExecutionEvent event) {
	}
}