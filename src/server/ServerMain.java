package server;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author babak
 *
 */
public class ServerMain {

	/**
	 * server main method run server class
	 * @param args
	 */
	public static void main(String[] args) {
		
		Server server = new Server(5700);
		server.start();
	}
}
