package aufgabe1;

import java.util.List;

/**
 * Vorgebebebn Methoden für die implementierung der ResponseMap
 * 
 * @author Philip
 */
public interface ResponseMapInterface {

	public String get(String key);

	public void put(String key, String msg);

	public boolean contains(String key);

	public List<String> allKeys();

	public int size();

}
