package Piezas;

import Error.ImagenNoLocalizadaException;
import Game.Combates;
import Game.Tablero;

// El Scout es la única pieza que puede vencer a una bomba.
// Subclase especializada para piezas tipo Scout.
public final class Scout extends Normal {
    public Scout(Tablero tablero, int col, int fil, Personaje personaje) throws ImagenNoLocalizadaException {
        super(tablero, col, fil, personaje);
    }


    @Override
    public Combates combate(Piezas defensor) {
        if(defensor.getRango()==-1){
            return Combates.GANA;
        }
        return super.combate(defensor);
    }

}
