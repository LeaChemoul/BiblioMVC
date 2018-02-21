package mvc.Model;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

public class Plateau extends Observable {
    private PieceBuilder builder = new PieceBuilder();
    private int largeur;
    private int hauteur;
    private Case[][] plateau;
    private int[][] test;
    private ArrayList<Piece> piecesPosees;
    private Piece pieceCourante;
    private ArrayList<Piece> piecesSuivantes;
    private boolean isFull = false; //si on ne peut pas placer d'autres pièces

    public Plateau(int h,int l){
        this.largeur = l;
        this.hauteur = h;
        this.piecesPosees = new ArrayList<>();
        plateau = new Case[h][l];
        test= new int[hauteur][largeur];

        for(int i=0;i<this.hauteur;i++)
            for(int j=0;j<this.largeur;j++){
                plateau[i][j]= new Case(i,j, Color.rgb(255,255,255),-1);
                test[i][j] = 0;
            }
    }

    /*public Piece findPiece(int i, int j){
        //parcours de la liste et recherche

    }*/

    public boolean poserPiece(Piece piece){
        Case[][] temp = piece.getCases();
        for(int i =0;i<8;i++)
            for(int j = 0;j<8;j++)
                if(temp[i][j] != null){
                    int a = temp[i][j].getX();
                    int b = temp[i][j].getY();
                    if(plateau[a][b].getIndex() == -1){
                        plateau[a][b] = temp[i][j];
                        piecesPosees.add(piece);
                        plateau[a][b].setIndex(piecesPosees.size());
                    }
                    else return false;
                }
        setChanged();
        notifyObservers();
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
        this.pieceCourante = builder.getITetris(hauteur/2-2,0);
        if(!this.poserPiece(pieceCourante))
            System.out.println("GAME OVER");

    }

    public Case[][] getPlateau() {
        return plateau;
    }

    public Piece getPieceCourante() {
        return pieceCourante;
    }

    //pseudo code
   /* public boolean descente(){
        if(collision())
            return true;
        else{
            //gérer la descente

            //descendre piece
            setChanged();
            notifyObservers();
            descente();
        }
    }*/

    public boolean collision(){
        return true;
    }


}