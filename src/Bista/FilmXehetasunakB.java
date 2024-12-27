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
    private Film film; 
    private JButton baloratuBtn;
    private JButton itxiBtn;
    private JLabel puntuBbLabel;
    private JTextArea iruzkinakArea;
    private DB_kudeatzailea dbK;

    public FilmXehetasunakB(Film film, DB_kudeatzailea dbK) {
        this.film = film;
        this.dbK=dbK;
        this.film.addObserver(this); 
        setTitle("Xehetasunak - " + film.getIzenburua());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null); 
        initialize();
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
        baloratuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String NAN = "455678123P"; //De prueba, cambiarlo cuando ya hayamos hecho lo del usuario
            	PuntuazioPantaila puntuP=new PuntuazioPantaila(film, NAN, dbK);
                puntuP.getFrame().setVisible(true);
            }
        });
        panel.add(baloratuBtn);
       
        itxiBtn=new JButton("Itxi");
        itxiBtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
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
}