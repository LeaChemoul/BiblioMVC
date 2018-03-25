package mvc.Blokus.ModeleBlokus;

import java.util.Observable;
import javafx.scene.paint.Color;
import mvc.Model.*;

public class Partie extends Observable {

    private PieceBuilder builder = new PieceBuilder();
    private Plateau plateau;
    private JoueurBlokus[] joueurs = new JoueurBlokus[4]; //Max 4 joueurs

    private int nbJoueurs;
    private int nbJoueursRestant;

    private JoueurBlokus joueurActif;



    public Partie(Plateau p, int nbJoueurs){

        this.plateau = p;
        this.nbJoueurs = nbJoueurs;
        this.nbJoueursRestant = nbJoueurs;

        //On génère les pièces du pool
        genererPieces();

        // 2 à 4 Joueurs seulement
        if ( nbJoueurs < 2 ) nbJoueurs = 2;
        else if ( nbJoueurs > 4 ) nbJoueurs = 4;

        //On attribut à chaque joueur une couleur et sa liste de pièces.
        for (int i = 0; i < 4 ; i++ ) {
            //On donne une couleur différente pour les pièces de chaque joueurs
            builder.setCouleurAll( intToColor(i) );
            joueurs[i] = new JoueurBlokus(i+1, p, intToColor(i), builder.exporterArrayList());
        }


        joueurActif = joueurs[0];

    }


    //Méthodes

    /**
     * Modifie JoueurActif avec le joueur suivant. Saute les joueurs ayant abandonné.
     */
    public boolean joueurSuivant() {

        int numJoueurActif;
        JoueurBlokus premierJoueurActif = joueurActif;

        //Si le joueur actif est un joueur ayant abandonné, on passe directement au joueur suivant.
        do {
            numJoueurActif = joueurActif.getNumJoueur();
            if ( numJoueurActif+1 > nbJoueurs )
                joueurActif = joueurs[0];
            else
                joueurActif = joueurs[numJoueurActif];

            //Si on a déjà passé tout les joueurs, erreur !
            if ( joueurActif == premierJoueurActif ) {
                System.out.println("On a fait le tour des joueurs !");
                setChanged();
                notifyObservers(joueurActif);
                return false;
            }

        } while ( joueurActif.isaAbandone() );

        setChanged();
        notifyObservers();

        return true;
    }

    /**
     * Joue la Piece piece donnée en argument à la case i,j du plateau de jeu.
     */
    public boolean jouerPiece(Piece piece, int i, int j) {

        boolean peutJouer = false;

        //Si c'est le premier coup du joueur
        if ( joueurActif.isPremierCoup() && toucheCoinJoueur(joueurActif, piece, i, j) ) {
            joueurActif.setPremierCoup(false);
            peutJouer = true;
        }
        else if ( !joueurActif.isPremierCoup() && peutPoserPiece(joueurActif, piece, i, j) ){
            peutJouer = true;
        }
        else {
            System.out.println("Vous ne pouvez pas jouer ce coup !");
        }

        if ( peutJouer ) {
            //On pose la pièce
            plateau.poserPiecePlateau(piece, i, j);
            //On la supprime de la liste des pièces du joueur
            //supprimerPiece(joueurActif, piece);
            supprimerPieceCourante();

        }

        return peutJouer;

    }

    /**
     * Permet de savoir si on peut poser une pièce, suivant les règles du blokus :<br>
      - On peut pas poser une pièce sur une autre<br>
      - La pièce ne peut pas toucher directement une autre pièce du même joueur.<br>
      - La pièce doit toucher en "diagonale" au moins une autre pièce du même joueur.<br>
     * @param joueur Joueur jouant la pièce
     * @param piece Pièce à poser
     * @param i_row ligne du plateau où jouer la pièce
     * @param j_col colonne du plateau où jouer la pièce
     * @return true si on peut poser la pièce à la case (i,j) du plateau, false sinon.
     */
    public boolean peutPoserPiece(JoueurBlokus joueur, Piece piece, int i_row, int j_col) {

        //Les règles pour poser une pièce au Blokus :
        // - On peut pas poser une pièce sur une autre
        // - La pièce ne peut pas toucher directement une autre pièce du même joueur.
        // - La pièce doit toucher en "diagonale" au moins une autre pièce du même joueur.

        int[][] matPiece = piece.croppedPiece();
        int hauteur = matPiece.length;
        int largeur = matPiece[0].length;
        boolean estDiago = false;
        int num_lig, num_col;

        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {

                num_lig = i + i_row;
                num_col = j + j_col;

                //Si un des bouts de la pièce
                if (matPiece[i][j] != 0) {
                    //est hors plateau, on ne peut pas poser la pièce.
                    if ( isHorsPlateau( num_lig, num_col ) ) {
                        System.out.println("case hors plateau");
                        return false;
                    }
                    //est adjacente à une case allié
                    else if ( isAdjacentePieceAllie( joueur.getCouleur(), num_lig, num_col) ) {
                        System.out.println("case adj allie");
                        return false;
                    }
                    //est au dessus une autre piece
                    else if ( plateau.getTableauJeu()[num_lig][num_col] != null ) {
                        System.out.println("Par dessus une case");
                        return false;
                    }
                    //est en diagonale d'une case allie
                    else if ( isDiagonalePieceAllie( joueur.getCouleur(), num_lig, num_col) )
                        estDiago = true;

                }
            }
        }

        //Si il y a une collision, on a déjà renvoyé false à ce point.
        //On vérifie donc juste si il y a une case qui touche diagonalement celle de la pièce.

        if (!estDiago)
            System.out.println("Pas de case diag allie");
        return estDiago;


    }

    /**
     * Permet de savoir si case est adjacente à une case avec une pièce de la couleur donnée.
     * @param couleur Couleur de la pièce
     * @param i ligne de la case dans le plateau
     * @param j colonne de la case dans le plateau
     * @return vrai si la case touche une case avec une pièce de couleur 'couleur', renvoie false sinon.
     */
    public boolean isAdjacentePieceAllie(Color couleur, int i, int j) {
        Case[][] matP = plateau.getTableauJeu();
        boolean isAdjPiece = false;

        if ( isHorsPlateau(i, j) ) {
            return false;
        }

        //Case Nord
        if ( i > 0 && matP[i-1][j] != null && matP[i-1][j].getCouleur() == couleur )
            isAdjPiece = true;
        //Case Sud
        else if ( i < plateau.getHauteur()-1 && matP[i+1][j] != null && matP[i+1][j].getCouleur() == couleur)
            isAdjPiece = true;
        //Case Ouest
        else if ( j > 0 && matP[i][j-1] != null && matP[i][j-1].getCouleur() == couleur)
            isAdjPiece = true;
        //Case Est
        else if ( j < plateau.getLargeur()-1 && matP[i][j+1] != null && matP[i][j+1].getCouleur() == couleur)
            isAdjPiece = true;

        return isAdjPiece;
    }


    /**
     * Renvoie true si la case touche en diagonale (via un de ses coins) une autre pièce du même joueur.
     * @param couleur Couleur de la pièce ( et donc du joueur )
     * @param i numéro de ligne de la case à tester
     * @param j numéro de colonne de de la case à tester
     * @return true si la case touche en diagonale une case allié, false sinon.
     */
    public boolean isDiagonalePieceAllie(Color couleur, int i, int j) {
        Case[][] matP = plateau.getTableauJeu();
        boolean isDiagPiece = false;

        if ( isHorsPlateau(i, j) ) {
            return isDiagPiece;
        }

        //On fait beaucoup de if pour la même instruction pour rendre le code plus clair.

        //Case Nord-Ouest
        if ( i > 0 && j > 0 && matP[i-1][j-1] != null && matP[i-1][j-1].getCouleur() == couleur)
            isDiagPiece = true;
        //Case SO
        else if ( i < plateau.getHauteur()-1 && j > 0 && matP[i+1][j-1] != null && matP[i+1][j-1].getCouleur() == couleur)
            isDiagPiece = true;
        //Case NE
        else if ( i > 0 && j < plateau.getLargeur()-1 && matP[i-1][j+1] != null && matP[i-1][j+1].getCouleur() == couleur)
            isDiagPiece = true;
        //Case SE
        else if ( i < plateau.getHauteur()-1 && j < plateau.getLargeur()-1 && matP[i+1][j+1] != null && matP[i+1][j+1].getCouleur() == couleur)
            isDiagPiece = true;

        return isDiagPiece;
    }


    /**
     * Permet de savoir si le premier coup du joueur est valide :<br>
     *     - la pièce touche le coin qui lui est attribué<br>
     *     - la pièce ne dépasse pas le plateau<br>
     * @param joueur Joueur jouant la pièce
     * @param piece Piece à jouer
     * @param i_row ligne du plateau où jouer la pièce
     * @param j_col colonne du plateau où jouer la pièce
     * @return vrai si la pièce touche le coin du joueur et est dans les limites du plateau, false sinon.
     */
    public boolean toucheCoinJoueur(JoueurBlokus joueur, Piece piece, int i_row, int j_col) {

        int[][] matPiece = piece.croppedPiece();
        int hauteur = matPiece.length;
        int largeur = matPiece[0].length;

        int num_lig, num_col;

        boolean toucheCoin = false;
        for (int i = 0; i < hauteur; i++) {
            for (int j = 0; j < largeur; j++) {
                num_lig = i + i_row;
                num_col = j + j_col;

                //Si un bout de la pièce
                if ( matPiece[i][j] != 0  ) {
                    //touche le coin d'un joueur
                    if (num_lig == joueur.getCoinDepartX() && num_col == joueur.getCoinDepartY())
                        toucheCoin = true;
                    //est hors du plateau
                    if (isHorsPlateau(num_lig, num_col)) {
                        System.out.println(" (i,j) = " + matPiece[i][j] + " hors plateau ! ( toucheCoin )");
                        return false;
                    }
                }
            }
        }

        return toucheCoin;
    }

    /**
     * Renvoie false si la case (i,j) n'est pas dans les limites du plateau
     * @param i num ligne plateau
     * @param j num colonne plateau
     * @return vrai si la case appartient au plateau, faux sinon.
     */
    public boolean isHorsPlateau(int i, int j) {
        return i < 0 || j < 0 || i >= plateau.getHauteur() || j >= plateau.getLargeur();
    }

    /**
     * Fais abandonner le joueur. Renvoie true si il le joueur a bien abandonné, false si il avait déjà abandonné
     * @param joueur joueur qui abandonne.
     * @return true si il y a un gagnant, false sinon.
     */
    public boolean abandonner(JoueurBlokus joueur) {

        if ( joueur.isaAbandone() )
            return false;
        else {
           joueur.setaAbandone(true);
           nbJoueursRestant--;
           return true;
        }

    }

    /**
     * Renvoie le joueur gagnant. Renvoie null si il n'y a aucun gagnant.
     * @return joueur gagnant.
     */
    public JoueurBlokus joueurGagnant() {

        for (int i = 0; i < nbJoueurs; i++) {
            //Si il ne reste qu'un joueur et que c'est le joueur qui n'a pas abandonné, c'est le joueur gagnant
            //OU si le joueur n'a plus de pièce, c'est le joueur gagnant.
            if ( ( nbJoueursRestant == 1  && !joueurs[i].isaAbandone() ) || joueurs[i].poolIsEmpty() )
                return joueurs[i];
        }

        return null;

    }

    /**
     * Supprime la Piece piece de la liste des pièces du joueur J.
     * @param j Joueur
     * @param piece Pièce à supprimer
     */
    public void supprimerPiece(JoueurBlokus j, Piece piece) {
        j.supprimerPiece(piece);

        //setChanged();
        //notifyObservers(piece);
    }

    /**
     * Supprimer la piece courante du plateau et de la liste des pièces du joueur actif.
     */
    public void supprimerPieceCourante() {
        joueurActif.supprimerPiece(getPieceCourante());
        setPieceCourante(null);

        //setChanged();
        //notifyObservers();

    }

    /**
     * Associe un int à une couleur, permet d'attribuer des couleurs aux joueurs :<br>
     * 0 -> RED <br>
     * 1 -> BLUE<br>
     * 2 -> GREEN<br>
     * 3 -> PURPLE<br>
     * autres -> GREY<br>
     * @param n Int à convertir en couleur
     * @return renvoie la couleur correspodante à l'entier donné.
     */
    public static Color intToColor(int n) {
        switch (n) {
            case 0:
                return Color.RED;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.PURPLE;
            default:
                return Color.GREY;
        }
    }

    public void genererPiecesDemo() {
        //Pièce à une case
        builder.addPiece("Piece1-1", new double[]{0,0});

        //Pièce à deux cases
        builder.addPiece("Piece2-1", new double[]{0,0, 1,0});

        //Pièces à trois cases
        builder.addPiece("Piece3-1", new double[]{0,0, 1,0 , 2,0});
        builder.addPiece("Piece3-2", new double[]{0,0, 1,0 , 1,1});
    }

    /**
     * Génération du pool de pièces des joueurs, propre au blokus, en utilisant le PieceBuilder.
     * @return La liste de Piece de toutes les pieces utilisées pour une partie de Blokus.
     */
    public void genererPieces() {

        //Pièce à une case
        builder.addPiece("Piece1-1", new double[]{0,0});

        //Pièce à deux cases
        builder.addPiece("Piece2-1", new double[]{0,0, 1,0});

        //Pièces à trois cases
        builder.addPiece("Piece3-1", new double[]{0,0, 1,0 , 2,0});
        builder.addPiece("Piece3-2", new double[]{0,0, 1,0 , 1,1});

        //Pièces à quatre cases
        builder.addPiece("Piece4-1", new double[]{0,0, 1,0 , 2,0, 3,0});
        builder.addPiece("Piece4-2", new double[]{2,0, 2,1 , 1,1, 0,1});
        builder.addPiece("Piece4-3", new double[]{0,0, 1,0 , 1,1, 2,0});
        builder.addPiece("Piece4-4", new double[]{0,0, 1,0 , 1,1, 0,1});
        builder.addPiece("Piece4-5", new double[]{0,0, 0,1 , 1,1, 1,2});

        //Pièces à cinq cases
        builder.addPiece("Piece5-1", new double[]{0,0, 1,0, 2,0, 3,0, 4,0});
        builder.addPiece("Piece5-2", new double[]{3,0, 3,1, 2,1, 1,1, 0,1});
        builder.addPiece("Piece5-3", new double[]{0,1, 1,1, 2,1, 2,0, 3,0});
        builder.addPiece("Piece5-4", new double[]{0,1, 1,0, 1,1, 2,1, 2,0});
        builder.addPiece("Piece5-5", new double[]{0,0, 0,1, 1,1, 2,1, 2,0});
        builder.addPiece("Piece5-6", new double[]{0,0, 1,0, 2,0, 3,0, 1,1});
        builder.addPiece("Piece5-7", new double[]{0,1, 1,1, 2,1, 2,2, 2,0});
        builder.addPiece("Piece5-8", new double[]{0,0, 1,0, 2,0, 2,1, 2,2});
        builder.addPiece("Piece5-9", new double[]{0,0, 0,1, 1,1, 1,2, 2,2});
        builder.addPiece("Piece5-10", new double[]{0,0, 1,0, 1,1, 1,2, 2,2});
        builder.addPiece("Piece5-11", new double[]{0,0, 1,0, 1,1, 1,2, 2,1});
        builder.addPiece("Piece5-12", new double[]{0,1, 1,0, 1,1, 1,2, 2,1});

        //DEBUG
        //builder.afficherPieces();
    }


    //Accesseurs


    public void setNbJoueurs(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public int getNbJoueursRestant() {
        return nbJoueursRestant;
    }

    public void setNbJoueursRestant(int nbJoueursRestant) {
        this.nbJoueursRestant = nbJoueursRestant;
    }

    public JoueurBlokus getJoueurActif() {
        return joueurActif;
    }

    public void setJoueurActif(JoueurBlokus joueurActif) {
        this.joueurActif = joueurActif;
    }

    public int getNumJoueurActif() {
        return joueurActif.getNumJoueur();
    }
    public Piece getPieceCourante() {
        return plateau.getPieceCourante();
    }

    public void setPieceCourante(Piece piece) {
        plateau.setPieceCourante(piece);
    }

    public JoueurBlokus getJoueur (int indexJ) {
        if ( indexJ >= 0 && indexJ < 4 )
            return joueurs[indexJ];
        return null;
    }
    public Plateau getPlateau() {
        return plateau;
    }
}
