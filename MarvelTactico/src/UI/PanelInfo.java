package UI;

import javax.swing.*;

import Game.Tablero;

import java.awt.*;
import java.awt.event.*;

// Panel auxiliar para mostrar información relevante durante la partida.


public class PanelInfo extends JPanel {

    Tablero tablero;

    JButton botonRetirarse;
    JLabel labelTurno;
    JLabel labelInstruccion;

    public PanelInfo(Tablero tablero) {
        this.tablero = tablero;
        this.setPreferredSize(new Dimension(0, 120));
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


        botonRetirarse = new ButtonDesign("Retirarse");
        botonRetirarse.setAlignmentX(Component.CENTER_ALIGNMENT);
                botonRetirarse.setPreferredSize(new Dimension(160, 26));
        botonRetirarse.setMaximumSize(new Dimension(160, 26));
        botonRetirarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmarRetiro(); // Llamado a la función para rendirse
            }
        });
        panelTurno.add(botonRetirarse);

        JButton botonMenu = new ButtonDesign("Menú principal");
        botonMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonMenu.setPreferredSize(new Dimension(160, 26));
        botonMenu.setMaximumSize(new Dimension(160, 26));
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
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));

        // Última acción, o el resultado cuando la partida terminó
        String texto = null;
        Color color = Color.orange;
        if (tablero.mensajeFinal != null) {
            texto = tablero.mensajeFinal;
            color = Color.yellow;
        } else if (tablero.ultimoMensaje != null) {
            texto = "Ultimo ataque: " + tablero.ultimoMensaje;
        }

        if (texto != null) {
            g2d.setColor(color);
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(texto, (getWidth() - fm.stringWidth(texto)) / 2, 16); // centrado
        }
    }
}
