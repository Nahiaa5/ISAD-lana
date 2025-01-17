package Bista;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import Kontroladorea.GestoreErabiltzaile;
import Kontroladorea.GestoreNagusia;
import java.util.Observer;
import java.util.Observable;
import Eredua.*;

public class SaioaHasi extends JFrame implements Observer {

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
		GestoreNagusia.getGN().addObserver(this);
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
			btnSaioaHasi.addActionListener(getSH());
		}
		return btnSaioaHasi;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof String) {
			String mezua = (String) arg1;
			if(mezua.equals("Txarto")) {
				JOptionPane.showMessageDialog(SaioaHasi.this, "Ez da aurkitu erabiltzailea.", "Ez da aurkitu erabiltzailea.", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(arg1 instanceof Erabiltzaile) {
			Erabiltzaile e = (Erabiltzaile) arg1;
			pantailakAukeratu(e);
		}
	}
	
	public void pantailakAukeratu(Erabiltzaile e) {
		if(e.getAdmin() == 0) {
			ErabiltzailePN E = new ErabiltzailePN();
			E.SetNAN(textFieldNan.getText());
			E.setVisible(true);
			setVisible(false);
		}
		else {
			AdminPN A = new AdminPN();
			A.setName(textFieldNan.getText());
			A.setVisible(true);
			setVisible(false);
		}
		
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
				Erabiltzaile a = GestoreErabiltzaile.getGE().erabiltzaileaBilatu(textFieldNan.getText(), textFieldPas.getText());
				if (a != null) {
					pantailakAukeratu(a);
				} else {
					System.out.println("Erabiltzailea edo Pasahitza ez dira zuzena");
				}
			}
		}
	}
}
