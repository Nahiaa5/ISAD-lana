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
	private Controller controller;
	private JButton btnDatuakAldatu;

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
        contentPane.add(getBtnDatuakAldatu());

        controller = new Controller();
        setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public JButton getBtnKN() {
		if (btnKN == null) {
            btnKN = new JButton("Katalogoa ikusi");
            btnKN.setBounds(52, 155, 150, 30);
            btnKN.addActionListener(getController());
        }
		return btnKN;
	}
	
	public JButton getZabaldua() {
		if (zabaldua == null) {
            zabaldua = new JButton("Filma eskatu");
            zabaldua.setBounds(250, 50, 150, 30);
            zabaldua.addActionListener(getController());
        }
		return zabaldua;
	}
	private JButton getBtnDatuakAldatu() {
		if (btnDatuakAldatu == null) {
			btnDatuakAldatu = new JButton("Datuak aldatu");
			btnDatuakAldatu.setBounds(52, 50, 150, 30);
			btnDatuakAldatu.addActionListener(getController());
		}
		return btnDatuakAldatu;
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
            if (e.getSource().equals(btnKN)) {
                new KatalogoNagusiaB();
            }

            if (e.getSource().equals(zabaldua)) {
                FilmKatalogoZabaldua FKZ = FilmKatalogoZabaldua.getPN();
                FKZ.setVisible(true);
            }
            
            if (e.getSource().equals(btnDatuakAldatu)) {
            	new DatuakAldatu();
            	setVisible(false);
            }
        }
    }
}
