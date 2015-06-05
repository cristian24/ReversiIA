package ia;

public class Move  {

    private int x;
    private int y;
    private int colour;

    public Move(int x, int y, int colour){
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getColour(){
        return this.colour;
    }

    @Override
    public String toString(){
        return (x+1)+","+(y+1)+": "+colour;
    }

    @Override
    public boolean equals(Object obj){
        if( ! (obj instanceof Move) ){
            return false;
        }
        else{
            Move m = (Move) obj;
            if ( this.hashCode() == m.hashCode() ){
                return true;
            }
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.x;
        hash = 13 * hash + this.y;
        hash = 13 * hash + this.colour;
        return hash;
    }

}
