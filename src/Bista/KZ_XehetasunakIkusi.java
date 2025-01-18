package Bista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import Kontroladorea.GestoreKatalogoZabaldua;
import Kontroladorea.GestoreNagusia;

@SuppressWarnings("deprecation")
public class KZ_XehetasunakIkusi extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton eskatu;
	private JButton itxi;
	private Controller controller;
	private JTextArea testua;

	public KZ_XehetasunakIkusi(String datuak) {
		GestoreNagusia.getGN().KZXehetasunakBilatu(datuak);
		initialize();
		datuakErakutsi(datuak);
		setVisible(true);
	}
	public void initialize() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		FlowLayout fl_contentPanel = new FlowLayout();
		contentPanel.setLayout(fl_contentPanel);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.add(getTestua());
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		buttonPane.add(getEButton());
		getRootPane().setDefaultButton(getEButton()); //Esto la verdad no se que hace, lo ha creado el windowBuilder
		buttonPane.add(getIButton());
	}
	
	public JButton getEButton() {
		if (eskatu == null) {
			eskatu = new JButton("Eskatu");
			eskatu.addActionListener(getCont());
		}
		return eskatu;
	}
	public JButton getIButton() {
		if (itxi == null) {
			itxi = new JButton("Itxi");
			itxi.addActionListener(getCont());
		}
		return itxi;
	}
	private JTextArea getTestua() {
		if (testua == null) {
			testua = new JTextArea("");
		}
		return testua;
	}

	public void datuakErakutsi(String izenUrte) {
		JSONObject datuak = GestoreNagusia.getGN().KZXehetasunakBilatu(izenUrte);
		getTestua().setText("Izenburua: " + datuak.getString("Title") +
							"\nUrtea: " + datuak.getString("Year") + 
							"\nGeneroa: " + datuak.getString("Genre") +
							"\nAktoreak: " + datuak.getString("Actors") +
							"\nZuzendaria: " + datuak.getString("Director"));
		testua.setEditable(false); // Desactiva la edición
		testua.setFocusable(false); // Evita que el usuario lo seleccione con el cursor
		testua.setOpaque(false);
	}
	
	public void bidaliEskaera() {
		Boolean bidaliDa = GestoreNagusia.getGN().KZBidaliEskaera();
		if (bidaliDa) {
			JOptionPane.showMessageDialog(this, "Filma katalogoan gehitu da");
		}
		else {
			JOptionPane.showMessageDialog(this, "Filma badago katalogoan edo eskatuta");
		}
	}
	
	public void ezabatu() {
		dispose();
	}
	
	public Controller getCont() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}
	
	//---------------------------------------KONTROLADOREA---------------------------------------

	
	public class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(itxi)) {
				ezabatu();
			}
			else if (e.getSource().equals(eskatu)) {
				bidaliEskaera();
				ezabatu();
			}
		}
	}
}
