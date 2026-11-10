package core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import Game.Tablero;
import Piezas.Piezas;



// Maneja eventos del mouse para seleccionar y mover piezas en el tablero.






public class Entrada extends MouseAdapter {
    Tablero tablero;

    public Entrada(Tablero tablero){
        this.tablero=tablero;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(tablero.partidaTerminada){
            return;
        }

        int col = tablero.colDe(e.getX());
        int fil = tablero.filDe(e.getY());

        if (!tablero.esCelda(col, fil)) {
            return;
        }

        Piezas piezaXY = tablero.getPieza(col, fil);
        if(piezaXY != null && puedeSeleccionar(piezaXY)){
            tablero.piezaElegida = piezaXY;
            tablero.repaint();

        }
    }

    // Solo se selecciona la ficha del equipo actual
    private boolean puedeSeleccionar(Piezas pieza){
        return pieza.esHeroe == tablero.turnoHeroe;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int col = tablero.colDe(e.getX());
        int fil = tablero.filDe(e.getY());

        if (tablero.piezaElegida != null) {

            boolean esClick = tablero.esCelda(col, fil)
                    && (col == tablero.piezaElegida.col && fil == tablero.piezaElegida.fil);

            if (esClick) {
                tablero.piezaElegida.xPos = tablero.pxX(tablero.piezaElegida.col);
                tablero.piezaElegida.yPos = tablero.pxY(tablero.piezaElegida.fil);
                tablero.repaint();
                return;
            }

            boolean sueltoDentro = tablero.esCelda(col, fil);
            Mover mov = null;
            if (sueltoDentro) {
                mov = new Mover(tablero, tablero.piezaElegida, col, fil);
            }

            if (mov != null && tablero.esMovimientoValido(mov)) {
                tablero.moverPieza(mov);
            }
            else {
                tablero.piezaElegida.xPos = tablero.pxX(tablero.piezaElegida.col);
                tablero.piezaElegida.yPos = tablero.pxY(tablero.piezaElegida.fil);

            }

            tablero.piezaElegida=null;
        }

        tablero.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e){
        if(tablero.piezaElegida!=null){
            tablero.piezaElegida.xPos=e.getX()-tablero.tCasillas/2;
            tablero.piezaElegida.yPos=e.getY()-tablero.tCasillas/2;

            tablero.repaint();
        }
    }
    
}
