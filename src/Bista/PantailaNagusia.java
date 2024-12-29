package Bista;

import Kontroladorea.GestoreNagusia;
import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Kontroladorea.KatalogoNagusia;

import javax.swing.JButton;

public class PantailaNagusia extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSaioaHasi;
	private JButton btnErregistratu;
	private Controller Controller = null;

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
			btnSaioaHasi.addActionListener(getController());
		}
		return btnSaioaHasi;
	}
	private JButton getBtnErregistratu() {
		if (btnErregistratu == null) {
			btnErregistratu = new JButton("Erregistratu");
			btnErregistratu.addActionListener(getController());
		}
		return btnErregistratu;
	}
	
	private Controller getController() {
		if (Controller == null) {
			Controller = new Controller();
		}
		return Controller;
	}
	
	//---------------------------------------KONTROLADOREA---------------------------------------
	private class Controller implements MouseListener, ActionListener{
		
		public Controller() {}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {}
		
		@Override
		public void mouseReleased(MouseEvent e) {}
		
		@Override
		public void mouseEntered(MouseEvent e) {}
		
		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource().equals(btnSaioaHasi)) {
				new ErabiltzailePN();
			}
		}
	}
}