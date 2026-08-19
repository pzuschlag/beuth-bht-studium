import cs101.lang.AnimatorThread;

public class Main {

	public static void main(String[] args) {

		TextContainer chnl;

		chnl = new TextContainerPrototype(3);
		// chnl = new TextContainerArray(5);
		// chnl = new TextContainerArrayList();
		// chnl = new TextContainerQueue();
		// chnl = new TextContainerStack();

		Sender sender = new Sender(chnl);
		AnimatorThread senderEngine = new AnimatorThread(sender);
		senderEngine.startExecution();

		Receiver receiver = new Receiver(chnl);
		AnimatorThread receiverEngine = new AnimatorThread(receiver);
		receiverEngine.startExecution();

		receiverEngine.setSleepRange(5000);

	}
}