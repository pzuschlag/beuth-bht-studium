package de.bht.fpa.mail.s826445.filter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

/**
 * Union-Filter
 */
public class Union implements IFilter {

	private List<IFilter> filterLi;

	/**
	 * The Constructor.
	 * 
	 * @param filter
	 */
	public Union(List<IFilter> filter) {
		this.filterLi = filter;
	}

	@Override
	public Set<Message> filter(Iterable<Message> messagesToFilter) {
		Set<Message> result = new HashSet<Message>();
		for (IFilter f : filterLi) {
			result.addAll(f.filter(messagesToFilter));
		}
		return result;
	}
}