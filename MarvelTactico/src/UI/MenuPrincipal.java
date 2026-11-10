package UI;

import javax.swing.*;

import Usuario.GestorJugadores;
import Usuario.Jugador;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Menú principal que permite navegar entre las opciones del juego.


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

        JLabel titulo = new JLabel("Menú principal");
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 50));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelBienvenida = new JLabel("Bienvenido, " + jugadorActual.getUsuario());
        labelBienvenida.setForeground(Color.white);
        labelBienvenida.setFont(new Font("Arial", Font.BOLD, 25));
        labelBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonJugar = new ButtonDesign("Partida nueva");
        botonJugar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonJugar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirSeleccionOponente();
            }
        });

        JButton botonMiCuenta = new ButtonDesign("Mi cuenta");
        botonMiCuenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonMiCuenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirMiCuenta();
            }
        });

        JButton botonReportes = new ButtonDesign("UNIVERSO MARVEL");
        botonReportes.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonReportes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirReportes();
            }
        });

        JButton botonCerrarSesion = new ButtonDesign("Cerrar sesión");
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
        panelCentro.add(botonReportes);
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

    private void abrirReportes() {
        SelectorRepo reportes = new SelectorRepo(marco, jugadorActual, gestor);

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(reportes, BorderLayout.CENTER);

        marco.revalidate();
        marco.repaint();
    }

    private void cerrarSesion() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea cerrar su sesión actual?", "Cerrar sesión", JOptionPane.YES_NO_OPTION);
        if(respuesta==JOptionPane.YES_OPTION){
            MenuInicio menuInicio = new MenuInicio(marco, gestor);
    
            marco.getContentPane().removeAll();
            marco.setLayout(new BorderLayout());
            marco.add(menuInicio, BorderLayout.CENTER);
    
            marco.revalidate();
            marco.repaint();
        }

    }
}
