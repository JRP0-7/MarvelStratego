package UI;

import javax.swing.*;

import Usuario.GestorJugadores;
import Usuario.Jugador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends Fondo {

    JFrame marco;
    GestorJugadores gestor;
    Jugador jugadorActual;

    public MenuPrincipal(JFrame marco, GestorJugadores gestor, Jugador jugadorActual) {
        super("fondo_2.jpg");
        this.marco = marco;
        this.gestor = gestor;
        this.jugadorActual = jugadorActual;

        this.setLayout(new GridBagLayout());

        JPanel panelCentro = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Menu Principal");
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 50));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelBienvenida = new JLabel("Bienvenido, " + jugadorActual.getUsuario());
        labelBienvenida.setForeground(Color.white);
        labelBienvenida.setFont(new Font("Aria", Font.BOLD, 25));
        labelBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonJugar = new JButton("Partida nueva");
        botonJugar.setFont(new Font("Arial", Font.PLAIN, 18));
        botonJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonJugar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirSeleccionOponente();
            }
        });

        JButton botonMiCuenta = new JButton("Mi cuenta");
        botonMiCuenta.setFont(new Font("Arial", Font.PLAIN, 18));
        botonMiCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonMiCuenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMiCuenta();
            }
        });

        JButton botonCerrarSesion = new JButton("Cerrar sesion");
        botonCerrarSesion.setFont(new Font("Arial", Font.PLAIN, 18));
        botonCerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCerrarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        panelCentro.add(titulo);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(labelBienvenida);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 30)));
        panelCentro.add(botonJugar);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonMiCuenta);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonCerrarSesion);

        this.add(panelCentro);
    }

    private void abrirSeleccionOponente() {
        SeleccionOponente seleccion = new SeleccionOponente(marco, gestor, jugadorActual);

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(seleccion, BorderLayout.CENTER);

        marco.revalidate();
        marco.repaint();

    }

    private void abrirMiCuenta() {
        MiCuenta miCuenta = new MiCuenta(marco, gestor, jugadorActual);

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(miCuenta, BorderLayout.CENTER);

        marco.revalidate();
        marco.repaint();
    }

    private void cerrarSesion() {
        MenuInicio menuInicio = new MenuInicio(marco, gestor);

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(menuInicio, BorderLayout.CENTER);

        marco.revalidate();
        marco.repaint();
    }
}
