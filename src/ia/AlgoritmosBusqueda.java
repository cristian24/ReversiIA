package ia;

import ia.Nodo.TipoNodo;

import java.util.ArrayDeque;

public class AlgoritmosBusqueda {

    public enum Algoritmos{
        MINIMAX, PODA_ALPHA_BETA
    }

    public void aplicarAlgoritmo(Nodo n, int maxProfundidad, Algoritmos algoritmo, Evaluador eval){
        if(algoritmo == Algoritmos.MINIMAX){
            minimax(n, maxProfundidad, eval);
        }else if(algoritmo == Algoritmos.PODA_ALPHA_BETA){
            podaAlphaBetha(n, maxProfundidad, eval);
        }
    }

    private void minimax(Nodo n, int maxProfundidad, Evaluador eval) {
        if (n.isEndGameNode()) {
            System.out.println("H1");
            return;
        }
        ArrayDeque<Nodo> pilaBusqueda = new ArrayDeque<Nodo>();
        pilaBusqueda.push(n);

        while (pilaBusqueda.isEmpty() == false) {
            Nodo nodo = pilaBusqueda.pop();

            Move nextMove = nodo.getNextMove();
            boolean isRoot = nodo.isRaiz();

            if (nodo.isHoja(maxProfundidad)) {
                nodo.setVal(eval.evaluar(nodo));
                if (!isRoot) {
                    Nodo padre = nodo.getPadre();
                    if (padre.setVal(nodo.getVal())) {
                        padre.setNextMove(nodo.getMoveLeadingHere());
                    }
                }
            } else {
                if (nodo.tieneMasHijos()) {
                    pilaBusqueda.push(nodo);
                    pilaBusqueda.push(nodo.getNextHijo());
                } else {
                    if (nextMove != null) {
                        if (!isRoot) {
                            Nodo padre = nodo.getPadre();
                            if (padre.setVal(nodo.getVal())) {
                                padre.setNextMove(nodo.getMoveLeadingHere());
                            }
                        }
                    }
                }
            }
            nodo.setVisitado(true);
            //print(node);
        }
    }


    private void podaAlphaBetha(Nodo n, int maxProfundidad, Evaluador eval) {
        if (n.isEndGameNode()) {
            System.out.println("H1");
            return;
        }
        ArrayDeque<Nodo> pilaBusqueda = new ArrayDeque<Nodo>();
        pilaBusqueda.push(n);

        while (pilaBusqueda.isEmpty() == false) {
            Nodo nodo = pilaBusqueda.pop();

            Move nextMove = nodo.getNextMove();
            boolean isRoot = nodo.isRaiz();

            if (nodo.isHoja(maxProfundidad)) {
                nodo.setVal(eval.evaluar(nodo));
                if (!isRoot) {
                    Nodo parent = nodo.getPadre();
                    if (parent.setVal(nodo.getVal())) {
                        parent.setNextMove(nodo.getMoveLeadingHere());
                    }
                }
            } else {
                if (nodo.tieneMasHijos()) {
                    if (!isRoot) {
                        Integer valNodo = nodo.getVal();
                        if(valNodo!=null){
                            Nodo padre = nodo.getPadre();
                            Integer valNodoPadre = padre.getVal();
                            if(valNodoPadre==null){
                                pilaBusqueda.push(nodo);
                                pilaBusqueda.push(nodo.getNextHijo());
                            }else{
                                TipoNodo type = padre.getTipo();
                                if(type == TipoNodo.MAX){
                                    //Pruning:
                                    if(nodo.getVal() >= valNodoPadre){
                                        pilaBusqueda.push(nodo);
                                        pilaBusqueda.push(nodo.getNextHijo());
                                    }
                                }else{
                                    //Pruning:
                                    if(nodo.getVal()<=valNodoPadre){
                                        pilaBusqueda.push(nodo);
                                        pilaBusqueda.push(nodo.getNextHijo());
                                    }
                                }
                            }
                        }
                        else{
                            pilaBusqueda.push(nodo);
                            pilaBusqueda.push(nodo.getNextHijo());
                        }
                    }else{
                        pilaBusqueda.push(nodo);
                        pilaBusqueda.push(nodo.getNextHijo());
                    }
                } else {
                    if (nextMove != null) {
                        if (!isRoot) {
                            Nodo parent = nodo.getPadre();
                            if (parent.setVal(nodo.getVal())) {
                                parent.setNextMove(nodo.getMoveLeadingHere());
                            }
                        }
                    }
                }
            }
            nodo.setVisitado(true);
            //print(node);
        }
    }    

    private void print(Nodo n) {
        System.out.println(n.toString());
    }
}
