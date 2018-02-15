package mvc.Model;

import java.util.LinkedList;
import java.util.Observable;

public class Plateau extends Observable {
    private int largeur;
    private int hauteur;
    private Case[][] plateau;
    private int[][] test;
    private LinkedList<Piece> piecesPosees;
    private Piece pieceCourante;

    public Plateau(int l, int h){
        this.largeur = l;
        this.hauteur = h;
        this.piecesPosees = new LinkedList<>();
        plateau = new Case[l][h];
        test= new int[10][10];

        for(int i=0;i<this.largeur;i++)
            for(int j=0;j<this.hauteur;j++){
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

    }

    public void versDroite(Piece piece){

    }

    public void versGauche(Piece piece){

    }

}
