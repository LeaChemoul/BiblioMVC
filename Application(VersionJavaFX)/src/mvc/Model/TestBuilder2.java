package mvc.Model;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

public class TestBuilder2 {


    static PieceBuilder builder = new PieceBuilder();

    public static void main ( String[] args ) {


        Vec2d[] listeCoor = new Vec2d[] { new Vec2d(0, 1), new Vec2d(0, 2), new Vec2d(0, 3), new Vec2d(0, 0) };
        builder.addPiece("Barre", Color.GREEN, listeCoor);

        centrerPiece("Barre");
        rotationComplete("Barre");

        listeCoor = new Vec2d[] { new Vec2d(1, 1), new Vec2d(2, 0), new Vec2d(2, 1), new Vec2d(2, 2) };
        builder.addPiece("Tetro", Color.BLUE, listeCoor);


        centrerPiece("Tetro");
        rotationComplete("Tetro");

        listeCoor = new Vec2d[] { new Vec2d(0, 0), new Vec2d(1, 0), new Vec2d(1, 1), new Vec2d(1, 2), new Vec2d(1, 3) };
        builder.addPiece("P", Color.BLUE, listeCoor);

        centrerPiece("P");
        rotationComplete("P");



    }

    static public void centrerPiece(String name) {
        builder.afficherPiece(name);
        System.out.println("    TEST - CENTRER PIECE ");
        builder.getPiece(name).centrerPiece();
        builder.afficherPiece(name);
    }

    static public void rotationComplete(String name) {

        System.out.println("    TEST - Rotation complete de la piece " + name);
        builder.afficherPiece(name);
        System.out.println("    Rotation droite(1) ");
        builder.getPiece(name).Rotation(Direction.RIGHT);
        builder.getPiece(name).afficherPiece();
        System.out.println("    Rotation droite(2) ");
        builder.getPiece(name).Rotation(Direction.RIGHT);
        builder.getPiece(name).afficherPiece();
        System.out.println("    Rotation droite(3) ");
        builder.getPiece(name).Rotation(Direction.RIGHT);
        builder.getPiece(name).afficherPiece();
        System.out.println("    Rotation droite (TOUR COMPLET) ");
        builder.getPiece(name).Rotation(Direction.RIGHT);
        builder.getPiece(name).afficherPiece();
    }
}
