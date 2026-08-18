package aufgabe1;

public class SupportSystem {

	/**
	 * Main zur kommunizierung mit dem Responder.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		InputReader in = new InputReader();
		Responder res = new Responder();

		String quest = "";
		boolean answer = false;

		while (true) {
			System.out.print("> ");

			quest = in.read();

			if (!quest.contains("bye")) {
				for (String key : res.responseMap.keySet()) {
					// for (String key : res.responseMap.allKeys()) {
					if (quest.toLowerCase().contains(key)) {
						System.out.println(res.generateResponse(key));
						answer = true;
					}
				}
				if (!answer) {
					System.out.println(res.pickDefaultResponse());
				}
				answer = false;
			} else {
				break;
			}
		}
	}
}
