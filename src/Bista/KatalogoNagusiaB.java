package Bista;

import org.json.JSONArray;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Eredua.DB_kudeatzailea;
import Eredua.Film;
import Kontroladorea.KatalogoNagusia;
import org.json.JSONObject;

public class KatalogoNagusiaB extends JFrame //implements Observer 
{
	private static final long serialVersionUID = 1L;
	private KatalogoNagusia katalogo;
	private JTextField bilaketa;
	private JButton bilatuBtn;
	private JButton ordenatuBtn;
	private JPanel filmPanel;
	private JLabel emaitzikEz;
	private DB_kudeatzailea dbK;
	private GNKatalogoNagusia GNkn = null;

	/**
	 * Create the application.
	 */
	public KatalogoNagusiaB() {
		this.katalogo = KatalogoNagusia.getKN();
		this.dbK = DB_kudeatzailea.getDB();
		//katalogo.addObserver(this);
		initialize();
		filmakErakutsi();
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Filmen Katalogo Nagusia");
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel bilaketaPanel=new JPanel();
		bilaketa=new JTextField(20);
		bilatuBtn=new JButton("Bilatu");
		ordenatuBtn=new JButton("Ordenatu");
		
		bilatuBtn.addActionListener(getGN());
		bilaketa.addKeyListener(getGN());
		ordenatuBtn.addActionListener(getGN());
		
		bilaketaPanel.add(bilaketa);
		bilaketaPanel.add(bilatuBtn);
		bilaketaPanel.add(ordenatuBtn);
		
		filmPanel=new JPanel();
		filmPanel.setLayout(new BoxLayout(filmPanel, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane=new JScrollPane(filmPanel);
		getContentPane().add(bilaketaPanel, BorderLayout.NORTH);
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		
		emaitzikEz = new JLabel("Ez da aurkitu filmarik izen horrekin.");
		emaitzikEz.setForeground(Color.RED);
		emaitzikEz.setVisible(false);  
	    getContentPane().add(emaitzikEz, BorderLayout.SOUTH);
	}
	
	public void filmakErakutsi() {
		JSONArray emaitza = getGN().getInfoFilmak();
		
		if(filmPanel!=null) {
			filmPanel.removeAll();
		}
	
		if(emaitza.length() == 0) {
			emaitzikEz.setVisible(true);
		} else {
			emaitzikEz.setVisible(false);
			
			for (int i = 0; i < emaitza.length(); i++) {
	            // Obtener el JSONObject en la posición 'i' del JSONArray
	            JSONObject filmJson = emaitza.getJSONObject(i);
	            // Obtener el nombre de la película
	            String nombre = filmJson.getString("izenburua");
	            double puntuazioa = filmJson.getDouble("puntuazioa");
	            
	            JButton infoBtn=new JButton("Xehetasunak");
	            infoBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//FilmXehetasunakB xehetasunP=new FilmXehetasunakB(film, dbK);
						//xehetasunP.setVisible(true);
					}
				});
	            
	            JPanel filmaP=new JPanel();
				filmaP.setLayout(new BorderLayout());
				
				String izenbPuntu=String.format("%s (%.2f)",nombre,puntuazioa);
				JLabel filmLabel=new JLabel(izenbPuntu);
				filmaP.add(filmLabel,BorderLayout.CENTER);	
				filmaP.add(infoBtn, BorderLayout.EAST);
				filmPanel.add(filmaP);
	        }
		}
		if(filmPanel!=null) {
			filmPanel.revalidate();
			filmPanel.repaint();
		}
	}
	
	private GNKatalogoNagusia getGN() {
        if (GNkn == null) {
            GNkn = new GNKatalogoNagusia();
        }
        return GNkn;
    }

//-------------------------------GESTORE NAGUSIA-------------------------------
	
    private class GNKatalogoNagusia extends KeyAdapter implements ActionListener {
    	
    	public JSONArray getInfoFilmak() {
    		KatalogoNagusia kat = KatalogoNagusia.getKN();
    		List<Film> filmak = kat.getFilmak();
    		
    		JSONArray JSONfilm = new JSONArray();
    		for (Film film : filmak) {
    			JSONObject json = kat.getInfo(film);
    			JSONfilm.put(json);
    		}
    		
    		return JSONfilm;
    	}
    	
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(bilatuBtn)) {
            	String f = bilaketa.getText().toLowerCase();
                katalogo.filmaBilatu(f);
            } else if (e.getSource().equals(ordenatuBtn)) {
                katalogo.ordenatuPuntuazioz();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getSource().equals(bilaketa) && e.getKeyCode() == KeyEvent.VK_ENTER) {
            	String f = bilaketa.getText().toLowerCase();
                katalogo.filmaBilatu(f);
            }
        }
    }
}