package de.bht.fpa.mail.s826445.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;

/**
 * Testmethode welche alle Filter testet
 *
 */
public class MainTest {

	public static void main(String[] args) {
		// TestMessages
		Collection<Message> testMessages = new RandomTestDataProvider(50).getMessages();

		// Union-Filter
		List<IFilter> filterUnion = new ArrayList<IFilter>();
		filterUnion.add(new SenderFilter("stulle", FilterOperator.CONTAINS));
		filterUnion.add(new RecipientFilter("stulle", FilterOperator.ENDS_WITH));
		Set<Message> resultUnion = new Union(filterUnion).filter(testMessages);

		// Intersection-Filter
		List<IFilter> filterIntersection = new ArrayList<IFilter>();
		filterIntersection.add(new SenderFilter("arnold", FilterOperator.CONTAINS));
		filterIntersection.add(new ReadFilter(true));
		Set<Message> resultIntersection = new Intersection(filterIntersection).filter(testMessages);

		System.out.println("Union-Filter: (" + resultUnion.size() + " Treffer)");
		for (Message msg : resultUnion) {
			System.out.println(msg.getSender().toString() + msg.getRecipients().toString());
		}
		System.out.println("Intersection-Filter: (" + resultIntersection.size() + " Treffer)");
		for (Message msg : resultIntersection) {
			System.out.println(msg.getSender().toString() + " read: " + msg.isRead().toString());
		}
	}
}