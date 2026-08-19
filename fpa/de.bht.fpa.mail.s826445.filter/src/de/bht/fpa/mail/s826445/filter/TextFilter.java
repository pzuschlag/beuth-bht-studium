package de.bht.fpa.mail.s826445.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Filter für den Text der Mail.
 */
public class TextFilter extends StringFilterAbstract {

	/**
	 * The Constructor
	 */
	public TextFilter(String filterString, FilterOperator op) {
		super(filterString, op);
	}

	@Override
	public Set<Message> filter(Iterable<Message> messagesToFilter) {
		Set<Message> result = new HashSet<Message>();
		for (Message m : messagesToFilter) {
			if (super.filterCheck(m.getText())) {
				result.add(m);
			}
		}
		return result;
	}
}