package Bista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Kontroladorea.GestoreNagusia;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPN extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnErab;
	private JButton btnEska;
	private JButton btnFilm;
	private JButton btnExit;
	private AdminKontroladorea kontroladorea = null;

	public AdminPN() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnErab());
		contentPane.add(getBtnEska());
		contentPane.add(getBtnFilm());
		contentPane.add(getBtnExit());
		setVisible(true);
	}

	private JButton getBtnErab() {
		if (btnErab == null) {
			btnErab = new JButton("Erabiltzaileak kudeatu");
			btnErab.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnErab.setBounds(112, 55, 196, 38);
			btnErab.addActionListener(getA());
		}
		return btnErab;
	}
	private JButton getBtnEska() {
		if (btnEska == null) {
			btnEska = new JButton("Eskaerak onartu");
			btnEska.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnEska.setBounds(115, 112, 196, 38);
			btnEska.addActionListener(getA());
		}
		return btnEska;
	}
	private JButton getBtnFilm() {
		if (btnFilm == null) {
			btnFilm = new JButton("Film eskaerak");
			btnFilm.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnFilm.setBounds(115, 168, 196, 38);
			btnEska.addActionListener(getA());
		}
		return btnFilm;
	}
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("exit");
			btnExit.setBounds(351, 10, 85, 21);
			btnExit.addActionListener(getA());
		}
		return btnExit;
	}
	
	private AdminKontroladorea getA() {
		if (kontroladorea == null) {
			kontroladorea = new AdminKontroladorea ();
		}
		return kontroladorea;
	}
	
	//---------------------------------------KONTROLADOREA---------------------------------------
	private class AdminKontroladorea implements ActionListener{
		
		public AdminKontroladorea() {}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource().equals(btnErab)){
				new ErabKudeatu();
				setVisible(false);
			}
		}
	}
}
