package Error;

// Excepción para credenciales inválidas durante el inicio de sesión.


public class CredencialesInvalidasException extends Exception {

    public CredencialesInvalidasException(String msg) {
        super(msg);
    }
}
