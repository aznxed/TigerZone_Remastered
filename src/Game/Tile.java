package Game;

enum TerrainType {
	GAMETRAIL, JUNGLE, LAKE, BUFFALO, CROCODILE, DEER, BOAR, DEN, END
}

public class Tile {
	private int row;
	private int col;
	private String terrainTypeString;
	private int degrees;
	
	private TerrainType[] tilePortionType = new TerrainType[9];
	
	/*******************************************/
	/******* Getter and Setter Functions *******/
	/*******************************************/
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public int getDegrees(){
		return degrees;
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
	
	
	public Tile(String terrainTypeString, int row, int col, int degrees){
		this.terrainTypeString = terrainTypeString;
		this.row = row;
		this.col = col;
		this.degrees = degrees;
		
		//Switch to create array of TerrainType
		
		//Rotate if degrees != 0
		
		
	}
	
	
	

}
