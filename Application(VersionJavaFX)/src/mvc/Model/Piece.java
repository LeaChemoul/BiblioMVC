package mvc.Model;


import java.util.LinkedList;

public class Piece {
    private String nom;
    private String couleur;
    private int vitesseChute;
    private LinkedList<Case> cases;

    public Piece(String nom, String couleur, int vitesse, LinkedList<Case> cases){
        this.nom = nom;
        this.couleur = couleur;
        this.vitesseChute = vitesse;
        this.cases = cases;
    }
}
