package mvc.Blokus.ModeleBlokus;

import Model.Joueur;
import Model.Piece;
import Model.Plateau;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class JoueurBlokus extends Joueur {

    private int coinDepartX;
    private int coinDepartY;

    private boolean premierCoup;
    private boolean aAbandone;

    public JoueurBlokus (int num, Plateau plateau, Color couleur, ArrayList<Piece> PoolDePiece) {
        super(num, plateau, couleur, PoolDePiece);
        setCoinDepart();
        this.premierCoup = true;
        this.aAbandone = false;
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

    //Accesseurs


    public boolean isaAbandone() {
        return aAbandone;
    }

    public void setaAbandone(boolean aAbandone) {
        this.aAbandone = aAbandone;
    }

    public boolean isPremierCoup() {
        return premierCoup;
    }

    public void setPremierCoup(boolean premierCoup) {
        this.premierCoup = premierCoup;
    }

    public int getCoinDepartX() {
        return coinDepartX;
    }

    public void setCoinDepartX(int coinDepartX) {
        this.coinDepartX = coinDepartX;
    }

    public int getCoinDepartY() {
        return coinDepartY;
    }

    public void setCoinDepartY(int coinDepartY) {
        this.coinDepartY = coinDepartY;
    }
}
