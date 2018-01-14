package server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * 
 * @author babak
 *
 */
public class ServerMain {
	public static HashMap<String, String> clients = new HashMap<>();
	/**
	 * server main method run server class
	 * @param args
	 */
	public static void main(String[] args) {
		
		clients.put("jim", "jim");
		clients.put("ali", "ali");
		clients.put("mohammad", "mohammad");
		
		Server server = new Server(5700);
		server.start();
	}
}
