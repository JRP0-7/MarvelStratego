package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Usuario.GestorJugadores;
import Usuario.Jugador;

public class SelectorRepo extends Fondo {
    JFrame marco;
    Jugador jugadorActual;
    GestorJugadores gestor;

    public SelectorRepo(JFrame marco, Jugador jugadorActual, GestorJugadores gestor) {
        super("golden_city.jpeg");
        this.marco = marco;
        this.jugadorActual = jugadorActual;
        this.gestor = gestor;

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

        JLabel titulo = new JLabel("UNIVERSO MARVEL");
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.BOLD, 50));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonVerBatallas = new JButton("Batallas");
        botonVerBatallas.setFont(new Font("Arial", Font.PLAIN, 18));
        botonVerBatallas.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonVerBatallas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirReportes("batallas");
            }
        });
        JButton botonVerRanking = new JButton("Ranking de jugadores");
        botonVerRanking.setFont(new Font("Arial", Font.PLAIN, 18));
        botonVerRanking.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonVerRanking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirReportes("ranking");
            }
        });

        JButton botonVolver = new JButton("Volver");
        botonVolver.setFont(new Font("Arial", Font.PLAIN, 18));
        botonVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAlMenuPrincipal();
            }
        });

        panelCentro.add(titulo);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 30)));
        panelCentro.add(botonVerBatallas);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonVerRanking);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentro.add(botonVolver);

        this.add(panelCentro);
    }

    // Abre la pantalla de reportes mostrando la tarjeta indicada
    private void abrirReportes(String tarjeta) {
        Reportes reportes = new Reportes(marco, gestor, jugadorActual, tarjeta);

        marco.getContentPane().removeAll();
        marco.setLayout(new BorderLayout());
        marco.add(reportes, BorderLayout.CENTER);

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
