package Piezas;

import Error.ImagenNoLocalizadaException;
import Game.Tablero;

// El Místico recorre varias casillas en línea recta, como una torre.
public class Mistico extends Piezas {
    public Mistico(Tablero tablero, int col, int fil, Personaje personaje) throws ImagenNoLocalizadaException {
        super(tablero);
        this.col = col;
        this.fil = fil;
        this.xPos = tablero.pxX(col);
        this.yPos = tablero.pxY(fil);
        // El personaje define el nombre, el alias y el rango de la pieza.
        asignarPersonaje(personaje);
    }

    // Revisa cada casilla del recorrido; solo el destino puede tener una pieza enemiga.
    private boolean caminoLibre(int c, int f, int destC, int destF) {
        if (c == destC && f == destF)
            return true;
        if (tablero.esZonaProhibida(c, f))
            return false;
        if (tablero.getPieza(c, f) != null && !(c == this.col && f == this.fil))
            return false;
        int pasoC = Integer.compare(destC, c);
        int pasoF = Integer.compare(destF, f);
        return caminoLibre(c + pasoC, f + pasoF, destC, destF);
    }

    public boolean esMovimientoValido(int col, int fil) {
        int distCol = Math.abs(col - this.col);
        int distFil = Math.abs(fil - this.fil);

        // En diagonal, solo puede avanzar una casilla.
        if (distCol == 1 && distFil == 1)
            return true;

        if (this.col != col && this.fil != fil)
            return false; // El movimiento no va en línea recta.
        return caminoLibre(this.col, this.fil, col, fil);
    }

    public boolean chocaPieza(int col, int fil) {
        return false;
    }
}
