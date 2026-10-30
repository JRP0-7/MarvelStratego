package UI;

import javax.swing.*;

import Error.ImagenNoLocalizadaException;
import Game.Tablero;
import Usuario.GestorJugadores;
import Usuario.Jugador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SeleccionOponente extends Fondo {

    JFrame marco;
    GestorJugadores gestor;
    Jugador jugadorActual;

    JComboBox<String> listaOponentes;
    ArrayList<Jugador> oponentesDisponibles;

    public SeleccionOponente(JFrame marco, GestorJugadores gestor, Jugador jugadorActual) {
        super("background.jpeg");
        this.marco = marco;
        this.gestor = gestor;
        this.jugadorActual = jugadorActual;

        this.setLayout(new GridBagLayout());

        JPanel panelCentro = new JPanel();
        panelCentro.setOpaque(false);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Elegir oponente");
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentro.add(titulo);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));

        oponentesDisponibles = buscarOponentes();

        if (oponentesDisponibles.isEmpty()) {
            JLabel labelSinOponentes = new JLabel("No hay otros jugadores registrados todavia.");
            labelSinOponentes.setForeground(Color.yellow);
            labelSinOponentes.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelCentro.add(labelSinOponentes);
            panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));

        } else {
            String[] nombres = new String[oponentesDisponibles.size()];
            for (int i = 0; i < oponentesDisponibles.size(); i++) {
                nombres[i] = oponentesDisponibles.get(i).getUsuario();
            }

            listaOponentes = new JComboBox<String>(nombres);
            listaOponentes.setMaximumSize(new Dimension(200, 30));
            listaOponentes.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelCentro.add(listaOponentes);
            panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));

            JButton botonIniciar = new JButton("Iniciar partida");
            botonIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
            botonIniciar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    iniciarPartida();
                }
            });
            panelCentro.add(botonIniciar);
            panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JButton botonVolver = new JButton("Volver");
        botonVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlMenuPrincipal();
            }
        });
        panelCentro.add(botonVolver);

        this.add(panelCentro);
    }

    // Todos los jugadores activos, menos el que tiene la sesion iniciada.
    private ArrayList<Jugador> buscarOponentes() {
        ArrayList<Jugador> lista = new ArrayList<Jugador>();
        Jugador[] todos = gestor.obtenerJugadores();

        for (int i = 0; i < todos.length; i++) {
            Jugador j = todos[i];
            if (j.isActivo() && !j.getUsuario().equalsIgnoreCase(jugadorActual.getUsuario())) {
                lista.add(j);
            }
        }

        return lista;
    }

    private void iniciarPartida() {
        int indiceElegido = listaOponentes.getSelectedIndex();
        Jugador oponente = oponentesDisponibles.get(indiceElegido);

        Tablero tablero = new Tablero();
        tablero.setJugadorHeroe(jugadorActual);
        tablero.setJugadorVillano(oponente);
        tablero.gestor = gestor;

        PanelInfo panelInfo = new PanelInfo(tablero);

        JPanel panelJuego = new Fondo("battle.jpeg");
        panelJuego.setLayout(new BorderLayout());

        // Envoltorio transparente para que el tablero no se estire y se vea el fondo alrededor
        JPanel vistaTablero = new JPanel();
        vistaTablero.setOpaque(false);
        vistaTablero.setLayout(new GridBagLayout());
        vistaTablero.add(tablero);

        panelJuego.add(vistaTablero, BorderLayout.CENTER);
        panelJuego.add(panelInfo, BorderLayout.EAST);

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(panelJuego, BorderLayout.CENTER);

        marco.revalidate();
        marco.repaint();
    }

    private void volverAlMenuPrincipal() {
        MenuPrincipal menuPrincipal = new MenuPrincipal(marco, gestor, jugadorActual);

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(menuPrincipal, BorderLayout.CENTER);

        marco.revalidate();
        marco.repaint();
    }
}
