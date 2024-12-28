package Bista;

import javax.swing.*;

import Eredua.DB_kudeatzailea;
import Eredua.Film;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;
import java.util.Observable;

public class FilmXehetasunakB extends JFrame implements Observer {
    private static final long serialVersionUID = 1L;
    private Film film; 
    private JButton baloratuBtn;
    private JButton itxiBtn;
    private JLabel puntuBbLabel;
    private JTextArea iruzkinakArea;
    private DB_kudeatzailea dbK;
    private Controler controler;

    public FilmXehetasunakB(Film film, DB_kudeatzailea dbK) {
        this.film = film;
        this.dbK=dbK;
        this.film.addObserver(this); 
        
        setTitle("Xehetasunak - " + film.getIzenburua());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null); 
        
        initialize();
        
        controler=new Controler();
        film.gordeIruzkinak(dbK);
    }

    private void initialize() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Izenburua: " + film.getIzenburua()));
        panel.add(new JLabel("Aktoreak: " + film.getAktoreak()));
        panel.add(new JLabel("Urtea: " + film.getUrtea()));
        panel.add(new JLabel("Generoa: " + film.getGeneroa()));
        panel.add(new JLabel("Zuzendaria: " + film.getZuzendaria()));
        puntuBbLabel = new JLabel("Batez besteko Puntuazioa: " + film.getPuntuazioaBb());
        panel.add(puntuBbLabel);

        iruzkinakArea=new JTextArea(10, 30);
        iruzkinakArea.setEditable(false);
        JScrollPane scrollPane=new JScrollPane(iruzkinakArea);
        panel.add(new JLabel("Iruzkinak: "));
        panel.add(scrollPane);
        
        baloratuBtn = new JButton("Baloratu");
        baloratuBtn.addActionListener(getControler());
        panel.add(baloratuBtn);
       
        itxiBtn=new JButton("Itxi");
        itxiBtn.addActionListener(getControler());
        panel.add(itxiBtn);
        
        getContentPane().add(panel, BorderLayout.CENTER);
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
    
    private Controler getControler() {
        if (controler == null) {
            controler = new Controler();
        }
        return controler;
    }

    private class Controler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(baloratuBtn)) {
                String NAN = "79136031T"; // De prueba, cambiar cuando se implemente el usuario
                PuntuazioPantaila puntuP = new PuntuazioPantaila(film, NAN, dbK);
                puntuP.setVisible(true);
            }

            if (e.getSource().equals(itxiBtn)) {
                dispose();
            }
        }
    }
}