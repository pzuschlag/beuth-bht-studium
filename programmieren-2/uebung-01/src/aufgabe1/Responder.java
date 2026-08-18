package aufgabe1;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

/**
 * Dient dazu für Eingaben eine Antwort zu generieren.
 * 
 * @author Philip, Leon & Charline
 */
public class Responder {

	// public ResponseMap_Array responseMap = new ResponseMap_Array();
	// public ResponseMap_HashMap responseMap = new ResponseMap_HashMap();
	// public ResponseMap_TreeMap responseMap = new ResponseMap_TreeMap();
	public TreeMap<String, String> responseMap = new TreeMap<String, String>();
	public ArrayList<String> defaultList = new ArrayList<String>();
	public Random randomGenerator = new Random();

	public Responder() {
		fillResponseMap();
		fillDefaultList();
	}

	public String generateResponse(String keyword) {

		String response = responseMap.get(keyword);

		if (response != null) {
			return response;
		}

		return pickDefaultResponse();
	}

	public String pickDefaultResponse() {
		int nextIndex = randomGenerator.nextInt(defaultList.size());
		return defaultList.get(nextIndex);
	}

	private void fillResponseMap() {
		responseMap.put("hello", "Hello, ");
		responseMap.put("sleep", "maybe u can go to bed");
		responseMap.put("lonely", "here is my number, call me !");
		responseMap.put("help", "Oh so u should call the police !");
		responseMap.put("hungry", "u should eat something");
		responseMap.put("boring", "go to youtube and search for 'funny cats' :)");
		responseMap.put(":(", ":)");
		responseMap.put(":)", ":(");
		responseMap.put("yes", "no");
		responseMap.put("no", "yes");
		responseMap.put("are you", "i'm fine and u ?");
		responseMap.put("much", "too much...");
		responseMap.put(",too", "thats great bro !");
		responseMap.put("bro", "bro's before hoes");
		responseMap.put("phil", "just think phil is a pretty guy");
	}

	private void fillDefaultList() {
		defaultList.add("Oh..., this is embarrassing for me please ask me something else");
		defaultList.add("Maybe ur mom know it ?");
		defaultList.add("are u killing me ?");
		defaultList.add("to be or not to be...");
	}
}
