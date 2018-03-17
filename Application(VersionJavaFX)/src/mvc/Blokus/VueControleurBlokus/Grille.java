package mvc.Blokus.VueControleurBlokus;



import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.event.Event;
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

public class Grille extends GridPane{

    private int largeur;
    private int hauteur;
    private Rectangle[][] tab;

    public Grille(int[][] grille, Color couleur, boolean visibleLine) {

        this.largeur = grille[0].length;
        this.hauteur = grille.length;

        tab = new Rectangle[hauteur][largeur];

        // création des boutons et placement dans la grille
        for(int i = 0; i< hauteur; i++)
            for(int j = 0; j< largeur; j++){
                tab[i][j] = new Rectangle();
                tab[i][j].setHeight(15);
                tab[i][j].setWidth(15);

                if ( grille[i][j] == 0)
                    tab[i][j].setFill(Color.WHITE);
                else
                    tab[i][j].setFill(couleur);

                this.add(tab[i][j], i, j);
            }

        //CENTER
        this.setGridLinesVisible(visibleLine);

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