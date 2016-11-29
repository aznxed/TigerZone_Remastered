package Game;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Junit {

	// TerrainType[] for testing purposes
	// Continuation road to right
	static TerrainType[] tileTR = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
			TerrainType.GAMETRAIL, TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
			TerrainType.JUNGLE };

	// Continuation road to left
	static TerrainType[] tileTL = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
			TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE };

	static Board testBoard = new Board();
	static Tile testTile1 = new Tile(tileTR);
	static Tile testTile2 = new Tile(tileTL);

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
		//testBoard.removeTile(77, 77, testTile1);
	}

	// Test isValid
	@Test
	public void testD() {

		assertTrue(testBoard.isValid(77, 78, testTile2));
	}

	// Test getNeighbors
	@Test
	public void testE() {

		int result = testBoard.placeTile(77, 78, 0, testTile2);
		assertEquals(result, 1);
	}

}
