package Piezas;

public interface Movable {
    public boolean esMovimientoValido(int col, int fil);
    public boolean chocaPieza(int col, int fil);
}
