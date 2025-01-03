package Bista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import Eredua.KatalogoZabalduaKargatu;
import Kontroladorea.GestoreZerrenda;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.Font;

public class FilmakSartuZerrenda extends JFrame {

	public static FilmakSartuZerrenda nFSZ;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel izena;
	private JPanel panel;
	private Controller controller;
	private JPanel panel_1;
	private JButton gehitu;
	private JButton sortu;
	private int ID;
	private List<JButton> pelikulenBotoiak;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilmakSartuZerrenda frame = new FilmakSartuZerrenda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static FilmakSartuZerrenda getFSZ(){
		if(nFSZ==null) {
			nFSZ=new FilmakSartuZerrenda();
		}
		return nFSZ;
	}
	
	public void setID (int ID) {
		this.ID = ID;
	}
	
	public void setIzena(String izena) {
		this.izena.setText(izena);
	}
	
	public void sartuFilma(String izena) {
        JButton button = new JButton(izena);
        panel.add(button);
        pelikulenBotoiak.add(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
        button.addActionListener(getCont());
        revalidate();
        repaint();
    }
	
	public void print()
	{
		for (JButton botoia : pelikulenBotoiak) {
			System.out.println(botoia.getText());
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
	
	/**
	 * Create the frame.
	 */
	public FilmakSartuZerrenda() {
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
		
		sortu = new JButton("Sortu Zerrenda");
		sortu.addActionListener(getCont());
		sortu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_1.add(sortu);
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
			if (e.getSource().equals(gehitu) || e.getSource().equals(sortu)) {
				if (e.getSource().equals(gehitu)) {
					FilmKatalogoZabaldua FKZ = FilmKatalogoZabaldua.getPN();
					FKZ.setFlag(2);
	                FKZ.setVisible(true);
	                dispose();
				} else if (e.getSource().equals(sortu)){
					ZerrendaPertsonalizatuaB ZPB = ZerrendaPertsonalizatuaB.getnZP();
					ZPB.setID(ID);
					ZPB.setVisible(true);
					dispose();
					
				}
			} else {
				JButton botoia = (JButton) e.getSource();
				String datuak = botoia.getText();
				JSONObject xehetasunak = KatalogoZabalduaKargatu.getnZK().xehetasunakBilatu(datuak);
				XehetasunakZ X = new XehetasunakZ(xehetasunak);
				X.setVisible(true);
				X.setflag(0);
				dispose();
			}
		}
	}
}

