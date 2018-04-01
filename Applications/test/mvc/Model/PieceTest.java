package mvc.Model;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import mvc.Model.Direction;
import mvc.Model.PieceBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void rotation() {
        PieceBuilder pieceBuilder = new PieceBuilder();
        Vec2d[] listeVect = new Vec2d[4];
        listeVect[0] = new Vec2d(2,2);
        listeVect[1] = new Vec2d(1,2);
        listeVect[2] = new Vec2d(3,2);
        listeVect[3] = new Vec2d(1,3);
        pieceBuilder.addPiece("test",Color.rgb(120,150,1),new double[]{0,0, 1,0, 2,0, 2,1, 2,2});
        pieceBuilder.afficherPiece("test");
        pieceBuilder.getPiece("test").rotation(Direction.DOWN);
        pieceBuilder.getPiece("test").rotation(Direction.DOWN);
        pieceBuilder.getPiece("test").rotation(Direction.DOWN);
        pieceBuilder.afficherPiece("test");
    }
}