package mvc.VueControleur;


import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mvc.Model.Plateau;

import javafx.scene.text.Text;

public class VuePrincipale extends BorderPane{

    private int largeur;
    private int hauteur;
    private Plateau p;
    private Rectangle[][] tab;
    private MediaPlayer mediaPlayer;
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


        //SON
        String path = new File(System.getProperty("user.dir") + "/sound/mouvement.wav").getAbsolutePath();
        Media media = new Media(new File(path).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);

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

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
