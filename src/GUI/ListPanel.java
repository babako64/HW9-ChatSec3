package GUI;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import client.ChatClient;
import client.UserLoginListener;

public class ListPanel extends JFrame implements UserLoginListener{

	 private JList<String> onlineList;
	 ChatClient client;
	 DefaultListModel<String> listModel;
	 public ListPanel(ChatClient client) throws IOException {
	        this.client = client;
	       listModel = new DefaultListModel<>();
	        
	        client.addOnlineUser(this);
	      
	        //create the list
	        onlineList = new JList<>(listModel);
	        add(onlineList,BorderLayout.CENTER);
	       
	        MouseListener mouseListener = new MouseAdapter() {
	            public void mouseClicked(MouseEvent mouseEvent) {
	              JList theList = (JList) mouseEvent.getSource();
	              if (mouseEvent.getClickCount() == 2) {
	                int index = theList.locationToIndex(mouseEvent.getPoint());
	                if (index >= 0) {
	                  Object o = theList.getModel().getElementAt(index);
	                  System.out.println("Double-clicked on: " + o.toString());
	                  
	                  ChatPanel cp = new ChatPanel(client, o.toString());
	                  JFrame f = new JFrame("Chat " + o.toString());
	                  f.setSize(300, 300);
	                  f.getContentPane().add(cp, BorderLayout.CENTER);
	                  f.setVisible(true);
	                  
	                }
	              }
	            }
	          };
	          onlineList.addMouseListener(mouseListener);
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setTitle("JList Example");       
	        this.setSize(400,400);
	        this.setLocationRelativeTo(null);
	        this.setVisible(true);
	        
	       // new ListPanel();
	    }
	@Override
	public void online(String login) {
		
		listModel.addElement(login);
	}
}
