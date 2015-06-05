package reversi.ia;

import ia.Evaluador;
import ia.Nodo;
import reversi.EstadoTablero;
import reversi.Auxiliar;

public class ReversiEvaluador extends Evaluador{

    public enum EvaluationMethod {
        VALID_MOVES_AND_SIDES_COUNT, VALID_MOVES_AND_CORNERS
    }

     private int player;
     private EvaluationMethod evalMethod;

     public ReversiEvaluador(){
         player = Auxiliar.BLACK;
         evalMethod = ReversiEvaluador.EvaluationMethod.VALID_MOVES_AND_CORNERS;
     }
     
     /**
      * Constructor de la clase.
      * @param player: Valor numerico que representa el jugador.
      * @param evalMethod: metodo de evaluacion.
      */
     public ReversiEvaluador(int player, EvaluationMethod evalMethod)
     {
         this.player = player;
         this.evalMethod = evalMethod;
     }

     
     public int evaluar(Nodo n) 
     {        
        int value;
        ReversiNode node = (ReversiNode) n;
        EstadoTablero s = node.getState();

        if (evalMethod == EvaluationMethod.VALID_MOVES_AND_SIDES_COUNT) {
            value = validMovesCount(s, player)+ sidesCount(s, player);
        } else {
            value = validMovesCount(s, player) + cornerCount(s, player) * 120;
        }

        return value;
    }

    /**
     * Retorna el puntaje de un jugador.
     * @param s
     * @param player
     * @return
     */
    private int totalScore(EstadoTablero s, int player) {
        int dimension = s.getDimension();
        int opponent = Auxiliar.getOpponentsColour(player);

        int total = 0;
        int val = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                val = s.get(i, j);
                if (val == player) {
                    total++;
                } else if (val == opponent) {
                    total--;
                }
            }
        }
        return total;
    }

    /**
     * Retorna lo movimientos validos
     * @param s
     * @param player
     * @return
     */
    private int validMovesCount(EstadoTablero s, int player) {
        int mobility = s.getMovimientosValidos(player).size();

        return mobility;
    }

    /**
     * Retorna el numero de esquinas flanqueadas.
     * @param state
     * @param player
     * @return
     */
    private int cornerCount(EstadoTablero state, int player) {
        int total = 0;
        int opponent = Auxiliar.getOpponentsColour(player);
        int val = 0;
        int max = state.getDimension() - 1;

        val = state.get(0, 0);
        if (val == player) {
            total++;
        } else if (val == opponent) {
            total--;
        }

        val = state.get(max, 0);
        if (val == player) {
            total++;
        } else if (val == opponent) {
            total--;
        }

        val = state.get(0, max);
        if (val == player) {
            total++;
        } else if (val == opponent) {
            total--;
        }

        val = state.get(max, max);
        if (val == player) {
            total++;
        } else if (val == opponent) {
            total--;
        }
        return total;
    }

    /**
     * Retorna el numero de fichas en los bordes
     * @param state
     * @param player
     * @return
     */
    private int sidesCount(EstadoTablero state, int player) {
        int total = 0;
        int opponent = Auxiliar.getOpponentsColour(player);
        int max = state.getDimension() - 1;
        int val = 0;

        for (int i = 1; i < max; i++) {
            val = state.get(i, 0);
            if (val == player) {
                total++;
            } else if (val == opponent) {
                total--;
            }

            val = state.get(0, i);
            if (val == player) {
                total++;
            } else if (val == opponent) {
                total--;
            }

            val = state.get(i, max);
            if (val == player) {
                total++;
            } else if (val == opponent) {
                total--;
            }

            val = state.get(max, i);
            if (val == player) {
                total++;
            } else if (val == opponent) {
                total--;
            }
        }

        return total;
    }

}
