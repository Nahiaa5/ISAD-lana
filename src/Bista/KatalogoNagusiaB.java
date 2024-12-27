package Bista;

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
import Eredua.KatalogoNagusia;

public class KatalogoNagusiaB extends JFrame implements Observer {
	private KatalogoNagusia katalogo;
	private JTextField bilaketa;
	private JButton bilatuBtn;
	private JButton ordenatuBtn;
	private JPanel filmPanel;
	private JLabel emaitzikEz;
	private DB_kudeatzailea dbK;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KatalogoNagusia katalogo=KatalogoNagusia.getKN();
					DB_kudeatzailea dbK = new DB_kudeatzailea();
					KatalogoNagusiaB window = new KatalogoNagusiaB(katalogo, dbK);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KatalogoNagusiaB(KatalogoNagusia katalogo, DB_kudeatzailea dbK) {
		this.katalogo=katalogo;
		this.dbK=dbK;
		this.katalogo.addObserver(this);
		initialize();
		katalogo.loadFilmak(dbK);
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
		
		bilatuBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String f=bilaketa.getText().toLowerCase();
				katalogo.filmaBilatu(f);
			}
		});
		
		bilaketa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					String f=bilaketa.getText().toLowerCase();
					katalogo.filmaBilatu(f);
				}
			}
		});
		
		ordenatuBtn.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				katalogo.ordenatuPuntuazioz();
			}
		});
		
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

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof KatalogoNagusia) {
			KatalogoNagusia katalogo = (KatalogoNagusia) o;
			List<Film> filmak=katalogo.getFilmak();
			
			if(filmPanel!=null) {
				filmPanel.removeAll();
			}
		
			if(filmak.isEmpty()) {
				emaitzikEz.setVisible(true);
			}else {
				emaitzikEz.setVisible(false);
				for(Film film: filmak) {
					film.eguneratuPuntuBb(dbK);
					JButton infoBtn=new JButton("Xehetasunak");
					infoBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							FilmXehetasunakB xehetasunP=new FilmXehetasunakB(film, dbK);
							xehetasunP.setVisible(true);
						}
					});
					
					JPanel filmaP=new JPanel();
					filmaP.setLayout(new BorderLayout());
					
					String izenbPuntu=String.format("%s (%.2f)",film.getIzenburua(),film.getPuntuazioaBb());
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
	}
}