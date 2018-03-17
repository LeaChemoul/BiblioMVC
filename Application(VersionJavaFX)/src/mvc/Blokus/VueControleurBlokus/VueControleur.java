package mvc.Blokus.VueControleurBlokus;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
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
import mvc.VueControleur.GrilleVue;

import java.util.Observable;
import java.util.Observer;


public class VueControleur extends Application implements Observer {


    private final int NB_JOUEURS = 2;

    private Plateau plateau = new Plateau(20, 20);
    private int joueurActif = 1;
    private Partie partie = new Partie(plateau, 2);

    private int xCase;
    private int yCase;

    private BorderPane bPane = new BorderPane();
    private Rectangle[][] tab;

    /*
    Actions possible du joueur de Blokus dans la vue :
        -> Cliquer sur une pièce de sa liste de pièce (affiché à côté) pour la définir comme pièce active
        -> Cliquer sur le plateau pour poser la pièce active
            -> Feedback si la pose n'est pas possible.
            -> Affichage de la pièce en hoover du plateau ?
            -> Lorsqu'une pièce est posée, on passe au joueur suivant.
        -> Bouton abandonner. (Si on n'implémente pas de vérification de condition de victoire).
    */


    @Override
    public void start(Stage primaryStage){

        partie.getPlateau().addObserver(this);
        partie.addObserver(this);

        //Préparation de la scene de la grille de jeu.


        // ------------ TOP -- Un Titre
        Text titre = new Text("--- BLOKUS ---");
        titre.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        titre.setFill(Color.MEDIUMPURPLE);
        bPane.setAlignment(titre, Pos.CENTER);
        bPane.setTop(titre);

        //------------ RIGHT -- Liste des pièces du joueur actif
        TilePane tileP = new TilePane();

        //On remplit une TilePane avec chaque pièce du joueur.
        for (Piece piece: partie.getJoueur(joueurActif).getPoolDePiece()) {

            GridPane grillePiece = new Grille( piece.getCases(), piece.getCouleur(), true );
            grillePiece.setPadding(new Insets(3));
            tileP.getChildren().add(grillePiece);

            //CONTROLLEURS : Quand on clique sur une pièce, elle devient la pièce active du plateau (celle qu'on peut manipuler).
            grillePiece.setOnMouseClicked(event -> {
                    plateau.setPieceCourante(piece);
                    System.out.println("Piece active changée ! - " +piece.getNom());
                });
        }

        //tileP.setAlignment(Pos.TOP_RIGHT);
        //bPane.setAlignment(tileP, Pos.BOTTOM_RIGHT);
        bPane.setMargin(tileP, new Insets(20, 0, 0, 10));
        bPane.setRight(tileP);


        // ------------- CENTER -- Plateau de Jeu
        GridPane grilleJeu = new GridPane();
        tab = new Rectangle[plateau.getHauteur()][plateau.getLargeur()];
        for(int i = 0; i < plateau.getHauteur(); i++)
            for(int j = 0; j < plateau.getLargeur(); j++){
                tab[i][j] = new Rectangle();
                tab[i][j].setHeight(30);
                tab[i][j].setWidth(30);
                tab[i][j].setFill(Color.WHITE);
                grilleJeu.add(tab[i][j], i, j);
                //CONTROLEUR -- Si on clique sur une case ça essaye de poser la pièce
                xCase = i;
                yCase = j;
                tab[i][j].setOnMouseClicked( event -> {
                    System.out.println("xCase = " +xCase +" yCase = " + yCase);
                    plateau.poserPiecePlateau(plateau.getPieceCourante(), xCase, yCase) ;
                    });

            }
        grilleJeu.setGridLinesVisible(true);
        grilleJeu.setPadding(new Insets(10, -120, 10, 20));

        //bPane.setAlignment(grilleJeu, Pos.CENTER_RIGHT);
        bPane.setCenter(grilleJeu);


        //LEFT -- Liste des Joueurs.

        //On utilise un tableau de joueur pour interagir plus facilement en fct du joueur actif.
        Text[] textsJ = new Text[NB_JOUEURS+1];
        //Texte Titre
        textsJ[0] = new Text("Liste des Joueurs");
        textsJ[0].setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        //Texte nom des joueurs
        for ( int i = 1; i < NB_JOUEURS+1; i++) {
            textsJ[i] = new Text("Joueur "+i);
            textsJ[i].setFont(Font.font("Helvetica", 15));
        }

        VBox vBoxJ = new VBox(5);
        vBoxJ.setPadding(new Insets(10));

        vBoxJ.setMargin(textsJ[0], new Insets(60, 5, 2, 5));
        for ( int i = 1; i < NB_JOUEURS+1; i++)
            vBoxJ.setMargin(textsJ[i], new Insets(5, 5, 5, 30));

        vBoxJ.getChildren().addAll(textsJ);
        bPane.setLeft(vBoxJ);

        //SCENE


        Scene scene = new Scene(bPane);

        primaryStage.setTitle("BLOKUS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void update(Observable o, Object arg) {

        if (o instanceof Partie) {

        }
        else if (o instanceof Plateau) {

            //DEBUG GRILLE

            System.out.print("  -");
            for ( int j = 0; j < plateau.getLargeur(); j++ )
                System.out.print("---");
            System.out.println("-");

            for ( int i = 0; i < plateau.getHauteur(); i++ ) {
                System.out.print("  |");
                for (int j = 0; j < plateau.getLargeur(); j++) {
                    //Si la case est égale à 0, elle est vide. (Convention actuelle. A CHANGER ?)
                    if ( plateau.getTableauJeu()[i][j] != null )
                        System.out.print(" X ");
                    else
                        System.out.print("   ");
                }
                System.out.println("|");
            }

            //Ligne bordure au pied.
            System.out.print("  -");
            for ( int j = 0; j < plateau.getLargeur(); j++ )
                System.out.print("---");
            System.out.println("-");



            //On rafraichit la grille.
            for( int a = 0; a < plateau.getLargeur(); a++)
                for( int b = 0; b < plateau.getHauteur(); b++){

                    if(plateau.getTableauJeu()[b][a] != null)
                        tab[a][b].setFill(plateau.getTableauJeu()[b][a].getCouleur());
                    else
                        tab[a][b].setFill(Color.WHITE);
                }

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}