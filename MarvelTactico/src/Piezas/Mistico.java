package Piezas;

import Error.ImagenNoLocalizadaException;
import Game.Combates;
import Game.Tablero;

// Rango 2: se mueve cualquier cantidad de casillas en linea recta, como la torre
// Subclase especializada para piezas de tipo Místico.
public final class Mistico extends Piezas {
    public Mistico(Tablero tablero, int col, int fil, Personaje personaje) throws ImagenNoLocalizadaException {
        super(tablero);
        this.col = col;
        this.fil = fil;
        this.xPos = tablero.pxX(col);
        this.yPos = tablero.pxY(fil);
        // El nombre de la imagen, el alias y el rango salen del personaje
        asignarPersonaje(personaje);
    }

    // Recursiva: avanza paso a paso hasta el destino.
    // Base: llegamos al destino (puede ser casilla enemiga, ataque permitido).
    // Se bloquea si hay una pieza o una zona prohibida en el camino.
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

    @Override
    public boolean esMovimientoValido(int col, int fil) {
        int distCol = Math.abs(col - this.col);
        int distFil = Math.abs(fil - this.fil);

        // Diagonal: solo 1 casilla
        if (distCol == 1 && distFil == 1)
            return true;

        if (this.col != col && this.fil != fil)
            return false; // no es recta
        return caminoLibre(this.col, this.fil, col, fil);
    }

    @Override
    public boolean chocaPieza(int col, int fil) {
        return false;
    }

    @Override
    public boolean seRevela(int ncol, int nFil) {
        int dFil = Math.abs(nFil - this.fil);
        int dCol = Math.abs(ncol - this.col);
        return Math.max(dFil, dCol) > 1;
    }

    @Override
    public Combates combate(Piezas defensor) {
        if (defensor.getRango() == -2) {
            return Combates.GANA;
        } else if (defensor.getRango() == -1) {
            return Combates.PIERDE;
        } else {
            if (defensor.getRango() > this.getRango()) {
                return Combates.PIERDE;
            } else if (defensor.getRango() == this.getRango()) {
                return Combates.EMPATA;
            } else {
                return Combates.GANA;
            }
        }
    }
}
