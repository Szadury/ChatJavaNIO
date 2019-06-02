package zad1;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
	private JLabel nameLab;
	private JTextField nameInp;
	private JButton confirmBut;
	JFrame tmp;
	
	public LoginPanel(Client user) {
		setPreferredSize (new Dimension (300, 70));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)) ;
		nameLab = new JLabel("Wpisz nick:");
		nameInp = new JTextField();
		nameInp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nameInp.getText().length()>0) {
					tmp.dispose();
					new Client(nameInp.getText());
				
				}
				else {
					nameLab.setText(nameLab.getText() + " (Wypelnij pole)");
				}
			}
		});
		
		confirmBut = new JButton("Confirm");
		confirmBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(nameInp.getText().length()>0) {
					tmp.dispose();
					new Client(nameInp.getText());
				
				}
				else {
					nameLab.setText(nameLab.getText() + " (Wypelnij pole)");
				}
			}
		});
		
		add(nameLab);
		add(nameInp);
		add(confirmBut);
		
		tmp = new JFrame("Login");
		tmp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tmp.getContentPane().add(this);
		tmp.pack();
		tmp.setVisible(true);
		tmp.setResizable(false);
		
	}
}
