public class FullException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FullException() {
		System.err.println("Queue ist voll");
	}

}
