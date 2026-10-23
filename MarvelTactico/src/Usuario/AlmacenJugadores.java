package Usuario;

public interface AlmacenJugadores {
    boolean existeUsuario(String usuario);

    void agregarJugador(Jugador jugador);

    Jugador buscarPorUsuario(String usuario);

    Jugador[] obtenerJugadores();

    int cantidadJugadores();
}
