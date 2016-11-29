package Game;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
	//Boundaries of the Board
	public static int CENTER_CELL = 77;
	public static int MAX_ROWS = CENTER_CELL * 2 - 1;
	public static int MAX_COLS = CENTER_CELL * 2 - 1;
	
	private int topBound;
	private int bottomBound;
	private int leftBound;
	private int rightBound;
	
	public Deck deck = new Deck();
	private Tile[][] board = new Tile[MAX_ROWS][MAX_COLS];
	private List<Tile> placedTiles = new ArrayList<Tile>();
	
	//Is this needed?
	private boolean startTile = true;
	
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
	
	public Tile[][] getBoard(){
		return this.board;
	}
	
	public Tile getTile(int x, int y) {
		if (x >= 0 && x < MAX_ROWS && y >= 0 && y < MAX_COLS) {
			return board[x][y];
		}
		return null;
	}
	
	/********************************/
	/******* Player Functions *******/
	/********************************/
	
	//Place a Tile
	public int placeTile(int x, int y, int rotation, Tile tile){
		
		return 1;
	}
	
	
	/*********************************/
	/******* Testing Functions *******/
	/*********************************/
	
	//Print the Board
	public int printBoard(){
		
		return 1;
	}
	
	//Get Neighboring Tiles
	//TEST NOT COMPLETE
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
	
	//Can I place this tile here?
	//TEST NOT COMPLETED
	public boolean isValid(int x, int y, Tile tile) {
		// Space already has tile
		if (board[x][y] != null) {
			return false;
		}

		List<Tile> nbors = getNeighbors(x, y);

		// No neighbors
		if (nbors.isEmpty()) {
			return false;
		}

		return true;
	}
	
}
