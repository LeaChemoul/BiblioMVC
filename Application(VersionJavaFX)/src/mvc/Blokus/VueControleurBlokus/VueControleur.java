package mvc.Blokus.VueControleurBlokus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;

import mvc.Model.*;
import mvc.Blokus.ModeleBlokus.*;

import java.util.Observable;
import java.util.Observer;


public class VueControleur extends Application implements Observer {


    private int NbJoueurs;

    private Plateau plateau = new Plateau(20, 20);
    private Partie partie = new Partie(plateau, 4);

    private boolean pieceEnSurvol;

    private BorderPane bPane = new BorderPane();
    private GridPane grilleJeu;

    private ListePiece[] listesPiecesJoueurs = new ListePiece[5];
    private ListeJoueur listeJoueurs;
    private Text[] textsJoueurs = new Text[4];

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

        //region titre
        Text titre = new Text("--- BLOKUS ---");
        titre.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        titre.setFill(Color.MEDIUMPURPLE);
        bPane.setAlignment(titre, Pos.CENTER);
        bPane.setTop(titre);
        //endregion

        //------------- RIGHT -- Liste des pièces du joueur actif

        //region Liste de Pièces

        //On initialise les liste de pièces de chaque joueurs
        for (int i = 0; i < 4; i++) {
            listesPiecesJoueurs[i] = new ListePiece( partie.getJoueur(i), partie);
            bPane.setMargin(listesPiecesJoueurs[i], new Insets(20));
            listesPiecesJoueurs[i].setAlignment(Pos.TOP_LEFT);
        }
        //On l'ajoute pas encore à la bPane, on attend le choix du nombre de joueur,
        //donc on l'ajoutera via un controlleur.

        //endregion

        //------------- LEFT -- Liste des Joueurs.

        //region Liste Joueurs

        //region choix nb joueurs
        VBox vbox = new VBox();

        Text textChoix = new Text("   Choississez un\nnombre de joueurs :");
        textChoix.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        vbox.setPadding(new Insets(60, 0, 10, 25));
        vbox.getChildren().add(textChoix);

        for (int i = 2; i <= 4; i++) {

            Button btChoix = new Button(i + " Joueurs");
            final int nb = i;
            //Lorsqu'on click sur un bouton, on redéfini le nb de joueurs de partie,
            //et on affiche la liste des joueurs en fct de celui-ci + la liste de pièce du premier joueur.
            btChoix.setOnMouseClicked(event -> {
                NbJoueurs = nb;
                partie.setNbJoueurs(nb);
                listeJoueurs = new ListeJoueur(nb);
                bPane.setLeft(listeJoueurs);
                bPane.setRight(listesPiecesJoueurs[0]);
            });
            btChoix.setPadding(new Insets(10));
            vbox.setMargin(btChoix, new Insets(10, 5, 5, 40));
            vbox.getChildren().add(btChoix);
        }

        //Pour éviter d'avoir un changement de taille de la région lorsqu'on la changera avec la liste de joueurs.
        vbox.setMinWidth(200);

        bPane.setLeft(vbox);
        //endregion

        // ------------ CENTER -- Plateau de Jeu

        //region Plateau Jeu
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
                //CONTROLLEURS
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
                            partie.joueurSuivant();
                            pieceEnSurvol = false;
                        }
                        //Si c'est un click droit, on la tourne
                        else if( button == MouseButton.SECONDARY ) {
                            if ( plateau.getPieceCourante() != null ) {
                                plateau.getPieceCourante().rotation(Direction.RIGHT);
                                effacerPieceSurvol(grilleJeu.getRowIndex(rect), grilleJeu.getColumnIndex(rect));
                                afficherPieceSurvol(grilleJeu.getRowIndex(rect), grilleJeu.getColumnIndex(rect));
                            }
                        }

                    }
                });
                // Si on survole une case avec une pièce active, ça nous montre où elle serait posée.
                rect.setOnMouseEntered( event -> {
                    //System.out.println("RowIndex = " +grilleJeu.getRowIndex(rect) +" ColIndex = " + grilleJeu.getColumnIndex(rect));
                    if (plateau.getPieceCourante() != null)
                        afficherPieceSurvol(grilleJeu.getRowIndex(rect), grilleJeu.getColumnIndex(rect));
                });
                // Si on ne survole plus une case, on efface la pièce qui y était affiché en survole
                rect.setOnMouseExited( event -> {
                    effacerPieceSurvol(grilleJeu.getRowIndex(rect), grilleJeu.getColumnIndex(rect));
                });

            }
        grilleJeu.setGridLinesVisible(true);
        grilleJeu.setPadding(new Insets(10, 0, 10, 20));

        bPane.setCenter(grilleJeu);

        //endregion

        //Résout le problème de gap entre le plateau de jeu et les listes de pièces.
        bPane.setMinSize(1100, 700);

        //SCENE

        Scene scene = new Scene(bPane, 1100, 700);
        scene.setOnScroll(
                event -> plateau.getPieceCourante().rotation(Direction.RIGHT));

        primaryStage.setTitle("BLOKUS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Efface l'affichage en survol de la pièce courante. Basiquement on rafraichit les cases proches avec le contenu du plateau.
     * @param row nb_lig de la pièce à effacer.
     * @param col nb_col de la pièce à effacer.
     */
    public void effacerPieceSurvol(int row, int col) {

        //Si il n'y a pas de pièce à effacer on n'efface rien.
        if ( partie.getPieceCourante() == null )
            return;

        //On agit seulement sur les cases qui font la taille de la pièce, pour éviter trop de traitement mémoire.
        for (int i = row; i < row+partie.getPieceCourante().getHauteur(); i++) {
            for (int j = col; j < col+partie.getPieceCourante().getLargeur(); j++) {

                //Si la case visée ne dépasse pas les limites du plateau
                if ( inBound(i, j, plateau.getHauteur(), plateau.getLargeur()) )
                {
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

    /**
     * Affiche la piece courante par dessus le plateau à partir de la case donnée, dans une couleur différente pour la différencier.
     * @param row num lig de la case à partir d'où l'afficher
     * @param col num col de la case à partir d'où l'afficher
     */
    public void afficherPieceSurvol(int row, int col) {

        if ( plateau.getPieceCourante() == null )
            return;

        int[][] croppedPiece = plateau.getPieceCourante().croppedPiece();

        //On modifie la vue pour afficher la pièce par dessus le point de la grille.
        for (int i = 0; i < croppedPiece.length; i++ ) {
            for (int j = 0; j < croppedPiece[0].length; j++) {

                //Si la case visée ne dépasse pas les limites du plateau
                if (inBound(row+i, col+j, plateau.getHauteur(), plateau.getLargeur()) ) {
                    //On colorie par dessus le plateau avec la couleur de la piece qu'on veut visualiser.
                    if (croppedPiece[i][j] != 0)
                        tab[row+i][col+j].setFill(plateau.getPieceCourante().getCouleur());
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


        if (o instanceof Partie) {

            //Si on reçoit une pièce, on met à jour cette pièce.
            if (arg instanceof Piece) {
                listesPiecesJoueurs[partie.getNumJoueurActif()-1].supprimerPiece( (Piece)arg );
                listesPiecesJoueurs[partie.getNumJoueurActif()-1].update();
            }
            //Si on reçoit un joueur, on mets à jour les listes des joueurs, ça veut aussi dire qu'on change de joueur.
            else if (arg instanceof JoueurBlokus) {

                listeJoueurs.update( partie.getNumJoueurActif() );

                //On met à jour/change la liste de pièce affiché pour celle du nouveau joueur actif
                bPane.setRight(listesPiecesJoueurs[partie.getJoueurActif().getNumJoueur()-1]);

                //On change les couleur de la liste des joueurs.
            }
        }
        //Si on reçoit le plateau, on rafraichit toute la grille de jeu.
        else if (o instanceof Plateau) {


            //On rafraichit la grille.
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