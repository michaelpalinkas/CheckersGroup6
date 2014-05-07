import java.util.*;

public class CheckersState implements GameState {

	int[] board = new int[36];
	boolean myColorIsBlack;
	final int INVALID = 100;
	double[] weights = new double[36];

	/*		
	 * 		0	=>	unoccupied 
	 * 		-1	=>	white piece
	 * 		-2	=>	white king
	 * 		1	=>	black piece
	 * 		2	=>	black king
	 */
	
	
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
		
		weights = new double[]	{0,  
								   1.2,   1,     1,     0.75, 
								1.2,  0,     0,     0.25, 		0,  
								  0.25,   0,     0,     1, 
								1,    0,     0,     0.25,		0, 
								  0.25,   0,     0,     1, 
								1,    0,     0,     0.25,		0, 
								  0.25,   0,     0,     1.2, 
								0.75,  1,     1,     1.2		};
	}

	public String player() {
		if (myColorIsBlack) 
			return "Black";
		else 
			return "White";
	}
	
	public String addJumps(int index, boolean isBlack) {
		
		String toReturn = "";
		if (index-10 >= 0 && !isBlack && board[index-10] == 0 && (board[index-5] == 1 || board[index-5] == 2)) {
			toReturn += ":(" + getRow(index-10) + ":" + getCol(index-10) + ")";
			toReturn += addJumps(index-10, isBlack);
		}
		else if (index-8 >= 0 && !isBlack && board[index-8] == 0 && (board[index-4] == 1 || board[index-4] == 2)) {
			toReturn += ":(" + getRow(index-8) + ":" + getCol(index-8) + ")";
			toReturn += addJumps(index-8, isBlack);
		}
		else if(index+10 < board.length && isBlack && board[index+10] == 0 && (board[index+5] == -1 || board[index+5] == -2)) {
			toReturn += ":(" + getRow(index+10) + ":" + getCol(index+10) + ")";
			toReturn += addJumps(index+10, isBlack);
		}
		else if (index+8 < board.length && isBlack && board[index+8] == 0 && (board[index+4] == -1 || board[index+4] == -2)) {
			toReturn += ":(" + getRow(index+8) + ":" + getCol(index+8) + ")";
			toReturn += addJumps(index+8, isBlack);
		}
		return toReturn;
	}
	
	public String addKingJumps(int index, boolean isBlack, ArrayList<Integer> capped) {
		
		String toReturn = "";
		if (isBlack) { //black
			if (index-10 >= 0 && board[index-10] == 0 && (board[index-5] == -1 || board[index-5] == -2)) {
				boolean good = true;
				for (int i = 0; i < capped.size(); i++) {
					if (index-5 == capped.get(i))
						good = false;
				}
				if (good) {
					toReturn += ":(" + getRow(index-10) + ":" + getCol(index-10) + ")";
					capped.add(index-5);
					toReturn += addKingJumps(index-10, isBlack, capped);
				}
			}
			if (index-8 >= 0 && board[index-8] == 0 && (board[index-4] == -1 || board[index-4] == -2)) {
				boolean good = true;
				for (int i = 0; i < capped.size(); i++) {
					if (index-4 == capped.get(i))
						good = false;
				}
				if (good) {
					toReturn += ":(" + getRow(index-8) + ":" + getCol(index-8) + ")";
					capped.add(index-4);
					toReturn += addKingJumps(index-8, isBlack, capped);
				}
			}
			if(index+10 < board.length && board[index+10] == 0 && (board[index+5] == -1 || board[index+5] == -2)) {
				boolean good = true;
				for (int i = 0; i < capped.size(); i++) {
					if (index+5 == capped.get(i))
						good = false;
				}
				if (good) {
					toReturn += ":(" + getRow(index+10) + ":" + getCol(index+10) + ")";
					capped.add(index+5);
					toReturn += addKingJumps(index+10, isBlack, capped);
				}
			}
			if (index+8 < board.length && board[index+8] == 0 && (board[index+4] == -1 || board[index+4] == -2)) {
				boolean good = true;
				for (int i = 0; i < capped.size(); i++) {
					if (index+4 == capped.get(i))
						good = false;
				}
				if (good) {
					toReturn += ":(" + getRow(index+8) + ":" + getCol(index+8) + ")";
					capped.add(index+4);
					toReturn += addKingJumps(index+8, isBlack, capped);
				}
			}
		}
		else { //white
			if (index-10 >= 0 && board[index-10] == 0 && (board[index-5] == 1 || board[index-5] == 2)) {
				boolean good = true;
				for (int i = 0; i < capped.size(); i++) {
					if (index-5 == capped.get(i))
						good = false;
				}
				if (good) {
					toReturn += ":(" + getRow(index-10) + ":" + getCol(index-10) + ")";
					capped.add(index-5);
					toReturn += addKingJumps(index-10, isBlack, capped);
				}
			}
			if (index-8 >= 0 && board[index-8] == 0 && (board[index-4] == 1 || board[index-4] == 2)) {
				boolean good = true;
				for (int i = 0; i < capped.size(); i++) {
					if (index-4 == capped.get(i))
						good = false;
				}
				if (good) {
					toReturn += ":(" + getRow(index-8) + ":" + getCol(index-8) + ")";
					capped.add(index-4);
					toReturn += addKingJumps(index-8, isBlack, capped);
				}
			}
			if(index+10 < board.length && board[index+10] == 0 && (board[index+5] == 1 || board[index+5] == 2)) {
				boolean good = true;
				for (int i = 0; i < capped.size(); i++) {
					if (index+5 == capped.get(i))
						good = false;
				}
				if (good) {
					toReturn += ":(" + getRow(index+10) + ":" + getCol(index+10) + ")";
					capped.add(index+5);
					toReturn += addKingJumps(index+10, isBlack, capped);
				}
			}
			if (index+8 < board.length && board[index+8] == 0 && (board[index+4] == 1 || board[index+4] == 2)) {
				boolean good = true;
				for (int i = 0; i < capped.size(); i++) {
					if (index+4 == capped.get(i))
						good = false;
				}
				if (good) {
					toReturn += ":(" + getRow(index+8) + ":" + getCol(index+8) + ")";
					capped.add(index+4);
					toReturn += addKingJumps(index+8, isBlack, capped);
				}
			}
		}
		
		return toReturn;
	}
		
	public List<String> actions() {
		
		LinkedList<String> possibleMoves = new LinkedList<String>();
		
		for (int i = 0; i < board.length; i++) {
			
			if (board[i] == -1 && myColorIsBlack == false) { //white piece
				
				if (inBounds(i-4) && board[i-4] == 0) {
					possibleMoves.add("(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i-4) + ":" + getCol(i-4) + ")\n");
				}
				if (inBounds(i-5) && board[i-5] == 0) {
					possibleMoves.add("(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i-5) + ":" + getCol(i-5) + ")\n");
				}
				if (inBounds(i-10) && board[i-10] == 0 && (board[i-5] == 1 || board[i-5] == 2)) { //capture
					String temp = "cap " + "(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i-10) + ":" + getCol(i-10) + ")";
					temp += addJumps(i-10, false);
					temp += "\n";
					possibleMoves.add(temp);					
				}
				if (inBounds(i-8) && board[i-8] == 0 && (board[i-4] == 1 || board[i-4] == 2)) { //capture
					String temp = "cap " + "(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i-8) + ":" + getCol(i-8) + ")";
					temp += addJumps(i-8, false);
					temp += "\n";
					possibleMoves.add(temp);
				}
			}
			else if (board[i] == 1 && myColorIsBlack == true) { //black piece
				
				if (inBounds(i+4) && board[i+4] == 0) {
					possibleMoves.add("(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i+4) + ":" + getCol(i+4) + ")\n");
				}
				if (inBounds(i+5) && board[i+5] == 0) {
					possibleMoves.add("(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i+5) + ":" + getCol(i+5) + ")\n");
				}
				if (inBounds(i+10) && board[i+10] == 0 && (board[i+5] == -1 || board[i+5] == -2)) { //capture
					String temp = "cap " + "(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i+10) + ":" + getCol(i+10) + ")";
					temp += addJumps(i+10, true);
					temp += "\n";
					possibleMoves.add(temp);
				}
				if (inBounds(i+8) && board[i+8] == 0 && (board[i+4] == -1 || board[i+4] == -2)) { //capture
					String temp = "cap " + "(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i+8) + ":" + getCol(i+8) + ")";
					temp += addJumps(i+8, true);
					temp += "\n";
					possibleMoves.add(temp);
				}
			}
			else if (board[i] == -2 && !myColorIsBlack) { //white king
				
				ArrayList<Integer> capped;
				
				if (inBounds(i+4) && board[i+4] == 0) {
					possibleMoves.add("(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i+4) + ":" + getCol(i+4) + ")\n");
				}
				if (inBounds(i+5) && board[i+5] == 0) {
					possibleMoves.add("(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i+5) + ":" + getCol(i+5) + ")\n");
				}
				if (inBounds(i-4) && board[i-4] == 0) {
					possibleMoves.add("(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i-4) + ":" + getCol(i-4) + ")\n");
				}
				if (inBounds(i-5) && board[i-5] == 0) {
					possibleMoves.add("(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i-5) + ":" + getCol(i-5) + ")\n");
				}
				if (inBounds(i+10) && board[i+10] == 0 && (board[i+5] == 1 || board[i+5] == 2)) { //capture
					String temp = "cap " + "(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i+10) + ":" + getCol(i+10) + ")";
					capped = new ArrayList<Integer>();
					capped.add(i+5);
					temp += addKingJumps(i+10, false, capped);
					temp += "\n";
					possibleMoves.add(temp);
				}
				if (inBounds(i+8) && board[i+8] == 0 && (board[i+4] == 1 || board[i+4] == 2)) { //capture
					String temp = "cap " + "(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i+8) + ":" + getCol(i+8) + ")";
					capped = new ArrayList<Integer>();
					capped.add(i+4);
					temp += addKingJumps(i+8, false, capped);
					temp += "\n";
					possibleMoves.add(temp);
				}
				if (inBounds(i-10) && board[i-10] == 0 && (board[i-5] == 1 || board[i-5] == 2)) { //capture
					String temp = "cap " + "(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i-10) + ":" + getCol(i-10) + ")";
					capped = new ArrayList<Integer>();
					capped.add(i-5);
					temp += addKingJumps(i-10, false, capped);
					temp += "\n";
					possibleMoves.add(temp);					
				}
				if (inBounds(i-8) && board[i-8] == 0 && (board[i-4] == 1 || board[i-4] == 2)) { //capture
					String temp = "cap " + "(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i-8) + ":" + getCol(i-8) + ")";
					capped = new ArrayList<Integer>();
					capped.add(i-4);
					temp += addKingJumps(i-8, false, capped);
					temp += "\n";
					possibleMoves.add(temp);
				}
			}
			else if (board[i] == 2 && myColorIsBlack) { //black king
				
				ArrayList<Integer> capped;
				
				if (inBounds(i+4) && board[i+4] == 0) {
					possibleMoves.add("(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i+4) + ":" + getCol(i+4) + ")\n");
				}
				if (inBounds(i+5) && board[i+5] == 0) {
					possibleMoves.add("(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i+5) + ":" + getCol(i+5) + ")\n");
				}
				if (inBounds(i-4) && board[i-4] == 0) {
					possibleMoves.add("(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i-4) + ":" + getCol(i-4) + ")\n");
				}
				if (inBounds(i-5) && board[i-5] == 0) {
					possibleMoves.add("(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i-5) + ":" + getCol(i-5) + ")\n");
				}
				if (inBounds(i+10) && board[i+10] == 0 && (board[i+5] == -1 || board[i+5] == -2)) { //capture
					String temp = "cap " + "(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i+10) + ":" + getCol(i+10) + ")";
					capped = new ArrayList<Integer>();
					capped.add(i+5);
					temp += addKingJumps(i+10, true, capped);
					temp += "\n";
					possibleMoves.add(temp);
				}
				if (inBounds(i+8) && board[i+8] == 0 && (board[i+4] == -1 || board[i+4] == -2)) { //capture
					String temp = "cap " + "(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i+8) + ":" + getCol(i+8) + ")";
					capped = new ArrayList<Integer>();
					capped.add(i+4);
					temp += addKingJumps(i+8, true, capped);
					temp += "\n";
					possibleMoves.add(temp);
				}
				if (inBounds(i-10) && board[i-10] == 0 && (board[i-5] == -1 || board[i-5] == -2)) { //capture
					String temp = "cap " + "(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i-10) + ":" + getCol(i-10) + ")";
					capped = new ArrayList<Integer>();
					capped.add(i-5);
					temp += addKingJumps(i-10, true, capped);
					temp += "\n";
					possibleMoves.add(temp);					
				}
				if (inBounds(i-8) && board[i-8] == 0 && (board[i-4] == -1 || board[i-4] == -2)) { //capture
					String temp = "cap " + "(" + getRow(i) + ":" + getCol(i) + "):(" + getRow(i-8) + ":" + getCol(i-8) + ")";
					capped = new ArrayList<Integer>();
					capped.add(i-4);
					temp += addKingJumps(i-8, true, capped);
					temp += "\n";
					possibleMoves.add(temp);
				}
			}
		}
		
		/*System.out.println("Possible Moves:");
		for (int i = 0; i < possibleMoves.size(); i++) {
			System.out.print(possibleMoves.get(i));
		}*/
		
		if (canCapture(possibleMoves)) {
			LinkedList<String> newMoves = new LinkedList<String>();
			for (int i = 0; i < possibleMoves.size(); i++) {
				if (possibleMoves.get(i).contains("cap")) {
					String temp = possibleMoves.get(i).substring(4);
					newMoves.add(temp);	
				}
			}
			return newMoves;
		}
		
		return possibleMoves;
	}
	
	public CheckersState result(String move) {
		//System.out.print(move);
		CheckersState newCheckers = new CheckersState(!myColorIsBlack);
		
		for (int i = 0; i < board.length; i++) {
			newCheckers.board[i] = board[i];
		}
		
		String[] splitmessage = move.split("[:()]");
				
		int fromRow, toRow, fromCol, toCol, indexFrom, indexTo;
		int i = 1;
		while (i <= splitmessage.length-5) {
			
			fromRow = Integer.valueOf(splitmessage[i]);
			fromCol = Integer.valueOf(splitmessage[i+1]);
			toRow = Integer.valueOf(splitmessage[i+4]);
			toCol = Integer.valueOf(splitmessage[i+5]);
			
			indexFrom = getBoardIndex(fromRow, fromCol);
			indexTo = getBoardIndex(toRow, toCol);
			
			int pieceValue = newCheckers.board[indexFrom];
			newCheckers.board[indexFrom] = 0;
			newCheckers.board[indexTo] = pieceValue;
			
			if (getRow(indexTo) == 0 && newCheckers.board[indexTo] == 1) {
				newCheckers.board[indexTo] = 2;
			}
			else if (getRow(indexTo) == 7 && newCheckers.board[indexTo] == -1) {
				newCheckers.board[indexTo] = -2;
			}
			
			int diff = indexFrom - indexTo;
			if (diff == 10) { //delete captured pieces
				newCheckers.board[indexFrom-5] = 0;
			}
			else if (diff == 8){
				newCheckers.board[indexFrom-4] = 0;
			}
			else if (diff == -10) {
				newCheckers.board[indexFrom+5] = 0;
			}
			else if (diff == -8) {
				newCheckers.board[indexFrom+4] = 0;
			}
			
			i += 4;
		}
		return newCheckers;
	}
	
	public boolean isTerminal () {
		
		boolean terminal = false;
		int countBlack = 0;
		int countWhite = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 1 || board[i] == 2) {
				countBlack++;
			}
			else if (board[i] == -1 || board[i] == -2) {
				countWhite++;
			}
		}
		
		if (countBlack == 0 || countWhite == 0) {
			terminal = true;
		}
		
		return terminal;
	}
	
	public double utility(String player) {
		
		double posValue = 0.0;
        double whitePieces = 0.0;
        double whiteKings = 0.0;
        double blackPieces = 0.0;
        double blackKings = 0.0;
        double totalPieces = 0.0;

        for(int i = 0; i < board.length; i++)
        {
            if(board[i] == -1) {
                posValue -= 1 * weights[i];
                whitePieces++;
                totalPieces++;
            }
            if(board[i] == -2) {
            	posValue -= 1.5 * weights[i];
            	whiteKings++;
            	totalPieces++;
        	}
            if(board[i] == 1) {
                posValue += 1 * weights[i];
                blackPieces++;
                totalPieces++;
            }
            if(board[i] == 2) {
                posValue += 1.5 * weights[i];
                blackKings++;
                totalPieces++;
            }
        }
        
        if (totalPieces <= 8) {
        	posValue = posValue * 0.5;
        }
      
        
        double pieceAdvantage = blackPieces - whitePieces;
        double kingAdvantage = blackKings - whiteKings;

        	
        double value = (11*kingAdvantage) + (6.5*pieceAdvantage) + (0.65*posValue);
        
        if (whitePieces + whiteKings == 0) {
        	value += 1000;
        }
        else if (blackPieces + blackKings == 0) {
        	value -= 1000;
        }
        
        return value;
	}
	
	public int getBoardIndex(int row, int col) {
		
		if (row == 7) {
			if (col == 1) return 1;
			else if (col == 3) return 2;
			else if (col == 5) return 3;
			else if (col == 7) return 4;
		}
		else if (row == 6) {
			if (col == 0) return 5;
			else if (col == 2) return 6;
			else if (col == 4) return 7;
			else if (col == 6) return 8; 
		}
		else if (row == 5) {
			if (col == 1) return 10;
			else if (col == 3) return 11;
			else if (col == 5) return 12;
			else if (col == 7) return 13; 
		}
		else if (row == 4) {
			if (col == 0) return 14;
			else if (col == 2) return 15;
			else if (col == 4) return 16;
			else if (col == 6) return 17; 
		}
		else if (row == 3) {
			if (col == 1) return 19;
			else if (col == 3) return 20;
			else if (col == 5) return 21;
			else if (col == 7) return 22; 
		}
		else if (row == 2) {
			if (col == 0) return 23;
			else if (col == 2) return 24;
			else if (col == 4) return 25;
			else if (col == 6) return 26; 
		}
		else if (row == 1) {
			if (col == 1) return 28;
			else if (col == 3) return 29;
			else if (col == 5) return 30;
			else if (col == 7) return 31; 
		}
		else if (row == 0) {
			if (col == 0) return 32;
			else if (col == 2) return 33;
			else if (col == 4) return 34;
			else if (col == 6) return 35; 
		}
		
		return 0;
	}
	
	public int getRow(int boardIndex) {

		if (boardIndex >= 1 && boardIndex <= 4) return 7;
		else if (boardIndex >= 5 && boardIndex <= 8) return 6;
		else if (boardIndex >= 10 && boardIndex <= 13) return 5;
		else if (boardIndex >= 14 && boardIndex <= 17) return 4;
		else if (boardIndex >= 19 && boardIndex <= 22) return 3;
		else if (boardIndex >= 23 && boardIndex <= 26) return 2;
		else if (boardIndex >= 28 && boardIndex <= 31) return 1;
		else if (boardIndex >= 32 && boardIndex <= 35) return 0;		
		
		return 100;
	}
	
	public int getCol(int boardIndex) {
		
		if (boardIndex == 5 || boardIndex == 14 || boardIndex == 23 || boardIndex == 32) return 0;
		else if (boardIndex == 1 || boardIndex == 10 || boardIndex == 19 || boardIndex == 28) return 1;
		else if (boardIndex == 6 || boardIndex == 15 || boardIndex == 24 || boardIndex == 33) return 2;
		else if (boardIndex == 2 || boardIndex == 11 || boardIndex == 20 || boardIndex == 29) return 3;
		else if (boardIndex == 7 || boardIndex == 16 || boardIndex == 25 || boardIndex == 34) return 4;
		else if (boardIndex == 3 || boardIndex == 12 || boardIndex == 21 || boardIndex == 30) return 5;
		else if (boardIndex == 8 || boardIndex == 17 || boardIndex == 26 || boardIndex == 35) return 6;
		else if (boardIndex == 4 || boardIndex == 13 || boardIndex == 22 || boardIndex == 31) return 7;	
		
		return 100;
	}
	
	void printBoard () {
		
		System.out.println();
		
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
		System.out.println("\n");
	}
	
	public boolean inBounds (int index) {
		return (index >= 0 && index < board.length);
	}
	
	public boolean canCapture (List<String> moves) {
		
		boolean canCapture = false;
		for (int i = 0; i < moves.size(); i++) {
			if (moves.get(i).contains("cap ")) {
				canCapture = true;
			}
		}
		
		return canCapture;
	}
	
	public boolean allKings (boolean isBlack) {
		int wcount = 0;
		int bcount = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] == -1) {
				wcount++;
			}
			else if (board[i] == 1) {
				bcount++;
			}
		}
		if (isBlack) {
			return bcount == 0;
		}
		else {
			return wcount == 0;
		}
	}
}