package Piezas;

import Error.ImagenNoLocalizadaException;
import Game.Tablero;

// El Scout es la única pieza que puede vencer a una bomba.
public class Scout extends Normal {
    public Scout(Tablero tablero, int col, int fil, Personaje personaje) throws ImagenNoLocalizadaException {
        super(tablero, col, fil, personaje);
    }

    public boolean vence(Piezas defensor) {
        if (defensor.getRango() == -1) {
            return true;
        }
        return super.vence(defensor);
    }
}
