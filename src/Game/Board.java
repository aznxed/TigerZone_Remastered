package Game;

import java.util.ArrayList;
import java.util.List;

import Game.sect;
import Game.move;
import Game.position;

public class Board {
	public int size;
	public int center;
	
	private static final int DEER = 1;
	private static final int BUFFALO = 2;
	private static final int BOAR = 3;

	private static final int J = 1;
	private static final int L = 2;
	private static final int T = 3;
	private static final int D = 4;
	
	private List<position> posList = new ArrayList<position>();
	
	private block[][] board = new block[154][154];
	
	public int topBound = 0;
	public int bottomBound = 0;
	public int leftBound = 0;
	public int rightBound = 0;
	
	//Chain
	private lake[] boardLake;
	private den[] boardDen;
	private jungle[] boardJungle;
	private trail[] boardTrail;
	private int[] cluster;
	
	private int featureNum = 0;
	
	public class meepStruct {
		public int meepNum = 0;
		public int meepType = 0;
	}
	
	public class block {
		public String tileName = "NULL";
		public int rot = 0;
		public int tileNum = 0;
		public meepStruct[] meeps = new meepStruct[9];
		public piece piece = new piece();
	}
	
	//tiles on the board
	//int type
	//int number (used to distinguish connected parts)
	public class piece {
		public sect[] sects = new sect[9];
		public int anim = 0;
	}
	
	public class prey {
		public int num = 0;
		public boolean deer = false;
		public boolean buffalo = false;
		public boolean boar = false;
	}
	
	public void addprey(prey tilePrey, int preyToAdd) {
		switch(preyToAdd) {
		case DEER:
			if (!tilePrey.deer) {
				//No Deer in tile chain
				tilePrey.deer = true;
				tilePrey.num++;
			}
			break;
		case BUFFALO:
			if (!tilePrey.buffalo) {
				tilePrey.buffalo = true;
				tilePrey.num++;
			}
			break;
		case BOAR:
			if (!tilePrey.boar) {
				tilePrey.boar = true;
				tilePrey.num++;
			}
			break;
		}
	}
	
	public class den {
		public boolean comp = false;
		public int tileNum = 0;
		public int tigerNum = 0;
	}
	
	public class jungle {
		public int tigerNum = 0;
		public List<Integer> lakes = new ArrayList<Integer>();
		public List<Integer> dens = new ArrayList<Integer>();
	}
	
	public class lake {
		public boolean comp = false;
		public int tileNum = 0;
		public prey preyNum;
		public int tigerNum = 0;
		public int crocNum = 0;
	}
	
	public class trail {
		public int tileNum = 0;
		public int tigerNum = 0;
		public int crocNum = 0;
		public prey preyNum;
	}
	
	public block[][] getBoard() {
		return board;
	}
	
	public void expandBounds(int x, int y) {
		if (y + 1 > topBound) { topBound = y + 1; System.out.println("NEW TOP " + (y + 1));}
		if (y - 1 < bottomBound) { bottomBound = y - 1;  System.out.println("NEW BOT " + (y - 1));}
		if (x + 1 > rightBound) { rightBound = x + 1;  System.out.println("NEW RIGHT " + (x + 1));}
		if (x - 1 < leftBound) { leftBound = x -1;  System.out.println("NEW LEFT " + (x - 1));}
	}
	
	//Rotate piece
	public piece rotate(piece piece, int rot) {
		piece tempPiece = new piece();
		tempPiece.sects[4] = piece.sects[4];
		switch(rot){
			case 0:
				return piece;
			case 90: 
				tempPiece.sects[0] = piece.sects[2];
				tempPiece.sects[1] = piece.sects[5];
				tempPiece.sects[2] = piece.sects[8];
				tempPiece.sects[3] = piece.sects[1];
				tempPiece.sects[5] = piece.sects[7];
				tempPiece.sects[6] = piece.sects[0];
				tempPiece.sects[7] = piece.sects[3];
				tempPiece.sects[8] = piece.sects[6]; 
				break;
			case 180: 
				tempPiece.sects[0] = piece.sects[8];
				tempPiece.sects[1] = piece.sects[7];
				tempPiece.sects[2] = piece.sects[6];
				tempPiece.sects[3] = piece.sects[5];
				tempPiece.sects[5] = piece.sects[3];
				tempPiece.sects[6] = piece.sects[2];
				tempPiece.sects[7] = piece.sects[1];
				tempPiece.sects[8] = piece.sects[0];
				break;
			case 270:
				tempPiece.sects[0] = piece.sects[6];
				tempPiece.sects[1] = piece.sects[3];
				tempPiece.sects[2] = piece.sects[0];
				tempPiece.sects[3] = piece.sects[7];
				tempPiece.sects[5] = piece.sects[1];
				tempPiece.sects[6] = piece.sects[8];
				tempPiece.sects[7] = piece.sects[5];
				tempPiece.sects[8] = piece.sects[2];
				break;
			default:
				System.out.println("ERROR INVALID ROTATION: " + rot);
		}
		return tempPiece;
	}
	
	public void initBoards(int tiles) {
		size = (tiles * 2) - 1;
		center = tiles;
		topBound = center + 1;
		bottomBound = center - 1;
		rightBound = center + 1;
		leftBound = center - 1;
		
		//2d Array of Tiles (Path to tile.png, int rotation, int[9] meep)
		//Used to Display to map
		block tempBlock = new block();
		tempBlock.tileName = "NULL";
		board = new block[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = null;
			}
		}
		
		//Initialize a cluster of each type (# of tiles * 4)
		boardLake = new lake[tiles * 4];
		boardTrail = new trail[tiles * 4];
		boardJungle = new jungle[tiles * 4];
		boardDen = new den[tiles * 4];
		
		//Initialize array where each tile feature points to 
		cluster = new int[tiles * 9];
	}
	
	//Adds new possible tile positions to tileList
	public void addPossiblePos(int x, int y){
		expandBounds(x, y);
		if (board[x + 1][y] == null) {
			//System.out.println("ADD NEW POS1");
			posList.add(new position((x + 1), y));
		}
		if (board[x - 1][y] == null) {
			posList.add(new position((x - 1), y));
			//System.out.println("ADD NEW POS2");
		}
		if (board[x][y + 1] == null) {
			posList.add(new position(x, (y + 1)));
			//System.out.println("ADD NEW POS3");
		}
		if (board[x][y - 1] == null) {
			posList.add(new position(x, (y - 1)));
			//System.out.println("ADD NEW POS4");
		}
	}
	
	//Generate Clusters and chains
	public void processTile (piece tile, int x, int y) {
		for (int i = 0; i < 9; i++) {
			//Generate a new cluster in array for 
			int clusterNum = featureNum + tile.sects[i].num;
			
			//Check North Side
			//tile.sects[1].type == board[x][y+1].piece.sect[7];
			//Create new chain
			//Create new cluster that points to it
			//set tile num
			//merge with other chain
			
			/*if (cluster[clusterNum] == null) {
				
				
			}
			else {
				//Check other sides to merge chain
			}*/
			//Check each side that isnt null
			//If side then merge chains 
			//set clusters to new chain
			if (board[x - 1][y] == null ) {
				//Create new cluster and new chain
			}
			else {
				//Create new cluster and merge 
				//Create new chain
			}
			
		}
	}
	
	//Always merge b into a
	public void mergeChains(int a, int b, int type) {
		switch (type) {
			case J:
				//process Jungle merge
				boardJungle[a].tigerNum += boardJungle[b].tigerNum;
				for (int i = 0; i < boardJungle[b].lakes.size(); i++) {
					boardJungle[a].lakes.add(boardJungle[b].lakes.get(i));
				}
				for (int i = 0; i < boardJungle[b].dens.size(); i++) {
					boardJungle[a].dens.add(boardJungle[b].dens.get(i));
				}
				break;
			case T:
				//process Trail merge
				break;
			case L:
				//process Lake merge
				break;
			default:
				System.out.println("ERROR INVALID TYPE IN MERGE CHAIN: " + type);
		}
	}
	
	//Return a valid move
	public move addTile(String tile) {
		System.out.println("PLACING TILE: " + tile);
		move tempMove;
		piece tempPiece = transTile(tile);
		//Get a valid move
		System.out.println("Position List Size: " + posList.size());
		for (int i = 0; i < posList.size(); i++) {
			//Get Best move (Valid)
			position currMove = posList.get(i);
			int currX = currMove.x;
			int currY = currMove.y;
			for (int j = 0; j < 4; j++) {
				if (isValidMove(rotate(tempPiece, j * 90), currX, currY)) {
					//Add tile to board
					block tempBlock = new block();
					tempBlock.piece = rotate(tempPiece, j * 90);
					tempBlock.tileName = tile;
					tempBlock.rot = j * 90;
					board[currX][currY] = tempBlock;
					System.out.println("Board X: " + currX + " Y: " + currY + " Rot: " + tempBlock.rot);
					tempMove = new move(currX, currY, j * 90, "", 0);
					//add new positions to moves list
					addPossiblePos(currX, currY);
					//Update bounds
					expandBounds(currX, currY);
					//Remove placed move from list
					posList.remove(i);
					return tempMove;
				}
				else {
					System.out.println("Not a valid move");
				}
			}
		}
		//No valid Moves
		tempMove = new move(0, 0, 0, "PASS", -1);
		return tempMove;
	}
	
	//Used to place start tile and enemy tiles
	public move placeTile(String tile, int x, int y, int rot) {
		//rotate block
		piece tempPiece = rotate(transTile(tile), rot);
		
		//place tile in first position
		if (posList.isEmpty()) {
			System.out.println("EMPTY LIST");
			//translate tile
			block tempBlock = new block();
			tempBlock.piece = tempPiece;
			tempBlock.tileName = tile;
			tempBlock.rot = rot;
			board[x][y] = tempBlock;
			System.out.println("Board X: " + x + " Y: " + y + " Rot: " + rot);
			//add new positions to moves list
			addPossiblePos(x, y);
			//processTile(tempBlock.piece, x, y);
			move tempMess = new move(x, y, rot, "", -1);
			return tempMess;
		}
		else {
			//translate tile
			block tempBlock = new block();
			tempBlock.piece = tempPiece;
			tempBlock.tileName = tile;
			tempBlock.rot = rot;
			board[x][y] = tempBlock;
			System.out.println("Board X: " + x + " Y: " + y + " Rot: " + rot);
			//add new positions to moves list
			addPossiblePos(x, y);
			//Remove placed move from list
			position tempPos = new position(x,y);
			System.out.println(posList.indexOf(tempPos));
			posList.remove(tempPos);
			//processTile(tempBlock.piece, x, y);
			move tempMess = new move(x, y, rot, "", -1);
			return tempMess;
		}
		//translate tile
		//place tile
			//Remove location from moves list
			//Add new positions to move list
			//Link Chains on all sides or create new chains
			//Set tile number 
			
	}
	
	public void print() {
		UI UI = new UI();
		UI.createUIBoard(this);
	}
	
	public boolean isValidMove(piece piece, int x, int y) {
		//System.out.println("TRYING X: " + x + " Y: " + y);
		//Top side
		if (board[x][y + 1] != null && board[x][y + 1].piece.sects[7].type != piece.sects[1].type){
			//System.out.print("TOP");
			return false;
		}
		//Bottom side
		if (board[x][y - 1] != null && board[x][y - 1].piece.sects[1].type != piece.sects[7].type){
			//System.out.println("BOTTOM");
			return false;
		}
		//Right side
		if (board[x + 1][y] != null && board[x + 1][y].piece.sects[3].type != piece.sects[5].type){
			//System.out.println("RIGHT");
			return false;
		}
		//Left side
		if (board[x - 1][y] != null && board[x - 1][y].piece.sects[5].type != piece.sects[3].type){
			//System.out.println("LEFT");
			return false;
		}
		return true;
	}
	
	
	public piece transTile(String tileName) {
		piece tempPiece = new piece();
		switch(tileName) {
			case "JJJJ-":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(J, 1, 0), new sect(J, 1, 0), 
											new sect(J, 1, 0), new sect(J, 1, 0), new sect(J, 1, 0),
											new sect(J, 1, 0), new sect(J, 1, 0), new sect(J, 1, 0)};
				break;
			case "JJJJX":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(J, 1, 0), new sect(J, 1, 0), 
											new sect(J, 1, 0), new sect(D, 2, 0), new sect(J, 1, 0),
											new sect(J, 1, 0), new sect(J, 1, 0), new sect(J, 1, 0)};
				break;
			case "JJTJX":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(J, 1, 0), new sect(J, 1, 0), 
											new sect(J, 1, 0), new sect(D, 2, 0), new sect(J, 1, 0),
											new sect(J, 1, 0), new sect(T, 3, 0), new sect(J, 1, 0)};
				break;
			case "TTTT-":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 1, 0), 
											new sect(T, 2, 0), new sect(T, 2, 0), new sect(T, 2, 0),
											new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 1, 0)};
				break;
			case "TJTJ-":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 1, 0), 
											new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 1, 0),
											new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 1, 0)};
				break;
			case "TJJT-":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 1, 0), 
											new sect(T, 2, 0), new sect(T, 2, 0), new sect(J, 1, 0),
											new sect(J, 1, 0), new sect(J, 2, 0), new sect(J, 1, 0)};
				break;
			case "TJTT-":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 1, 0), 
											new sect(T, 2, 0), new sect(T, 2, 0), new sect(J, 1, 0),
											new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 1, 0)};
				break;
			case "LLLL-":
				tempPiece.sects = new sect[]{new sect(L, 1, 0), new sect(L, 1, 0), new sect(L, 1, 0), 
											new sect(L, 1, 0), new sect(L, 1, 0), new sect(L, 1, 0),
											new sect(L, 1, 0), new sect(L, 1, 0), new sect(L, 1, 0)};
				break;
			case "JLLL-":
				tempPiece.sects = new sect[]{new sect(J, 2, 0), new sect(J, 2, 0), new sect(J, 2, 0), 
											new sect(L, 1, 0), new sect(J, 2, 0), new sect(L, 1, 0),
											new sect(L, 1, 0), new sect(L, 1, 0), new sect(L, 1, 0)};
				break;
			case "LLJJ-":
				tempPiece.sects = new sect[]{new sect(J, 2, 0), new sect(L, 1, 0), new sect(L, 1, 0), 
											new sect(J, 2, 0), new sect(J, 2, 0), new sect(L, 1, 0),
											new sect(J, 2, 0), new sect(J, 2, 0), new sect(J, 2, 0)};
				break;
			case "JLJL-":
				tempPiece.sects = new sect[]{new sect(J, 2, 0), new sect(J, 2, 0), new sect(J, 2, 0), 
											new sect(L, 1, 0), new sect(L, 1, 0), new sect(L, 1, 0),
											new sect(J, 3, 0), new sect(J, 3, 0), new sect(J, 3, 0)};
				break;
			case "LJLJ-":
				tempPiece.sects = new sect[]{new sect(L, 2, 0), new sect(L, 2, 0), new sect(L, 2, 0), 
											new sect(J, 1, 0), new sect(J, 1, 0), new sect(J, 1, 0),
											new sect(L, 3, 0), new sect(L, 3, 0), new sect(L, 3, 0)};
				break;
			case "LJJJ-":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(L, 2, 0), new sect(J, 1, 0), 
											new sect(J, 1, 0), new sect(J, 1, 0), new sect(J, 1, 0),
											new sect(J, 1, 0), new sect(J, 1, 0), new sect(J, 1, 0)};
				break;
			case "JLLJ-":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(J, 1, 0), new sect(J, 1, 0), 
											new sect(J, 1, 0), new sect(J, 1, 0), new sect(L, 2, 0),
											new sect(J, 1, 0), new sect(L, 3, 0), new sect(J, 1, 0)};
				break;
			case "TLJT-":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 4, 0), 
											new sect(T, 2, 0), new sect(T, 2, 0), new sect(L, 3, 0),
											new sect(J, 4, 0), new sect(L, 4, 0), new sect(J, 4, 0)};
				break;
			case "TLJTP":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 4, 0), 
											new sect(T, 2, 0), new sect(T, 2, 0), new sect(L, 3, 0),
											new sect(J, 4, 0), new sect(L, 4, 0), new sect(J, 4, 0)};
				tempPiece.anim = BOAR;
				break;
			case "JLTT-":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(J, 1, 0), new sect(J, 1, 0), 
											new sect(T, 2, 0), new sect(T, 2, 0), new sect(L, 3, 0),
											new sect(J, 4, 0), new sect(T, 2, 0), new sect(J, 1, 0)};
				break;
			case "JLTTB":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(J, 1, 0), new sect(J, 1, 0), 
											new sect(T, 2, 0), new sect(T, 2, 0), new sect(L, 3, 0),
											new sect(J, 4, 0), new sect(T, 2, 0), new sect(J, 1, 0)};
				tempPiece.anim = BUFFALO;
				break;
			case "TLTJ-":
				tempPiece.sects = new sect[]{new sect(J, 4, 0), new sect(T, 2, 0), new sect(J, 1, 0), 
											new sect(J, 4, 0), new sect(T, 2, 0), new sect(L, 3, 0),
											new sect(J, 4, 0), new sect(T, 2, 0), new sect(J, 1, 0)};
				break;
			case "TLTJD":
				tempPiece.sects = new sect[]{new sect(J, 4, 0), new sect(T, 2, 0), new sect(J, 1, 0), 
											new sect(J, 4, 0), new sect(T, 2, 0), new sect(L, 3, 0),
											new sect(J, 4, 0), new sect(T, 2, 0), new sect(J, 1, 0)};
				tempPiece.anim = DEER;
				break;
			case "TLLL-":
				tempPiece.sects = new sect[]{new sect(J, 2, 0), new sect(T, 3, 0), new sect(J, 2, 0), 
											new sect(L, 1, 0), new sect(L, 1, 0), new sect(L, 1, 0),
											new sect(L, 1, 0), new sect(L, 1, 0), new sect(L, 1, 0)};
				break;
			case "TLTT-":
				tempPiece.sects = new sect[]{new sect(J, 5, 0), new sect(T, 2, 0), new sect(J, 1, 0), 
											new sect(T, 2, 0), new sect(T, 2, 0), new sect(L, 3, 0),
											new sect(J, 4, 0), new sect(T, 2, 0), new sect(J, 1, 0)};
				break;
			case "TLTTP":
				tempPiece.sects = new sect[]{new sect(J, 5, 0), new sect(T, 2, 0), new sect(J, 1, 0), 
											new sect(T, 2, 0), new sect(T, 2, 0), new sect(L, 3, 0),
											new sect(J, 4, 0), new sect(T, 2, 0), new sect(J, 1, 0)};
				tempPiece.anim = BOAR;
				break;
			case "TLLT-":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 4, 0), 
											new sect(T, 2, 0), new sect(T, 2, 0), new sect(L, 3, 0),
											new sect(J, 4, 0), new sect(L, 3, 0), new sect(L, 3, 0)};
				break;
			case "TLLTB":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 4, 0), 
											new sect(T, 2, 0), new sect(T, 2, 0), new sect(L, 3, 0),
											new sect(J, 4, 0), new sect(L, 3, 0), new sect(L, 3, 0)};
				tempPiece.anim = BUFFALO;
				break;
			case "LJTJ-":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(L, 3, 0), new sect(J, 4, 0), 
											new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 4, 0),
											new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 4, 0)};
				break;
			case "LJTJD":
				tempPiece.sects = new sect[]{new sect(J, 1, 0), new sect(L, 3, 0), new sect(J, 4, 0), 
											new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 4, 0),
											new sect(J, 1, 0), new sect(T, 2, 0), new sect(J, 4, 0)};
				tempPiece.anim = DEER;
				break;
			default: 
				System.out.println("ERROR COULD NOT FIND TILE: " + tileName);
			}
		return tempPiece;
	}

	//Scoring
	//Jungles
	//Scored at the end
	//3 * adjacent complete lake + 5 * adjacent complete den
	
	//Lakes 
	//Unique Prey animals = animalsTypes - # crocs
	//Complete = 2 * tileNum * 1 + unique prey animals
	//Incomplete = 1 * tileNum * 1 + unique prey animals
	
	//Game-Trails
	//Unique Prey animals = animalsTypes - # crocs
	//Complete = 1 * tileNum + unique prey animals
	//Incomplete = 1 * tileNum + unique prey animals
	
	//Dens
	//# of tiles
	
}