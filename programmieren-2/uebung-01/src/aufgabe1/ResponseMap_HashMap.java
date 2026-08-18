package aufgabe1;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * Realisierung der ResponseMap mithilfe einer HashMap
 * 
 * @author Philip, Leon & Charline
 */
public class ResponseMap_HashMap implements ResponseMapInterface {

	private HashMap<String, String> responseList = new HashMap<String, String>();

	public String get(String key) {

		return responseList.get(key);
	}

	@Override
	public void put(String key, String msg) {

		if (contains(key)) {
			try {
				throw new KeyException();
			} catch (KeyException e) {
				e.printStackTrace();
			}
		}
		responseList.put(key, msg);

	}

	@Override
	public boolean contains(String key) {
		return allKeys().contains(key);
	}

	@Override
	public List<String> allKeys() {
		return new ArrayList<String>(responseList.keySet());
	}

	@Override
	public int size() {
		return responseList.size();
	}
}
