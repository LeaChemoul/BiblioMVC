package mvc.Blokus.ModeleBlokus;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import mvc.Model.*;
import mvc.Blokus.ModeleBlokus.*;

import java.util.ArrayList;

public class Partie {

    private Plateau plateau;
    private Joueur[] joueurs = new Joueur[4];

    public Partie(Plateau p){
        this.plateau = p;
        for (int i = 0; i < 4; i++ ) {
            joueurs[i] = new Joueur(i+1);
        }
    }


    //Méthodes

    //Génération du pool de pièces des joueurs, propre au blokus, en utilisant le Piece Builder.
    public ArrayList<Piece> GenererPieces() {

        PieceBuilder builder = new PieceBuilder();

        //Pièce à une case
        Vec2d[] PieceCoor = { new Vec2d(1, 1) };
        builder.addPiece("Piece1-1", Color.GREEN, PieceCoor);

        return builder.exporterArrayListe();
    }

    public Plateau getPlateau() {
        return plateau;
    }
}
