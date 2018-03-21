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

        //Création d'une configuration particulière
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
        //On considère que la dernière pièce de ma liste de pièce est la pièce gagnante
        this.plateau.newPiece(pieceHashMap.get("2RushHourH"),2,1,true, Color.RED);
    }

    public void partieFinie(){
        //On considère que la pièce qu'il faut faire sortir et la dernière pièce de notre liste de pièces.
        //D'où son index qui sera toujours this.plateau.getPiecesPosees().size()-1
        if(!this.estFinie && this.plateau.getTableauJeu()[(int)this.sortie.x][(int)this.sortie.y]!= null &&
                this.plateau.getTableauJeu()[(int)this.sortie.x][(int)this.sortie.y].getIndex() == this.plateau.getPiecesPosees().size()-1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("BRAVO");
            alert.setHeaderText(null);
            alert.setContentText("La pièce est sortie, vous avez gagné !");

            alert.show();

            this.estFinie = true;
        }
    }

    /**
     * Les couleurs des pièces du RUSH HOUR seront générées par cette fonction.
     * @return Couleure aléatoire parmi un groupe prédéfini.
     */
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

    public boolean isEstFinie() {
        return estFinie;
    }
}
