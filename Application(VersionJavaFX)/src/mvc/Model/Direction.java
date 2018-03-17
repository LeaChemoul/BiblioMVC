package mvc.Model;

public enum Direction {
    DOWN(1,0),
    UP(-1,0),
    RIGHT(0,1),
    LEFT(0,-1);

    int x,y;

    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
