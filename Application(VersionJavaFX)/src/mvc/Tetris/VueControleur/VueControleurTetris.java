package mvc.Tetris.VueControleur;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mvc.Tetris.Modele.Partie;
import mvc.VueControleur.VueControleur;


public class VueControleurTetris extends Application {

    private Partie partie;

    @Override
    public void start(Stage primaryStage){
        VueControleur grille = new VueControleur(10,20);
        //TOP
        Text titre = new Text("Le super TETRIS");
        grille.setTop(titre);

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


        this.partie = new Partie(grille.getP());

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
                //System.out.println("Pressed: " + ke.getCode());
                //Indisponible au tétris
                /*if (ke.getCode().equals(KeyCode.UP)) {
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().versHaut(grille.getP().getPieceCourante());
                    //bouger à gauche la pièce courante du plateau si possible
                }*/
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
                    //TODO ici test de la descente, automatqiue : synchroniser l'affichage
                    //p.descente(p.getPieceCourante());
                    //descendre la pièce courante du plateau si possible
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
}
