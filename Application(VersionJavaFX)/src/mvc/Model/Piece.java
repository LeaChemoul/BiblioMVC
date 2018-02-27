package mvc.Model;


import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

public class Piece {
    private  String nom;
    private Color couleur;
    private int vitesseChute;
    private Vec2d pivot;
    private boolean isStatic = false;
    private int[][] cases;

    public Piece(String nom, Color couleur, int vitesse, int[][] cases, Vec2d pivot) {
        this.nom = nom;
        this.couleur = couleur;
        this.vitesseChute = vitesse;
        this.cases = cases;
        this.pivot = pivot;
    }

    public int[][] getCases() {
        return cases;
    }

    public void fission(){

    }
}