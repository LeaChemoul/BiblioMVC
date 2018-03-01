package mvc.Model;

import com.sun.javafx.geom.Vec2d;
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
                tableauJeu[i][j]= null;
                test[i][j] = 0;
            }
    }

    /**
     * Pose une piède donnée à une coordonée (x,y) de notre plateau
     * Lorsque l'on pose notre pièce on veille a bien la démarrer à l'endroit (i,j)
     * On gère bien les collisions
     * @param piece
     * @param i
     * @param j
     * @return
     */
    public boolean poserPiecePlateau(Piece piece,int i, int j){
        //On ajoutera à positionsPlateau les positions de notre plateau à remplir par notre pièce. Evite uen boucle supplémentaire.
        ArrayList<Vec2d> positionsPlateau = new ArrayList<>();
        boolean pieceTrouvee = false;
        int decalageY = 0;
        //cette variable permet de décaler notre pièce à (i,j) au cas ou elle n'est definie
        // par ex que à une coordonnée (3,2) sur un palteau (4,4)
        for(int x=0; x<piece.getCases().length;x++){
            pieceTrouvee = false;
            int decalageX = 0;
            for (int y = 0; y < piece.getCases().length; y++) {
                if(i+x < tableauJeu.length && j+y < tableauJeu.length && tableauJeu[i+y][j+x] == null)         //On teste les collisions
                {
                    if (piece.getCases()[x][y] == 1){
                        positionsPlateau.add(new Vec2d(i + y -decalageX, j + x - decalageY));
                        pieceTrouvee = true;
                    }
                }
                else{
                    System.out.println("La place est occupée");
                    return false;
                }
                if(!pieceTrouvee)
                    decalageX ++;
            }
            if(!pieceTrouvee)
                decalageY ++;
        }
        for (Vec2d aPositionsPlateau : positionsPlateau) {
            int ii = (int) aPositionsPlateau.x;
            int jj = (int) aPositionsPlateau.y;
            this.tableauJeu[ii][jj] = new Case(ii,jj, piece.getCouleur(), this.piecesPosees.indexOf(piece));
        }
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
        //mettre a jour etat rotation pièce
    }


    public void newPiece(){
        //TEST de cération et d'affichage d'une pièce
        Vec2d[] listeVect = new Vec2d[4];
        listeVect[0] = new Vec2d(2,2);
        listeVect[1] = new Vec2d(1,2);
        listeVect[2] = new Vec2d(3,2);
        listeVect[3] = new Vec2d(1,3);
        builder.addPiece("test",4,4,Color.rgb(120,150,1),2,listeVect);
        builder.afficherPiece("test");
        this.pieceCourante = builder.getPiece("test");
        this.piecesPosees.add(pieceCourante);
        this.poserPiecePlateau(pieceCourante,0,0);
        Vec2d[] listeVect2 = new Vec2d[4];
        listeVect2[0] = new Vec2d(2,2);
        listeVect2[1] = new Vec2d(1,1);
        listeVect2[2] = new Vec2d(1,2);
        listeVect2[3] = new Vec2d(2,1);
        builder.addPiece("test2",4,4,Color.rgb(160,30,115),2,listeVect2);
        builder.afficherPiece("test2");
        this.pieceCourante = builder.getPiece("test2");
        this.piecesPosees.add(pieceCourante);
        this.poserPiecePlateau(pieceCourante, 1,1);
        setChanged();
        notifyObservers();
    }

    public void deplacer(Direction direction){
        setChanged();
        notifyObservers();
    }

    public void versBas(Piece piece){
        this.deplacer(Direction.DOWN);
    }

    public void versDroite(Piece piece){
        this.deplacer(Direction.RIGHT);
    }

    public void versGauche(Piece piece){
        this.deplacer(Direction.LEFT);
    }

    public void versHaut(Piece piece){
        this.deplacer(Direction.UP);
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