package mvc.Model;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

public class TestBuilder {

    public static void main ( String[] args ) {

        PieceBuilder builder = new PieceBuilder();

        System.out.println("    TEST - Affichage d'une pièce non présente dans la liste :");
        builder.afficherPiece("Piece1");
        System.out.println("    TEST - Affichage d'une liste vide :");
        builder.afficherPieces();

        System.out.println("    TEST - Remplissage de la liste du Builder avec deux nouvelles pieces + Affichage toute une liste");
        Vec2d[] listeCoor = new Vec2d[] { new Vec2d(0, 1), new Vec2d(0, 2), new Vec2d(0, 3), new Vec2d(0, 0) };
        builder.addPiece("Barre", Color.GREEN, listeCoor);

        listeCoor = new Vec2d[] { new Vec2d(1, 1), new Vec2d(2, 0), new Vec2d(2, 1), new Vec2d(2, 2) };
        builder.addPiece("Tetro", Color.BLUE, listeCoor);

        builder.afficherPieces();

        System.out.println("    TEST - Remplacer une piece par une autre avec le même nom : ");
        listeCoor = new Vec2d[] { new Vec2d(0, 0), new Vec2d(1, 0), new Vec2d(0, 1), new Vec2d(1, 1) };
        builder.addPiece("Barre", Color.RED, listeCoor);

        System.out.println("    TEST - Afficher une seule piece de la liste : ");
        builder.afficherPiece("Barre");

        System.out.println("    TEST - Supprimer une piece de la liste : ");
        builder.removePiece("Barre");

        System.out.println("    TEST - Tester absence de la pièce supprimé : ");
        builder.afficherPiece("Barre");

        System.out.println("    TEST - Ajout nouvelle Piece + Affichage Liste : ");
        listeCoor = new Vec2d[] { new Vec2d(0, 1), new Vec2d(1, 0), new Vec2d(1, 1), new Vec2d(2, 0) };
        builder.addPiece("Zigzag1", Color.RED, listeCoor);
        builder.afficherPieces();

        System.out.println("    TEST - Vider Liste + Vérification : ");
        builder.clearListePiece();
        if ( builder.listeIsEmpty() )
            System.out.println("La liste est vide !");
        else
            System.out.println("La liste n'est pas vide!");


        listeCoor = new Vec2d[] { new Vec2d(0, 1), new Vec2d(1, 0), new Vec2d(1, 1), new Vec2d(2, 0) };
        builder.addPiece("Zigzag1", Color.RED, listeCoor);
        listeCoor = new Vec2d[] { new Vec2d(0, 0), new Vec2d(1, 0), new Vec2d(2, 0), new Vec2d(3, 0) };
        builder.addPiece("Barre",  Color.RED, listeCoor);

        System.out.println("    TEST - Exporter liste (Fonctionne si aucun message d'erreur) ");
        Piece[] listeDesPieces = builder.exporterArray();

        builder.afficherPieces();

        System.out.println("    TEST - Translations Right");
        //listeDesPieces[0].translater(Direction.RIGHT);
        listeDesPieces[0].afficherInfosPiece();
        System.out.println("    TEST - rotation droite ");
        listeDesPieces[0].rotation(Direction.RIGHT);
        listeDesPieces[0].afficherInfosPiece();
        System.out.println("    TEST - rotation droite ");
        listeDesPieces[0].rotation(Direction.RIGHT);
        listeDesPieces[0].afficherInfosPiece();
        System.out.println("    TEST - rotation droite ");
        listeDesPieces[0].rotation(Direction.RIGHT);
        listeDesPieces[0].afficherInfosPiece();
        System.out.println("    TEST - rotation droite (TOUR COMPLET) ");
        listeDesPieces[0].rotation(Direction.RIGHT);
        listeDesPieces[0].afficherInfosPiece();
        System.out.println("    TEST - rotation gauche ");
        listeDesPieces[0].rotation(Direction.LEFT);
        listeDesPieces[0].afficherInfosPiece();


        System.out.println("    TEST - rotation gauche ");
        listeDesPieces[1].rotation(Direction.LEFT);
        listeDesPieces[1].afficherInfosPiece();
        System.out.println("    TEST - rotation gauche ");
        listeDesPieces[1].rotation(Direction.LEFT);
        listeDesPieces[1].afficherInfosPiece();
        System.out.println("    TEST - rotation vide (droite) ");
        listeDesPieces[1].rotation(Direction.UP);
        listeDesPieces[1].afficherInfosPiece();

        /*

        System.out.println("    TEST - Translations ");
        listeCoor = new Vec2d[] { new Vec2d(1, 1), new Vec2d(1, 2), new Vec2d(2, 1), new Vec2d(2, 2) };
        builder.addPiece("Cube", Color.RED, listeCoor);
        builder.afficherPiece("Cube");
        System.out.println("    TEST - Translations UP");
        builder.getPiece("Cube").translater(Direction.UP);
        builder.afficherPiece("Cube");
        System.out.println("    TEST - Translations LEFT");
        builder.getPiece("Cube").translater(Direction.LEFT);
        builder.afficherPiece("Cube");
        System.out.println("    TEST - Translations DOWN");
        builder.getPiece("Cube").translater(Direction.DOWN);
        builder.afficherPiece("Cube");
        System.out.println("    TEST - Translations RIGHT");
        builder.getPiece("Cube").translater(Direction.RIGHT);
        builder.afficherPiece("Cube");
        */


    }
}
