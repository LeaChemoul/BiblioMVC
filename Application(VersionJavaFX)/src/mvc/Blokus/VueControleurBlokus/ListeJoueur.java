package mvc.Blokus.VueControleurBlokus;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import mvc.Blokus.ModeleBlokus.Partie;

public class ListeJoueur extends VBox {


    private Text[] textsJoueurs;
    private Boolean[] joueurAbandon;
    private int nbJoueurs;

    public ListeJoueur(int nbJoueurs) {

        this.nbJoueurs = nbJoueurs;

        textsJoueurs = new Text[nbJoueurs+1];
        joueurAbandon = new Boolean[nbJoueurs];

        textsJoueurs[0] = new Text("Liste des Joueurs");
        textsJoueurs[0].setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        for (int i = 1; i < nbJoueurs +1; i++) {

            joueurAbandon[i-1] = false;
            textsJoueurs[i] = new Text("Joueur " + i );
            //Le nom du joueur actif ( le premier joueur ) est écrit plus gros.
            if ( i == 1 )
                textsJoueurs[i].setFont(Font.font("Helvetica", FontWeight.BOLD,20));
            else
                textsJoueurs[i].setFont(Font.font("Helvetica", FontWeight.BOLD, 15));

            textsJoueurs[i].setFill( Partie.intToColor(i-1) );
        }

        setPadding(new Insets(10));

        setMargin(textsJoueurs[0], new Insets(60, 5, 2, 5));
        getChildren().add(textsJoueurs[0]);
        for (int i = 1; i < nbJoueurs +1; i++) {
            setMargin(textsJoueurs[i], new Insets(5, 5, 5, 40));
            getChildren().add(textsJoueurs[i]);
        }

        setMinWidth(200);
    }

    public void setAbandon(int numJoueur) {
        //On retiens que le joueur a abandonné
        joueurAbandon[numJoueur-1] = true;

        getChildren().removeAll(textsJoueurs);

        //On modifie son texte dans la liste
        textsJoueurs[numJoueur] = new Text("Joueur " + numJoueur + " a abandonné !");
        textsJoueurs[numJoueur].setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        textsJoueurs[numJoueur].setFill( Partie.intToColor(numJoueur-1) );
        setMargin(textsJoueurs[numJoueur], new Insets(5, 5, 5, 40));

        //On rafraichit toute la liste pour garder les noms dans l'ordre.
        getChildren().addAll(textsJoueurs);
    }

    public void update(int numJoueurActif) {

        for (int i = 1; i < nbJoueurs+1; i++) {

            //Si le joueur n'a pas abandonné
            if ( !joueurAbandon[numJoueurActif-1] ) {

                if ( i == numJoueurActif )
                    textsJoueurs[i].setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
                else
                    textsJoueurs[i].setFont(Font.font("Helvetica", FontWeight.BOLD, 15));

            }
        }
    }
}
