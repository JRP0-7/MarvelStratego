package UI;

import javax.swing.*;

import Usuario.GestorJugadores;
import Usuario.Jugador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Pantalla para ver reportes, historial y estadísticas del juego.


public class Reportes extends Fondo {

    JFrame marco;
    GestorJugadores gestor;
    Jugador jugadorActual;
    String tarjetaInicial; // tarjeta con la que se abrió (ranking, historial o batallas)

    JPanel panelContenido;
    CardLayout cardLayout;

    public Reportes(JFrame marco, GestorJugadores gestor, Jugador jugadorActual, String tarjetaInicial) {
        super("golden_city.jpeg");
        this.marco = marco;
        this.gestor = gestor;
        this.jugadorActual = jugadorActual;
        this.tarjetaInicial = tarjetaInicial;

        this.setLayout(new BorderLayout());

        JButton botonVolver = new ButtonDesign("Volver");
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlSelector();
            }
        });
        botonVolver.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setOpaque(false);
        panelContenido.add(crearPanelRanking(), "ranking");
        panelContenido.add(crearPanelHistorial(), "historial");
        panelContenido.add(crearPanelBatallas(), "batallas");

        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panelBotones.add(panelContenido, c);

        c.gridy = 1;
        c.insets = new Insets(15, 0, 0, 0);
        panelBotones.add(botonVolver, c);

        this.add(panelBotones, BorderLayout.CENTER);

        // Muestra la tarjeta que se eligio en el selector
        cardLayout.show(panelContenido, tarjetaInicial);
    }

    private JPanel crearPanelRanking() {
        Jugador[] todos = gestor.obtenerJugadores();

        // El ranking solo muestra jugadores activos
        ArrayList<Jugador> soloActivos = new ArrayList<Jugador>();
        for (int i = 0; i < todos.length; i++) {
            if (todos[i].isActivo()) {
                soloActivos.add(todos[i]);
            }
        }

        JLabel titulo = new JLabel("UNIVERSO MARVEL", SwingConstants.CENTER){
            protected void paintComponent(Graphics g){
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0,0,0,170));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(8, 30, 8, 30));

        Jugador[] jugadores = soloActivos.toArray(new Jugador[0]);

        // Ordenamos por puntos de mayor a menor con quicksort (recursivo)
        ordenarPorPuntos(jugadores, jugadores.length );

        String[] columnas = { "Posición", "Usuario", "Puntos", "Puntos Heroe", "Puntos Villano" };
        String[][] filas = new String[jugadores.length][5];

        for (int i = 0; i < jugadores.length; i++) {
            filas[i][0] = "" + (i + 1);
            filas[i][1] = jugadores[i].getUsuario();
            filas[i][2] = "" + jugadores[i].getTotalPoints();
            filas[i][3] = "" + jugadores[i].getPuntosH();
            filas[i][4] = "" + jugadores[i].getPuntosV();
        }

        JTable tabla = new JTable(filas, columnas);
        tabla.setEnabled(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JScrollPane tablaS = new JScrollPane(tabla);
        tablaS.setSize(new Dimension(750, 500));
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(tablaS, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelBatallas() {
        int activos = 0;
        Jugador[] todos = gestor.obtenerJugadores();

        // El ranking solo muestra jugadores activos
        for (int i = 0; i < todos.length; i++) {
            if (todos[i].isActivo()) {
                activos++;
            }
        }

        String[] datos = {
                "Usuarios activos: " + activos,
                "Usuarios totales: " + gestor.cantidadJugadores(),
                "Partidas jugadas: " + gestor.getPartidasTotales(),
                "Victorias de héroes: " + gestor.getVictoriasHeroes(),
                "Victorias de villanos: " + gestor.getVictoriasVillanos()
        };

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 170));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Multiverso de Batallas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 50));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);

        for (int index = 0; index < datos.length; index++) {
            JLabel etiqueta = new JLabel(datos[index]);
            etiqueta.setForeground(Color.white);
            etiqueta.setFont(new Font("Arial", Font.PLAIN, 20));
            etiqueta.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(etiqueta);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setOpaque(false);
        panelCentro.add(panel);
        return panelCentro;

    }

     // Ordena de mayor a menor puntaje: ordena los primeros n-1 jugadores
    // de forma recursiva y luego coloca al último en su posición correcta.
    private void ordenarPorPuntos(Jugador[] arreglo, int n) {
        // Caso base: con uno o ningún jugador no hay nada que ordenar
        if (n <= 1) {
            return;
        }

        // Ordena los primeros n-1 elementos
        ordenarPorPuntos(arreglo, n - 1);

        // Inserta el último en su posición dentro de la parte ya ordenada
        Jugador ultimo = arreglo[n - 1];
        int j = n - 2;

        // Corre una posición a la derecha a los que tienen MENOS puntos que el último
        while (j >= 0 && arreglo[j].getTotalPoints() < ultimo.getTotalPoints()) {
            arreglo[j + 1] = arreglo[j];
            j--;
        }
        arreglo[j + 1] = ultimo;
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] historialCompleto = gestor.obtenerHistorial();

        JLabel titulo = new JLabel("UNIVERSO MARVEL-HISTORIAL", SwingConstants.CENTER){
            protected void paintComponent(Graphics g){
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0,0,0,170));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(8, 30, 8, 30));

        JLabel etiqueta = new JLabel(
                "Historial de " + jugadorActual.getUsuario() + " (del mas reciente al mas antiguo)");
        etiqueta.setForeground(Color.white);
        etiqueta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(etiqueta);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        boolean hayRegistros = false;

        // Se recorre del mas reciente al mas antiguo, mostrando solo los jugadores
        // activos
        for (int i = historialCompleto.length - 1; i >= 0; i--) {
            String registro = historialCompleto[i];

            if (registro.startsWith(jugadorActual.getUsuario() + " - ")) {
                JLabel labelRegistro = new JLabel(registro);
                labelRegistro.setForeground(Color.lightGray);
                labelRegistro.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(labelRegistro);
                hayRegistros = true;
            }
        }

        if (!hayRegistros) {
            JLabel labelVacio = new JLabel("Todavia no tienes partidas registradas.");
            labelVacio.setForeground(Color.lightGray);
            labelVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(labelVacio);
        }

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setPreferredSize(new Dimension(750, 500));
        JPanel envoltorio = new JPanel(new GridBagLayout());
        envoltorio.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx =0;
        c.gridy=0;
        envoltorio.add(titulo, c);

        c.gridy = 1;
        envoltorio.add(scroll, c);
        return envoltorio;
    }

    private void volverAlSelector() {
        // El historial se abre desde Mi cuenta, así que ahí regresa
        JPanel destino;
        if (tarjetaInicial.equals("historial")) {
            destino = new MiCuenta(marco, gestor, jugadorActual);
        } else {
            destino = new SelectorRepo(marco, jugadorActual, gestor);
        }

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(destino, BorderLayout.CENTER);

        marco.revalidate();
        marco.repaint();
    }
}
