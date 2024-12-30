package Bista;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
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



public class ErabKudeatu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField bilaketa;
	private JButton bilatuBtn;
	private JPanel erabPanel;
	private JLabel emaitzikEz;
	private ErabKudeatuKontroladorea kontroladorea = null;

	public ErabKudeatu() {
		initialize();
		erabiltzaileakErakutsi();
		setVisible(true);
	}
	
	private void initialize() {
		setTitle("Filmen Katalogo Nagusia");
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel bilaketaPanel=new JPanel();
		bilaketa=new JTextField(20);
		bilatuBtn=new JButton("Bilatu");
		
		bilatuBtn.addActionListener(getEK());
		
		bilaketaPanel.add(bilaketa);
		bilaketaPanel.add(bilatuBtn);
		
		erabPanel=new JPanel();
		erabPanel.setLayout(new BoxLayout(erabPanel, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane=new JScrollPane(erabPanel);
		getContentPane().add(bilaketaPanel, BorderLayout.NORTH);
		getContentPane().add(scrollPane,BorderLayout.CENTER);
		
		emaitzikEz = new JLabel("Ez da aurkitu erabiltzailerik izen horrekin.");
		emaitzikEz.setForeground(Color.RED);
		emaitzikEz.setVisible(false);  
	    getContentPane().add(emaitzikEz, BorderLayout.SOUTH);
	}
	
	public void erabiltzaileakErakutsi() {
		JSONArray emaitza = GestoreNagusia.getGN().getInfoErabiltzaileak();
		erabiltzaileakEguneratu(emaitza);
	}
	
	public void erabiltzaileakEguneratu(JSONArray erabiltzaileak) {
		
		if(erabPanel!=null) {
			erabPanel.removeAll();
		}
	
		if(erabiltzaileak.length() == 0) {
			emaitzikEz.setVisible(true);
		} else {
			emaitzikEz.setVisible(false);
			
			for (int i = 0; i < erabiltzaileak.length(); i++) {
	            JSONObject erabJson = erabiltzaileak.getJSONObject(i);

	            String nan = erabJson.getString("NAN");
	            String izena = erabJson.getString("Izena");
	            
	            JButton aldatuBtn=new JButton("Aldatu");
	            aldatuBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
					}
				});
	            
	            JButton ezabatuBtn=new JButton("Ezabatu");
	            ezabatuBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
					}
				});
	            
	            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)); 
	            btnPanel.add(aldatuBtn);
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
	
	
	private ErabKudeatuKontroladorea getEK() {
		if (kontroladorea == null) {
			kontroladorea = new ErabKudeatuKontroladorea ();
		}
		return kontroladorea;
	}
	
	//---------------------------------------KONTROLADOREA---------------------------------------
	private class ErabKudeatuKontroladorea implements ActionListener {
		
		public ErabKudeatuKontroladorea() {}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			 if (e.getSource().equals(bilatuBtn)) {
	            	String erab = bilaketa.getText();
	            	if (!erab.isEmpty()) {
	            		JSONArray erabiltzaileak = GestoreNagusia.getGN().bilatzaileanErabiltzaileak(erab);
	            		erabiltzaileakEguneratu(erabiltzaileak);
	            	} else {
	            		erabiltzaileakErakutsi();
	            	}
	            }

		}
	} 

}
