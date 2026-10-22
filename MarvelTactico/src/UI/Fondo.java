package UI;

import javax.swing.*;
import java.awt.*;

// Panel base que dibuja una imagen de fondo escalada a todo el tamaño del panel.
// Las pantallas de menu heredan de esta clase para no repetir el codigo de dibujo.
public class Fondo extends JPanel {

    private Image fondo;

    public Fondo(String nombreImagen) {
        java.net.URL url = getClass().getResource("/resources/" + nombreImagen);
        if (url != null) {
            this.fondo = new ImageIcon(url).getImage();
            this.setOpaque(false);
        } else {
            // Si la imagen no existe, se usa un fondo oscuro para que el texto blanco sea legible.
            System.out.println("Aviso: no se encontro la imagen '" + nombreImagen + "', se usa fondo por defecto.");
            this.fondo = null;
            this.setOpaque(true);
            this.setBackground(new Color(20, 20, 30));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Carga un icono desde resources y lo escala al tamaño indicado (en pixeles).
    public static ImageIcon icono(String archivo, int size) {
        Image imagen = new ImageIcon(Fondo.class.getResource("/resources/" + archivo)).getImage();
        return new ImageIcon(imagen.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    public static ImageIcon icono(String archivo, int alto, int ancho){
        java.net.URL url = Fondo.class.getResource("/resources/" + archivo);
        if(url==null){
            System.out.println("Aviso: no se encontro el icono '" + archivo + "'.");
            return null;
        }
        Image imagen = new ImageIcon(url).getImage();
        return new ImageIcon(imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));
    }
}
