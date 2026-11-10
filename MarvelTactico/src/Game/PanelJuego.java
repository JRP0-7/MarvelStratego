package Game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import Piezas.Piezas;

// Panel principal que integra el tablero y la interfaz de la partida.


public class PanelJuego extends JPanel{
    Tablero tablero;
    boolean esHeroe;
    int anchoIcono=40;

    public PanelJuego(Tablero tablero, boolean esHeroe){
        this.tablero=tablero;
        this.esHeroe = esHeroe;
        this.setPreferredSize(new Dimension(260, 0));

        if(esHeroe){
            this.setBackground(new Color(20,50,110));
        }else{
            this.setBackground(new Color(110,20,20));
        }

        Timer temporizador = new Timer(200, e -> repaint());
        temporizador.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Elige el texto del bando y SU lista de piezas vencidas
        String bando = "VILLANOS";
        ArrayList<Piezas> perdidas = tablero.capturadasVillano;
        if (esHeroe) {
            bando = "HÉROES";
            perdidas = tablero.capturadasHeroe;
        }

        // Borde amarillo si es el turno de este equipo
        if (!tablero.partidaTerminada && tablero.turnoHeroe == esHeroe) {
            g2d.setColor(Color.yellow);
            g2d.setStroke(new BasicStroke(4));
            g2d.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
        }

        // Nombre del bando y del jugador
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString(bando, 15, 28);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.drawString(tablero.nombreDe(esHeroe), 15, 50);

        g2d.drawString("Piezas vencidas", 15, 85);

        // Piezas vencidas, en filas que bajan al llegar al borde
        int x = 15;
        int y = 95;
        for (Piezas p : perdidas) {
            if (p.getImagen() != null) {
                g2d.drawImage(p.getImagen(), x, y, anchoIcono, anchoIcono, null);
            }
            x += anchoIcono + 5;
            if (x > getWidth() - anchoIcono) {
                x = 15;
                y += anchoIcono + 5;
            }
        }
    }
}
