package mvc.Model;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Observable;

public class Plateau extends Observable {

    private PieceBuilder builder = new PieceBuilder();
    private int hauteur;
    private int largeur;
    private Case[][] tableauJeu;
    private int[][] test;

    //Liste des pièces présentes sur le plateau.
    private ArrayList<Piece> piecesPosees;

    //Piece actuelle ( à déplacer ou poser )
    private Piece pieceCourante;

    //Liste dans laquelle on va piocher les pièces avec lesquelles on va jouer.
    private Piece[] PoolDePiece;

    //Pas encore implémenté.
    private ArrayList<Piece> piecesSuivantes;
    //Useless ?
    private boolean isFull = false; //si on ne peut pas placer d'autres pièces

    public Plateau(int h,int l){
        this.hauteur = l;
        this.largeur = h;
        this.piecesPosees = new ArrayList<>();
        tableauJeu = new Case[h][l];
        test= new int[largeur][hauteur];

        for(int i = 0; i<this.largeur; i++)
            for(int j = 0; j<this.hauteur; j++){
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
    synchronized public boolean poserPiecePlateau(Piece piece,int i, int j){
        //On ajoutera à positionsPlateau les positions de notre plateau à remplir par notre pièce. Evite uen boucle supplémentaire.
        ArrayList<Vec2d> positionsPlateau = new ArrayList<>();
        int index = this.piecesPosees.indexOf(piece);
        boolean pieceTrouvee = false;
        int decalageX = 0;
        int nbrCasesParcourues = 0;
        //cette variable permet de décaler notre pièce à (i,j) au cas ou elle n'est definie
        // par ex que à une coordonnée (3,2) sur un plateau (4,4)
        for(int x=0; x<piece.getCases().length;x++){
            pieceTrouvee = false;
            int decalageY = 0;
            for (int y = 0; y < piece.getCases().length; y++) {
                if(i+y >= 0 && j+x >= 0 && i+y-decalageY< this.getLargeur() && j+x-decalageX < this.getHauteur()
                        && (tableauJeu[i+y-decalageY][j+x-decalageX] == null || (tableauJeu[i+y][j+x]!= null && tableauJeu[i+y][j+x].getIndex() == index))) //On teste les collisions
                {
                    if (piece.getCases()[x][y] == 1){
                        positionsPlateau.add(new Vec2d(i + y -decalageY, j + x - decalageX));
                        pieceTrouvee = true;
                        nbrCasesParcourues ++;
                    }
                }else if(nbrCasesParcourues<=piece.getTaille()){
                    break;
                }
                else{
                    System.out.println("La place est occupée ou en dehors du plateau");
                    return false;
                }
                if(!pieceTrouvee)
                    decalageY ++;
            }
            if(!pieceTrouvee)
                decalageX ++;
        }
        for (Vec2d aPositionsPlateau : positionsPlateau) {
            int ii = (int) aPositionsPlateau.x;
            int jj = (int) aPositionsPlateau.y;
            this.tableauJeu[ii][jj] = new Case(ii,jj, piece.getCouleur(),index);
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
        this.poserPiecePlateau(pieceCourante, 0,1); //collision
        this.poserPiecePlateau(pieceCourante, 1,1);
        setChanged();
        notifyObservers();
    }

    public void deplacer(Direction direction, Piece piece, int nbrDeplac){
        //tester si la place est occupée avant de bouger
        int iter = 1;
        while(iter <= nbrDeplac){
            ArrayList<Vec2d> positions = occurrencesPiecesPlateau(piece);
            if(positions != null){

                    if(!collision(positions, direction,this.piecesPosees.indexOf(piece))){
                        effacerPiecePlateau(positions);
                        this.poserPiecePlateau(piece,(int) positions.get(0).x + direction.x, (int) positions.get(0).y +direction.y);
                    }else{
                        break;
                    }
                    iter++;
            }
        }
        setChanged();
        notifyObservers();
    }

    public void descente(Piece piece){
        deplacer(Direction.DOWN, piece, this.getHauteur());
    }

    public void versBas(Piece piece){
        this.deplacer(Direction.DOWN, piece, 1);
    }

    public void versDroite(Piece piece){
        this.deplacer(Direction.RIGHT, piece,1);
    }

    public void versGauche(Piece piece){
        this.deplacer(Direction.LEFT, piece,1);
    }

    public void versHaut(Piece piece){
        this.deplacer(Direction.UP, piece,1);
    }

    /**
     * Efface une pièce mais retourne la position ou elle était.
     * @param piece
     * @return
     */
    public ArrayList<Vec2d> occurrencesPiecesPlateau(Piece piece){
        int index = this.piecesPosees.indexOf(piece);
        ArrayList<Vec2d> positions = new ArrayList<>();
        for (int i = 0; i < this.largeur; i++) {
            for (int j = 0; j < this.hauteur; j++) {
                if(this.tableauJeu[i][j] != null && this.tableauJeu[i][j].getIndex() == index){
                    positions.add(new Vec2d(i,j));
                }
            }
        }
        return positions;
    }

    public boolean collision(ArrayList<Vec2d> occurrences, Direction dir, int index){
        for (Vec2d occurrence : occurrences){
            int a = (int) occurrence.x + dir.x;
            int b = (int) occurrence.y + dir.y;
            if (a<0 || a>=this.largeur || b< 0 || b>=this.hauteur || (this.getTableauJeu()[a][b] != null && this.getTableauJeu()[a][b].getIndex() != index ))
                return true;
        }
        return false;
    }

    public void effacerPiecePlateau(ArrayList<Vec2d> occurrences){
        for (Vec2d occurrence : occurrences) {
            this.getTableauJeu()[(int) occurrence.x][(int) occurrence.y] = null;
        }
    }

    public void effacerLigne(){

    }

    //Méthodes relatives à PoolDePiece
    /*
        PoolDePiece va contenir les pièces générés à travers le Builder et qu'on va utiliser dans notre jeu. On veut pouvoir :
        - Juste piocher une pièce aléatoirement sans la retire de la liste (cas Tetris)
        - Retirer des pièces (Cas Blokus. Possibilité d'avoir une liste de pièce nécessaire pour chaque joueur.)
        - Ajouter des pièces (autre jeux non couverts dans ceux que l'on doit créer.
     */


    //Accesseurs

    public Case[][] getTableauJeu(){
        return tableauJeu;
    }

    public Piece getPieceCourante() {
        return pieceCourante;
    }


    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }


}