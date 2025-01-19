package Bista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import Eredua.FilmZerrenda;
import Kontroladorea.GestoreKatalogoZabaldua;
import Kontroladorea.GestoreZerrenda;

public class ZerrendaIkusi extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel izena;
	private JPanel panel;
	private Controller controller;
	private JPanel panel_1;
	private JButton btnExit;
	private GestoreZerrenda GZ = GestoreZerrenda.getnZZ();
	private int ID;
	private JPanel panel_2;

	/**
	 * Create the frame.
	 */
	public ZerrendaIkusi(int ID) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		izena = new JLabel("Titulua");
		izena.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(izena, BorderLayout.NORTH);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnExit = new JButton("EXIT");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExit.addActionListener(getCont());
		panel_1.add(btnExit);
		
		panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(null);
		
		this.ID = ID;
		filmakSartu(ID);
	}
	
	private void filmakSartu(int id) {
		FilmZerrenda z = GZ.bilatuZerrenda(id);
		izena.setText(z.getIzena());
		ArrayList<String> izenak = z.filmenIzenUrte();
		for (String izena : izenak) {
			JButton button = new JButton(izena);
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
			if(e.getSource().equals(btnExit)) {
				ZerrendaKatalogoa k = new ZerrendaKatalogoa();
				k.setVisible(true);
				setVisible(false);
            } else {
            	JButton botoia = (JButton) e.getSource();
				String datuak = botoia.getText();
				JSONObject xehetasunak = GestoreKatalogoZabaldua.getnZK().xehetasunakBilatu(datuak);
				XehetasunakZ X = new XehetasunakZ(xehetasunak);
				X.setVisible(true);
				X.setID(ID);
				X.setFlag(1);
				setVisible(false);
            }
		}
	}
}
