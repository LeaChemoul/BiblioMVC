package mvc.Model;


import javafx.scene.paint.Color;

public class Case {
    private int x;
    private int y;
    private Color couleur;

    //correspond à l'index de la pièce dans la liste de pièces, à -1 si la case n'appartient pas a une pièce
    private int index; // Idée : Le remplacer par la référence de la pièce ?

    //TODO : Look at that.
    private int xLocal; //La coordonnée x de la case dans la matrice locale de la pièce
    private int yLocal; //La coordonnée x de la case dans la matrice locale de la pièce

    //TODO : Supprimer x et y pour laisser xLocal et yLocal si on n'utilise pas les premiers ?

    public Case(int x, int y, Color coul, int i){
        this.x = x;
        this.y = y;
        this.couleur = coul;
        this.index = i;
    }

    public Case(int x, int y, int xLoc, int yLoc, Color coul, int i){
        this.x = x;
        this.y = y;
        this.xLocal = xLoc;
        this.yLocal = yLoc;
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
