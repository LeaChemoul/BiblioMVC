package mvc.Blokus.ModeleBlokus;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import mvc.Model.*;

import java.util.ArrayList;

public class Partie {

    private PieceBuilder builder = new PieceBuilder();
    private Plateau plateau;
    private Joueur[] joueurs = new Joueur[4];

    public Partie(Plateau p){

        this.plateau = p;
        for (int i = 0; i < 4; i++ ) {
            joueurs[i] = new Joueur(i+1);
        }

    }


    //Méthodes

    /**
     * Génération du pool de pièces des joueurs, propre au blokus, en utilisant le PieceBuilder.
     * @return La liste de Piece de toutes les pieces utilisées pour une partie de Blokus.
     */
    public void genererPieces() {

        //Pièce à une case
        builder.addPiece("Piece1-1", new double[]{0,0});

        //Pièce à deux cases
        builder.addPiece("Piece2-1", new double[]{0,0, 0,1});

        //Pièces à trois cases
        builder.addPiece("Piece3-1", new double[]{0,0, 0,1 , 0,2});
        builder.addPiece("Piece3-2", new double[]{0,0, 1,0 , 1,1});

        //Pièces à trois cases
        builder.addPiece("Piece4-1", new double[]{0,0, 0,1 , 0,2, 0,3});
        builder.addPiece("Piece4-2", new double[]{2,0, 2,1 , 1,1, 0,1});
        builder.addPiece("Piece4-3", new double[]{0,0, 1,0 , 1,1, 2,0});
        builder.addPiece("Piece4-4", new double[]{0,0, 1,0 , 1,1, 0,1});
        builder.addPiece("Piece4-5", new double[]{0,0, 0,1 , 1,1, 1,2});

        //DEBUG
        builder.afficherPieces();
    }

    public Plateau getPlateau() {
        return plateau;
    }
}
