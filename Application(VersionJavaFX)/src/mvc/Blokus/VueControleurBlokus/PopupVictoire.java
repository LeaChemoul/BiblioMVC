package mvc.Blokus.VueControleurBlokus;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mvc.Blokus.ModeleBlokus.JoueurBlokus;
import mvc.Blokus.ModeleBlokus.Partie;

public class PopupVictoire extends Stage {

    private VBox popupVBox;
    private Text textVictoire;
    private Button btQuitter;

    public PopupVictoire(Stage primaryStage) {

        //this.primaryStage = primaryStage;

        //Le contenu du Popup : Un message de victoire et un bouton pour quitter.
        popupVBox = new VBox();

        //Bouton pour quitter
        btQuitter = new Button("Quitter le jeu");
        btQuitter.setPadding(new Insets(10));
        btQuitter.setOnMouseClicked(event -> {
            close();
            primaryStage.close();
        });

        //Message de Victoire
        textVictoire = new Text();
        textVictoire.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));

        //On ajoute le bouton et le texte a la vbox.
        popupVBox.setAlignment(Pos.CENTER);
        popupVBox.setSpacing(20);
        popupVBox.getChildren().addAll(textVictoire, btQuitter);

        //---------------
        //On prépare le popup : C'est une nouvelle scène sur un nouveau stage.
        Scene popupScene = new Scene(popupVBox, 200, 120);

        close();
        setScene(popupScene);
        //Cette ligne sert à rendre le stage un popup.
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Bravo !");
        setOnCloseRequest(e -> Platform.exit());

    }

    /**
     * Mets à jour le Popup de Victoire avec le nom du gagnant et l'affiche.
     * @param joueurGagnant joueur qui a gagné la partie
     */
    public void afficherPopupVictoire(JoueurBlokus joueurGagnant) {
        textVictoire.setText("Le joueur " + joueurGagnant.getNumJoueur() + " a gagné !");
        textVictoire.setFill(joueurGagnant.getCouleur());
        showAndWait();
    }

}
