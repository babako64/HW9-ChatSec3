package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.ChatMain;
import client.Resorce;

public class Login extends JFrame {

	Resorce r;
	ChatMain main;
	JFrame frame;

	public Login(ChatMain main) {

		this.main = main;

		frame = new JFrame("");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JTextField ip, port, userName;
		JPasswordField tPass;
		JButton login = new JButton("login");

		JLabel ipLable = new JLabel("Ip");
		JLabel portLable = new JLabel("Port");
		JLabel userNameLable = new JLabel("User Name");
		JLabel passLable = new JLabel("Password");

		ip = new JTextField();
		port = new JTextField();
		userName = new JTextField();

		ip.setBounds(150, 100, 200, 30);
		ipLable.setBounds(50, 100, 100, 30);

		port.setBounds(150, 150, 200, 30);
		portLable.setBounds(50, 150, 100, 30);

		userName.setBounds(150, 200, 200, 30);
		userNameLable.setBounds(50, 200, 100, 30);

		tPass = new JPasswordField();
		tPass.setBounds(150, 250, 200, 30);
		passLable.setBounds(50, 250, 100, 30);

		login.setBounds(150, 300, 200, 30);

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

		frame.add(ipLable);
		frame.add(portLable);
		frame.add(userNameLable);
		frame.add(passLable);

		frame.setSize(500, 500);
		frame.setLayout(null);
		frame.setVisible(true);

	}

	public Resorce getResorce() {

		return r;
	}
}
