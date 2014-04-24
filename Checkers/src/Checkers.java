import java.net.*;
import java.io.*;
import java.util.*;


public class Checkers {

	private static Socket mySocket;
	private static CheckersState myState;
	
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
			
			String[] splitMessage = message.split(":");
			if (splitMessage[1].equals("White")){
				myState = new CheckersState(false);
				message = br.readLine();
				System.out.println(message);
				myState.result(message, true);
				myState.printBoard();
			}
			else {
				myState = new CheckersState(true);
			}
			
			
			boolean inGame = true;
			String move;
			while (inGame) {
				message = br.readLine();
				System.out.println(message);	//?Move(time)
				if (message.contains("Result") || message.contains("Error")) break;
				move = myState.actions().get(0);
				System.out.print("\nMove chosen:\n" + move);
				bw.write(move);
				bw.flush();
				myState.result(move, false);
				myState.printBoard();
				
				message = br.readLine();	//Move:ourmove
				System.out.println(message);
				message = br.readLine();	//Move:theirmove
				System.out.println(message);

				if (message.contains("Result") || message.contains("Error")) break;
				myState.result(message, true);
				myState.printBoard();
			}
			
			mySocket.close();
		}
		catch(IOException e)
	    {
	       e.printStackTrace();
	    }
	}
}