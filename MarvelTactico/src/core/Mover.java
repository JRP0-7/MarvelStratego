package core;

import Game.Tablero;
import Piezas.Piezas;

//Clase final que se encarga del movimiento de piezas
public final class Mover {
    int aFil;
    int aCol;
    int nCol;
    int nFil;

    Piezas pieza;
    Piezas captura;

    public Mover(Tablero tablero, Piezas pieza, int nCol, int nFila){
        this.aCol=pieza.getCol();
        this.aFil=pieza.getFil();
        this.nCol=nCol;
        this.nFil=nFila; 

        this.pieza=pieza;
        this.captura=tablero.getPieza(nCol, nFila);
    }

    public int getaCol() {
        return aCol;
    }

    public int getaFil() {
        return aFil;
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
