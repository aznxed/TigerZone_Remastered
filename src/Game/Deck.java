package Game;
import java.util.ArrayDeque;
import java.util.Queue;

public class Deck {
	private Queue<Tile> tileDeck = new ArrayDeque<>();

	public void addTile(String tile){
		Tile adding = new Tile(tile);
		tileDeck.add(adding);
	}
	
	public Tile getTop(){
		return tileDeck.remove();
	}
	
	public int getSize(){
		return tileDeck.size();
	}
	
}
