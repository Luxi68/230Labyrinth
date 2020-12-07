package entity;

import java.util.ArrayList;

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
	private final int LENGTH; //number of floor tiles in the board vertically
	private final int HEIGHT; //number of floor tiles horizontally

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

	/**
	 * Returns the height of the board
	 *
	 * @return The height of the board
	 */
	public int getHeight() {
		return HEIGHT;
	}

	/**
	 * Returns the length of the board
	 *
	 * @return The length of the board
	 */
	public int getLength() {
		return LENGTH;
	}

	/**
	 * Returns the tile at a certain coordinate
	 *
	 * @param row    The coordinate of the tile we want to get
	 * @param column The coordinate of the tile we want to get
	 * @return The tile at coordinate BOARD[row][column]
	 */
	public Floor getTileAt(int row, int column) {
		return BOARD[row][column];
	}

	/**
	 * Inserts a tile at a certain coordinate
	 *
	 * @param row    The coordinate we want to insert the tile at
	 * @param column The coordinate we want to insert the tile at
	 */
	public void insertTileAt(int row, int column, Floor insert) {
		BOARD[row][column] = insert;
		insert.updateCoords(row, column);
		insert.setIsOnBoard(true);
	}

	/**
	 * insertFromTop inserts a Floor tile to the board and shifts relevant tiles from the top of the board
	 *
	 * @param insert The Floor tile that needs to be inserted
	 * @param column The coordinate of the column we need to insert the tile to
	 * @return The ejected tile that needs to be inserted in the Silkbag
	 * @throws IllegalStateException - if the tile cannot be inserted into this column
	 */
	public Floor insertFromTop(Floor insert, int column) throws IllegalStateException {
		if (checkIfColumnMovable(column)) {
			Floor ejectedTile = BOARD[HEIGHT - 1][column];
			ejectedTile.setIsOnBoard(false);
			for (int i = HEIGHT - 1; i > 0; i--) {
				Floor movedFloor = BOARD[i - 1][column];
				BOARD[i][column] = movedFloor;
				movedFloor.updateCoords(i, column);
			}
			BOARD[0][column] = insert;
			insert.updateCoords(0, column);
			insert.setIsOnBoard(true);
			return ejectedTile;
		} else {
			throw new IllegalStateException(
					"WARNING: These islands cannot be moved as they have been ice over.\n");
		}
	}

	/**
	 * insertFromRight inserts a Floor tile to the board and shifts relevant tiles from the right of the board
	 *
	 * @param insert The Floor tile that needs to be inserted
	 * @param row    The coordinate of the row we need to insert the tile to
	 * @return The ejected tile that needs to be inserted in the Silkbag
	 * @throws IllegalStateException - if the tile cannot be inserted into this row
	 */
	public Floor insertFromRight(Floor insert, int row) throws IllegalStateException {
		if (checkIfRowMovable(row)) {
			Floor ejectedTile = BOARD[row][0];
			ejectedTile.setIsOnBoard(false);
			for (int i = 0; i < LENGTH - 1; i++) {
				Floor movedFloor = BOARD[row][i + 1];
				BOARD[row][i] = movedFloor;
				movedFloor.updateCoords(row, i);
			}
			BOARD[row][LENGTH - 1] = insert;
			insert.updateCoords(row, LENGTH - 1);
			insert.setIsOnBoard(true);
			return ejectedTile;
		} else {
			throw new IllegalStateException(
					"WARNING: These islands cannot be moved as they have been ice over.\n");
		}
	}

	/**
	 * insertFromLeft inserts a Floor tile to the board and shifts relevant tiles from the left of the board
	 *
	 * @param insert The Floor tile that needs to be inserted
	 * @param row    The coordinate of the row we need to insert the tile to
	 * @return The ejected tile that needs to be inserted in the Silkbag
	 * @throws IllegalStateException - if the tile cannot be inserted into this row
	 */
	public Floor insertFromLeft(Floor insert, int row) throws IllegalStateException {
		if (checkIfRowMovable(row)) {
			Floor ejectedTile = BOARD[row][LENGTH - 1];
			ejectedTile.setIsOnBoard(false);
			for (int i = LENGTH - 1; i > 0; i--) {
				Floor movedFloor = BOARD[row][i - 1];
				BOARD[row][i] = movedFloor;
				movedFloor.updateCoords(row, i);
			}
			BOARD[row][0] = insert;
			insert.updateCoords(row, 0);
			insert.setIsOnBoard(true);
			return ejectedTile;
		} else {
			throw new IllegalStateException(
					"WARNING: These islands cannot be moved as they have been ice over.\n");
		}
	}

	/**
	 * insertFromBottom inserts a Floor tile to the board and shifts relevant tiles from the bottom of the board
	 *
	 * @param insert The Floor tile that needs to be inserted
	 * @param column The coordinate of the column we need to insert the tile to
	 * @return The ejected tile that needs to be inserted in the Silkbag
	 * @throws IllegalStateException - if the tile cannot be inserted into the board
	 */
	public Floor insertFromBottom(Floor insert, int column) throws IllegalStateException {
		if (checkIfColumnMovable(column)) {
			Floor ejectedTile = BOARD[0][column];
			ejectedTile.setIsOnBoard(false);
			for (int i = 0; i < HEIGHT - 1; i++) {
				Floor movedFloor = BOARD[i + 1][column];
				BOARD[i][column] = movedFloor;
				movedFloor.updateCoords(i, column);
			}
			BOARD[HEIGHT - 1][column] = insert;
			insert.updateCoords(HEIGHT - 1, column);
			insert.setIsOnBoard(true);
			return ejectedTile;
		} else {
			throw new IllegalStateException(
					"WARNING: These islands cannot be moved as they have been ice over.\n");
		}
	}

	/**
	 * checkIfRowMovable checks whether it is possible or not to insert a tile into this row
	 *
	 * @param row - the row to be checked
	 * @return true if we can insert a tile into the row, false otherwise
	 */
	private boolean checkIfRowMovable(int row) {
		for (int i = 0; i <= LENGTH - 1; i++) {
			Floor curTile = BOARD[row][i];
			if (curTile.getIsIce()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * checkIfColumnMovable checks whether it is possible or not to insert a tile into this column
	 *
	 * @param column The coordinate of column to be checked
	 * @return true if we can insert a tile into the column, false otherwise
	 */
	private boolean checkIfColumnMovable(int column) {
		for (int i = 0; i <= HEIGHT - 1; i++) {
			Floor curTile = BOARD[i][column];
			if (curTile.getIsIce()) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Returns a list of the floor tiles surrounding a specific floor tile on a board
	 *
	 * @param inflictedTile - the tile at the centre
	 * @return - the inflicted tile and all surrounding tiles
	 */
	public ArrayList<Floor> getSurroundingTiles(Floor inflictedTile) {
		ArrayList<Floor> affectedTilesR = new ArrayList<>();
		int row = inflictedTile.getRow();
		int column = inflictedTile.getColumn();

		for (int r = row - 1; r < row + 2; r++) {
			for (int c = column - 1; c < column + 2; c++) {
				if (c < 0 || r < 0 || c > HEIGHT - 1 || r > LENGTH - 1) {
					//outta range
				} else {
					affectedTilesR.add(getTileAt(r, c));
				}
			}
		}
		return affectedTilesR;
	}
}