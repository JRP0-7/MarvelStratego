package Error;

// Excepción para contraseñas no válidas o con formato incorrecto.


public class InvalidPasswordException extends Exception{
    public InvalidPasswordException(String mensaje) {
        super(mensaje);
    }
}
