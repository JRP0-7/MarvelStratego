package UI;

import javax.swing.*;

import Error.CredencialesInvalidasException;
import Error.InvalidPasswordException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Usuario.GestorJugadores;
import Usuario.Jugador;

public class MenuInicio extends Fondo {

    GestorJugadores gestor;

    JTextField campoUsuario;
    JPasswordField campoContraseña;
    JLabel labelMensaje;
    JButton btnVerContra;
    private boolean contraVisible = false;

    // Iconos del ojo: cerrado = contraseña oculta, abierto = visible
    private ImageIcon ojoCerrado = icono("invisible.png", 20, 20);
    private ImageIcon ojoAbierto = icono("visible.png", 20, 20);

    public MenuInicio(JFrame marco) {
        this(marco, new GestorJugadores());
    }

    public MenuInicio(JFrame marco, GestorJugadores gestor) {
        super("FondoMain.jpg");
        this.gestor = gestor;

        this.setLayout(new GridBagLayout());

        JPanel panelCentro = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Marvel Stratego");
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelUsuario = new JLabel("Usuario:");
        labelUsuario.setForeground(Color.white);
        labelUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
        labelUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoUsuario = new JTextField(15);
        campoUsuario.setMaximumSize(new Dimension(300, 50));
        campoUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelContraseña = new JLabel("Contraseña (5 caracteres):");
        labelContraseña.setForeground(Color.white);
        labelContraseña.setFont(new Font("Arial", Font.PLAIN, 18));
        labelContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Fila con el campo de contraseña y el botón del ojo al lado
        JPanel filaContraseña = new JPanel();
        filaContraseña.setOpaque(false);
        filaContraseña.setLayout(new BoxLayout(filaContraseña, BoxLayout.X_AXIS));
        filaContraseña.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoContraseña = new JPasswordField(15);
        campoContraseña.setMaximumSize(new Dimension(220, 25));

        btnVerContra = new JButton(ojoCerrado);
        btnVerContra.addActionListener(e -> {
            if (!contraVisible) {
                contraVisible = !contraVisible;
                campoContraseña.setEchoChar((char) 0);
                btnVerContra.setIcon(ojoAbierto);
            } else {
                contraVisible = !contraVisible;
                campoContraseña.setEchoChar('•');
                btnVerContra.setIcon(ojoCerrado);
            }
        });
        filaContraseña.add(campoContraseña);
        filaContraseña.add(Box.createRigidArea(new Dimension(5, 0)));
        filaContraseña.add(btnVerContra);

        labelMensaje = new JLabel(" ");
        labelMensaje.setForeground(Color.red);
        labelMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonIniciarSesion = new JButton("Iniciar sesión");
        botonIniciarSesion.setFont(new Font("Arial", Font.PLAIN, 18));
        botonIniciarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonIniciarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarSesion(); // se llama a la función de iniciar sesión
            }
        });

        JButton botonCrearJugador = new JButton("Crear jugador");
        botonCrearJugador.setFont(new Font("Arial", Font.PLAIN, 18));
        botonCrearJugador.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCrearJugador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearJugador();
                // se llama a la función de crear jugador
            }
        });

        JButton botonSalir = new JButton("Salir");
        botonSalir.setFont(new Font("Arial", Font.PLAIN, 18));
        botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panelCentro.add(titulo);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));
        panelCentro.add(labelUsuario);
        panelCentro.add(campoUsuario);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(labelContraseña);
        panelCentro.add(filaContraseña);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(labelMensaje);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonIniciarSesion);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonCrearJugador);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonSalir);

        this.add(panelCentro);

        this.marco = marco;
    }

    JFrame marco;

    // Función para iniciar sesión dentro del menú de inicio
    private void iniciarSesion() {
        String usuario = campoUsuario.getText().trim();
        String contraseña = new String(campoContraseña.getPassword()).trim();

        try {
            Jugador jugador = gestor.iniciarSesion(usuario, contraseña);

            labelMensaje.setText(" ");
            abrirMenuPrincipal(jugador); // se llama a la función de abrir el menú con los datos del jugador
        } catch (CredencialesInvalidasException e) {
            labelMensaje.setText(e.getMessage());
        }
    }

    // Función para crear un jugador nuevo dentro del menú de inicio
    private void crearJugador() {
        String usuario = campoUsuario.getText().trim();
        String contraseña = new String(campoContraseña.getPassword()).trim();

        try {
            String error = gestor.crearJugador(usuario, contraseña);

            if (error != null) {
                labelMensaje.setText(error);
            } else {
                labelMensaje.setText(" ");
                Jugador jugadorNuevo = gestor.buscarPorUsuario(usuario);
                abrirMenuPrincipal(jugadorNuevo);
            }
        } catch (InvalidPasswordException e) {
            labelMensaje.setText(e.getMessage());
        }
    }

    // Función que abre el menú principal del juego
    private void abrirMenuPrincipal(Jugador jugador) {
        MenuPrincipal menuPrincipal = new MenuPrincipal(marco, gestor, jugador);

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(menuPrincipal, BorderLayout.CENTER);

        marco.revalidate();
        marco.repaint();
    }
}
