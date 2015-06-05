package reversi;

import ia.Evaluador;
import ia.AlgoritmosBusqueda;
import ia.AlgoritmosBusqueda.Algoritmos;
import reversi.ia.ReversiEvaluador;
import reversi.ia.ReversiNode;
import reversi.ia.ReversiEvaluador.EvaluationMethod;

public class JugadorIA extends Thread {

    private GameController gc;
    private long waitFor;
    private int d;
    private ReversiNode n;
    private Algoritmos algorithm;
    private EvaluationMethod evalMethod;

    public JugadorIA(GameController gc, ReversiNode n, int maxDepth, Algoritmos algorithm, EvaluationMethod evalMethod, long waitForMillis) {
        this.gc = gc;
        this.waitFor = waitForMillis;
        this.d = maxDepth;
        this.n = n;
        this.algorithm = algorithm;
        this.evalMethod = evalMethod;
    }

    @Override
    public void run() {
        long time1 = System.currentTimeMillis();
        AlgoritmosBusqueda m = new AlgoritmosBusqueda();
        Evaluador eval = new ReversiEvaluador(n.getCurrentPlayer(), evalMethod);
        m.aplicarAlgoritmo(n, d, algorithm, eval);

        long time2 = System.currentTimeMillis();
        long remaining = waitFor - (time2 - time1);

        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException ie) {
            }
        }

        try {
            gc.AIEndMove(n);
        } catch (MoveInvalidException ime) {
        }
    }
}
