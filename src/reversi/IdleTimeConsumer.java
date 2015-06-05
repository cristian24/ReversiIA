package reversi;

/**
 * Reinicia la ejecucion del hilo del juego.
 * @author
 *
 */
public class IdleTimeConsumer extends Thread{

    private long msec;
    private IdleObject o;

    public IdleTimeConsumer(IdleObject o, long msec){
        this.o = o;
        this.msec = msec;
    }

    public void run(){

        try{
            Thread.sleep(msec);
        }
        catch(InterruptedException ie){
            
        }
        o.resume();

    }

}
