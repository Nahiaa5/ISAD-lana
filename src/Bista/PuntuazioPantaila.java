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

public class PuntuazioPantaila extends JFrame{
	private static final long serialVersionUID = 1L; 
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Film film;
	private String NAN;
	private DB_kudeatzailea dbK;
	private Controller controller;

	/**
	 * Create the application.
	 */
	public PuntuazioPantaila(Film film, String NAN, DB_kudeatzailea dbK) {
		this.film = film;
		this.NAN = NAN;
		this.dbK = dbK;
		initialize();
		controller = new Controller();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		getContentPane().setEnabled(false);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Puntuazioa", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//Botoiak 1-5
		for(int i=1;i<=5; i++) {
			JRadioButton radioButton = new JRadioButton(String.valueOf(i));
            radioButton.setActionCommand(String.valueOf(i)); // Establecer el comando de acción
            buttonGroup.add(radioButton);
            panel.add(radioButton);
            radioButton.addActionListener(getController());
		}
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Iruzkina", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JTextArea textAreaComentario = new JTextArea(5, 30); 
		JScrollPane scrollPane = new JScrollPane(textAreaComentario);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		JButton btnGorde = new JButton("Gorde");
		panel_1.add(btnGorde, BorderLayout.SOUTH);
		btnGorde.addActionListener(getController());
	}

	
	private Controller getController() {
        if (controller == null) {
            controller = new Controller();  
        }
        return controller;
    }

    private class Controller implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton && ((JButton) e.getSource()).getText().equals("Gorde")) {
                int puntuazioa = 0;

                if (buttonGroup.getSelection() != null) {
                    puntuazioa = Integer.parseInt(buttonGroup.getSelection().getActionCommand());
                }

                if (puntuazioa == 0) {
                    JOptionPane.showMessageDialog(PuntuazioPantaila.this, "Aukeratu puntuazio bat.", "Errorea", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                JPanel panelIruzkina = (JPanel) getContentPane().getComponent(1); 
                JScrollPane scrollPane = (JScrollPane) panelIruzkina.getComponent(0); 
                JTextArea textAreaComentario = (JTextArea) scrollPane.getViewport().getView();

                String iruzkina = textAreaComentario.getText();

                if (iruzkina.isEmpty()) {
                    JOptionPane.showMessageDialog(PuntuazioPantaila.this, "Idatzi iruzkina bat.", "Errorea", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Puntuazioa puntu = new Puntuazioa(NAN, film.getFilmID(), puntuazioa, iruzkina, LocalDate.now());

                if (dbK.puntuazioaExistitzenDa(NAN, film.getFilmID())) {
                    dbK.eguneratuPuntuazioa(puntu);
                } else {
                    dbK.gordePuntuazioa(puntu);
                }

                film.eguneratuPuntuBb(dbK);
                film.gordeIruzkinak(dbK);

                JOptionPane.showMessageDialog(PuntuazioPantaila.this, "Puntuazioa gorde da.", "Arrakasta", JOptionPane.INFORMATION_MESSAGE);
                dispose(); 
            }
        }
    }
}
