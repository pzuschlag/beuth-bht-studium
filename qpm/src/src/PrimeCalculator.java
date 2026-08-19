package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Determine prime utilities
 */
public class PrimeCalculator {

	/**
	 * 
	 */
	private PrimeCalculator() {
	}

	/**
	 * Returns the first 100 primes
	 *
	 * @return ArrayList<Integer> with primes
	 */
	public static List<Integer> getFirst100PrimeNum() {
		int num = 0;
		ArrayList<Integer> primes = new ArrayList<>();

		while (primes.size() < 100) {
			if (isPrime(num)) {
				primes.add(num);
			}
			num++;
		}
		return primes;
	}

	/**
	 * Returns true if the given int is prime, else return false
	 *
	 * @return boolean
	 */
	public static boolean isPrime(int num) {
		// chekc if num is less than 2
		if (num < 2) {
			return false;
		} else {
			// check if num is a multiple of 2
			if (num % 2 == 0)
				return false;
			// if not, then just check the odds
			for (int i = 3; i * i <= num; i += 2) {
				if (num % i == 0)
					return false;
			}
			return true;
		}
	}

	/**
	 * Based on the entered integer value, it determines a 5-digit random number and outputs all primes from 0 to this
	 * random number.
	 *
	 * @return ArrayList<Integer> with primes
	 */
	public static List<Integer> getPrimesUpToRandomNumber(int num) {
		int number;

		if (num < 0) {
			number = num * (-1);
		} else {
			number = num;
		}

		StringBuilder result = new StringBuilder("");

		for (int i = 0; i < 5; i++) {
			result.append(((Double) (Math.random() * (100 * number))).toString().substring(0, 1));
		}

		return getPrimesUpTo(Integer.parseInt(result.toString()));
	}

	/**
	 * Returns all primes up to a given number
	 *
	 * @return ArrayList<Integer> with primes
	 */
	private static ArrayList<Integer> getPrimesUpTo(int number) {
		ArrayList<Integer> primes = new ArrayList<>();

		for (int i = 0; i <= number; i++) {
			if (isPrime(i)) {
				primes.add(i);
			}
		}
		return primes;
	}
}
