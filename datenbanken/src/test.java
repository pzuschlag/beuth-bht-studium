import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class test {
	public static void main(String[] args) {

		try {
			PrintWriter writer = new PrintWriter(new FileOutputStream("test.txt"));
			writer.write("Hallo :)");
			writer.flush();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
