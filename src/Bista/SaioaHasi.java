package Bista;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import Kontroladorea.GestoreNagusia;

public class SaioaHasi extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNan;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField textFieldPas;
	private JButton btnSaioaHasi;
	private SaioaHasiKontroladorea kontroladorea = null;


	public SaioaHasi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTextFieldNan());
		contentPane.add(getLblNewLabel());
		contentPane.add(getLblNewLabel_1());
		contentPane.add(getTextFieldPas());
		contentPane.add(getBtnSaioaHasi());
		setVisible(true);
	}
	private JTextField getTextFieldNan() {
		if (textFieldNan == null) {
			textFieldNan = new JTextField();
			textFieldNan.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldNan.setBounds(123, 65, 163, 26);
			textFieldNan.setColumns(10);
		}
		return textFieldNan;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Erabiltzailea");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(156, 35, 94, 20);
		}
		return lblNewLabel;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Pasahitza");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_1.setBounds(156, 117, 98, 20);
		}
		return lblNewLabel_1;
	}
	private JTextField getTextFieldPas() {
		if (textFieldPas == null) {
			textFieldPas = new JTextField();
			textFieldPas.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldPas.setColumns(10);
			textFieldPas.setBounds(123, 147, 163, 26);
		}
		return textFieldPas;
	}
	private JButton getBtnSaioaHasi() {
		if (btnSaioaHasi == null) {
			btnSaioaHasi = new JButton("Saioa Hasi");
			btnSaioaHasi.setBounds(156, 213, 103, 26);
		}
		return btnSaioaHasi;
	}
	
	private SaioaHasiKontroladorea getSH() {
		if (kontroladorea == null) {
			kontroladorea = new SaioaHasiKontroladorea ();
		}
		return kontroladorea;
	}
	
	//---------------------------------------KONTROLADOREA---------------------------------------
	private class SaioaHasiKontroladorea implements ActionListener{
		
		public SaioaHasiKontroladorea() {}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource().equals(btnSaioaHasi)) {
				GestoreNagusia.getGN().getErabiltzaile();
			}
		}
	}
}
