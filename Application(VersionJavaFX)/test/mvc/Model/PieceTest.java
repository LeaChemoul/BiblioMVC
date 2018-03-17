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
        /*pieceBuilder.addPiece("test",4,4, Color.rgb(120,150,1),2,listeVect);
        pieceBuilder.afficherPiece("test");
        pieceBuilder.getPiece("test").Rotation_New(Direction.RIGHT);
        pieceBuilder.afficherPiece("test");*/
    }
}