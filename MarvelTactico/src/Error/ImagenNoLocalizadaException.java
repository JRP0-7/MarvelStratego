package Error;

// Excepción lanzada cuando no se encuentra una imagen del juego.


public class ImagenNoLocalizadaException extends Exception{
    public ImagenNoLocalizadaException(String msg){
        super(msg);
    }
}
