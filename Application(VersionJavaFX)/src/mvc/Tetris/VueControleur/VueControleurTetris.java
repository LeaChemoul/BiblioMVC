package mvc.Tetris.VueControleur;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mvc.Model.Direction;
import mvc.Tetris.Modele.Partie;
import mvc.VueControleur.GrilleVue;

import java.util.Observable;
import java.util.Observer;


public class VueControleurTetris extends Application implements Observer {

    private Partie partie;
    GrilleVue grille = new GrilleVue(10,20);

    @Override
    public void start(Stage primaryStage){
        //TOP
        Text titre = new Text("Le super TETRIS");
        grille.setTop(titre);
        partie = new Partie(grille.getP());

        //RIGHT
        Button startButton = new Button();
        startButton.setText("Commencer");
        grille.setRight(startButton);
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                partie.deroulement();
            }
        });

        // la vue observe les "update" du modèle, et réalise les mises à jour graphiques
        partie.getPlateau().addObserver(this);

        Scene scene = new Scene(grille, Color.WHITE);

        for (int i = 0; i < grille.getLargeur(); i++) {
            for (int j = 0; j < grille.getHauteur() ; j++) {
                Rectangle[][] tab = grille.getTab();
                tab[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //dans le tetris rien à faire
                        //dans le blockus recupération de la case et placement d'une pièce
                        //dans le puzzle recupération de la case et déplacement d'une pièce
                    }
                });
            }
        }

        //EVENEMENTS LIES AUX TOUCHES CLAVIER
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke){
                if (ke.getCode().equals(KeyCode.UP)) {
                    //rotation
                    /*
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().tournerPieceCourante(Direction.RIGHT);
                    */

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

        primaryStage.setTitle("Jeu Plateau");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
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
            }
        });
    }
}
