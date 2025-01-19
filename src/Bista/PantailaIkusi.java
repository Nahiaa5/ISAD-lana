package Bista;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PantailaIkusi extends JFrame {
    private static final long serialVersionUID = 1L;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public PantailaIkusi(String pathBideoa) throws IllegalStateException {
    	super();
    	String vlcPath = "C:\\Program Files\\VideoLAN\\VLC";
    	System.setProperty("jna.library.path", vlcPath);
    	
    	if (!vlcInstalatutaDago(vlcPath)) {
            throw new IllegalStateException();
        }
    	
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
            try {
                File tempVideoFile = extractResourceToFile(pathBideoa);
                bideoaJarri(tempVideoFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("No se pudo extraer el video: " + pathBideoa);
            }
        }
    }
    
    private File extractResourceToFile(String resourcePath) throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("El recurso no existe: " + resourcePath);
        }

        File tempFile = File.createTempFile("video", ".mp4");
        tempFile.deleteOnExit();

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return tempFile;
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
    
    private static boolean vlcInstalatutaDago(String vlcPath) {
        File vlcFolder = new File(vlcPath);
        return vlcFolder.exists() && vlcFolder.isDirectory();
    }
}