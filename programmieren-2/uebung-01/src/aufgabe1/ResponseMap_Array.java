package aufgabe1;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;

/**
 * ResponseMap realisiert mithilfe einer ArrayList<String, String>
 * 
 * @author Philip
 */
public class ResponseMap_Array implements ResponseMapInterface {

	private ArrayList<MapElement<String, String>> responseList = new ArrayList<MapElement<String, String>>();

	@Override
	public String get(String key) {

		for (MapElement<String, String> map : responseList) {
			if (map.getKey().equals(key)) {
				return map.getValue();
			}
		}
		return null;
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
		responseList.add(new MapElement<String, String>(key, msg));
	}

	@Override
	public boolean contains(String key) {
		return allKeys().contains(key);
	}

	@Override
	public List<String> allKeys() {
		List<String> keyList = new ArrayList<String>();
		for (MapElement<String, String> e : responseList) {
			keyList.add(e.getKey());
		}

		return keyList;
	}

	@Override
	public int size() {
		return responseList.size();
	}
}
