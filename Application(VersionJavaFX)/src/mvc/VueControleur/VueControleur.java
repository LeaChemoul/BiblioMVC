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
import mvc.Model.Plateau;

/**
 *
 * @author freder
 */
public class VueControleur extends Application{

    private int hauteur = 10;
    private int largeur = 20;

    @Override
    public void start(Stage primaryStage) {
        
        // initialisation du modèle que l'on souhaite utiliser
        Plateau p = new Plateau(hauteur,largeur);
        
        // gestion du placement (permet de palcer le champ Text affichage en haut, et GridPane gPane au centre)
        BorderPane border = new BorderPane();
        
        // permet de placer les diffrents boutons dans une grille
        GridPane gPane = new GridPane();

        Rectangle[][] tab = new Rectangle[hauteur][largeur];
        // la vue observe les "update" du modèle, et réalise les mises à jour graphiques
        p.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                for(int a = 0; a<hauteur; a++)
                    for(int b = 0; b<largeur; b++){
                        switch(p.getTest()[a][b]){
                            case 1 :
                                tab[a][b].setFill(Color.CHOCOLATE);
                                //changer l'accès au plateau test
                                //mettre le vrai palteau plus tard
                                //et tester dans le switch les vrais couleurs
                        }
                    }
            }
        });

        // création des boutons et placement dans la grille
        for(int i=0;i<hauteur;i++)
            for(int j=0;j<largeur;j++){
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



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
