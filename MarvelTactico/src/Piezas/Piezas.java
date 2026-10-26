package Piezas;

import javax.imageio.ImageIO;

import Error.ImagenNoLocalizadaException;
import Game.Tablero;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public abstract class Piezas {
    public int col, fil;
    public int xPos, yPos;

    public boolean esHeroe;
    protected String nombre;
    protected String alias;
    protected int rango; // Los rangos van del 1 al 10; -1 identifica una bomba y -2, tierra.
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

    // Copia de Personaje los datos que esta pieza necesita y carga su imagen.
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

    public abstract boolean esMovimientoValido(int col, int fil);

    public abstract boolean chocaPieza(int col, int fil);

    // Decide si esta pieza vence al defensor; si empatan, ambas se eliminan.
    public boolean vence(Piezas defensor) {
        if (defensor.getRango() == -2) {
            return true; // Cualquier pieza vence a Tierra.
        }
        if (defensor.getRango() == -1) {
            return false; // La bomba vence a las piezas que no sean Scout.
        }
        return this.rango >= defensor.getRango();
    }

    public void paint(Graphics2D g2d) {
        // Dejamos un margen para centrar la pieza dentro de la casilla.
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
            // La imagen ocupa el espacio disponible dentro de la casilla.
            g2d.drawImage(imagen, xPos + offset, yPos + offset, size, size, null);
        } else {
            // Si falta la imagen, mostramos un cuadro con el rango de la pieza.
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