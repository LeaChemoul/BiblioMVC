package mvc.Model;


import javafx.scene.paint.Color;

public class Case {

    //TODO : Jamais utilisé, à supprimer ?
    private int x;
    private int y;


    private Color couleur;

    //correspond à l'index de la pièce dans la liste de pièces, à -1 si la case n'appartient pas a une pièce
    private int index;

    private int xLocal; //La coordonnée x de la case dans la matrice locale de la pièce
    private int yLocal; //La coordonnée x de la case dans la matrice locale de la pièce

    public Case(int x, int y, Color coul, int i){
        this.x = x;
        this.y = y;
        this.couleur = coul;
        this.index = i;
    }

    public Case(int x, int y, Color coul, int i, int xLocal, int yLocal){
        this.x = x;
        this.y = y;
        this.xLocal = xLocal;
        this.yLocal = yLocal;
        this.couleur = coul;
        this.index = i;
    }



    public int getxLocal() {
        return xLocal;
    }

    public void setxLocal(int xLocal) {
        this.xLocal = xLocal;
    }

    public int getyLocal() {
        return yLocal;
    }

    public void setyLocal(int yLocal) {
        this.yLocal = yLocal;
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
