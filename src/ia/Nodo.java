package ia;

import java.util.Vector;

public abstract class Nodo {

    public enum TipoNodo 
    {
        MAX, MIN
    }
    
    private boolean isVisited;
    private boolean isExpanded;
    private Nodo padre;
    private Vector<Nodo> hijos;
    private int nextChildIndex;
    private Move moveLeadingHere;
    private Move nextMove;
    private TipoNodo tipoNodo;
    private int profundidad;
    private Integer value;
    public static long NextNodeId;
    private long nodeId;

    public Nodo() {
        init(null, null);
    }

    public Nodo(Nodo padre, Move m) {
        init(padre, m);
    }

    private void init(Nodo padre, Move m) {
        this.setPadre(padre);
        if (padre == null) {
            this.setTipo(TipoNodo.MAX);
            this.hijos = new Vector<Nodo>();
            this.setMoveLeadingHere(null);
            this.setNextMove(null);
            this.setProfundidad(0);
            this.setVal(null);
            this.setVisitado(false);
            this.setExpanded(false);
            Nodo.NextNodeId = 0;

        } else {
            this.setPadre(padre);
            this.hijos = new Vector<Nodo>();
            this.setMoveLeadingHere(m);
            this.setNextMove(null);
            this.setProfundidad(this.padre.getProfundidad() + 1);
            this.setVal(null);
            this.setVisitado(false);
            this.setExpanded(false);
            this.setTipo(this.invertirTipo(padre.getTipo()));    //si el padre es max el hijo es min.         
        }

        this.nextChildIndex = 0;
        this.nodeId = Nodo.NextNodeId;
        Nodo.NextNodeId++;
    }

    /**
     * Retorna el padre del nodo.
     * @return
     */
    public Nodo getPadre() {
        return this.padre;
    }

    /**
     * Cambia el padre del nodo.
     * @param parent Nodo
     */
    public void setPadre(Nodo parent) {
        this.padre = parent;
    }

    public Move getMoveLeadingHere() {
        return this.moveLeadingHere;
    }

    public void setMoveLeadingHere(Move moveLeadingHere) {
        this.moveLeadingHere = moveLeadingHere;
    }

    /**
     * @return Boolean que indica si es raiz.
     */
    public boolean isRaiz() {
        return (this.padre == null);
    }
    
    /**
     * Bool que indica si el nodo ha sido visitado.
     * @return Boolean
     */
    public boolean isVisitado() {
        return this.isVisited;
    }

    public void setVisitado(boolean visited) {
        this.isVisited = visited;
    }
    
    /**
     * Bool que indica si el nodo ha sido visitado
     * @return Boolean
     */
    public boolean isExpanded() {
        return this.isExpanded;
    }

    public void setExpanded(boolean expanded) {
        this.isExpanded = expanded;
    }

    /**
     * Obtiene el tipo del nodo.
     * @return
     */
    public TipoNodo getTipo() {
        return this.tipoNodo;
    }

    /**
     * Cambia el tipo del nodo.
     * @param tipo
     */
    public void setTipo(TipoNodo tipo) {
        this.tipoNodo = tipo;
    }

    /**
     * Retorna la profundidad en la que se encuentra el nodo.
     * @return
     */
    public int getProfundidad() {
        return this.profundidad;
    }

    /**
     * Cambia la profundidad del nodo.
     * @param profundidad
     */
    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    /**
     * Metodo para agregar hijos a nodo. 
     * @param hijo
     */
    public void addHijo(Nodo hijo){
        this.hijos.add(hijo);
    }

    public abstract void expand();
    
    /**
     * Obtiene los hijos del padre y expande el nodo.
     * @return Vector de tipo Node, con los hijos del nodo.
     */
    public Vector<Nodo> getHijos() {
        if (!this.isExpanded()) {
            this.expand();
        }
        return this.hijos;
    }
    
    /**
     * Retorna el proximo hijo del nodo.
     * @return Un Nodo Hijo
     */
    public Nodo getNextHijo() {
        Nodo result = null;
        Vector<Nodo> hijos = this.getHijos();
        int lastIndex = hijos.size() - 1;
        if (this.nextChildIndex <= lastIndex) {
            result = hijos.get(this.nextChildIndex);
            this.nextChildIndex++;
        }
        return result;
    }

    public boolean tieneMasHijos() {
        if (!this.isExpanded()) {
            this.expand();
        }
        return (this.nextChildIndex <= this.getHijos().size() - 1);
    }

    public Move getNextMove() {
        return this.nextMove;
    }

    public void setNextMove(Move m) {
        this.nextMove = m;
    }

    public abstract boolean isEndGameNode();/*{
    return this.getState().checkGameOver();
    }*/

    /**
     * Retorna boolean que indica, si es un nodo terminal.
     * @param maxDepth Profundidad Maxima.
     * @return Boolean
     */
    public boolean isHoja(int maxDepth) {
        if ((isEndGameNode()) || (getProfundidad() == maxDepth)) {
            return true;
        }
        return false;
    }

    /**
     * Invierte el tipo del nodo
     * @param type nodo
     * @return
     */
    public TipoNodo invertirTipo(TipoNodo type) {
        if (type == TipoNodo.MAX) {
            return TipoNodo.MIN;
        }
        return TipoNodo.MAX;
    }

    /**
     * Valor entero del nodo.
     * @return Integer
     */
    public Integer getVal() {
        return this.value;
    }
    
    /**
     * Cambia el valor del nodo, si el nodo tiene un valor null
     * le da a el nodo el valor newValue, si no es null, se analiza
     * si es un nodo Max, si es max se compara el valor actual del nodo
     * con el de newValue, si newValue es mayor, se hace el cambio y se retorna
     * true. si no es un nodo de tipo min entonces se compara si newValue es menor
     * al valor actual del nodo y se retorna true. 
     * @param newVal
     * @return boolean que indica si se hizo el cambio.
     */
    public boolean setVal(Integer newVal)
    {
        Integer currentVal = this.getVal();
        if (currentVal == null) {
            this.value = newVal;
            return true;
        } else {
            if (this.getTipo() == TipoNodo.MAX) {
                if (newVal > currentVal) {
                    this.value = newVal;
                    return true;
                } else {
                    return false;
                }
            } else {
                if (newVal < currentVal) {
                    this.value = newVal;
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    @Override
    public String toString() {
        String T = "MAX";
        if (this.getTipo() == TipoNodo.MIN) 
        {
            T = "MIN";
        }
        Integer mmv = this.getVal();
        String V = "null";
        if (mmv != null) 
        {
            V = mmv.toString();
        }
        return this.nodeId + " (V: " + V + ",T: " + T + ", D: " + this.getProfundidad() + ")";
    }
}
