public class TextContainerPrototype implements TextContainer {

	private String[] list;
	private int size, top = 0;

	public TextContainerPrototype() {
		this(25);
	}

	public TextContainerPrototype(int size) {
		this.size = size;
		list = new String[size];
	}

	public void enter(String s) {
		if (top == size)
			throw new FullException();
		list[top] = s;
		top++;
	}

	public String remove() throws EmptyException {
		if (top == 0)
			throw new EmptyException();
		top--;
		return list[top];
	}

	public boolean empty() {
		return top == 0;
	}
}