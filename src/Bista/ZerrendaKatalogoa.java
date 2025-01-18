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

import Eredua.FilmZerrenda;
import Kontroladorea.GestoreZerrenda;

public class ZerrendaKatalogoa extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel1;
	private Controller controller;
	private JTextField bTextField;
	private JButton bJButton;
	private JButton eJButton;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	public ZerrendaKatalogoa() {
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
	
	private void getZerrendak(ArrayList<FilmZerrenda> z) {
		for (FilmZerrenda f : z) {
			String izena = f.getIzena() +"   ID:" + f.getID();
			JButton button = new JButton(izena);
	        panel1.add(button);
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
			
			if (e.getSource().equals(bJButton) || e.getSource().equals(bTextField)) {
				getZerrendak(GestoreZerrenda.getnZZ().bilatuZerrendakKat(getBilatuTextField().getText()));
			}
			else if (e.getSource().equals(eJButton)){
				new ErabiltzailePN();
				setVisible(false);
			} else {
				JButton botoia = (JButton) e.getSource();
				String izena = botoia.getText();
				String[] partes = izena.split("ID:");
				int id = Integer.parseInt(partes[1].trim());
				ZerrendaIkusi z = new ZerrendaIkusi(id);
				z.setVisible(true);
				setVisible(false);
			}
		}
	}
}