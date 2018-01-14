package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import GUI.ListPanel;

public class ChatClient {

	private String serverName;
	private int serverPort;
	private Socket socket;
	private OutputStream serverOut;
	private InputStream serverIn;
	private BufferedReader bufferedIn;
	private ArrayList<String> messageList = new ArrayList<>();
	private ArrayList<MessageListener> messageListenner = new ArrayList<>();
	private ArrayList<UserLoginListener> onlineListenner = new ArrayList<>();
	private String onlineList;

	public ChatClient(String serverName, int serverPort) {
		this.serverName = serverName;
		this.serverPort = serverPort;
	}
	/**
	 * send message to target
	 * @param sendTo
	 * @param body
	 * @throws IOException
	 */
	public void msg(String sendTo, String body) throws IOException {

		String cmd = "msg " + sendTo + " " + body + "\n";
		serverOut.write(cmd.getBytes());
	}
	
	/**
	 * check user to login
	 * @param login
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public boolean login(String login, String password) throws IOException {

		String cmd = "LOGIN " + login + " " + password + "\n";
		serverOut.write(cmd.getBytes());
		String responce = bufferedIn.readLine();
		System.out.println("responce line: " + responce);

		if ("ok login".equalsIgnoreCase(responce)) {
			String cmd2 = "LIST\n";

			startMessageReader();

			ListPanel listPanel = new ListPanel(this);

			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * start thread for read commands
	 */
	private void startMessageReader() {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					readMessage();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
	
	/**
	 * reads commands
	 * @throws IOException
	 */
	protected void readMessage() throws IOException {

		try {
			String line;
			while ((line = bufferedIn.readLine()) != null) {

				String[] token = line.split(" ");
				if (token != null && token.length > 0) {
					String cmd = token[0];
					if ("online".equalsIgnoreCase(cmd)) {
						handelOnline(token);
					} else if ("msg".equalsIgnoreCase(cmd)) {
						handelMessage(token);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			socket.close();
		}

	}
	
	/**
	 * handel recive message
	 * @param token
	 */
	private void handelMessage(String[] token) {

		String sendTo = token[1];
		String body = "";
		for (int i = 2; i < token.length; i++) {
			body += token[i] + " ";
			System.out.println(body);
		}

		for (MessageListener ml : messageListenner) {
			ml.onMessage(sendTo, body);
		}
	}

	private void handelOnline(String[] token) {

		String login = token[1];
		for (UserLoginListener user : onlineListenner) {
			user.online(login);
		}
	}

	/**
	 * connect to server
	 * @return
	 */
	public boolean connect() {

		try {

			this.socket = new Socket(serverName, serverPort);
			this.serverIn = socket.getInputStream();
			this.serverOut = socket.getOutputStream();

			this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
			return true;

		} catch (IOException e) {

		}
		return false;
	}

	/**
	 * listen to recive message
	 * @param listener
	 */
	public void addMessageListener(MessageListener listener) {

		messageListenner.add(listener);
	}

	public void addOnlineUser(UserLoginListener listener) {
		onlineListenner.add(listener);
	}
}
