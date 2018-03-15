package mvc.Model;


import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

public class Piece {
    private  String nom;
    private int taille;
    private Color couleur;
    private Vec2d pivot;
    private int[][] cases;


    //Nécessaire pour le RushHour, permet de savoir si la pièce peut se déplacer verticalement ( UP/DOWN ) ou horizontalement ( LEFT/RIGHT )
    private Direction sensDeplacement;


    public Piece(String nom, int[][] cases) {
        this.nom = nom;
        this.couleur = Color.BLACK; //Couleur par défaut.
        this.cases = cases;
        calculPivotEtTaille();
        centrerPiece();
    }

    public Piece(String nom, Color couleur, int[][] cases) {
        this.nom = nom;
        this.couleur = couleur;
        this.cases = cases;
        calculPivotEtTaille();
        centrerPiece();
    }

    /**
     * Tourne une pièce de 90° degré dans sa matrice locale
     * dans le sens trigonométrique (LEFT) ou horaire (RIGHT, ou par défaut) en fonction de la direction sens
     * @param sens
     */
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


    /**
     * Supprime la case de coordonnées (x,y) dans la matrice local de Piece. (Set sa valeur à 0)
     * @param x Coordonnée x de la 'case' à supprimer.
     * @param y Coordonnée y de la 'case' à supprimer.
     */
    public void supprimer(int x, int y) {
        if (x < cases.length && y < cases[0].length)
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
     * Affichage complet de la pièce, avec nom, matrice, couleur, pivot.
     */
    public void afficherInfosPiece() {

        //On affiche le nom de la pièce.
        System.out.println("Piece \'"+nom+"\' : ");

        afficherPiece();

        //On affiche sa couleur
        //TODO : Créer un COLOR TO STRING. Pour l'instant getCouleur() et getCouleur().toString() renvoie tout les deux un code hexadecimal dégeulasse.
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

    }



    //TODO : translater(dir) est potentiellement inutile, je ne l'ai pas utilisé pour centrer la pièce. On la garde atm mais si on lui trouve aucune utilité on la supprimera.
    /**
     * Translate toutes les cases de la pièce d'une case dans la matrice locale vers la direction donnée.
     * @param dir Direction vers laquelle translater la piece
     */
    //Testé & Fonctionnelle.
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
        calculPivotEtTaille();
    }

    //Centre la pièce le plus possible sur sa matrice locale en translatant la matrice de façon à centrer le pivot.
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
        System.out.println("hauteur = " + hauteur);
        System.out.println("largeur = " + largeur);

        //Nouvelle dimension (carré) de la matrice, on prévoit large.
        int dimM = Math.max(hauteur, largeur) + 1;
        int[][] newCases = new int[dimM][dimM];

        //On calcule le décalage qu'on doit appliquer pour centrer la pièce grâce au pivot.
        //Le pivot idéal est ( dimM/2 , dimM/2 )
        //On calcule la différence par rapport à notre pivot, arrondi car on ne peut pas toujours centrer parfaitement.
        int offsetX = (int) (dimM/2.0 - pivot.x);
        int offsetY = (int) (dimM/2.0 - pivot.y);

        System.out.println("dimM = " + dimM);
        System.out.println("offsetX = " + offsetX);
        System.out.println("offsetY = " + offsetY);

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

    public Direction getSensDeplacement() {
        return sensDeplacement;
    }

    public void setSensDeplacement(Direction sensDeplacement) {
        this.sensDeplacement = sensDeplacement;
    }
}