package testing;

import java.io.IOException;
import Game.Board;
import Game.Deck;
import Game.UI;


public class boardTest {
	public static void main(String[] args) throws IOException {
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
		
		
		
		
		/*
		gameBoard.placeTile(CENTER_CELL, CENTER_CELL, 0, deck.getTop());
		gameBoard.placeTile(CENTER_CELL+1, CENTER_CELL, 270, deck.getTop());
		gameBoard.placeTile(CENTER_CELL, CENTER_CELL+1, 90, deck.getTop());
		gameBoard.placeTile(CENTER_CELL, CENTER_CELL-1, 0, deck.getTop());
		gameBoard.placeTile(CENTER_CELL-1, CENTER_CELL, 90, deck.getTop());
		gameBoard.placeTile(CENTER_CELL, CENTER_CELL-2, 180, deck.getTop());
		gameBoard.placeTile(CENTER_CELL+2, CENTER_CELL, 0, deck.getTop());
		gameBoard.placeTile(CENTER_CELL+2, CENTER_CELL-1, 270, deck.getTop());
		gameBoard.placeTile(CENTER_CELL+1, CENTER_CELL+1, 270, deck.getTop());
		gameBoard.placeTile(CENTER_CELL-1, CENTER_CELL+1, 270, deck.getTop());
		gameBoard.placeTile(CENTER_CELL, CENTER_CELL+2, 180, deck.getTop());
		gameBoard.placeTile(CENTER_CELL-2, CENTER_CELL, 90, deck.getTop());
		*/

		//System.out.println(pos.size());
		//gameBoard.addTile(deck.getTop());
		//gameBoard.placeTile(CENTER_CELL+1, CENTER_CELL, 90, tile2);
		//System.out.println(tile2.getCol());
		
		//Print out the board
		gameBoard.printBoard();
	}
}