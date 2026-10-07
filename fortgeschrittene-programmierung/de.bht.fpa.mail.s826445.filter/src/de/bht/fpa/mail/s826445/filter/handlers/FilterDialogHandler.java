package de.bht.fpa.mail.s826445.filter.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s000000.common.filter.FilterCombination;
import de.bht.fpa.mail.s000000.common.filter.FilterDialog;
import de.bht.fpa.mail.s000000.common.filter.FilterGroupType;
import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.FilterType;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s826445.filter.ImportanceFilter;
import de.bht.fpa.mail.s826445.filter.Intersection;
import de.bht.fpa.mail.s826445.filter.ReadFilter;
import de.bht.fpa.mail.s826445.filter.RecipientFilter;
import de.bht.fpa.mail.s826445.filter.SenderFilter;
import de.bht.fpa.mail.s826445.filter.SubjectFilter;
import de.bht.fpa.mail.s826445.filter.TextFilter;
import de.bht.fpa.mail.s826445.filter.Union;

/**
 * Verarbeitung der Filteroperationen
 */
public class FilterDialogHandler extends AbstractHandler {

	/**
	 * Konstruktor
	 */
	public FilterDialogHandler() {
	}

	/**
	 * Execute-Methode, erzeugt den benötigten Filter
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		Shell shell = window.getShell();

		FilterDialog filterDialog = new FilterDialog(shell);
		filterDialog.open();
		List<FilterCombination> filterCombinations = filterDialog.getFilterCombinations();

		if (filterCombinations != null) {
			FilterGroupType filterGroupType = filterDialog.getFilterGroupType();
			List<IFilter> filterListe = new ArrayList<IFilter>();

			for (FilterCombination filterCombination : filterCombinations) {
				FilterType filterType = filterCombination.getFilterType();
				FilterOperator filterOperator = filterCombination.getFilterOperator();
				Object filterValue = filterCombination.getFilterValue();

				switch (filterType.toString().toUpperCase()) {
				case "SENDER":
					filterListe.add(new SenderFilter(filterValue.toString(), filterOperator));
					break;
				case "RECIPIENT":
					filterListe.add(new RecipientFilter(filterValue.toString(), filterOperator));
					break;
				case "SUBJECT":
					filterListe.add(new SubjectFilter(filterValue.toString(), filterOperator));
					break;
				case "TEXT":
					filterListe.add(new TextFilter(filterValue.toString(), filterOperator));
					break;
				case "IMPORTANCE":
					filterListe.add(new ImportanceFilter(getImportance(filterValue.toString())));
					break;
				case "READ":
					filterListe.add(new ReadFilter((boolean) filterValue));
					break;
				default:
					break;
				}
			}

			if (filterGroupType.toString().equals("UNION")) {
				return new Union(filterListe);
			}
			if (filterGroupType.toString().equals("INTERSECTION")) {
				return new Intersection(filterListe);
			}
		}
		return null;
	}

	private Importance getImportance(String s) {
		switch (s.toLowerCase()) {
		case "high":
			return Importance.HIGH;
		case "normal":
			return Importance.NORMAL;
		case "low":
			return Importance.LOW;
		default:
			return null;
		}
	}
}