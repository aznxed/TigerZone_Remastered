package Game;

import Game.TerrainType;

enum TerrainType {
	GAMETRAIL, JUNGLE, LAKE, BUFFALO, CROCODILE, DEER, BOAR, DEN, END
}

public class Tile {
	private int conquered = 0;
	private int row;
	private int col;
	private String terrainTypeString;
	private int degrees = 0;

	private Board board;
	private TerrainType[] tilePortionType = new TerrainType[9];

	/*******************************************/
	/******* Getter and Setter Functions *******/
	/*******************************************/

	public void setConquered()
	{
		this.conquered = 1;
	}
	public int getConquered()
	{
		return conquered;
	}
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getDegrees(){
		return degrees;
	}
	
	public void setDegrees(int degrees){
		this.degrees = degrees;
	}
	
	public String getTileType(){
		return terrainTypeString;
	}
	
	public TerrainType getTopEdge() {
		return tilePortionType[1];
	}

	public TerrainType getBottomEdge() {
		return tilePortionType[7];
	}

	public TerrainType getLeftEdge() {
		return tilePortionType[3];
	}

	public TerrainType getRightEdge() {
		return tilePortionType[5];
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public TerrainType[] getTilePortionType() {
		return tilePortionType;
	}

	public void setTilePortionType(TerrainType[] tilePortionType) {
		this.tilePortionType = tilePortionType;
	}
	
	/********************************/
	/******* Tile Constructor *******/
	/********************************/
	
	public Tile(TerrainType[] tilePortionType){
		this.tilePortionType = tilePortionType;
	}
	
	public Tile(TerrainType[] tilePortionType, int row, int col, int degrees){
		this.tilePortionType = tilePortionType;
	}
	
	public Tile(String terrainTypeString){
		this.terrainTypeString = terrainTypeString;
		this.tilePortionType = returnTileTerrain(terrainTypeString);
	}
	
	public TerrainType[] returnTileTerrain(String terrainType){
		switch(terrainType){
			case"JJJJ-":	
				TerrainType[] tileA = { TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE };
				return tileA;
			case"JJJJX":	
				TerrainType[] tileB = { TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.DEN,
					TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE }; 
				return tileB;
			case"JJTJX":	
				TerrainType[] tileC = { TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.DEN,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE };
				return tileC;
			case"TTTT-":	
				TerrainType[] tileD = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.END,
						TerrainType.GAMETRAIL, TerrainType.JUNGLE,
						TerrainType.GAMETRAIL, TerrainType.JUNGLE }; 
				return tileD;
			case"TJTJ-":	
				TerrainType[] tileE = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE }; 
				return tileE;
			case"TJJT-":	
				TerrainType[] tileF = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,TerrainType.JUNGLE, 
						TerrainType.GAMETRAIL,TerrainType.GAMETRAIL, TerrainType.JUNGLE, 
						TerrainType.JUNGLE,TerrainType.JUNGLE, TerrainType.JUNGLE }; 
				return tileF;
			case"TJTT-":	
				TerrainType[] tileG = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.END,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE };
				return tileG;
			case"LLLL-":	
				TerrainType[] tileH = { TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE }; 
				return tileH;	
			case"JLLL-":	
				TerrainType[] tileI = { TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE };
				return tileI;	
			case"LLJJ-":	
				TerrainType[] tileJ = { TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE }; 
				return tileJ;	
			case"JLJL-":	
				TerrainType[] tileK = { TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE };
				return tileK;
			case"LJLJ-":	
				TerrainType[] tileL = { TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.JUNGLE }; 
				return tileL;
			case"LJJJ-":	
				TerrainType[] tileM = { TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE }; 
				return tileM;
			case"JLLJ-":	
				TerrainType[] tileN = { TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.JUNGLE };
				return tileN;
			case"TLJT-":	
				TerrainType[] tileO = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE };
				return tileO;
			case"TLJTP":	
				TerrainType[] tileP = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.JUNGLE };
				return tileP;
			case"JLTT-":	
				TerrainType[] tileQ = { TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
						TerrainType.GAMETRAIL, TerrainType.JUNGLE };
				return tileQ;
			case"JLTTB":	
				TerrainType[] tileR = { TerrainType.JUNGLE, TerrainType.JUNGLE,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
						TerrainType.GAMETRAIL, TerrainType.JUNGLE }; 
				return tileR;
			case"TLTJ-":	
				TerrainType[] tileS = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE };
				return tileS;
			case"TLTJD":	
				TerrainType[] tileT = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE };
				return tileT;
			case"TLLL-":	
				TerrainType[] tileU = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.END,
						TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE }; 
				return tileU;
			case"TLTT-":	
				TerrainType[] tileV = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.END,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE }; 
				return tileV;
			case"TLTTP":	
				TerrainType[] tileW = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.END,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE }; 
				return tileW;
			case"TLLT-":	
				TerrainType[] tileX = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.LAKE }; 
				return tileX;
			case"TLLTB":	
				TerrainType[] tileY = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.LAKE }; 
				return tileY;
			case"LJTJ-":	
				TerrainType[] tileZ = { TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE }; 
				return tileZ;
			case"LJTJD":	
				TerrainType[] tileZZ = { TerrainType.JUNGLE, TerrainType.LAKE,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.END,
						TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE };
				return tileZZ;
			case"TLLLC":	
				TerrainType[] tileZZZ = { TerrainType.JUNGLE, TerrainType.GAMETRAIL,
						TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
						TerrainType.LAKE };
				return tileZZZ;
			}
		
		TerrainType[] empty = {}; 
		return empty;
	}	


}
