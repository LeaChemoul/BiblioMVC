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
        Case[][] temp = piece.getCases();
        piecesPosees.add(piece);
        for(int i =0;i<5;i++)
            for(int j = 0;j<5;j++)
                if(temp[i][j] != null){
                    int a = temp[i][j].getX();
                    int b = temp[i][j].getY();
                    if(tableauJeu[a][b].getIndex() == -1){
                        tableauJeu[a][b] = temp[i][j];
                        tableauJeu[a][b].setIndex(piecesPosees.size());
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
            this.descendre();
            this.pieceCourante = null;
        this.pieceCourante = builder.getOTetris(hauteur/2-2,0);
        if(!this.poserPiece(pieceCourante))
            System.out.println("GAME OVER");
    }

    public void descendre(){
        boolean enCours = true;
        while(enCours){
            enCours = pieceCourante.mouvement(this,Direction.DOWN);
            setChanged();
            notifyObservers();
                /*try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
        }
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

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }
}