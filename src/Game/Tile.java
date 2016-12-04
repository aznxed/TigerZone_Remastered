package Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Game.TerrainType;
import Game.Tile;

enum TerrainType {
	GAMETRAIL, JUNGLE, LAKE, BUFFALO, CROCODILE, DEER, BOAR, DEN, END
}

public class Tile {
	private int row;
	private int col;
	private boolean visited;
	private String terrainTypeString;
	private int degrees = 0;
	private int lakeEdgeCount;
	private int trailEdgeCount;

	private Board board;
	private TerrainType[] tilePortionType = new TerrainType[9];

	/*******************************************/
	/******* Getter and Setter Functions *******/
	/*******************************************/

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

	public int getDegrees() {
		return degrees;
	}

	public void setDegrees(int degrees) {
		this.degrees = degrees;
	}

	public String getTileType() {
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

	public boolean isDen() {
		return tilePortionType[4] == TerrainType.DEN;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getTrailEdgeCount() {
		return trailEdgeCount;
	}

	public void setTrailEdgeCount(int trailEdgeCount) {
		this.trailEdgeCount = trailEdgeCount;
	}

	/**
	 * @return the lakeEdgeCount
	 */
	public int getLakeEdgeCount() {
		return lakeEdgeCount;
	}

	/**
	 * @param lakeEdgeCount
	 *            the lakeEdgeCount to set
	 */
	public void setLakeEdgeCount(int lakeEdgeCount) {
		this.lakeEdgeCount = lakeEdgeCount;
	}

	public boolean isLakeCenter() {
		return tilePortionType[4] == TerrainType.LAKE;
	}

	// returns true if this Tile includes a trail continuation
	public boolean isContinuation() {
		return trailEdgeCount == 2;
	}

	// returns true if this Tile is a t-junction, 4 way cross road, or one way
	// road end
	public boolean isTrailEnd() {
		return (trailEdgeCount > 2 || trailEdgeCount == 1);
	}

	/**
	 * Returns the score for all trails emanating from this tile.
	 * 
	 * @return
	 */
	public int getTrailScore() {

		int score = 0;

		// make sure Tile pertains to board
		if (this.getBoard() == null) {
			return 0;
		}

		// get the number of trails emanating from this tile
		int trailCount = getTrailEdgeCount();

		// System.out.println("trailCount1 = " + trailCount);

		if (trailCount == 2) {
			--trailCount;
		}

		// no trails emanating from this tile, so return a
		// score of 0
		if (trailCount == 0) {
			return score;
		}

		// A list of trails emanating from this tile
		List<List<Tile>> trails = new ArrayList<List<Tile>>();

		List<Tile> trail;
		if (this.getTopEdge() == TerrainType.GAMETRAIL) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(this);
			isTrailComplete(trail, getBoard().getTile(row - 1, col));
			trails.add(trail);
		}
		if (this.getBottomEdge() == TerrainType.GAMETRAIL) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(this);
			isTrailComplete(trail, getBoard().getTile(row + 1, col));
			trails.add(trail);
		}
		if (this.getRightEdge() == TerrainType.GAMETRAIL) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(this);
			isTrailComplete(trail, getBoard().getTile(row, col + 1));
			trails.add(trail);
		}
		if (this.getLeftEdge() == TerrainType.GAMETRAIL) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			trail = new ArrayList<Tile>();
			trail.add(this);
			isTrailComplete(trail, getBoard().getTile(row, col - 1));
			trails.add(trail);
		}

		// Filtered lsit of trails based on the original trails list
		// We'll be using addTrail() to filter out any duplicates and
		// non-existent trails
		// (trails of only size 1)
		List<List<Tile>> uniqueTrails = new ArrayList<List<Tile>>();

		for (List<Tile> originalTrail : trails) {
			addDistinctList(uniqueTrails, originalTrail);
		}

		// System.out.println("uniqueTrails size = " + uniqueTrails.size());
		// System.out.println("trailCount = " + trailCount);

		// set score to trailCount - the number of trails emanating from tile.
		// Each of the trails will be scored individually
		score = trailCount - uniqueTrails.size();

		// System.out.println("score = " + score);

		// Set used to determine if multiple trails share the same end tile
		Set<Tile> endTiles = new HashSet<Tile>();

		// Iterate through all the "unique" trails that emanate from this tile
		for (List<Tile> cTrail : uniqueTrails) {
			Tile frontTile = cTrail.get(0);
			Tile endTile = cTrail.get(cTrail.size() - 1);
			endTiles.add(endTile);

			// System.out.println("cTrail.size = " + cTrail.size());
			score += cTrail.size();
			// if the trail has a proper ending, then see if there is a tiger in
			// the trail
			if (endTile.isTrailEnd()) {
				// Tiger
			}
			// if the trail forms a loop, compensate for the fact that the start
			// tile is found more than once in the trail
			if (frontTile == endTile) {
				score--;
				// if it is a junction and there is a loop, then two of the
				// junction trails heads bridge one trail, so we must decrement
				// one more time
				if (trailCount > 2) {
					score--;
				}
			}
			// System.out.println("score2 = " + score);

		}

		// compensate for those trails that shared the same end tile, thus
		// it was counted multiple times
		if (!(uniqueTrails.size() > 1 && endTiles.size() == 1)) {
			score -= (uniqueTrails.size() - endTiles.size());
		}

		// compensate for the fact that all the trails share the same
		// starting tile, thus its counted multiple times.
		// if (uniqueTrails.size() > 0) {
		// score -= (uniqueTrails.size() - 1);
		// }
		return score;
	}

	// Get the score if this is a den tile
	// Return 0 if it's not a den
	public int getDenScore() {

		int score = 0;

		// If this is not a den tile, just return
		if (!this.isDen()) {
			return score;
		}

		// Else get all its possible 8 neighbors
		List<Tile> neighbors = this.getBoard().getDenNeighbors(this.getRow(), this.getCol());

		// Return the size of that list plus one to include itself
		return neighbors.size() + 1;
	}

	/**
	 * Returns the score for lakes emanating from this tile
	 * 
	 * @return
	 */
	public int getLakeScore() {

		int score = 0;

		// make sure Tile pertains to board
		if (this.getBoard() == null) {
			return 0;
		}

		// return 0 if this is not a lake tile
		if (!isLake()) {
			return score;
		}

		// System.out.println("lake score1 = " + score);

		// Create a list of possible lakes emanating from this tile
		List<List<Tile>> lakes = new ArrayList<List<Tile>>();
		List<Tile> lake;

		if (this.getTopEdge() == TerrainType.LAKE) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(this);
			isLakeComplete(lake, getBoard().getTile(row - 1, col));
			lakes.add(lake);
		}
		if (this.getBottomEdge() == TerrainType.LAKE) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(this);
			isLakeComplete(lake, getBoard().getTile(row + 1, col));
			lakes.add(lake);
		}
		if (this.getRightEdge() == TerrainType.LAKE) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(this);
			isLakeComplete(lake, getBoard().getTile(row, col + 1));
			lakes.add(lake);
		}
		if (this.getLeftEdge() == TerrainType.LAKE) {
			this.getBoard().clearVisited();
			this.setVisited(true);
			lake = new ArrayList<Tile>();
			lake.add(this);
			isLakeComplete(lake, getBoard().getTile(row, col - 1));
			lakes.add(lake);
		}

		// System.out.println("lakes size = " + lakes.size());

		// Filtered list of lakes based on the original lakes list
		// We'll be using addDistinctList() to filter out any duplicates and
		// non-existent lakes (lakes of only size 1)
		List<List<Tile>> uniqueLakes = new ArrayList<List<Tile>>();

		// Begin the filtering process
		for (List<Tile> originalLake : lakes) {
			addDistinctList(uniqueLakes, originalLake);
		}

		// if this tile is a lake center, then do not touch its base score,
		// which should be 1
		if (!isLakeCenter()) {
			score = getLakeEdgeCount() - uniqueLakes.size();
		} else {
			score = getLakeEdgeCount();
		}

		// System.out.println("unique lakes size = " + uniqueLakes.size());
		// System.out.println("getLakeEdgeCount = " + getLakeEdgeCount());
		// System.out.println("lake score2 = " + score);
		// System.out.println("lake center = " + isLakeCenter());
		// System.out.println();

		boolean lakeComplete = true;
		// if this tile contains a lake center, then all lakes emanating from
		// this tile should represent one lake
		if (isLakeCenter()) {

			// Set used to determine if multiple lakes share the same end tile
			Set<Tile> endTiles = new HashSet<Tile>();

			// iterate through all the lake channels and see if they all
			// complete. we must iterate through all the channel regardless of
			// complete or not because they must all be scored
			for (List<Tile> l : uniqueLakes) {

				// System.out.println("unique lake size = " + l.size());

				// get the first and last tile in the channel to see if the
				// channel makes a complete loop
				Tile startTile = l.get(0);
				Tile endTile = l.get(l.size() - 1);
				endTiles.add(endTile);

				// if the channel does not complete in a lake center, then its
				// not a complete lake, so the channels emanating from this tile
				// do not represent one contiguous lake
				if (endTile.isLakeCenter()) {
					lakeComplete = false;
				}
				// does this one channel form a loop
				if (startTile != endTile) {
					score += l.size() - 1;
				} else {
					score += l.size() - 2;
					// we must do this because looping around to the same tile
					// also completes a lake!!
					lakeComplete = true;
				}
			}

			// System.out.println("endTiles size = " + endTiles.size());

			// compensate for those lakes that shared the same end tile, thus
			// it was counted multiple times
			score -= (uniqueLakes.size() - endTiles.size());

			// if all the channels collectively form one lake, then update the
			// score accordingly. Note that if there are no surrounding lake
			// tiles, then no need to check for completeness
			if (uniqueLakes.size() > 0 && lakeComplete) {
				score *= 2;
			}

			return (score == 0) ? 1 : score;
		}

		// System.out.println("not lake center");

		// Base score must be 0 when working with separate lakes.
		score = 0;

		// tile does not contain a lake center, therefore all lake channel
		// emanating from this tile must be treated as separate lakes/channels.
		for (List<Tile> l : uniqueLakes) {

			int lakeScore = 0;

			// System.out.println("unique lake size = " + l.size());
			lakeComplete = true;

			Tile startTile = l.get(0);
			Tile endTile = l.get(l.size() - 1);

			if (endTile.isLakeCenter()) {
				lakeComplete = false;
			}
			// System.out.println("lake complete = " + lakeComplete);

			if (startTile != endTile) {
				lakeScore += l.size();
			} else {
				lakeScore += l.size() - 1;
			}

			if (lakeComplete) {
				lakeScore *= 2;
			}

			// update the base score with this separate lake's score
			score += lakeScore;
		}

		return score;
	}

	// Check if the tile completes an existing den
	public static boolean doesCompleteDen(Tile tile) {

		List<Tile> neighbors = tile.getBoard().getDenNeighbors(tile.getRow(), tile.getCol());
		for (Tile t : neighbors) {
			if (t.isDen()) {
				if (isDenComplete(t)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean isDenComplete(Tile den) {

		/*
		 * If the Tile has not been assigned to a board, then its not in-play
		 */
		if (den.getBoard() == null) {
			return false;
		}

		/* Get this Den tile's four neighbors */
		List<Tile> neighbors = den.getBoard().getNeighbors(den.getRow(), den.getCol());

		/* A Den must have neighbors on all four sides */
		if (neighbors.size() != 4) {
			return false;
		}

		/*
		 * Now make sure that the top and bottom neighbors have neighbors to
		 * their left and right. Note that there is no need to check that this
		 * Den tile's left and right neighbors have top and bottom tiles!
		 */
		for (Tile n : neighbors) {
			if (n.getRow() == den.getRow() + 1 || n.getRow() == den.getRow() - 1) {
				// this is either my top or bottom neighbor, so make sure it has
				// neighbors to the left and right
				if (den.getBoard().getTile(n.getRow(), n.getCol() + 1) != null) {
					if (den.getBoard().getTile(n.getRow(), n.getCol() - 1) != null) {
						continue;
					} else {
						return false;
					}
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * This recursive method follows a trail and determines if the trail
	 * completes or not. As the method follows the trail, it adds to the path
	 * followed;i.e., the end result is the full trail/path followed.
	 */
	public static boolean isTrailComplete(List<Tile> path, Tile current) {

		// do nothing if there is no current tile
		if (current == null) {
			return false;
		}

		// record from where we came
		Tile previous = path.get(path.size() - 1);

		// add this current tile to the path
		path.add(current);

		// if this current tile was previously visited, then the trail has
		// looped and its complete
		if (current.isVisited()) {
			return true;
		}

		// mark current tile as being visited
		current.setVisited(true);

		// return true if we've reached a trail end; e.g., a Den
		if (current.isTrailEnd()) {
			return true;
		}

		// get the neighbors for this current tile
		List<Tile> neighbors = current.getBoard().getNeighbors(current.getRow(), current.getCol());

		boolean completed;

		// Iterate through the neighbors that allow the road to continue; i.e.,
		// find those neighboring tiles that share a trail edge
		for (Tile neighbor : neighbors) {

			// can't go back from where we came
			if (neighbor == previous) {
				continue;
			}

			// Check if neighbor is in same row
			if (neighbor.getRow() == current.getRow()) {
				if (neighbor.getCol() > current.getCol()) {
					// This is right neighbor - is there a trail
					// leading to that neighbor?
					if (current.getRightEdge() == TerrainType.GAMETRAIL) {
						// yes, so follow that trail
						completed = isTrailComplete(path, neighbor);
						// return true if the trail completed
						if (completed == true) {
							return true;
						}
						// move on to next neighbor
					}
				} else {
					// This is left neighbor- is there a trail
					// leading to that neighbor?
					if (current.getLeftEdge() == TerrainType.GAMETRAIL) {
						completed = isTrailComplete(path, neighbor);
						if (completed == true) {
							return true;
						}
					}
				}
			}

			// If not in the same row, it must be in the same column
			if (neighbor.getCol() == current.getCol()) {
				if (neighbor.getRow() > current.getRow()) {
					// This is bottom neighbor - is there a trail
					// leading to that neighbor
					if (current.getBottomEdge() == TerrainType.GAMETRAIL) {
						completed = isTrailComplete(path, neighbor);
						if (completed == true) {
							return true;
						}
					}

				} else {
					if (current.getTopEdge() == TerrainType.GAMETRAIL) {
						// This is top neighbor - is there a trail
						// leading to that neighbor
						completed = isTrailComplete(path, neighbor);
						if (completed == true) {
							return true;
						}
					}
				}
			}
		}

		// the trail never completed.
		return false;
	}

	/**
	 * This recursive method is used to follow a lake.
	 * 
	 * @param lake
	 *            - the lake being built
	 * @param current
	 *            - the tile that is currently on
	 * @return
	 */
	public static boolean isLakeComplete(List<Tile> lake, Tile current) {

		// do nothing if there is no current tile
		if (current == null) {
			return false;
		}

		// record from where we came
		Tile previous = lake.get(lake.size() - 1);

		// add this current tile to the lake
		lake.add(current);

		// if there is no lake in the middle, then the lake cannot continiue and
		// is complete
		if (!current.isLakeCenter()) {
			return true;
		}

		// since there is a lake in the center, we should be able to advance the
		// lake

		// if this current tile was previously visited, then the lake has
		// looped and its complete
		if (current.isVisited()) {
			return true;
		}

		// mark current tile as being visited
		current.setVisited(true);

		// get the neighbors for this current tile
		List<Tile> neighbors = current.getBoard().getNeighbors(current.getRow(), current.getCol());

		boolean completed;

		// Iterate through the neighbors that allow the lake to continue; i.e.,
		// find those neighboring tiles that share a lake edge
		for (Tile neighbor : neighbors) {

			// can't go back from where we came
			if (neighbor == previous) {
				continue;
			}

			// Check if neighbor is in same row
			if (neighbor.getRow() == current.getRow()) {
				if (neighbor.getCol() > current.getCol()) {
					// This is right neighbor - is there a lake
					// leading to that neighbor?
					if (current.getRightEdge() == TerrainType.LAKE) {
						// yes, so follow that lake
						completed = isLakeComplete(lake, neighbor);
						// return true if the lake completed
						if (completed == false) {
							return false;
						}
						// move on to next neighbor
					}
				} else {
					// This is left neighbor- is there a trail
					// leading to that neighbor?
					if (current.getLeftEdge() == TerrainType.LAKE) {
						completed = isLakeComplete(lake, neighbor);
						if (completed == false) {
							return false;
						}
					}
				}
			}

			// If not in the same row, it must be in the same column
			if (neighbor.getCol() == current.getCol()) {
				if (neighbor.getRow() > current.getRow()) {
					// This is bottom neighbor - is there a lake
					// leading to that neighbor
					if (current.getBottomEdge() == TerrainType.LAKE) {
						completed = isLakeComplete(lake, neighbor);
						if (completed == false) {
							return false;
						}
					}

				} else {
					if (current.getTopEdge() == TerrainType.LAKE) {
						// This is top neighbor - is there a lake
						// leading to that neighbor
						completed = isLakeComplete(lake, neighbor);
						if (completed == false) {
							return false;
						}
					}
				}
			}
		}

		// the lake never completed.
		return true;
	}

	/**
	 * Add a list to a list of lists, but only if it does not already exist in
	 * the list of lists
	 * 
	 * @param lists
	 * @param list
	 */
	public static void addDistinctList(List<List<Tile>> lists, List<Tile> list) {

		// System.out.println("addDistinctList: lists size = " + lists.size());
		// System.out.println("addDistinctList: list size = " + list.size());

		// Don't want to add empty lists
		if (list.size() == 1) {
			return;
		}

		// If there are no existing lists, then add the list
		if (lists.isEmpty()) {
			lists.add(list);
			return;
		}

		// Create a set from the passed in list
		Set<Tile> inSet = new HashSet<Tile>(list);

		// Go through each list in the list of lists
		for (List<Tile> lList : lists) {
			// If the two lists are of different size, we know they
			// are two different lists, continue
			if (lList.size() != list.size()) {
				continue;
			}

			// Else they're the same size and could possibly be the
			// same list (loop)
			// Create a new set from the lList elements
			Set<Tile> tSet = new HashSet<Tile>(lList);
			// If the elements in both lists are the same, they are the
			// same list, so return
			if (tSet.equals(inSet)) {
				return;
			}
		}

		// They are all different lists, so it's safe to add it to the list
		lists.add(list);

	}

	/**
	 * Returns true if the Tile contains a lake
	 * 
	 * @return
	 */
	public boolean isLake() {
		if (this.isLakeCenter()) {
			return true;
		}

		if (this.getLeftEdge() == TerrainType.LAKE || this.getRightEdge() == TerrainType.LAKE
				|| this.getTopEdge() == TerrainType.LAKE || this.getBottomEdge() == TerrainType.LAKE) {
			return true;
		}

		return false;
	}

	/**
	 * This method finds all neigboring Dens and collects their scores
	 * 
	 * @return
	 */
	public int collectDenScores() {
		int score = 0;

		if (this.getBoard() == null) {
			return score;
		}

		// System.out.println("collectDenScores: isDen returns " +
		// this.isDen());

		// get my score if I'm a Den
		if (this.isDen()) {
			score += this.getDenScore();
		}

		// Look for neighbors that are Dens and get their score
		List<Tile> allDenNeighbors = getBoard().getDenNeighbors(getRow(), getCol());

		// System.out.println("collectDenScores: allDenNeighbors size = " +
		// allDenNeighbors.size());

		// return if there are no Den neighbors
		if (allDenNeighbors.isEmpty()) {
			return score;
		}

		for (Tile n : allDenNeighbors) {
			if (!n.isDen()) {
				continue;
			}
			score += n.getDenScore();
		}

		return score;
	}

	/********************************/
	/******* Tile Constructor *******/
	/********************************/

	// This code I originally had in a constructor

	/*
	 * count number of gametrail edges for this tile // every odd element of the
	 * tilePortionType array // represents the center of an edge for (int i = 0;
	 * i < tilePortionType.length; i++) { if (i % 2 != 0) { if
	 * (tilePortionType[i] == TerrainType.GAMETRAIL) { trailEdgeCount++; } } }
	 * 
	 * // count number of lake edges for this tile // if there is a lake center,
	 * then its all one continuous lake else // they're separate lakes
	 * represents the center of an edge if (isLakeCenter()) { lakeEdgeCount = 1;
	 * } else { for (int i = 0; i < tilePortionType.length; i++) { if (i % 2 !=
	 * 0) { if (tilePortionType[i] == TerrainType.LAKE) { lakeEdgeCount++; } } }
	 * }
	 */

	public Tile(TerrainType[] tilePortionType) {
		this.tilePortionType = tilePortionType;
	}

	public Tile(TerrainType[] tilePortionType, int row, int col, int degrees) {
		this.tilePortionType = tilePortionType;
	}

	public Tile(String terrainTypeString) {
		this.terrainTypeString = terrainTypeString;
		this.tilePortionType = returnTileTerrain(terrainTypeString);
	}

	public TerrainType[] returnTileTerrain(String terrainType) {
		switch (terrainType) {
		case "JJJJ-":
			TerrainType[] tileA = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE };
			return tileA;
		case "JJJJX":
			TerrainType[] tileB = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.DEN, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE };
			return tileB;
		case "JJTJX":
			TerrainType[] tileC = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.DEN, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
					TerrainType.JUNGLE };
			return tileC;
		case "TTTT-":
			TerrainType[] tileD = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.END, TerrainType.GAMETRAIL, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.JUNGLE };
			return tileD;
		case "TJTJ-":
			TerrainType[] tileE = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
					TerrainType.JUNGLE };
			return tileE;
		case "TJJT-":
			TerrainType[] tileF = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.JUNGLE };
			return tileF;
		case "TJTT-":
			TerrainType[] tileG = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.END, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.JUNGLE };
			return tileG;
		case "LLLL-":
			TerrainType[] tileH = { TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE,
					TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE };
			return tileH;
		case "JLLL-":
			TerrainType[] tileI = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.LAKE,
					TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE };
			return tileI;
		case "LLJJ-":
			TerrainType[] tileJ = { TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.LAKE, TerrainType.JUNGLE,
					TerrainType.LAKE, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE };
			return tileJ;
		case "JLJL-":
			TerrainType[] tileK = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.LAKE,
					TerrainType.LAKE, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE };
			return tileK;
		case "LJLJ-":
			TerrainType[] tileL = { TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.JUNGLE };
			return tileL;
		case "LJJJ-":
			TerrainType[] tileM = { TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE };
			return tileM;
		case "JLLJ-":
			TerrainType[] tileN = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.JUNGLE };
			return tileN;
		case "TLJT-":
			TerrainType[] tileO = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.JUNGLE };
			return tileO;
		case "TLJTP":
			TerrainType[] tileP = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE,
					TerrainType.JUNGLE, TerrainType.JUNGLE };
			return tileP;
		case "JLTT-":
			TerrainType[] tileQ = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
					TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
					TerrainType.JUNGLE };
			return tileQ;
		case "JLTTB":
			TerrainType[] tileR = { TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
					TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
					TerrainType.JUNGLE };
			return tileR;
		case "TLTJ-":
			TerrainType[] tileS = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
					TerrainType.JUNGLE };
			return tileS;
		case "TLTJD":
			TerrainType[] tileT = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
					TerrainType.JUNGLE };
			return tileT;
		case "TLLL-":
			TerrainType[] tileU = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.LAKE,
					TerrainType.END, TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE };
			return tileU;
		case "TLTT-":
			TerrainType[] tileV = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.END, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
					TerrainType.JUNGLE };
			return tileV;
		case "TLTTP":
			TerrainType[] tileW = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.END, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
					TerrainType.JUNGLE };
			return tileW;
		case "TLLT-":
			TerrainType[] tileX = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
					TerrainType.LAKE };
			return tileX;
		case "TLLTB":
			TerrainType[] tileY = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.LAKE, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.LAKE,
					TerrainType.LAKE };
			return tileY;
		case "LJTJ-":
			TerrainType[] tileZ = { TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
					TerrainType.JUNGLE };
			return tileZ;
		case "LJTJD":
			TerrainType[] tileZZ = { TerrainType.JUNGLE, TerrainType.LAKE, TerrainType.JUNGLE, TerrainType.JUNGLE,
					TerrainType.END, TerrainType.JUNGLE, TerrainType.JUNGLE, TerrainType.GAMETRAIL,
					TerrainType.JUNGLE };
			return tileZZ;
		case "TLLLC":
			TerrainType[] tileZZZ = { TerrainType.JUNGLE, TerrainType.GAMETRAIL, TerrainType.JUNGLE, TerrainType.LAKE,
					TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE, TerrainType.LAKE };
			return tileZZZ;
		}

		TerrainType[] empty = {};
		return empty;
	}

}
