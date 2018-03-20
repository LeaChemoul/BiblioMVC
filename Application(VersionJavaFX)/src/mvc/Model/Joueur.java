package mvc.Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Joueur {


    protected int numJoueur; //Numéro du joueur
    protected Color couleur;
    protected Piece pieceActive; //Piece avec laquelle le joueur peut interagir
    protected ArrayList<Piece> PoolDePiece; //Liste des pièces du joueur, utilisés différemment en fonction du jeu


    /*  TODO : A lire
        A voir : On peut donner en référence le plateau pour donner à Joueur les méthodes lui permettant de joueur.
        Ou alors on supprime cette attribut et on récupère juste les pièces  du joueur, et on le fera jouer à travers Partie
        qui a la classe Plateau.
     */
    protected Plateau plateau;


    public Joueur (int num) {
        this.numJoueur = num;
    }

    public Joueur (int num, Plateau plateau, ArrayList<Piece> PoolDePiece) {
        this.numJoueur = num;
        this.plateau = plateau;
        this.PoolDePiece = PoolDePiece;
    }

    public Joueur (int num, Plateau plateau, Color couleur, ArrayList<Piece> PoolDePiece) {
        this.numJoueur = num;
        this.plateau = plateau;
        this.couleur = couleur;
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


    public boolean poolIsEmpty() {
        return PoolDePiece.isEmpty();
    }
    //Accesseurs

    public ArrayList<Piece> getPoolDePiece() {
        return PoolDePiece;
    }

    public Color getCouleur() {
        return couleur;
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
