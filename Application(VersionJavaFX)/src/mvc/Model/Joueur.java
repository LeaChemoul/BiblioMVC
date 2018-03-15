package mvc.Model;

import java.util.ArrayList;

public class Joueur {


    private int numJoueur; //Numéro du joueur
    private Piece pieceActive; //Piece avec laquelle le joueur peut interagir
    private ArrayList<Piece> PoolDePiece; //Liste des pièces du joueur, utilisés différemment en fonction du jeu


    /*  TODO : A lire
        A voir : On peut donner en référence le plateau pour donner à Joueur les méthodes lui permettant de joueur.
        Ou alors on supprime cette attribut et on récupère juste les pièces  du joueur, et on le fera jouer à travers Partie
        qui a la classe Plateau.
     */
    private Plateau plateau;


    public Joueur (int num) {
        this.numJoueur = num;
    }

    public Joueur (int num, Plateau plateau, ArrayList<Piece> PoolDePiece) {
        this.numJoueur = num;
        this.plateau = plateau;
        this.PoolDePiece = PoolDePiece;
    }



    //Manipulation liste de Pieces.


    public void ajouterPiece(Piece piece) {
        PoolDePiece.add(piece);
    }

    public void supprimerPiece(int index) {
        PoolDePiece.remove(index);
    }

    public void supprimerPiece(Piece piece) {
        PoolDePiece.remove(piece);
    }

    public void SelectPiece(int index) {
        pieceActive = PoolDePiece.get(index);
    }




    public Piece getPieceActive() {
        return pieceActive;
    }

    public void setPieceActive(Piece pieceActive) {
        this.pieceActive = pieceActive;
    }

    public int getNumJoueur() {
        return numJoueur;
    }

    public void setNumJoueur(int numJoueur) {
        this.numJoueur = numJoueur;
    }

}
