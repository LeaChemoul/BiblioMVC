package mvc.RushHour.VueControleur;

import com.sun.javafx.geom.Vec2d;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mvc.RushHour.Modele.Partie;
import mvc.VueControleur.GrilleVue;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class VueControleurRH extends Application implements Observer {

    private Partie partie;
    private MediaPlayer mediaPlayer;
    GrilleVue grille = new GrilleVue(6,6,50);

    @Override
    public void start(Stage primaryStage){
        //TOP : Titre
        Text titre = new Text("-- Rush Hour --");
        titre.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        titre.setFill(Color.MEDIUMPURPLE);
        BorderPane.setAlignment(titre, Pos.CENTER);
        grille.setTop(titre);
        partie = new Partie(grille.getP());

        //---------------------------------------------------------------------
        //LEFT : regles du jeu
        Label reglesText = new Label("Le but du jeu est d'extraire la \n" +
                "voiture rouge de la grille en \n" +
                "la faisant atteindre la sortie \n" +
                "indiquée à droite.");
        reglesText.setStyle("-fx-font: 13 arial; -fx-base: #b6e7c9;");
        reglesText.setPadding(new Insets(25,10,0,0));
        grille.setLeft(reglesText);

        //---------------------------------------------------------------------
        //RIGHT : Bouton de départ et pour rejouer
        Button startButton = new Button();
        startButton.setPadding(new Insets(10));
        startButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        startButton.setCursor(Cursor.HAND);
        startButton.setText("Commencer");

        Button replayButton = new Button();
        replayButton.setPadding(new Insets(10));
        replayButton.setCursor(Cursor.HAND);
        replayButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        replayButton.setText("Rejouer");

        Label sortieText = new Label("<== Sortie");
        sortieText.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        sortieText.setPadding(new Insets(25,10,0,0));

        GridPane rightPane = new GridPane();
        rightPane.add(startButton,0,0);
        rightPane.add(replayButton,0,1);
        rightPane.add(sortieText,0,2);
        grille.setRight(rightPane);
        grille.setPadding(new Insets(20));

        //---------------------------------------------------------------------
        //EVENEMENTS
        //---------------------------------------------------------------------

        //---------------------------------------------------------------------
        //Bouton commencer
        replayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                partie.getPlateau().reinitialiser();
                partie.initialiser();
                grille.getP().setPieceCourante(null);
            }
        });

        //---------------------------------------------------------------------
        //Bouton rejouer
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                partie.getPlateau();
                partie.initialiser();
                grille.getP().setPieceCourante(null);
            }
        });

        //---------------------------------------------------------------------
        //A la fermeture de la fenêtre
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        //La vue observe les "update" du modèle, et réalise les mises à jour graphiques
        partie.getPlateau().addObserver(this);

        Scene scene = new Scene(grille, Color.WHITE);

        //---------------------------------------------------------------------
        //Evenements liés au clique sur les cases
        initialiserCases(scene);


        //EVENEMENTS LIES AUX TOUCHES CLAVIER
        initialiserEvenementsClavier(scene);

        //SON
        String path = new File(System.getProperty("user.dir") + "/sound/mouvement.wav").getAbsolutePath();
        Media media = new Media(new File(path).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);


        primaryStage.setTitle("Jeu Plateau");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    /**
     * Gestion des evenements claviers liés aux flêches de déplacement.
     * @param scene scene sur laquelle on souhaite appliquer les evenements
     */
    private void initialiserEvenementsClavier(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke){
                if (!partie.isEstFinie() && ke.getCode().equals(KeyCode.UP) && !grille.getP().getPieceCourante().isHorizontal()) {
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().versHaut(grille.getP().getPieceCourante());
                }
                if (!partie.isEstFinie() && ke.getCode().equals(KeyCode.LEFT) && grille.getP().getPieceCourante().isHorizontal()) {
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().versGauche(grille.getP().getPieceCourante());
                    //bouger à gauche la pièce courante du plateau si possible
                }

                if (!partie.isEstFinie() && ke.getCode().equals(KeyCode.RIGHT) && grille.getP().getPieceCourante().isHorizontal()) {
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().versDroite(grille.getP().getPieceCourante());
                    //bouger à droite la pièce courante du plateau si possible
                }
                if (!partie.isEstFinie() && ke.getCode().equals(KeyCode.DOWN) && !grille.getP().getPieceCourante().isHorizontal()) {
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().versBas(grille.getP().getPieceCourante());
                }
            }
        });
    }

    /**
     * Création de l'evenement en réponse au clique sur une case
     */
    private void initialiserCases(Scene scene) {
        for (int i = 0; i < grille.getLargeur(); i++) {
            for (int j = 0; j < grille.getHauteur() ; j++) {
                Rectangle[][] tab = grille.getTab();
                int finalI = i;
                int finalJ = j;
                tab[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        setCurrentPiece(finalJ, finalI, tab);
                    }
                });

                tab[i][j].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        setCurrentPiece(finalJ, finalI, tab);
                        if(grille.getP().getTableauJeu()[finalJ][finalI] != null)
                            scene.setCursor(Cursor.OPEN_HAND);
                        else
                            scene.setCursor(Cursor.DEFAULT);
                    }
                });
                tab[i][j].setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Rectangle cell = (Rectangle) mouseEvent.getSource();
                        cell.startFullDrag();
                        scene.setCursor(Cursor.CLOSED_HAND);
                    }
                });

                tab[i][j].setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
                    @Override
                    public void handle(MouseDragEvent mouseDragEvent) {
                        //poser la pièce a l'endroit
                        bougerPiece(finalJ, finalI);
                        scene.setCursor(Cursor.CLOSED_HAND);
                    }
                });

                tab[i][j].setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
                    @Override
                    public void handle(MouseDragEvent mouseDragEvent) {
                        //poser la pièce a l'endroit
                        bougerPiece(finalJ, finalI);
                        scene.setCursor(Cursor.DEFAULT);

                        mediaPlayer.stop();
                        mediaPlayer.play();
                    }
                });
            }
        }
    }

    private void setCurrentPiece(int finalJ, int finalI, Rectangle[][] tab) {
        ArrayList<Vec2d> positions = grille.getP().occurrencesPiecesPlateau( grille.getP().recupererPiece(finalJ,finalI));
        effacerBordures();
        for (Vec2d position : positions) {
            grille.getP().recupererPiece(finalJ, finalI).setBordure(Color.BLUE);
            tab[(int) position.y][(int) position.x].setStroke(Color.BLUE);
        }
        grille.getP().setPieceCourante(grille.getP().recupererPiece(finalJ,finalI));
    }

    /**
     * Déplacement de la pièce vers la case concernée.
     * @param finalJ la ligne du déplacement
     * @param finalI la colonne du déplacement
     */
    private void bougerPiece(int finalJ, int finalI) {
        if(!partie.isEstFinie() && grille.getP().getPieceCourante()!=null){
            ArrayList<Vec2d> positionsAvant = grille.getP().occurrencesPiecesPlateau(grille.getP().getPieceCourante());

            //Premier if : Si la pièce est horizontale et que la case vers laquelle on souhaite
            // la déplacer se situe sur la même ligne mais une colonne différente.
            if(positionsAvant.get(0).y <finalI && grille.getP().getPieceCourante().isHorizontal())
                grille.getP().versDroite(grille.getP().getPieceCourante());
            else if(positionsAvant.get(0).y > finalI && grille.getP().getPieceCourante().isHorizontal())
                grille.getP().versGauche(grille.getP().getPieceCourante());
            else if(positionsAvant.get(0).x < finalJ && !grille.getP().getPieceCourante().isHorizontal())
                grille.getP().versBas(grille.getP().getPieceCourante());
            else if(positionsAvant.get(0).x > finalJ && !grille.getP().getPieceCourante().isHorizontal())
                grille.getP().versHaut(grille.getP().getPieceCourante());
        }
    }

    /**
     * Fonction appellée après le notify observer.
     * Met à jour la vue.
     * @param o L'element observable, ici notre plateau de jeu
     * @param arg
     */
    @Override
    synchronized public void update(Observable o, Object arg) {
                effacerBordures();
                for(int a = 0; a< grille.getP().getLargeur(); a++)
                    for(int b = 0; b< grille.getP().getHauteur(); b++){

                        //On met à jour les couleurs des cases du plateau (decrivant les pièces presentes)
                        if(grille.getP().getTableauJeu()[b][a] != null){
                            grille.getTab()[a][b].setFill(grille.getP().getTableauJeu()[b][a].getCouleur());
                            grille.getTab()[a][b].setStroke(grille.getP().recupererPiece(b,a).getBordure());
                        }
                        else{
                            grille.getTab()[a][b].setFill(Color.WHITE);
                            grille.getTab()[a][b].setStroke(Color.TRANSPARENT);
                        }
                        partie.partieFinie();
                    }
            }

    /**
     * Efface les bordures des pièces qui ne sont pas al pière courante selectionnée par le joueur.
     */
    private void effacerBordures(){
        for (int i = 0; i < grille.getHauteur(); i++) {
            for (int j = 0; j < grille.getLargeur(); j++) {
                grille.getTab()[i][j].setStroke(Color.TRANSPARENT);
                if(grille.getP().recupererPiece(i,j) != null && grille.getP().recupererPiece(i,j)!=grille.getP().getPieceCourante())
                    grille.getP().recupererPiece(i,j).setBordure(Color.TRANSPARENT);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
