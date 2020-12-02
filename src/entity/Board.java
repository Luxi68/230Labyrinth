package entity;//package game;

/**
 * <P>Purpose: Setting out tiles by collaborating with Floor in order to create the game board.
 * It is also responsible for managing the tiles movements</p>
 * <P>File name: Board.java </p>
 * @author Nouran and Gordon
 * <P>Created: 16/11/2020
 * <P>Modified: /2020
 */

public class Board{

	private final Floor[][] BOARD;
	private final int LENGTH;
	private final int HEIGHT;
	private final SilkBag SILKBAG;

	/**
	 * Constructor used to create a new instance of Board.
	 * @param HEIGHT The height of the board (i.e. how many tiles it will hold vertically)
	 * @param LENGTH The length of the board (i.e. how many tiles it will hold horizontally)
	 */
	public Board (int HEIGHT, int LENGTH, SilkBag silkBag) {
		this.BOARD = new Floor[HEIGHT -1][LENGTH -1];//do we need -1?
		this.LENGTH = LENGTH;
		this.HEIGHT = HEIGHT;
		this.SILKBAG = silkBag;
		//add tiles setup...
		//add players arraylist and their starting points...
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public int getLENGTH() {
		return LENGTH;
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
		} else if (button says insert from botom) {
			insertFromBottom(insert, x, y);
		}
			
	}*/

	public Floor getTileAt(int x, int y) {
		return BOARD[x][y];
	}

	public void insertTileAt(int x, int y, Floor insert) {
		BOARD[x][y] = insert;
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
		int xCurFloor = LENGTH; //the x of the current floor
		SILKBAG.addTile(BOARD[LENGTH][y]);
		BOARD[LENGTH][y] = null;
		while (reachedEnd != LENGTH) { //maybe change into for loop?
			curFloor = BOARD[xCurFloor - 1][y];
			BOARD[xCurFloor][y] = curFloor;
			xCurFloor --;
			reachedEnd ++;
		}
		BOARD[0][y] = insert;
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
		SILKBAG.addTile(BOARD[0][y]);
		BOARD[0][y] = null;
		while (reachedEnd != LENGTH) { //maybe change into for loop?
			curFloor = BOARD[xCurFloor + 1][y];
			BOARD[xCurFloor][y] = curFloor;
			xCurFloor ++;
			reachedEnd ++;
		}
		BOARD[LENGTH][y] = insert;
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
		int yCurFloor = HEIGHT; //the y of the current floor
		SILKBAG.addTile(BOARD[x][HEIGHT]);
		BOARD[x][HEIGHT] = null;
		while (reachedEnd != HEIGHT) { //maybe change into for loop?
			curFloor = BOARD[x][yCurFloor - 1];
			BOARD[x][yCurFloor] = curFloor;
			yCurFloor --;
			reachedEnd ++;
		}
		BOARD[x][HEIGHT] = insert;
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
			SILKBAG.addTile(BOARD[x][0]);
			BOARD[x][0] = null;
			while (reachedEnd != HEIGHT) { //maybe change into for loop?
				curFloor = BOARD[x][yCurFloor + 1];
				BOARD[x][yCurFloor] = curFloor;
				yCurFloor ++;
				reachedEnd ++;
			}
			BOARD[x][0] = insert;
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
		for (int i = 0; i<= LENGTH -1; i++) {
			if ( i <= LENGTH -1) {
				if (curTile.getIsIce() == true || curTile.getIsFixed() == true) {
					return false;
				} else {
					curTile = BOARD[i][y];
				}
			}
		}
		return true;
	}

	public boolean checkIfColumnMovable(int x, Floor curTile) {//x starts at 0
		for (int i = 0; i<= HEIGHT -1; i++) {
			if ( i <= HEIGHT -1) {
				if (curTile.getIsIce() == true || curTile.getIsFixed() == true) {
					return false;
				} else {
					curTile = BOARD[x][i];
				}
			}
		}
		return true;
	}
	/*
	public void useActionTiles(int x, int y) {
		useFireTile(board[x][y]),
	}
	*/

	public Floor[] getSurroundingTiles(Floor inflictedTile) {
		Floor affectedTiles[] = null;

		if (inflictedTile.getX() == 0) {
			if (inflictedTile.getY() == 0) {
				affectedTiles = new Floor[4];
				affectedTiles[0] = BOARD[0][0];
				affectedTiles[1] = BOARD[1][0];
				affectedTiles[2] = BOARD[0][1];
				affectedTiles[3] = BOARD[1][1];
			} else if (inflictedTile.getY() == HEIGHT -1) {
				affectedTiles = new Floor[4];
				affectedTiles[0] = BOARD[0][HEIGHT -1];
				affectedTiles[1] = BOARD[0][HEIGHT -2];
				affectedTiles[2] = BOARD[1][HEIGHT -1];
				affectedTiles[3] = BOARD[1][HEIGHT -2];
			} else if  (inflictedTile.getY() > 0 && inflictedTile.getY() < HEIGHT - 1) {
				affectedTiles = new Floor[6];
				affectedTiles[0] = BOARD[inflictedTile.getX()][inflictedTile.getY()];
				affectedTiles[1] = BOARD[inflictedTile.getX()][inflictedTile.getY() + 1];
				affectedTiles[2] = BOARD[inflictedTile.getX() + 1][inflictedTile.getY()];
				affectedTiles[3] = BOARD[inflictedTile.getX()][inflictedTile.getY() + 1];
				affectedTiles[4] = BOARD[inflictedTile.getX() + 1][inflictedTile.getY() - 1];
				affectedTiles[5] = BOARD[inflictedTile.getX()][inflictedTile.getY() - 1];
			}
		} else if (inflictedTile.getX() == LENGTH -1) {
			if (inflictedTile.getY() == 0) {
				affectedTiles = new Floor[4];
				affectedTiles[0] = BOARD[LENGTH - 1][0];
				affectedTiles[1] = BOARD[LENGTH - 2][0];
				affectedTiles[2] = BOARD[LENGTH - 1][1];
				affectedTiles[3] = BOARD[LENGTH - 2][1];
			} else if (inflictedTile.getY() == HEIGHT - 1) {
				
			}



		} else if (inflictedTile.equals(BOARD[LENGTH -1][HEIGHT -1])) {
			affectedTiles = new Floor[4];
			affectedTiles[0] = BOARD[LENGTH -1][HEIGHT -1];
			affectedTiles[1] = BOARD[LENGTH -1][HEIGHT -2];
			affectedTiles[2] = BOARD[LENGTH -2][HEIGHT -1];
			affectedTiles[3] = BOARD[LENGTH -2][HEIGHT -2];

			// still need to make conditions for top/bottom/side rows
		} else { // all 3x3 square is affected
			affectedTiles = new Floor[9];

		}
		return affectedTiles;
	}
}