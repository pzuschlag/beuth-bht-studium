package src;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class JUnitTests {
	static ArrayList<Integer> numbers = new ArrayList<>();

	/**
	 * Initialize primes
	 * 
	 * @throws IOException
	 * @throws NumberFormatException
	 * 
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("/Users/Philip/Documents/git/QPM_Uebung/src/txt/primeNumbers.txt"));
		String line;

		while ((line = br.readLine()) != null) {
			numbers.add(Integer.parseInt(line.replace(", ", "")));
		}

		br.close();
	}

	/**
	 * Tests for PrimeCalculator
	 */
	@Test
	public void testPrimeCalculator() {
		// tests isPrime()
		assertTrue("isPrime - valid number", testIsPrime(26357));
		assertFalse("isPrime - valid number", testIsPrime(35252));
		assertFalse("isPrime - zero", testIsPrime(0));
		assertFalse("isPrime - negative number", testIsPrime(-1));
		assertFalse("isPrime - negative prime number", testIsPrime(-35251));

		// tests getFirst100PrimeNum()
		assertTrue("getFirst100PrimeNum", testGetFirst100PrimeNum());

		// tests getPrimesUpToRandomNumber()
		assertTrue("getPrimesUpToRandomNumber - random number", testGetPrimesUpToRandomNumber(12341));
		assertTrue("getPrimesUpToRandomNumber - negativ random number", testGetPrimesUpToRandomNumber(-12341));
		assertTrue("getPrimesUpToRandomNumber - zero", testGetPrimesUpToRandomNumber(0));
	}

	/**
	 * Tests isPrime()
	 * 
	 * @param num
	 * @return
	 */
	public boolean testIsPrime(int num) {
		return PrimeCalculator.isPrime(num);
	}

	/**
	 * Tests getFirst100PrimeNum()
	 * 
	 * @return
	 */
	public boolean testGetFirst100PrimeNum() {
		List<Integer> primes = PrimeCalculator.getFirst100PrimeNum();

		for (int n : primes) {
			if (!numbers.contains(n)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Tests getPrimesUpToRandomNumber
	 * 
	 * @param num
	 * @return
	 */
	public boolean testGetPrimesUpToRandomNumber(int num) {
		List<Integer> primes = PrimeCalculator.getPrimesUpToRandomNumber(num);

		for (int n : primes) {
			if (!numbers.contains(n)) {
				return false;
			}
		}

		return true;
	}
}