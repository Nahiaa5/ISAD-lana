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

import Eredua.KatalogoZabalduaKargatu;
import Kontroladorea.GestoreNagusia;

@SuppressWarnings("deprecation")
public class KZ_XehetasunakIkusi extends JDialog implements Observer {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton eskatu;
	private JButton itxi;
	private Controller controller;
	private JTextArea testua;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			KZ_XehetasunakIkusi dialog = new KZ_XehetasunakIkusi();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public KZ_XehetasunakIkusi() {
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

	@Override
	public void update(Observable o, Object arg) {
		if(arg.getClass().equals(JSONObject.class)) {
			JSONObject datuak = (JSONObject) arg;
			getTestua().setText("Izenburua: " + datuak.getString("Title") +
								"\nUrtea: " + datuak.getString("Year") + 
								"\nGeneroa: " + datuak.getString("Genre") +
								"\nAktoreak: " + datuak.getString("Actors") +
								"\nZuzendaria: " + datuak.getString("Director"));
			testua.setEditable(false); // Desactiva la edición
			testua.setFocusable(false); // Evita que el usuario lo seleccione con el cursor
			testua.setOpaque(false);
		}
		else if(arg.getClass().equals(Boolean.class)) {
			JOptionPane.showMessageDialog(this, "Filma badago katalogoan edo eskatuta");
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
			if (e.getSource().equals(itxi)) {
				setVisible(false);
			}
			else if (e.getSource().equals(eskatu)) {
				GestoreNagusia.getGN().KZBidaliEskaera();
			}
		}
	}
}
