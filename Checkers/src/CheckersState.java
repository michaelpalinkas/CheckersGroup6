
public class CheckersState {

	int[] board = new int[36];		//something
	boolean myColorIsBlack;
	final int INVALID = 100;
	
	public CheckersState(boolean color) {
		
		myColorIsBlack = color;
		for (int i = 0; i < board.length; i++) {
			
			if (i == 0 || i == 9 || i == 18 || i == 27) {
				board[i] = INVALID;
			}
			else if (i <= 13) {
				//1 for black piece
				board[i] = 1;
			}
			else if (i >= 23) {
				//-1 for white piece
				board[i] = -1;
			}
			else {
				//0 for unoccupied
				board[i] = 0;
			}
		}
	}
	
	void printBoard () {
		
		String temp;
		for (int i = 0; i < board.length; i++) {
			
			if (board[i] == INVALID) {
				temp = "";
			}
			else if (board[i] == 1) {
				temp = "b";
			}
			else if (board[i] == 2) {
				temp = "B";
			}
			else if (board[i] == -1) {
				temp = "w";
			}
			else if (board[i] == -2) {
				temp = "W";
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
	}
}
