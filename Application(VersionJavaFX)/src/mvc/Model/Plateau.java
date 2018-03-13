package mvc.Model;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

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
        this.hauteur = h;
        this.largeur = l;
        this.piecesPosees = new ArrayList<>();
        tableauJeu = new Case[h][l];

        for(int i = 0; i<this.hauteur; i++)
            for(int j = 0; j<this.largeur; j++){
                tableauJeu[i][j]= null;
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
        //On parcours le plateau de jeu depuis la position (i,j) et les cases de la pièce simultanement.
        //On ajoutera à positionsPlateau les positions de notre plateau à remplir par notre pièce. Evite uen boucle supplémentaire.
        ArrayList<Vec2d> positionsPlateau = new ArrayList<>(); // Les positions (x,y) du plateau où il faudra placer notre pièce
        int index = this.piecesPosees.indexOf(piece);
        boolean pieceTrouvee;
        //cette variable permet de garder en mémoire combien de colonnes de Piece.cases nous avons parcourus avant de trouver la pièce : décalage
        int decalageX = 0, decalageY = 0, nbrCasesParcourues = 0;
        //on parcours le tableau de cases de notre pièce.
        for(int x=0; x<piece.getCases().length;x++){ //Parcours des colonnes
            pieceTrouvee = false;
            decalageY = 0;
            for (int y = 0; y < piece.getCases().length; y++) { //Parcours des lignes
                if(j+y >= 0 && i+x >= 0
                        && j+y-decalageY< this.getLargeur() && i+x-decalageX < this.getHauteur() //Si cela ne dépasses pas notre plateau de jeu
                        //On teste les collisions
                        && (tableauJeu[i+x-decalageX][j+y-decalageY] == null || //Ou la case est vide et on peut poser notre pièce.
                                (tableauJeu[i+x-decalageX][j+y-decalageY]!= null && tableauJeu[i+x-decalageX][j+y-decalageY].getIndex() == index))) //Ou c'est une occurence de notre pièce (avant d'être bougée)
                {
                    if (piece.getCases()[x][y] == 1){
                        positionsPlateau.add(new Vec2d(i + x - decalageX, j + y -decalageY));
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
                    decalageY ++; //On a toujours pas trouvé notre pièce on décale en ligne
            }
            if(!pieceTrouvee)
                decalageX ++; //On a toujours pas trouvé notre pièce on décale en colonne
        }
        for (Vec2d aPositionsPlateau : positionsPlateau) { //On met à jour le plateau en y posant la pièce
            int ii = (int) aPositionsPlateau.x;
            int jj = (int) aPositionsPlateau.y;
            this.tableauJeu[ii][jj] = new Case(ii,jj, piece.getCouleur(),index);
        }
        return true;
    }

    public void rotationPiece(){
        //mettre a jour etat rotation pièce
    }

    /**
     * On crée une nouvelle instance de pièce qu'on pose sur notre plateau de jeu.
     */
    public void newPiece(){
        this.pieceCourante = null;

        //On crée une pièce à partir des modèles disponibles dans le pool de pièces
        if(PoolDePiece != null){
            Random random = new Random();
            this.pieceCourante = new Piece(PoolDePiece[random.nextInt(PoolDePiece.length)]);
            this.piecesPosees.add(pieceCourante);
            this.poserPiecePlateau(pieceCourante,0,0);
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Prends en charge le déplacement d'une piece sur un plateau
     * @param direction Enum qui determine si on la déplace en haut, a droite, à gauche ou en bas
     * @param piece Piece à déplacer
     */
    public boolean deplacer(Direction direction, Piece piece){
        //tester si la place est occupée avant de bouger
        int iter = 1; //L'itérateur qui va compter le nombea de déplacements réalisés.
        ArrayList<Vec2d> positions = occurrencesPiecesPlateau(piece); //Toutes les occurences de notre pièce donnée sur le plateau
        if(positions != null){

            if(!collision(positions, direction,this.piecesPosees.indexOf(piece))){ //Si nos positions ne génère pas de collisions
                effacerPiecePlateau(positions); //On efface la pièce
                //On la pose aux nouvelles coordonnées.
                // On la place à partir de la position précédente à laquelle on a ajouté (0,-1) par exemple pour la descendre verticalement
                this.poserPiecePlateau(piece,(int) positions.get(0).x + direction.x, (int) positions.get(0).y +direction.y);
            }else{
                return false;
            }
        }
        setChanged();
        notifyObservers();
        return true;
    }

    public boolean versBas(Piece piece){
        return this.deplacer(Direction.RIGHT, piece);
    }

    public boolean versDroite(Piece piece){
        return this.deplacer(Direction.DOWN, piece);
    }

    public boolean versGauche(Piece piece){
        return this.deplacer(Direction.UP, piece);
    }

    public boolean versHaut(Piece piece){
        return this.deplacer(Direction.LEFT, piece);
    }

    /**
     * Retourne toutes les occurences (i,j) d'une pièce sur le plateau
     * @param piece notre pièce
     * @return
     */
    public ArrayList<Vec2d> occurrencesPiecesPlateau(Piece piece){
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

    public boolean collision(ArrayList<Vec2d> occurrences, Direction dir, int index){
        for (Vec2d occurrence : occurrences){
            int a = (int) occurrence.x + dir.x;
            int b = (int) occurrence.y + dir.y;
            if (a<0 || a>=this.hauteur || b< 0 || b>=this.largeur || (this.getTableauJeu()[a][b] != null && this.getTableauJeu()[a][b].getIndex() != index ))
                return true;
        }
        return false;
    }

    public void effacerPiecePlateau(ArrayList<Vec2d> occurrences){
        for (Vec2d occurrence : occurrences) {
            this.getTableauJeu()[(int) occurrence.x][(int) occurrence.y] = null;
        }
        setChanged();
        notifyObservers();
    }


    public int ligneASupprimer(){
        boolean estRemplie;
        for (int i = 0; i < this.hauteur ; i++) {
            estRemplie =true;
            for (int j = 0; j < this.largeur; j++) {
                if(this.tableauJeu[i][j] == null){
                    estRemplie =false;
                }
            }
            if(estRemplie){
                return i;
            }
        }
        return -1; //Si aucune ligne est remplie
    }

    public void effacerLigne(int ligne){
        //TODO Pour la ligne considerée mettre à jour les pièces puis descendre celles au-dessus
        //Pour chaque pièce à descendre, la passer en pièce courante et appeller le timer qui la descend
        ArrayList<Piece> ontEteDescendues = new ArrayList<>();
        for (int j = 0; j < this.getHauteur() ; j++) {
            Piece pieceASupp = recupererPiece(ligne,j);
            if(pieceASupp != null){
                Vec2d decalage = decalagePremiereCase(pieceASupp,new Vec2d(ligne,j));
                if(decalage != null){
                    pieceASupp.deleteDecalage(decalage);
                    ArrayList<Vec2d> occurences = occurrencesPiecesPlateau(pieceASupp);
                    effacerPiecePlateau(occurences);
                    poserPiecePlateau(pieceASupp,(int) occurences.get(0).x, (int) occurences.get(0).y);

                    setChanged();
                    notifyObservers();
                }
            }
        }

    }

    //Retourne le decalage d'une case sur notre plateau
    // vis à vis de la première occurence de la pièce en paratnt de en haut à gauche du plateau.
    public Vec2d decalagePremiereCase(Piece piece, Vec2d pos){
        ArrayList<Vec2d> occurences = this.occurrencesPiecesPlateau(piece);
        //première occurence par rapport à laquelle on va calculer notre décalage
        int firstX = (int) occurences.get(0).x;
        int firstY = (int) occurences.get(0).y;
        for (int i = 0; i < occurences.size(); i++) {
            //On compare la position de chaque case de la pièce par rapport à la position donnée
            int a = (int) occurences.get(i).x;
            int b = (int) occurences.get(i).y;
            if(a-firstX == (int) pos.x && b-firstY == (int) pos.y){
                return new Vec2d(a-firstX, b-firstY);
            }
        }
        return  null;
    }

    /**
     * Renvoie la pièce à qui appartient la case aux coordonnées x,y du plateau.
     */
    public Piece recupererPiece(int x, int y) {
        if(tableauJeu[y][x].getIndex() != -1)
            return piecesPosees.get(tableauJeu[x][y].getIndex());
        else
            return null;
    }


    public void effacerLigne(){

    }

    //Méthodes relatives à PoolDePiece
    /*
        PoolDePiece va contenir les pièces générés à travers le Builder et qu'on va utiliser dans notre jeu. On veut pouvoir :
        - Juste piocher une pièce aléatoirement sans la retirer de la liste (cas Tetris)
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

    public void setPieceCourante(Piece pieceCourante) {
        this.pieceCourante = pieceCourante;
    }

    public void setPoolDePiece(Piece[] poolDePiece) {
        PoolDePiece = poolDePiece;
    }
}