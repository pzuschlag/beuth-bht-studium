package aufgabe1;

/**
 * Abstracter Datentyp zur Ablage von Antworten.
 * 
 * @author Philip, Leon & Charline
 *
 * @param <K>
 * @param <V>
 */
public class MapElement<K, V> {

	private K key;
	private V value;

	public MapElement(K key, V value) {
		setKey(key);
		setValue(value);
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
}