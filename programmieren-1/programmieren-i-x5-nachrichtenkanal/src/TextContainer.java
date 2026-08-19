public interface TextContainer {

	public void enter(String s) throws FullException;

	public String remove() throws EmptyException;

	public boolean empty();
}
