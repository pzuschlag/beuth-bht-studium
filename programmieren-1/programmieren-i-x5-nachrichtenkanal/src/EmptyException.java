public class EmptyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyException() {
		System.err.println("Queue ist Leer");
	}
}
