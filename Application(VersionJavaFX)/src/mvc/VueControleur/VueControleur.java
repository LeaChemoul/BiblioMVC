package mvc.VueControleur;


import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.scene.Scene;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import mvc.Model.Direction;
import mvc.Model.Plateau;

public class VueControleur extends Application{

    private int largeur = 10;
    private int hauteur = 20;

    @Override
    public void start(Stage primaryStage) {
        
        // initialisation du modèle que l'on souhaite utiliser
        Plateau p = new Plateau(largeur, hauteur);
        
        // gestion du placement (permet de placer le champ Text affichage en haut, et GridPane gPane au centre)
        BorderPane border = new BorderPane();
        
        // permet de placer les diffrents boutons dans une grille
        GridPane gPane = new GridPane();

        Rectangle[][] tab = new Rectangle[largeur][hauteur];
        // la vue observe les "update" du modèle, et réalise les mises à jour graphiques
        p.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {

                //System.out.println("test");

                for(int a = 0; a< largeur; a++)
                    for(int b = 0; b< hauteur; b++){
                        tab[a][b].setFill(p.getTableauJeu()[a][b].getCouleur());
                    }
            }
        });

        // création des boutons et placement dans la grille
        for(int i = 0; i< largeur; i++)
            for(int j = 0; j< hauteur; j++){
                tab[i][j] = new Rectangle();
                tab[i][j].setHeight(30);
                tab[i][j].setWidth(30);
                tab[i][j].setFill(Color.TRANSPARENT);
                gPane.add(tab[i][j],i, j);

                final int ii = i;
                final int jj = j;

                tab[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(p.getPieceCourante() == null)
                            //p.newPiece();
                            p.click(ii, jj);
                    }
                });

                tab[i][j].setOnKeyPressed(new EventHandler<KeyEvent>(){
                    @Override
                    public void handle(KeyEvent ke){
                        System.out.println("Pressed: " + ke.getCode());
                        if (ke.getCode().equals(KeyCode.LEFT)) {
                            //bouger à gauche la pièce courante du plateau si possible
                        }
                        if (ke.getCode().equals(KeyCode.RIGHT)) {
                            //bouger à droite la pièce courante du plateau si possible
                        }
                        if (ke.getCode().equals(KeyCode.DOWN)) {
                            //descendre la pièce courante du plateau si possible
                        }
                    }
                });

            }


        gPane.setGridLinesVisible(true);
        border.setCenter(gPane);
        Scene scene = new Scene(border, Color.LIGHTBLUE);
        
        primaryStage.setTitle("Jeu Plateau");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public EventHandler<KeyEvent> test = new EventHandler<KeyEvent>(){

        @Override
        public void handle(KeyEvent event) {
            System.out.println("Pressed: " + event.getCode());
        }
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
