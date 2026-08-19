package de.bht.fpa.mail.s826445.filter.parser;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s826445.filter.ImportanceFilter;
import de.bht.fpa.mail.s826445.filter.Intersection;
import de.bht.fpa.mail.s826445.filter.ReadFilter;
import de.bht.fpa.mail.s826445.filter.RecipientFilter;
import de.bht.fpa.mail.s826445.filter.SenderFilter;
import de.bht.fpa.mail.s826445.filter.SubjectFilter;
import de.bht.fpa.mail.s826445.filter.TextFilter;
import de.bht.fpa.mail.s826445.filter.Union;

/**
 * Generator für den Parser.
 */
public class Generator {

	/**
	 * Ermittelt den richtigen Filter für die gesuchte Operation.
	 */
	public static IFilter firstGroup(String type, String operator, String text) {

		switch (type.toLowerCase()) {
		case "sender":
			return new SenderFilter(text, getFilterOperator(operator));
		case "recipient":
			return new RecipientFilter(text, getFilterOperator(operator));
		case "text":
			return new TextFilter(text, getFilterOperator(operator));
		case "subject":
			return new SubjectFilter(text, getFilterOperator(operator));
		default:
			return null;
		}
	}

	/**
	 * Liefert den richtien Importance-Filter da hier noch zwischen niedrig, mittel und Hoch unterschieden werden muss
	 */
	public static IFilter importanceFilter(String type) {

		switch (type.toLowerCase()) {
		case "low":
			return new ImportanceFilter(Importance.LOW);
		case "normal":
			return new ImportanceFilter(Importance.NORMAL);
		case "high":
			return new ImportanceFilter(Importance.HIGH);
		default:
			return null;
		}
	}

	/**
	 * Liefert den richtien read/unread-Filter da hier noch zusätlich zwischen gelesen und ungelesen untschieden werden
	 * muss.
	 */
	public static IFilter readFilter(String type) {

		switch (type.toLowerCase()) {
		case "read":
			return new ReadFilter(true);
		case "unread":
			return new ReadFilter(false);
		default:
			return null;
		}
	}

	/**
	 * Verknüpfung zwischen und Union und Interception
	 */
	public static Set<Message> connection(String type, List<IFilter> filterList, Collection<Message> messages) {
		switch (type.toLowerCase()) {
		case "union":
			return new Union(filterList).filter(messages);
		case "intersection":
			return new Intersection(filterList).filter(messages);
		default:
			return null;
		}
	}

	/**
	 * Filteroperationen
	 */
	public static FilterOperator getFilterOperator(String operator) {

		switch (operator.toLowerCase()) {
		case "is":
			return FilterOperator.IS;
		case "contains":
			return FilterOperator.CONTAINS;
		case "contains not":
			return FilterOperator.CONTAINS_NOT;
		case "starts with":
			return FilterOperator.STARTS_WITH;
		case "ends with":
			return FilterOperator.ENDS_WITH;
		default:
			return null;
		}
	}
}