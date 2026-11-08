package UI;

import javax.swing.*;
import javax.swing.border.SoftBevelBorder;

import Error.InvalidPasswordException;
import Usuario.GestorJugadores;
import Usuario.Jugador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class MiCuenta extends Fondo {

    JFrame marco;
    GestorJugadores gestor;
    Jugador jugadorActual;

    JLabel labelMensaje;

    public MiCuenta(JFrame marco, GestorJugadores gestor, Jugador jugadorActual) {
        super("watcher.jpg");
        this.marco = marco;
        this.gestor = gestor;
        this.jugadorActual = jugadorActual;

        this.setLayout(new GridBagLayout());

        // Panel con fondo semitransparente y borde para que la informacion resalte
        // sobre la imagen
        JPanel panelCentro = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white, 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)));
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Mi cuenta");
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 50));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        JLabel labelUsuario = new JLabel("Usuario: " + jugadorActual.getUsuario());
        labelUsuario.setFont(new Font("Arial", Font.ROMAN_BASELINE, 30));
        labelUsuario.setForeground(Color.white);
        labelUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelPuntos = new JLabel("Puntos: " + jugadorActual.getTotalPoints());
        labelPuntos.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        labelPuntos.setForeground(Color.white);
        labelPuntos.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelPuntoV = new JLabel("Puntos villano: " + jugadorActual.getPuntosV());
        labelPuntoV.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        labelPuntoV.setForeground(Color.white);
        labelPuntoV.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelPuntoH = new JLabel("Puntos héroe: " + jugadorActual.getPuntosH());
        labelPuntoH.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        labelPuntoH.setForeground(Color.white);
        labelPuntoH.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelPartidasH = new JLabel("Partidas como héroe: " + jugadorActual.getPartidasH());
        labelPartidasH.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        labelPartidasH.setForeground(Color.white);
        labelPartidasH.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelPartidasV = new JLabel("Partidas como villano: " + jugadorActual.getPartidasV());
        labelPartidasV.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        labelPartidasV.setForeground(Color.white);
        labelPartidasV.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelFecha = new JLabel("Fecha de ingreso: " + formato.format(jugadorActual.getFechaIngreso()));
        labelFecha.setFont(new Font("Arial", Font.ROMAN_BASELINE, 20));
        labelFecha.setForeground(Color.white);
        labelFecha.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelMensaje = new JLabel(" ");
        labelMensaje.setForeground(Color.red);
        labelMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón que abre el historial de partidas del jugador
        JButton botonHistorial = new JButton("Mis últimos juegos");
        botonHistorial.setFont(new Font("Arial", Font.PLAIN, 18));
        botonHistorial.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonHistorial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirHistorial();
            }
        });

        JButton botoncambiarPassword = new JButton("Cambiar contraseña");
        botoncambiarPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        botoncambiarPassword.setBackground(Color.decode("#032263"));
        botoncambiarPassword.setForeground(Color.WHITE);
        botoncambiarPassword.setBorder(new SoftBevelBorder(
                SoftBevelBorder.RAISED,
                Color.WHITE,
                Color.LIGHT_GRAY,
                Color.GRAY,
                Color.DARK_GRAY) {

        });
        botoncambiarPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoncambiarPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    cambiarPassword(); // se llama a la función para cambiar la contraseña
                } catch (InvalidPasswordException error) {
                    labelMensaje.setForeground(Color.red);
                    labelMensaje.setText(error.getMessage());
                }
            }
        });

        JButton botonCerrarCuenta = new JButton("Cerrar mi cuenta");
        botonCerrarCuenta.setFont(new Font("Arial", Font.PLAIN, 18));
        botonCerrarCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCerrarCuenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrarCuenta(); // se llama a la función para desactivar la cuenta
            }
        });

        JButton botonVolver = new JButton("Volver");
        botonVolver.setFont(new Font("Arial", Font.PLAIN, 18));
        botonVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlMenuPrincipal(); // se llama a la función para volver al menú principal
            }
        });

        panelCentro.add(titulo);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentro.add(labelUsuario);
        panelCentro.add(labelPuntos);
        panelCentro.add(labelPuntoH);
        panelCentro.add(labelPuntoV);
        panelCentro.add(labelPartidasH);
        panelCentro.add(labelPartidasV);
        panelCentro.add(labelFecha);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(labelMensaje);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonHistorial);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botoncambiarPassword);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonCerrarCuenta);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonVolver);

        this.add(panelCentro);
    }

    // Abre la vista de historial (la tarjeta "historial" de Reportes)
    private void abrirHistorial() {
        Reportes reportes = new Reportes(marco, gestor, jugadorActual, "historial");

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(reportes, BorderLayout.CENTER);

        marco.revalidate();
        marco.repaint();
    }

    private void cambiarPassword() throws InvalidPasswordException {
        String actual = JOptionPane.showInputDialog(this, "Ingrese su contraseña actual:");
        if (actual == null) {
            return;
        }

        if (!actual.trim().equals(jugadorActual.getPassword())) {
            throw new InvalidPasswordException("La contraseña ingresada no es correcta");
        }

        String nueva = JOptionPane.showInputDialog(this, "Escribe la nueva contraseña (5 caracteres):");

        if (nueva == null) {
            return; // el jugador le dio cancelar
        }

        if (nueva.length() != 5) {
            throw new InvalidPasswordException("La contraseña debe tener exactamente 5 caracteres.");
        }

        if (!nueva.matches("[a-zA-Z0-9]+")) {
            throw new InvalidPasswordException("La contraseña debe ser alfanumérica (solo letras y números).");
        }

        if (!nueva.matches(".*[a-zA-Z].*")) {
            throw new InvalidPasswordException("La contraseña debe tener al menos una letra.");
        }

        jugadorActual.setContraseña(nueva);
        labelMensaje.setForeground(Color.green);
        labelMensaje.setText("Contraseña actualizada.");
    }

    // Función para desactivar la cuenta del usuario
    private void cerrarCuenta() {
        String respuesta = JOptionPane.showInputDialog(
                this,
                "¿Seguro que quieres cerrar tu cuenta? No vas a poder iniciar sesión de nuevo. \n Una vez ingresada la contraseña se cerrara");

        if (respuesta == null) {
            return;
        }

        if (!respuesta.trim().equals(jugadorActual.getPassword())) {
            labelMensaje.setForeground(Color.red);
            labelMensaje.setText("La contraseña ingresada no es correcta");
            return;
        }
        jugadorActual.setActivo(false);

        MenuInicio menuInicio = new MenuInicio(marco, gestor);
        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(menuInicio, BorderLayout.CENTER);
        marco.revalidate();
        marco.repaint();
    }

    // Función que regresa al menú principal
    private void volverAlMenuPrincipal() {
        MenuPrincipal menuPrincipal = new MenuPrincipal(marco, gestor, jugadorActual);

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(menuPrincipal, BorderLayout.CENTER);

        marco.revalidate();
        marco.repaint();
    }
}
