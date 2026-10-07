package de.bht.fpa.mail.s826445.filter.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s826445.filter.Intersection;
import de.bht.fpa.mail.s826445.filter.Union;

/*
 * Kann geschriebene Suchanfragen in einer bestimmten Syntax in Filter umwandeln. 
 * Sammelt zu einer Gruppe gehörende Filter und gibt die gefilterten Nachrichten zurück
 */
public class Parser {

  private Collection<Message> testMessages;
  private String searchPattern;

  /*
   * Konstruktor
   */
  public Parser(Collection<Message> testMessages) {
    this.testMessages = testMessages;
    this.searchPattern = createPattern();
  }

  /*
   * Erzeugt die Pattern der regulären Ausdrücke
   */
  private String createPattern() {
    // first search group
    String senRepSubTexRE = "(sender|recipient|subject|text)+";
    String operatorRE = "(is|contains|contains\\snot|starts\\swith|ends\\swith)+";
    String searchStrRE = "(\"[A-Z0-9._%+-ÄÖÜß#\\*@\\s]*\")+";
    String textSubject = senRepSubTexRE + "\\s+" + operatorRE + "\\s+" + searchStrRE;
    // importance search group
    String importance = "(importance)+\\s(high|low|normal)+";
    // read search group
    String read = "(read|unread)+";
    // connection search group
    String unionIntersection = "(union|intersection)+";

    String searchPattern = "(" + textSubject + ")|(" + importance + ")|(" + read + ")|(" + unionIntersection + ")";

    return searchPattern;
  }

  /*
   * Wendet den regulären Ausdruck auf den übergebenen String an. Kombiniert die
   * gefundenen Suchausdrücke und generiert aus diesen über die Filter die
   * Nachrichten
   */
  public Collection<Message> getMessages(String string) {

    // compile search pattern
    Pattern fullPattern = Pattern.compile(this.searchPattern, Pattern.CASE_INSENSITIVE);
    // Create matcher object.
    Matcher mSR = fullPattern.matcher(string);

    List<IFilter> filterList = new ArrayList<IFilter>();

    String lastConnection = null;
    String previousConnection = null;

    Collection<Message> returnMessages = testMessages;

    while (mSR.find()) {

      if (mSR.group(1) != null) {
        IFilter f = Generator.firstGroup(mSR.group(2), mSR.group(3), mSR.group(4).replaceAll("\"", ""));
        if (f != null) {
          filterList.add(f);
        }
      }
      if (mSR.group(5) != null) {
        IFilter f = Generator.importanceFilter(mSR.group(7));
        if (f != null) {
          filterList.add(f);
        }
      }
      if (mSR.group(8) != null) {
        IFilter f = Generator.readFilter(mSR.group(9));
        if (f != null) {
          filterList.add(f);
        }
      }
      if (mSR.group(10) != null) {
        previousConnection = lastConnection;
        lastConnection = mSR.group(11);
      }
      if (previousConnection != null && !lastConnection.equalsIgnoreCase(previousConnection)) {
        Collection<Message> messages = Generator.connection(previousConnection, filterList, returnMessages);
        if (messages != null) {
          returnMessages = new HashSet<Message>(messages);
        }
        filterList.clear();
        lastConnection = null;
        previousConnection = null;
      }
    }
    if (lastConnection != null) {
      Collection<Message> messages = Generator.connection(lastConnection, filterList, returnMessages);
      if (messages != null) {
        returnMessages = new HashSet<Message>(messages);
      }
    }
    for (int x = 0; x < filterList.size(); x++) {
      if (lastConnection == null && !Intersection.class.isInstance(filterList.get(x))
          && !Union.class.isInstance(filterList.get(x))) {
        returnMessages = filterList.get(x).filter(returnMessages);
      }
    }
    return returnMessages;

  }
}
