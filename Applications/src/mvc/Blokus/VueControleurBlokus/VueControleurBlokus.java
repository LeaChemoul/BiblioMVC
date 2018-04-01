package mvc.Blokus.VueControleurBlokus;

import Model.Direction;
import Model.Piece;
import Model.Plateau;
import VueControleur.PopupFinPartie;
import VueControleur.VuePrincipale;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mvc.Blokus.ModeleBlokus.JoueurBlokus;
import mvc.Blokus.ModeleBlokus.Partie;

import java.util.Observable;
import java.util.Observer;


public class VueControleurBlokus extends Application implements Observer {


    private Plateau plateau = new Plateau(20, 20);
    private Partie partie = new Partie(plateau, 4);


    private VuePrincipale vueP = new VuePrincipale(20, 20, 30, false);

    private ListePiece[] listesPiecesJoueurs = new ListePiece[5];
    private ListeJoueur listeJoueurs;
    private PopupFinPartie popupFinPartie;



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

        //------------------------------------------
        //----- POPUP VICTOIRE
        popupFinPartie = new PopupFinPartie(primaryStage);


        //-----------------------------------------
        //Préparation de la scene de la grille de jeu.
        //-----------------------------------------

        //---------------------------------------------------------------------
        // ------------ TOP -- Le Titre

        //region titre
        Text titre = new Text("--- BLOKUS ---");
        titre.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        titre.setFill(Color.MEDIUMPURPLE);
        vueP.setAlignment(titre, Pos.CENTER);
        vueP.setTop(titre);
        //endregion

        //---------------------------------------------------------------------
        //------------- RIGHT -- Liste des pièces du joueur actif

        //region Liste de Pièces

        //On initialise les liste de pièces de tout les joueurs
        for (int i = 0; i < 4; i++) {
            listesPiecesJoueurs[i] = new ListePiece( partie.getJoueur(i), partie);
            vueP.setMargin(listesPiecesJoueurs[i], new Insets(20));
            listesPiecesJoueurs[i].setAlignment(Pos.TOP_LEFT);
        }
        /* On l'ajoute pas encore à bPane, on attend le choix du nombre de joueur,
         * on l'ajoutera via un controlleur.
         */

        //endregion

        //---------------------------------------------------------------------
        //------------- LEFT -- Choix Nb Joueur & Liste des Joueurs.

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
                partie.setNbJoueurs(nb);
                partie.setNbJoueursRestant(nb);
                listeJoueurs = new ListeJoueur(partie, nb);
                vueP.setLeft(listeJoueurs);
                vueP.setRight(listesPiecesJoueurs[0]);

                colorierCoins();
            });
            btChoix.setPadding(new Insets(10));
            vbox.setMargin(btChoix, new Insets(10, 5, 5, 40));
            vbox.getChildren().add(btChoix);
        }

        //Pour éviter d'avoir un changement de taille de la région lorsqu'on la changera avec la liste de joueurs.
        vbox.setMinWidth(200);

        vueP.setLeft(vbox);
        //endregion

        //---------------------------------------------------------------------
        // ------------ CENTER -- Plateau de Jeu

        //region Plateau Jeu

        //Le centre est déjà initialisé quand on crée VuePrincipale !

        vueP.getGridP().setGridLinesVisible(true);
        vueP.getGridP().setPadding(new Insets(10, 0, 10, 20));

        //On initialise les controleurs.
        initialiserCases();

        //endregion

        //---------------------------------------------------------------------
        // ------------ BOTTOM -- Bouton abandon
        Button btAbandon = new Button("Abandonner");
        btAbandon.setPadding(new Insets(10));
        btAbandon.setOnMouseClicked( event -> {
            if (partie.abandonner(partie.getJoueurActif()))
                listeJoueurs.setAbandon(partie.getNumJoueurActif());
            System.out.println("Joueur " + partie.getNumJoueurActif() + " a abandonné !");

            partie.supprimerPieceCourante();

            //Si il y a un gagnant, message de victoire
            JoueurBlokus joueurGagnant = partie.joueurGagnant();
            if (joueurGagnant != null) {
                popupFinPartie.setTextPopup("Le joueur " + joueurGagnant.getNumJoueur() + " a gagné !");
                popupFinPartie.afficherPopup();
            }

            else //On passe au joueur suivant.
                partie.joueurSuivant();

        });

        vueP.setMargin(btAbandon, new Insets(5));
        vueP.setAlignment(btAbandon, Pos.CENTER);
        vueP.setPadding(new Insets(0));
        vueP.setBottom(btAbandon);

        //Résout le problème de gap entre le plateau de jeu et les listes de pièces.
        vueP.setMinSize(1100, 700);



        //---------------------------------------------------------------------
        //----- SCENE

        Scene scene = new Scene(vueP, 1100, 700);
        scene.setOnScroll( event -> {
        if ( plateau.getPieceCourante() != null )
            plateau.getPieceCourante().rotation(Direction.RIGHT);
        });

        primaryStage.setOnCloseRequest(e -> Platform.exit());
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

        Piece pieceCourante = plateau.getPieceCourante();

        //Si il n'y a pas de pièce à effacer on n'efface rien.
        if ( pieceCourante == null )
            return;

        //On agit seulement sur les cases qui font la taille de la pièce, pour éviter trop de traitement mémoire.
        for (int i = row; i < row + pieceCourante.getHauteur(); i++) {
            for (int j = col; j < col + pieceCourante.getLargeur(); j++) {

                //Si la case visée ne dépasse pas les limites du plateau
                if ( inBound(i, j, plateau.getHauteur(), plateau.getLargeur()) )
                {
                    //On rétablit les couleurs d'origines du plateau.
                    if (plateau.getTableauJeu()[i][j] != null)
                        vueP.getTab()[i][j].setFill(plateau.getTableauJeu()[i][j].getCouleur());
                    else
                        vueP.getTab()[i][j].setFill(Color.WHITE);
                }

            }
        }

        colorierCoins();

    }

    /**
     * Affiche la piece courante par dessus le plateau à partir de la case donnée, dans une couleur différente pour la différencier.
     * @param row num lig de la case à partir d'où l'afficher
     * @param col num col de la case à partir d'où l'afficher
     */
    public void afficherPieceSurvol(int row, int col) {

        Piece pieceCourante = plateau.getPieceCourante();

        if ( pieceCourante == null )
            return;

        int[][] croppedPiece = pieceCourante.croppedPiece();

        //On modifie la vue pour afficher la pièce par dessus le point de la grille.
        for (int i = 0; i < croppedPiece.length; i++ ) {
            for (int j = 0; j < croppedPiece[0].length; j++) {

                //Si la case visée ne dépasse pas les limites du plateau
                if (inBound(row+i, col+j, plateau.getHauteur(), plateau.getLargeur()) ) {
                    //On colorie par dessus le plateau avec la couleur de la piece qu'on veut visualiser.
                    if (croppedPiece[i][j] != 0)
                        vueP.getTab()[row+i][col+j].setFill(pieceCourante.getCouleur());
                }
            }
        }

    }

    /**
     * Colorie les coins du plateau avec les couleurs des joueurs, pour indiquer dans quel coin chacun commence.
     */
    public void colorierCoins() {
        //On colorie les coins en fonction du nombre de joueurs
        for (int i = 0; i < partie.getNbJoueurs(); i++) {
            JoueurBlokus joueur = partie.getJoueur(i);
            vueP.getTab()[ joueur.getCoinDepartX() ] [joueur.getCoinDepartY() ].setFill(joueur.getCouleur());
        }
    }

    public boolean inBound(int i, int j, int iMax, int jMax) {
        return ( i >= 0 && j >= 0 && i < iMax && j < jMax);
    }

    /**
     * Création de l'evenement en réponse au clique sur une case
     */
    private void initialiserCases() {
        for (int i = 0; i < vueP.getHauteur(); i++) {
            for (int j = 0; j < vueP.getLargeur() ; j++) {
                Rectangle[][] tab = vueP.getTab();
                int finalI = i;
                int finalJ = j;
                tab[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {

                        //Si on a pas de pièce courante avec laquelle interagir on ne fait rien.
                        if (plateau.getPieceCourante() == null) return;

                        MouseButton button = event.getButton();
                        //Si c'est un click gauche, on pose la pièce
                        if( button == MouseButton.PRIMARY ) {

                            //On pose la pièce courante, si on réussi :
                            if ( partie.jouerPiece(plateau.getPieceCourante(), finalI, finalJ) ) {
                                effacerPieceSurvol(finalI, finalJ);

                                //Si il y a un gagnant, message de victoire
                                JoueurBlokus joueurGagnant = partie.joueurGagnant();
                                if ( joueurGagnant != null ) {
                                    popupFinPartie.setTextPopup("Le joueur " + joueurGagnant.getNumJoueur() + " a gagné !");
                                    popupFinPartie.afficherPopup();
                                }
                                else //On passe au joueur suivant.
                                    partie.joueurSuivant();
                            }

                        }

                        //Si c'est un click droit, on la tourne
                        else if( button == MouseButton.SECONDARY ) {
                            if ( plateau.getPieceCourante() != null ) {
                                plateau.getPieceCourante().rotation(Direction.RIGHT);
                                effacerPieceSurvol(finalI, finalJ);
                                afficherPieceSurvol(finalI, finalJ);
                            }
                        }

                    }
                });

                // Si on survole une case avec une pièce active, ça nous montre où elle serait posée.
                tab[i][j].setOnMouseEntered( event -> {
                    //System.out.println("RowIndex = " +grilleJeu.getRowIndex(rect) +" ColIndex = " + grilleJeu.getColumnIndex(rect));
                    if (plateau.getPieceCourante() != null)
                        afficherPieceSurvol(finalI, finalJ);
                });

                tab[i][j].setOnMouseExited( event -> {
                    effacerPieceSurvol(finalI, finalJ);
                });
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {


        if (o instanceof Partie) {

            int numJoueurActif = partie.getNumJoueurActif();

            //listesPiecesJoueurs[numJoueurActif-1].supprimerPiece( (Piece)arg );
            listesPiecesJoueurs[numJoueurActif-1].update();

            //On met à jour la liste des joueurs à gauche.
            listeJoueurs.update( numJoueurActif );

            //On met à jour/change la liste de pièce affiché pour celle du nouveau joueur actif
            vueP.setRight(listesPiecesJoueurs[numJoueurActif-1]);

        }
        //Si on reçoit le plateau, on rafraichit toute la grille de jeu.
        else if (o instanceof Plateau) {


            //On rafraichit la grille.
            for (int i = 0; i < plateau.getHauteur(); i++ ) {
                for (int j = 0; j < plateau.getLargeur(); j++) {

                    //Case non vide
                    if (plateau.getTableauJeu()[i][j] != null)
                        vueP.getTab()[i][j].setFill(plateau.getTableauJeu()[i][j].getCouleur());
                    else //Case vide
                        vueP.getTab()[i][j].setFill(Color.WHITE);

                }
            }

            colorierCoins();


        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}