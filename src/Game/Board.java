package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Game.Tile;
import Game.TerrainType;
import Game.move;
import testing.debugPrint;
import Game.UI;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class Board {
	// Boundaries of the Board
	public static int CENTER_CELL = 77;
	public static int MAX_ROWS = CENTER_CELL * 2 - 1;
	public static int MAX_COLS = CENTER_CELL * 2 - 1;
	
	private int topBound = 77;
	private int bottomBound = 77;
	private int leftBound = 77;
	private int rightBound = 77;

	public debugPrint debugPrint = new debugPrint();
	public Deck deck = new Deck();
	private Tile[][] board = new Tile[MAX_ROWS][MAX_COLS];
	private List<Tile> placedTiles = new ArrayList<Tile>();

	//TESTING
	public int tigers = 7;

	/*******************************************/
	/******* Getter and Setter Functions *******/
	/*******************************************/

	public int getTopBound() {
		return this.topBound;
	}

	public int getBottomBound() {
		return this.bottomBound;
	}

	public int getLeftBound() {
		return this.leftBound;
	}

	public int getRightBound() {
		return this.rightBound;
	}

	public void setTopBound(int upperBound) {
		this.topBound = upperBound;
	}

	public void setBottomBound(int lowerBound) {
		this.bottomBound = lowerBound;
	}

	public void setLeftBound(int leftBound) {
		this.leftBound = leftBound;
	}

	public void setRightBound(int rightBound) {
		this.rightBound = rightBound;
	}


	/*********************************/
	/******* Testing Functions *******/
	/*********************************/

	public void print() {
		UI test = new UI();
		test.createUIBoard(this);
		return;
	}

	public List<Tile> getNeighbors(int x, int y) {
		List<Tile> n = new ArrayList<Tile>();

		if (y > 0) {
			if (board[x][y - 1] != null) {
				n.add(board[x][y - 1]);
			}
		}

		if (y < MAX_COLS) {
			if (board[x][y + 1] != null) {
				n.add(board[x][y + 1]);
			}
		}

		if (x > 0) {
			if (board[x - 1][y] != null) {
				n.add(board[x - 1][y]);
			}
		}

		if (x < MAX_ROWS) {
			if (board[x + 1][y] != null) {
				n.add(board[x + 1][y]);
			}
		}

		return n;
	}

	public List<Integer> getNeighbors2(int x, int y) {
		List<Integer> n = new ArrayList<Integer>();

		//TOP IS OPEN
		if (x > 0) {
			if (board[x - 1][y] == null) {
				n.add(1);
			}
		}

		//LEFT IS OPEN
		if (y > 0) {
			if (board[x][y - 1] == null) {
				n.add(2);
			}
		}

		//RIGHT IS OPEN
		if (y < MAX_COLS) {
			if (board[x][y + 1] == null) {
				n.add(3);
			}
		}

		//BOTTOM IS OPEN
		if (x < MAX_ROWS) {
			if (board[x + 1][y] == null) {
				n.add(4);
			}
		}

		return n;
	}

	public boolean isValid(int x, int y, Tile tile) {

		if (board[x][y] != null) {
			return false;
		}

		if (placedTiles.isEmpty()) {
			return true;
		}

		else {
			List<Tile> nbors = getNeighbors(x, y);
			if (nbors.isEmpty()) {
				return false;
			}

			else{
				return true;
			}

		}
	}

	public Tile rotateTile(Tile tile, int degrees) {
		Tile rotateTile = new Tile(tile.getTileType());

		if (degrees != 0) {
			TerrainType[] rotateArr = new TerrainType[9];
			if (degrees == 90) {
				rotateArr[0] = tile.getTilePortionType()[2];
				rotateArr[1] = tile.getTilePortionType()[5];
				rotateArr[2] = tile.getTilePortionType()[8];
				rotateArr[3] = tile.getTilePortionType()[1];
				rotateArr[4] = tile.getTilePortionType()[4];
				rotateArr[5] = tile.getTilePortionType()[7];
				rotateArr[6] = tile.getTilePortionType()[0];
				rotateArr[7] = tile.getTilePortionType()[3];
				rotateArr[8] = tile.getTilePortionType()[6];
			}
			if (degrees == 180) {
				for (int i = 0; i < 9; i++) {
					rotateArr[i] = tile.getTilePortionType()[8 - i];
				}
			}
			if (degrees == 270) {
				rotateArr[0] = tile.getTilePortionType()[6];
				rotateArr[1] = tile.getTilePortionType()[3];
				rotateArr[2] = tile.getTilePortionType()[0];
				rotateArr[3] = tile.getTilePortionType()[7];
				rotateArr[4] = tile.getTilePortionType()[4];
				rotateArr[5] = tile.getTilePortionType()[1];
				rotateArr[6] = tile.getTilePortionType()[8];
				rotateArr[7] = tile.getTilePortionType()[5];
				rotateArr[8] = tile.getTilePortionType()[2];
			}
			rotateTile.setTilePortionType(rotateArr);
			rotateTile.setDegrees(degrees);
			rotateTile.setCol(tile.getCol());
			rotateTile.setRow(tile.getRow());

			return rotateTile;
		}
		rotateTile.setTilePortionType(tile.getTilePortionType());
		rotateTile.setDegrees(0);
		rotateTile.setCol(tile.getCol());
		rotateTile.setRow(tile.getRow());
		return rotateTile;
	}

	public List<Integer> getValidOrients(int x, int y, Tile tile) {
		List<Integer> validOrients = new ArrayList<Integer>();

		List<Tile> nbors = getNeighbors(x, y);

		// Add possible orientation to list
		validOrients.add(0);
		validOrients.add(90);
		validOrients.add(180);
		validOrients.add(270);

		// For each neighboring tile, check if sides match for each orientation
		// If not, remove from validOrients
		for (int i = 0; i < nbors.size(); i++) {
			Tile nTile = nbors.get(i);

			if (validOrients.isEmpty()) {
				break;
			}
			// Check if its in same row
			if (nTile.getRow() == x) {
				if (nTile.getCol() > y) {
					// This is right neighbor
					if (nTile.getLeftEdge() != tile.getRightEdge()) {
						if (validOrients.contains(0)) {
							validOrients.remove(Integer.valueOf(0));
						}
					}
					if (nTile.getLeftEdge() != tile.getBottomEdge()) {
						if (validOrients.contains(90)) {
							validOrients.remove(Integer.valueOf(90));
						}
					}
					if (nTile.getLeftEdge() != tile.getLeftEdge()) {
						if (validOrients.contains(180)) {
							validOrients.remove(Integer.valueOf(180));
						}
					}
					if (nTile.getLeftEdge() != tile.getTopEdge()) {
						if (validOrients.contains(270)) {
							validOrients.remove(Integer.valueOf(270));
						}
					}
				} else {
					// This is left neighbor
					if (nTile.getRightEdge() != tile.getLeftEdge()) {
						if (validOrients.contains(0)) {
							validOrients.remove(Integer.valueOf(0));
						}
					}
					if (nTile.getRightEdge() != tile.getTopEdge()) {
						if (validOrients.contains(90)) {
							validOrients.remove(Integer.valueOf(90));
						}
					}
					if (nTile.getRightEdge() != tile.getRightEdge()) {
						if (validOrients.contains(180)) {
							validOrients.remove(Integer.valueOf(180));
						}
					}
					if (nTile.getRightEdge() != tile.getBottomEdge()) {
						if (validOrients.contains(270)) {
							validOrients.remove(Integer.valueOf(270));
						}
					}
				}
			}

			if (nTile.getCol() == y) {
				if (nTile.getRow() > x) {
					// This is bottom neighbor
					if (nTile.getTopEdge() != tile.getBottomEdge()) {
						if (validOrients.contains(0)) {
							validOrients.remove(Integer.valueOf(0));
						}
					}
					if (nTile.getTopEdge() != tile.getLeftEdge()) {
						if (validOrients.contains(90)) {
							validOrients.remove(Integer.valueOf(90));
						}
					}
					if (nTile.getTopEdge() != tile.getTopEdge()) {
						if (validOrients.contains(180)) {
							validOrients.remove(Integer.valueOf(180));
						}
					}
					if (nTile.getTopEdge() != tile.getRightEdge()) {
						if (validOrients.contains(270)) {
							validOrients.remove(Integer.valueOf(270));
						}
					}
				} else {
					// This is top neighbor
					if (nTile.getBottomEdge() != tile.getTopEdge()) {
						if (validOrients.contains(0)) {
							validOrients.remove(Integer.valueOf(0));
						}
					}
					if (nTile.getBottomEdge() != tile.getRightEdge()) {
						if (validOrients.contains(90)) {
							validOrients.remove(Integer.valueOf(90));
						}
					}
					if (nTile.getBottomEdge() != tile.getBottomEdge()) {
						if (validOrients.contains(180)) {
							validOrients.remove(Integer.valueOf(180));
						}
					}
					if (nTile.getBottomEdge() != tile.getLeftEdge()) {
						if (validOrients.contains(270)) {
							validOrients.remove(Integer.valueOf(270));
						}
					}
				}
			}
		}

		return validOrients;
	}

	public List<Tile> getPossibleMoves(Tile tile) {
		List<Tile> possibleMoves = new ArrayList<Tile>();
		for (int i = getTopBound(); i <= getBottomBound(); i++) {
			for (int j = getLeftBound(); j <= getRightBound(); j++) {
				if (isValid(i, j, tile)) {
					List<Integer> validOrients = getValidOrients(i, j, tile);

					for (int k = 0; k < validOrients.size(); k++) {
						Tile newTile = new Tile(tile.getTileType());
						newTile.setRow(i);
						newTile.setCol(j);
						debugPrint.out(i + " ");
						debugPrint.out(j);
						possibleMoves.add(rotateTile(newTile, validOrients.get(k)));

					}
				}
			}
		}

		return possibleMoves;
	}

	public Tile[][] getBoard() {
		return this.board;
	}

	public List<Tile> getPlacedTile() {
		return this.placedTiles;
	}

	public Tile getTile(int x, int y) {
		if (x >= 0 && x < MAX_ROWS && y >= 0 && y < MAX_COLS) {
			return this.board[x][y];
		}
		return null;
	}

	public void removeTile(int x, int y, Tile tile) {
		board[x][y] = null;
		placedTiles.remove(tile);
	}


	/********************************/
	/******* Player Functions *******/
	/********************************/

	//Places a tile on the board
	//Used for start tile and enemy tile
	public int placeTile(int x, int y, int rotation, Tile tile) {
		List<Integer> validOrients = getValidOrients(x, y, tile);
		if(!validOrients.contains(rotation)){
			System.out.println("INVALID Orientation, Tile not placed");
			// 0 = false
			return 0;
		}

		if(rotation != 0) {
			TerrainType[] rotateArr = new TerrainType[9];
			if (rotation == 90) {
				rotateArr[0] = tile.getTilePortionType()[2];
				rotateArr[1] = tile.getTilePortionType()[5];
				rotateArr[2] = tile.getTilePortionType()[8];
				rotateArr[3] = tile.getTilePortionType()[1];
				rotateArr[4] = tile.getTilePortionType()[4];
				rotateArr[5] = tile.getTilePortionType()[7];
				rotateArr[6] = tile.getTilePortionType()[0];
				rotateArr[7] = tile.getTilePortionType()[3];
				rotateArr[8] = tile.getTilePortionType()[6];
			}
			if (rotation == 180) {
				for (int i = 0; i < 9; i++) {
					rotateArr[i] = tile.getTilePortionType()[8 - i];
				}
			}
			if (rotation == 270) {
				rotateArr[0] = tile.getTilePortionType()[6];
				rotateArr[1] = tile.getTilePortionType()[3];
				rotateArr[2] = tile.getTilePortionType()[0];
				rotateArr[3] = tile.getTilePortionType()[7];
				rotateArr[4] = tile.getTilePortionType()[4];
				rotateArr[5] = tile.getTilePortionType()[1];
				rotateArr[6] = tile.getTilePortionType()[8];
				rotateArr[7] = tile.getTilePortionType()[5];
				rotateArr[8] = tile.getTilePortionType()[2];
			}
			tile.setTilePortionType(rotateArr);
			tile.setDegrees(rotation);
		}

		if (!isValid(x, y, tile)) {
			System.out.println("INVALID, Tile not placed");
			// 0 = false
			return 0;
		}
		// add tile to board
		// give tile coords
		placedTiles.add(tile);
		this.board[x][y] = tile;
		tile.setCol(y);
		tile.setRow(x);
		tile.setBoard(this);

		if (x == getTopBound() && x > 0) {
			setTopBound(x - 1);
		}
		if (x == getBottomBound() && x < MAX_ROWS - 1) {
			setBottomBound(x + 1);
		}
		if (y == getLeftBound() && y > 0) {
			setLeftBound(y - 1);
		}
		if (y == getRightBound() && y < MAX_COLS - 1) {
			setRightBound(y + 1);
		}
		return 1;
		// return true;
	}

	private List<Tile> possiblePlaces;

	class option {
		private int score; //calculated score
		private int ref;//refers to the tile position in possible moves
		public int getRef() {
			return ref;
		}
		public int getScore() {
			return score;
		}
		public void setRef(int temp) {
			this.ref = temp;
		}

		public void setScore(int temp) {
			this.score = temp;
		}

	}
	public void sortList(List<option> list)
	{
		{
			Collections.sort(list, new Comparator<option>() {
				@Override
				public int compare(final option object1, final option object2) {
					return object1.score < object2.score ? -1
							: object1.score > object2.score ? 1
							: 0;
				}
			});
		}
	}
	//Get a valid move for the tile
	public move addTile(Tile tile) {
			move tempMove;
			ArrayList<option> scores = new ArrayList<option>();
			long startTime = System.currentTimeMillis(); //start clock
			if (startTime - System.currentTimeMillis() < 8000) {
				possiblePlaces = getPossibleMoves(tile);
				if(possiblePlaces.isEmpty())
				{
					System.out.println("PASS");
					return new move(0, 0, 0, "PASS", -1);
				}
				System.out.println("Succsess... .... finding best place for" + tile.getTileType());
				for (int i = 0; i < possiblePlaces.size(); i++) {
					option temp = new option();
					//System.out.println("adding refrence to: " + i);
					temp.setRef(i);
					temp.setScore(0);

					scores.add(temp);
					//System.out.println("Score it:" + scores.get(i).getScore());
				}
			}
			int score;
			if (startTime - System.currentTimeMillis() < 8000) {
				System.out.println("Sorting ....");
				for (int i = 0; i < scores.size()-1; i++)
				{
					int temp = scores.get(i).getRef();
					int x = possiblePlaces.get(temp).getCol();
					int y = possiblePlaces.get(scores.get(i).getRef()).getRow();

					//System.out.println("Proximity is "+ getProximity(x,y));
					score = getProximity(x,y);
					//System.out.println("Score is:" + score);
					scores.get(i).setScore(score);
					//System.out.println("Score it:" + scores.get(i).getScore());
				}
				sortList(scores);
			}
			//!!!!!!!!!!!!INSERT MORE FUNCTIONS!!!!!!!!!!!!!!!//

			//Add to board
			System.out.println("Best move: " +scores.get(scores.size() - 1).getScore());
			Tile addTile = possiblePlaces.get(scores.get(scores.size() - 1).getRef());//takes best move from the array list

			int x = addTile.getRow();
			int y = addTile.getCol();
			debugPrint.out("Tile placed at " + x + " " + y);
			Tile tempTile = new Tile(tile.getTileType());
			tempTile.setConquered();
			this.placeTile(x, y, addTile.getDegrees(), tempTile);

			if (x == getTopBound() && x > 0) {
				setTopBound(x - 1);
			}
			if (x == getBottomBound() && x < MAX_ROWS - 1) {
				setBottomBound(x + 1);
			}
			if (y == getLeftBound() && y > 0) {
				setLeftBound(y - 1);
			}
			if (y == getRightBound() && y < MAX_COLS - 1) {
				setRightBound(y + 1);
			}

			tempMove = new move(x, y, addTile.getDegrees(), "", 0);

			//SCORING STATS
			if(tigers > 0){
				//PLACE ON DEN
				if (tile.getTilePortionType()[4] == (TerrainType.DEN)){
					tempMove.meepPos = 5;
					tempMove.meep = "TIGER";
					tigers--;
				}

				else{
					List<Integer> open = getNeighbors2(x,y);
					if(open.size() != 0){
						for(int a = 0; a < open.size(); a++){
							if(open.get(a) == 1)//top
							{
								if(tile.getTilePortionType()[1] == (TerrainType.LAKE)){
									//if(!open.contains(2) || )
								}

								else if((tile.getTilePortionType()[1] == (TerrainType.GAMETRAIL))
										&&(tile.getTilePortionType()[4] == (TerrainType.END))){
									tempMove.meepPos = 2;
									tempMove.meep = "TIGER";
									tigers--;
									break;
								}
							}

							else if(open.get(a) == 2)//left
							{
								if(tile.getTilePortionType()[3] == (TerrainType.LAKE)){
									//if(!open.contains(2) || )
								}

								else if((tile.getTilePortionType()[3] == (TerrainType.GAMETRAIL))
										&&(tile.getTilePortionType()[4] == (TerrainType.END))){
									tempMove.meepPos = 4;
									tempMove.meep = "TIGER";
									tigers--;
									break;
								}
							}

							else if(open.get(a) == 3)//right
							{
								if(tile.getTilePortionType()[5] == (TerrainType.LAKE)){
									//if(!open.contains(2) || )
								}

								else if((tile.getTilePortionType()[5] == (TerrainType.GAMETRAIL))
										&&(tile.getTilePortionType()[4] == (TerrainType.END))){
									tempMove.meepPos = 6;
									tempMove.meep = "TIGER";
									tigers--;
									break;
								}
							}

							else if(open.get(a) == 4)//bottom
							{
								if(tile.getTilePortionType()[7] == (TerrainType.LAKE)){
									//if(!open.contains(2) || )
								}

								else if((tile.getTilePortionType()[7] == (TerrainType.GAMETRAIL))
										&&(tile.getTilePortionType()[4] == (TerrainType.END))){
									tempMove.meepPos = 8;
									tempMove.meep = "TIGER";
									tigers--;
									break;
								}
							}
						}
					}
				}
			}






		System.out.println("A Tiger was placed at " + tempMove.meepPos + "at tile " + tile.getTileType());
		return tempMove;
	}
	public int getProximity(int x, int y) {
		int count = 0;
		if (board[x][y - 1] != null)
		{
			count += board[x][y - 1].getConquered();
		}
		if (board[x][y + 1] != null)
		{
			count += board[x][y + 1].getConquered();
		}
		if (board[x][y - 1] != null)
		{
			count += board[x][y-1].getConquered();
		}
		if (board[x-1][y] != null)
		{
			count += board[x-1][y].getConquered();
		}
		if (board[x+1][y + 1] != null)
		{
			count += board[x+1][y+1].getConquered()/4;
		}
		if (board[x-1][y - 1] != null)
		{
			count += board[x-1][y-1].getConquered()/4;
		}
		if (board[x+1][y - 1] != null)
		{
			count += board[x+1][y-1].getConquered()/4;
		}
		if (board[x-1][y + 1] != null)
		{
			count += board[x-1][y+1].getConquered()/4;
		}



		//half points for corners





		return count;
	}
	public static void main(String[] args){
		Board board = new Board();
		Deck deck = new Deck();

		deck.addTile("JJJJ-");
		deck.addTile("JJJJX");
		deck.addTile("TJTT-");
		deck.addTile("TJTJ-");
		deck.addTile("TTTT-");
		deck.addTile("TJTT-");
		deck.addTile("JJJJ-");

		board.addTile(deck.getTop());
		board.addTile(deck.getTop());
		board.addTile(deck.getTop());
		board.addTile(deck.getTop());
		board.addTile(deck.getTop());
		board.addTile(deck.getTop());
		board.addTile(deck.getTop());

		UI ui = new UI();
		ui.createUIBoard(board);

	}

	
}
