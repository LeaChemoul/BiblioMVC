package mvc.VueControleur;


import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mvc.Model.Plateau;

import javafx.scene.text.Text;

public class GrilleVue extends BorderPane{

    private int largeur;
    private int hauteur;
    private Plateau p;
    private Rectangle[][] tab;

    public GrilleVue(int a, int b) {
        this.largeur = a;
        this.hauteur = b;
        
        // initialisation du modèle que l'on souhaite utiliser
        p = new Plateau(hauteur, largeur);

        // permet de placer les differents boutons dans une grille
        GridPane gPane = new GridPane();

        tab = new Rectangle[largeur][hauteur];

        // création des boutons et placement dans la grille
        for(int i = 0; i< largeur; i++)
            for(int j = 0; j< hauteur; j++){
                tab[i][j] = new Rectangle();
                tab[i][j].setHeight(40);
                tab[i][j].setWidth(40);
                tab[i][j].setFill(Color.WHITE);
                gPane.add(tab[i][j],i, j);
            }

        //CENTER
        gPane.setGridLinesVisible(false);
        this.setCenter(gPane);
        this.setPadding(new Insets(10, 20, 10, 20));

    }

    public Plateau getP() {
        return p;
    }

    public Rectangle[][] getTab() {
        return tab;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }
}
