package server;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerWorker extends Thread {

	private final Socket clientsocket;
	private String login = null;
	private Server server;
	private OutputStream outputStream;

	public ServerWorker(Server server, Socket clientsocket) {
		this.server = server;
		this.clientsocket = clientsocket;
	}

	public String getLogin() {
		return login;
	}

	@Override
	public void run() {
		try {
			handelClientSocket();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private void handelClientSocket() throws IOException {
		InputStream inputStream = clientsocket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		this.outputStream = clientsocket.getOutputStream();

		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			String[] token = line.split(" ");
			if (token != null && token.length > 0) {
				String cmd = token[0];

				if ("LOGIN".equals(cmd)) {
					handelLogin(outputStream, token);
				} else if ("msg".equals(cmd)) {
					handleMessage(token);
				} else if ("LIST".equals(cmd)) {
					handelListLogin();
				}

			}
		}
		clientsocket.close();
	}

	private void handelListLogin() throws IOException {

		ArrayList<ServerWorker> workerList = server.getWorkerList();
		String msg2 = null;
		msg2 = Integer.toString(workerList.size()-1);
		for (ServerWorker worker : workerList) {
			if (worker.getLogin() != null) {
				if (!login.equals(worker.getLogin())) {
					msg2 += " " + worker.getLogin();
				}
			}
				msg2+="\n";
				//send(msg2);
		}
		outputStream.write(msg2.getBytes());
	}

	private void handleMessage(String[] token) throws IOException {
		String sendTo = token[1];
		
		// String body = token[2];
		String body = "";
		for (int i = 2; i < token.length; i++) {
			body += token[i] + " ";
			System.out.println(body);
		}
		
		
		List<ServerWorker> workerList = server.getWorkerList();

		for (ServerWorker worker : workerList) {
			if (sendTo.equalsIgnoreCase(worker.getLogin())) {
				String outMSG = "msg " + login + " " + body + "\n";
				worker.send(outMSG);
			}
		}
	}

	private void handelLogin(OutputStream outputStream, String[] token) throws IOException {
		if (token.length == 3) {
			String login = token[1];
			String password = token[2];

			if ((login.equals("guest") && password.equals("guest"))
					|| (login.equals("jim") && password.equals("jim"))) {
				String msg = "ok login\n";
				System.out.println("login");
				outputStream.write(msg.getBytes());
				this.login = login;
				System.out.println("user loged in successes :" + login + "\n");

				ArrayList<ServerWorker> workerList = server.getWorkerList();

				for (ServerWorker worker : workerList) {
					if (worker.getLogin() != null) {
						if (!login.equals(worker.getLogin())) {
							String msg2 = "online " + worker.getLogin() + "\n";
							send(msg2);
						}
					}
				}

				String onlineMSG = "online " + login + "\n";
				for (ServerWorker worker : workerList) {
					if (!login.equals(worker.getLogin())) {
						worker.send(onlineMSG);
					}
				}
			} else {
				String msg = "error login\n";
				outputStream.write(msg.getBytes());
			}
		}

	}

	private void send(String onlineMSG) throws IOException {

		if (login != null) {
			outputStream.write(onlineMSG.getBytes());
		}
	}
}
