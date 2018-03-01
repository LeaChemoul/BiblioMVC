package mvc.Model;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class PieceBuilder {

    private HashMap<String, Piece> listePieces;

    public PieceBuilder() {
        listePieces = new HashMap<>();
    }

    public void addPiece (String name, int n, int m, Color couleur, int vitesseChute, Vec2d[] tabV ) {

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
        //TODO : Calcul pivot à partir des coordonnées. Pivot = 0,0 par défaut.
        Vec2d pivot = new Vec2d(0d, 0d);

        //On génère la pièce et on l'ajoute à notre liste de piece.
        Piece piece = new Piece(name, couleur, vitesseChute, matricePiece , pivot );
        if ( listePieces.containsKey(name) )
            System.out.println("Une pièce nommée \'"+name+"\' existait déjà, elle a été écrasé par la nouvelle pièce générée.");
        listePieces.put(name, piece);

    }

    //Surcharges de addPiece() pour entrer moins de valeur
    //TODO : Se mettre d'accord sur des valeurs de Couleur et vitesseChute par défaut, ici RED et 0.
    public void addPiece (String name, int n, int m, Vec2d[] tabV ) {
        addPiece(name, n, m, Color.RED, 0, tabV );
    }
    public void addPiece (String name, int n, int m, Color couleur, Vec2d[] tabV ) {
        addPiece(name, n, m, couleur, 0, tabV );
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

        //On affiche le nom de la pièce.
        System.out.println("Piece \'"+name+"\' : ");

            //On récupère la matrice qui la compose.
        int[][] matricePiece = piece.getCases();
            //On récupère les dimensions de la pièce.
        int n = matricePiece.length;
        int m = matricePiece[0].length;

        System.out.println("Dimensions : "+n+"*"+m);

        //Ligne bordure au sommet.
        System.out.print("  -");
        for ( int j = 0; j < m; j++ )
            System.out.print("---");
        System.out.println("-");

        for ( int i = 0; i < n; i++ ) {

            System.out.print("  |");
            for (int j = 0; j < m; j++) {

                //Si la case est égale à 0, elle est vide. (Convention actuelle. A CHANGER ?)
                if ( matricePiece[i][j] != 0 )
                    System.out.print(" X ");
                else
                    System.out.print("   ");

            }
            System.out.println("|");
        }

        //Ligne bordure au pied.
        System.out.print("  -");
        for ( int j = 0; j < m; j++ )
            System.out.print("---");
        System.out.println("-");

        /* TYPE DE RENDU :
            -------
            |     |
            |  X  |
            | XXX |
            -------
         */

        //On affiche sa couleur
            //TODO : Créer un COLOR TO STRING. Pour l'instant getCouleur() et getCouleur().toString() renvoie tout les deux un code hexadecimal dégeulasse.
        System.out.println("Couleur de la piece : "+ piece.getCouleur() );

        //On affiche sa vitesse de chute.
        System.out.println("Vitesse de chute de la piece : " + piece.getVitesseChute() );

        //On affiche son pivot.
        System.out.println("Coordonnées du pivot : " + piece.getPivot() );

        System.out.println("---------------");
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
