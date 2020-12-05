package entity;

import java.util.ArrayList;

import sun.security.util.Length;

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
     * @return The height of the board
     */
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * Returns the length of the board
     * @return The length of the board
     */
    public int getLength() {
        return LENGTH;
    }

    /**
     * Returns the tile at a certain coordinate
     * @param row The coordinate of the tile we want to get
     * @param column The coordinate of the tile we want to get
     * @return The tile at coordinate BOARD[row][column]
     */
    public Floor getTileAt(int row, int column) {
        return BOARD[row][column];
    }

    /**
     * Inserts a tile at a certain coordinate
     * @param row The coordinate we want to insert the tile at
     * @param column The coordinate we want to insert the tile at
     */
    public void insertTileAt(int row, int column, Floor insert) {
        BOARD[row][column] = insert;
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


    /**
     * insertFromTop inserts a Floor tile to the board and shifts relevant tiles from the top of the board
     * @param insert The Floor tile that needs to be inserted
     * @param column The coordinate of the column we need to insert the tile to
     * @return The ejected tile that needs to be inserted in the Silkbag
     * @throws IllegalStateException - if the tile cannot be inserted into this column
     */
    public Floor insertFromTop(Floor insert, int column) throws IllegalStateException {
        if (checkIfColumnMovable(column)) {
            Floor ejectedTile = BOARD[HEIGHT - 1][column];
            for (int i = HEIGHT - 1; i > 0; i--) {
                Floor movedFloor = BOARD[i - 1][column];
                BOARD[i][column] = movedFloor;
                movedFloor.updateCoords(i, column);
            }
            BOARD[0][column] = insert;
            insert.updateCoords(0, column);
            return ejectedTile;
        } else {
            throw new IllegalStateException(
                    "ERROR: This column cannot be moved as there are iced tiles in the way.\n");
        }
    }

    /**
     * insertFromRight inserts a Floor tile to the board and shifts relevant tiles from the right of the board
     * @param insert The Floor tile that needs to be inserted
     * @param row The coordinate of the row we need to insert the tile to
     * @return The ejected tile that needs to be inserted in the Silkbag
     * @throws IllegalStateException - if the tile cannot be inserted into this row
     */
    public Floor insertFromRight(Floor insert, int row) throws IllegalStateException {
        if (checkIfRowMovable(row)) {
            Floor ejectedTile = BOARD[row][0];
            for (int i = 0; i < LENGTH - 1; i++) {
                Floor movedFloor = BOARD[row][i + 1];
                BOARD[row][i] = movedFloor;
                movedFloor.updateCoords(row, i);
            }
            BOARD[row][LENGTH - 1] = insert;
            insert.updateCoords(row, LENGTH - 1);
            return ejectedTile;
        } else {
            throw new IllegalStateException(
                    "ERROR: This row cannot be moved as there are iced tiles in the way.\n");
        }
    }

    /**
     * insertFromLeft inserts a Floor tile to the board and shifts relevant tiles from the left of the board
     * @param insert The Floor tile that needs to be inserted
     * @param row The coordinate of the row we need to insert the tile to
     * @return The ejected tile that needs to be inserted in the Silkbag
     * @throws IllegalStateException - if the tile cannot be inserted into this row
     */
    public Floor insertFromLeft(Floor insert, int row) throws IllegalStateException {
        if (checkIfRowMovable(row)) {
            Floor ejectedTile = BOARD[row][LENGTH - 1];
            for (int i = LENGTH - 1; i > 0; i--) {
                Floor movedFloor = BOARD[row][i - 1];
                BOARD[row][i] = movedFloor;
                movedFloor.updateCoords(row, i);
            }
            BOARD[row][0] = insert;
            insert.updateCoords(row, 0);
            return ejectedTile;
        } else {
            throw new IllegalStateException(
                    "ERROR: This row cannot be moved as there are iced tiles in the way.\n");
        }
    }

    /**
     * insertFromBottom inserts a Floor tile to the board and shifts relevant tiles from the bottom of the board
     * @param insert The Floor tile that needs to be inserted
     * @param column The coordinate of the column we need to insert the tile to
     * @return The ejected tile that needs to be inserted in the Silkbag
     * @throws IllegalStateException - if the tile cannot be inserted into the board
     */
    public Floor insertFromBottom(Floor insert, int column) throws IllegalStateException {
        if (checkIfColumnMovable(column)) {
            Floor ejectedTile = BOARD[0][column];
            for (int i = 0 ; i < HEIGHT - 1; i++) {
                Floor movedFloor = BOARD[i + 1][column];
                BOARD[i][column] = movedFloor;
                movedFloor.updateCoords(i, column);
            }
            BOARD[HEIGHT-1][column] = insert;
            insert.updateCoords(HEIGHT - 1, column);
            return ejectedTile;
        } else {
            throw new IllegalStateException(
                    "ERROR: This column cannot be moved as there are iced tiles in the way.\n");
        }
    }

    /**
     * checkIfRowMovable checks whether it is possible or not to insert a tile into this row
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
     * @param inflictedTile - the tile at the centre
     * @return - the inflicted tile and all surrounding tiles
     */
    public ArrayList<Floor> getSurroundingTiles(Floor inflictedTile) {
        ArrayList<Floor> affectedTilesR = new ArrayList<>();
        int row = inflictedTile.getRow();
        int column = inflictedTile.getColumn();

        for (int r = row - 1; r < row + 2;r++){
            for (int c = column - 1; c < column + 2;c++){
                if (c < 0 || r < 0 || c > HEIGHT || r > LENGTH){
                    //outta range
                }else{
                    affectedTilesR.add(getTileAt(r, c));
                }
            }
        }

        /*
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
        */
        return affectedTilesR;
    }
}