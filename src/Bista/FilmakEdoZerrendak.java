package Bista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilmakEdoZerrendak extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JButton btnFilmak;
	private JButton btnZerrendak;
	private Controller controller;

	/**
	 * Create the frame.
	 */
	public FilmakEdoZerrendak() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Zer bilatu nahi duzu?");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(126, 11, 184, 33);
		contentPane.add(lblNewLabel);
		
		btnFilmak = new JButton("Filmak");
		btnFilmak.setBounds(161, 81, 103, 33);
		btnFilmak.addActionListener(getController());
		contentPane.add(btnFilmak);
		
		btnZerrendak = new JButton("Zerrendak");
		btnZerrendak.setBounds(161, 148, 103, 33);
		btnZerrendak.addActionListener(getController());
		contentPane.add(btnZerrendak);
		setVisible(true);
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
            if (e.getSource().equals(btnFilmak)) {
                new KatalogoNagusiaB();
                setVisible(false);
            }

            if (e.getSource().equals(btnZerrendak)) {
                ZerrendaKatalogoa z = new ZerrendaKatalogoa();
                z.setVisible(true);
                setVisible(false);
            }
            
        }
    }
}
