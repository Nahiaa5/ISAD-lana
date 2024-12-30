package Bista;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import Bista.FilmKatalogoZabaldua.Controller;
import Eredua.KatalogoZabalduaKargatu;
import Kontroladorea.GestoreFilm;
import Kontroladorea.GestoreNagusia;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;


@SuppressWarnings("deprecation")
public class FilmEskaeraKudeatu extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel filmPanel;
	private JLabel emaitzikEz;
	private EskKudeatuKontroladorea kontroladorea = null;

	public FilmEskaeraKudeatu() {
		initialize();
		eskaerakErakutsi();
		setVisible(true);
	}
	
	private void initialize() {
		setTitle("Film Eskaerak");
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		filmPanel=new JPanel();
		filmPanel.setLayout(new BoxLayout(filmPanel, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane=new JScrollPane(filmPanel);
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		
	    getContentPane().add(getEmaitzikEz(), BorderLayout.SOUTH);
	}
	
	private JLabel getEmaitzikEz() {
		if(emaitzikEz == null) {
			emaitzikEz = new JLabel("Ez daude film eskaerarik.");
			emaitzikEz.setForeground(Color.RED);
			emaitzikEz.setVisible(false);  
		}
		return emaitzikEz;
	}
	
	public void eskaerakErakutsi() {
		JSONArray emaitza = GestoreNagusia.getGN().getFilmEskaerak();
		
		if(filmPanel!=null) {
			filmPanel.removeAll();
		}
	
		if(emaitza.length() == 0) {
			getEmaitzikEz().setVisible(true);
		} else {
			getEmaitzikEz().setVisible(false);
			
// Parte de esto lo mismo tambien podria hacerse con observers, asi en vez de llamar a este metodo cada vez que se pulse
// un boton podria administrarse eso mismo mediante observers, da un poco igual, funciona igualmente
			
			for (int i = 0; i < emaitza.length(); i++) {
	            JSONObject filmJson = emaitza.getJSONObject(i);

	            String izena = filmJson.getString("izenburua");
	            Integer urtea = filmJson.getInt("urtea");
	            
	            JButton onartuBtn=new JButton("✔");
	            onartuBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
	            onartuBtn.setActionCommand("onartu:" + izena);
	            onartuBtn.addActionListener(getEK());
	            
	            JButton ezabatuBtn=new JButton("X");
	            ezabatuBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
	            ezabatuBtn.setActionCommand("ezabatu:" + izena);
	            ezabatuBtn.addActionListener(getEK());
	            
	            JButton infoBtn=new JButton("Info");
	            infoBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
	            infoBtn.setActionCommand("info:" + izena);
	            infoBtn.addActionListener(getEK());
	            
	            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); 
	            btnPanel.add(onartuBtn);
	            btnPanel.add(ezabatuBtn);
	            btnPanel.add(infoBtn);
	            
	            JPanel erabP=new JPanel();
	            erabP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				erabP.setLayout(new BorderLayout(5, 5));
				JPanel labelsPanel = new JPanel(new GridLayout(2, 1, 5, 5));
				JLabel nanLabel = new JLabel(izena);
				JLabel izenaLabel=new JLabel(urtea.toString());
				labelsPanel.add(nanLabel);
				labelsPanel.add(izenaLabel);
				
				erabP.add(btnPanel, BorderLayout.EAST);
				erabP.add(labelsPanel, BorderLayout.CENTER);
				
				filmPanel.add(erabP);
			}         
		}
		
		if(filmPanel!=null) {
			filmPanel.revalidate();
			filmPanel.repaint();
		}
	}
	
	private void datuakErakutsi(String izena) {
    	JSONObject datuak = GestoreNagusia.getGN().getFilmXehetasunak(izena);
    	JOptionPane.showMessageDialog(this, 
    			"Izenburua: " + datuak.getString("izenburua") +
				"\nUrtea: " + datuak.getInt("urtea") + 
				"\nGeneroa: " + datuak.getString("generoa") +
				"\nAktoreak: " + datuak.getString("aktoreak") +
				"\nZuzendaria: " + datuak.getString("zuzendaria"));
	}
	
	private EskKudeatuKontroladorea getEK() {
		if (kontroladorea == null) {
			kontroladorea = new EskKudeatuKontroladorea ();
		}
		return kontroladorea;
	}
	
	//---------------------------------------KONTROLADOREA---------------------------------------
	private class EskKudeatuKontroladorea extends KeyAdapter implements ActionListener {
		
		public EskKudeatuKontroladorea() {}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
	        if (command != null) {
	            String[] parts = command.split(":");
	            String action = parts[0];
	            String izena = parts[1];

	            switch (action) {
	                case "onartu":
	                    // Erabiltzailea onartzen da
	                    GestoreNagusia.getGN().filmaOnartu(izena);
	                    eskaerakErakutsi();
	                    break;
	                case "ezabatu":
	                    // Erabiltzailea ezabatzen da
	                    GestoreNagusia.getGN().filmaEzabatu(izena);
	                    eskaerakErakutsi();
	                    break;
	                case "info":
	                	datuakErakutsi(izena);
	                	break;
	                default:
	                    System.err.println("Acción desconocida: " + action);
	            }
	        }
		}
	}
}
