package mvc.Model;


import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

import java.util.Random;

public class Piece {
    private  String nom;
    private int taille;
    private Color couleur;
    private Vec2d pivot;
    private int[][] cases;


    private int vitesseChute; //Useless ?
    private boolean isStatic = false; //Useless ?
    private int rotationState; //Useless ?
    // A voir : 0 si initial, 1 si tourné uen fois droite, 2 si tourné 2 fois. Idem pour 3.

    public Piece(String nom, Color couleur, int vitesse, int[][] cases) {
        this.nom = nom;
        this.couleur = couleur;
        this.vitesseChute = vitesse;
        this.cases = cases;
        calculPivotEtTaille();
    }

    /**
     * Constructeur par copie de la classe.
     * @param piece
     */
    public Piece(Piece piece){
        this.nom = piece.nom;
        this.taille = piece.taille;
        Random random = new Random();
        this.couleur = Color.rgb(random.nextInt(255),random.nextInt(255),random.nextInt(255));
        //this.couleur = piece.couleur;
        this.vitesseChute = piece.vitesseChute;
        int length = piece.cases.length;
        this.cases = new int[length][length];
        for (int i = 0; i < length ; i++) {
            for (int j = 0; j < length; j++) {
                this.cases[i][j] = piece.cases[i][j];
            }
        }
        this.pivot = piece.pivot;
    }



    public void deleteDecalage(Vec2d decalage){
        boolean trouve = false;
        int decalageX = 0;
        int decalageY = 0;
        for (int i = 0; i < this.getCases().length ; i++) {
            for (int j = 0; j < this.getCases().length; j++) {
                if(this.getCases()[i][j] == 1){
                    trouve = true;
                    deleteCase(i + (int) decalage.x, j + (int) decalage.y);
                }
            }
        }
    }

    public void deleteCase(int i, int j){
        this.cases[i][j] = 0;
    }

    /**
     * Tourne une pièce de 90° degré dans sa matrice locale
     * dans le sens trigonométrique (LEFT) ou horaire (RIGHT, ou par défaut) en fonction de la direction sens
     * @param sens
     */

    /*
    public void Rotation(Direction sens){
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
        calculPivotEtTaille();
    }

    */

    public void Rotation (Direction sens) {
        int nbRaw = this.cases.length;
        int nbCol = this.cases[0].length;

        int[][] vr = new int[nbRaw][nbCol]; // coordinates comparatively to the pivot
        int[][] vt = new int[nbRaw][nbCol]; // coordinates comparatively to the pivot after transform
        int[][] vprim = new int[nbRaw][nbCol]; // absolute coordinates after transform

        // int[][] Rg = { {0,-1} , {1,0} }; // 90 degrees rotation to the left (counter clockwise)
        // int[][] Rd = { {0,1} , {-1,0} }; // 90 degrees rotation to the left (clockwise)

        int px = (int) this.pivot.x;
        int py = (int) this.pivot.y;

        for (int i = 0; i < nbRaw; i++) {
            vr[i][0] = this.cases[i][0] - px;
            vr[i][1] = this.cases[i][1] - py;
        }

        switch (sens) {
            case LEFT:
                for (int i = 0; i < nbRaw; i++) {
                    vt[i][0] = (0 * vr[i][0]) + (-1 * vr[i][1]);
                    vt[i][1] = (1 * vr[i][0]) + (0 * vr[i][1]);
                }
            case RIGHT:
                for (int i = 0; i < nbRaw; i++) {
                    vt[i][0] = (0 * vr[i][0]) + (1 * vr[i][1]);
                    vt[i][1] = (-1 * vr[i][0]) + (0 * vr[i][1]);
                }
        }

        for (int i = 0; i < nbRaw; i++) {
            vprim[i][0] = vt[i][0] + px;
            vprim[i][1] = vt[i][1] + py;
            this.cases[i] = vprim[i];
        }

        calculPivotEtTaille();
    }



        /**
         * Supprime la case de coordonnées (x,y) dans la matrice local de Piece. (Set sa valeur à 0)
         * @param x Coordonnée x de la 'case' à supprimer.
         * @param y Coordonnée y de la 'case' à supprimer.
         */
    public void supprimer(int x, int y) {
        if (x < cases.length && y < cases[0].length)
            cases[x][y] = 0;
    }

    // TODO : C'est quoi ?
    public void fission(){

    }


    /**
     * Mets à jour les valeurs des attributs Pivot et Taille en fonction de l'état de la pièce.
     */
    public void calculPivotEtTaille() {
        pivot = new Vec2d(0,0);
        taille = 0;
        for (int i = 0; i < cases.length; i++) {
            for (int j = 0; j < cases[0].length; j++) {
                if ( cases[i][j] != 0 ) {
                    taille++;
                    pivot.x += i + 0.5;
                    pivot.y += j + 0.5;
                }
            }
        }
        pivot.x /= taille;
        pivot.y /= taille;
    }

    /**
     * Mets à jour la valeur de l'attribut taille (int) de la pièce.
     * La taille est égale au nombre de cases occupées par la pièce.
     */
    public void calculTaille() {

        taille = 0;
        for (int i = 0; i < cases.length; i++)
            for (int j = 0; j < cases[0].length; j++)
                if ( cases[i][j] == 1 )
                    taille++;
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

    /**
     * Translate toutes les cases de la pièce d'une case dans la matrice locale vers la direction donnée.
     * @param dir Direction vers laquelle translater la piece
     */
    //Testé & Fonctionnelle.
    //Va servir à centrer les pièces.
    public void translater(Direction dir) {

        //Pour chaque case, on décale leur contenu d'une case vers la gauche/droite/haut/bas. En ignorant la première/dernière ligne/colonne
        // et en partant d'une différente extrémité de la matrice en fonction de la direction.
        switch (dir) {
            case LEFT:
                for(int i = 0; i < cases.length; i++) { //Chaque ligne
                    for (int j = 0; j < cases[0].length - 1; j++) { //Chaque colonne - la dernière
                        cases[i][j] = cases[i][j + 1]; //On copie le contenu de la case de droite dans la case actuelle.
                    }
                }
                for ( int i = 0; i < cases.length; i++ )
                    cases[i][cases[0].length-1] = 0;
                break;
            case RIGHT:
                for(int i = 0; i < cases.length; i++) { //Chaque ligne
                    for (int j = cases[0].length-1; j > 0 ; j--) { //Chaque colonne sauf la première
                        cases[i][j] = cases[i][j - 1]; //On copie le contenu de la case de gauche dans la case actuelle.
                    }
                }
                for ( int i = 0; i < cases.length; i++ )
                    cases[i][0] = 0;
                break;
            case UP:
                for(int i = 0; i < cases.length-1; i++) { //Chaque ligne sauf la dernière
                    for (int j = 0; j < cases[0].length; j++) { //Chaque colonne
                        cases[i][j] = cases[i+1][j]; //On copie le contenu de la case en dessous dans celle actuelle.
                    }
                }
                for ( int j = 0; j < cases[0].length; j++ )
                    cases[cases.length-1][j] = 0;
                break;
            case DOWN:
                for(int i = cases.length-1; i > 0; i--) {
                    for (int j = 0; j < cases[0].length; j++) {
                        cases[i][j] = cases[i-1][j];
                    }
                }
                for ( int j = 0; j < cases[0].length; j++ )
                    cases[0][j] = 0;
                break;
        }
    }

    //Centre la pièce le plus possible sur sa matrice locale en translatant la matrice de façon à centrer le pivot.
    public void centrerPiece() {

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