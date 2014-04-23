import java.util.*;

public class CheckersState {

	int[] board = new int[36];
	boolean myColorIsBlack;
	final int INVALID = 100;
	
	public CheckersState(boolean color) {
		
		myColorIsBlack = color;
		for (int i = 0; i < board.length; i++) {
			
			if (i == 0 || i == 9 || i == 18 || i == 27) {
				board[i] = INVALID;
			}
			else if (i <= 13) {
				//1 for white piece
				board[i] = 1;
			}
			else if (i >= 23) {
				//-1 for black piece
				board[i] = -1;
			}
			else {
				//0 for unoccupied
				board[i] = 0;
			}
		}
	}
	
	public List<String> actions() {
		
		LinkedList<String> possibleMoves = new LinkedList<String>();
		
		for (int i = 0; i < board.length; i++) {
			
			if (board[i] == -1) { //black piece
				
				
			}
			else if (board[i] == 1) { //white piece
				
			}
			else if (board[i] == -2) { //black king
				
			}
			else if (board[i] == 2) { //white king
				
			}
			
		}
		
		return possibleMoves;
	}
	
	public CheckersState result(String move) {
		
		String[] splitmessage = move.split("[:()]");
		
		for (int i = 0; i < splitmessage.length; i++) {
			//System.out.print(i + splitmessage[i] + " ");
		}
		//System.out.println();
		
		int x1 = Integer.valueOf(splitmessage[3]);
		int y1 = Integer.valueOf(splitmessage[4]);
		int x2 = Integer.valueOf(splitmessage[7]);
		int y2 = Integer.valueOf(splitmessage[8]);
		int index1 = getBoardIndex(x1, y1);
		int index2 = getBoardIndex(x2, y2);
		
		int pieceValue = board[index1];
		board[index1] = 0;
		board[index2] = pieceValue;
		
		return this;
	}
	
	public int getBoardIndex(int row, int col) {
		
		if (row == 0) {
			if (col == 1) return 1;
			else if (col == 3) return 2;
			else if (col == 5) return 3;
			else if (col == 7) return 4;
		}
		else if (row == 1) {
			if (col == 0) return 5;
			else if (col == 2) return 6;
			else if (col == 4) return 7;
			else if (col == 6) return 8; 
		}
		else if (row == 2) {
			if (col == 1) return 10;
			else if (col == 3) return 11;
			else if (col == 5) return 12;
			else if (col == 7) return 13; 
		}
		else if (row == 3) {
			if (col == 0) return 14;
			else if (col == 2) return 15;
			else if (col == 4) return 16;
			else if (col == 6) return 17; 
		}
		else if (row == 4) {
			if (col == 1) return 19;
			else if (col == 3) return 20;
			else if (col == 5) return 21;
			else if (col == 7) return 22; 
		}
		else if (row == 5) {
			if (col == 0) return 23;
			else if (col == 2) return 24;
			else if (col == 4) return 25;
			else if (col == 6) return 26; 
		}
		else if (row == 6) {
			if (col == 1) return 28;
			else if (col == 3) return 29;
			else if (col == 5) return 30;
			else if (col == 7) return 31; 
		}
		else if (row == 7) {
			if (col == 0) return 32;
			else if (col == 2) return 33;
			else if (col == 4) return 34;
			else if (col == 6) return 35; 
		}
		
		return 0;
	}
	
	void printBoard () {
		
		String temp;
		for (int i = 0; i < board.length; i++) {
			
			if (board[i] == INVALID) {
				temp = "";
			}
			else if (board[i] == 1) {
				temp = "w";
			}
			else if (board[i] == 2) {
				temp = "W";
			}
			else if (board[i] == -1) {
				temp = "b";
			}
			else if (board[i] == -2) {
				temp = "B";
			}
			else {
				temp = "-";
			}
			
			if (i <= 4 || (i >= 10 && i <= 13) || (i >= 19 && i <= 22) || (i >= 28 || i <= 31)) {
				if (i == 4 || i == 13 || i == 22 || i == 31) {
					System.out.print(temp);
				}
				else {
					System.out.print(temp + "-");
				}
			}
			else {
				System.out.print("-" + temp);
			}
			
			if (i == 4 || i == 8 || i == 13 || i == 17 || i == 22 || i == 26 || i == 31) {
				System.out.println();
			}
		}
		System.out.println();
	}
}