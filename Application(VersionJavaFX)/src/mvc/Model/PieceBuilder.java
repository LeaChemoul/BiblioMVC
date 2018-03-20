package mvc.Model;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class PieceBuilder {

    private HashMap<String, Piece> listePieces;

    public PieceBuilder() {
        listePieces = new HashMap<>();
    }

    /**
     * Créer et Ajoute à listePieces une Pièce défini par les paramètres donnés
     * @param name Nom de la Pièce
     * @param couleur Couleur de la Pièce
     * @param tabV Coordonnées des cases qui composent la pièce.
     */
    public void addPiece (String name, Color couleur, Vec2d[] tabV) {

        //ON CALCULE LA TAILLE REQUISE DE LA MATRICE
        int maxX = 0;
        int maxY = 0;

        //On cherche les maximums x et y
        for ( Vec2d Coor : tabV ) {
            if ( Coor.x > maxX )
                maxX = (int)Coor.x;
            if ( Coor.y > maxY )
                maxY = (int)Coor.y;
        }

        int hauteur = maxY, largeur = maxX; //On les renomme pour la lisibilité.
        int[][] matricePiece = new int[hauteur+1][largeur+1];

        //---- ON REMPLIT MATRICE PIECE

        //On initialise les cases vides à 0
        for ( int i = 0; i < hauteur; i++) {
            for ( int j = 0; j < largeur; j++) {
                matricePiece[i][j] = 0;
            }
        }

        //Pour chaque coordonnées dans tabV, on définit un bout de piece dans le tableau à ses coordoonnée.
        for ( Vec2d Coor : tabV ) {

            //Gestion erreur (inBound)
            if ( Coor.x < 0 || Coor.y < 0 ) {
                //TODO : Raise erreur ? Si on tombe dans ce cas là fonction s'arrête.
                System.out.println("ERREUR coordonnées non valides ! ( Négative ) : " + Coor.toString() );
                return;
            }
            matricePiece[(int)Coor.y][(int)Coor.x] = 1;
        }

        //On génère la pièce et on l'ajoute à notre liste de piece.
        //Le calcul de la taille, du pivot, et le centrage de la pièce sont pris en charge par le constructeur de la Piece.
        Piece piece = new Piece(name, couleur, matricePiece);
        piece.centrerPiece();

        //Si il y avait déjà une pièce avec le même nom, on l'écrase et on informe l'utilisateur..
        if ( listePieces.containsKey(name) )
            System.out.println("Une pièce nommée \'"+name+"\' existait déjà, elle a été écrasé par la nouvelle pièce générée.");

        listePieces.put(name, piece);
    }

    /**
     * Créer et Ajoute à listePieces une Pièce défini par les paramètres donnés
     * @param name Nom de la Pièce
     * @param tabV Coordonnées des cases qui composent la pièce.
     */
    public void addPiece (String name, Vec2d[] tabV) {
        addPiece(name, Color.BLACK, tabV);
    }

    /**
     * Ajoute à listePieces la pièce donnée en argument
     * Attention ! NE RECENTRE PAS.
     * @param piece Piece à ajouter à ListePieces.
     */
    public void addPiece (Piece piece) {
        if ( listePieces.containsKey(piece.getNom()) )
            System.out.println("Une pièce nommée \'"+piece.getNom()+"\' existait déjà, elle a été écrasé par la nouvelle pièce générée.");

        listePieces.put(piece.getNom(), piece);
    }

    /**
     * Ajout de pièce alternatif. On rentre une liste de double et il va automatiquement le trier en couple de valeur, des coordonnées.
     * Sert juste à rendre l'ajout de pièce plus simple sans avoir à déclarer énormément de "new Vec2d(x,y)" à la suite.
     * @param name Nom de la piece
     * @param tabCoor Liste des coordonnées x,y de la pièce, tel quel : [x1, y1, x2, y2, ect...]
     */
    public void addPiece(String name, double[] tabCoor) {
        addPiece(name, arrayDoubleToVec2D(tabCoor));
    }

    public void addPiece(String name, Color couleur, double[] tabCoor) {
        addPiece(name, couleur, arrayDoubleToVec2D(tabCoor));
    }


    /**
     * Supprime la pièce avec le nom donné
     * @param name Nom de la piece à supprimer
     */
    public void removePiece (String name) {
        listePieces.remove(name);
    }

    /**
     * Vide la HashMap listePieces
     */
    public void clearListePiece() {
        listePieces.clear();
    }

    /**
     * Renvoie un tableau de Piece construit à partir de toutes les pieces contenus dans la HashMap listePieces.
     * C'est ce format qu'on utilisera pour manipuler nos pieces.
     * @return Tableau de Piece
     */
    public Piece[] exporterArray() {
        return listePieces.values().toArray( new Piece[listePieces.size()] );
    }

    /**
     * Renvoie une liste de Pice construite à partir de toutes les pieces contenus dans la HashMap listePieces.
     * C'est ce format qu'on utilisera pour manipuler nos pieces.
     * @return Liste de Pieces
     */
    public ArrayList<Piece> exporterArrayList() {

        //Decrepated
        //return new ArrayList<Piece>(listePieces.values());

        //On procède par copie, en copiant chaque pièce, pour éviter de référencer celle du builder.
        ArrayList<Piece> listePieceExport = new ArrayList<>();
        listePieces.forEach( (name, piece) -> listePieceExport.add( new Piece(piece, false) ) );

        return listePieceExport;
    }

    //Méthodes booléenes

    /**
     * @return Vrai si la liste est vide, false sinon.
     */
    public boolean listeIsEmpty() {
        return listePieces.isEmpty();
    }

    /**
     * @param name Nom de la Piece
     * @return Vrai si la pièce existe dans la listePieces, faux sinon.
     */
    public boolean listeContains(String name) {
        return listePieces.containsKey(name);
    }

    //Méthodes d'affichage

    /**
     * Affiche la matrice de la pièce avec le nom donnée.
     * @param name Nom de la pièce
     */
    public void afficherPiece(String name) {
        Piece piece = getPiece(name);
        if ( piece != null )
            piece.afficherPiece();
    }
    /**
     * Affiche toutes les informations (nom, matrice, couleur, pivot) de la pièce avec le nom donné.
     * @param name Nom de la pièce
     */
    public void afficherInfosPiece(String name) {
        Piece piece = getPiece(name);
        if ( piece != null )
            piece.afficherInfosPiece();
    }

    /**
     * Affiche simplement (matrice uniquement) toutes les pièces dans le builder.
     */
    public void afficherPieces() {

        //Cas erreur
        if (listePieces.isEmpty()) {
            System.out.println("Il n'y a aucune piece dans le builder !");
            return;
        }

        System.out.println("----- Liste des Pieces -----");
        //Pour chaque piece de listePiece, on affiche la piece.
        listePieces.forEach( (name, piece) -> {
            afficherPiece(name);
        });
    }
    /**
     * Affiche les informations complètes de toutes les pièces dans le builder.
     */
    public void afficherInfosPieces() {

        //Cas erreur
        if (listePieces.isEmpty()) {
            System.out.println("Il n'y a aucune piece dans le builder !");
            return;
        }

        System.out.println("----- Liste des Pieces -----");
        //Pour chaque piece de listePiece, on affiche la piece.
        listePieces.forEach( (name, piece) -> {
            afficherInfosPiece(name);
        });
    }

    /**
     * Set la couleur de toutes les pièces de listePieces avec la couleur donnée.
     * @param couleur nouvelle couleur des Pieces
     */
    public void setCouleurAll(Color couleur) {
        listePieces.forEach( (name, piece) -> {
            piece.setCouleur(couleur);
        });
    }

    //Fonctions statiques

    /**
     * Prends un tableau de double et renvoie un tableau de Vec2d en sélectionnant les valeurs deux à deux.
     * Si le nombre de valeur est impair, ignore la dernière.
     * @param tabCoor Tableau de coordonnées
     * @return Tableau de Vec2d
     */
    public static Vec2d[] arrayDoubleToVec2D(double[] tabCoor ) {
        //Récupère les valeurs double de tabCoor deux à deux et remplit un tableau de Vec2d avec.
        Vec2d[] tabVec = new Vec2d[tabCoor.length/2];

        //Si le nombre de coordonnées est impair, on ignore la dernière grâce à " - tabCoor.length%2 "
        for ( int i = 0; i < tabCoor.length - tabCoor.length%2 ; i+=2 ) {
            tabVec[i/2] = new Vec2d( tabCoor[i], tabCoor[i+1] );
        }

        /* DEBUG
        System.out.println("tabCoor = " + Arrays.toString(tabCoor) );
        System.out.println("tabVec = " + Arrays.toString(tabVec) );
        */

        return tabVec;
    }

    //Accesseurs

    public Piece getPiece(String name) {
        //Si la pièce est dans la liste.
        if ( listePieces.containsKey(name) )
            return listePieces.get(name);

        System.out.println("Erreur : La pièce "+name+" n'existe pas !");
        return null;
    }

    //Permet de récupérer la HashMap, pas très utile normalement.
    public HashMap<String, Piece> getListePiece() {
        return listePieces;
    }

}
