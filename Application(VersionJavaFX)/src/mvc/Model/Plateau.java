package mvc.Model;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;
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
     * Pose une pièce donnée à une coordonée (x,y) de notre plateau
     * Lorsque l'on pose notre pièce on veille a bien la démarrer à l'endroit (i,j)
     * On gère bien les collisions.
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
        int nbrCasesParcourues = 0;
        //cette variable permet de décaler notre pièce à (i,j) au cas ou elle n'est definie
        // par ex que à une coordonnée (3,2) sur un palteau (4,4)
        for(int x=0; x<piece.getCases().length;x++){
            pieceTrouvee = false;
            int decalageX = 0;
            for (int y = 0; y < piece.getCases().length; y++) {
                if(i+y >= 0 && j+x >= 0 && i+y < this.getHauteur() && j+x < this.getLargeur()
                        && tableauJeu[i+y][j+x] == null  && nbrCasesParcourues<=piece.getTaille())         //On teste les collisions
                {
                    //TODO regler le problèmeeeeeee !!!!!!!!! :( lorsqu'on va a droite ou en bas on dépasse la limite
                    if (piece.getCases()[x][y] == 1){
                        positionsPlateau.add(new Vec2d(i + y -decalageX, j + x - decalageY));
                        pieceTrouvee = true;
                        nbrCasesParcourues ++;
                    }
                }
                else{
                    System.out.println("La place est occupée ou en dehors du plateau");
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
        /*Vec2d[] listeVect2 = new Vec2d[4];
        listeVect2[0] = new Vec2d(2,2);
        listeVect2[1] = new Vec2d(1,1);
        listeVect2[2] = new Vec2d(1,2);
        listeVect2[3] = new Vec2d(2,1);
        builder.addPiece("test2",4,4,Color.rgb(160,30,115),2,listeVect2);
        builder.afficherPiece("test2");
        this.pieceCourante = builder.getPiece("test2");
        this.piecesPosees.add(pieceCourante);
        this.poserPiecePlateau(pieceCourante, 0,1); //collision
        this.poserPiecePlateau(pieceCourante, 1,1);*/
        setChanged();
        notifyObservers();
    }

    public void deplacer(Direction direction, Piece piece){
        //tester si la place est occupée avant de bouger
        ArrayList<Vec2d> positions = occurencesPiecesPlateau(piece);
        if(positions != null){
            if(!collision(positions, direction,this.piecesPosees.indexOf(piece))){
                effacerPiecePlateau(positions);
                this.poserPiecePlateau(piece,(int) positions.get(0).x + direction.x, (int) positions.get(0).y +direction.y);
            }
        }
        setChanged();
        notifyObservers();
    }

    public void versBas(Piece piece){
        this.deplacer(Direction.DOWN, piece);
    }

    public void versDroite(Piece piece){
        this.deplacer(Direction.RIGHT, piece);
    }

    public void versGauche(Piece piece){
        this.deplacer(Direction.LEFT, piece);
    }

    public void versHaut(Piece piece){
        this.deplacer(Direction.UP, piece);
    }

    /**
     * Efface une pièce mais retourne la position ou elle était.
     * @param piece
     * @return
     */
    public ArrayList<Vec2d> occurencesPiecesPlateau(Piece piece){
        int index = this.piecesPosees.indexOf(piece);
        ArrayList<Vec2d> positions = new ArrayList<>();
        for (int i = 0; i < this.hauteur; i++) {
            for (int j = 0; j < this.largeur; j++) {
                if(this.tableauJeu[i][j] != null && this.tableauJeu[i][j].getIndex() == index){
                    positions.add(new Vec2d(i,j));
                }
            }
        }
        return positions;
    }

    public boolean collision(ArrayList<Vec2d> occurences, Direction dir, int index){
        for (Vec2d occurence : occurences){
            int a = (int) occurence.x + dir.x;
            int b = (int) occurence.y + dir.y;
            if (a<0 || a>=this.largeur || b< 0 || b>=this.hauteur || (this.getTableauJeu()[a][b] != null && this.getTableauJeu()[a][b].getIndex() != index ))
                return true;
        }
        return false;
    }

    public void effacerPiecePlateau(ArrayList<Vec2d> occurences){
        for (Vec2d occurence : occurences) {
            this.getTableauJeu()[(int) occurence.x][(int) occurence.y] = null;
        }
    }

    public Case[][] getTableauJeu(){
        return tableauJeu;
    }

    public Piece getPieceCourante() {
        return pieceCourante;
    }


    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }
}