package VueControleur;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GrillePiece extends GridPane{

    private int largeur;
    private int hauteur;
    private Rectangle[][] tab;

    public GrillePiece(int[][] grille, Color couleur, boolean visibleLine, int size) {

        this.hauteur = grille.length;
        this.largeur = grille[0].length;

        tab = new Rectangle[hauteur][largeur];

        // création des boutons et placement dans la grille
        for(int i = 0; i < hauteur; i++)
            for(int j = 0; j < largeur; j++){
                tab[i][j] = new Rectangle();
                tab[i][j].setHeight(size);
                tab[i][j].setWidth(size);

                if ( grille[i][j] == 0)
                    tab[i][j].setFill(Color.WHITE);
                else
                    tab[i][j].setFill(couleur);

                this.add(tab[i][j], j, i);
            }

        //CENTER
        this.setGridLinesVisible(visibleLine);

    }

}