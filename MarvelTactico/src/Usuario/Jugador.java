package Usuario;

import java.util.Date;

// Representa a un jugador dentro del sistema del juego.


public class Jugador {
    private String usuario;
    private String password;
    private int puntosV;
    private int puntosH;
    private int partidasH;
    private int partidasV;
    private Date fechaIngreso;
    private boolean activo;

    public Jugador(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
        this.puntosV = 0;
        this.puntosH = 0;
        this.partidasH=0;
        this.partidasV=0;
        this.fechaIngreso = new Date();
        this.activo = true;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntosV() {
        return puntosV;
    }

    public int getPuntosH() {
        return puntosH;
    }


    public int getPartidasH() {
        return partidasH;
    }

    public int getPartidasV() {
        return partidasV;
    }

    public void sumarPuntos(boolean Heroe){
        if(Heroe){
            puntosH+=3;
        }else{
            puntosV+=3;
        }
        sumarPartidas(Heroe);
    }

    public void sumarPartidas(boolean Heroe){
        if(Heroe){
            partidasH++;
        }
        else{
            partidasV++;
        }
    }

    public int getTotalPoints(){
        return this.puntosH + this.puntosV;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
