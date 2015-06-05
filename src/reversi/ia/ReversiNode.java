package reversi.ia;

import ia.Move;
import ia.Nodo;

import java.util.Iterator;
import java.util.Vector;

import reversi.EstadoTablero;
import reversi.Auxiliar;

public class ReversiNode extends Nodo {

    private EstadoTablero state;
    private int currentPlayer;

    public ReversiNode(EstadoTablero s, int currentPlayer) {
        super();
        init(s, currentPlayer);
    }

    public ReversiNode(ReversiNode parent, Move m) {
        super(parent, m);
        init(parent.getState().getEstadoTableroHijo(m), Auxiliar.getOpponentsColour(parent.getCurrentPlayer()));
        //System.out.println(m.toString());
    }

    private void init(EstadoTablero s, int currentPlayer) {
        this.setState(s);
        this.setCurrentPlayer(currentPlayer);
    }

    public EstadoTablero getState() {
        return this.state;
    }

    public void setState(EstadoTablero state) {
        this.state = state;
    }

    public int getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void expand() {
        Vector<Move> availableMoves = this.getState().getMovimientosValidos(this.currentPlayer);
        for (Iterator<Move> it = availableMoves.iterator(); it.hasNext();) {
            Move move = it.next();
            ReversiNode child = new ReversiNode(this, move);
            this.addHijo(child);
        }
        this.setExpanded(true);
    }

    public boolean isEndGameNode() {
        return this.getState().verifyGameOver();
    }
}
