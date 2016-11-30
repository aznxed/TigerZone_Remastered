package Game;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import Game.TerrainType;

import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Junit {

	// TerrainType[] for testing purposes
	// Continuation road to right
	static TerrainType[] tileTR = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, 
			TerrainType.JUNGLE,TerrainType.GAMETRAIL, TerrainType.GAMETRAIL, 
			TerrainType.JUNGLE, TerrainType.GAMETRAIL,TerrainType.JUNGLE };

	// Continuation road to left
	static TerrainType[] tileTL = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, 
			TerrainType.GAMETRAIL,TerrainType.GAMETRAIL, TerrainType.JUNGLE, 
			TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE };

	static TerrainType[] tileBL = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE, 
			TerrainType.JUNGLE,TerrainType.GAMETRAIL, TerrainType.GAMETRAIL, 
			TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE };

	static TerrainType[] tileBR = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE,
			TerrainType.GAMETRAIL, TerrainType.GAMETRAIL, TerrainType.JUNGLE, 
			TerrainType.JUNGLE, TerrainType.JUNGLE,TerrainType.JUNGLE };
	
	static TerrainType[] tileJ = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
			TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, 
			TerrainType.JUNGLE, TerrainType.JUNGLE,TerrainType.JUNGLE };

	static Board testBoard = new Board();
	static Tile testTile1 = new Tile(tileTR);
	static Tile testTile2 = new Tile(tileTL);
	static Tile testTile3 = new Tile(tileBL);
	static Tile testTile4 = new Tile(tileBR);
	static Tile testTile5 = new Tile(tileJ);
	
	static Tile testTile6 = new Tile(tileJ, 77, 79, 0);

	// Test tiles are being placed on board
	@Test
	public void testA() {

		int result = testBoard.placeTile(77, 77, 0, testTile1);

		assertEquals(result, 1);
		// assertEquals(testTile, testBoard.getTile(77, 77));
		// assertEquals(testTile, testBoard.getPlacedTile().get(0));
	}

	// Test that we can retrieve tiles from board
	@Test
	public void testB() {

		assertEquals(testTile1, testBoard.getTile(77, 77));

	}

	@Test
	public void testC() {

		assertEquals(testTile1, testBoard.getPlacedTile().get(0));
		
		// testBoard.removeTile(77, 77, testTile1);
	}

	// Test isValid
	@Test
	public void testD() {

		assertTrue(testBoard.isValid(77, 78, testTile2));
	}

	// Test getNeighbors
	@Test
	public void testE() {

		int result1 = testBoard.placeTile(77, 78, 0, testTile2);
		assertEquals(result1, 1);
		int result2 = testBoard.placeTile(78, 78, 0, testTile3);
		assertEquals(result2, 1);
		int result3 = testBoard.placeTile(78, 77, 0, testTile4);
		assertEquals(result3, 1);

		List<Tile> nbors1 = testBoard.getNeighbors(77, 77);
		assertEquals(nbors1.size(), 2);

		List<Tile> nbors2 = testBoard.getNeighbors(77, 78);
		assertEquals(nbors2.size(), 2);
		
		List<Tile> nbors3 = testBoard.getNeighbors(78, 78);
		assertEquals(nbors3.size(), 2);
		
		List<Tile> nbors4 = testBoard.getNeighbors(78, 77);
		assertEquals(nbors4.size(), 2);
	}
	
	//test rotateTile
	@Test
	public void testF(){
		
		Tile rotTile90 = testBoard.rotateTile(testTile1, 90);
		int result1 = testBoard.placeTile(78, 76, 0, rotTile90);
		assertEquals(result1, 1);
		
		Tile rotTile180 = testBoard.rotateTile(testTile1, 180);
		int result2 = testBoard.placeTile(78, 79, 0, rotTile180);
		assertEquals(result2, 1);
		testBoard.removeTile(78, 79, testTile1);
		
		Tile rotTile270 = testBoard.rotateTile(testTile1, 270);
		int result3 = testBoard.placeTile(78, 79, 0, rotTile270);
		assertEquals(result3, 1);
		//testBoard.removeTile(78, 79, testTile1);
	}
	
	//Test getValidOrients and getPossibleMoves
	@Test
	public void testG(){
		
		//Tile rotTile90 = testBoard.rotateTile(testTile1, 180);
		//int result1 = testBoard.placeTile(79, 79, 0, rotTile90);
		//assertEquals(result1, 1);
		
		//I tried a bunch of different combinations by just changing the coords
		//and the tile
		
		/*testBoard.setBottomBound(76);
		testBoard.setTopBound(79);
		testBoard.setLeftBound(75);
		testBoard.setRightBound(80);*/
		
		/*System.out.println(testBoard.getBottomBound()); 
		System.out.println(testBoard.getTopBound());
		System.out.println(testBoard.getBottomBound());
		System.out.println(testBoard.getBottomBound());*/
		
		List<Integer> posOrients = testBoard.getValidOrients(77, 79, testTile5);
		assertEquals(posOrients.size(), 4);
		
		List<Tile> posMoves = testBoard.getPossibleMoves(testTile6);
		assertEquals(posMoves.size(), 32);
		
		System.out.println(testBoard.getBottomBound()); 
		System.out.println(testBoard.getTopBound());
		System.out.println(testBoard.getRightBound());
		System.out.println(testBoard.getLeftBound());
		
	}
	
	
	

}
