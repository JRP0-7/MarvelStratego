package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;

import java.awt.event.MouseEvent;

public class ButtonDesign extends JButton{

    private boolean encima = false;

    public ButtonDesign(String texto){
        super(texto);
        this.setFont(new Font("Arial", Font.BOLD, 16));
        this.setForeground(Color.WHITE);
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false); // el fondo lo pintamos nosotros
        this.setOpaque(false);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension tam = new Dimension(220, 40);
        this.setPreferredSize(tam);
        this.setMaximumSize(tam);

        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                encima = true;
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                encima = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color fondo = new Color(200, 30, 40);
        if (!isEnabled()) {
            fondo = new Color(70, 70, 70);
        } else if (getModel().isPressed()) {
            fondo = new Color(140, 20, 30);
        } else if (encima) {
            fondo = new Color(230, 45, 55);
        }

        g2d.setColor(fondo);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
        g2d.dispose();

        super.paintComponent(g); // dibuja el texto encima
    }

}
