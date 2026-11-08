package Piezas;

import javax.imageio.ImageIO;

import Error.ImagenNoLocalizadaException;
import Game.Combates;
import Game.Tablero;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public abstract class Piezas implements Movable{
    public int col, fil;
    public int xPos, yPos;

    public boolean esHeroe;
    protected String nombre;
    protected String alias;
    protected int rango; // 1-10. -1 = Bomba, -2 = Tierra
    protected BufferedImage imagen;
    public boolean visible= false;
    Tablero tablero;

    public Piezas(Tablero tablero) {
        this.tablero = tablero;
    }

    public void setPos(int col, int fil, int xPos, int yPos) {
        this.col = col;
        this.fil = fil;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    // Toma del personaje el bando, el rango, el alias y la imagen
    protected void asignarPersonaje(Personaje personaje) throws ImagenNoLocalizadaException {
        this.esHeroe = personaje.esHeroe();
        this.rango = personaje.getRango();
        this.alias = personaje.getNombre();
        this.nombre = personaje.getArchivo();
        cargarImagenes(this.nombre);
    }

    public void cargarImagenes(String nombre) throws ImagenNoLocalizadaException {
        InputStream is = getClass().getResourceAsStream("/resources/personajes/" + nombre + ".png");

        if(is==null){
            throw new ImagenNoLocalizadaException("No se encontro la imagen de " + nombre);
        }
        
        try {
            this.imagen = ImageIO.read(is);
        } catch (IOException e) {
            throw new ImagenNoLocalizadaException("No se pudo cargar la imagen de la pieza " +nombre);
        }
    }

    // Dice si esta pieza gana al atacar al defensor. En empate mueren las dos
    // piezas
    public abstract boolean seRevela(int ncol, int nFil);
    public abstract Combates combate(Piezas defensor);

    public void paint(Graphics2D g2d) {
        // Calculamos un tamaño que quepa en la celda (tCasillas es 72)
        int size = tablero.tCasillas - 10;
        int offset = (tablero.tCasillas - size) / 2;

        if(!tablero.sePuedeVer(this)){
            g2d.setColor(esHeroe ? new Color(40,90,170) : new Color(150,30,30));
            g2d.fillRect(xPos+offset, yPos+offset, size, size);
            g2d.setColor(Color.white);
            g2d.drawString("?", xPos+tablero.tCasillas / 2 -3 , yPos + tablero.tCasillas / 2 + 4);
            return;
        }

        if (imagen != null) {
            // Dibujamos la imagen centrada y escalada al tamaño de la celda
            g2d.drawImage(imagen, xPos + offset, yPos + offset, size, size, null);
        } else {
            // FALLBACK VISUAL: Cuadro centrado
            g2d.setColor(esHeroe ? new Color(0, 0, 255, 100) : new Color(255, 0, 0, 100));
            g2d.fillRect(xPos + offset, yPos + offset, size, size);
            g2d.setColor(Color.WHITE);
            g2d.drawString("R" + rango, xPos + offset + 10, yPos + offset + (size / 2));
        }
    }

    public final int getCol() {
        return col;
    }

    public final int getFil() {
        return fil;
    }

    public final String getNombre() {
        return nombre;
    }

    public final String getAlias() {
        return alias;
    }

    public final int getRango() {
        return rango;
    }

    public BufferedImage getImagen() {
        return imagen;
    }
}