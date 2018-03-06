package mvc.Model;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class PieceBuilder {

    private HashMap<String, Piece> listePieces;

    public PieceBuilder() {
        listePieces = new HashMap<>();
    }

    public void addPiece (String name, int n, int m, Color couleur, int vitesseChute, Vec2d[] tabV) {

        int[][] matricePiece = new int[n][m];

        //---- ON REMPLIT MATRICE PIECE

        /* TODO : A LIRE
            Ici j'ai pris la convention 0 = case vide. Je peux passer à null = case vide si vous préférez,
            faudra juste modifier quelques bouts du codes.
         */

        //On initialise les cases vides à 0 (Utile? Ou les laisser à Null ?)
        for ( int i = 0; i < n; i++) {
            for ( int j = 0; j < m; j++) {
                matricePiece[i][j] = 0;
            }
        }
        //Pour chaque coordonnées dans tabV, on définit un bout de piece dans le tableau à ses coordoonnée.
        if(tabV != null)
            for ( Vec2d Coor : tabV ) {

                //Gestion erreur (inBound)
                if ( Coor.x >= n || Coor.x < 0 || Coor.y >= m || Coor.y < 0 ) {
                    //TODO : Raise erreur ? Si on tombe dans ce cas là fonction s'arrête.
                    System.out.println("ERREUR coordonnées non valides ! ( Négative ou supérieur à la taille de la case ) : " + Coor.toString() );
                    return;
                }

                //TODO : Fonction COULEUR TO INT pour attribuer des int à une couleur. On met 1 par défaut pour l'instant.
                matricePiece[(int)Coor.x][(int)Coor.y] = 1;
                    //TODO : A LIRE ----  VEC2D est un couple de double. Est-ce une bonne idée pour des coordonnées par case ? ( Cast obligatoire ! )
            }
        else{
            //TODO : generer pièce aléatoire
            //Voir Léa pour son idée d'algo récursif
        }

        //On génère la pièce et on l'ajoute à notre liste de piece.
        Piece piece = new Piece(name, couleur, vitesseChute, matricePiece);
        if(tabV != null)
            piece.setTaille(tabV.length);
        if ( listePieces.containsKey(name) )
            System.out.println("Une pièce nommée \'"+name+"\' existait déjà, elle a été écrasé par la nouvelle pièce générée.");
        listePieces.put(name, piece);

    }

    public void addPiece (String name, Color couleur, int vitesseChute, Vec2d[] tabV) {

        //boolean colGaucheVide = true;
        //boolean ligBottomVide = true;
        int maxX = 0;
        int maxY = 0;
        //On cherche la taille de matrice nécessaire pour acceuillir la pièce.

        //On cherche les maximums x et y
        for ( Vec2d Coor : tabV ) {
            if ( Coor.x > maxX )
                maxX = (int)Coor.x;
            if ( Coor.y > maxY )
                maxY = (int)Coor.y;
        }

        int tMat = Math.max(maxX, maxY) + 1;

        addPiece(name, tMat, tMat, couleur, vitesseChute, tabV);

    }

    //Surcharges de addPiece() pour entrer moins de valeur
    //TODO : Se mettre d'accord sur des valeurs de Couleur et vitesseChute par défaut, ici RED et 0.
    public void addPiece (String name, int n, int m, Vec2d[] tabV ) {
        addPiece(name, n, m, Color.RED, 0, tabV );
    }
    public void addPiece (String name, int n, int m, Color couleur, Vec2d[] tabV ) {
        addPiece(name, n, m, couleur, 0, tabV );
    }
    public void addPiece (String name, Color couleur, Vec2d[] tabV ) {
        addPiece(name, couleur, 0, tabV );
    }

    public void removePiece (String name) {
        listePieces.remove(name);
    }

    public void clearListePiece() {
        listePieces.clear();
    }

    /**
     * Renvoie un tableau de Piece construit à partir de toutes les pieces contenus dans la HashMap listePieces.
     * C'est ce format qu'on utilisera pour manipuler nos pieces.
     * @return
     */
    public Piece[] exporterListe() {
        return listePieces.values().toArray( new Piece[listePieces.size()] );
    }

    //Méthodes booléenes
    public boolean listeIsEmpty() {
        return listePieces.isEmpty();
    }
    public boolean listeContains(String name) {
        return listePieces.containsKey(name);
    }

    //Méthodes d'affichage

    public void afficherPiece(String name) {
        Piece piece = listePieces.get(name);

        if (piece == null) { //La piece n'existe pas !
            System.out.println("La piece \""+ name + "\" n'existe pas !" );
            return;
        }

        piece.afficherPiece();
    }

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

    //Accesseurs

    //TODO : Faire tout les getters/setters de Piece pour pouvoir modifier les attributs Couleur/Vitesse/(ect...) d'une piece à travers cette méthode.
    public Piece getPiece(String name) {

        //Si la pièce est dans la liste.
        if ( listePieces.containsKey(name) )
            return listePieces.get(name);

        //TODO : Raise erreur ?
        System.out.println("ERREUR : Il n'y a aucune piece avec ce nom dans la liste des pièces !");
        return null;
    }

    //Permet de récupérer la HashMap, pas très utile normalement.
    public HashMap<String, Piece> getListePiece() {
        return listePieces;
    }

}
