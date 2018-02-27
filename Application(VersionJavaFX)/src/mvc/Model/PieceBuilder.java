package mvc.Model;

import com.sun.istack.internal.Nullable;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;

import java.util.Vector;

public class PieceBuilder {

    public final static int XMAX = 5;
    public final static int YMAX = 5;

    public Piece createPiece(int n, int m, @Nullable Vector<Vec2d> cases){
        if(cases != null){

        }else{

        }
        return new Piece("test",Color.WHITE,0, null, null);
    }

    public Vec2d barycentre(int[][] matrice){

        return new Vec2d(0,0);
    }
}
