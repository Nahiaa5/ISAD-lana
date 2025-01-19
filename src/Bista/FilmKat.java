package Bista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Bista.ZerrendaKatalogoa.Controller;
import Eredua.Film;
import Eredua.FilmZerrenda;
import Kontroladorea.GestoreFilm;
import Kontroladorea.GestoreNagusia;
import Kontroladorea.GestoreZerrenda;

public class FilmKat extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	GestoreZerrenda GZ = GestoreZerrenda.getnZZ();
	private JPanel panel1;
	private Controller controller;
	private JTextField bTextField;
	private JButton bJButton;
	private JButton eJButton;
	private JScrollPane scrollPane;
	private int ID;
	private int flag;
	private ArrayList<JButton> botoiak;

	/**
	 * Create the frame.
	 */
	public FilmKat() {
		botoiak= new ArrayList<JButton>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panel1 = new JPanel();
    	panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		scrollPane = new JScrollPane(panel1);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		panel.add(getBilatuTextField());
		panel.add(getBilatuJButton());
		panel.add(getExitJButton());
		
	}
	
	public void setID (int ID) {
		this.ID = ID;
	}
	
	public void setFlag (int flag) {
		this.flag = flag;
	}
	
	private JTextField getBilatuTextField() {
		if (bTextField == null) {
			bTextField = new JTextField();
			bTextField.setColumns(25);
			bTextField.addActionListener(getCont());
		}
		return bTextField;
	}
	
	private JButton getBilatuJButton() {
		if (bJButton == null) {
			bJButton = new JButton("Bilatu");
			bJButton.addActionListener(getCont());
		}
		return bJButton;
	}
	
	private JButton getExitJButton() {
		if (eJButton == null) {
			eJButton = new JButton("Exit");
			eJButton.addActionListener(getCont());
		}
		return eJButton;
	}
	
	private void getZerrendak(ArrayList<Film> z) {
		for (Film f : z) {
			boolean badago = false;
			String izena = f.getIzenburua() ;
			String urtea = f.getUrtea();
			for (JButton b : botoiak) {
				if (b.getText().equals(izena + " (" + urtea + ")")) {
					badago = true;
				}
			}
			if (!badago) {
				JButton button = new JButton(izena + " (" + urtea + ")");
				botoiak.add(button);
		        panel1.add(button);
		        button.setAlignmentX(Component.CENTER_ALIGNMENT);
		        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
		        button.addActionListener(getCont());
		        revalidate();
		        repaint();
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
			
			if (e.getSource().equals(bJButton) || e.getSource().equals(bTextField)) {
				getZerrendak(GestoreFilm.getKN().bilatuFilmakKat(getBilatuTextField().getText()));
			}

			else if (e.getSource().equals(eJButton)){
				new ErabiltzailePN();
				setVisible(false);
			}
			else {
				JButton botoia = (JButton) e.getSource();
				String datuak = botoia.getText();
				switch (flag) {
				
					case 0:
						GestoreNagusia.getGN().KZXehetasunakErakutsi(datuak);
						break;
					case 1:
						FilmakSartuZerrenda FSZ = new FilmakSartuZerrenda(ID);
						String izena = GZ.bilatuZerrenda(ID).getIzena();
						FSZ.setIzena(izena);
						Boolean b = GZ.sartuFilmaZerrendaBaten(ID, datuak);
						if (b) {
							FSZ.sartuFilma(datuak);
						}
						FSZ.setID(ID);
						FSZ.setVisible(true);
						setVisible(false);
						break;
					case 2:
						GZ.sartuFilmaZerrendaBaten(ID, datuak);
						ZerrendaPertsonalizatuaB ZPB = new ZerrendaPertsonalizatuaB(ID);
						ZPB.setVisible(true);
						dispose();
						break;
						
				}
			}
		}
	}
}