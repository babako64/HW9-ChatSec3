package server;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

	public static void main(String[] args) {
		
		Server server = new Server(5700);
		server.start();
	}
}
