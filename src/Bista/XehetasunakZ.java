package Bista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.json.JSONObject;

import Eredua.KatalogoZabalduaKargatu;

public class XehetasunakZ extends JFrame {

	private static final long serialVersionUID = 1L;
	public static XehetasunakZ xehetasunak;
    private JButton baloratuBtn;
    private JButton itxiBtn;
    private JLabel puntuBbLabel;
    private JTextArea iruzkinakArea;
    private Controller controller = null;
    private JPanel panel_1;
    private JButton alokatuBtn;
    private JPanel panel_2;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					XehetasunakZ frame = new XehetasunakZ();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 */
    
	/**
	 * Create the frame.
	 */
	public XehetasunakZ(JSONObject xehetasunak) {
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane=new JScrollPane();
        panel.add(scrollPane);

        itxiBtn=new JButton("Itxi");
        itxiBtn.addActionListener(getController());
        panel.add(itxiBtn);
        
        getContentPane().add(panel, BorderLayout.SOUTH);
        
        panel_2 = new JPanel();
        getContentPane().add(panel_2, BorderLayout.NORTH);
        panel_2.setLayout(new GridLayout(7, 1, 0, 0));
        
        JLabel label = new JLabel("Izenburua: " + xehetasunak.getString("Title"));
        panel_2.add(label);
        JLabel label_1 = new JLabel("Aktoreak: " + xehetasunak.getString("Actors"));
        panel_2.add(label_1);
        JLabel label_2 = new JLabel("Urtea: " + xehetasunak.getString("Year"));
        panel_2.add(label_2);
        JLabel label_3 = new JLabel("Generoa: " + xehetasunak.getString("Genre"));
        panel_2.add(label_3);
        JLabel label_4 = new JLabel("Zuzendaria: " + xehetasunak.getString("Director"));
        panel_2.add(label_4);
                
        iruzkinakArea=new JTextArea(6, 30);
        getContentPane().add(iruzkinakArea, BorderLayout.CENTER);
        iruzkinakArea.setEditable(false);
        setVisible(true);
	}

	public Controller getController() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}
	
	public class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(itxiBtn)) {
				setVisible(false);
			}
		}
	}
}
