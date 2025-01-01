package Bista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import Eredua.FilmZerrenda;
import Eredua.KatalogoZabalduaKargatu;
import Kontroladorea.GestoreZerrenda;

public class ZerrendaPertsonalizatuaB extends JFrame {

	public static ZerrendaPertsonalizatuaB nZP;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel izena;
	private JPanel panel;
	private Controller controller;
	private JPanel panel_1;
	private JButton gehitu;
	private JButton kendu;
	private JButton xehetasunak;
	private GestoreZerrenda GZ = GestoreZerrenda.getnZZ();
	private int ID;
	private List<JButton> pelikulenBotoiak;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ZerrendaPertsonalizatuaB frame = new ZerrendaPertsonalizatuaB();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static ZerrendaPertsonalizatuaB getnZP(){
		if(nZP==null) {
			nZP=new ZerrendaPertsonalizatuaB();
		}
		return nZP;
	}
	/**
	 * Create the frame.
	 */
	public ZerrendaPertsonalizatuaB() {
		pelikulenBotoiak = new ArrayList<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		
		gehitu = new JButton("Beste film bat Gehitu");
		gehitu.addActionListener(getCont());
		panel_1.add(gehitu);
		
		kendu = new JButton("Film bat kendu");
		kendu.addActionListener(getCont());
		kendu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_1.add(kendu);
		
		xehetasunak = new JButton("Xehetasunak aldatu");
		xehetasunak.addActionListener(getCont());
		xehetasunak.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_1.add(xehetasunak);
		filmakSartu(ID);
	}

	public void setID (int id) {
		this.ID = ID;
	}
	
	private void filmakSartu(int id) {
		ArrayList<String> izenak = GZ.bilatuZerrenda(id).filmenIzenak();
		for (String izena : izenak) {
			JButton button = new JButton();
	        panel.add(button);
	        pelikulenBotoiak.add(button);
	        button.setAlignmentX(Component.CENTER_ALIGNMENT);
	        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
	        button.addActionListener(getCont());
	        revalidate();
	        repaint();
		}
	}
	
	public void kenduFilm(String izena) {
		for (JButton botoia : pelikulenBotoiak) {
			if (botoia.getText().equals(izena)) {
				panel.remove(botoia);
				pelikulenBotoiak.remove(botoia);
			}
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
			if (e.getSource().equals(gehitu) || e.getSource().equals(kendu) || e.getSource().equals(xehetasunak)) {
				if (e.getSource().equals(gehitu)) {
					FilmKatalogoZabaldua FKZ = FilmKatalogoZabaldua.getPN();
					FKZ.setFlag(2);
	                FKZ.setVisible(true);
	                dispose();
				}
				if (e.getSource().equals(xehetasunak)) {
					ZerrendaXehetasunak ZX = ZerrendaXehetasunak.getnZX();
					ZX.setID(ID);
					ZX.setVisible(true);
					dispose();
				}
			} else {
				JButton botoia = (JButton) e.getSource();
				String datuak = botoia.getText();
				JSONObject xehetasunak = KatalogoZabalduaKargatu.getnZK().xehetasunakBilatu(datuak);
				XehetasunakZ X = new XehetasunakZ(xehetasunak);
				X.setVisible(true);
				X.setflag(1);
				dispose();
			}
		}
	}

}
