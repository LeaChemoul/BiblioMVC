package mvc.Blokus.VueControleurBlokus;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import mvc.Blokus.ModeleBlokus.Partie;

public class ListeJoueur extends VBox {


    private Text[] textsJoueurs;
    private int nbJoueurs;

    public ListeJoueur(int nbJoueurs) {

        this.nbJoueurs = nbJoueurs;

        textsJoueurs = new Text[nbJoueurs+1];

        textsJoueurs[0] = new Text("Liste des Joueurs");
        textsJoueurs[0].setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        for (int i = 1; i < nbJoueurs +1; i++) {

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
            getChildren().addAll(textsJoueurs[i]);
        }

        setMinWidth(200);
        //setPrefWidth(200);
    }

    public void update(int numJoueurActif) {

        for (int i = 1; i < nbJoueurs +1; i++) {
            if ( i == numJoueurActif )
                textsJoueurs[i].setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
            else
                textsJoueurs[i].setFont(Font.font("Helvetica", FontWeight.BOLD, 15));
        }
    }
}
