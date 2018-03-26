package mvc.VueControleur;


import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mvc.Model.Plateau;

public class VuePrincipale extends BorderPane{

    private int largeur;
    private int hauteur;
    private Plateau p;
    private Rectangle[][] tab;
    private GridPane gridP;

    public VuePrincipale(int a, int b, int rectSize , boolean reversed) {
        this.largeur = a;
        this.hauteur = b;
        
        // initialisation du modèle que l'on souhaite utiliser
        p = new Plateau(hauteur, largeur);

        // permet de placer les differents boutons dans une grille
        gridP = new GridPane();

        //TODO : FIX L'INVERSION LARGEUR HAUTEUR
        if (reversed)
            tab = new Rectangle[largeur][hauteur];
        else
            tab = new Rectangle[hauteur][largeur];


        // création des boutons et placement dans la grille
        for(int i = 0; i < largeur; i++)
            for(int j = 0; j < hauteur; j++){
                tab[i][j] = new Rectangle();
                tab[i][j].setHeight(rectSize);
                tab[i][j].setWidth(rectSize);
                tab[i][j].setFill(Color.WHITE);
                if (reversed)
                    gridP.add(tab[i][j], i, j);
                else
                    gridP.add(tab[i][j], j, i);

            }

        //CENTER
        gridP.setGridLinesVisible(true);
        this.setCenter(gridP);
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

    public GridPane getGridP() {
        return gridP;
    }
}
