package Piezas;

import Error.ImagenNoLocalizadaException;
import Game.Tablero;

public class Tierra extends Piezas {
    public Tierra(Tablero tablero, int col, int fil, boolean esHeroe) throws ImagenNoLocalizadaException {
        super(tablero);
        this.col = col;
        this.fil = fil;
        this.esHeroe = esHeroe;
        this.rango = -2;
        this.alias = "Tierra";
        this.xPos = tablero.pxX(col);
        this.yPos = tablero.pxY(fil);

        // Cada bando usa su propia imagen de Tierra.
        if (esHeroe) {
            this.nombre = "tierra_heroes";
        } else {
            this.nombre = "tierra_villanos";
        }
        cargarImagenes(this.nombre);
    }

    public boolean esMovimientoValido(int col, int fil) {
        return false;
    }

    public boolean chocaPieza(int col, int fil) {
        return false;
    }
}
