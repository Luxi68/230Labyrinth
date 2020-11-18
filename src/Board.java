package game;

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
	 */
	public Board (int height, int length) {
		this.board = new Floor[height][length];//do we need -1?
		this.length = length;
	}
	/*
	 * insertFloor inserts a Floor tile to the board and shifts relevant tiles
	 * @param p The profile that needs to get inserted
	 * @param temp, the node we're using to move along the tree
	 * 
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
	
	public void insertFromLeft(Floor insert,int y) {
		//make sure we can insert the tile
		int reachedEnd = 0;
		Floor curFloor = null; // current floor
		int xCurFloor = length; //the x of the current floor
		// insert board[length][y] in silk bag
		board[length][y] = null;
			while (reachedEnd != length) { //again we need to decide if length starts from 0 or 1
				curFloor = board[xCurFloor - 1][y];
				board[xCurFloor][y] = curFloor;
				xCurFloor --;
				reachedEnd ++;
			}
		board [0][y] = insert;
		//we need to add conditions to deal with fixed tile n all
	}
	
	public void insertFromRight(Floor insert, int y) {
		//make sure we can insert the tile
		int reachedEnd = 0;
		Floor curFloor = null; // current floor
		int xCurFloor = 0; //the x of the current floor
		// insert board[length][y] in silk bag
		board[0][y] = null;
			while (reachedEnd != length) { //again we need to decide if length starts from 0 or 1
				curFloor = board[xCurFloor + 1][y];
				board[xCurFloor][y] = curFloor;
				xCurFloor ++;
				reachedEnd ++;
			}
		board [length][y] = insert;
		//we need to add conditions to deal with fixed tile n all
	}
	
	
	
	
	public void insertFromTop(Floor insert, int x) {
		//make sure we can insert the tile
		int reachedEnd = 0;
		Floor curFloor = null; // current floor
		int yCurFloor = height; //the y of the current floor
		// insert board[x][height] in silk bag
		board[x][height] = null;
			while (reachedEnd != height) { //again we need to decide if height starts from 0 or 1
				curFloor = board[x][yCurFloor - 1];
				board[x][yCurFloor] = curFloor;
				yCurFloor --;
				reachedEnd ++;
			}
		board [x][height] = insert;
		//we need to add conditions to deal with fixed tile n all
	}
	
	
	public void insertFromBottom(Floor insert, int x) {
		//make sure we can insert the tile
		int reachedEnd = 0;
		Floor curFloor = null; // current floor
		int yCurFloor = 0; //the y of the current floor
		// insert board[x][height] in silk bag
		board[x][0] = null;
			while (reachedEnd != height) { //again we need to decide if height starts from 0 or 1
				curFloor = board[x][yCurFloor + 1];
				board[x][yCurFloor] = curFloor;
				yCurFloor ++;
				reachedEnd ++;
			}
		board [x][0] = insert;
		//we need to add conditions to deal with fixed tile n all
	}
	
	
}
	
