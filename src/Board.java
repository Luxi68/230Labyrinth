//package game;

/**
* <P>Purpose: Setting out tiles by collaborating with Floor in order to create the game board. 
* It is also responsible for managing the tiles movements</p>
* <P>File name: Board.java </p> 
* @author Nouran and Gordon
* <P>Created: 16/11/2020
* <P>Modified: /2020
*/

public class Board{
	
	private Floor board[][];
	private int length;
	private int height;
	
	/**
	 * Constructor used to create a new instance of Board.
	 * @param height The height of the board (i.e. how many tiles it will hold vertically)
	 * @param length The length of the board (i.e. how many tiles it will hold horizontally)
	 */
	public Board (int height, int length) {
		this.board = new Floor[height-1][length-1];//do we need -1?
		this.length = length;
		//add tiles setup...
		//add players arraylist and their starting points...
	}
	/*
	 * insertFloor inserts a Floor tile to the board and shifts relevant tiles
	 * @param insert The Floor tile that needs to be inserted
	 * @param The y coordinate of the row we need to insert the tile into
	 
	public void insertFloor(Floor insert, int x, int y, button?) {
		//change it to switch statement!!
		if (button says insert from left) {
			insertFromLeft(insert, x, y);
		} else if (button says insert from right) {
			insertFromRight(insert, x, y);
		} else if (button says insert from top) {
			insertFromTop(insert, x, y);
		} else if (button says insert from botoom) {
			insertFromBottom(insert, x, y);
		}
			
	}*/
	
	public Floor getTileAt(int x, int y) {
		return board[x][y];
	}
	
	public void insertTileAt(int x, int y, Floor insert) {
		board[x][y] = insert;
	}
	
	
	/**
	 * insertFromLeft inserts a Floor tile to the board and shifts relevant tiles from the left side
	 * @param insert The Floor tile that needs to be inserted
	 * @param y The y coordinate of the row we need to insert the tile to
	 */
	public void insertFromLeft(Floor insert,int y) {
		//make sure we can insert the tile
		int reachedEnd = 0;
		Floor curFloor = null; // current floor
		int xCurFloor = length; //the x of the current floor
		// insert board[length][y] in silk bag.. need to access silkbag somehow
		board[length][y] = null;
			while (reachedEnd != length) { //maybe change into for loop?
				curFloor = board[xCurFloor - 1][y];
				board[xCurFloor][y] = curFloor;
				xCurFloor --;
				reachedEnd ++;
			}
		board [0][y] = insert;
		//we need to add conditions to deal with fixed tile n all
	}
	
	/**
	 * insertFromRight inserts a Floor tile to the board and shifts relevant tiles from the right side
	 * @param insert The Floor tile that needs to be inserted
	 * @param y The y coordinate of the row we need to insert the tile to
	 */
	public void insertFromRight(Floor insert, int y) {
		//make sure we can insert the tile
		int reachedEnd = 0;
		Floor curFloor = null; // current floor
		int xCurFloor = 0; //the x of the current floor
		// insert board[length][y] in silk bag
		board[0][y] = null;
			while (reachedEnd != length) { //maybe change into for loop?
				curFloor = board[xCurFloor + 1][y];
				board[xCurFloor][y] = curFloor;
				xCurFloor ++;
				reachedEnd ++;
			}
		board [length][y] = insert;
		//we need to add conditions to deal with fixed tile n all
	}
	
	
	/**
	 * insertFromTop inserts a Floor tile to the board and shifts relevant tiles from the top of the board
	 * @param insert The Floor tile that needs to be inserted
	 * @param x The x coordinate of the column we need to insert the tile to
	 */
	public void insertFromTop(Floor insert, int x) {
		//make sure we can insert the tile
		int reachedEnd = 0;
		Floor curFloor = null; // current floor
		int yCurFloor = height; //the y of the current floor
		// insert board[x][height] in silk bag
		board[x][height] = null;
			while (reachedEnd != height) { //maybe change into for loop?
				curFloor = board[x][yCurFloor - 1];
				board[x][yCurFloor] = curFloor;
				yCurFloor --;
				reachedEnd ++;
			}
		board [x][height] = insert;
		//we need to add conditions to deal with fixed tile n all
	}
	
	/**
	 * insertFromBottom inserts a Floor tile to the board and shifts relevant tiles from the bottom of the board
	 * @param insert The Floor tile that needs to be inserted
	 * @param x The x coordinate of the column we need to insert the tile to
	 */
	public void insertFromBottom(Floor insert, int x) { //need to add the exception
		if (checkIfColumnMovable(x, insert)) { 
			int reachedEnd = 0;
			Floor curFloor = null; // current floor
			int yCurFloor = 0; //the y of the current floor
			// insert board[x][height] in silk bag
			board[x][0] = null;
				while (reachedEnd != height) { //maybe change into for loop?
					curFloor = board[x][yCurFloor + 1];
					board[x][yCurFloor] = curFloor;
					yCurFloor ++;
					reachedEnd ++;
				}
				board [x][0] = insert;
		} else {
		}
		//we need to add conditions to deal with fixed tile n all
	}
	
	/**
	 * checkIfRowMovable checks whether it is possible or not to insert a tile into this row
	 * @param y
	 * @param curTile - The Floor tile that needs to be inserted
	 */
	public boolean checkIfRowMovable(int y, Floor curTile) {//x starts at 0
		for (int i=0; i<= length-1; i++) {
			if ( i <= length-1) {
				if (curTile.getIsIce() == true || curTile.getIsFixed() == true) {
					return false;
				} else {
					curTile = board[i][y];
				}
			}
		}
		return true;
	}
	
	public boolean checkIfColumnMovable(int x, Floor curTile) {//x starts at 0
		for (int i=0; i<= height-1; i++) {
			if ( i <= height-1) {
				if (curTile.getIsIce() == true || curTile.getIsFixed() == true) {
					return false;
				} else {
					curTile = board[x][i];
				}
			}
		}
		return true;
	}
	/*
	public void useActionTiles(int x, int y) {
		//useFireTile(board[x][y]),
	}
	*/
	
	public Floor[] getSurroundingTiles(Floor inflictedTile) {
		Floor affectedTiles[];
		
		if (inflictedTile.equals(board[0][0])) {
			affectedTiles = new Floor[4];
			affectedTiles[0] = board[0][0];
			affectedTiles[1] = board[1][0];
			affectedTiles[2] = board[0][1];
			affectedTiles[3] = board[1][1];
			
		} else if (inflictedTile.equals(board[length -1][0])) {
			affectedTiles = new Floor[4];
			affectedTiles[0] = board[length-1][0];
			affectedTiles[1] = board[length-2][0];
			affectedTiles[2] = board[length-1][1];
			affectedTiles[3] = board[length-2][1];
			
		} else if (inflictedTile.equals(board[0][height -1])) {
			affectedTiles = new Floor[4];
			affectedTiles[0] = board[0][height-1];
			affectedTiles[1] = board[0][height-2];
			affectedTiles[2] = board[1][height-1];
			affectedTiles[3] = board[1][height-2];
			
		} else if (inflictedTile.equals(board[length -1][height -1])) {
			affectedTiles = new Floor[4];
			affectedTiles[0] = board[length-1][height-1];
			affectedTiles[1] = board[length-1][height-2];
			affectedTiles[2] = board[length-2][height-1];
			affectedTiles[3] = board[length-2][height-2];
			
		// still need to make conditions for top/bottom/side rows
		} else { // all 3x3 square is affected
			affectedTiles = new Floor[9];
			
		}
		return affectedTiles;
	}		
}
	
	

	
