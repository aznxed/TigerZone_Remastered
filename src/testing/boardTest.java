package testing;

import Game.Board;
import Game.Deck;

public class boardTest {
	public static void main(){
		//Initialize board and deck
		Board gameBoard = new Board();
		Deck deck = new Deck();
		
		//Add a bunch of tiles to the deck
		deck.addTile("TLTJ-");
		deck.addTile("TJJT-");
		deck.addTile("LLJJ-");
		deck.addTile("LJLJ-");
		deck.addTile("TLLT-");
		deck.addTile("JLLJ-");
		deck.addTile("JLJL-");
		deck.addTile("LLLL-");
		deck.addTile("LJTJD");
		deck.addTile("TLLTB");
		deck.addTile("TJTT-");
		deck.addTile("TLTTP");
		
		//Get every tile from the deck and print it out
		while (!deck.isEmpty()) {
			gameBoard.addTile(deck.getTop());
		}
		
		//Print out the board
		gameBoard.print();
	}
}