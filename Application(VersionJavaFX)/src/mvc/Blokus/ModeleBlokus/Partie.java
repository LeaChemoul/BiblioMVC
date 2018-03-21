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
    public void jouerPiece(Piece piece, int i, int j) {

        //On pose la pièce
        plateau.poserPiecePlateau(piece, i, j);
        //On la supprime de la liste des pièces du joueur
        supprimerPiece(joueurActif, piece);
        //Il n'y a alors plus de pièce courantes
        plateau.setPieceCourante(null);

    }

    public boolean peutPoserPiece(JoueurBlokus Joueur, Piece piece, int i_row, int j_col,) {

        //Les règles pour poser une pièce au Blokus :
        // - On peut pas poser une pièce sur une autre
        // - La pièce ne peut pas toucher directement une autre pièce du même joueur.
        // - La pièce doit toucher en "diagonale" au moins une autre pièce du même joueur.

        //On crée un masque de collision pour la zone du plateau, un int[][] de la meme taille que la matrice de la pièce,
        // On la superposera à la matrice de la pièce pour savoir si on peut poser la pièce ici, et on comparera les cases de la pièce
        // à la valeur de la case du masque correspondante, tel que :
        // 0 = Aucune restriction
        // 1 = Cases qui doivent être vide côté pièce ( Cases correspondant à la pièce ou ses cases voisines directes )
        // 2 = Cases où au moins une doit être touché par la pièce.

        int[][] masque = new int[piece.getHauteur()][piece.getLargeur()];
        for (int i = 0; i < piece.getHauteur(); i++) {
            for (int j = 0; j < piece.getLargeur(); j++) {

                //Si la case est bien dans le plateau.
                if ( i_row >= 0 && j_col >= 0 && i_row < plateau.getHauteur() && j_col < plateau.getLargeur() ) {

                }

            }
        }


        return true;
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
