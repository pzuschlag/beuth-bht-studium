package de.bht.fpa.mail.s826445.filter;

import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Abstracter Filter für alle String-Operationen.
 */
public abstract class StringFilterAbstract implements IFilter {

	protected FilterOperator operator;
	protected String filterString;

	/**
	 * The Construktor.
	 */
	public StringFilterAbstract(String filterString, FilterOperator op) {
		this.filterString = filterString;
		this.operator = op;
	}

	@Override
	public abstract Set<Message> filter(Iterable<Message> messagesToFilter);

	/**
	 * Prüft einen übergebnen String auf alle übergebenedne Vergleichsoperatoren.
	 */
	protected boolean filterCheck(String s) {
		return StringCompareHelper.matches(s, filterString, operator);
	}
}