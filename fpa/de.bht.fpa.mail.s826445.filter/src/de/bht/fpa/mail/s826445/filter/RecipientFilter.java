package de.bht.fpa.mail.s826445.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;

/**
 * Filter für die Receipient-Spalte
 */
public class RecipientFilter extends StringFilterAbstract {

	/**
	 * The Contstructor
	 * 
	 * @param filterString
	 * @param op
	 */
	public RecipientFilter(String filterString, FilterOperator op) {
		super(filterString, op);
	}

	@Override
	public Set<Message> filter(Iterable<Message> messagesToFilter) {
		Set<Message> result = new HashSet<Message>();
		for (Message m : messagesToFilter) {
			for (Recipient r : m.getRecipients()) {
				if (super.filterCheck(r.getEmail()) || super.filterCheck(r.getPersonal())) {
					result.add(m);
				}
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return "RecipientFilter [operator=" + operator + ", filterString=" + filterString + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
