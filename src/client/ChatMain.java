package client;
import java.io.IOException;

import GUI.Login;

public class ChatMain {
	
public static void main(String[] args) throws IOException {
		
		ChatMain main = new ChatMain();
		Login login = new Login(main);
		//main.start();
	}
	/**
	 * input data connect to server
	 * @param res
	 * @throws IOException
	 */
	public void start(Resorce res) throws IOException {
		
		System.out.println(res.ip);
		System.out.println(res.userName);
		
		ChatClient client = new ChatClient(res.ip,Integer.parseInt(res.port));
		client.addOnlineUser(new UserLoginListener() {
			
			@Override
			public void online(String login) {
				
				System.out.println(login);
			}
		});
		client.addMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(String from, String body) {
				System.out.println("message from " + from+" >"+body);
				
			}
		});
		if(!client.connect()) {
			System.err.println("connect faild");
		}else {
			System.out.println("connect successful");
			if(client.login(res.userName,res.pass)) {
				System.out.println("login successed");
				//client.msg("jim","hello world");
			}else {
				System.out.println("login fail");
			}
			
		}
		
	}
}
