package Usuario;

import java.util.Date;

public class Jugador {
    private String usuario;
    private String contraseña;
    private int puntosV;
    private int puntosH;
    private int puntosT;
    private Date fechaIngreso;
    private boolean activo;

    public Jugador(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.puntosV = 0;
        this.puntosH = 0;
        this.puntosT = 0;
        this.fechaIngreso = new Date();
        this.fechaIngreso = new Date();
        this.activo = true;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getPuntosV() {
        return puntosV;
    }

    public int getPuntosH() {
        return puntosH;
    }

    public void sumarPuntosH(int cantidad) {
        this.puntosH += cantidad;
    }

    public void sumarPuntosV(int cantidad){
        this.puntosV += cantidad;
    }

    public void calcularTotal(){
        this.puntosT = this.puntosH + this.puntosV;
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
