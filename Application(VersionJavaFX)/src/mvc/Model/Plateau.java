package mvc.Model;

import com.sun.javafx.geom.Vec2d;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

public class Plateau extends Observable {
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
        this.pieceCourante = new Piece();
        plateau = new Case[h][l];
        test= new int[hauteur][largeur];

        for(int i=0;i<this.hauteur;i++)
            for(int j=0;j<this.largeur;j++){
                plateau[i][j]= new Case(i,j,"BLANC",-1);
                test[i][j] = 0;
            }

        plateau[9][0]= new Case(9,0,"JAUNE",-1);
        plateau[9][1]= new Case(9,1,"JAUNE",-1);
        plateau[9][2]= new Case(9,2,"JAUNE",-1);
    }

    /*public Piece findPiece(int i, int j){
        //parcours de la liste et recherche

    }*/


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
        this.pieceCourante = new Piece();
        Vec2d dimPiece = pieceCourante.getLargeurHateur();
        int dimX = (int) dimPiece.x;
        int dimY = (int) dimPiece.y;
        int milieu = largeur/2;
        int milieuPiece = dimX/2;
        int restePiece = dimX - milieuPiece;


        setChanged();
        notifyObservers();
    }

}
