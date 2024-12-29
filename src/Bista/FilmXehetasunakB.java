package Bista;

import javax.swing.*;
import Eredua.DB_kudeatzailea;
import Eredua.Film;
import Kontroladorea.GestoreNagusia;
import org.json.JSONObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JPanel panel_2;

    public FilmXehetasunakB(String filmIzena) {
        setTitle("Xehetasunak - " + filmIzena);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null); 
        
        initialize(filmIzena);
        
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
        
        alokatuBtn = new JButton("Alokatu");
        panel_1.add(alokatuBtn);
        baloratuBtn.addActionListener(getController());
       
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
        JLabel label_2 = new JLabel("Urtea: " + xehetasunak.getInt("urtea"));
        panel_2.add(label_2);
        JLabel label_3 = new JLabel("Generoa: " + xehetasunak.getString("generoa"));
        panel_2.add(label_3);
        JLabel label_4 = new JLabel("Zuzendaria: " + xehetasunak.getString("zuzendaria"));
        panel_2.add(label_4);
        puntuBbLabel = new JLabel("Batez besteko Puntuazioa: " + xehetasunak.getDouble("bbpuntuazioa"));
        panel_2.add(puntuBbLabel);
        JLabel label_5 = new JLabel("Iruzkinak: ");
        panel_2.add(label_5);
                
        iruzkinakArea=new JTextArea(6, 30);
        getContentPane().add(iruzkinakArea, BorderLayout.CENTER);
        iruzkinakArea.setEditable(false);
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Film) {
            film = (Film) o;
            setTitle("Xehetasunak - " + film.getIzenburua());
            puntuBbLabel.setText("Batez besteko Puntuazioa: "+film.getPuntuazioaBb());
            
            iruzkinakArea.setText("");
           
            for(String iruzkina: film.getIruzkinak()) {
            	iruzkinakArea.append(iruzkina + "\n");
            }
        }
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
                String NAN = "79136031T"; // De prueba, cambiar cuando se implemente el usuario
                PuntuazioPantaila puntuP = new PuntuazioPantaila(film, NAN);
                puntuP.setVisible(true);
            }

            if (e.getSource().equals(itxiBtn)) {
                dispose();
            }
        }
    }
}