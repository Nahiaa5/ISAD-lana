package Bista;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.util.Observer;
import java.util.Observable;

public class PantailaNagusia extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private JButton btnSaioaHasi;
	private JButton btnErregistratu;
	private PantailaNagusiaKontroladorea kontroladorea = null;

	/**
	 * Create the panel.
	 */
	public PantailaNagusia() {
		setLayout(null);
		add(getBtnSaioaHasi());
		add(getBtnErregistratu());

	}
	private JButton getBtnSaioaHasi() {
		if (btnSaioaHasi == null) {
			btnSaioaHasi = new JButton("Saioa hasi");
			btnSaioaHasi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnSaioaHasi.setBounds(118, 92, 217, 34);
		}
		return btnSaioaHasi;
	}
	private JButton getBtnErregistratu() {
		if (btnErregistratu == null) {
			btnErregistratu = new JButton("Erregistratu");
			btnErregistratu.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnErregistratu.setBounds(118, 163, 217, 34);
		}
		return btnErregistratu;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		
	}
	
	
	//---------------------------------------KONTROLADOREA---------------------------------------
	private class PantailaNagusiaKontroladorea implements MouseListener{
		
		public PantailaNagusiaKontroladorea() {
		}
		
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
	}
}
