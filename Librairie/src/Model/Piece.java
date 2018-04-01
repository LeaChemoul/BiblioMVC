package Model;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

import java.util.Random;

public class Piece {

    private  String nom;

    private int taille;

    private Color couleur;

    private Vec2d pivot;

    private int[][] cases;

    private Color bordure;

    private boolean horizontal;

    public Piece(String nom, int[][] cases) {
        this.nom = nom;
        this.couleur = Color.BLACK; //Couleur par défaut.
        this.cases = cases;
        this.bordure = Color.TRANSPARENT;
        calculPivotEtTaille();
    }

    public Piece(String nom, Color couleur, int[][] cases) {
        this.nom = nom;
        this.couleur = couleur;
        this.cases = cases;
        this.bordure = Color.TRANSPARENT;
        calculPivotEtTaille();
    }

    public Piece(Piece piece, boolean couleurAleatoire,boolean h,Color couleur){
        this(piece,couleurAleatoire);
        this.horizontal = h;
        this.couleur = couleur;
    }

    /**
     * Constructeur par copie de la classe.
     * @param piece piece a copier
     * @param couleurAleatoire Vrai si on genère une couleur aléatoirement pour la pièce.
     */
    public Piece(Piece piece, boolean couleurAleatoire){
        this.nom = piece.nom;
        this.taille = piece.taille;

        if (couleurAleatoire) {
            Random random = new Random();
            this.couleur = Color.hsb(random.nextInt(),0.80,0.94);
        }
        else
            this.couleur = piece.couleur;

        int length = piece.cases.length;
        this.cases = new int[length][length];
        for (int i = 0; i < length ; i++) {
            System.arraycopy(piece.cases[i], 0, this.cases[i], 0, length);
        }
        this.pivot = piece.pivot;
        this.bordure = piece.bordure;
    }


    public boolean colonneVide(int j){
        for (int i = 0; i < this.getHauteur(); i++) {
            if(this.cases[i][j] == 1)
                return false;
        }
        return true;
    }
    public boolean ligneVide(int i){
        for (int j = 0; j < this.getLargeur(); j++) {
            if(this.cases[i][j] == 1)
                return false;
        }
        return true;
    }

    /**
     * Tourne une pièce de 90° degré dans sa matrice locale
     * dans le sens trigonométrique (UP) ou horaire (DOWN, ou par défaut) en fonction de la direction sens
     * @param sens Sens de la rotation.
     */
    public void rotation(Direction sens){
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


    /**
     * Supprime la case de coordonnées (x,y) dans la matrice local de Piece. (Set sa valeur à 0)
     * @param x Coordonnée x de la 'case' à supprimer.
     * @param y Coordonnée y de la 'case' à supprimer.
     */
    public void supprimerCase(int x, int y) {
        if (x >= 0 && x < cases.length && y >= 0 && y < cases[0].length)
            cases[x][y] = 0;
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
     * Renvoie la matrice local de la pièce rognée de ses premières lignes et colonnes vides.
     * Utilisé pour l'affichage des pièces afin qu'il soit en accord avec la pose.
     * @return Matrice de la pièce rognée des ses lignes et colonnes vides.
     */
    public int[][] croppedPiece() {
        boolean colonneVide = true;
        boolean ligneVide = true;
        int nbColVide = 0;
        int nbLigVide = 0;


        //On compte le nombre de colonnes et lignes vide.
        //Nb Ligne vides
        for (int i = 0; i < cases.length && ligneVide; i++) {
            for (int j = 0; j < cases[0].length && ligneVide; j++)
                if ( cases[i][j] != 0)
                    ligneVide = false;
            if (ligneVide)
                nbLigVide++;
        }
        for (int j = 0; j < cases[0].length && colonneVide; j++) {
            for (int i = 0; i < cases.length && colonneVide; i++)
                if ( cases[i][j] != 0)
                    colonneVide = false;
            if (colonneVide)
                nbColVide++;
        }

        //On déclare le nouveau tableau à afficher
        int[][] croppedCases = new int[cases.length-nbLigVide][cases[0].length-nbColVide];
        for (int i = 0; i < croppedCases.length; i++) {
            for (int j = 0; j < croppedCases[0].length; j++)
                croppedCases[i][j] = cases[i+nbLigVide][j+nbColVide];
        }


        return croppedCases;
    }
    /**
     * Affichage complet de la pièce, avec nom, matrice, couleur, pivot.
     */
    public void afficherInfosPiece() {

        //On affiche le nom de la pièce.
        System.out.println("Piece \'"+nom+"\' : ");

        afficherPiece();

        //On affiche sa couleur
        System.out.println("Couleur de la piece : "+ getCouleur() );

        //On affiche son pivot.
        System.out.println("Coordonnées du pivot : " + getPivot() );

        System.out.println("---------------");
    }

    /**
     * Affichage simple de la Pièce, de ce style :
     -------
     |     |
     |  X  |
     | XXX |
     -------
     */
    public void afficherPiece() {
        //On récupère la matrice qui la compose.
        int[][] matricePiece = getCases();
        //On récupère les dimensions de la pièce.
        int hauteur = matricePiece.length;
        int largeur = matricePiece[0].length;

        System.out.println(nom + " -- Dimensions : "+hauteur+"*"+largeur);

        afficherMatrice(cases);

    }

    /**
     * Prends en paramètre une matrice d'int à 2 dim et l'affiche en console.
     * Si la case = 0, Case vide, sinon on pose une X. On a un rendu de ce style :
     -------
     |     |
     |  X  |
     | XXX |
     -------
     * @param mat int[][] que l'on veut afficher en console.
     */
    public static void afficherMatrice(int[][] mat) {
        //On récupère les dimensions de la pièce.
        int hauteur = mat.length;
        int largeur = mat[0].length;

        //Ligne bordure au sommet.
        System.out.print("  -");
        for ( int j = 0; j < largeur; j++ )
            System.out.print("---");
        System.out.println("-");

        for (int[] aMat : mat) {
            System.out.print("  |");
            for (int j = 0; j < largeur; j++) {
                if (aMat[j] != 0)
                    System.out.print(" X ");
                else
                    System.out.print("   ");
            }
            System.out.println("|");
        }

        //Ligne bordure au pied.
        System.out.print("  -");
        for ( int j = 0; j < largeur; j++ )
            System.out.print("---");
        System.out.println("-");
    }

    /**
     * Centre la pièce le plus possible sur sa matrice locale en réécrivant la matrice de façon à centrer le pivot.
     */
    public void centrerPiece() {

        /* Conditions et objectifs :
        Il faut que la Piece finisse dans une matrice carré.
        Son pivot doit être le plus centré possible dans la matrice.
        On calcule donc la hauteur et largeux max de la pièce, et on fait une matrice carré capable de contenir tout ça,
        et on recopie la pièce dans cette nouvelle matrice, en la décalant poru centrer le plus possible son pivot.
         */

        //CALCUL DIMENSION DE LA NOUVELLE MATRICE
        int hauteur, hMin = 0, hMax = 0;
        int largeur, lMin = 0, lMax = 0;
        boolean ligneVide = true;

        //On parcours la matrice de la piece ligne par ligne.
        for ( int i = 0; i < cases.length; i++) { //Lignes
            for ( int j = 0; j < cases[0].length ; j++) { //Colonnes

                //Si la case n'est pas vide.
                if ( cases[i][j] != 0 ) {

                    //Si c'est la première case de la ligne
                    if ( ligneVide ) {

                        //TRAITEMENT HAUTEUR
                        //Si on a pas encore de hauteur min, elle est défini ici.
                        if ( hMin == 0 )
                            hMin = i+1;
                        //Sinon c'est une nouvelle hauteur max.
                        else
                            hMax = i+1;

                        //TRAITEMENT LARGEUR
                        //Si on a pas encore de largeur min, elle est défini ici.
                        //Si on a une nouvelle largeur minimum, on modifie lMin.
                        if ( lMin == 0 || j+1 < lMin)
                            lMin = j+1;

                        ligneVide = false;

                    }
                    //Si la case n'est pas vide, c'est peut-être une nouvelle largeur max.
                    else if ( lMax < j+1 )
                            lMax = j+2;
                }

            }
            //On réintiialise ligneVide pour la prochaine ligne
            ligneVide = true;
        }

        //On choisit d'utiliser deux variables et non juste incrémenter une variable h pour permettre
        //la création de pièce "surréaliste" où tout ses cases ne sont pas reliés.
        hauteur = Math.abs(hMax - hMin);
        largeur = Math.abs(lMax - lMin);

        //Nouvelle dimension (carré) de la matrice, on prévoit large.
        int dimM = Math.max(hauteur, largeur) + 1;
        int[][] newCases = new int[dimM][dimM];

        //On calcule le décalage qu'on doit appliquer pour centrer la pièce grâce au pivot.
        //Le pivot idéal est ( dimM/2 , dimM/2 )
        //On calcule la différence par rapport à notre pivot, arrondi car on ne peut pas toujours centrer parfaitement.
        int offsetX = (int) (dimM/2.0 - pivot.x);
        int offsetY = (int) (dimM/2.0 - pivot.y);

    /* DEBUG
        System.out.println("hauteur = " + hauteur);
        System.out.println("largeur = " + largeur);
        System.out.println("dimM = " + dimM);
        System.out.println("offsetX = " + offsetX);
        System.out.println("offsetY = " + offsetY);
        */

        //On remplit la nouvelle matrice avec ces nouvelles informations
        for (int i = 0; i < cases.length; i++ ) {
            for (int j = 0; j < cases[0].length; j++ ) {
                if (cases[i][j] != 0)
                    newCases[i + offsetX][j + offsetY] = cases[i][j];
            }
        }

        //On remplace l'ancienne matrice par la nouvelle, centrée.
        cases = newCases;

        //On met à jour le pivot.
        calculPivotEtTaille();
    }

    //Accesseurs
    public int getHauteur() {
        return cases.length;
    }
    public int getLargeur() {
        return cases[0].length;
    }

    public int[][] getCases() {
        return cases;
    }

    public Vec2d getPivot() {
        return pivot;
    }

    public String getNom() {
        return nom;
    }

    public Color getCouleur() {
        return couleur;
    }
    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public int getTaille(){
        return this.taille;
    }
    public void setTaille(int taille) {
        this.taille = taille;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Color getBordure() {
        return bordure;
    }

    public void setBordure(Color bordure) {
        this.bordure = bordure;
    }

    public boolean isHorizontal() {
        return horizontal;
    }
}