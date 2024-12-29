package Bista;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Eredua.DB_kudeatzailea;
import Kontroladorea.KatalogoNagusia;

public class ErabiltzailePN extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton zabaldua;
	private JButton btnKN;
	private Controler controler;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErabiltzailePN frame = new ErabiltzailePN();
					frame.setVisible(true);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        contentPane.add(getBtnKN());
        contentPane.add(getZabaldua());

        controler = new Controler();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public JButton getBtnKN() {
		if (btnKN == null) {
            btnKN = new JButton("Katalogoa ikusi");
            btnKN.setBounds(50, 50, 150, 30);
            btnKN.addActionListener(getControler());
        }
		return btnKN;
	}
	
	public JButton getZabaldua() {
		if (zabaldua == null) {
            zabaldua = new JButton("Filma eskatu");
            zabaldua.setBounds(250, 50, 150, 30);
            zabaldua.addActionListener(getControler());
        }
		return zabaldua;
	}

	private Controler getControler() {
		if (controler == null) {
			controler = new Controler();
        }
        return controler;
	}
	
	private class Controler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnKN)) {
                DB_kudeatzailea dbK = new DB_kudeatzailea();
                KatalogoNagusia katalogo = KatalogoNagusia.getKN();
                KatalogoNagusiaB KN = new KatalogoNagusiaB(katalogo, dbK);
                KN.setVisible(true);
            }

            if (e.getSource().equals(zabaldua)) {
                FilmKatalogoZabaldua FKZ = FilmKatalogoZabaldua.getPN();
                FKZ.setVisible(true);
            }
        }
    }
}
