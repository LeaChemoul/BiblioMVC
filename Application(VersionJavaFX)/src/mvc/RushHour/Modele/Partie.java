package mvc.RushHour.Modele;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import mvc.Model.Piece;
import mvc.Model.Plateau;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Partie{

    private GenerateurPieces generateurPieces = new GenerateurPieces();
    private Plateau plateau;
    private boolean estFinie;
    private Vec2d sortie = new Vec2d(2,5);

    public Partie(Plateau p){
        this.plateau = p;
    }


    public void initialiser(){
        HashMap<String, Piece> pieceHashMap = generateurPieces.createPieces();
        plateau.setPoolDePiece(pieceHashMap.values().toArray(new Piece[0]));

        this.plateau.newPiece(pieceHashMap.get("2RushHourV"),0,0,false,genererCouleur());

        this.plateau.newPiece(pieceHashMap.get("2RushHourV"),2,0,false,genererCouleur());

        this.plateau.newPiece(pieceHashMap.get("2RushHourH"),4,0,true,genererCouleur());

        this.plateau.newPiece(pieceHashMap.get("3RushHourH"),5,0,true,genererCouleur());

        this.plateau.newPiece(pieceHashMap.get("2RushHourV"),3,2,false,genererCouleur());

        this.plateau.newPiece(pieceHashMap.get("2RushHourH"),3,3,true,genererCouleur());

        this.plateau.newPiece(pieceHashMap.get("3RushHourV"),3,5,false,genererCouleur());

        this.plateau.newPiece(pieceHashMap.get("2RushHourH"),0,4,true,genererCouleur());

        this.plateau.newPiece(pieceHashMap.get("2RushHourH"),1,4,true,genererCouleur());

        this.plateau.newPiece(pieceHashMap.get("3RushHourV"),0,3,false,genererCouleur());

        //Piece a bouger
        this.plateau.newPiece(pieceHashMap.get("2RushHourH"),2,1,true, Color.RED);
    }

    public void partieFinie(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("I have a great message for you!");

        alert.showAndWait();
    }

    public Color genererCouleur(){
        ArrayList<Color> colorArrayList = new ArrayList<>();
        colorArrayList.add(Color.rgb(255,116,0));
        colorArrayList.add(Color.rgb(69,220,79));
        colorArrayList.add(Color.rgb(222,70,156));
        colorArrayList.add(Color.rgb(154,70,200));
        colorArrayList.add(Color.rgb(11,87,196));
        colorArrayList.add(Color.rgb(255,232,80));
        colorArrayList.add(Color.rgb(62,174,190));
        Random r = new Random();
        return colorArrayList.get(r.nextInt(colorArrayList.size()));
    }

    public Plateau getPlateau() {
        return plateau;
    }
}
