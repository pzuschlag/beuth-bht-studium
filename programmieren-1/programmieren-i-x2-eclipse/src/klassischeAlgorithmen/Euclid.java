package klassischeAlgorithmen;
import cs101.io.Console;

public class Euclid {
	static int a;
	static int b;

	public static void main(String[] args) {
		Console.println("You want to work out the greates common divider?");
		Console.println("I know a great technique. It dates back more than 2200 years.");
		Console.println("And it still works! Let me show it to you: ");
		Console.println("You give the numbers, I do the work. Ok?");
		Console.println("");
		Console.println("Enter your first number:");
		a = readNumber();
		Console.println("I call it A. A is " + a);
		if (a < 0) {
			a = -a;
			Console.println("I don't care for signs. A is" + a);
		}
		Console.println("Now enter your second number:");
		b = readNumber();
		Console.println("I call it B. B is " + b);
		if (a < 0) {
			a = -a;
			Console.println("I don't care for signs. B is" + b);
		}
		while (a != b) {
			if (a > b) {
				Console.println("A is larger than B, so reduce A by B.");
				a = a - b;
				Console.println("Now A is " + a);

			} else {
				Console.println("B is larger than A, so reduce B by A.");
				b = b - a;
				Console.println("Now B is " + b);
			}
		}
		Console.println("");
		Console.println("Now both numbers are equal. That's the GCD, says Euklid.");
		Console.println("So the GCD is " + a);
	}

	static int readNumber() {
		return Integer.parseInt(Console.readln());
	}
}
