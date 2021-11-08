import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 *  Establishes server and creates a new thread for each of the clients which connect to the server
 * 
 */
public class QuoteServer extends Thread {
	private final static int port = 5000;
	private static boolean runServer = true;
	private static int counter = 0;
	private static String ip;
	//Array of quotes which server displays to client
	private final static String[] quotes = {
			"Try to be a rainbow in someone's cloud.\n"
			+ "   -Maya Angelou",
			"It is in your moments of decision that your destiny is shaped.\n"
			+ "   -Tony Robbins",
			"It is always the simple that produces the marvelous.\n"
			+ "   -Amelia Barr",
			"A single sunbeam is enough to drive away many shadows.\n"
			+ "   -Francis of Assisi",
			"Two roads diverged in a wood and I, I took the one less traveled by, and that has made all the difference.\n"
			+ "   -Robert Frost",
			"There is nothing impossible to they who will try.\n"
			+ "   -Alexander the Great",
			"Accept the things to which fate binds you, and love the people with whom fate brings you together, but do so with all your heart.\n"
			+ "   -Marcus Aurelius",
			"The world is full of magical things patiently waiting for our wits to grow sharper.\n"
			+ "   -Bertrand Russell"};
	
	public static void main(String[] args) throws IOException{
		ServerSocket server = null;
		try {
			server = new ServerSocket(port); // creates new server socket using a port
			System.out.println("Waiting for Client Connection");
			while(runServer) {
				Socket clientConnection = server.accept(); // Waits for client then accepts client connection
				counter++;
				System.out.println("Client Connected");
				System.out.println("Number of Clients which have Connected " + counter);
				new Thread(new ClientServerThreadHandler(clientConnection)).start(); // starts a new thread uses the client socket connection
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 *  Calls the run method for each client which has connected to the server
	 *
	 */
	private static class ClientServerThreadHandler implements Runnable{
		
		Socket clientConnection;
		
		/**
		 * ClientServerThreadHandler Constructor passes through the client connection
		 * 
		 * @param clientConnection
		 * 		- client socket connection
		 */
		public ClientServerThreadHandler(Socket clientConnection) {
			this.clientConnection = clientConnection;
		}
		
		/**
		 * Gets random quote from the string array of quotes
		 * 
		 * @return
		 * 		- randomly selected quote
		 */
		public String getRandomQuote() {
			Random rand = new Random();
			int randomNumber = rand.nextInt(quotes.length); // generates random number 
			return quotes[randomNumber];
		}

		@Override
		public void run(){
			try {
				ip = InetAddress.getLocalHost().getHostAddress();
				PrintStream stream = new PrintStream(clientConnection.getOutputStream()); // creates new stream to output server contents to client 
				stream.println(ip + " says: " +getRandomQuote().toString()); // print contents to the client 
				stream.close();
				clientConnection.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}	
	}
}
