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

    public Piece(String nom, Color couleur, int vitesse, int[][] cases) {
        this.nom = nom;
        this.couleur = couleur;
        this.vitesseChute = vitesse;
        this.cases = cases;
        calculPivot();
    }


    public void Rotation(Direction sens){ // tourne une pièce p de 90 degrés dans le sens horaire "h" ou le sens trigonométrique "t"
        int[][] m = getCases(); // On récupère la matrice locale de la pièce
        int l = m.length -1; // longueur de la matrice, celle-ci est carrée
        for (int i = 0; i <= l/2; i++){ // On se déplace de la couronne extérieure à la couronne intérieur
            for (int j = i; j < l-i; j++){ // on prend les cases à tourner 4 par 4 dans la couronne
                // coordonnées des cases de la couronne
                int c1 = m[i][j];
                int c2 = m[j][l-i];
                int c3 = m[l-i][l-j];
                int c4 = m[l-j][i];
                switch (sens){
                    case LEFT : // sens trigonométrique
                        m[l-j][i] = c1;
                        m[i][j] = c2;
                        m[j][l-i] = c3;
                        m[l-i][l-j] = c4;
                        break;
                    case RIGHT : // sens horaire (par défaut si choix non valide)
                    default :
                        m[j][l-i] = c1;
                        m[l-i][l-j] = c2;
                        m[l-j][i] = c3;
                        m[i][j] = c4;
                        break;
                }

            }
        }
        calculPivot();
    }


    public void fission(){

    }


    public void calculPivot() {
        pivot = new Vec2d(0,0);
        int nbCases = 0;
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases[0].length; j++) {
                if ( cases[i][j] != 0 ) {
                    nbCases++;
                    pivot.x += i + 0.5;
                    pivot.y += j + 0.5;
                }
            }
        }
        pivot.x /= nbCases;
        pivot.y /= nbCases;
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