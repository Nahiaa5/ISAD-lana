package Bista;

import javax.swing.*;
import Eredua.DB_kudeatzailea;
import Eredua.Film;
import Kontroladorea.GestoreErabiltzaile;
import Kontroladorea.GestoreFilm;
import Kontroladorea.GestoreNagusia;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Observer;
import java.util.Observable;

public class FilmXehetasunakB extends JFrame implements Observer {
    private static final long serialVersionUID = 1L;
    private JButton baloratuBtn;
    private JButton itxiBtn;
    private JLabel puntuBbLabel;
    private JTextArea iruzkinakArea;
    private Controller controller = null;
    private JPanel panel_1;
    private JButton alokatuBtn;
    private JButton ikusiBtn;
    private JPanel panel_2;

    public FilmXehetasunakB(String filmIzena) {
    	GestoreNagusia.getGN().addObserver(this);
    	
        setTitle("Xehetasunak - " + filmIzena);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null); 
        
        initialize(filmIzena);
		iruzkinakErakutsi();
        controller = getController();
    }

    private void initialize(String filmIzena) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane=new JScrollPane();
        panel.add(scrollPane);
        
        panel_1 = new JPanel();
        panel.add(panel_1);
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        baloratuBtn = new JButton("Baloratu");
        panel_1.add(baloratuBtn);
        baloratuBtn.addActionListener(getController());
        
        alokatuBtn = new JButton("Alokatu");
        panel_1.add(alokatuBtn);
        alokatuBtn.addActionListener(getController());
        
        ikusiBtn = new JButton("Ikusi");
        panel_1.add(ikusiBtn);
        ikusiBtn.addActionListener(getController());
       
        itxiBtn=new JButton("Itxi");
        itxiBtn.addActionListener(getController());
        panel.add(itxiBtn);
        
        getContentPane().add(panel, BorderLayout.SOUTH);
        
        panel_2 = new JPanel();
        getContentPane().add(panel_2, BorderLayout.NORTH);
        panel_2.setLayout(new GridLayout(7, 1, 0, 0));
        JSONObject xehetasunak = GestoreNagusia.getGN().getFilmXehetasunak(filmIzena);
        JLabel label = new JLabel("Izenburua: " + xehetasunak.getString("izenburua"));
        panel_2.add(label);
        JLabel label_1 = new JLabel("Aktoreak: " + xehetasunak.getString("aktoreak"));
        panel_2.add(label_1);
        JLabel label_2 = new JLabel("Urtea: " + xehetasunak.getString("urtea"));
        panel_2.add(label_2);
        JLabel label_3 = new JLabel("Generoa: " + xehetasunak.getString("generoa"));
        panel_2.add(label_3);
        JLabel label_4 = new JLabel("Zuzendaria: " + xehetasunak.getString("zuzendaria"));
        panel_2.add(label_4);
        puntuBbLabel = new JLabel("Batez besteko Puntuazioa: " + xehetasunak.getDouble("puntuazioaBb"));
        panel_2.add(puntuBbLabel);
        JLabel label_5 = new JLabel("Iruzkinak: ");
        panel_2.add(label_5);
                
        iruzkinakArea=new JTextArea(6, 30);
        getContentPane().add(iruzkinakArea, BorderLayout.CENTER);
        iruzkinakArea.setEditable(false);
        setVisible(true);
    }
    
    public void iruzkinakErakutsi() {
    	String filmIzena = getTitle().substring("Xehetasunak - ".length());
        
        Film film = GestoreNagusia.getGN().bilatuFilmaIzenaz(filmIzena);
        
        if (film != null) {
            JSONObject xehetasunak = GestoreNagusia.getGN().getFilmXehetasunak(filmIzena);
            
            JLabel label1 = (JLabel) panel_2.getComponent(0);
            label1.setText("Izenburua: " + xehetasunak.getString("izenburua"));
            
            JLabel label2 = (JLabel) panel_2.getComponent(1);
            label2.setText("Aktoreak: " + xehetasunak.getString("aktoreak"));
            
            JLabel label3 = (JLabel) panel_2.getComponent(2);
            label3.setText("Urtea: " + xehetasunak.getString("urtea"));
            
            JLabel label4 = (JLabel) panel_2.getComponent(3);
            label4.setText("Generoa: " + xehetasunak.getString("generoa"));
            
            JLabel label5 = (JLabel) panel_2.getComponent(4);
            label5.setText("Zuzendaria: " + xehetasunak.getString("zuzendaria"));
            
            DecimalFormat df = new DecimalFormat("#.00");
            puntuBbLabel.setText("Batez besteko Puntuazioa: " + df.format(xehetasunak.getDouble("puntuazioaBb")));
                        
            String iruzkinak = String.join("\n", film.getIruzkinak());
            iruzkinakArea.setText(iruzkinak);
        }
    }
    
    public void puntuazioaKalkulatu() {
    	JLabel izenburuaLabel = (JLabel) panel_2.getComponent(0); 
        String izenburua = izenburuaLabel.getText().replace("Izenburua: ", "").trim();
        JLabel urteaLabel = (JLabel) panel_2.getComponent(2); 
        String urtea = urteaLabel.getText().replace("Urtea: ", "").trim();
    	GestoreNagusia.getGN().kalkulatuPuntuazioaBb(izenburua, urtea);
    }
    
    private boolean eguneratu = false; //esto es raro, pero funciona
    @Override
    public void update(Observable o, Object arg) {
    	if(eguneratu) {
    		return;
    	}
    	eguneratu=true;
    	if (o instanceof GestoreNagusia) {
    			puntuazioaKalkulatu();
        		iruzkinakErakutsi();
    		
        }
    	eguneratu=false;
    }
    
    private Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    private class Controller implements ActionListener {
    	@Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(baloratuBtn)) {
                String labelText = ((JLabel) panel_2.getComponent(0)).getText();
                String filmIzena = labelText.substring(labelText.indexOf(":") + 2);
                
                Film film = GestoreNagusia.getGN().bilatuFilmaIzenaz(filmIzena);
                if(film==null) {
                	JOptionPane.showMessageDialog(FilmXehetasunakB.this, "Ez da aurkitu filma.", "Errorea", JOptionPane.ERROR_MESSAGE);
                	return;
                }
                String nan = GestoreNagusia.getGN().getSaioaNAN();
                boolean alokatuta = GestoreNagusia.getGN().erabiltzaileakAlokatuDu(nan, film.getFilmID());
                if(!alokatuta) {
                	JOptionPane.showMessageDialog(FilmXehetasunakB.this, "Ez duzu filma hau alokatu, ezin duzu baloratu.", "Errorea", JOptionPane.ERROR_MESSAGE);
                }else {
                	PuntuazioPantaila puntuP = new PuntuazioPantaila(film, nan);
                    puntuP.setVisible(true);
                }
                
            } else if (e.getSource().equals(itxiBtn)) {
            	KatalogoNagusiaB.getKNB().katalogoaErakutsi();
                dispose();
            } else if (e.getSource().equals(alokatuBtn)) {
            	String labelText = ((JLabel) panel_2.getComponent(0)).getText();
                String filmIzena = labelText.substring(labelText.indexOf(":")+2);
            	boolean alokatuta = GestoreNagusia.getGN().filmaAlokatu(filmIzena);
            	if (!alokatuta) {
            		JOptionPane.showMessageDialog(FilmXehetasunakB.this, "Filma jada alokatuta daukazu. Ikusi nahi baduzu, 'ikusi' botoia sakatu.", "Errorea", JOptionPane.ERROR_MESSAGE);
            	} else {
            		JOptionPane.showMessageDialog(FilmXehetasunakB.this, "Filma alokatu da. Ikusi nahi baduzu, 'ikusi' botoia sakatu.", "Alokatuta", JOptionPane.INFORMATION_MESSAGE);
            	}
            } else if (e.getSource().equals(ikusiBtn)) {
            	//VideoScreen.showVideo("/resources/LaLaLand.mp4");
            }
        }
    }
}