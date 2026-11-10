package Piezas;

import Error.ImagenNoLocalizadaException;
import Game.Combates;
import Game.Tablero;

// Las piezas de rango 4 a 10 avanzan una casilla en horizontal o vertical.
// Pieza base con comportamiento estándar del juego.
public class Normal extends Piezas {
    public Normal(Tablero tablero, int col, int fil, Personaje personaje) throws ImagenNoLocalizadaException {
        super(tablero);
        this.col = col;
        this.fil = fil;
        this.xPos = tablero.pxX(col);
        this.yPos = tablero.pxY(fil);
        // El personaje define el nombre, el alias y el rango de la pieza.
        asignarPersonaje(personaje);
    }

    @Override
    public boolean esMovimientoValido(int col, int fil) {
        int distCol = Math.abs(col - this.col);
        int distFil = Math.abs(fil - this.fil);
        return distCol + distFil == 1;   // Debe avanzar una casilla, sin diagonales.
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
        if(defensor.getRango()==-2){
            return Combates.GANA;
        }else if (defensor.getRango() == -1) {
            return Combates.PIERDE;
        } else{
            if(defensor.getRango()>this.getRango()){
                return Combates.PIERDE;
            }else if(defensor.getRango() == this.getRango()){
                return Combates.EMPATA;
            }else{
                return Combates.GANA;
            }
        }
    }
}
