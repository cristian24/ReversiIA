package reversi;

public class MoveInvalidException extends Exception {

	private static final long serialVersionUID = 1L;
	private int x;
    private int y;
    private int valorJugador;

    public MoveInvalidException(int x, int y, int valorJugador) {        
        this.valorJugador = valorJugador;
        this.x = x;
        this.y = y;        
    }

    @Override
    public String toString() {
		String result = "Movimiento Invalido: ";
		if (valorJugador == 1) {
			result = result + "La ficha negra ";
		} else {
			result = result + "blanca ";
		}
		result = result + "no se puede reemplazar en la casilla (" + (x)
				+ ", " + (y) + ")!";
		return result;
	}

}
