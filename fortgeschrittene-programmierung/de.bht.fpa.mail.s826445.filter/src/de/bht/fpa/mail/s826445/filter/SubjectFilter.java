package de.bht.fpa.mail.s826445.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Filter für die Betreff-Spalte
 */
public class SubjectFilter extends StringFilterAbstract {

	/**
	 * The Constructor
	 * 
	 * @param filterString
	 * @param op
	 */
	public SubjectFilter(String filterString, FilterOperator op) {
		super(filterString, op);
	}

	/**
	 * Prüfung des Betreffes der Mail
	 */
	public Set<Message> filter(Iterable<Message> messagesToFilter) {
		Set<Message> result = new HashSet<Message>();
		for (Message msg : messagesToFilter) {
			if (super.filterCheck(msg.getSubject())) {
				result.add(msg);
			}
		}
		return result;
	}

}
