package mvc.Blokus.ModeleBlokus;

import java.util.Observable;
import javafx.scene.paint.Color;
import mvc.Model.*;

public class Partie extends Observable {

    private PieceBuilder builder = new PieceBuilder();
    private Plateau plateau;
    private JoueurBlokus[] joueurs = new JoueurBlokus[4]; //Max 4 joueurs
    int nbJoueurs = 2;

    private int numJoueurActif;
    private JoueurBlokus joueurActif;



    public Partie(Plateau p, int nbJoueur){

        this.plateau = p;
        this.nbJoueurs = nbJoueur;

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
     * Modifie JoueurActif avec le joueur suivant.
     */
    public void joueurSuivant() {

        int numJoueurActif = joueurActif.getNumJoueur();
        if ( numJoueurActif+1 > nbJoueurs )
            joueurActif = joueurs[0];
        else
            joueurActif = joueurs[numJoueurActif];

        setChanged();
        notifyObservers(joueurActif);
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
            supprimerPiece(joueurActif, piece);
            //Il n'y a alors plus de pièce courantes
            plateau.setPieceCourante(null);
            joueurSuivant();
        }

        return peutJouer;

    }

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

    public boolean isDiagonalePieceAllie(Color couleur, int i, int j) {
        Case[][] matP = plateau.getTableauJeu();
        boolean isDiagPiece = false;

        if ( isHorsPlateau(i, j) ) {
            return false;
        }

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
     * Supprime la Piece piece de la liste des pièces du joueur J.
     * @param j Joueur
     * @param piece Pièce à supprimer
     */
    public void supprimerPiece(JoueurBlokus j, Piece piece) {
        j.supprimerPiece(piece);

        setChanged();
        notifyObservers(piece);
    }

    /**
     * Supprimer la piece courante du plateau.
     */
    public void supprimerPieceCourante() {
        joueurActif.supprimerPiece(getPieceCourante());

        setChanged();
        notifyObservers(getPieceCourante());

        setPieceCourante(null);
    }

    //TODO : Laisser ici ou la déplacer en static ailleurs pour etre réutilisable ?
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

    public boolean aGagne(Joueur joueur) {
        return joueur.poolIsEmpty();
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
