package mvc.Blokus.VueControleurBlokus;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;

import mvc.Model.*;
import mvc.Blokus.ModeleBlokus.*;
import mvc.VueControleur.*;

import java.util.Observable;
import java.util.Observer;


public class VueControleur extends Application implements Observer {



    private Plateau plateau= new Plateau(20, 20);
    private Partie partie = new Partie(plateau, 2);


    /*
    Actions possible du joueur de Blokus dans la vue :
        -> Cliquer sur une pièce de sa liste de pièce (affiché à côté) pour la définir comme pièce active
        -> Cliquer sur le plateau pour poser la pièce active
            -> Feedback si la pose est pas possible.
            -> Affichage de la pièce en hoover du plateau ?
        -> Bouton abandonner. (Si on n'implémente pas de vérification de condition de victoire).
    */

    /*

     */


    @Override
    public void start(Stage primaryStage){


        //Préparation de la scene de la grille de jeu.
        BorderPane bPane = new BorderPane();

        //bPane.setPadding(new Insets(5, 2, 2, 5));

        //TOP -- Un Titre
        Text titre = new Text("--- BLOKUS ---");
        titre.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        titre.setFill(Color.MEDIUMPURPLE);
        bPane.setTop(titre);
        bPane.setAlignment(titre, Pos.CENTER);

        //CENTRE -- Plateau de Jeu
        GrilleVue grille = new GrilleVue(20, 20);
        bPane.setCenter(grille);

        //RIGHT -- Liste des pièces du joueur actif

        //LEFT -- Liste des Joueurs.

        Text listeJ = new Text("Liste des Joueurs");
        listeJ.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        Text textJ1 = new Text("Joueur 1");
        textJ1.setFont(Font.font("Helvetica", 15));
        Text textJ2 = new Text("Joueur 2");
        textJ2.setFont(Font.font("Helvetica", 15));

        VBox vBoxJ = new VBox(5);
        vBoxJ.setPadding(new Insets(10));

        vBoxJ.setMargin(listeJ, new Insets(60, 5, 2, 5));
        vBoxJ.setMargin(textJ1, new Insets(5, 5, 5, 30));
        vBoxJ.setMargin(textJ2, new Insets(5, 5, 5, 30));

        vBoxJ.getChildren().addAll(listeJ, textJ1, textJ2);
        bPane.setLeft(vBoxJ);

        //SCENE

        Scene scene = new Scene(bPane);

        primaryStage.setTitle("BLOKUS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public static void main(String[] args) {
        launch(args);
    }
}