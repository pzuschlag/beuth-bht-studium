package de.bht.fpa.mail.s826445.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Filter für die Prio-Spalte.
 *
 */
public class ImportanceFilter implements IFilter {

	private Importance prio;

	/**
	 * The Constructor.
	 * 
	 * @param prio
	 */
	public ImportanceFilter(Importance prio) {
		this.prio = prio;
	}

	@Override
	public Set<Message> filter(Iterable<Message> messagesToFilter) {
		Set<Message> result = new HashSet<Message>();
		for (Message msg : messagesToFilter) {
			if (msg.getImportance().equals(prio)) {
				result.add(msg);
			}
		}
		return result;
	}
}
