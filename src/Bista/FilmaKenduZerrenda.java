package Bista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import Eredua.FilmZerrenda;
import Kontroladorea.GestoreZerrenda;

public class FilmaKenduZerrenda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel izena;
	private JPanel panel;
	private Controller controller;
	private JPanel panel_1;
	private GestoreZerrenda GZ = GestoreZerrenda.getnZZ();
	private int ID;
	private List<JButton> pelikulenBotoiak;
	private JLabel lblNewLabel;

	/**
	 * Create the frame.
	 */
	
	public FilmaKenduZerrenda(int ID) {
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
		
		lblNewLabel = new JLabel("Aukeratu kendu nahi duzun filma");
		panel_1.add(lblNewLabel);
		
		this.ID = ID;
		filmakSartu(ID);
	}
		
	private void filmakSartu(int id) {
		FilmZerrenda z = GZ.bilatuZerrenda(id);
		izena.setText(z.getIzena());
		ArrayList<String> izenak = z.filmenIzenak();
		for (String izena : izenak) {
			JButton button = new JButton(izena);
	        panel.add(button);
	        pelikulenBotoiak.add(button);
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
		
		public void actionPerformed(ActionEvent e) {
			JButton botoia = (JButton) e.getSource();
			String titulua = botoia.getText();
			GestoreZerrenda.getnZZ().kenduFilmaZerrendaBaten(ID, titulua);
			ZerrendaPertsonalizatuaB ZPB = new ZerrendaPertsonalizatuaB(ID);
			ZPB.setVisible(true);
			dispose();
		}
	}
}
