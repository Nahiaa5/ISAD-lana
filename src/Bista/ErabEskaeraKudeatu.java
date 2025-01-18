package Bista;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import Kontroladorea.GestoreNagusia;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class ErabEskaeraKudeatu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel erabPanel;
	private JLabel emaitzikEz;
	private JButton eJButton;
	private EskKudeatuKontroladorea kontroladorea = null;

	public ErabEskaeraKudeatu() {
		initialize();
		eskaerakErakutsi();
		setVisible(true);
	}
	
	private void initialize() {
		setTitle("Erabiltzaile Eskaerak");
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		erabPanel=new JPanel();
		erabPanel.setLayout(new BoxLayout(erabPanel, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane=new JScrollPane(erabPanel);
		getContentPane().add(scrollPane,BorderLayout.CENTER);
	    getContentPane().add(getEmaitzikEz(), BorderLayout.SOUTH);
	    
	    JPanel panel = new JPanel();
	    panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
	    getContentPane().add(panel,BorderLayout.NORTH);
	    panel.add(getExitJButton());
	}
	
	private JLabel getEmaitzikEz() {
		if(emaitzikEz == null) {
			emaitzikEz = new JLabel("Ez daude erabiltzaile eskaerarik.");
			emaitzikEz.setForeground(Color.RED);
			emaitzikEz.setVisible(false);  
		}
		return emaitzikEz;
	}
	
	private JButton getExitJButton() {
		if (eJButton == null) {
			eJButton = new JButton("Exit");
			eJButton.addActionListener(getEK());
		}
		return eJButton;
	}
	
	public void eskaerakErakutsi() {
		JSONArray emaitza = GestoreNagusia.getGN().getInfoEskaerak();
		
		if(erabPanel!=null) {
			erabPanel.removeAll();
		}
	
		if(emaitza.length() == 0) {
			getEmaitzikEz().setVisible(true);
		} else {
			getEmaitzikEz().setVisible(false);
			
			for (int i = 0; i < emaitza.length(); i++) {
	            JSONObject erabJson = emaitza.getJSONObject(i);

	            String nan = erabJson.getString("NAN");
	            String izena = erabJson.getString("Izena");
	            
	            JButton onartuBtn=new JButton("✔");
	            onartuBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
	            onartuBtn.setActionCommand("onartu:" + nan);
	            onartuBtn.addActionListener(getEK());
	            
	            JButton ezabatuBtn=new JButton("X");
	            ezabatuBtn.setAlignmentY(Component.CENTER_ALIGNMENT);
	            ezabatuBtn.setActionCommand("ezabatu:" + nan);
	            ezabatuBtn.addActionListener(getEK());
	            
	            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); 
	            btnPanel.add(onartuBtn);
	            btnPanel.add(ezabatuBtn);
	            
	            JPanel erabP=new JPanel();
	            erabP.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				erabP.setLayout(new BorderLayout(5, 5));
				JPanel labelsPanel = new JPanel(new GridLayout(2, 1, 5, 5));
				JLabel nanLabel = new JLabel(nan);
				JLabel izenaLabel=new JLabel(izena);
				labelsPanel.add(nanLabel);
				labelsPanel.add(izenaLabel);
				
				erabP.add(btnPanel, BorderLayout.EAST);
				erabP.add(labelsPanel, BorderLayout.CENTER);
				
				erabPanel.add(erabP);
			}         
		}
		
		if(erabPanel!=null) {
			erabPanel.revalidate();
			erabPanel.repaint();
		}
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
			if (e.getSource().equals(eJButton)) {
				new AdminPN();
				setVisible(false);
			}
			else if (command != null) {
	            String[] parts = command.split(":");
	            String action = parts[0];
	            String nan = parts[1];

	            switch (action) {
	                case "onartu":
	                    // Erabiltzailea onartzen da
	                    GestoreNagusia.getGN().erabiltzaileaOnartu(nan);
	                    eskaerakErakutsi();
	                    break;
	                case "ezabatu":
	                    // Erabiltzailea ezabatzen da
	                    GestoreNagusia.getGN().erabiltzaileaEzabatu(nan);
	                    eskaerakErakutsi();
	                    break;
	                default:
	                    System.err.println("Acción desconocida: " + action);
	            }
	        }
		}
	}
}
