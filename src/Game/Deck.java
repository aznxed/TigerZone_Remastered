package Game;
import java.util.ArrayDeque;
import java.util.Queue;

public class Deck {
	private Queue<String> tileDeck = new ArrayDeque<>();

	public void addTile(String tile){
		tileDeck.add(tile);
	}
	
	public String getTop(){
		return tileDeck.remove();
	}
	
	public int getSize(){
		return tileDeck.size();
	}
	
	public boolean isEmpty() {
		return tileDeck.isEmpty();
	}
	
}
