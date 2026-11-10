package core;

import Game.Tablero;
import Piezas.Piezas;

//Clase final que se encarga del movimiento de piezas
// Representa un movimiento válido de una pieza desde una casilla a otra.
public final class Mover {
    int nCol;
    int nFil;

    Piezas pieza;
    Piezas captura;

    public Mover(Tablero tablero, Piezas pieza, int nCol, int nFila){
        this.nCol=nCol;
        this.nFil=nFila; 

        this.pieza=pieza;
        this.captura=tablero.getPieza(nCol, nFila);
    }

    public int getnCol() {
        return nCol;
    }

    public int getnFil() {
        return nFil;
    }

    public Piezas getPieza() {
        return pieza;
    }

    public Piezas getCaptura() {
        return captura;
    }

    
}
