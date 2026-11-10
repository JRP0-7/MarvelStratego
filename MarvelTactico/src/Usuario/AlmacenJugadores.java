package Usuario;

// Interfaz para acceder al almacenamiento y gestión de jugadores.


public interface AlmacenJugadores {
    boolean existeUsuario(String usuario);

    void agregarJugador(Jugador jugador);

    Jugador buscarPorUsuario(String usuario);

    Jugador[] obtenerJugadores();

    int cantidadJugadores();
}
