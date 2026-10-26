package Piezas;

import Error.ImagenNoLocalizadaException;
import Game.Tablero;

public class Bomba extends Piezas{
    public Bomba(Tablero tablero, int col, int fil, boolean esHeroe) throws ImagenNoLocalizadaException {
        super(tablero);
        this.col = col;
        this.fil = fil;
        this.esHeroe = esHeroe;
        this.rango = -1;
        this.xPos = tablero.pxX(col);
        this.yPos = tablero.pxY(fil);

        // El alias se muestra en el juego y el nombre identifica su imagen.
        if (esHeroe) {
            this.alias = "Nova Blast";
            this.nombre = "bomba_nova";
        } else {
            this.alias = "Pumpkin Bomb";
            this.nombre = "bomba_calabaza";
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
