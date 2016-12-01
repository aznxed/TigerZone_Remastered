package testing;

import java.io.IOException;
import Game.Board;
import Game.Deck;
import Game.UI;


public class boardTest {
	public static void main(String[] args) throws IOException {
		Board gameBoard = new Board();
		Deck deck = new Deck();
		//Tile tile1 = new Tile("JJJJ-");
		//Tile tile2 = new Tile("TJTJ-");
		
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
		
		
		//Tile tile1 = new Tile("TLLT-");
		//gameBoard.placeTile(CENTER_CELL, CENTER_CELL, 0, tile1);
		
		
		gameBoard.addTile(deck.getTop());
		gameBoard.addTile(deck.getTop());
		gameBoard.addTile(deck.getTop());
		gameBoard.addTile(deck.getTop());
		gameBoard.addTile(deck.getTop());
		gameBoard.addTile(deck.getTop());
		gameBoard.addTile(deck.getTop());
		gameBoard.addTile(deck.getTop());
		gameBoard.addTile(deck.getTop());
		//gameBoard.addTile(deck.getTop());
		//gameBoard.addTile(deck.getTop());
		//gameBoard.addTile(deck.getTop());
		
		
		
		
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
		
		UI test = new UI();
		test.createUIBoard(gameBoard);
	}
}