package mvc.Model;


import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

public class Piece {
    private  String nom;
    private int taille;
    private Color couleur;
    private int vitesseChute;
    private Vec2d pivot;
    private boolean isStatic = false;
    private int[][] cases;
    private int rotationState; // A voir : 0 si initial, 1 si tourné uen fosi droite, 2 si tourné 2 fois. Idem pour 3.

    public Piece(String nom, Color couleur, int vitesse, int[][] cases, Vec2d pivot) {
        this.nom = nom;
        this.couleur = couleur;
        this.vitesseChute = vitesse;
        this.cases = cases;
        this.pivot = pivot;
    }



    public void afficherPiece() {

        //On affiche le nom de la pièce.
        System.out.println("Piece \'"+nom+"\' : ");

        //On récupère la matrice qui la compose.
        int[][] matricePiece = getCases();
        //On récupère les dimensions de la pièce.
        int n = matricePiece.length;
        int m = matricePiece[0].length;

        System.out.println("Dimensions : "+n+"*"+m);

        //Ligne bordure au sommet.
        System.out.print("  -");
        for ( int j = 0; j < m; j++ )
            System.out.print("---");
        System.out.println("-");

        for ( int i = 0; i < n; i++ ) {

            System.out.print("  |");
            for (int j = 0; j < m; j++) {

                //Si la case est égale à 0, elle est vide. (Convention actuelle. A CHANGER ?)
                if ( matricePiece[i][j] != 0 )
                    System.out.print(" X ");
                else
                    System.out.print("   ");

            }
            System.out.println("|");
        }

        //Ligne bordure au pied.
        System.out.print("  -");
        for ( int j = 0; j < m; j++ )
            System.out.print("---");
        System.out.println("-");

        /* TYPE DE RENDU :
            -------
            |     |
            |  X  |
            | XXX |
            -------
         */

        //On affiche sa couleur
        //TODO : Créer un COLOR TO STRING. Pour l'instant getCouleur() et getCouleur().toString() renvoie tout les deux un code hexadecimal dégeulasse.
        System.out.println("Couleur de la piece : "+ getCouleur() );

        //On affiche sa vitesse de chute.
        System.out.println("Vitesse de chute de la piece : " + getVitesseChute() );

        //On affiche son pivot.
        System.out.println("Coordonnées du pivot : " + getPivot() );

        System.out.println("---------------");
    }

    public void fission(){
    }

    public void rotation(){
    }

    //Accesseurs
    public int[][] getCases() {
        return cases;
    }

    public int getVitesseChute() {
        return vitesseChute;
    }

    public Vec2d getPivot() {
        return pivot;
    }

    public Color getCouleur() {
        return couleur;
    }

    public int getTaille(){
        return this.taille;
    }


    public void setTaille(int taille) {
        this.taille = taille;
    }
}