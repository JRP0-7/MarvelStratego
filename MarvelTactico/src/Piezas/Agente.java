package Piezas;

import Error.ImagenNoLocalizadaException;
import Game.Combates;
import Game.Tablero;

// El Agente suele perder por su rango, pero puede derrotar al rango 10.
public class Agente extends Normal {
    public Agente(Tablero tablero, int col, int fil, Personaje personaje) throws ImagenNoLocalizadaException {
        super(tablero, col, fil, personaje);
    }

    @Override
    public Combates combate(Piezas defensor) {
        if(defensor.getRango()==10){
            return Combates.GANA;
        }
        return super.combate(defensor);
    }
}
