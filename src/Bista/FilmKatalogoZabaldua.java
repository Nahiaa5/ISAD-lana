package Bista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;
import Kontroladorea.GestoreKatalogoZabaldua;
import Kontroladorea.GestoreNagusia;
import Kontroladorea.GestoreZerrenda;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTextField;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
@SuppressWarnings("deprecation")
public class FilmKatalogoZabaldua extends JFrame{

	private static final long serialVersionUID = 1L;
	private static FilmKatalogoZabaldua nFKZ;
	GestoreZerrenda GZ = GestoreZerrenda.getnZZ();
	FilmakSartuZerrenda FSZ = FilmakSartuZerrenda.getFSZ();
	private JPanel contentPane;
	private JPanel panel1;
	private Controller controller;
	private JTextField bTextField;
	private JButton bJButton;
	private JButton eJButton;
	private JScrollPane scrollPane;
	private int ID;
	private int flag = 0;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilmKatalogoZabaldua frame = new FilmKatalogoZabaldua();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public static FilmKatalogoZabaldua getPN(){
		if(nFKZ==null) {
			nFKZ=new FilmKatalogoZabaldua();
		}
		return nFKZ;
	}
	private FilmKatalogoZabaldua() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panel1 = new JPanel();
    	panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		scrollPane = new JScrollPane(panel1);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		panel.add(getBilatuTextField());
		panel.add(getBilatuJButton());
		panel.add(getExitJButton());
		
	}
	
	public void setID (int id) {
		this.ID = id;
	}
	
	public void setFlag (int flag) {
		this.flag = flag;
	}
	
	private JTextField getBilatuTextField() {
		if (bTextField == null) {
			bTextField = new JTextField();
			bTextField.setColumns(25);
			bTextField.addActionListener(getCont());
		}
		return bTextField;
	}
	private JButton getBilatuJButton() {
		if (bJButton == null) {
			bJButton = new JButton("Bilatu");
			bJButton.addActionListener(getCont());
		}
		return bJButton;
	}
	private JButton getExitJButton() {
		if (eJButton == null) {
			eJButton = new JButton("Exit");
			eJButton.addActionListener(getCont());
		}
		return eJButton;
	}
    private void addNewButton(String datuak) {
        JButton button = new JButton(datuak);
        panel1.add(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
        button.addActionListener(getCont());
        revalidate();
        repaint();
    }
        
    public void filmakErakutsi(String izena) {
    	JSONArray filmak = GestoreNagusia.getGN().KZFilmakBilatu(izena);
    	if (filmak != null) {
    		panel1.removeAll();
    		panel1.revalidate();
    		panel1.repaint();
    		for (int i = 0; i < filmak.length(); i++) {
    			JSONObject movie = filmak.getJSONObject(i);
                String title = movie.getString("Title");
                String year = movie.getString("Year");
                addNewButton(title + " (" + year + ")");
    		}
    	}
    	else {
    		panel1.removeAll();
    		panel1.revalidate();
    		panel1.repaint();
    		JLabel emaitzikEz = new JLabel("Ez da aurkitu filma.");
    		panel1.add(emaitzikEz);
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
			if (e.getSource().equals(bJButton) || e.getSource().equals(bTextField)) {
				filmakErakutsi(getBilatuTextField().getText());
			}
			
			else if (e.getSource().equals(eJButton)){
				new ErabiltzailePN();
				setVisible(false);
			}
			else {
				JButton botoia = (JButton) e.getSource();
				String datuak = botoia.getText();
				switch (flag) {
				
					case 0:
						new KZ_XehetasunakIkusi(datuak);
						break;
					case 1:
						String izena = GZ.bilatuZerrenda(ID).getIzena();
						FSZ.setIzena(izena);
						Boolean b = GZ.sartuFilmaZerrendaBaten(ID, datuak);
						if (b) {
							FSZ.sartuFilma(datuak);
						}
						FSZ.setVisible(true);
						flag = 0;
						setVisible(false);
						break;
					case 2:
						Boolean b1 = GZ.sartuFilmaZerrendaBaten(ID, datuak);
						if (b1) {
							FSZ.sartuFilma(datuak);
						}
						FSZ.setVisible(true);
						flag = 0;
						setVisible(false);
						break;
						
				}
				FSZ.setID(ID);
			}
		}
	}
	
	
}