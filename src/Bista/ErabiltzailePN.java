package Bista;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Eredua.DB_kudeatzailea;
import Eredua.KatalogoNagusia;

public class ErabiltzailePN {

	private JFrame frame;
	private JButton zabaldua;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErabiltzailePN window = new ErabiltzailePN();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ErabiltzailePN() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel=new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JButton btnKN=new JButton ("Katalogoa ikusi");
		btnKN.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				DB_kudeatzailea dbK = new DB_kudeatzailea();
				KatalogoNagusia katalogo=KatalogoNagusia.getKN();
				KatalogoNagusiaB KN=new KatalogoNagusiaB(katalogo, dbK);
				KN.setVisible(true);
			}
		});
		panel.add(btnKN);;
		panel.add(getZabaldua());
	}
	
	public JButton getZabaldua() {
		if(zabaldua == null) {
			zabaldua = new JButton ("Filma eskatu");
			zabaldua.addActionListener(new ActionListener() {
				@Override 
				public void actionPerformed(ActionEvent e) {
					FilmKatalogoZabaldua FKZ = FilmKatalogoZabaldua.getPN();
					FKZ.setVisible(true);
				}
			});
		}
		return zabaldua;
	}
}