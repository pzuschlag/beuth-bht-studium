package de.bht.fpa.mail.s826445.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Filter für die Read-Spalte.
 *
 */
public class ReadFilter implements IFilter {

	private boolean read;

	/**
	 * The Konstructor
	 * 
	 * @param read
	 */
	public ReadFilter(boolean read) {
		this.read = read;
	}

	@Override
	public Set<Message> filter(Iterable<Message> messagesToFilter) {
		Set<Message> result = new HashSet<Message>();
		for (Message m : messagesToFilter) {
			if (m.isRead().equals(read)) {
				result.add(m);
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return "ReadFilter [read=" + read + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
}
