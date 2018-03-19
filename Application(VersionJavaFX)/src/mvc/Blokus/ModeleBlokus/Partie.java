package mvc.Blokus.ModeleBlokus;

import com.sun.javafx.geom.Vec2d;
import java.util.Observable;
import javafx.scene.paint.Color;
import mvc.Model.*;

import java.util.ArrayList;

public class Partie extends Observable {

    private PieceBuilder builder = new PieceBuilder();
    private Plateau p;
    private Joueur[] joueurs = new Joueur[2]; //Max 4 joueurs

    private int numJoueurActif;
    private Joueur joueurActif;

    private Piece pieceCourante;



    public Partie(Plateau p, int nbJoueur){

        this.p = p;

        //On génère les pièces du pool
        genererPieces();

        // 2 à 4 Joueurs seulement
        if ( nbJoueur < 2 ) nbJoueur = 2;
        else if ( nbJoueur > 4 ) nbJoueur = 4;

        //On attribut à chaque joueur une couleur et sa liste de pièces.
        for (int i = 0; i < nbJoueur ; i++ ) {
            //On donne une couleur différente pour les pièces de chaque joueurs
            builder.setCouleurAll( intToColor(i) );
            joueurs[i] = new Joueur(i+1, p, intToColor(i), builder.exporterArrayList());
        }

    }


    //Méthodes

    /**
     * Modifie numJoueurActif et JoueurActif avec le joueur suivant.
     */
    public void joueurSuivant() {

        numJoueurActif++;
        if ( numJoueurActif > joueurs.length )
            numJoueurActif = 1;
        joueurActif = joueurs[numJoueurActif-1];

        setChanged();
        notifyObservers(joueurActif);
    }

    //TODO : Laisser ici ou la déplacer en static ailleurs pour etre réutilisable ?
    public Color intToColor(int n) {
        switch (n) {
            case 0:
                return Color.RED;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.YELLOW;
            default:
                return Color.PURPLE;
        }
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


    public Piece getPieceCourante() {
        return pieceCourante;
    }

    public Joueur getJoueur (int numJ) {
        if ( numJ > 0 && numJ < joueurs.length )
            return joueurs[numJ-1];
        return null;
    }
    public Plateau getPlateau() {
        return p;
    }
}
