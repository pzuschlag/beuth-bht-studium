package de.bht.fpa.mail.s826445.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Filter dür die Sender-Spalte
 * 
 */
public class SenderFilter extends StringFilterAbstract {

	/**
	 * The Constructor
	 * 
	 * @param filterString
	 * @param op
	 */
	public SenderFilter(String filterString, FilterOperator op) {
		super(filterString, op);
	}

	@Override
	public Set<Message> filter(Iterable<Message> messagesToFilter) {
		Set<Message> result = new HashSet<Message>();
		for (Message m : messagesToFilter) {
			if (super.filterCheck(m.getSender().getEmail()) || super.filterCheck(m.getSender().getPersonal())) {
				result.add(m);
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return "SenderFilter [operator=" + operator + ", filterString=" + filterString + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}