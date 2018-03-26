package mvc.Model;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class Plateau extends Observable {

    private PieceBuilder builder = new PieceBuilder();
    private int hauteur;
    private int largeur;
    private Case[][] tableauJeu;

    //Liste des pièces présentes sur le plateau.
    private ArrayList<Piece> piecesPosees;

    //Piece actuelle ( à déplacer ou poser )
    private Piece pieceCourante;

    //Liste dans laquelle on va piocher les pièces avec lesquelles on va jouer.
    private Piece[] PoolDePiece;

    //Liste des pièces suivantes dans lesquelles on piochera le type de la pièce courante (ex : tetris)
    private ArrayList<Piece> piecesSuivantes = new ArrayList<>();

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
    synchronized public boolean poserPiecePlateau(Piece piece,int i, int j){
    //On parcours le plateau de jeu depuis la position (i,j) et les cases de la pièce simultanement.
        //On ajoutera à positionsPlateau les positions de notre plateau à remplir par notre pièce. Evite uen boucle supplémentaire.
        ArrayList<Vec2d> positionsPlateau = new ArrayList<>(); // Les positions (x,y) du plateau où il faudra placer notre pièce
        HashMap<Vec2d, Vec2d> positionsLocalPiece = new  HashMap<Vec2d, Vec2d>(); //Se souvient des coordonnées local des pièces
        int index = this.piecesPosees.indexOf(piece);
        boolean pieceTrouvee = false;
        //cette variable permet de garder en mémoire combien de colonnes de Piece.cases nous avons parcourus avant de trouver la pièce : décalage
        int decalageX = 0, decalageY = 0;
        //on parcours le tableau de cases de notre pièce.
        for(int x = 0; x < piece.getCases().length; x++ ){ //Parcours des colonnes
            pieceTrouvee = false;
            decalageY = 0;
            for (int y = 0; y < piece.getCases()[0].length; y++) { //Parcours des lignes
                if( peutEtrePosee(i, j, index,decalageX,decalageY,x,y) ) //Ou c'est une occurence de notre pièce (avant d'être bougée)
                {
                    if (piece.getCases()[x][y] == 1){
                        positionsPlateau.add(new Vec2d(i + x - decalageX, j + y - decalageY));
                        positionsLocalPiece.put(new Vec2d(i + x - decalageX, j + y - decalageY), new Vec2d(x, y));
                        pieceTrouvee = true;
                    }
                }
                else if(piece.getCases()[x][y] != 1){
                    if(!pieceTrouvee && piece.colonneVide(y))
                        decalageY++; //On a toujours pas trouvé notre pièce on décale en ligne
                    continue;
                }
                else{
                    System.out.println("La place est occupée ou en dehors du plateau");
                    return false;
                }
                if(!pieceTrouvee && piece.colonneVide(y))
                    decalageY++; //On a toujours pas trouvé notre pièce on décale en ligne
            }
            if(!pieceTrouvee && piece.ligneVide(x))
                decalageX++; //On a toujours pas trouvé notre pièce on décale en colonne
        }
        if(!positionsPlateau.isEmpty() && positionsPlateau.size() == piece.getTaille()) { //Si on a bien pu tout poser dans le plateau
            index = piecesPosees.indexOf(piece);
            for (Vec2d position : positionsPlateau) { //On met à jour le plateau en y posant la pièce
                int ii = (int) position.x;
                int jj = (int) position.y;
                Vec2d posLoc = positionsLocalPiece.get(position);
                this.tableauJeu[ii][jj] = new Case(ii, jj, piece.getCouleur(), index, (int) posLoc.x, (int) posLoc.y);
            }
        }
        else
            return false;
        setChanged();
        notifyObservers();
        return true;

    }

    private boolean peutEtrePosee(int i, int j, int index, int decalageX, int decalageY, int x, int y) {
        return j+y >= 0 && i+x >= 0
                && j+y-decalageY< this.getLargeur() && i+x-decalageX < this.getHauteur() //Si cela ne dépasses pas notre plateau de jeu
                //On teste les collisions
                && ((tableauJeu[i+x-decalageX][j+y-decalageY] == null) || //Ou la case est vide et on peut poser notre pièce.
                (tableauJeu[i+x-decalageX][j+y-decalageY]!= null && tableauJeu[i+x-decalageX][j+y-decalageY].getIndex() == index));
    }

    /**
     * On crée une nouvelle instance de pièce qu'on pose sur notre plateau de jeu.
     */
    public boolean newPiece(){
        this.pieceCourante = null;

        //On crée une pièce à partir des modèles disponibles dans le pool de pièces
        if(piecesSuivantes != null){
            this.pieceCourante = new Piece(piecesSuivantes.get(0), true);
            this.piecesPosees.add(pieceCourante);
            setChanged();
            notifyObservers();
            return this.poserPiecePlateau(pieceCourante,0,0);
        }

        setChanged();
        notifyObservers();
        return false;
    }

    public boolean newPiece(Piece piece,int i,int j,boolean h, Color couleur){
        this.pieceCourante = null;
        this.pieceCourante = new Piece(piece, false,h, couleur);
        this.piecesPosees.add(pieceCourante);
        setChanged();
        notifyObservers();
        return this.poserPiecePlateau(pieceCourante,i,j);
    }

    /**
     * Prends en charge le déplacement d'une piece sur un plateau
     * @param direction Enum qui determine si on la déplace en haut, a droite, à gauche ou en bas
     * @param piece Piece à déplacer
     */
    private boolean deplacer(Direction direction, Piece piece){
        //tester si la place est occupée avant de bouger
        int iter = 1; //L'itérateur qui va compter le nombea de déplacements réalisés.
        ArrayList<Vec2d> positions = occurrencesPiecesPlateau(piece); //Toutes les occurences de notre pièce donnée sur le plateau
        Vec2d min = minimum(positions);
        if(!positions.isEmpty() && min != null){
            if(!collision(positions, direction,this.piecesPosees.indexOf(piece))){ //Si nos positions ne génère pas de collisions
                effacerPiecePlateau(positions); //On efface la pièce
                //On la pose aux nouvelles coordonnées.
                // On la place à partir de la position précédente à laquelle on a ajouté (0,-1) par exemple pour la descendre verticalement
                this.poserPiecePlateau(piece,(int) min.x + direction.getX(), (int) min.y + direction.getY());
            }else{
                return false;
            }
        }
        setChanged();
        notifyObservers();
        return true;
    }

    /**
     * Retourne la première coordonnée (x,y) d'une pièce sur le plateau (en partant du haut à gauche)
     * @param arrayList tableau de coordonnées
     * @return
     */
    private Vec2d minimum(ArrayList<Vec2d> arrayList){
        if(arrayList!= null && !arrayList.isEmpty()){
            int minY =(int) arrayList.get(0).y;
            int minX =(int) arrayList.get(0).x;
            for (Vec2d anArrayList : arrayList) {
                if ((int) anArrayList.y < minY)
                    minY = (int) anArrayList.y;
                if ((int) anArrayList.x < minX)
                    minX = (int) anArrayList.x;
            }
            return new Vec2d(minX,minY);
        }
        return null;
    }

    //TODO : Javadoc
    public boolean versBas(Piece piece){
        return this.deplacer(Direction.DOWN, piece);
    }

    //TODO : Javadoc
    public boolean versDroite(Piece piece){
        return this.deplacer(Direction.RIGHT, piece);
    }

    //TODO : Javadoc
    public boolean versGauche(Piece piece){
        return this.deplacer(Direction.LEFT, piece);
    }

    //TODO : Javadoc
    public boolean versHaut(Piece piece){
        return this.deplacer(Direction.UP, piece);
    }

    /**
     * Rotation de la pièce courante.
     * @param dir direction de la rotation.
     */
    public void tournerPieceCourante(Direction dir) {
        pieceCourante.rotation(dir);
        ArrayList<Vec2d> positionsAvant = occurrencesPiecesPlateau(pieceCourante); //Toutes les occurences de notre pièce donnée sur le plateau
        Vec2d min = minimum(positionsAvant);
        effacerPiecePlateau(positionsAvant);
        if(!this.poserPiecePlateau(pieceCourante,(int) min.x, (int) min.y)){
            pieceCourante.rotation(dir.opposee());
            this.poserPiecePlateau(pieceCourante,(int) min.x, (int) min.y);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Retourne toutes les occurences (i,j) d'une pièce sur le plateau
     * @param piece notre pièce
     * @return
     */
    synchronized public ArrayList<Vec2d> occurrencesPiecesPlateau(Piece piece){
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

    /**
     * Determine si le mouvement d'une pièce dans une direction génère une collision (bords du plateau ou autre pièce)
     * @param occurrences coordonnées des occurences de la pièce sur le plateau
     * @param dir la direction du mouvement
     * @param index l'index de la pièce dans la liste des pièces posées
     * @return vrai si il y a collision
     */
    private boolean collision(ArrayList<Vec2d> occurrences, Direction dir, int index){
        for (Vec2d occurrence : occurrences){
            int a = (int) occurrence.x + dir.getX();
            int b = (int) occurrence.y + dir.getY();
            if (a<0 || a>=this.hauteur || b< 0 || b>=this.largeur || (this.getTableauJeu()[a][b] != null && this.getTableauJeu()[a][b].getIndex() != index ))
                return true;
        }
        return false;
    }

    /**
     * Efface les occurences d'une pièce sur le plateau.
     * @param occurrences cooredonnées de la pièce a supprimer (Vect2D)
     */
    private void effacerPiecePlateau(ArrayList<Vec2d> occurrences){
        if(occurrences != null){
            for (Vec2d occurrence : occurrences) {
                this.getTableauJeu()[(int) occurrence.x][(int) occurrence.y] = null;
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Supprime la case aux coordonnées (i,j) du plateau. ( y compris dans la pièce. )
     * @param i colonne
     * @param j ligne
     */
    private void supprimerCase(int i, int j) {
        //On récupère la case à supprimer et la case auquel elle appartient.
        Piece piece = recupererPiece(i, j);
        Case caseASuppr = tableauJeu[i][j];

        //Cas erreur
        if (piece == null) {
            System.out.println("La piece n'existe pas !");
            return;
        }
        else if (caseASuppr == null) {
            System.out.println("La pièce à suppr n'existe pas !");
            return;
        }

        //On supprime la case de la pièce.
        piece.supprimerCase( caseASuppr.getxLocal(), caseASuppr.getyLocal() );
        //On supprime la case du plateau.
        tableauJeu[i][j] = null;

    }

    /**
     * Supprime la ligne ii du tableau de jeu, et fais descendre toutes les pièces d'un cran.
     * @param ii
     */
    public void supprimerLigne(int ii) {

        //Cas outOfBound
        if ( ii < 0 || ii >= tableauJeu.length) {
            System.out.println("Il n'existe pas de ligne " + ii + " !");
            return;
        }

        //On supprime le contenu de la ligne
        for (int j = 0; j < tableauJeu[0].length; j++)
            supprimerCase(ii, j);

        boolean ligneVide;
        //On descend d'une case tout le reste du plateau en partant du bas, on s'arrête prématurément si on tombe sur une ligne vide.
        for (int i = ii-1; i > 0; i-- ) {
            ligneVide = true;
            for (int j = 0; j < tableauJeu[0].length; j++) {

               //System.out.println("i = " + i + ", j = " + j);
                if ( ligneVide && tableauJeu[i][j] != null )
                    ligneVide = false;


                tableauJeu[i+1][j] = tableauJeu[i][j];
                tableauJeu[i][j] = null;

            }
            //Si la ligne était vide on s'arrête tout de suite, il n'y a rien d'autre à redescendre.
            if (ligneVide)
                break;
        }

        setChanged();
        notifyObservers();

    }

    /**
     * Permet de savoir quelle ligne de otre plateau doit être supprimée, c'est à dire qui est complète.
     * @return ligne complète
     */
    public int ligneASupprimer(){
        boolean estRemplie;
        for (int i = 0; i < this.hauteur ; i++) {
            estRemplie = true;
            for (int j = 0; j < this.largeur; j++) {
                if(this.tableauJeu[i][j] == null){
                    estRemplie = false;
                }
            }
            if(estRemplie){
                return i;
            }
        }
        return -1; //Si aucune ligne est remplie
    }

    /**
     * Renvoie la pièce à qui appartient la case aux coordonnées x,y du plateau.
     */
    public Piece recupererPiece(int x, int y) {
        if(tableauJeu[x][y] != null && x < tableauJeu.length && y < tableauJeu[0].length)
            return piecesPosees.get(tableauJeu[x][y].getIndex());
        else
            return null;
    }

    //TODO : Javadoc
    public void reinitialiser(){
        for (int i = 0; i < this.hauteur; i++) {
            for (int j = 0; j < this.largeur; j++) {
                this.getTableauJeu()[i][j]=null;
            }
        }
        this.setPieceCourante(null);
        this.getPiecesSuivantes().clear();
        this.getPiecesPosees().clear();
    }

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

    public Piece[] getPoolDePiece() {
        return PoolDePiece;
    }

    public ArrayList<Piece> getPiecesSuivantes() {
        return piecesSuivantes;
    }

    public ArrayList<Piece> getPiecesPosees() {
        return piecesPosees;
    }
}