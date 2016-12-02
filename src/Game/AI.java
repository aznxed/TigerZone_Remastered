package Game;

import Game.Board;
import Game.Tile;
import Game.UI;
import testing.debugPrint;

import java.util.Random;

public class AI {
		//Initialize the deck of tiles
		private Board boardA;
		private Board boardB;
		
		//Initialize Decks
		private Deck deckA;
		private Deck deckB;
		
		private debugPrint debugPrint = new debugPrint();
		
		private String firstGame = "";
	
		public void initDecks(){
			deckA = new Deck();
			deckB = new Deck();
			return;
		}
		
		public void initBoards() {
			boardA = new Board();
			boardB = new Board();
			boardA.initBoards(77);
			boardB.initBoards(77);
		}
		
		//Add a tile to the Deck
		public void addDeck(String tile){
			debugPrint.out("Add " + tile + " to deck");
			deckA.addTile(tile);
			deckB.addTile(tile);
			return;
		}
		//Initialize the board
		public void Boards(){
			boardA = new Board();
			boardB = new Board();
			return;
		}
		//Place first tile
		public void placeFirstTile(String tile, int x, int y, int rot){
			boardA.placeTile(tile, 77 + x, 77 + y, rot);
			boardB.placeTile(tile, 77 + x, 77 + y, rot);
			return;
		}
		
		//Place a tile ALWAYS ENEMY TILE
		public void placeTile(String game, String tile, int x, int y, int rot, String meep, int meepPos){
			if (game.equals(firstGame)) {
				boardA.placeTile(tile, x + 77, y + 77, rot);
			}
			else {
				boardB.placeTile(tile, x + 77 , y + 77, rot);
			}
			return;
		}
		
		//Return a move for the given tile 
		public move makeMove(String game, int time, String tile){
			
			if (firstGame.equals("")){
				firstGame = game;
			}
			
			//Move is in form (int xPos, int yPos, int rot, String meep, int meepPos)
			//To Pass Set meep equal to "PASS" and meepPos to -1
			//To Retrieve Meep Set meep equal to "RETRIEVE" and meepPos to -1. Use x and y for coor
			//To ADD Meep Set meep equal to "ADD" and meepPos to -1. Use x and y for coor
			long startTime = System.currentTimeMillis();
			
			move tempMove2;
			if (game.equals(firstGame)) {
				/*if (newTile.equals(boardA.deck.getTop())){
					System.out.println("ERROR Tile Dealt doesn't equal top tile of deck");
				}*/
				tempMove2 = boardA.addTile(tile);
			}
			else {
				/*if (newTile.equals(boardB.deck.getTop())){
					System.out.println("ERROR Tile Dealt doesn't equal top tile of deck");
				}*/
				tempMove2 = boardB.addTile(tile);
			}
			//Translate x and y coordinates and rotation
			move currMove = new move(tempMove2.xPos - 77, tempMove2.yPos - 77, tempMove2.rot, tempMove2.meep, tempMove2.meepPos);
			debugPrint.out("Got a move in: " + (System.currentTimeMillis() - startTime));
			return currMove;
		}
		
		//Tile couldn't be placed
		//Place Tiger at x y
		public void placeMeep(int x, int y) {
			return;
		}
		//Remove Tiger at x y
		public void removeMeep(int x, int y) {
			return;
		}
		//Extra Processing Time Do Whatever
		public void AIProcess(int time){
			
			return;
		}
		public void printBoard(int boardNum) {
			UI window = new UI();
			if (boardNum == 1) {
				window.createUIBoard(boardA);
			}
			else {
				window.createUIBoard(boardB);
			}
		}
}