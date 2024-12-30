package Bista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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

public class Erregistratu extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField textFieldNan;
	private JLabel lblNewLabel_1;
	private JTextField textFieldIzena;
	private JLabel lblNewLabel_1_1;
	private JTextField textFieldAbizena;
	private JLabel lblEmail;
	private JTextField textFieldEmail;
	private JLabel lblPasahitza;
	private JTextField textFieldPasahitza;
	private JButton btnErregistratu;
	private ErregistratuKontroladorea kontroladorea = null;
	private JButton btnExit;

	
	public Erregistratu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNewLabel());
		contentPane.add(getTextFieldNan());
		contentPane.add(getLblNewLabel_1());
		contentPane.add(getTextFieldIzena());
		contentPane.add(getLblNewLabel_1_1());
		contentPane.add(getTextFieldAbizena());
		contentPane.add(getLblEmail());
		contentPane.add(getTextFieldEmail());
		contentPane.add(getLblPasahitza());
		contentPane.add(getTextFieldPasahitza());
		contentPane.add(getBtnErregistratu());
		contentPane.add(getBtnExit());
		setVisible(true);
		GestoreNagusia.getGN().addObserver(this);
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("NAN:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel.setBounds(0, 25, 45, 13);
		}
		return lblNewLabel;
	}
	private JTextField getTextFieldNan() {
		if (textFieldNan == null) {
			textFieldNan = new JTextField();
			textFieldNan.setBounds(0, 48, 186, 19);
			textFieldNan.setColumns(10);
		}
		return textFieldNan;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Izena:");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_1.setBounds(0, 88, 45, 13);
		}
		return lblNewLabel_1;
	}
	private JTextField getTextFieldIzena() {
		if (textFieldIzena == null) {
			textFieldIzena = new JTextField();
			textFieldIzena.setColumns(10);
			textFieldIzena.setBounds(0, 111, 186, 19);
		}
		return textFieldIzena;
	}
	private JLabel getLblNewLabel_1_1() {
		if (lblNewLabel_1_1 == null) {
			lblNewLabel_1_1 = new JLabel("Abizena:");
			lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_1_1.setBounds(0, 147, 45, 13);
		}
		return lblNewLabel_1_1;
	}
	private JTextField getTextFieldAbizena() {
		if (textFieldAbizena == null) {
			textFieldAbizena = new JTextField();
			textFieldAbizena.setColumns(10);
			textFieldAbizena.setBounds(0, 174, 186, 19);
		}
		return textFieldAbizena;
	}
	private JLabel getLblEmail() {
		if (lblEmail == null) {
			lblEmail = new JLabel("Email:");
			lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblEmail.setBounds(240, 88, 45, 13);
		}
		return lblEmail;
	}
	private JTextField getTextFieldEmail() {
		if (textFieldEmail == null) {
			textFieldEmail = new JTextField();
			textFieldEmail.setColumns(10);
			textFieldEmail.setBounds(240, 111, 186, 19);
		}
		return textFieldEmail;
	}
	private JLabel getLblPasahitza() {
		if (lblPasahitza == null) {
			lblPasahitza = new JLabel("Pasahitza:");
			lblPasahitza.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblPasahitza.setBounds(240, 147, 98, 13);
		}
		return lblPasahitza;
	}
	private JTextField getTextFieldPasahitza() {
		if (textFieldPasahitza == null) {
			textFieldPasahitza = new JTextField();
			textFieldPasahitza.setColumns(10);
			textFieldPasahitza.setBounds(240, 174, 186, 19);
		}
		return textFieldPasahitza;
	}
	private JButton getBtnErregistratu() {
		if (btnErregistratu == null) {
			btnErregistratu = new JButton("Erregistratu");
			btnErregistratu.setBounds(157, 226, 113, 27);
			btnErregistratu.addActionListener(getE());
		}
		return btnErregistratu;
	}
	
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("Exit");
			btnExit.setBounds(356, 10, 70, 21);
			btnExit.addActionListener(getE());
			
		}
		return btnExit;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Boolean) {
			boolean ondo = (boolean) arg1;
			if (ondo == true) {
				JOptionPane.showMessageDialog(Erregistratu.this, "Erabiltzailea sortu da.", "Erabiltzailea sortu da.", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(Erregistratu.this, "Errore bat gertatu da.", "Errore bat gertatu da.", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private ErregistratuKontroladorea getE() {
		if (kontroladorea == null) {
			kontroladorea = new ErregistratuKontroladorea ();
		}
		return kontroladorea;
	}
	
	//---------------------------------------KONTROLADOREA---------------------------------------
	private class ErregistratuKontroladorea implements ActionListener{
		
		public ErregistratuKontroladorea() {}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource().equals(btnErregistratu)) {
				GestoreNagusia.getGN().ErabiltzaileBerriaSartu(textFieldNan.getText(), textFieldIzena.getText(), textFieldAbizena.getText(), textFieldEmail.getText(), textFieldPasahitza.getText());
			}
			else if (e.getSource().equals(btnExit)) {
				new PantailaNagusia();
				setVisible(false);
			}
		}
	}
}
