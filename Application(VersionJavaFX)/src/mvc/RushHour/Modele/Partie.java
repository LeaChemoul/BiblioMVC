package mvc.RushHour.Modele;

import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import mvc.Model.Piece;
import mvc.Model.Plateau;

import java.awt.*;
import java.util.HashMap;

public class Partie{

    private GenerateurPieces generateurPieces = new GenerateurPieces();
    private Plateau plateau;
    private boolean estFinie;

    public Partie(Plateau p){
        this.plateau = p;
    }

    public void initialiser(){
        HashMap<String, Piece> pieceHashMap = generateurPieces.createPieces();
        plateau.setPoolDePiece(pieceHashMap.values().toArray(new Piece[0]));

        this.plateau.newPiece(pieceHashMap.get("2RushHourV"),0,0);
        this.plateau.getPieceCourante().setNom("2RushHourV1");

        this.plateau.newPiece(pieceHashMap.get("2RushHourV"),2,0);
        this.plateau.getPieceCourante().setNom("2RushHourV2");

        this.plateau.newPiece(pieceHashMap.get("2RushHourH"),4,0);
        this.plateau.getPieceCourante().setNom("2RushHourH1");

        this.plateau.newPiece(pieceHashMap.get("3RushHourH"),5,0);
        this.plateau.getPieceCourante().setNom("3RushHourH1");

        this.plateau.newPiece(pieceHashMap.get("2RushHourV"),3,2);
        this.plateau.getPieceCourante().setNom("2RushHourV3");

        this.plateau.newPiece(pieceHashMap.get("2RushHourH"),3,3);
        this.plateau.getPieceCourante().setNom("2RushHourH2");

        this.plateau.newPiece(pieceHashMap.get("3RushHourV"),3,5);
        this.plateau.getPieceCourante().setNom("3RushHourV1");

        this.plateau.newPiece(pieceHashMap.get("2RushHourH"),0,4);
        this.plateau.getPieceCourante().setNom("2RushHourH3");

        this.plateau.newPiece(pieceHashMap.get("2RushHourH"),1,4);
        this.plateau.getPieceCourante().setNom("2RushHourH4");

        this.plateau.newPiece(pieceHashMap.get("3RushHourV"),0,3);
        this.plateau.getPieceCourante().setNom("3RushHourV2");

        //Piece a bouger
        this.plateau.newPiece(pieceHashMap.get("2RushHourH"),2,1);
        this.plateau.getPieceCourante().setCouleur(Color.RED);
        this.plateau.getPieceCourante().setNom("2RushHourH5");

    }

    public void partieFinie(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("I have a great message for you!");

        alert.showAndWait();
    }

    public Plateau getPlateau() {
        return plateau;
    }

}
