package de.bht.fpa.mail.s826445.filter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Schnittmengen Filter
 *
 */
public class Intersection implements IFilter {

	private List<IFilter> filterListe;

	public Intersection(List<IFilter> filter) {
		this.filterListe = filter;
	}

	@Override
	public Set<Message> filter(Iterable<Message> messagesToFilter) {
		Set<Message> result = new HashSet<Message>();
		result = filterListe.get(0).filter(messagesToFilter);
		for (IFilter filter : filterListe) {
			result.retainAll(filter.filter(messagesToFilter));
		}
		return result;
	}
}