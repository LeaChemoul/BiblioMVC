package mvc.Blokus.VueControleurBlokus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;

import mvc.Model.*;
import mvc.Blokus.ModeleBlokus.*;
import mvc.VueControleur.GrillePiece;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ListePiece extends TilePane{

    private JoueurBlokus joueur;
    private Partie partie;

    private HashMap<Piece, GridPane> listeGrillesPieces;

    public ListePiece(JoueurBlokus joueur, Partie partie) {
        this.joueur = joueur;
        this.partie = partie;
        this.listeGrillesPieces = new HashMap<Piece, GridPane>();

        //On remplit une TilePane avec chaque pièce du joueur.
        for (Piece piece: joueur.getPoolDePiece() ) {

            //On crée une gridPane avec la pièce.
            GridPane grillePiece = new GrillePiece( piece.getCases(), joueur.getCouleur(), true,15);
            listeGrillesPieces.put(piece, grillePiece);
            grillePiece.setPadding(new Insets(3));
            getChildren().add(grillePiece);

            //CONTROLLEURS : Quand on clique sur une pièce, elle devient la pièce active du plateau (celle qu'on peut manipuler).
            grillePiece.setOnMouseClicked(event -> {
                partie.setPieceCourante(piece);
            });
        }
    }

    public void supprimerPiece(Piece piece) {
        getChildren().remove(listeGrillesPieces.get(piece));
        listeGrillesPieces.remove(piece);
    }
    public void supprimerGrille(GridPane grid) {
        getChildren().remove(grid);
    }


    public void update() {

        listeGrillesPieces.forEach( (piece, grid) -> {
            getChildren().remove(grid);
        });

        listeGrillesPieces.clear();
        for (Piece piece: joueur.getPoolDePiece() ) {

            //On refait une nouvelle grille à partir de l'état actuel de la pièce.
            GridPane grillePiece = new GrillePiece( piece.getCases(), joueur.getCouleur(), true,15);
            grillePiece.setPadding(new Insets(3));
            listeGrillesPieces.put(piece, grillePiece);

            //CONTROLLEURS : Quand on clique sur une pièce, elle devient la pièce active du plateau (celle qu'on peut manipuler).
            grillePiece.setOnMouseClicked(event -> {
                partie.setPieceCourante(piece);
                System.out.println("Piece active changée ! - " + piece.getNom());
                partie.getPieceCourante().afficherPiece();
            });

            //On la rajoute à gridPane.
            getChildren().add(listeGrillesPieces.get(piece));
        }
    }

    public void update(Piece piece) {

    }

    public GridPane getGridPiece(Piece piece) {
        return listeGrillesPieces.get(piece);
    }



}
