package Model;


import javafx.scene.paint.Color;

public class Case {
    private Color couleur;

    //correspond à l'index de la pièce dans la liste de pièces, à -1 si la case n'appartient pas a une pièce
    private int index;

    private int xLocal; //La coordonnée x de la case dans la matrice locale de la pièce
    private int yLocal; //La coordonnée x de la case dans la matrice locale de la pièce

    public Case(int x, int y, Color coul, int i, int xLocal, int yLocal){
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
