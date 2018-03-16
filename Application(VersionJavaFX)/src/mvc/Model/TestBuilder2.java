package mvc.Model;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

public class TestBuilder2 {


    static PieceBuilder builder = new PieceBuilder();

    public static void main ( String[] args ) {


        Vec2d[] listeCoor = new Vec2d[] { new Vec2d(0, 1), new Vec2d(0, 2), new Vec2d(0, 3), new Vec2d(0, 0) };
        builder.addPiece("Barre", Color.GREEN, listeCoor);

        afficherInfosPiece("Barre");
        rotationComplete("Barre");

        listeCoor = new Vec2d[] { new Vec2d(1, 1), new Vec2d(2, 0), new Vec2d(2, 1), new Vec2d(2, 2) };
        builder.addPiece("Tetro", Color.BLUE, listeCoor);

        afficherInfosPiece("Tetro");
        rotationComplete("Tetro");

        listeCoor = new Vec2d[] { new Vec2d(0, 0), new Vec2d(1, 0), new Vec2d(1, 1), new Vec2d(1, 2), new Vec2d(1, 3) };
        builder.addPiece("P", Color.BLUE, listeCoor);

        afficherInfosPiece("P");
        rotationComplete("P");

        System.out.println("    TEST - Ajout tableau double");
        builder.addPiece("Zigzag", new double[]{ 4,4 , 3,4 , 3,3, 2,3 } );
        afficherPiece("Zigzag");
        rotationComplete("Zigzag");

        System.out.println("    TEST - Ajout tableau double NbValeurs impair");
        builder.addPiece("Angle", new double[]{ 5,5 , 4,4 , 4,5, 2 } );
        afficherPiece("Angle");
        rotationComplete("Angle");




    }

    static public void afficherInfosPiece(String name) {
        builder.getPiece(name).afficherInfosPiece();
    }

    static public void afficherPiece(String name) {
        builder.getPiece(name).afficherPiece();
    }

    static public void rotationComplete(String name) {

        System.out.println("    TEST - Rotation complete de la piece (POSITION INITIAL) : " + name);
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
