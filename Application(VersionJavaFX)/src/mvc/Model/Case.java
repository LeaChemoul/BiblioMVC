package mvc.Model;


import javafx.scene.paint.Color;

public class Case {
    private int x;
    private int y;
    private Color couleur;
    private int index; //correspond à l'index de la pièce dans la liste de pièces, à -1 si la case n'apparteint a une pièce

    public Case(){

    }

    public Case(int x, int y, Color coul, int i){
        this.x = x;
        this.y = y;
        this.couleur = coul;
        this.index = i;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getCouleur() {
        return couleur;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
