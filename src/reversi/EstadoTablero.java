package reversi;

import ia.Move;
import java.awt.Point;
import java.util.Vector;

public class EstadoTablero {

    private int[][] estado;
    private int dimension;

    public EstadoTablero(int dimension){
        this.dimension = dimension;
        estado = new int[this.dimension][this.dimension];
    }

    public EstadoTablero(EstadoTablero original){
        this.dimension = original.getDimension();
        estado = new int[this.dimension][this.dimension];
        for(int i=0; i<this.dimension; i++){
            for(int j=0; j<this.dimension; j++){
                this.estado[i][j] = original.get(i, j);
            }
        }
    }
    
    public int getDimension(){
        return this.dimension;
    }

    public int get(int x, int y){
        return this.estado[x][y];
    }

    /**
     * Cambiar el valor de una casilla
     * @param x
     * @param y
     * @param val
     */
    public void set(int x, int y, int val){
        this.estado[x][y] = val;
    }

    /**
     * Pone una ficha en una casilla, luego se envia a evaluacion los posibles flanqueos.
     * @param x
     * @param y
     * @param val
     */
    public void put(int x, int y, int val){
        this.estado[x][y] = val;
        this.flip(x, y, val);
    }

    public Point getScore(){
        int totalBlack = 0;
        int totalWhite = 0;
        int val = 0;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                val = this.estado[i][j];
                if (val == Auxiliar.BLACK) {
                    totalBlack++;
                } else if (val == Auxiliar.WHITE) {
                    totalWhite++;
                }
            }
        }
        return new Point(totalBlack, totalWhite);
    }

    public boolean verifyGameOver() {
        int black = 0;
        int white = 0;
        int empty = 0;
        int legalBlack = 0;
        int legalWhite = 0;
        int val = 0;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                val = this.estado[i][j];
                if (val == Auxiliar.BLACK) {
                    black++;
                } else if (val == Auxiliar.WHITE) {
                    white++;
                }
                else{
                    empty++;
                }
                if (checkPosition(i, j, Auxiliar.BLACK)) {
                    legalBlack++;
                }
                if (checkPosition(i, j, Auxiliar.WHITE)) {
                    legalWhite++;
                }
            }
        }
        if( (empty==0) || (black==0) || (white==0) || ((legalBlack==0)&&(legalWhite==0)) ){
            return true;
        }
        return false;
    }

    public Vector<Move> getMovimientosValidos(int playerColour) {
        Vector<Move> movimientos = new Vector<Move>();
        for (int j = 0; j < this.dimension; j++) {
            for (int i = 0; i < this.dimension; i++) {
                if (checkPosition(i, j, playerColour)) {
                    Move m = new Move(i, j, playerColour);
                    movimientos.add(m);
                }
            }
        }
        return movimientos;
    }    
    
    public EstadoTablero getEstadoTableroHijo(Move move){
        if(checkPosition(move.getX(), move.getY(), move.getColour())){
            EstadoTablero child = new EstadoTablero(this);
            child.put(move.getX(), move.getY(), move.getColour());
            return child;
        }
        return null;
    }
    
    private boolean checkLeftUp(int x, int y, int colorPlayer) {
        if ((y == 0) || (x == 0)) {
            return false;
        }
        int colorOponente = Auxiliar.getOpponentsColour(colorPlayer);
        if (this.estado[x - 1][y - 1] != colorOponente) {
            return false;
        } else {
            int j = y - 2;
            int i = x - 2;
            int value = 0;
            boolean contiguous = true;
            while ((j >= 0) && (i >= 0) && (contiguous)) {
                value = this.estado[i][j];
                if (value != colorOponente) {
                    contiguous = false;
                    if (value == colorPlayer) {
                        return true;
                    }
                } else {
                    j--;
                    i--;
                }
            }
            return false;
        }
    }

    private boolean checkLeftDown(int x, int y, int colorPlayer) {
        int max = this.dimension - 1;
        if ((y == max) || (x == 0)) {
            return false;
        }
        int colorOponente = Auxiliar.getOpponentsColour(colorPlayer);
        if (this.estado[x - 1][y + 1] != colorOponente) {
            return false;
        } else {
            int j = y + 2;
            int i = x - 2;
            int value = 0;
            boolean contiguous = true;
            while ((j <= max) && (i >= 0) && (contiguous)) {
                value = this.estado[i][j];
                if (value != colorOponente) {
                    contiguous = false;
                    if (value == colorPlayer) {
                        return true;
                    }
                } else {
                    j++;
                    i--;
                }
            }
            return false;
        }
    }

    private boolean checkRightUp(int x, int y, int colorPlayer) {
        int max = this.dimension - 1;
        if ((y == 0) || (x == max)) {
            return false;
        }
        int colorOponente = Auxiliar.getOpponentsColour(colorPlayer);
        if (this.estado[x + 1][y - 1] != colorOponente) {
            return false;
        } else {
            int j = y - 2;
            int i = x + 2;
            int value = 0;
            boolean contiguous = true;
            while ((j >= 0) && (i <= max) && (contiguous)) {
                value = this.estado[i][j];
                if (value != colorOponente) {
                    contiguous = false;
                    if (value == colorPlayer) {
                        return true;
                    }
                } else {
                    j--;
                    i++;
                }
            }
            return false;
        }
    }

    private boolean checkRightDown(int x, int y, int colorPlayer) {
        int max = this.dimension - 1;
        if ((y == max) || (x == max)) {
            return false;
        }
        int colorOponente = Auxiliar.getOpponentsColour(colorPlayer);
        if (this.estado[x + 1][y + 1] != colorOponente) {
            return false;
        } else {
            int j = y + 2;
            int i = x + 2;
            int value = 0;
            boolean contiguous = true;
            while ((j <= max) && (i <= max) && (contiguous)) {
                value = this.estado[i][j];
                if (value != colorOponente) {
                    contiguous = false;
                    if (value == colorPlayer) {
                        return true;
                    }
                } else {
                    j++;
                    i++;
                }
            }
            return false;
        }
    }

    private boolean checkUp(int x, int y, int colorPlayer) {
        if (y == 0) {
            return false;
        }
        int colorOponente = Auxiliar.getOpponentsColour(colorPlayer);
        if (this.estado[x][y - 1] != colorOponente) {
            return false;
        } else {
            int j = y - 2;
            int value = 0;
            boolean contiguous = true;
            while ((j >= 0) && (contiguous)) {
                value = this.estado[x][j];
                if (value != colorOponente) {
                    contiguous = false;
                    if (value == colorPlayer) {
                        return true;
                    }
                } else {
                    j--;
                }
            }
            return false;
        }
    }

    private boolean checkDown(int x, int y, int colorPlayer) {
        int max = this.dimension - 1;
        if (y == max) {
            return false;
        }
        int colorOponente = Auxiliar.getOpponentsColour(colorPlayer);
        if (this.estado[x][y + 1] != colorOponente) {
            return false;
        } else {
            int j = y + 2;
            int value = 0;
            boolean contiguous = true;
            while ((j <= max) && (contiguous)) {
                value = this.estado[x][j];
                if (value != colorOponente) {
                    contiguous = false;
                    if (value == colorPlayer) {
                        return true;
                    }
                } else {
                    j++;
                }
            }
            return false;
        }
    }

    private boolean checkLeft(int x, int y, int colorPlayer) {
    	//Si esta en un lateral, retorna false
        if (x == 0) {
            return false;
        }
        int colorOponente = Auxiliar.getOpponentsColour(colorPlayer);       
        if (this.estado[x - 1][y] != colorOponente) {
            return false;
        } else {
            int i = x - 2;
            int value = 0;
            boolean contiguous = true;
            while ((i >= 0) && (contiguous)) {
                value = this.estado[i][y];
                if (value != colorOponente) {
                    contiguous = false;
                    if (value == colorPlayer) {
                        return true;
                    }
                } else {
                    i--;
                }
            }
            return false;
        }
    }

    private boolean checkRight(int x, int y, int colorPlayer) {
        int max = this.dimension - 1;
        if (x == max) {
            return false;
        }
        int colorOponente = Auxiliar.getOpponentsColour(colorPlayer);
        if (this.estado[x + 1][y] != colorOponente) {
            return false;
        } else {
            int i = x + 2;
            int value = 0;
            boolean contiguous = true;
            while ((i <= max) && (contiguous)) {
                value = this.estado[i][y];
                if (value != colorOponente) {
                    contiguous = false;
                    if (value == colorPlayer) {
                        return true;
                    }
                } else {
                    i++;
                }
            }
            return false;
        }
    }
    
    
    private boolean checkHorizontal(int x, int y, int colorPlayer) {
        if (checkLeft(x, y, colorPlayer)) {
            return true;
        }
        if (checkRight(x, y, colorPlayer)) {
            return true;
        }
        return false;
    }

    private boolean checkVertical(int x, int y, int colorPlayer) {
        if (checkUp(x, y, colorPlayer)) {
            return true;
        }
        if (checkDown(x, y, colorPlayer)) {
            return true;
        }
        return false;
    }

    private boolean checkDiagonal(int x, int y, int colorPlayer) {
        if (checkLeftUp(x, y, colorPlayer)) {
            return true;
        }
        if (checkLeftDown(x, y, colorPlayer)) {
            return true;
        }
        if (checkRightUp(x, y, colorPlayer)) {
            return true;
        }
        if (checkRightDown(x, y, colorPlayer)) {
            return true;
        }
        return false;
    }

    /**
     * Evalua si es una posiciona valida, para flanquear, evalua
     * horizontalmente, verticalmente, y en diagonal.
     * @param x
     * @param y
     * @param colorPlayer: color de jugador actual
     * @return boolean que indica si es una posicion valida.
     */
    public boolean checkPosition(int x, int y, int colorPlayer) {
        if (this.estado[x][y] != Auxiliar.EMPTY) {
            return false;
        }
        if (this.checkHorizontal(x, y, colorPlayer)) {
            return true;
        }
        if (this.checkVertical(x, y, colorPlayer)) {
            return true;
        }
        if (this.checkDiagonal(x, y, colorPlayer)) {
            return true;
        }
        return false;
    }

    private void flanqueoUp(int x, int y, int colorPlayer) {
        if (this.checkUp(x, y, colorPlayer)) {
            int i = y - 1;
            int value = 0;
            boolean contiguous = true;
            while ((i >= 0) && (contiguous)) {
                value = this.estado[x][i];
                if (value == colorPlayer) {
                    contiguous = false;
                } else {
                    this.estado[x][i] = colorPlayer;
                    i--;
                }
            }
        }
    }

    private void flanqueoDown(int x, int y, int colorPlayer) {
        int max = this.dimension - 1;
        if (this.checkDown(x, y, colorPlayer)) {
            int i = y + 1;
            int value = 0;
            boolean contiguous = true;
            while ((i <= max) && (contiguous)) {
                value = this.estado[x][i];
                if (value == colorPlayer) {
                    contiguous = false;
                } else {
                    this.estado[x][i] = colorPlayer;
                    i++;
                }
            }
        }
    }

    private void flanqueoRight(int x, int y, int colorPlayer) {
        int max = this.dimension - 1;
        if (this.checkRight(x, y, colorPlayer)) {
            int i = x + 1;
            int value = 0;
            boolean contiguous = true;
            while ((i <= max) && (contiguous)) {
                value = this.estado[i][y];
                if (value == colorPlayer) {
                    contiguous = false;
                } else {
                    this.estado[i][y] = colorPlayer;
                    i++;
                }
            }
        }
    }

    private void flanqueoLeft(int x, int y, int colorPlayer) {
        if (this.checkLeft(x, y, colorPlayer)) {
            int i = x - 1;
            int value = 0;
            boolean contiguous = true;
            while ((i >= 0) && (contiguous)) {
                value = this.estado[i][y];
                if (value == colorPlayer) {
                    contiguous = false;
                } else {
                    this.estado[i][y] = colorPlayer;
                    i--;
                }
            }
        }
    }

    private void flanqueoLeftUp(int x, int y, int colorPlayer) {
        if (this.checkLeftUp(x, y, colorPlayer)) {
            int j = y - 1;
            int i = x - 1;
            int value = 0;
            boolean contiguous = true;
            while ((j >= 0) && (i >= 0) && (contiguous)) {
                value = this.estado[i][j];
                if (value == colorPlayer) {
                    contiguous = false;
                } else {
                    this.estado[i][j] = colorPlayer;
                    j--;
                    i--;
                }
            }
        }
    }

    private void flanqueoLeftDown(int x, int y, int colorPlayer) {
        if (this.checkLeftDown(x, y, colorPlayer)) {
            int max = this.dimension - 1;
            int j = y + 1;
            int i = x - 1;
            int value = 0;
            boolean contiguous = true;
            while ((j <= max) && (i >= 0) && (contiguous)) {
                value = this.estado[i][j];
                if (value == colorPlayer) {
                    contiguous = false;
                } else {
                    this.estado[i][j] = colorPlayer;
                    j++;
                    i--;
                }
            }
        }
    }

    private void flanqueoRightUp(int x, int y, int colorPlayer) {
        if (this.checkRightUp(x, y, colorPlayer)) {
            int max = this.dimension - 1;
            int j = y - 1;
            int i = x + 1;
            int value = 0;
            boolean contiguous = true;
            while ((j >= 0) && (i <= max) && (contiguous)) {
                value = this.estado[i][j];
                if (value == colorPlayer) {
                    contiguous = false;
                } else {
                    this.estado[i][j] = colorPlayer;
                    j--;
                    i++;
                }
            }
        }
    }

    private void flanqueoRightDown(int x, int y, int colorPlayer) {
        if (this.checkRightDown(x, y, colorPlayer)) {
            int max = this.dimension - 1;
            int j = y + 1;
            int i = x + 1;
            int value = 0;
            boolean contiguous = true;
            while ((j <= max) && (i <= max) && (contiguous)) {
                value = this.estado[i][j];
                if (value == colorPlayer) {
                    contiguous = false;
                } else {
                    this.estado[i][j] = colorPlayer;
                    j++;
                    i++;
                }
            }
        }
    }    

    /**
     * Realiza los cambios en el valor de la casilla, si esta es
     * flanqueable.
     * @param x
     * @param y
     * @param colorCurrentPlayer
     */
    public void flip(int x, int y, int colorCurrentPlayer) {
        this.flanqueoLeft(x, y, colorCurrentPlayer);
        this.flanqueoRight(x, y, colorCurrentPlayer);
        this.flanqueoUp(x, y, colorCurrentPlayer);
        this.flanqueoDown(x, y, colorCurrentPlayer);
        this.flanqueoLeftUp(x, y, colorCurrentPlayer);
        this.flanqueoLeftDown(x, y, colorCurrentPlayer);
        this.flanqueoRightUp(x, y, colorCurrentPlayer);
        this.flanqueoRightDown(x, y, colorCurrentPlayer);
    }

    
}
