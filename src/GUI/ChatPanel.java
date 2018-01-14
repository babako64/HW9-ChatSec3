package GUI;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.ChatClient;
import client.MessageListener;
import javafx.scene.layout.Border;

public class ChatPanel extends JPanel implements MessageListener{

	private ChatClient client;
	private String login;
	
	DefaultListModel<String> listModel;
	private JList<String> messageList;
	private JTextField textField = new JTextField();
	
	public ChatPanel(ChatClient client,String login) {
		this.client = client;
		this.login = login;
		
		client.addMessageListener(this);
		
		listModel = new DefaultListModel<>();
		messageList= new JList<>(listModel);
		
		setLayout(new BorderLayout());
		add(messageList,BorderLayout.CENTER);
		add(textField,BorderLayout.SOUTH);
		
		textField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					client.msg(login, textField.getText());
					listModel.addElement("you: "+textField.getText());
					textField.setText("");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			}
		});
	}

	@Override
	public void onMessage(String from, String body) {
		
		String line = from + ":" + body;
		listModel.addElement(line);
	}
	
}
