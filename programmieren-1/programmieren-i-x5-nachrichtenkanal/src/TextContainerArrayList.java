import java.util.ArrayList;

public class TextContainerArrayList implements TextContainer {

	private ArrayList<String> queue = new ArrayList<String>();

	public void enter(String s) {
		if (!queue.add(s)) {
			throw new FullException();
		}
	}

	public String remove() throws EmptyException {
		if (empty()) {
			throw new EmptyException();
		}
		return queue.remove(0);

	}

	public boolean empty() {
		return queue.isEmpty();
	}
}
