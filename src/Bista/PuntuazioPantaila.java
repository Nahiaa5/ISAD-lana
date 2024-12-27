package Bista;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.time.LocalDate;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Eredua.DB_kudeatzailea;
import Eredua.Film;
import Eredua.Puntuazioa;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import java.awt.BorderLayout;
import javax.swing.JButton;

public class PuntuazioPantaila {
	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Film film;
	private String NAN;
	private DB_kudeatzailea dbK;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PuntuazioPantaila window = new PuntuazioPantaila(film, erabiltzaileNAN);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public PuntuazioPantaila(Film film, String NAN, DB_kudeatzailea dbK) {
		this.film = film;
		this.NAN = NAN;
		this.dbK=dbK;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setEnabled(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Puntuazioa", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("1");
		buttonGroup.add(rdbtnNewRadioButton_1);
		panel.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("2");
		buttonGroup.add(rdbtnNewRadioButton_2);
		panel.add(rdbtnNewRadioButton_2);
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("3");
		buttonGroup.add(rdbtnNewRadioButton_3);
		panel.add(rdbtnNewRadioButton_3);
		
		JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("4");
		buttonGroup.add(rdbtnNewRadioButton_4);
		panel.add(rdbtnNewRadioButton_4);
		
		JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("5");
		buttonGroup.add(rdbtnNewRadioButton_5);
		panel.add(rdbtnNewRadioButton_5);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Iruzkina", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JTextArea textAreaComentario = new JTextArea(5, 30); 
		JScrollPane scrollPane = new JScrollPane(textAreaComentario);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		JButton btnGorde = new JButton("Gorde");
		panel_1.add(btnGorde, BorderLayout.SOUTH);
		btnGorde.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int puntuazioa = 0;
				if (rdbtnNewRadioButton_1.isSelected()) puntuazioa = 1;
				else if (rdbtnNewRadioButton_2.isSelected()) puntuazioa = 2;
				else if (rdbtnNewRadioButton_3.isSelected()) puntuazioa = 3;
				else if (rdbtnNewRadioButton_4.isSelected()) puntuazioa = 4;
				else if (rdbtnNewRadioButton_5.isSelected()) puntuazioa = 5;
				
				if(puntuazioa == 0) {
					JOptionPane.showMessageDialog(frame, "Aukeratu puntuazio bat.", "Errorea", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String iruzkina = textAreaComentario.getText();
				if(iruzkina.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Idatzi iruzkina bat.", "Errorea", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Puntuazioa puntu = new Puntuazioa(NAN, film.getFilmID(),puntuazioa, iruzkina, LocalDate.now());
				
				if(dbK.puntuazioaExistitzenDa(NAN, film.getFilmID())) {
					dbK.eguneratuPuntuazioa(puntu);
				}else {
					dbK.gordePuntuazioa(puntu);
				}
				film.eguneratuPuntuBb(dbK);
				film.gordeIruzkinak(dbK);
				
				JOptionPane.showMessageDialog(frame, "Puntuazioa gorde da.", "Arrakasta", JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}
		});
		panel_1.add(btnGorde, BorderLayout.SOUTH);
	}

	public JFrame getFrame() {
		return frame;
	}
}
