import java.net.*;
import java.io.*;
import java.util.*;


public class Checkers {

	private static Socket mySocket;
	private static GameState myState;
	private static MinimaxSearch mms;
	
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
			String[] splitMessage = message.split(":");
			if (splitMessage[1].equals("White")){
				maximizing = false;
				myState = new CheckersState(false);
				message = br.readLine();
				System.out.println(message);
				myState = myState.result(message.substring(11));
				((CheckersState)myState).printBoard();
			}
			else {
				maximizing = true;
				myState = new CheckersState(true);
			}
			
			mms = new MinimaxSearch();
			boolean inGame = true;
			String move;
			while (inGame) {
				message = br.readLine();
				System.out.println(message);	//?Move(time)
				if (message.contains("Result") || message.contains("Error")) break;
				List<String> potentialMoves = myState.actions();
				System.out.println("Possible Moves:");
				for (int i = 0; i < potentialMoves.size(); i++) {
					System.out.print(potentialMoves.get(i));
				}
				System.out.println();
				move = mms.minimaxDecision(myState, maximizing);
				System.out.print("\nMove chosen:\n" + move);
				bw.write(move);
				bw.flush();
				myState = myState.result(move);
				((CheckersState)myState).printBoard();
				
				message = br.readLine();	//Move:ourmove
				System.out.println(message);
				message = br.readLine();	//Move:theirmove
				System.out.println(message);

				if (message.contains("Result") || message.contains("Error")) break;
				myState = myState.result(message.substring(11));
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