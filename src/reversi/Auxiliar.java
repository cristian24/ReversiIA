package reversi;


public class Auxiliar {

    public static final int TAMANO_TABLERO = 8;
    public static final int BLACK = 1;
    public static final int WHITE = 2;
    public static final int EMPTY = 0;
    public static final int PROFUNDIDAD_MAX = 4;
    public static final long TIEMPO_RESPUESTA = 250;

    public static int getOpponentsColour(int colour) {
        if (colour == Auxiliar.BLACK) {
            return Auxiliar.WHITE;
        } else {
            return Auxiliar.BLACK;
        }
    }

    public static String getPlayerName(int playerColour) {
        String playerName = "Jugador1";
        if (playerColour == Auxiliar.WHITE) {
            playerName = "Jugador2";
        }
        return playerName;
    }
}
