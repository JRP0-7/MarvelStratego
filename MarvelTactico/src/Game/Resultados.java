package Game;

public enum Resultados {
    CAPTURA_TIERRA,
    RETIRO,
    MOVIMIENTOS_INSUFICIENTES,
    PIEZAS_INMOVILES;

    public String mensaje(String ganador, String perdedor, boolean ganoHeroe) {
        String vencedor, derrotado;
        if (ganoHeroe) {
            vencedor = "HEROES";
            derrotado = "VILLANOS";
        } else {
            vencedor = "VILLANOS";
            derrotado = "HEROES";
        }

        switch (this) {
            case CAPTURA_TIERRA:
                if (ganoHeroe) {
                    return ganador + " usando " + vencedor + " ha SALVADO la TIERRA! Venciendo a " + perdedor;
                } else {
                    return ganador + " usando " + vencedor + " ha CAPTURADO la TIERRA! Venciendo a " + perdedor;
                }
            case RETIRO:
                return ganador + " usando " + vencedor + " ha ganado ya que " + perdedor
                        + " usando " + derrotado + " se ha retirado del juego";
            case MOVIMIENTOS_INSUFICIENTES:
            case PIEZAS_INMOVILES:
                return perdedor + " usando " + derrotado
                        + " ha perdido por no tener movimientos válidos disponibles ante " + ganador;
            default:
                return null;
        }
    }
}
