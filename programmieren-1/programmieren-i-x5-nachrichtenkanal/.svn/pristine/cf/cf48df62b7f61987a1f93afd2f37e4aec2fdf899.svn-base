import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class TextContainerQueue implements TextContainer {

	// Queue, FIFO-Prinzip (First in, First out)
	// Wir arbeiten hier mit den Methoden welche Exceptions werfen
	// Queue bitet jedoch auch die Möglichkeit null anstatt Exceptions zurück zu bekommen

	private Queue<String> queue = new LinkedList<String>();

	@Override
	public void enter(String s) throws FullException {
		queue.add(s);
	}

	@Override
	public String remove() throws EmptyException {
		try {
			return queue.remove();
		} catch (NoSuchElementException e) {
			throw new EmptyException();
		}
	}

	@Override
	public boolean empty() {
		return queue.isEmpty();
	}
}
