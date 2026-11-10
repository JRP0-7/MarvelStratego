package Piezas;

// Interfaz que define el contrato de movimiento de las piezas.


public interface Movable {
    public boolean esMovimientoValido(int col, int fil);
    public boolean chocaPieza(int col, int fil);
}
