package reversi;

import ia.Move;
import java.awt.Point;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import ia.Archivo;

public class Tablero {
    
    private EstadoTablero estadoTablero;
    private int dimensionTablero;
    private Vector<TableroListener> views;
    private boolean gameOver;
    private Archivo archivo;

    public Tablero(Archivo archivo) {
        this.archivo = archivo;
        this.dimensionTablero = Auxiliar.TAMANO_TABLERO;
        this.estadoTablero = new EstadoTablero(this.dimensionTablero);
        this.initStateValues();
        this.views = new Vector<TableroListener>();
        this.gameOver = false;
    }

    private void initStateValues(){     
        int[][] tablero;
        
        try {
        	tablero = this.archivo.convertMatriz();
        	for (int i = 0; i < tablero.length; i++) {
    			for (int j = 0; j < tablero.length; j++) {
    				this.estadoTablero.set(i , j, tablero[i][j]);
    			}
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public EstadoTablero getState() {
        return this.estadoTablero;
    }

    public void setState(EstadoTablero s){
        if(s!=null){
            this.estadoTablero = s;
            this.gameOver = this.verifyGameOver();
            this.updateViews();
        }
    }

    public int getBoardDimension() {
        return this.dimensionTablero;
    }

    public int getBlackValue() {
        return Auxiliar.BLACK;
    }

    public int getWhiteValue() {
        return Auxiliar.WHITE;
    }

    public int getEmptyValue() {
        return Auxiliar.EMPTY;
    }

    public void put(int x, int y, int colour) throws MoveInvalidException {
        if (this.estadoTablero.checkPosition(x, y, colour)) {
            this.estadoTablero.put(x, y, colour);
            this.gameOver = this.verifyGameOver();
            this.updateViews();            
        } else {
            throw new MoveInvalidException(x, y, colour);
        }
    }

    public Point getPuntaje(){
        return this.estadoTablero.getScore();
    }

    public boolean isGameOver(){
        this.gameOver = this.verifyGameOver();
        return this.gameOver;
    }

    private boolean verifyGameOver() {
        return this.estadoTablero.verifyGameOver();
    }    

    public boolean checkPosition(int x, int y, int colour) {
        this.estadoTablero.checkPosition(x,y,colour);
        return false;
    }


    public Vector<Move> getMovimientosValidos(int playerColour) {
        return this.estadoTablero.getMovimientosValidos(playerColour);
    }

    public void addView(TableroListener view) {
        this.views.add(view);
    }

    public void removeView(TableroListener view) {
        this.views.remove(view);
    }

    public Vector<TableroListener> getViews() {
        return this.views;
    }

    private void updateViews() {
        for (Iterator<TableroListener> it = views.iterator(); it.hasNext();) {
            TableroListener boardView = it.next();
            boardView.notifyBoardChanged(this);
        }
    }
}
