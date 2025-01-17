package Bista;

import Kontroladorea.GestoreNagusia;
import Kontroladorea.GestorePuntuazio;

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
import Kontroladorea.GestoreFilm;
import org.json.JSONObject;

public class KatalogoNagusiaB extends JFrame 
{
	private static KatalogoNagusiaB nKNB=null;
	private static final long serialVersionUID = 1L;
	private JTextField bilaketa;
	private JButton bilatuBtn;
	private JButton ordenatuBtn;
	private JButton exitBtn;
	private JPanel filmPanel;
	private JLabel emaitzikEz;
	private Controller controller = null;
	
	public static KatalogoNagusiaB getKNB() {
		if(nKNB==null) {
			nKNB=new KatalogoNagusiaB();
			
		}
		return nKNB;
	}
	/**
	 * Create the application.
	 */
	public KatalogoNagusiaB() {
		initialize();
		katalogoaErakutsi();
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
		
		bilatuBtn.addActionListener(getController());
		bilaketa.addKeyListener(getController());
		ordenatuBtn.addActionListener(getController());

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
	
	public void katalogoaErakutsi() {
		JSONArray emaitza = GestoreNagusia.getGN().getInfoKatalogokoFilmGuztiak();
		eguneratuZerrenda(emaitza);
        setVisible(true);
	}
	
	public void eguneratuZerrenda(JSONArray zerrenda) {
		if(filmPanel!=null) {
			filmPanel.removeAll();
		}
	
		if(zerrenda.length() == 0) {
			emaitzikEz.setVisible(true);
		} else {
			emaitzikEz.setVisible(false);
			
			for (int i = 0; i < zerrenda.length(); i++) {
	            JSONObject filmJson = zerrenda.getJSONObject(i);

	            String izena = filmJson.getString("izenburua");
	            double puntuazioa = filmJson.getDouble("puntuazioa");
				
				String izenbPuntu=String.format("%s (%.2f)",izena,puntuazioa);
				
				JButton infoBtn=new JButton("Xehetasunak");
	            infoBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new FilmXehetasunakB(izena);
		                setVisible(false);
					}
				});
	            JPanel filmaP=new JPanel();
				filmaP.setLayout(new BorderLayout());
				
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
	
	private Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }
	
    private class Controller extends KeyAdapter implements ActionListener {
    	
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(bilatuBtn)) {
            	String f = bilaketa.getText();
            	if (!f.isEmpty()) {
            		JSONArray filmak = GestoreNagusia.getGN().bilaketaEgin(f);
            		eguneratuZerrenda(filmak);
            	} else {
            		katalogoaErakutsi();
            	}
            } else if (e.getSource().equals(ordenatuBtn)) {
                GestoreNagusia.getGN().ordenatuFilmakPuntuazioz();
                katalogoaErakutsi();
            }else if(e.getSource().equals(exitBtn)) {
            	//Falta hacerlo
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getSource().equals(bilaketa) && e.getKeyCode() == KeyEvent.VK_ENTER) {
            	String f = bilaketa.getText();
            	if (!f.isEmpty()) {
            		JSONArray filmak = GestoreNagusia.getGN().bilaketaEgin(f);
            		eguneratuZerrenda(filmak);
            	} else {
            		katalogoaErakutsi();
            	}
            }
        }
    }
}