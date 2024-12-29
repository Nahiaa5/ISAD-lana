package Bista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ErabiltzailePN extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton zabaldua;
	private JButton btnKN;
	private Controler controler;

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
        setVisible(true);
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
                new KatalogoNagusiaB();
            }

            if (e.getSource().equals(zabaldua)) {
                FilmKatalogoZabaldua FKZ = FilmKatalogoZabaldua.getPN();
                FKZ.setVisible(true);
            }
        }
    }
}
