package mvc.Model;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import mvc.Blokus.ModeleBlokus.Partie;

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

        System.out.println("    TEST - PIECES BLOKUS");
        builder.clearListePiece();
        genererPiecesBlokus();
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

    public static void genererPiecesBlokus() {

        /*
        //Pièce à une case
        builder.addPiece("Piece1-1", new double[]{0,0});

        //Pièce à deux cases
        builder.addPiece("Piece2-1", new double[]{0,0, 1,0});

        //Pièces à trois cases
        builder.addPiece("Piece3-1", new double[]{0,0, 1,0 , 2,0});
        builder.addPiece("Piece3-2", new double[]{0,0, 1,0 , 1,1});

        //Pièces à quatre cases
        builder.addPiece("Piece4-1", new double[]{0,0, 1,0 , 2,0, 3,0});
        builder.addPiece("Piece4-2", new double[]{2,0, 2,1 , 1,1, 0,1});
        builder.addPiece("Piece4-3", new double[]{0,0, 1,0 , 1,1, 2,0});
        builder.addPiece("Piece4-4", new double[]{0,0, 1,0 , 1,1, 0,1});
        builder.addPiece("Piece4-5", new double[]{0,0, 0,1 , 1,1, 1,2});

        //Pièces à cinq cases
        builder.addPiece("Piece5-1", new double[]{0,0, 1,0, 2,0, 3,0, 4,0});
        builder.addPiece("Piece5-2", new double[]{3,0, 3,1, 2,1, 1,1, 0,1});
        builder.addPiece("Piece5-3", new double[]{0,1, 1,1, 2,1, 2,0, 3,0});
        builder.addPiece("Piece5-4", new double[]{0,1, 1,0, 1,1, 2,1, 2,0});
        builder.addPiece("Piece5-5", new double[]{0,0, 0,1, 1,1, 2,1, 2,0});
        builder.addPiece("Piece5-6", new double[]{0,0, 1,0, 2,0, 3,0, 1,1});
        builder.addPiece("Piece5-7", new double[]{0,1, 1,1, 2,1, 2,2, 2,0});
        builder.addPiece("Piece5-8", new double[]{0,0, 1,0, 2,0, 2,1, 2,2});
        builder.addPiece("Piece5-9", new double[]{0,0, 0,1, 1,1, 1,2, 2,2});
        builder.addPiece("Piece5-10", new double[]{0,0, 1,0, 1,1, 1,2, 2,2});
        builder.addPiece("Piece5-11", new double[]{0,0, 1,0, 1,1, 1,2, 2,1});
        builder.addPiece("Piece5-12", new double[]{0,1, 1,0, 1,1, 1,2, 2,1});
        */

        //DEBUG
        //builder.afficherPieces();
    }

}
