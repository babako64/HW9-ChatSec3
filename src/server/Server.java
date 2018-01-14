package server;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author babak
 *
 */
public class Server extends Thread{

	private final int serverPort;
	private ArrayList<ServerWorker> workerList = new ArrayList<>();
	
	public Server(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public ArrayList<ServerWorker> getWorkerList(){
		return workerList;
	}
	
	/**
	 * accept new client socket and run thread
	 */
	@Override
	public void run() {
	
		try {
			
			ServerSocket serverSocket = new ServerSocket(serverPort);
			while(true) {
			Socket clientsocket = serverSocket.accept();
			System.out.println("Accept connection ...");
			ServerWorker worker = new ServerWorker(this,clientsocket);
			workerList.add(worker);
			worker.start();
			}

		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
