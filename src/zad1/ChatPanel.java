package zad1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.channels.SocketChannel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPanel extends JPanel {
	Client user;
	JTextArea chatArea;
	JTextField inputText;
	JButton sendButton;
	JButton logoutButt;
	JScrollPane scroll;
	boolean exists = false;
	SocketChannel sc;
	JFrame mainFrame;
	public ChatPanel(Client user) {
		super(new BorderLayout());
		
		this.setPreferredSize(new Dimension(800, 800));

		this.user = user;
		chatArea = new JTextArea();
		chatArea.setEditable(false);
//		chatArea.setLineWrap(true);
		scroll = new JScrollPane(chatArea);
		inputText = new JTextField();
		inputText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputText.getText().length() > 0) {
					
					user.sendMessage(user.nick + ": " + inputText.getText());
					System.out.println("Message: " + inputText.getText() + " sent");
					inputText.setText("");
				}
			}
		});
		sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputText.getText().length() > 0)
					user.sendMessage(user.nick + ": " + inputText.getText());
					System.out.println("Message: " + inputText.getText() + " sent");
					inputText.setText("");
			}
		});

		logoutButt = new JButton("Wyloguj");
		logoutButt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					user.s.close();
					mainFrame.dispose();
				} catch (IOException e1) {e1.printStackTrace();}
				
				

			}
		});

		this.add(scroll, BorderLayout.CENTER);
		this.add(inputText, BorderLayout.SOUTH);
		this.add(logoutButt, BorderLayout.WEST);
		this.add(sendButton, BorderLayout.EAST);

		mainFrame = new JFrame("Chat");
		mainFrame.setPreferredSize(new Dimension(800, 800));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().add(this);
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
		
	}


	public void appendToArea(String text) {
		chatArea.append(text);
		chatArea.update(chatArea.getGraphics());

	}
}
