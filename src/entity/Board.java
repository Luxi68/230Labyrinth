package entity;//package game;

/**
 * <P>Purpose: Setting out tiles by collaborating with Floor in order to create the game board.
 * It is also responsible for managing the tiles movements</p>
 * <P>File name: Board.java </p>
 *
 * @author Nouran, Junjie and Gordon
 * <P>Created: 16/11/2020
 * <P>Modified: /2020
 */

public class Board {

	private final Floor[][] BOARD;
	private final int LENGTH;
	private final int HEIGHT;

	/**
	 * Constructor used to create a new instance of Board.
	 *
	 * @param HEIGHT The height of the board (i.e. how many tiles it will hold vertically)
	 * @param LENGTH The length of the board (i.e. how many tiles it will hold horizontally)
	 */


	public Board(int HEIGHT, int LENGTH) {
		this.BOARD = new Floor[HEIGHT][LENGTH];
		this.LENGTH = LENGTH;
		this.HEIGHT = HEIGHT;
		//add tiles setup...
		//add players arraylist and their starting points...
	}

	public int getHeight() {
		return HEIGHT;
	}

	public int getLength() {
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

	public Floor getTileAt(int row, int column) {
		return BOARD[row][column];
	}

	public void insertTileAt(int x, int y, Floor insert) {
		BOARD[x][y] = insert;
	}


	/**
	 * InsertFromTop inserts a Floor tile to the board and shifts relevant tiles from the top.
	 * Returns the rejected tile.
	 *
	 * @param insert The Floor tile that needs to be inserted
	 * @param column The x coordinate of the column we need to insert the tile to
	 */
	public Floor insertFromTop(Floor insert, int column) {
		if (checkIfColumnMovable(column)) {
			Floor ejectedTile = BOARD[HEIGHT - 1][column];
			for (int i = HEIGHT - 1; i > 0; i--) {
				Floor tempFloor = BOARD[i - 1][column];
				BOARD[i][column] = tempFloor;
			}
			BOARD[0][column] = insert;
			return ejectedTile;
		} else {
			throw new IllegalStateException(
					"ERROR: This column cannot be moved as there are iced tiles in the way.");
		}
	}

	/**
	 * insertFromRight inserts a Floor tile to the board and shifts relevant tiles from the right side
	 *
	 * @param insert The Floor tile that needs to be inserted
	 * @param y      The y coordinate of the row we need to insert the tile to
	 */
	public void insertFromRight(Floor insert, int y) {
		//make sure we can insert the tile
		int reachedEnd = 0;
		Floor curFloor; // current floor
		int xCurFloor = 0; //the x of the current floor
		// SILKBAG.addTile(BOARD[0][y]); TODO remove silk bag
		BOARD[0][y] = null;
		while (reachedEnd != LENGTH) { //maybe change into for loop?
			curFloor = BOARD[xCurFloor + 1][y];
			BOARD[xCurFloor][y] = curFloor;
			xCurFloor++;
			reachedEnd++;
		}
		BOARD[LENGTH][y] = insert;
		//we need to add conditions to deal with fixed tile n all
	}


	/**
	 * insertFromTop inserts a Floor tile to the board and shifts relevant tiles from the top of the board
	 *
	 * @param insert The Floor tile that needs to be inserted
	 * @param x      The x coordinate of the column we need to insert the tile to
	 */
	public void insertFromLeft(Floor insert, int x) {
		//make sure we can insert the tile
		int reachedEnd = 0;
		Floor curFloor = null; // current floor
		int yCurFloor = HEIGHT; //the y of the current floor
		// SILKBAG.addTile(BOARD[x][HEIGHT]); TODO remove silk bag
		BOARD[x][HEIGHT] = null;
		while (reachedEnd != HEIGHT) { //maybe change into for loop?
			curFloor = BOARD[x][yCurFloor - 1];
			BOARD[x][yCurFloor] = curFloor;
			yCurFloor--;
			reachedEnd++;
		}
		BOARD[x][HEIGHT] = insert;
		//we need to add conditions to deal with fixed tile n all
	}

	/**
	 * insertFromBottom inserts a Floor tile to the board and shifts relevant tiles from the bottom of the board
	 *
	 * @param insert The Floor tile that needs to be inserted
	 * @param x      The x coordinate of the column we need to insert the tile to
	 */
	public void insertFromBottom(Floor insert, int x) { //need to add the exception
		if (checkIfColumnMovable(x)) {
			int reachedEnd = 0;
			Floor curFloor = null; // current floor
			int yCurFloor = 0; //the y of the current floor
			// SILKBAG.addTile(BOARD[x][0]); TODO remove silk bag
			BOARD[x][0] = null;
			while (reachedEnd != HEIGHT) { //maybe change into for loop?
				curFloor = BOARD[x][yCurFloor + 1];
				BOARD[x][yCurFloor] = curFloor;
				yCurFloor++;
				reachedEnd++;
			}
			BOARD[x][0] = insert;
		} else {
		}
		// TODO - we need to add conditions to deal with fixed tile n all
	}

	/**
	 * checkIfRowMovable checks whether it is possible or not to insert a tile into this row
	 *
	 * @param row - the row to be checked
	 * @return
	 */
	private boolean checkIfRowMovable(int row) {
		for (int i = 0; i <= HEIGHT - 1; i++) {
			Floor curTile = BOARD[row][i];
			if (curTile.getIsIce() || curTile.getIsFixed()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param column
	 * @return
	 */
	private boolean checkIfColumnMovable(int column) {
		for (int i = 0; i <= LENGTH - 1; i++) {
			Floor curTile = BOARD[i][column];
			if (curTile.getIsIce()) {
				return false;
			}
		}
		return true;
	}
	/*
	public void useActionTiles(int x, int y) {
		useFireTile(board[x][y]),
	}
	*/

	/**
	 * Returns a list of the floor tiles surrounding a specific floor tile on a board
	 *
	 * @param inflictedTile - the tile at the centre
	 * @return - the inflicted tile and all surrounding tiles
	 * @throws IndexOutOfBoundsException - if the tile is outside the bounds of the board
	 */
	public Floor[] getSurroundingTiles(Floor inflictedTile) throws IndexOutOfBoundsException {
		Floor[] affectedTiles = null;
		String errorMsg = "Inflicted tile is not on the board. Please choose another floor tile\n";

		if (inflictedTile.getRow() == 0) {
			if (inflictedTile.getColumn() == 0) {
				// Top Left
				affectedTiles = new Floor[4];
				affectedTiles[0] = BOARD[0][0];
				affectedTiles[1] = BOARD[1][0];
				affectedTiles[2] = BOARD[0][1];
				affectedTiles[3] = BOARD[1][1];

			} else if (inflictedTile.getColumn() == LENGTH - 1) {
				// Top Right
				affectedTiles = new Floor[4];
				affectedTiles[0] = BOARD[0][LENGTH - 1];
				affectedTiles[1] = BOARD[0][LENGTH - 2];
				affectedTiles[2] = BOARD[1][LENGTH - 1];
				affectedTiles[3] = BOARD[1][LENGTH - 2];

			} else if (inflictedTile.getColumn() > 0 && inflictedTile.getColumn() < LENGTH - 1) {
				// Top Row
				affectedTiles = new Floor[6];
				affectedTiles[0] = BOARD[0][inflictedTile.getColumn()];
				affectedTiles[1] = BOARD[0][inflictedTile.getColumn() + 1];
				affectedTiles[2] = BOARD[0][inflictedTile.getColumn() - 1];
				affectedTiles[3] = BOARD[1][inflictedTile.getColumn()];
				affectedTiles[4] = BOARD[1][inflictedTile.getColumn() + 1];
				affectedTiles[5] = BOARD[1][inflictedTile.getColumn() - 1];
			} else {
				throw new IndexOutOfBoundsException(errorMsg);
			}
		} else if (inflictedTile.getRow() == HEIGHT - 1) {
			if (inflictedTile.getColumn() == 0) {
				// Bottom Left
				affectedTiles = new Floor[4];
				affectedTiles[0] = BOARD[HEIGHT - 1][0];
				affectedTiles[1] = BOARD[HEIGHT - 2][0];
				affectedTiles[2] = BOARD[HEIGHT - 1][1];
				affectedTiles[3] = BOARD[HEIGHT - 2][1];

			} else if (inflictedTile.getColumn() == LENGTH - 1) {
				// Bottom Right
				affectedTiles = new Floor[4];
				affectedTiles[0] = BOARD[HEIGHT - 1][LENGTH - 1];
				affectedTiles[1] = BOARD[HEIGHT - 2][LENGTH - 1];
				affectedTiles[2] = BOARD[HEIGHT - 1][LENGTH - 2];
				affectedTiles[3] = BOARD[HEIGHT - 2][LENGTH - 2];

			} else if (inflictedTile.getColumn() > 0 && inflictedTile.getColumn() < LENGTH - 1) {
				// Bottom Row
				affectedTiles = new Floor[6];
				affectedTiles[0] = BOARD[HEIGHT - 1][inflictedTile.getColumn()];
				affectedTiles[1] = BOARD[HEIGHT - 1][inflictedTile.getColumn() + 1];
				affectedTiles[2] = BOARD[HEIGHT - 1][inflictedTile.getColumn() - 1];
				affectedTiles[3] = BOARD[HEIGHT - 2][inflictedTile.getColumn()];
				affectedTiles[4] = BOARD[HEIGHT - 2][inflictedTile.getColumn() + 1];
				affectedTiles[5] = BOARD[HEIGHT - 2][inflictedTile.getColumn() - 1];
			} else {
				throw new IndexOutOfBoundsException(errorMsg);
			}
		} else if (inflictedTile.getRow() > 0 && inflictedTile.getRow() < HEIGHT - 1) {
			if (inflictedTile.getColumn() == 0) {
				// Left Column
				affectedTiles = new Floor[6];
				affectedTiles[0] = BOARD[inflictedTile.getRow()][0];
				affectedTiles[1] = BOARD[inflictedTile.getRow() + 1][0];
				affectedTiles[2] = BOARD[inflictedTile.getRow() - 1][0];
				affectedTiles[3] = BOARD[inflictedTile.getRow()][1];
				affectedTiles[3] = BOARD[inflictedTile.getRow() + 1][1];
				affectedTiles[3] = BOARD[inflictedTile.getRow() - 1][1];

			} else if (inflictedTile.getColumn() == LENGTH - 1) {
				// Right Column
				affectedTiles = new Floor[6];
				affectedTiles[0] = BOARD[inflictedTile.getRow()][LENGTH - 1];
				affectedTiles[1] = BOARD[inflictedTile.getRow() + 1][LENGTH - 1];
				affectedTiles[2] = BOARD[inflictedTile.getRow() - 1][LENGTH - 1];
				affectedTiles[3] = BOARD[inflictedTile.getRow()][LENGTH - 2];
				affectedTiles[3] = BOARD[inflictedTile.getRow() + 1][LENGTH - 2];
				affectedTiles[3] = BOARD[inflictedTile.getRow() - 1][LENGTH - 2];

			} else if (inflictedTile.getColumn() > 0 && inflictedTile.getColumn() < LENGTH - 1) {
				// Centre (all 3x3 square is affected)
				affectedTiles = new Floor[9];
				affectedTiles[0] = BOARD[inflictedTile.getRow()][inflictedTile.getColumn()];
				affectedTiles[1] = BOARD[inflictedTile.getRow()][inflictedTile.getColumn() + 1];
				affectedTiles[2] = BOARD[inflictedTile.getRow()][inflictedTile.getColumn() - 1];
				affectedTiles[3] = BOARD[inflictedTile.getRow() + 1][inflictedTile.getColumn()];
				affectedTiles[4] = BOARD[inflictedTile.getRow() + 1][inflictedTile.getColumn() + 1];
				affectedTiles[5] = BOARD[inflictedTile.getRow() + 1][inflictedTile.getColumn() - 1];
				affectedTiles[5] = BOARD[inflictedTile.getRow() - 1][inflictedTile.getColumn()];
				affectedTiles[5] = BOARD[inflictedTile.getRow() - 1][inflictedTile.getColumn() + 1];
				affectedTiles[5] = BOARD[inflictedTile.getRow() - 1][inflictedTile.getColumn() - 1];
			} else {
				throw new IndexOutOfBoundsException(errorMsg);
			}
		} else {
			throw new IndexOutOfBoundsException(errorMsg);
		}
		return affectedTiles;
	}
}