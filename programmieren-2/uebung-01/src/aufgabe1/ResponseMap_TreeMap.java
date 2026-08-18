package aufgabe1;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * ResponseMap ralisiert mithilfe der TreeMap
 * 
 * @author Philip, Leon & Charline
 */
public class ResponseMap_TreeMap implements ResponseMapInterface {

	public TreeMap<String, String> responseMap = new TreeMap<String, String>();

	@Override
	public String get(String key) {
		return responseMap.get(key);
	}

	@Override
	public void put(String key, String value) {
		responseMap.put(key, value);
	}

	@Override
	public boolean contains(String key) {
		return responseMap.containsKey(key);
	}

	@Override
	public List<String> allKeys() {
		return new ArrayList<String>(responseMap.keySet());
	}

	@Override
	public int size() {
		return responseMap.size();
	}

}
