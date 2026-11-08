package Piezas;

import Error.ImagenNoLocalizadaException;
import Game.Combates;
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

        // Nombre de la pieza en el juego y archivo de imagen
        if (esHeroe) {
            this.alias = "Nova Blast";
            this.nombre = "bomba_nova";
        } else {
            this.alias = "Pumpkin Bomb";
            this.nombre = "bomba_calabaza";
        }
        cargarImagenes(this.nombre);
    }

    @Override
    public boolean esMovimientoValido(int x, int y) {
        return false;
    }

    @Override
    public boolean chocaPieza(int col, int fil) {
        return false;
    }

    @Override
    public boolean seRevela(int ncol, int nFil) {
        return false;
    }

    @Override
    public Combates combate(Piezas defensor) {
        return Combates.PIERDE;
    }
}
