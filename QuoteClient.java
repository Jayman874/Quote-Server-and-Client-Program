import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * 
 * Client connection which connects to server uses its address and port
 *
 */
public class QuoteClient {
	
	private final static int port = 5000;
	private static String current_ip = "";
	
	public static void main(String[] args) throws IOException{
		try {
			System.out.println("Enter Server IP Address:");
			Scanner scan = new Scanner(System.in);
			current_ip = scan.next();
			Socket socket = new Socket(current_ip, port); // creates new socket connection to server using the address and port
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream())); //gets the content the server wants to display to the client
			String line = br.readLine();
			while (line != null) {
				System.out.println(line); // display server content to client 
				line = br.readLine();
			}
			scan.close();
			br.close();
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
