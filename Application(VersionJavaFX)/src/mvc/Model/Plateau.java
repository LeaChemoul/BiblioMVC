package mvc.Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Observable;

public class Plateau extends Observable {
    private PieceBuilder builder = new PieceBuilder();
    private int largeur;
    private int hauteur;
    private Case[][] tableauJeu;
    private int[][] test;
    private ArrayList<Piece> piecesPosees;
    private Piece pieceCourante;
    private ArrayList<Piece> piecesSuivantes;
    private boolean isFull = false; //si on ne peut pas placer d'autres pièces

    public Plateau(int h,int l){
        this.largeur = l;
        this.hauteur = h;
        this.piecesPosees = new ArrayList<>();
        tableauJeu = new Case[h][l];
        test= new int[hauteur][largeur];

        for(int i=0;i<this.hauteur;i++)
            for(int j=0;j<this.largeur;j++){
                tableauJeu[i][j]= new Case(i,j, Color.rgb(255,255,255),-1);
                test[i][j] = 0;
            }
    }

    /*public Piece findPiece(int i, int j){
        //parcours de la liste et recherche

    }*/

    public boolean poserPiece(Piece piece){

        return true;
    }

    public void click(int i, int j){
        test[i][j] = 1;

        setChanged();
        notifyObservers();
    }


    public int[][] getTest() {
        return test;
    }

    public void rotationPiece(){
        //mettrea jour etat rotation pièce
    }

    public void versBas(Piece piece){

        setChanged();
        notifyObservers();
    }

    public void versDroite(Piece piece){
        setChanged();
        notifyObservers();
    }

    public void versGauche(Piece piece){
        setChanged();
        notifyObservers();
    }

    public void newPiece(){
        //this.pieceCourante = builder.getITetris(hauteur/2-2,0);
        if(!this.poserPiece(pieceCourante))
            System.out.println("GAME OVER");
            this.descendre();
            this.pieceCourante = null;
    }

    public void descendre(){

    }

    public Case[][] getTableauJeu(){
        return tableauJeu;
    }

    public Piece getPieceCourante() {
        return pieceCourante;
    }

    public void modifierPlateau(int i, int j, Case case1){
        this.tableauJeu[i][j] = case1;
        setChanged();
        notifyObservers();
    }

    public boolean collision(){
        return true;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }
}