package Usuario;

import Error.CredencialesInvalidasException;
import Error.InvalidPasswordException;

// Clase encargada de administrar jugadores, puntuaciones y resultados.


public class GestorJugadores implements AlmacenJugadores {
    //Arreglo que maneja la cantidad de jugadores
    private Jugador[] jugadores = new Jugador[100];
    private int cantidad = 0;

    //Arreglo que manera el historial de juegos
    private String[] historial = new String[200];
    private int cantidadHistorial = 0;

    private int victoriasHeroes =0;
    private int victoriasVillanos = 0;


    public void registrarVictorias(boolean ganoHeroe) {
        if (ganoHeroe) {
            victoriasHeroes++;
        } else {
            victoriasVillanos++;
        }
    }

    public int getVictoriasHeroes() {
        return victoriasHeroes;
    }

    public int getVictoriasVillanos() {
        return victoriasVillanos;
    }

    public int getPartidasTotales() {
        return victoriasHeroes + victoriasVillanos;
    }

    public void agregarAlHistorial(String mensaje) {
        if (cantidadHistorial < historial.length) {
            historial[cantidadHistorial] = mensaje;
            cantidadHistorial++;
        }
    }

    public String[] obtenerHistorial() {
        String[] histo = new String[cantidadHistorial];
        for (int i = 0; i < cantidadHistorial; i++) {
            histo[i] = historial[i];
        }
        return histo;
    }

    //Verificacion de si existe el usuario
    public boolean existeUsuario(String usuario) {
        for (int i = 0; i < cantidad; i++) {
            if (jugadores[i].getUsuario().equalsIgnoreCase(usuario)) {
                return true;
            }
        }
        return false;
    }

    //Agregar un jugador nuevo
    public void agregarJugador(Jugador jugador) {
        if (cantidad < jugadores.length) {
            jugadores[cantidad] = jugador;
            cantidad++;
        }
    }

    //Busca la informacion relacionada con el usuario
    public Jugador buscarPorUsuario(String usuario) {
        for (int i = 0; i < cantidad; i++) {
            if (jugadores[i].getUsuario().equalsIgnoreCase(usuario)) {
                return jugadores[i];
            }
        }
        return null;
    }

    //Busca la informaciion de los jugadores
    public Jugador[] obtenerJugadores() {
        Jugador[] player = new Jugador[cantidad];
        for (int i = 0; i < cantidad; i++) {
            player[i] = jugadores[i];
        }
        return player;
    }

    public int cantidadJugadores() {
        return cantidad;
    }

    //Login para los jugadores ya registrados
    public Jugador iniciarSesion(String usuario, String password) throws CredencialesInvalidasException{
        Jugador jugador = buscarPorUsuario(usuario);

        if (jugador == null) {
            throw new CredencialesInvalidasException("Usuario o contraseña incorrectos.");
        }

        if (jugador.getPassword().equals(password) && jugador.isActivo()) {
            return jugador;
        }

        throw new CredencialesInvalidasException("Usuario o contraseña incorrectos.");
    }

    //Creacion del jugador con los campos correspondientes
    public String crearJugador(String usuario, String password) throws InvalidPasswordException {
        if (usuario == null || usuario.trim().length() == 0) {
            return "Debes escribir un nombre de usuario.";
        }

        if (existeUsuario(usuario)) {
            return "Ese nombre de usuario ya existe.";
        }

        if (password == null || password.length() != 5) {
            throw new InvalidPasswordException("La contraseña debe tener exactamente 5 caracteres.");
        }

        if (!password.matches(".*[a-zA-Z].*")) {
            throw new InvalidPasswordException("La contraseña debe tener al menos una letra.");
        }

        if (!password.matches(".*[^a-zA-Z0-9].*")) {
            throw new InvalidPasswordException("La contraseña debe tener al menos un símbolo.");
        }

        Jugador nuevo = new Jugador(usuario, password);
        agregarJugador(nuevo);

        return null;
    }

}
