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
    private Partie partie;

    public ListeJoueur(Partie partie, int nbJoueurs) {

        this.partie = partie;
        this.nbJoueurs = nbJoueurs;

        textsJoueurs = new Text[nbJoueurs+2];
        //Une place pour chaque joueur + une place pour le titre + une place pour les règles.

        //Titre
        textsJoueurs[0] = new Text("Liste des Joueurs");
        textsJoueurs[0].setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

        //Règles
        textsJoueurs[nbJoueurs+1] = new Text("\n\nRègles du Blokus :\n\n" +
                "- But du jeu :\n" +
                "Poser toutes ses pièces ou\n" +
                "n'avoir aucun autre joueur\n" +
                "pouvant poser une pièce.\n\n" +
                "- La première pièce doit être\n" +
                "posée dans le coin de la même\n" +
                "couleur que le joueur.\n" +
                "(Elle doit se superposer au\n" +
                "coin coloré)\n\n" +
                "- Les autres pièces doivent\n" +
                "toucher en diagonale une\n" +
                "autre pièce du même joueur\n" +
                "sans la toucher directement.");
        textsJoueurs[nbJoueurs+1].setFont(Font.font("Helvetica", FontWeight.BOLD, 12));

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
        for (int i = 1; i < nbJoueurs + 1; i++) {
            setMargin(textsJoueurs[i], new Insets(5, 5, 5, 40));
            getChildren().add(textsJoueurs[i]);
        }
        setMargin(textsJoueurs[nbJoueurs+1], new Insets(5));
        getChildren().add(textsJoueurs[nbJoueurs+1]);

        setMinWidth(200);
    }

    /**
     * Change le texte du joueur numJoueur par un texte d'abandon.
     * @param numJoueur Numéro du joueur qui choisit d'abandonner
     */
    public void setAbandon(int numJoueur) {

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
            if ( !partie.getJoueur(numJoueurActif-1).isaAbandone() ) {

                if ( i == numJoueurActif )
                    textsJoueurs[i].setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
                else
                    textsJoueurs[i].setFont(Font.font("Helvetica", FontWeight.BOLD, 15));

            }
        }
    }
}
