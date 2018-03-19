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

import java.util.Observable;
import java.util.Observer;


public class VueControleur extends Application implements Observer {


    private final int NB_JOUEURS = 2;

    private Plateau plateau = new Plateau(20, 20);
    private int joueurActif = 1;
    private Partie partie = new Partie(plateau, 2);

    private boolean pieceEnSurvol;

    private Piece pieceCourante = null;
    private BorderPane bPane = new BorderPane();
    private GridPane grilleJeu;
    private ListePiece tileP;

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
        tileP = new ListePiece(partie.getJoueurActif(), partie);

        //tileP.setAlignment(Pos.TOP_RIGHT);
        //bPane.setAlignment(tileP, Pos.BOTTOM_RIGHT);
        bPane.setMargin(tileP, new Insets(20, 0, 0, 10));
        bPane.setRight(tileP);


        // ------------- CENTER -- Plateau de Jeu
        grilleJeu = new GridPane();
        tab = new Rectangle[plateau.getHauteur()][plateau.getLargeur()];
        for(int i = 0; i < plateau.getHauteur(); i++)
            for(int j = 0; j < plateau.getLargeur(); j++){
                Rectangle rect = new Rectangle();
                rect.setHeight(30);
                rect.setWidth(30);
                rect.setFill(Color.WHITE);
                tab[i][j] = rect;
                grilleJeu.add(tab[i][j], j, i);
                //CONTROLEURS
                // Lors d'un click de souris sur une case du plateau
                rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        //Si on a pas de pièce courante avec laquelle interagir on ne fait rien.
                        if (plateau.getPieceCourante() == null) return;

                        MouseButton button = event.getButton();
                        //Si c'est un click gauche, on pose la pièce
                        if( button == MouseButton.PRIMARY ) {
                            //On pose la pièce courante.
                            partie.jouerPiece(plateau.getPieceCourante(), grilleJeu.getRowIndex(rect), grilleJeu.getColumnIndex(rect));
                            effacerPieceSurvol(grilleJeu.getRowIndex(rect), grilleJeu.getColumnIndex(rect));
                            pieceEnSurvol = false;
                        }
                        //Si c'est un click droit, on la tourne
                        else if( button == MouseButton.SECONDARY ) {
                            if ( plateau.getPieceCourante() != null ) {
                                plateau.getPieceCourante().rotation(Direction.RIGHT);
                                effacerPieceSurvol(grilleJeu.getRowIndex(rect), grilleJeu.getColumnIndex(rect));
                                afficherPieceSurvol(plateau.getPieceCourante(), grilleJeu.getRowIndex(rect), grilleJeu.getColumnIndex(rect));
                            }
                        }

                    }
                });
                // Si on survole une case avec une pièce active, ça nous montre où elle serait posée.
                rect.setOnMouseEntered( event -> {
                    //System.out.println("RowIndex = " +grilleJeu.getRowIndex(rect) +" ColIndex = " + grilleJeu.getColumnIndex(rect));
                    if (plateau.getPieceCourante() != null)
                        afficherPieceSurvol(plateau.getPieceCourante(), grilleJeu.getRowIndex(rect), grilleJeu.getColumnIndex(rect));
                });
                // Si on ne survole plus une case, on efface la pièce qui y était affiché en survole
                rect.setOnMouseExited( event -> {
                    effacerPieceSurvol(grilleJeu.getRowIndex(rect), grilleJeu.getColumnIndex(rect));
                });

            }
        grilleJeu.setGridLinesVisible(true);
        grilleJeu.setPadding(new Insets(10, 0, 10, 20));

        //bPane.setAlignment(grilleJeu, Pos.CENTER_RIGHT);
        bPane.setCenter(grilleJeu);


        //LEFT -- Liste des Joueurs.

        //region Liste Joueurs
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
        //endregion

        //SCENE


        Scene scene = new Scene(bPane);

        primaryStage.setTitle("BLOKUS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void effacerPieceSurvol(int row, int col) {

        //Si il n'y a pas de pièce à effacer on n'efface rien.
        if ( !pieceEnSurvol || partie.getPieceCourante() == null )
            return;

        for (int i = row-partie.getPieceCourante().getHauteur(); i < row+partie.getPieceCourante().getHauteur(); i++) {
            for (int j = col-partie.getPieceCourante().getLargeur(); j < col+partie.getPieceCourante().getLargeur(); j++) {

                //Si la case visée ne dépasse pas les limites du plateau
                if ( inBound(i, j, plateau.getHauteur(), plateau.getLargeur()) ) {

                    //On rétablit les couleurs d'origines du plateau.
                    if (plateau.getTableauJeu()[i][j] != null)
                        tab[i][j].setFill(plateau.getTableauJeu()[i][j].getCouleur());
                    else
                        tab[i][j].setFill(Color.WHITE);
                }
            }
        }
        pieceEnSurvol = false;

    }

    public void afficherPieceSurvol(Piece piece, int row, int col) {

        if ( piece == null )
            return;

        int[][] croppedPiece = piece.croppedPiece();

        //On modifie la vue pour afficher la pièce par dessus le point de la grille.
        for (int i = 0; i < croppedPiece.length; i++ ) {
            for (int j = 0; j < croppedPiece[0].length; j++) {

                //Si la case visée ne dépasse pas les limites du plateau
                if (inBound(row+i, col+j, plateau.getHauteur(), plateau.getLargeur()) ) {
                    //On colorie par dessus le plateau avec la couleur de la piece qu'on veut visualiser.
                    if (croppedPiece[i][j] != 0)
                        tab[row+i][col+j].setFill(piece.getCouleur());
                }
            }
        }
        pieceEnSurvol = true;

    }

    public boolean inBound(int i, int j, int iMax, int jMax) {
        return ( i >= 0 && j >= 0 && i < iMax && j < jMax);
    }

    @Override
    public void update(Observable o, Object arg) {

        pieceCourante = plateau.getPieceCourante();

        if (o instanceof Partie) {
            if (arg instanceof Piece) {
                tileP.supprimerPiece( (Piece)arg );
                tileP.update();
            }
        }
        else if (o instanceof Plateau) {


            //On rafraichit la grille.
            //On modifie la vue pour afficher la pièce par dessus le point de la grille.
            for (int i = 0; i < plateau.getHauteur(); i++ ) {
                for (int j = 0; j < plateau.getLargeur(); j++) {

                    if(plateau.getTableauJeu()[i][j] != null)
                        tab[i][j].setFill(plateau.getTableauJeu()[i][j].getCouleur());
                    else
                        tab[i][j].setFill(Color.WHITE);

                }
            }


        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}