package Bista;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PantailaIkusi extends JFrame {
    private static final long serialVersionUID = 1L;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public PantailaIkusi(String pathBideoa) {
    	super();
    	System.setProperty("jna.library.path", "C:\\Program Files\\VideoLAN\\VLC");
    	
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        add(mediaPlayerComponent, BorderLayout.CENTER);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                errekurtsoakAskatu(); // Bideoa gelditu eta errekurtsoak askatu
            }
        });
        
        setVisible(true);

        if (pathBideoa != null) {
            bideoaJarri(pathBideoa);
        }
    }

    public void bideoaJarri(String pathBideoa) {
        if (pathBideoa != null && !pathBideoa.isEmpty()) {
            mediaPlayerComponent.mediaPlayer().media().play(pathBideoa);
        } else {
            System.err.println("Bideoaren path null da edo hutsik dago.");
        }
    }
    
    private void errekurtsoakAskatu() {
        if (mediaPlayerComponent.mediaPlayer().status().isPlaying()) {
            mediaPlayerComponent.mediaPlayer().controls().stop(); // Gelditu
        }
        mediaPlayerComponent.release(); // Askatu
    }
}