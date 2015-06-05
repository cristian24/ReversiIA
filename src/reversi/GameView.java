package reversi;

public interface GameView {

    public void notifyGameChanged(GameController g);
    public void forceUpdate();
    public void setController(GameController g);
    public Tablero getBoard();
    public void  setBoard(Tablero b);
    public void setAIIsPlaying(boolean AIIsPlaying);
    public boolean getAIIsPlaying();
    public void setPlayerHasNoMovesAvailable(boolean playerHasNoValidMoves, String player);

}
