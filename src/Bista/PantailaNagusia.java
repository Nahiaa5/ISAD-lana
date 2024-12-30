package Bista;

import Kontroladorea.GestoreNagusia;
import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Kontroladorea.GestoreFilm;
import javax.swing.JButton;
import java.util.Observer;
import java.util.Observable;


public class PantailaNagusia extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSaioaHasi;
	private JButton btnErregistratu;
	private PantailaNagusiaKontroladorea kontroladorea = null;

	/**
	 * Create the frame.
	 */
	public PantailaNagusia() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();
		GestoreNagusia.getGN().datuakKargatu();
		
	}
	
	private void initialize() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.add(getBtnSaioaHasi());
		contentPane.add(getBtnErregistratu());
		setVisible(true);
	}

	private JButton getBtnSaioaHasi() {
		if (btnSaioaHasi == null) {
			btnSaioaHasi = new JButton("Saioa hasi");
			btnSaioaHasi.addActionListener(getPN());
		}
		return btnSaioaHasi;
	}
	private JButton getBtnErregistratu() {
		if (btnErregistratu == null) {
			btnErregistratu = new JButton("Erregistratu");
			btnErregistratu.addActionListener(getPN());
		}
		return btnErregistratu;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		
	}
	
	private PantailaNagusiaKontroladorea getPN() {
		if (kontroladorea == null) {
			kontroladorea = new PantailaNagusiaKontroladorea ();
		}
		return kontroladorea;
	}
	
	//---------------------------------------KONTROLADOREA---------------------------------------
	private class PantailaNagusiaKontroladorea implements ActionListener{
		
		public PantailaNagusiaKontroladorea() {}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource().equals(btnSaioaHasi)) {
				new SaioaHasi();
				setVisible(false);
			}
			else if(e.getSource().equals(btnErregistratu)) {
				new Erregistratu();
				setVisible(false);
			}
		}
	}
}