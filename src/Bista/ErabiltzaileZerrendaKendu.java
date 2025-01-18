package Bista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Eredua.Erabiltzaile;
import Eredua.FilmZerrenda;
import Kontroladorea.GestoreErabiltzaile;
import Kontroladorea.GestoreZerrenda;

public class ErabiltzaileZerrendaKendu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel titulua;
	private JPanel panel;
	private JPanel panel_1;
	private Controller controller;
	private String NAN;
	private JButton exit;
	private JLabel lblNewLabel;

	/**
	 * Create the frame.
	 */
	public ErabiltzaileZerrendaKendu(String NAN) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		String izena = GestoreErabiltzaile.getGE().erabiltzaileaBilatuNAN(NAN).getIzena();
		titulua = new JLabel(izena +" dituen Zerrendak");
		titulua.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(titulua, BorderLayout.NORTH);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		exit = new JButton("EXIT");
		exit.addActionListener(getCont());
		exit.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_1.add(exit);
		
		lblNewLabel = new JLabel("Aukeratu ezabatu nahi duzun zerrenda");
		panel_1.add(lblNewLabel);
		
		this.NAN = NAN;
		getZerrendak(NAN);
	}
	
	private void getZerrendak(String NAN) {
		GestoreErabiltzaile GE = GestoreErabiltzaile.getGE();
		Erabiltzaile e = GE.erabiltzaileaBilatuNAN(NAN);
		for (FilmZerrenda f : e.getZerrendak()) {
			JButton button = new JButton(f.getIzena());
	        panel.add(button);
	        button.setAlignmentX(Component.CENTER_ALIGNMENT);
	        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
	        button.addActionListener(getCont());
	        revalidate();
	        repaint();
		}
		
	}

	public Controller getCont() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}
	
	public class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(exit)) {
				
				new ErabiltzailePN();
				setVisible(false);
				
			} else {
				String zerrendarenIzena = ((JButton) e.getSource()).getText();
				Erabiltzaile erab = GestoreErabiltzaile.getGE().erabiltzaileaBilatuNAN(NAN);
				int ID = erab.bilatuZerrendaID(zerrendarenIzena);
				if (ID != -1) {
					GestoreZerrenda.getnZZ().ezabatuZerrenda(ID, NAN);
				}
				new ErabiltzaileZerrendak(NAN).setVisible(true);;
            	setVisible(false);
			}
		}
	}
}
