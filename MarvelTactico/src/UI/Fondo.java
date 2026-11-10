package UI;

import javax.swing.*;
import java.awt.*;

// Panel base que dibuja una imagen de fondo escalada a todo el tamaño del panel.
// Las pantallas de menu heredan de esta clase para no repetir el codigo de dibujo.
// Clase base visual con fondo reutilizable para las pantallas del juego.
public class Fondo extends JPanel {

    private Image fondo;

    public Fondo(String nombreImagen) {
        java.net.URL url = getClass().getResource("/resources/" + nombreImagen);
        if (url != null) {
            this.fondo = new ImageIcon(url).getImage();
            this.setOpaque(false);
        } else {
            // Si la imagen no existe, se usa un fondo oscuro para que el texto blanco sea legible.
            this.fondo = null;
            this.setOpaque(true);
            this.setBackground(new Color(20, 20, 30));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            int anchoImagen = fondo.getWidth(this);
            int altoImagen = fondo.getHeight(this);

            double escala = Math.max((double) getWidth() / anchoImagen, (double) getHeight() / altoImagen);
            int ancho = (int) (anchoImagen * escala);
            int alto = (int) (altoImagen*escala);

            int x = (getWidth() - ancho ) / 2;
            int y = (getHeight() - alto) / 2;
            g.drawImage(fondo, x, y, ancho, alto, this);
        }
    }

    // Carga un icono desde resources y lo escala al alto y ancho indicados (en pixeles).
    public static ImageIcon icono(String archivo, int alto, int ancho){
        java.net.URL url = Fondo.class.getResource("/resources/" + archivo);
        if(url==null){
            return null;
        }
        Image imagen = new ImageIcon(url).getImage();
        return new ImageIcon(imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));
    }
}
