package Bista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Bista.FilmKatalogoZabaldua.Controller;
import Eredua.KatalogoZabalduaKargatu;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.Font;

public class FilmakSartuZerrenda extends JFrame {

	public static FilmakSartuZerrenda nFSZ;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JPanel panel;
	private Controller controller;
	private JPanel panel_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilmakSartuZerrenda frame = new FilmakSartuZerrenda("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static FilmakSartuZerrenda getFSZ(String izena){
		if(nFSZ==null) {
			nFSZ=new FilmakSartuZerrenda(izena);
		}
		return nFSZ;
	}
	
	public void sartuFilma(String datuak) {
        JButton button = new JButton(datuak);
        panel.add(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
        button.addActionListener(getCont());
        revalidate();
        repaint();
    }

	/**
	 * Create the frame.
	 */
	public FilmakSartuZerrenda(String izena) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		lblNewLabel = new JLabel(izena);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnNewButton = new JButton("Beste film bat Gehitu");
		panel_1.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Sortu Zerrenda");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel_1.add(btnNewButton_1);
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
			if (e.getSource().equals(btnNewButton)) {
			} else {
				JButton botoia = (JButton) e.getSource();
				String datuak = botoia.getText();
				KatalogoZabalduaKargatu.getnZK().xehetasunakBilatu(datuak);
			}
		}
	}
}

