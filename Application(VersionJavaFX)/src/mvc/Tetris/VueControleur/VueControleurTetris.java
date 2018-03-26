package mvc.Tetris.VueControleur;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import mvc.Model.Direction;
import mvc.Tetris.Modele.Partie;
import mvc.VueControleur.GrillePiece;
import mvc.VueControleur.PopupFinPartie;
import mvc.VueControleur.VuePrincipale;

import java.util.Observable;
import java.util.Observer;


public class VueControleurTetris extends Application implements Observer {

    private PopupFinPartie popupFinPartie;
    private Partie partie;
    private VuePrincipale grille = new VuePrincipale(10,20,30, true);
    private Text score;

    @Override
    public void start(Stage primaryStage){

        partie = new Partie(grille.getP());
        // la vue observe les "update" du modèle, et réalise les mises à jour graphiques
        partie.getPlateau().addObserver(this);
        grille.getGridP().setGridLinesVisible(true);
        grille.setMinSize(600,600);

        Scene scene = new Scene(grille, Color.WHITE);

        //----------------------------------------------------------------
        //POPUP FIN DE PARTIE
        //----------------------------------------------------------------

        this.popupFinPartie = new PopupFinPartie(primaryStage);
        popupFinPartie.setTextPopup("GAME OVER !");

        grille.setStyle("-fx-background-color: #2f4f4f;\n" +
                "    -fx-padding: 15;\n" +
                "    -fx-spacing: 10;");

        //----------------------------------------------------------------
        //TOP
        //----------------------------------------------------------------

        Text titre = new Text("--- Tetris ---");
        titre.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        titre.setFill(Color.YELLOW);
        grille.setAlignment(titre, Pos.CENTER);
        grille.setTop(titre);

        //----------------------------------------------------------------
        //RIGHT
        //----------------------------------------------------------------

        Button startButton = new Button();
        DropShadow shadow = new DropShadow();
        startButton.setPadding(new Insets(10));
        startButton.setStyle("-fx-font: 22 helvetica; -fx-base: #b6e7c9;");
        startButton.setText("Commencer");

        grille.setMargin(startButton, new Insets(20, 0, 0, 15));
        grille.setRight(startButton);

        grille.getGridP().setEffect(shadow);
        grille.getGridP().setPadding(new Insets(20));

        grille.setPadding(new Insets(20));
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                grille.getChildren().remove(startButton);
                partie.deroulement();
            }
        });
        startButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                startButton.setEffect(shadow);
            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        //----------------------------------------------------------------
        //EVENEMENTS LIES AUX TOUCHES CLAVIER
        //----------------------------------------------------------------
        evenementsClavier(scene);


        primaryStage.setTitle("TETRIS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void evenementsClavier(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke){
                if (ke.getCode().equals(KeyCode.UP)) {
                    //rotation
                    if(grille.getP().getPieceCourante() != null) {
                        grille.getP().tournerPieceCourante(Direction.RIGHT);
                    }
                }
                if (ke.getCode().equals(KeyCode.LEFT)) {
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().versGauche(grille.getP().getPieceCourante());
                    //bouger à gauche la pièce courante du plateau si possible
                }
                if (ke.getCode().equals(KeyCode.RIGHT)) {
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().versDroite(grille.getP().getPieceCourante());
                    //bouger à droite la pièce courante du plateau si possible
                }
                if (ke.getCode().equals(KeyCode.DOWN)) {
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().versBas(grille.getP().getPieceCourante());
                }
            }
        });
    }

    @Override
    synchronized public void update(Observable o, Object arg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for(int a = 0; a< grille.getP().getLargeur(); a++)
                    for(int b = 0; b< grille.getP().getHauteur(); b++){

                        if(grille.getP().getTableauJeu()[b][a] != null)
                            grille.getTab()[a][b].setFill(grille.getP().getTableauJeu()[b][a].getCouleur());
                        else
                            grille.getTab()[a][b].setFill(Color.WHITE);
                    }
                   if(partie.isEstFinie()){
                       popupFinPartie.afficherPopup();
                   }

                //Mise a jour du score et de la pièce suivante
                miseAJourJeu();

            }
        });
    }

    private void miseAJourJeu() {
        //Mise à jour du score.
        score = new Text("Score : " + Integer.toString(partie.getScore()));
        score.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        score.setFill(Color.WHITE);

        //Mise a jour de la pièce suivante affichée
        GrillePiece grillePiecePieceSuivante = new GrillePiece(partie.getPlateau().getPiecesSuivantes().get(0).getCases(),Color.BLUE,false,30);
        grillePiecePieceSuivante.setPadding(new Insets(15));
        grillePiecePieceSuivante.setMaxSize(20.0,20.0);
        grillePiecePieceSuivante.setPrefWidth(210);

        Text chrono = new Text(partie.getChronometre().getTemps());
        chrono.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
        chrono.setFill(Color.WHITE);

        GridPane rightpane = new GridPane();
        rightpane.add(grillePiecePieceSuivante,0,0);
        rightpane.add(score,0,1);
        rightpane.add(chrono,0,2);

        grille.setRight(rightpane);
        grille.setBottom(null);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
