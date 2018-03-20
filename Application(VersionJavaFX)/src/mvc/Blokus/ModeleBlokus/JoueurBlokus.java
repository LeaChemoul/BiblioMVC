package mvc.Blokus.ModeleBlokus;

import javafx.scene.paint.Color;
import mvc.Blokus.*;
import mvc.Model.*;

import java.util.ArrayList;

public class JoueurBlokus extends Joueur {

    private int coinDepartX;
    private int coinDepartY;

    public JoueurBlokus (int num, Plateau plateau, Color couleur, ArrayList<Piece> PoolDePiece) {
        super(num, plateau, couleur, PoolDePiece);
        setCoinDepart();
    }


    /**
     * Attribut au joueur les coordonnées du coin où il commence
     */
    public void setCoinDepart() {
        switch (numJoueur) {
            case 1:
                coinDepartX = plateau.getHauteur() - 1;
                coinDepartY = plateau.getLargeur() - 1;
                break;
            case 2:
                coinDepartX = 0;
                coinDepartY = 0;
                break;
            case 3:
                coinDepartX = 0;
                coinDepartY = plateau.getLargeur() - 1;
                break;
            case 4:
            default:
                coinDepartX = plateau.getHauteur() - 1;
                coinDepartY = 0;
                break;
        }
    }
}
