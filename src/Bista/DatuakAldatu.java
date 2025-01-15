package Bista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Kontroladorea.GestoreErabiltzaile;
import Kontroladorea.GestoreNagusia;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.util.Observer;
import java.util.Observable;
import Eredua.*;

public class DatuakAldatu extends JFrame implements Observer {

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
	private String nanErabiltzailea;


	public DatuakAldatu(String pNan) {
		this.nanErabiltzailea = pNan;
		
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
		GestoreNagusia.getGN().addObserver(this);
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
			btnAldatu.addActionListener(getSH());
		}
		return btnAldatu;
	}
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("Exit");
			btnExit.setBounds(365, 10, 71, 21);
			btnExit.addActionListener(getSH());
		}
		return btnExit;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof String) {
			String mezua = (String) arg1;
			if(mezua.equals("Hutsik")) {
				JOptionPane.showMessageDialog(DatuakAldatu.this, "Errorea gertatu da.", "Errorea gertatu da.", JOptionPane.INFORMATION_MESSAGE);
			}
			else if (mezua.equals("Sartuta")) {
				JOptionPane.showMessageDialog(DatuakAldatu.this, "Datuak aldatu dira.", "Datuak aldatu dira.", JOptionPane.INFORMATION_MESSAGE);
			}
		}
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
			if (e.getSource().equals(btnAldatu)) {
				GestoreNagusia.getGN().erabiltzaileDatuakAldatu(nanErabiltzailea, textFieldIzena.getText(), textFieldAbizena.getText(), textFieldEmail.getText(), textFieldPasahitza.getText());
			}
			if (e.getSource().equals(btnExit)) {
				Erabiltzaile erab = GestoreErabiltzaile.getGE().getErabiltzaileByNAN(GestoreErabiltzaile.getGE().getSaioaNan());
				if(erab.getAdmin() == 1) {
					new ErabKudeatu();
				}
				else {
					new ErabiltzailePN();
				}
				dispose();
			}
			
		}
	}
}
