public class anz10er_Class {

	public static void main(String[] args) {

		System.out.println(anz10er(146));

	}

	static int anz10er(int n) {
		if (n <= 9) {
			return 1;
		}
		return anz10er(n / 10) + 1;
	}

}
