package UI;

import javax.swing.*;
import javax.swing.Timer;

import Game.Tablero;
import Piezas.Piezas;

import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class PanelInfo extends JPanel {

    Tablero tablero;
    int anchoIcono = 40;

    JButton botonRetirarse;
    JLabel labelTurno;
    JLabel labelInstruccion;

    public PanelInfo(Tablero tablero) {
        this.tablero = tablero;
        this.setPreferredSize(new Dimension(260, tablero.tCasillas * tablero.getFil()));
        this.setBackground(Color.darkGray);
        this.setLayout(new BorderLayout());

        this.add(crearPanelTurno(), BorderLayout.NORTH);
        this.add(crearPanelDatos(), BorderLayout.CENTER);

        // Repintar y actualizar el estado de turno cada cierto tiempo
        Timer temporizador = new Timer(200, e -> {
            repaint();
            actualizarEstadoTurno();
        });
        temporizador.start();
    }


    // Función que se encarga de crear el panel del turno, el retiro y el menú
    private JPanel crearPanelTurno() {
        JPanel panelTurno = new JPanel();
        panelTurno.setBackground(Color.darkGray);
        panelTurno.setLayout(new BoxLayout(panelTurno, BoxLayout.Y_AXIS));

        
        labelTurno = new JLabel("Turno: Heroes");
        labelTurno.setForeground(Color.white);
        labelTurno.setFont(new Font("Arial", Font.PLAIN, 20));
        labelTurno.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTurno.add(labelTurno);

        labelInstruccion = new JLabel("Selecciona una pieza de tu equipo");
        labelInstruccion.setForeground(Color.yellow);
        labelInstruccion.setFont(new Font("Arial", Font.PLAIN, 15));
        labelInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelTurno.add(labelInstruccion);


        botonRetirarse = new JButton("Retirarse");
        botonRetirarse.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRetirarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmarRetiro(); // Llamado a la función para rendirse
            }
        });
        panelTurno.add(botonRetirarse);

        JButton botonMenu = new JButton("Menú principal");
        botonMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlMenuPrincipal(); // Llamado a la función para volver al menú
            }
        });
        panelTurno.add(botonMenu);

        return panelTurno;
    }

    private void volverAlMenuPrincipal() {
        if (!tablero.partidaTerminada) {
            int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que quieres salir? Se perderá el progreso de la partida actual.",
                "Volver al menú principal",
                JOptionPane.YES_NO_OPTION
            );

            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            if(!confirmarRetiro()){
                return;
            }
            
        }

        Window ventana = SwingUtilities.getWindowAncestor(this);
        if (!(ventana instanceof JFrame) || tablero.jugadorActual == null || tablero.gestor == null) {
            return;
        }

        JFrame marco = (JFrame) ventana;
        MenuPrincipal menuPrincipal = new MenuPrincipal(marco, tablero.gestor, tablero.jugadorActual);

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(menuPrincipal, BorderLayout.CENTER);

        marco.revalidate();
        marco.repaint();
    }

    // Función que se encarga de verificar si el jugador se rinde
    private boolean confirmarRetiro() {
        String bandoEnTurno = tablero.turnoHeroe ? "Heroes" : "Villanos";

        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Seguro que el jugador " + bandoEnTurno + " se quiere retirar? El otro jugador ganará la partida.",
            "Retirarse de la partida",
            JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            tablero.retirarse();
            actualizarEstadoTurno();
            return true;
        }
        return false;
    }

    // Función que crea el panel de datos de las piezas y las capturas
    private JPanel crearPanelDatos() {
        JPanel panelDatos = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarCapturadasYDatos((Graphics2D) g);
            }
        };
        panelDatos.setBackground(Color.darkGray);
        return panelDatos;
    }


    private void actualizarEstadoTurno() {
        if (tablero.partidaTerminada) {
            labelTurno.setText("Partida terminada");
            labelInstruccion.setText("");
            botonRetirarse.setEnabled(false);
            return;
        }

        
        labelTurno.setText("Turno: " + (tablero.turnoHeroe ? "Heroes" : "Villanos") + "-" + tablero.nombreDe(tablero.turnoHeroe));

        // La pista solo pide seleccionar una pieza propia
        labelInstruccion.setText("Selecciona una pieza de tu equipo");
    }

    private void dibujarCapturadasYDatos(Graphics2D g2d) {
        g2d.setColor(Color.white);

        int y = 20;

        if (tablero.mensajeFinal != null) {
            y = dibujarTextoConWrap(g2d, tablero.mensajeFinal, y, Color.yellow);
            y += 20;
        }
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString("Capturadas - Heroes", 10, y);
        y += 10;
        y = dibujarCapturadas(g2d, tablero.capturadasHeroe, y);

        y += 30;
        g2d.drawString("Capturadas - Villanos", 10, y);
        y += 10;
        y = dibujarCapturadas(g2d, tablero.capturadasVillano, y);

        y += 30;
        g2d.drawString("Datos de la pieza", 10, y);
        y += 20;
        y = dibujarDatos(g2d, y);

        if (tablero.ultimoMensaje != null) {
            y += 30;
            g2d.drawString("Ultimo ataque", 10, y);
            y += 20;
            dibujarTextoConWrap(g2d, tablero.ultimoMensaje, y, Color.orange);
        }
    }

    // Dibuja un texto largo partido en varias lineas para que no se salga
    // del panel (que es angosto), en el color indicado.
    private int dibujarTextoConWrap(Graphics2D g2d, String texto, int y, Color color) {
        g2d.setColor(color);

        String[] palabras = texto.split(" ");
        String lineaActual = "";

        for (String palabra : palabras) {
            String pruebaLinea = lineaActual + palabra + " ";

            if (pruebaLinea.length() > 32) {
                g2d.drawString(lineaActual, 10, y);
                y += 16;
                lineaActual = palabra + " ";
            } else {
                lineaActual = pruebaLinea;
            }
        }

        if (lineaActual.length() > 0) {
            g2d.drawString(lineaActual, 10, y);
            y += 16;
        }

        g2d.setColor(Color.white);
        return y;
    }


    //Funcion que dibuja las piezas que han sido capturadas
    private int dibujarCapturadas(Graphics2D g2d, ArrayList<Piezas> lista, int y) {
        int x = 10;

        for (Piezas p : lista) {
            if (p.getImagen() != null) {
                g2d.drawImage(p.getImagen(), x, y, anchoIcono, anchoIcono, null);
            }

            x += anchoIcono + 5;

            if (x > 260 - anchoIcono) {
                x = 10;
                y += anchoIcono + 5;
            }
        }

        return y + anchoIcono + 5;
    }

    public int dibujarDatos(Graphics2D g2d, int y) {
        Piezas seleccionada = tablero.piezaElegida;

        if (seleccionada == null) {
            g2d.drawString("Selecciona una pieza", 10, y);
            return y + 20;
        }

        g2d.drawString("Nombre: " + seleccionada.getAlias(), 10, y);

        // Las bombas y la tierra no tienen rango (-1 y -2)
        if (seleccionada.getRango() > 0) {
            g2d.drawString("Rango: " + seleccionada.getRango(), 10, y + 20);
        }

        return y + 60;
    }
}
