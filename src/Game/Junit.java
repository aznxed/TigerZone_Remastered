package Game;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Junit {
	
	static Board testBoard = new Board();
	static Tile testTile = new Tile();
	
	//Test tiles are being placed on board
	@Test
	public void testA(){
		
		int result = testBoard.placeTile(77, 77, 0, testTile);
		
		assertEquals(result, 1);
		
		//assertEquals(testTile, testBoard.getTile(77, 77));
		//assertEquals(testTile, testBoard.getPlacedTile().get(0));
	}
	
	//Test that we can retrieve tiles from board
	@Test 
	public void testB(){
		
		
		assertEquals(testTile, testBoard.getTile(77, 77));
		
	}
	
	@Test
	public void testC(){
		
		
		assertEquals(testTile, testBoard.getPlacedTile().get(0));
	}
}

