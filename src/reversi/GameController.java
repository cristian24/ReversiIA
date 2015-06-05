package reversi;

import ia.AlgoritmosBusqueda;
import ia.Move;
import reversi.ia.ReversiEvaluador;
import reversi.ia.ReversiNode;

public class GameController  implements IdleObject {
    
    private int currentPlayerColour;
    private Tablero tablero;
    private GameView view;
    private int player1Colour;
    private boolean singlePlayer;
    private long tiempoRespuesta;
    private int pofundidadMax;
    private AlgoritmosBusqueda.Algoritmos algoritmoBusqueda;
    private ReversiEvaluador.EvaluationMethod heuristica;

    public GameController() {
        this.currentPlayerColour = Auxiliar.BLACK;
        this.tablero = null;
        this.view = null;
        this.player1Colour = this.currentPlayerColour;
        this.tiempoRespuesta = Auxiliar.TIEMPO_RESPUESTA;
        this.pofundidadMax = Auxiliar.PROFUNDIDAD_MAX;
        this.singlePlayer = true;
        this.algoritmoBusqueda = AlgoritmosBusqueda.Algoritmos.PODA_ALPHA_BETA;
        this.heuristica = ReversiEvaluador.EvaluationMethod.VALID_MOVES_AND_SIDES_COUNT;
    }

    public GameController(int colorJugador, int pofundidadMax, long tiempoRespuesta) {
        if ((colorJugador == Auxiliar.BLACK) || (colorJugador == Auxiliar.WHITE)) {
            this.player1Colour = colorJugador;
        } else {
            this.player1Colour = Auxiliar.BLACK;
        }
        this.currentPlayerColour = Auxiliar.BLACK;
        this.tiempoRespuesta = tiempoRespuesta;
        this.pofundidadMax = pofundidadMax;
        this.tablero = null;
        this.view = null;
        this.singlePlayer = true;
        this.algoritmoBusqueda = AlgoritmosBusqueda.Algoritmos.PODA_ALPHA_BETA;
        this.heuristica = ReversiEvaluador.EvaluationMethod.VALID_MOVES_AND_SIDES_COUNT;
    }

    public GameController(boolean singlePlayer, int playerColour, Tablero b, GameView view, int maxDepth, AlgoritmosBusqueda.Algoritmos algorithm, ReversiEvaluador.EvaluationMethod evalMethod, long waitForMillis) {
        if ((playerColour == Auxiliar.BLACK) || (playerColour == Auxiliar.WHITE)) {
            this.player1Colour = playerColour;
        } else {
            this.player1Colour = Auxiliar.BLACK;
        }
        this.currentPlayerColour = Auxiliar.BLACK;              
        this.pofundidadMax = maxDepth;
        this.algoritmoBusqueda = algorithm;
        this.heuristica = evalMethod;
        this.tiempoRespuesta = waitForMillis;
        this.singlePlayer = singlePlayer;
        this.setTablero(b);
        this.setGameView(view);        
    }

    public void setTablero(Tablero b) {
        this.tablero = b;
        if (this.view != null) {
            this.view.setBoard(b);
        }
    }

    public void setGameView(GameView v) {
        this.view = v;
        if (this.view != null) {
            this.view.setController(this);
            this.view.setBoard(this.tablero);
        }
    }

    public int getCurrentPlayerColour() {
        return this.currentPlayerColour;
    }

    public void startGame() {
        if ((this.singlePlayer) && (this.player1Colour == Auxiliar.WHITE)) {
            this.AIStartMove();
        }
    }

    private void AIStartMove() {
        this.view.setAIIsPlaying(true);
        ReversiNode n = new ReversiNode(this.tablero.getState(), this.currentPlayerColour);
        JugadorIA tmp = new JugadorIA(this, n, this.pofundidadMax, this.algoritmoBusqueda, this.heuristica, this.tiempoRespuesta);
        tmp.start();        
    }

    public void AIEndMove(ReversiNode n) throws MoveInvalidException {
    	Move next = n.getNextMove();
        if (next != null) {
            this.play(next.getX(), next.getY());            
        }
        this.view.setAIIsPlaying(false);
    }

    public void play(int x, int y) {
        if (this.tablero != null) {
            if (!this.tablero.isGameOver()) {
                try {
                    this.tablero.put(x, y, this.currentPlayerColour);
                    this.changePlayer();
                    this.view.notifyGameChanged(this);

                    if((this.tablero.getMovimientosValidos(currentPlayerColour).size() == 0)&&(!this.tablero.isGameOver())){
                        this.skipTurn();
                    }

                    if ((this.singlePlayer) && (this.currentPlayerColour != player1Colour)&&(!this.tablero.isGameOver())) {
                        this.AIStartMove();
                    }

                } catch (MoveInvalidException ime) {
                    System.err.println(ime.toString());
                }
            }
        }
    }

    private void changePlayer() {
        this.currentPlayerColour = Auxiliar.getOpponentsColour(this.currentPlayerColour);
    }

    private void skipTurn() {
        String playerName = Auxiliar.getPlayerName(this.currentPlayerColour);
        this.showNoMovesAlert(playerName);
    }

    public void resume() {
        this.view.setPlayerHasNoMovesAvailable(false, "");
        this.changePlayer();
        this.view.notifyGameChanged(this);
    }

    /**
     * Hace que el flujo del juego siga, despues de que un jugador no 
     * tiene movimientos validos
     * @param playerName
     */
    private void showNoMovesAlert(String playerName) {
        this.view.setPlayerHasNoMovesAvailable(true, playerName);
        IdleTimeConsumer p = new IdleTimeConsumer(this, 2000);
        p.start();
    }
}
