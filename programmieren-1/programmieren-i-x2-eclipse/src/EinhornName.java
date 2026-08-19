import java.util.Scanner;

public class EinhornName {

	public static void main(String[] args) {

		String prename = "";
		String lastname = "";

		String input;
		Scanner scan = new Scanner(System.in);

		System.out.println("Glitzerfunkel 3000 - Wie ist dein Einhornname ?");

		while (prename.equals("")) {
			System.out.println("Wie lautet der Erste Buchstabe deines Namens ? (a-z)");
			input = scan.nextLine();
			prename = preName(input);

			if (prename == "") {
				System.err.println("Leider eine Fehlerhafte Eingabe :/");
			}
		}

		while (lastname.equals("")) {
			System.out.println("In welchem Monat wurdest du geboren ? (1-12)");
			input = scan.nextLine();
			lastname = lastName(input);

			if (lastname == "") {
				System.err.println("Leider eine Fehlerhafte Eingabe :/");
			}
		}

		System.out.println("Dein Einhornname lautet: " + prename + " " + lastname + " <3");
	}

	/**
	 * Eine total super Klasse, welche anhand von zwei Parametern einen tollen
	 * Einhornnamen generiernt :).
	 * 
	 * TODO: schreit quasi nach ner Enum...
	 * 
	 * @param letter
	 * @param number
	 * @return
	 */
	public static String getEinhornName(String letter, String number) {

		String name = "";

		if (!letter.equals("") && !number.equals("")) {
			name = preName(letter) + " " + lastName(number);
		}

		return name;
	}

	private static String preName(String input) {

		String prename = "";

		switch (input) {
		case "a":
			prename = "sugar";
			break;
		case "b":
			prename = "shiny";
			break;
		case "c":
			prename = "sunny";
			break;
		case "d":
			prename = "happy";
			break;
		case "e":
			prename = "little";
			break;
		case "f":
			prename = "lovely";
			break;
		case "g":
			prename = "glamorous";
			break;
		case "h":
			prename = "amazing";
			break;
		case "i":
			prename = "awesome";
			break;
		case "j":
			prename = "soft";
			break;
		case "k":
			prename = "fantastic";
			break;
		case "l":
			prename = "wet";
			break;
		case "m":
			prename = "horny";
			break;
		case "n":
			prename = "hotty";
			break;
		case "o":
			prename = "sweet";
			break;
		case "p":
			prename = "beautiful";
			break;
		case "q":
			prename = "freaky";
			break;
		case "r":
			prename = "smarty";
			break;
		case "s":
			prename = "phenomenal";
			break;
		case "t":
			prename = "sexy";
			break;
		case "u":
			prename = "hit body";
			break;
		case "v":
			prename = "crazy";
			break;
		case "w":
			prename = "big";
			break;
		case "x":
			prename = "delicious";
			break;
		case "y":
			prename = "hard";
			break;
		case "z":
			prename = "flauschi";
			break;
		default:
			break;
		}

		return prename;
	}

	private static String lastName(String input) {

		String lastname = "";

		switch (input) {
		case "1":
			lastname = "magic mausi";
			break;
		case "2":
			lastname = "crazy candy";
			break;
		case "3":
			lastname = "dupsi schnupsi";
			break;
		case "4":
			lastname = "trashy fishi";
			break;
		case "5":
			lastname = "tinky winky";
			break;
		case "6":
			lastname = "fluffy tuttu";
			break;
		case "7":
			lastname = "blue berry";
			break;
		case "8":
			lastname = "yellow banana";
			break;
		case "9":
			lastname = "happy hopsasa";
			break;
		case "10":
			lastname = "schubi bubi";
			break;
		case "11":
			lastname = "oompa loompa";
			break;
		case "12":
			lastname = "erdbeerkäse";
			break;
		default:
			break;
		}

		return lastname;

	}
}