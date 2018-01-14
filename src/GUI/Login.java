package GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.ChatMain;
import client.Resorce;

public class Login extends JFrame{

	Resorce r ;
	ChatMain main;
	JFrame frame;
	public Login(ChatMain main) {
		
		this.main = main;
		
		 frame = new JFrame("");
		
		JTextField ip,port,userName;
		JPasswordField tPass;
		JButton login = new JButton("login");
		
		ip=new JTextField();
		port=new JTextField();
		userName=new JTextField();
		
		ip.setBounds(100,100, 200,30);
		port.setBounds(100,150, 200,30); 
		userName.setBounds(100,200, 200,30);
		
		tPass=new JPasswordField();  
		tPass.setBounds(100,250, 200,30); 
		
		login.setBounds(100,300, 200,30); 
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String ipT = ip.getText();
				String portT = port.getText();
				String userT = userName.getText();
				String passT = tPass.getText();
				 r = new Resorce(ipT, portT, userT, passT);
				try {
					main.start(r);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				frame.dispose();
			}
		});
		
		frame.add(ip);
		frame.add(port);
		frame.add(userName);
		frame.add(tPass);
		frame.add(login);
		frame.setSize(500,500);  
		frame.setLayout(null);  
		frame.setVisible(true);  
	    
	    
	}
	
	public Resorce getResorce() {
		
		return r;
	}
}
