package Bista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Kontroladorea.GestoreNagusia;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public class DatuakAldatu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField textFieldIzena;
	private JLabel lblAbizena;
	private JTextField textFieldAbizena;
	private JLabel lblEmail;
	private JTextField textFieldEmail;
	private JLabel lblPasahitza;
	private JTextField textFieldPasahitza;
	private JButton btnAldatu;
	private JButton btnExit;
	private DatuakAldatuKontroladorea kontroladorea = null;


	public DatuakAldatu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNewLabel());
		contentPane.add(getTextFieldIzena());
		contentPane.add(getLblAbizena());
		contentPane.add(getTextFieldAbizena());
		contentPane.add(getLblEmail());
		contentPane.add(getTextFieldEmail());
		contentPane.add(getLblPasahitza());
		contentPane.add(getTextFieldPasahitza());
		contentPane.add(getBtnAldatu());
		contentPane.add(getBtnExit());
		setVisible(true);
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Izena:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel.setBounds(20, 66, 45, 13);
		}
		return lblNewLabel;
	}
	private JTextField getTextFieldIzena() {
		if (textFieldIzena == null) {
			textFieldIzena = new JTextField();
			textFieldIzena.setBounds(20, 89, 144, 19);
			textFieldIzena.setColumns(10);
		}
		return textFieldIzena;
	}
	private JLabel getLblAbizena() {
		if (lblAbizena == null) {
			lblAbizena = new JLabel("Abizena:");
			lblAbizena.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblAbizena.setBounds(20, 140, 45, 13);
		}
		return lblAbizena;
	}
	private JTextField getTextFieldAbizena() {
		if (textFieldAbizena == null) {
			textFieldAbizena = new JTextField();
			textFieldAbizena.setColumns(10);
			textFieldAbizena.setBounds(20, 162, 144, 19);
		}
		return textFieldAbizena;
	}
	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("Email:");
			lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblEmail.setBounds(255, 66, 45, 13);
		}
		return lblEmail;
	}
	private JTextField getTextFieldEmail() {
		if (textFieldEmail == null) {
			textFieldEmail = new JTextField();
			textFieldEmail.setColumns(10);
			textFieldEmail.setBounds(255, 89, 144, 19);
		}
		return textFieldEmail;
	}
	private JLabel getLblPasahitza() {
		if (lblPasahitza == null) {
			lblPasahitza = new JLabel("Pasahitza:");
			lblPasahitza.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblPasahitza.setBounds(256, 140, 97, 13);
		}
		return lblPasahitza;
	}
	private JTextField getTextFieldPasahitza() {
		if (textFieldPasahitza == null) {
			textFieldPasahitza = new JTextField();
			textFieldPasahitza.setColumns(10);
			textFieldPasahitza.setBounds(255, 162, 144, 19);
		}
		return textFieldPasahitza;
	}
	private JButton getBtnAldatu() {
		if (btnAldatu == null) {
			btnAldatu = new JButton("Aldatu");
			btnAldatu.setBounds(168, 214, 97, 28);
		}
		return btnAldatu;
	}
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("Exit");
			btnExit.setBounds(365, 10, 71, 21);
		}
		return btnExit;
	}
	
	private DatuakAldatuKontroladorea getSH() {
		if (kontroladorea == null) {
			kontroladorea = new DatuakAldatuKontroladorea ();
		}
		return kontroladorea;
	}
	
	//---------------------------------------KONTROLADOREA---------------------------------------
	private class DatuakAldatuKontroladorea implements ActionListener{
		
		public DatuakAldatuKontroladorea() {}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
}
