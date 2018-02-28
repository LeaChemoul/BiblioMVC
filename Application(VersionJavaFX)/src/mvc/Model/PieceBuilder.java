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

        //TODO : Meilleur nom pour tabV.
        int[][] matricePiece = new int[n][m];

        //---- ON REMPLIT MATRICE PIECE

        //On initialise les cases vides à 0 (Utile? Ou les laisser à Null ?)
        for ( int i = 0; i < n; i++) {
            for ( int j = 0; j < m; j++) {
                matricePiece[i][j] = 0;
            }
        }
        //Pour chaque coordonnées dans tabV, on définit un bout de piece dans le tableau à ses coordoonnée.
        for ( Vec2d Coor : tabV ) {

            //Gestion erreur (inBound)
            if ( Coor.x >= n || Coor.x < 0 || Coor.y >= m || Coor.y < 0 ) {
                //TODO : Raise erreur ? Si on tombe dans ce cas là fonction s'arrête.
                System.out.println("ERREUR coordonnées non valides ! ( Négative ou supérieur à la taille de la case ) : " + Coor.toString() );
                return;
            }

            //TODO : Fonction COULEUR TO INT. On met 1 par défaut pour l'instant.
            matricePiece[(int)Coor.x][(int)Coor.y] = 1;
                //NOTE : VEC2D est un couple de double. Est-ce une bonne idée pour des coordonnées par case ? Cast obligatoire !
        }

        //TODO : Calcul pivot à partir des coordonnées. Pivot = 0,0 par défaut.
        Vec2d pivot = new Vec2d(0d, 0d);

        //On génère la pièce et on l'ajoute à notre liste de piece.
        Piece piece = new Piece(name, couleur, vitesseChute, matricePiece , pivot );
        if ( listePieces.containsKey(name) )
            System.out.println("Une pièce associé à la clé \'"+name+"\' existait déjà, elle a été écrasé par la nouvelle.");
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

    //TODO : A renommer avec un meilleur nom ?
    public Piece[] recupererListe() {

        //On renvoie un tableau crée à partir de la HashMap du builder, vu qu'on manipulera pas une HashMap mais un array de
        //Piece dans le reste du code.
        return (Piece[])listePieces.values().toArray();

    }


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

        for ( int i = 0; i < n; i++ ) {
            for (int j = 0; j < m; j++) {

                //Si la case est égale à 0, elle est vide. (Convention actuelle. A CHANGER ?)
                if ( matricePiece[i][j] != 0 )
                    System.out.print("X");
                else
                    System.out.print(" ");

            }
            System.out.println(); //retour chariot.
        }

        //On affiche sa couleur
            //TODO : INT TO COULEUR. On met RED par défaut.
        Color coul = Color.RED;
        System.out.println("Couleur de la piece : "+ coul.toString() );

        //On affiche sa vitesse de chute.
            //TODO : Getter getVitesse();
        int vitesse = 0; // piece.getVitesse();
        System.out.println("Vitesse de chute de la piece : " + vitesse );

        //On affiche son pivot.
            //TODO : Getter getPivot();
        Vec2d pivot = new Vec2d(0, 0); // piece.getPivot();
        System.out.println("Coordonnées du pivot : " + pivot );

    }

    public void afficherPieces() {

        //Cas erreur
        if (listePieces.isEmpty()) {
            System.out.println("Il n'y a aucune piece dans le builder !");
            return;
        }

        //Pour chaque piece de listePiece, on affiche la piece.
        listePieces.forEach( (name, piece) -> {
            afficherPiece(name);
        });
    }

    //Accesseurs

    //TODO : Faire les getters/setters de Piece pour pouvoir modifier Couleur/Vitesse/ect... à travers cette méthode.
    public HashMap<String, Piece> getListePiece() {
        return listePieces;
    }

}
