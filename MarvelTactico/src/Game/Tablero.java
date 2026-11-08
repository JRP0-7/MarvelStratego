package Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import Error.ImagenNoLocalizadaException;
import Piezas.*;
import UI.Fondo;
import Usuario.GestorJugadores;
import Usuario.Jugador;
import core.Entrada;
import core.Mover;

public class Tablero extends JPanel {
    public int tCasillas = 72;
    public static final int margen = 48;

    int col = 10;
    int fil = 10;

    ArrayList<Piezas> piezasJuego = new ArrayList<>();

    public ArrayList<Piezas> capturadasHeroe = new ArrayList<>();
    public ArrayList<Piezas> capturadasVillano = new ArrayList<>();

    // Casillas del centro donde no se puede colocar ni mover ninguna pieza
    private boolean[][] zonasProhibidas = new boolean[col][fil];

    // Pieza que se encuentra elegida por mover
    public Piezas piezaElegida;

    // Siempre empiezan los heroes
    public boolean turnoHeroe = true;

    // Fin de la partida
    public boolean partidaTerminada = false;
    public String mensajeFinal = null;

    // Ultimo mensaje del combate
    public String ultimoMensaje = null;

    public Jugador jugadorHeroe; // Jugador con sesión iniciada
    public Jugador jugadorVillano; // Jugador oponente
    public Jugador jugadorActual;
    public GestorJugadores gestor; // Variable para sumarle puntos al jugador ganador

    public String nombreDe(boolean esHeroe) {
        if (esHeroe) {
            if (jugadorHeroe != null) {
                return jugadorHeroe.getUsuario(); // Nombre del jugador con los heroes
            }
            return "Heroes"; // Error si no se detecta el nombre
        } else {
            if (jugadorVillano != null) {
                return jugadorVillano.getUsuario(); // Nombre del jugador con los villanos
            }
            return "Villanos"; // Error si no se detecta el nombre
        }
    }

    Entrada entra = new Entrada(this);
    Image fondoTablero = Fondo.icono("tablero2.jpeg", col * tCasillas + 2 * margen, fil * tCasillas + 2 * margen)
            .getImage();

    // Conversiones entre casillas y pixeles
    public int pxX(int c) {
        return margen + c * tCasillas;
    }

    public int pxY(int f) {
        return margen + f * tCasillas;
    }

    public int colDe(int x) {
        return (x - margen) / tCasillas;
    }

    public int filDe(int y) {
        return (y - margen) / tCasillas;
    }

    public boolean esCelda(int c, int f) {
        return c >= 0 && c < col && f >= 0 && f < fil;
    }

    public Tablero() {
        this.setPreferredSize(new Dimension(col * tCasillas + 2 * margen, fil * tCasillas + 2 * margen));
        crearZona();
        generarPiezas();

        this.addMouseListener(entra);
        this.addMouseMotionListener(entra);

        ToolTipManager.sharedInstance().registerComponent(this);
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        int columnaM = colDe(event.getX());
        int filaM = filDe(event.getY());

        if (!esCelda(columnaM, filaM)) {
            return null;
        }
        Piezas piezaBajoMouse = getPieza(columnaM, filaM);
        
        if (piezaBajoMouse == null) {
            return null;
        }

        if (!sePuedeVer(piezaBajoMouse)) {
            return null;
        }

        return "<html>" + piezaBajoMouse.getAlias() + "</html>";
    }

    // Valida si es movimiento valido de pieza
    public boolean esMovimientoValido(Mover mov) {
        if (mismoEquipo(mov.getPieza(), mov.getCaptura())) {
            return false;
        }

        if (esZonaProhibida(mov.getnCol(), mov.getnFil())) {
            return false;
        }

        if (!mov.getPieza().esMovimientoValido(mov.getnCol(), mov.getnFil())) {
            return false;
        }

        if (mov.getPieza().chocaPieza(mov.getnCol(), mov.getnFil())) {
            return false;
        }

        return true;
    }

    // El jugador que tiene el turno en este momento se retira de la partida.
    public void retirarse() {
        boolean ganoElHeroe = !turnoHeroe; // el que tenia el turno es el que se retira

        terminarPartida(Resultados.RETIRO, ganoElHeroe);
    }

    // Suma los 3 puntos al jugador ganador y guarda el resultado en el historial de
    // cada jugador
    private void registrarResultado(boolean ganoHeroe) {
        if (gestor == null) {
            return;
        }

        gestor.registrarVictorias(ganoHeroe);

        if(ganoHeroe){
            jugadorHeroe.sumarPuntos(true);
            jugadorVillano.sumarPartidas(false);
        }
        else{
            jugadorVillano.sumarPuntos(false);
            jugadorHeroe.sumarPartidas(true);
        }
        gestor.agregarAlHistorial(jugadorHeroe.getUsuario() + " - " + mensajeFinal);
        gestor.agregarAlHistorial(jugadorVillano.getUsuario() + " - " + mensajeFinal);

    }

    public void pasarTurno() {
        turnoHeroe = !turnoHeroe;
        piezaElegida = null;
        repaint();
    }

    public boolean mismoEquipo(Piezas p1, Piezas p2) {
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.esHeroe == p2.esHeroe;
    }

    public void moverPieza(Mover mov) {
        if (mov.getCaptura() != null) {
            mov.getPieza().visible = true;
            mov.getCaptura().visible = true;
        }

        if (mov.getPieza().seRevela(mov.getnCol(), mov.getnFil())) {
            mov.getPieza().visible = true;
        }

        if (mov.getCaptura() != null) {
            Combates muereE = atacar(mov.getPieza(), mov.getCaptura());

            // El defensor muere si pierde o si hay empate
            if (muereE == Combates.GANA) {
                capturar(mov.getCaptura());
                moverPosicion(mov);
            }else if ( muereE ==  Combates.EMPATA) { // El atacante muere si pierde o si hay empate, si no ocupa la casilla
                capturar(mov.getPieza());
                capturar(mov.getCaptura());
            } else {
                capturar(mov.getPieza());
            }
        } else {
            moverPosicion(mov);
        }

        pasarTurno();

        // Si el jugador del turno no puede mover nada, pierde la partida
        if (!partidaTerminada && !tieneMovimientos(turnoHeroe)) {
            sinMovimientos();
        }
    }

    private void moverPosicion(Mover mov) {
        mov.getPieza().col = mov.getnCol();
        mov.getPieza().fil = mov.getnFil();
        mov.getPieza().xPos = pxX(mov.getnCol());
        mov.getPieza().yPos = pxY(mov.getnFil());
    }

    public boolean sePuedeVer(Piezas pieza) {
        return pieza.visible || pieza.esHeroe == turnoHeroe || partidaTerminada;
    }

    // Saca la pieza del tablero y la manda al panel de piezas vencidas
    public void capturar(Piezas pieza) {
        piezasJuego.remove(pieza);

        if (pieza.esHeroe) {
            capturadasHeroe.add(pieza);
        } else {
            capturadasVillano.add(pieza);
        }

        verificarVictoria(pieza);
    }

    // Devuelve true si el defensor muere
    public Combates atacar(Piezas atacante, Piezas defensor) {
        Combates resultado = atacante.combate(defensor);

        armarMensajeAtaque(atacante, defensor, resultado);

        return resultado;
    }

    // Mensaje de combate que se muestra en el panel lateral
    private void armarMensajeAtaque(Piezas atacante, Piezas defensor, Combates res) {
        String nombreDefensor = nombreDe(defensor.esHeroe);
        String nombreAtacante = nombreDe(atacante.esHeroe);

        if (res == Combates.GANA) {
            ultimoMensaje = nombreAtacante + " destruyo la pieza " + defensor.getAlias() + " del jugador "
                    + nombreDefensor;
        } else {
            ultimoMensaje = nombreAtacante + " ataco la pieza " + defensor.getAlias() + "";
        }
    }

    // Cierra la partida y guarda el resultado
    private void terminarPartida(Resultados motivo, boolean ganoElHeroe) {
        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime());
        mensajeFinal = motivo.mensaje(nombreDe(ganoElHeroe), nombreDe(!ganoElHeroe), ganoElHeroe) + " - " + fecha ;
        partidaTerminada = true;
        registrarResultado(ganoElHeroe);
        piezaElegida = null;
        repaint();
    }

    // Se gana capturando la Tierra o destruyendo todas las piezas moviles del rival
    private void verificarVictoria(Piezas capturada) {
        if (partidaTerminada) {
            return;
        }

        boolean equipoAfectado = capturada.esHeroe;
        boolean ganoElHeroe = !equipoAfectado;

        boolean quedanMoviles = false;

        for (Piezas p : piezasJuego) {
            if (p.esHeroe == equipoAfectado && p.getRango() > 0) {
                quedanMoviles = true;
            }
        }

        if (capturada.getRango() == -2) {
            terminarPartida(Resultados.CAPTURA_TIERRA, ganoElHeroe);
        } else if (quedanMoviles == false) {
            terminarPartida(Resultados.PIEZAS_INMOVILES, ganoElHeroe);
        }
    }

    // Revisa si alguna pieza del equipo tiene un movimiento valido
    private boolean tieneMovimientos(boolean esHeroe) {
        return tieneMovimientos(esHeroe, 0, 0);
    }

    private boolean tieneMovimientos(boolean esHeroe, int c, int f) {
        if (f >= fil) {
            return false;
        }
        for (Piezas p : piezasJuego) {
            if (p.esHeroe == esHeroe && esMovimientoValido(new Mover(this, p, c, f))) {
                return true;
            }
        }
        if (c + 1 < col) {
            return tieneMovimientos(esHeroe, c + 1, f);
        }
        return tieneMovimientos(esHeroe, 0, f + 1);
    }

    // El jugador del turno pierde porque no tiene movimientos validos
    private void sinMovimientos() {
        boolean ganoElHeroe = !turnoHeroe;

        terminarPartida(Resultados.MOVIMIENTOS_INSUFICIENTES, ganoElHeroe);
    }

    public Piezas getPieza(int col, int fil) {
        return getPieza(0, col, fil);
    }

    private Piezas getPieza(int i, int col, int fil) {
        if (i >= piezasJuego.size()) {
            return null;
        }

        Piezas p = piezasJuego.get(i);
        if (p.getCol() == col && p.getFil() == fil) {
            return p;
        }
        return getPieza(i + 1, col, fil);
    }

    // Marca un cuadro de 2x2 casillas como zona prohibida
    private void marcarZona(int colI, int filI) {
        for (int f = filI; f < filI + 2; f++) {
            for (int c = colI; c < colI + 2; c++) {
                zonasProhibidas[f][c] = true;
            }
        }
    }

    private void crearZona() {
        marcarZona(2, 4);
        marcarZona(6, 4);
    }

    public boolean esZonaProhibida(int c, int f) {
        return zonasProhibidas[f][c];
    }

    // Villanos en filas 0-3 y heroes en filas 6-9, 40 piezas por bando
    public void generarPiezas() {
        piezasJuego.clear();
        colocarBando(false);
        colocarBando(true);
    }

    private void colocarBando(boolean esHeroe) {
        Random rnd = new Random();

        // Fila del fondo, fila del frente y hacia donde se avanza desde el fondo
        int fondo = esHeroe ? 9 : 0;
        int frente = esHeroe ? 6 : 3;
        int haciaFrente = esHeroe ? -1 : 1;

        // Casillas que ocupa este bando
        ArrayList<Point> celdas = new ArrayList<>();
        for (int f = Math.min(fondo, frente); f <= Math.max(fondo, frente); f++) {
            for (int c = 0; c < col; c++) {
                celdas.add(new Point(c, f));
            }
        }

        // La Tierra va en la ultima fila (no en columna 0 ni 9) con 3 bombas alrededor
        int colTierra = 1 + rnd.nextInt(col - 2);
        agregarPieza(colTierra, fondo, esHeroe, -2);
        agregarPieza(colTierra - 1, fondo, esHeroe, -1);
        agregarPieza(colTierra + 1, fondo, esHeroe, -1);
        agregarPieza(colTierra, fondo + haciaFrente, esHeroe, -1);
        celdas.remove(new Point(colTierra, fondo));
        celdas.remove(new Point(colTierra - 1, fondo));
        celdas.remove(new Point(colTierra + 1, fondo));
        celdas.remove(new Point(colTierra, fondo + haciaFrente));

        // Las 3 bombas restantes van en las ultimas 2 filas
        ArrayList<Point> opcionesBomba = new ArrayList<>();
        for (Point p : celdas) {
            if (p.y == fondo || p.y == fondo + haciaFrente) {
                opcionesBomba.add(p);
            }
        }
        for (int i = 0; i < 3 && !opcionesBomba.isEmpty(); i++) {
            Point p = opcionesBomba.get(rnd.nextInt(opcionesBomba.size()));
            agregarPieza(p.x, p.y, esHeroe, -1);
            celdas.remove(p);
            opcionesBomba.remove(p);
        }

        ArrayList<Personaje> rango2 = new ArrayList<>();
        ArrayList<Personaje> resto = new ArrayList<>();
        for (Personaje pj : Personaje.deBando(esHeroe)) {
            if (pj.getRango() == 2) {
                rango2.add(pj);
            } else {
                resto.add(pj);
            }
        }

        // Los de Rango 2 van en las 2 filas del frente
        ArrayList<Point> opcionesR2 = new ArrayList<>();
        for (Point p : celdas) {
            if (p.y == frente || p.y == frente - haciaFrente) {
                opcionesR2.add(p);
            }
        }
        while (!rango2.isEmpty() && !opcionesR2.isEmpty()) {
            Personaje pj = rango2.remove(rnd.nextInt(rango2.size()));
            Point p = opcionesR2.get(rnd.nextInt(opcionesR2.size()));
            agregarPieza(p.x, p.y, pj);
            celdas.remove(p);
            opcionesR2.remove(p);
        }

        // Primero se llena la fila del frente y luego se rellena con las piezas
        // sobrantes
        while (!celdas.isEmpty() && !resto.isEmpty()) {
            ArrayList<Point> opciones = new ArrayList<>();
            for (Point p : celdas) {
                if (p.y == frente) {
                    opciones.add(p);
                }
            }
            if (opciones.isEmpty()) {
                opciones = celdas;
            }
            Point p = opciones.get(rnd.nextInt(opciones.size()));
            Personaje pj = resto.remove(rnd.nextInt(resto.size()));
            agregarPieza(p.x, p.y, pj);
            celdas.remove(p);
        }
    }

    // Agrega una bomba (-1) o la tierra (-2)
    private void agregarPieza(int c, int f, boolean esHeroe, int rango) {
        try {
            Piezas p;
            if (rango == -1) {
                p = new Bomba(this, c, f, esHeroe);
            } else {
                p = new Tierra(this, c, f, esHeroe);
            }
            p.setPos(c, f, pxX(c), pxY(f));
            piezasJuego.add(p);
        } catch (ImagenNoLocalizadaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Imagen no encontrada", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Agrega un personaje con la clase que le corresponde segun su rango
    private void agregarPieza(int c, int f, Personaje pj) {
        try {
            Piezas p;
            if (pj.getRango() == 1) {
                p = new Agente(this, c, f, pj);
            } else if (pj.getRango() == 2) {
                p = new Mistico(this, c, f, pj);
            } else if (pj.getRango() == 3) {
                p = new Scout(this, c, f, pj);
            } else {
                p = new Normal(this, c, f, pj);
            }
            p.setPos(c, f, pxX(c), pxY(f));
            piezasJuego.add(p);
        } catch (ImagenNoLocalizadaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Imagen no encontrada", JOptionPane.ERROR_MESSAGE);        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Fondo escalado al panel entero
        if (fondoTablero != null) {
            g2d.drawImage(fondoTablero, 0, 0, getWidth(), getHeight(), null);
        }

        // Grilla de juego
        g2d.setColor(new Color(60, 60, 60));
        g2d.setStroke(new BasicStroke(2));
        for (int i = 0; i <= col; i++) {
            g2d.drawLine(pxX(i), pxY(0), pxX(i), pxY(fil));
        }
        for (int j = 0; j <= fil; j++) {
            g2d.drawLine(pxX(0), pxY(j), pxX(col), pxY(j));
        }

        // Zonas prohibidas (sombra roja)
        g2d.setColor(new Color(200, 50, 50, 100));
        for (int f = 0; f < fil; f++) {
            for (int c = 0; c < col; c++) {
                if (esZonaProhibida(c, f)) {
                    g2d.fillRect(pxX(c) + 2, pxY(f) + 2, tCasillas - 4, tCasillas - 4);
                }
            }
        }

        // Dibujar las piezas que esten en el tablero
        for (Piezas p : piezasJuego) {
            if (p != null) {
                p.paint(g2d);
            }
        }

        // Rellenar la casilla de la pieza seleccionada
        if (piezaElegida != null) {
            g2d.setColor(new Color(230, 190, 30, 110));
            g2d.fillRect(pxX(piezaElegida.getCol()) + 2, pxY(piezaElegida.getFil()) + 2, tCasillas - 4, tCasillas - 4);
        }

        // Dibujar con borde las casillas a las que se puede mover o atacar
        if (piezaElegida != null) {
            g2d.setStroke(new BasicStroke(4));
            for (int f = 0; f < fil; f++) {
                for (int c = 0; c < col; c++) {
                    if (esMovimientoValido(new Mover(this, piezaElegida, c, f))) {
                        boolean esAtaque = getPieza(c, f) != null;

                        if (esAtaque) {
                            g2d.setColor(new Color(200, 40, 40));
                        } else {
                            g2d.setColor(new Color(68, 180, 57));
                        }

                        g2d.drawRect(pxX(c) + 3, pxY(f) + 3, tCasillas - 6, tCasillas - 6);
                    }
                }
            }
        }
    }

    public int getFil() {
        return this.fil;
    }

    public Jugador getJugadorHeroe() {
        return jugadorHeroe;
    }

    public Jugador getJugadorVillano() {
        return jugadorVillano;
    }

    public void setJugadorHeroe(Jugador j) {
        this.jugadorHeroe = j;
    }

    public void setJugadorVillano(Jugador j) {
        this.jugadorVillano = j;
    }
}
