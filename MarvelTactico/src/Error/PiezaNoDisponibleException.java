package Error;

// Excepcion que se lanza cuando una pieza no esta disponible para el movimiento
public class PiezaNoDisponibleException extends Exception {
    public PiezaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
