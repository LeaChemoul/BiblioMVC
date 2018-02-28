package mvc.VueControleur;
import mvc.Model.Piece;
import mvc.Model.PieceBuilder;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

public class TestBuilder {

    public static void main ( String[] args ) {

        PieceBuilder builder = new PieceBuilder();

        builder.afficherPiece("Piece1");
        builder.afficherPieces();

        System.out.println("----------------------------");

        Vec2d[] listeCoor = new Vec2d[] { new Vec2d(0, 1), new Vec2d(0, 2), new Vec2d(0, 3), new Vec2d(0, 0) };
        builder.addPiece("Barre", 4, 4, Color.GREEN, listeCoor);

        listeCoor = new Vec2d[] { new Vec2d(1, 1), new Vec2d(2, 0), new Vec2d(2, 1), new Vec2d(2, 2) };
        builder.addPiece("Equerre", 3, 3, Color.BLUE, 3, listeCoor);

        System.out.println("----------------------------");

        listeCoor = new Vec2d[] { new Vec2d(0, 0), new Vec2d(1, 0), new Vec2d(0, 1), new Vec2d(1, 1) };
        builder.addPiece("Barre", 2, 2, Color.RED, listeCoor);

        builder.afficherPiece("Barre");
        System.out.println("----------------------------");
        builder.afficherPieces();

        Piece[] listeDesPieces = builder.recupererListe();


    }
}
