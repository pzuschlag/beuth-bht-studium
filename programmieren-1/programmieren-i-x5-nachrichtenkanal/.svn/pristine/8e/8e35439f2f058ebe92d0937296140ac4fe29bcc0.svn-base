public class TextContainerArray implements TextContainer {

	private String[] list;
	private int size, entries, first, last = 0;

	// size -> Gibt die Größe des Arrays an
	// entries -> Ist der Zeiger wieviele Werte sich grad im Array befinden
	// first -> Zeiger für das Eintragen neuer Werte
	// last -> Zeiger für das Auslesen der untersten Wertes

	public TextContainerArray() {
		this(25);
	}

	public TextContainerArray(int size) {
		this.size = size;
		list = new String[size];
	}

	public void enter(String s) {
		if (entries == size)
			throw new FullException();
		list[first] = s;
		first = (first + 1) % size;
		entries++;
	}

	public String remove() throws EmptyException {
		if (entries == 0)
			throw new EmptyException();
		entries--;
		last = (last + 1) % size;
		return list[last - 1];
	}

	public boolean empty() {
		return entries == 0;
	}
}