package Bista;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import javax.swing.*;
import java.awt.*;

public class PantailaIkusi extends JFrame {
    private static final long serialVersionUID = 1L;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public PantailaIkusi(String titulo) {
        super(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        add(mediaPlayerComponent, BorderLayout.CENTER);
    }

    public void reproducirVideo(String rutaVideo) {
        if (rutaVideo != null && !rutaVideo.isEmpty()) {
            mediaPlayerComponent.mediaPlayer().media().play(rutaVideo);
        } else {
            System.err.println("La ruta del video es nula o vacía.");
        }
    }

    public void liberarRecursos() {
        mediaPlayerComponent.release();
    }

    public static void main(String[] args) {
        // Configurar las rutas de VLC
        System.setProperty("jna.library.path", "C:\\Program Files\\VideoLAN\\VLC");
        System.setProperty("VLC_PLUGIN_PATH", "C:\\Program Files\\VideoLAN\\VLC\\plugins");

        // Crear la ventana
        PantailaIkusi ventana = new PantailaIkusi("Reproductor VLCJ");
        ventana.setVisible(true);

        // Imprimir ruta del video para depuración
        String rutaVideo = null;
        try {
            //rutaVideo = PantailaIkusi.class.getClassLoader().getResource("resources/LaLaLand.mp4").toExternalForm();
        	rutaVideo = "resources/LaLaLand.mp4";
        	System.out.println("Ruta del video: " + rutaVideo);
        } catch (Exception e) {
            System.err.println("Error al obtener la ruta del video: " + e.getMessage());
        }

        // Comprobar y reproducir video
        if (rutaVideo != null) {
            ventana.reproducirVideo(rutaVideo);
        }

        // Añadir un hook para liberar recursos
        Runtime.getRuntime().addShutdownHook(new Thread(ventana::liberarRecursos));

        // Forzar refresco de la ventana
        ventana.repaint();
    }
}