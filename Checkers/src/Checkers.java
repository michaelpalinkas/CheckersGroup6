import java.net.*;
import java.io.*;
import java.util.*;


public class Checkers {

	private static Socket mySocket;
	private static GameState myState;
	private static MinimaxSearch mms;
	private static AlphaBetaSearch abs;
	private static final int MAXDEPTH = 12;
	
	public static void main(String[] args) {
		
		
		start();
	}
	
	public static void start () {
		
		try {
			Scanner scan = new Scanner(System.in);
			
			String host = "icarus2.engr.uconn.edu";
			int port = 3499;
			InetAddress address = InetAddress.getByName(host);
			mySocket = new Socket(address, port);
			
			OutputStream os = mySocket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
			InputStream is = mySocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String message = br.readLine();
			System.out.println(message);
			message = br.readLine();
			System.out.println(message);
			
			String user = "11\n";
			bw.write(user);
			bw.flush();
			message = br.readLine();
			System.out.println(message);
			String pass = "530811\n";
			bw.write(pass);
			bw.flush();
			message = br.readLine();
			System.out.println(message);
			bw.write("0\n");
			bw.flush();
			message = br.readLine();
			System.out.println(message);
			message = br.readLine();
			System.out.println(message);
			
			boolean maximizing;
			int ply = 0;
			String[] splitMessage = message.split(":");
			if (splitMessage[1].equals("White")){
				maximizing = false;
				myState = new CheckersState(true);
				message = br.readLine();
				System.out.println(message);
				myState = myState.result(message.substring(11));
				((CheckersState)myState).printBoard();
				ply++;
			}
			else {
				maximizing = true;
				myState = new CheckersState(true);
			}
			Random generator = new Random();
			mms = new MinimaxSearch();
			abs = new AlphaBetaSearch();
			boolean inGame = true;
			boolean valid = false;
			int time;
			int depth = MAXDEPTH;
			String move;
			while (inGame) {
				message = br.readLine();
				System.out.println(message);	//?Move(time)
				if (message.contains("Result") || message.contains("Error")) break;
				splitMessage = message.split("[()]");
				time = Integer.parseInt(splitMessage[1]);
				if (time < 8) {
					depth = 6;
				}
				else if (time < 22) {
						depth = MAXDEPTH-4;
				}
				else if (time < 42) {
					depth = MAXDEPTH-3;
				}
				else if (time < 60) {
					depth = MAXDEPTH-2;
				}
				else if (time < 80) {
					depth = MAXDEPTH-1;
				}
				else if(time < 150 && time > 120){
					depth = MAXDEPTH+1;
				}
				else if(time < 120){
					depth = MAXDEPTH;
				}
				else {
					depth = MAXDEPTH;
				}
				
			/*	if (time < 8) {
					depth = 6;
				}
				else if (time < 22) {
						depth = MAXDEPTH-4;
				}
				else if (time < 42) {
					depth = MAXDEPTH-3;
				}
				else if (time < 60) {
					depth = MAXDEPTH-2;
				}
				else if (time < 80) {
					depth = MAXDEPTH-1;
				}
				else {
					depth = MAXDEPTH;
				}
				*/
				//System.out.println();
				abs.decision(myState, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, maximizing, true);
				//System.out.println();
				List<String> potentialMoves = myState.actions();
				//System.out.println("Possible Moves:");
				valid = false;
				for (int i = 0; i < potentialMoves.size(); i++) {
					//System.out.print(potentialMoves.get(i));
					if (potentialMoves.get(i).equals(abs.getBestAction())) {
						valid = true;
					}
				}
				System.out.println();
				//move = mms.minimaxDecision(myState, maximizing);
				if (valid) {
					move = abs.getBestAction();
				}
				else {
					move = potentialMoves.get(0);
					System.out.println("search had invalid move");
				}
				System.out.print("\nMove chosen:\n" + move);
				bw.write(move);
				bw.flush();
				myState = myState.result(move);
				ply++;
				((CheckersState)myState).printBoard();
				
				message = br.readLine();	//Move:ourmove
				System.out.println(message);
				message = br.readLine();	//Move:theirmove
				System.out.println(message);

				if (message.contains("Result") || message.contains("Error")) break;
				myState = myState.result(message.substring(11));
				ply++;
				((CheckersState)myState).printBoard();
			}
			
			mySocket.close();
		}
		catch(IOException e)
	    {
	       e.printStackTrace();
	    }
	}
}